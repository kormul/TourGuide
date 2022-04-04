package tourGuide.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tourGuide.service.InternalTestHelperService;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class TrackerControllerTest {

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
    public void startTrackerTest() throws Exception {

        mockMvc.perform(get("/location/startTracker"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void stopTrackerTest() throws Exception {

        mockMvc.perform(get("/location/stopTracker"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
