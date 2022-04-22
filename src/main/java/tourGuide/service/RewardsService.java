package tourGuide.service;

import java.util.concurrent.ExecutorService;

import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.Location;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;

public interface RewardsService {
	
	public void setProximityBuffer(int proximityBuffer);
	
	public void setDefaultProximityBuffer();
	
	public void calculateRewards(User user);
	
	public boolean isWithinAttractionProximity(Attraction attraction, Location location);
	
	public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction);
	
	public int getRewardPoints(Attraction attraction, User user);
	
	public double getDistance(Location loc1, Location loc2);

	public ExecutorService getExecutorService();

	public ExecutorService getExecutorService2();

	public void setExecutorService(ExecutorService executorService);

	public void setExecutorService2(ExecutorService executorService2);
	

}
