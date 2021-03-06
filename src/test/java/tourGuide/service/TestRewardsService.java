package tourGuide.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;
import tourGuide.service.webclient.GpsUtilWebClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRewardsService {

	@Autowired
	TourGuideService tourGuideService;
	
	@Autowired
	RewardsService rewardsService;
	
	@Autowired
	GpsUtilWebClient gpsUtilWebClient;
	
	@Autowired
	InternalTestHelperService internalTestHelperService;
	
	
	@Test
	public void userGetRewards() {
		
		internalTestHelperService.setInternalUserNumber(0);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsUtilWebClient.getListAttractions().get(0);
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
		tourGuideService.trackUserLocation(user);
	    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) tourGuideService.getExecutorService();
	    
		while(threadPoolExecutor.getActiveCount() >0) {
		    try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		List<UserReward> userRewards = user.getUserRewards();
		tourGuideService.getTracker().stopTracking();
		assertTrue(userRewards.size() == 1);
	}
	
	@Test
	public void isWithinAttractionProximity() {
		Attraction attraction = gpsUtilWebClient.getListAttractions().get(0);
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
	}
	
	
	@Test
	public void nearAllAttractions() {
		
		rewardsService.setProximityBuffer(Integer.MAX_VALUE);
		
		internalTestHelperService.setInternalUserNumber(1);
		
		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
		List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0)); //ici
		
		tourGuideService.getTracker().stopTracking();

	    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) rewardsService.getExecutorService();

		while(threadPoolExecutor.getActiveCount() >0) {
		    try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		assertEquals(gpsUtilWebClient.getListAttractions().size(), userRewards.size());

		rewardsService.setDefaultProximityBuffer();
		
	}
	
}
