package tourGuide.service.webclient;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.model.attraction.Attraction;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GpsUtilWebClientTest {

	@Autowired
	private GpsUtilWebClient gpsUtilWebClient;
	
	@Test
	public void getUserLocationTest() {
		UUID userUUID = new UUID(1,1);
        Assertions.assertThat(gpsUtilWebClient.getUserLocation(userUUID))
        .isNotNull()
        .hasFieldOrPropertyWithValue("userId", userUUID)
        .hasFieldOrProperty("location")
        .hasFieldOrProperty("timeVisited");
	}
	
    @Test
    public void getAllAttractionsTest() {
        List<Attraction> attractions = gpsUtilWebClient.getListAttractions();
        Assertions.assertThat(attractions)
                .isNotNull()
                .isNotEmpty();
    }
}
