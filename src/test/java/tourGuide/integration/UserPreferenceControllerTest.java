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

    	UUID userID = new UUID(1,1);
    	
        PreferencesDTO preferencesDTO = new PreferencesDTO(new UUID(1,1).toString(), 0,"0",1,1,1,1,1,1);

        internalTestHelperService.setInternalUserNumber(1);

        User user = new User(userID, "internalUser0", "1243456", "internalUser0@Gmail.com");
        
        String userName = "internalUser0";
        String questionBody = "{\n" +
                "\"attractionProximity\": 21447,\n" +
                "\"currency\": \"USD\",\n" +
                "\"lowerPricePoint\": 0.0,\n" +
                "\"highPricePoint\": 300.0,\n" +
                "\"tripDuration\": 1,\n" +
                "\"ticketQuantity\": 1,\n" +
                "\"numberOfAdults\": 1,\n" +
                "\"numberOfChildren\": 0\n" +
                "}";

        MvcResult result = mockMvc.perform(put("/update/Preferences")
                .param("userName", userName)
                .content(questionBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertFalse(preferencesDTO.getNumberOfAdults() == user.getUserPreferences().getNumberOfAdults());
    }
}
