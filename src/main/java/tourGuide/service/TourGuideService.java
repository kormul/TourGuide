package tourGuide.service;
import java.util.List;
import java.util.concurrent.ExecutorService;

import tourGuide.dto.PreferencesDTO;
import tourGuide.dto.UserLocationDTO;
import tourGuide.model.Provider;
import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;

public interface TourGuideService {
	
	public List<UserReward> getUserRewards(User user);
	
	public VisitedLocation getUserLocation(User user);
	
	public User getUser(String userName);
	
	public List<User> getAllUsers();
	
	public List<UserLocationDTO> getLocationUsers();
	
	public void addUser(User user);
	
	public List<Provider> getTripDeals(User user);
	
	
	public void trackUserLocation(User user);

	public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);
	
	public void userPreferenceUpdate(String userName, PreferencesDTO preferencesDTO);
	
	public Tracker getTracker();

	public ExecutorService getExecutorService();

	public void setExecutorService(ExecutorService executorService);
	

}
