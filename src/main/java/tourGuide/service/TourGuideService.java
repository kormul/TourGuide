package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.dto.PreferencesDTO;
import tourGuide.dto.UserLocationDTO;
import tourGuide.model.Provider;
import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.UserReward;
import tourGuide.service.webclient.GpsUtilWebClient;
import tourGuide.service.webclient.TripPricerWebClient;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	
	@Autowired
	private RewardsService rewardsService;
	
	@Autowired
	private GpsUtilWebClient gpsUtilWebClient;
	
	@Autowired
	private TripPricerWebClient tripPricerWebClient;
	
	@Autowired
	private InternalTestHelperService internalTestHelperService;
	
	private ExecutorService executorService = Executors.newFixedThreadPool(1000);
	
	@Autowired
	private Tracker tracker;
	
	boolean testMode = true;
	
	public List<UserReward> getUserRewards(User user) {
		logger.debug("getUserRewards");
		return user.getUserRewards();
	}
	
	public VisitedLocation getUserLocation(User user) {
		logger.debug("getUserLocation");

		
		if(user.getVisitedLocations().size() == 0)
			trackUserLocation(user);
		
		return user.getLastVisitedLocation();
	}
	
	public User getUser(String userName) {
		logger.debug("getUser");

		return internalTestHelperService.getInternalUserMap().get(userName);
	}
	
	public List<User> getAllUsers() {
		logger.debug("getAllUsers");

		return internalTestHelperService.getInternalUserMap().values().stream().collect(Collectors.toList());
	}
	
	public List<UserLocationDTO> getLocationUsers(){
		logger.debug("getLocationUsers");

		List<User> users = this.getAllUsers();
		List<UserLocationDTO> userLocationDTOs = new ArrayList<>();
		
		users.parallelStream().forEach((user) -> {
			userLocationDTOs.add(new UserLocationDTO(user.getUserId(), user.getLastVisitedLocation().getLocation()));
		});
		
		return userLocationDTOs;
	}
	
	public void addUser(User user) {
		logger.debug("addUser");

		if(!internalTestHelperService.getInternalUserMap().containsKey(user.getUserName())) {
			internalTestHelperService.getInternalUserMap().put(user.getUserName(), user);
		}
	}
	
	public List<Provider> getTripDeals(User user) {
		logger.debug("getTripDeals");

		int cumulatativeRewardPoints = user.getUserRewards().parallelStream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = tripPricerWebClient.getPrice(InternalTestHelperService.getTrippricerapikey(), user.getUserId().toString(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	
	public void trackUserLocation(User user){
		
			CompletableFuture.supplyAsync(()-> {		
				//logger.debug("trackUserLocation");

				VisitedLocation visitedLocation = gpsUtilWebClient.getUserLocation(user.getUserId());
				return visitedLocation;
				
			}, executorService ).thenAccept(visitedLocation -> {
				user.addToVisitedLocations(visitedLocation);
				rewardsService.calculateRewards(user);
			});
		
	}

	public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
		logger.debug("getNearByAttractions");

		List<Attraction> allAttractions = gpsUtilWebClient.getListAttractions();
		
		allAttractions.sort((a1, a2)-> {
			if(rewardsService.getDistance(a1, visitedLocation.getLocation()) > rewardsService.getDistance(a1, visitedLocation.getLocation()))
				return -1;
			return 1;
		});
		return allAttractions.subList(0, 5);
	}
	
	public void userPreferenceUpdate(String userName, PreferencesDTO preferencesDTO) {
		logger.debug("userPreferenceUpdate");

		User user = internalTestHelperService.getInternalUserMap().get(userName);
		
		if(user == null) {
			return;
		}
		
		UserPreferences userPreferences = user.getUserPreferences();
		userPreferences.setAttractionProximity(preferencesDTO.getAttractionProximity());
		userPreferences.setCurrency(Monetary.getCurrency(preferencesDTO.getCurrency()));
		userPreferences.setLowerPricePoint(Money.of(preferencesDTO.getLowerPricePoint(), userPreferences.getCurrency()));
		userPreferences.setHighPricePoint(Money.of(preferencesDTO.getHighPricePoint(), userPreferences.getCurrency()));
		userPreferences.setTripDuration(preferencesDTO.getTripDuration());
		userPreferences.setTicketQuantity(preferencesDTO.getTicketQuantity());
		userPreferences.setNumberOfAdults(preferencesDTO.getNumberOfAdults());
		userPreferences.setNumberOfChildren(preferencesDTO.getNumberOfChildren());
		
	}
	
	/*private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}*/
	
	public Tracker getTracker() {
		logger.debug("getTracker");

		return tracker;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	
	
}
