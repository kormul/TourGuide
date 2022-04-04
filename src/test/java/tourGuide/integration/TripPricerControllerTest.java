package tourGuide.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tourGuide.service.InternalTestHelperService;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class TripPricerControllerTest {

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
    public void getTripDealsTest() throws Exception {
    	internalTestHelperService.setInternalUserNumber(1);

        String userName = "internalUser0";

        MvcResult result = mockMvc.perform(get("/getTripDeals")
                .param("userName", userName))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains("tripId"));
        Assert.assertTrue(result.getRequest().getParameter("userName").contains(userName));
    }
}
