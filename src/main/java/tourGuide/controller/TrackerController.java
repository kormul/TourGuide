package tourGuide.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.service.TourGuideService;

@RestController
public class TrackerController {

	private Logger logger = LogManager.getLogger();

	@Autowired
	TourGuideService tourGuideService;
	
    @GetMapping("/location/startTracker")
    public void startTracker() {
        logger.debug("Controller Start Tracker");
        tourGuideService.getTracker().startTracking();
    }

    @GetMapping("/location/stopTracker")
    public void stopTracker() {
        logger.debug("Controller Stop Tracker");
        tourGuideService.getTracker().stopTracking();
    }
}
