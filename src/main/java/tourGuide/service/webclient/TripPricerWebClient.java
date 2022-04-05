package tourGuide.service.webclient;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tourGuide.model.Provider;

@Service
public class TripPricerWebClient {

	public String dockerURLLocation = "http://localhost:8081";

	public List<Provider> getPrice(String apiKey, String userStringId, int adults, int children, int duration, int rewardsPoints){
		
		RestTemplate restTemplate = new RestTemplate();
        List<Provider> providers;
        
        ResponseEntity<List<Provider>> result =
        		restTemplate.exchange(dockerURLLocation+"/getTripPricer" +
        				"?apiKey="+ apiKey +
        				"&userStringId=" + userStringId +
        				"&adults=" +adults+
        				"&children=" +children+
        				"&duration=" +duration+
        				"&rewardsPoints=" + rewardsPoints,
        				HttpMethod.GET, null, new ParameterizedTypeReference<List<Provider>>() {
						});
        providers=result.getBody();
		return providers;
		
	}

}
