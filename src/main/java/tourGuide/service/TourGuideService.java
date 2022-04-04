package tourGuide.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.model.Provider;
import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;
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
	
	@Autowired
	private Tracker tracker;
	
	boolean testMode = true;
	
	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}
	
	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0) ?
			user.getLastVisitedLocation() :
			trackUserLocation(user);
		return visitedLocation;
	}
	
	public User getUser(String userName) {
		return internalTestHelperService.getInternalUserMap().get(userName);
	}
	
	public List<User> getAllUsers() {
		//return internalTestHelperService.getInternalUserMap().values().stream().collect(Collectors.toList()).subList(0, InternalTestHelper.getInternalUserNumber());
		return internalTestHelperService.getInternalUserMap().values().stream().collect(Collectors.toList());

	}
	
	public void addUser(User user) {
		if(!internalTestHelperService.getInternalUserMap().containsKey(user.getUserName())) {
			internalTestHelperService.getInternalUserMap().put(user.getUserName(), user);
		}
	}
	
	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().parallelStream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = tripPricerWebClient.getPrice(InternalTestHelperService.getTrippricerapikey(), user.getUserId().toString(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	
	public VisitedLocation trackUserLocation(User user) {
		VisitedLocation visitedLocation = gpsUtilWebClient.getUserLocation(user.getUserId());
		user.addToVisitedLocations(visitedLocation);
		rewardsService.calculateRewards(user);
		return visitedLocation;
	}

	public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
		List<Attraction> nearbyAttractions = new ArrayList<>();

		gpsUtilWebClient.getListAttractions().parallelStream().forEach((attraction) -> {
			if(rewardsService.isWithinAttractionProximity(attraction, visitedLocation.getLocation())) {
				nearbyAttractions.add(attraction);
			}
		});
		
		return nearbyAttractions;
	}
	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}
	
	public Tracker getTracker() {
		return tracker;
	}
	
	
}
