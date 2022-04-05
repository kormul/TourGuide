package tourGuide.service.webclient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tourGuide.model.attraction.Attraction;
import tourGuide.model.location.VisitedLocation;

@Service
public class GpsUtilWebClient {
	
	public String dockerURLLocation = "http://localhost:8082";
	
	public VisitedLocation getUserLocation(UUID userId) {
		
		VisitedLocation visitedLocation;
		
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
    
        ResponseEntity<VisitedLocation> result  = restTemplate.getForEntity(dockerURLLocation+"/getUserLocation?userId=" + userId, VisitedLocation.class);
        visitedLocation = result.getBody();
        
        return visitedLocation;
	}

    public List<Attraction> getListAttractions() {
    	
    	List<Attraction> attractionList;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Attraction>> result = restTemplate.exchange(dockerURLLocation+"/getListAttractions",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Attraction>>(){});
        
        attractionList= result.getBody();
        return attractionList;
    }
}
