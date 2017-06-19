package com.adserver.campaign;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

import com.adserver.campaign.controller.CampaignController;
import com.adserver.campaign.domain.Ad;
import com.adserver.campaign.domain.AdRepositoryImpl;
import com.adserver.campaign.service.AdServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CampaignController.class, secure = false)
public class AdserverCampaignControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdServiceImpl adService;
	
	//@MockBean
	//private AdRepositoryImpl adRepository;
	
	@Test
	public void listAll() {
	    Collection<Ad> mockCampaign = Arrays.asList(
	              new Ad(1, "Comcast", 180, "Comcast First Ad Content"),
	              new Ad(2, "Verizon", 120, "Verizon First Ad Content"));
	      
		Mockito.when(
				adService.listAllAdCampaigns()).thenReturn(mockCampaign);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/students/Student1/courses/Course1").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = null;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(result.getResponse());
		
	      //Response response = campaignController.listAllAdCampaigns();	
	      
	      /*Collection<Ad> adList = (Collection<Ad>) response.getEntity();
	      assertEquals(Response.ok(), response.getStatus());
		  assertEquals(2, adList.size());*/	      
	}
}
