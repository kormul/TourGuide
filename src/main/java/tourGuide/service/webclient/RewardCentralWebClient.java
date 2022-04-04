package tourGuide.service.webclient;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RewardCentralWebClient {
	
    public int getRewardPointsWebClient(UUID attractionUUID, UUID userUUID) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        int rewardPoints;

        ResponseEntity<Integer> result  =
        restTemplate.getForEntity("http://localhost:8083/getRewardPoints?attractionId=" + attractionUUID +"&userId=" + userUUID, Integer.class);

        rewardPoints = result.getBody();
        return rewardPoints;
    }

}
