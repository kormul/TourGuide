package tourGuide.service;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.Location;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;
import tourGuide.service.webclient.GpsUtilWebClient;
import tourGuide.service.webclient.RewardCentralWebClient;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	
	public int a = 0;
	public int b = 0;
	public int c = 0;
	public int d = 0;
	public int e = 0;
	
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	
	private ExecutorService executorService = Executors.newFixedThreadPool(32000);
	private ExecutorService executorService2 = Executors.newFixedThreadPool(32000);

	
	@Autowired
	private GpsUtilWebClient gpsUtilWebClient;
	
	@Autowired
	private RewardCentralWebClient rewardCentralWebClient;
	
	public RewardsService() {
	}
	
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}
	
	public void calculateRewards(User user) {
		
		logger.debug("trackUserLocation");

		a++;
		CompletableFuture.supplyAsync(()-> {		
			b++;
			List<Attraction> attractions = gpsUtilWebClient.getListAttractions();
			c++;
			return attractions;
		}, executorService ).thenAccept(attractions -> {
			
			List<VisitedLocation> userLocations = user.getVisitedLocations();
			d++;
			userLocations.stream().forEach((visitedLocation) -> {
				attractions.stream().forEach((attraction) -> {
					
					if(user.getUserRewards().stream().filter(r -> r.getAttraction().getAttractionName().equals(attraction.getAttractionName())).count() == 0) {
						if(nearAttraction(visitedLocation, attraction)) {
							
							CompletableFuture.supplyAsync(()->{
								return getRewardPoints(attraction, user);
							}, executorService2).thenAccept(points -> {
								user.addUserReward(new UserReward(visitedLocation, attraction, points));
								e++;
							});
						}
					}
				});
			});
		});
	}
	
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}
	
	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.getLocation()) > proximityBuffer ? false : true;
	}
	
	private int getRewardPoints(Attraction attraction, User user) {
		return rewardCentralWebClient.getRewardPointsWebClient(attraction.getAttractionId(), user.getUserId());
	}
	
	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public ExecutorService getExecutorService2() {
		return executorService2;
	}
}
