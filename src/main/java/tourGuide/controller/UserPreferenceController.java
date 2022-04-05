package tourGuide.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.dto.PreferencesDTO;
import tourGuide.exception.UserNotFoundException;
import tourGuide.exception.UserPreferenceNotFoundException;
import tourGuide.service.InternalTestHelperService;
import tourGuide.service.TourGuideService;

@RestController
public class UserPreferenceController {

	private Logger logger = LogManager.getLogger();

	@Autowired
	TourGuideService tourGuideService;

	
	@Autowired
	InternalTestHelperService internalTestHelperService;
	
	@PutMapping("/user/preferences")
	public String userUpdatePreferences(@RequestParam String user, @RequestBody PreferencesDTO preferencesDTO) {
		
		logger.debug("update user preference controller");
		if(!internalTestHelperService.checkUserName(user)) {
			logger.error("user not find");
			throw new UserNotFoundException(user);
		}
		if(preferencesDTO == null) {
			logger.error("preference is null");
			throw new UserPreferenceNotFoundException();
		}
		
		tourGuideService.userPreferenceUpdate(user, preferencesDTO);
		
		return user;
	}
}
