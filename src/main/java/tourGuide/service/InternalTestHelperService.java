package tourGuide.service;

import java.util.Map;
import tourGuide.model.user.User;

public interface InternalTestHelperService {
	
	static final String tripPricerApiKey = "test-server-api-key";
	
	public void addUser(User user);
	
	public boolean checkUserName(String userName);

	public Map<String, User> getInternalUserMap();
	
	public void setInternalUserNumber(int internalUserNumber);

	public static String getTrippricerapikey() {
		return tripPricerApiKey;
	}

}
