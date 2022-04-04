package tourGuide.service.webclient;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TripPricerWebClientTest {

	@Autowired
	private TripPricerWebClient tripPricerWebClient;
	
	   @Test
	    public void getPriceTest() {
	        String apiKey = "apiKey";
	        String userId =new UUID(1, 1).toString();
	        int adults = 1;
	        int children = 1;
	        int nightsStay = 1;
	        int rewardsPoints = 1;
	        Assertions.assertThat(tripPricerWebClient.getPrice(apiKey, userId, adults, children, nightsStay, rewardsPoints))
	                .isNotNull()
	                .isNotEmpty();
	    }
}
