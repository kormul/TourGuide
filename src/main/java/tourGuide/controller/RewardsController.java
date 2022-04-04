package tourGuide.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import tourGuide.exception.UserNotFoundException;
import tourGuide.service.InternalTestHelperService;
import tourGuide.service.TourGuideService;

@RestController
public class RewardsController {

	private Logger logger = LogManager.getLogger();
	
	@Autowired
	TourGuideService tourGuideService;

	@Autowired
    InternalTestHelperService internalTestHelperService;

    @GetMapping("/getRewards")
    public String getRewards(@RequestParam String userName) {
        logger.debug("Controller Get Rewards");
        if(!internalTestHelperService.checkUserName(userName)) {
            logger.error("This username does not exist" + userName);
            throw new UserNotFoundException(userName);
        }
    	return JsonStream.serialize(tourGuideService.getUserRewards(tourGuideService.getUser(userName)));
    }
    
}
