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
public class RewardCentralWebClientTest {
	
	@Autowired
	private RewardCentralWebClient rewardCentralWebClient;
	
    @Test
    public void rewardCentralWebClientTest(){

        UUID attractionId = new UUID(1, 1);
        UUID userId = new UUID(1, 1);
        
        int rewardPoints = rewardCentralWebClient.getRewardPointsWebClient(attractionId, userId);

        Assertions.assertThat(rewardPoints).isNotNull();
        
    }
}
