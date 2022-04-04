package tourGuide.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserPreferenceNotFoundException extends RuntimeException{

	private Logger logger = LogManager.getLogger();

	public UserPreferenceNotFoundException() {
		
		super("User preferences is null");
		logger.error("User preferences is null");
	}

}
