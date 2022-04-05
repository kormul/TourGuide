package tourGuide.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tourGuide.dto.PreferencesDTO;
import tourGuide.model.user.User;
import tourGuide.service.InternalTestHelperService;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPreferenceControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Autowired
	InternalTestHelperService internalTestHelperService;
	
	@Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
    @Test
    public void putUpdatePreferencesITest() throws Exception {

        internalTestHelperService.setInternalUserNumber(2);
        
        String userName = "internalUser0";
        String questionBody = "{\n" +
                "\"attractionProximity\": 2,\n" +
                "\"currency\": \"USD\",\n" +
                "\"lowerPricePoint\": 100.0,\n" +
                "\"highPricePoint\": 900.0,\n" +
                "\"tripDuration\": 5,\n" +
                "\"ticketQuantity\": 3,\n" +
                "\"numberOfAdults\": 2,\n" +
                "\"numberOfChildren\": 5\n" +
                "}";

        MvcResult result = mockMvc.perform(put("/user/preferences")
                .param("user", userName)
                .content(questionBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        
		User usera = internalTestHelperService.getInternalUserMap().get("internalUser0");
		User userb = internalTestHelperService.getInternalUserMap().get("internalUser1");
        
        Assert.assertTrue((usera.getUserPreferences().getNumberOfAdults() != userb.getUserPreferences().getNumberOfAdults()));
        Assert.assertTrue((usera.getUserPreferences().getNumberOfChildren() != userb.getUserPreferences().getNumberOfChildren()));

    }
}
