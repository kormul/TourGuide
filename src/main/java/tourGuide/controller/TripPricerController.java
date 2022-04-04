package tourGuide.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import tourGuide.exception.UserNotFoundException;
import tourGuide.model.Provider;
import tourGuide.service.InternalTestHelperService;
import tourGuide.service.TourGuideService;

@RestController
public class TripPricerController {
	
	private Logger logger = LogManager.getLogger();

	@Autowired
	TourGuideService tourGuideService;

    @Autowired
    InternalTestHelperService internalTestHelperService;

   
    @GetMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
        logger.debug("Access to /getTripDeals endpoint with username : " + userName);
        if(!internalTestHelperService.checkUserName(userName)) {
            logger.error("This username does not exist" + userName);
            throw new UserNotFoundException(userName);
        }
    	List<Provider> providers = tourGuideService.getTripDeals(tourGuideService.getUser(userName));
    	return JsonStream.serialize(providers);
    }

}
