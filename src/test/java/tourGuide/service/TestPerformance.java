package tourGuide.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.service.webclient.GpsUtilWebClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPerformance {
	
	@Autowired
	TourGuideService tourGuideService;
	
	@Autowired
	RewardsService rewardsService;
	
	@Autowired
	GpsUtilWebClient gpsUtilWebClient;
	
	@Autowired
	InternalTestHelperService internalTestHelperService;
	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */
	
	@Test
	public void highVolumeTrackLocation()  {
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		internalTestHelperService.setInternalUserNumber(100000);
		
		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();

	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		for(User user : allUsers) {
			tourGuideService.trackUserLocation(user);
		}
		
	    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) tourGuideService.getExecutorService();
	    ThreadPoolExecutor threadPoolExecutor1 = (ThreadPoolExecutor) rewardsService.getExecutorService();
	    
		while(threadPoolExecutor.getActiveCount() >0) {
		    try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	    
		stopWatch.stop();
		tourGuideService.getTracker().stopTracking();
		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		 /*try {
				TimeUnit.SECONDS.sleep(180);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
	}
	
	@Test
	public void highVolumeGetRewards() {
		
		tourGuideService.setExecutorService(Executors.newFixedThreadPool(1000));
		rewardsService.setExecutorService(Executors.newFixedThreadPool(1000));
		rewardsService.setExecutorService2(Executors.newFixedThreadPool(1000));

		
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) tourGuideService.getExecutorService();
	    ThreadPoolExecutor threadPoolExecutor1 = (ThreadPoolExecutor) rewardsService.getExecutorService();

	    
		// Users should be incremented up to 100,000, and test finishes within 20 minutes
		internalTestHelperService.setInternalUserNumber(100000);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
	    Attraction attraction = gpsUtilWebClient.getListAttractions().get(0);
		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
	    /*allUsers.forEach(u -> rewardsService.calculateRewards(u));

		while(threadPoolExecutor.getActiveCount() >0 || threadPoolExecutor1.getActiveCount() >0) {
		    try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		List<User> FinalUser = new ArrayList<>();
		while (allUsers.size()>0) {
			for (int i = 0 ; i<allUsers.size() ; i++) {
				if (allUsers.get(i).getUserRewards().size() <= 0) {
					rewardsService.calculateRewards(allUsers.get(i));
				} else {
					FinalUser.add(allUsers.get(i));
					allUsers.remove(i);
					i--;
				}
			}
			while (threadPoolExecutor.getActiveCount() > 0 || threadPoolExecutor1.getActiveCount() > 0) {
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}


	    for(User user : FinalUser) {
			assertTrue(user.getUserRewards().size() > 0);
		}
		
		stopWatch.stop();
		tourGuideService.getTracker().stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
}
