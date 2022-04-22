package tourGuide.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import tourGuide.model.helper.InternalTestHelper;
import tourGuide.model.location.Location;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;

@Service
public class InternalTestHelperServiceImpl implements InternalTestHelperService{

	private Logger logger = LogManager.getLogger();

	// Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
	private Map<String, User> internalUserMap = new HashMap<>();
	
	public InternalTestHelperServiceImpl() {
		initializeInternalUsers();
	}
	
	private void initializeInternalUsers() {
		internalUserMap = new HashMap<>();
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);
			
			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}

	public void addUser(User user) {
		if (!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}
	
	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i-> {
			user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}

	private double generateRandomLongitude() {
		double leftLimit = -180;
	    double rightLimit = 180;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	
	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	    return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
	
	
	public boolean checkUserName(String userName) {
		
		return internalUserMap.containsKey(userName);
	}

	public Map<String, User> getInternalUserMap() {
		return internalUserMap;
	}
	
	public void setInternalUserNumber(int internalUserNumber) {
		InternalTestHelper.setInternalUserNumbera(internalUserNumber);
		this.initializeInternalUsers();
	}
	
}
