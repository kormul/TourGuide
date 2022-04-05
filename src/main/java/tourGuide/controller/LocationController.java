package tourGuide.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import net.minidev.json.JSONArray;
import tourGuide.exception.UserNotFoundException;
import tourGuide.model.location.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.service.InternalTestHelperService;
import tourGuide.service.TourGuideService;

@RestController
public class LocationController {
	
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	TourGuideService tourGuideService;
	
	@Autowired
    InternalTestHelperService internalTestHelperService;

 
    @GetMapping("/getLocation")
    public String getLocation(@RequestParam String userName) throws UserNotFoundException {
    	
        logger.debug("Controller Get Location");
        if(!internalTestHelperService.checkUserName(userName)) {
            logger.error("This username does not exist" + userName);
            throw new UserNotFoundException(userName);
        }
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(tourGuideService.getUser(userName));
        
        return JsonStream.serialize(visitedLocation.getLocation());
    }
    
    @GetMapping("/getNearByAttractions")
    public String getNearByAttractions(@RequestParam String userName) {
        logger.debug("Controller Get Near By Attractions");
        
        if(!internalTestHelperService.checkUserName(userName)) {
            logger.error("This username does not exist" + userName);
            throw new UserNotFoundException(userName);
        }
        
        User user = tourGuideService.getUser(userName);
        System.out.println(user.getEmailAddress());
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
        System.out.println(user.getEmailAddress());
    	return JsonStream.serialize(tourGuideService.getNearByAttractions(visitedLocation));
    }
    
    @GetMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        logger.debug("Controller Get All Current Attractions");
    	return JsonStream.serialize(tourGuideService.getLocationUsers());
    }

}
