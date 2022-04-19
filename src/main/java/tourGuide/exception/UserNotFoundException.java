package tourGuide.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException{
	
	private Logger logger = LogManager.getLogger();

	public UserNotFoundException(String user) {
		
		super("User not found : " + user);
		logger.error("User not found : " + user );
	}

}
