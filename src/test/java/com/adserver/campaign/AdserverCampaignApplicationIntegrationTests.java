package com.adserver.campaign;

import static org.testng.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.adserver.campaign.controller.CampaignController;
import com.adserver.campaign.domain.Ad;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CampaignController.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdserverCampaignApplicationIntegrationTests {

	@LocalServerPort
	private int port;
	
	Logger logger = Logger.getLogger(AdserverCampaignApplicationIntegrationTests.class);
	
	/*@BeforeMethod
	public void setup () {
		logger.info("Before Method");
		headers.add("Content-Type", MediaType.APPLICATION_JSON);
	}*/
	
	@Test
	public void testGetCampaignForNonExistentPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();		
		HttpEntity<String> ad = new HttpEntity<String>(null, headers);
		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign/TestSample", 
										HttpMethod.GET, ad, Ad.class);
		
		try {
			JSONAssert.assertEquals("204", String.valueOf(response.getStatusCodeValue()), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCampaignForPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpEntity<String> ad = new HttpEntity<String>(null, headers);
		
		logger.info("Creating some sample Ad's to query");
		restTemplate.exchange("http://localhost:8081/adserver/ad", 
				HttpMethod.GET, null, String.class);
		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign/Comcast", 
										HttpMethod.GET, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody());

		try {
			JSONAssert.assertEquals("200", String.valueOf(response.getStatusCodeValue()), false);
			assertEquals("Comcast", response.getBody().getPartner());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPostFirstCampaignForPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
	    Ad sample = new Ad("Sample", 60, "Sample First Ad Content");
	    headers.add("Content-Type", MediaType.APPLICATION_JSON);
		HttpEntity<String> ad = new HttpEntity<String>(new Gson().toJson(sample, Ad.class), headers);

		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign", 
										HttpMethod.POST, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody() + ", response ="+response);
		try {
			JSONAssert.assertEquals("200", String.valueOf(response.getStatusCodeValue()), false);
			assertEquals("Sample", response.getBody().getPartner());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test (dependsOnMethods={"testPostFirstCampaignForPartner"})
	public void testGetFirstCampaignForPartnerSample() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
		headers.add("Accept", MediaType.APPLICATION_JSON);
		HttpEntity<String> ad = new HttpEntity<String>(null, headers);
		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign/Sample", 
										HttpMethod.GET, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody());
		try {
			JSONAssert.assertEquals("200", String.valueOf(response.getStatusCodeValue()), false);
			assertEquals("Sample", response.getBody().getPartner());
			assertEquals(60L, response.getBody().getDuration());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test (dependsOnMethods={"testGetFirstCampaignForPartnerSample"})
	public void testPostDuplicateCampaignForPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
		logger.info("testPostDuplicateCampaignForPartner Start");
	    Ad sample = new Ad(9, "Sample", 60, "Sample First Ad Content");
	    headers.add("Content-Type", MediaType.APPLICATION_JSON);
		HttpEntity<String> ad = new HttpEntity<String>(new Gson().toJson(sample, Ad.class), headers);
		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign", 
										HttpMethod.POST, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody() + ", response ="+response);
		try {
			JSONAssert.assertEquals("304", String.valueOf(response.getStatusCodeValue()), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//@Test (dependsOnMethods={"testGetFirstCampaignForPartnerSample"})
	@Test (dependsOnMethods={"testPostDuplicateCampaignForPartner"})
	public void testPostSecondCampaignForPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
		logger.info("testPostSecondCampaignForPartner Start");

		logger.info("Waiting for 75 seconds to create the second Ad for sample...");
		try {
			Thread.sleep(75000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
	    Ad sample1 = new Ad("Sample", 90, "Sample Second Ad Content");
	    headers.add("Content-Type", MediaType.APPLICATION_JSON);
		HttpEntity<String> ad1 = new HttpEntity<String>(new Gson().toJson(sample1, Ad.class), headers);
		ResponseEntity<Ad> response1 = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign", 
										HttpMethod.POST, ad1, Ad.class);
		
		logger.info("Status=" + response1.getStatusCodeValue() +", Body=" + response1.getBody() + ", response ="+response1);
		try {
			JSONAssert.assertEquals("200", String.valueOf(response1.getStatusCodeValue()), false);
			assertEquals("Sample", response1.getBody().getPartner());
			assertEquals(90L, response1.getBody().getDuration());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
	
	@Test (dependsOnMethods={"testPostSecondCampaignForPartner"})
	public void testGetSecondCampaignForPartnerSample() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpEntity<String> ad = new HttpEntity<String>(null, headers);
		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign/Sample", 
										HttpMethod.GET, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody());
		try {
			JSONAssert.assertEquals("200", String.valueOf(response.getStatusCodeValue()), false);
			assertEquals("Sample", response.getBody().getPartner());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test (dependsOnMethods={"testGetSecondCampaignForPartnerSample"})
	public void testUpdateSecondCampaignForPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
	    Ad sample = new Ad("Sample", 120, "Updated Sample for Second Ad Content");
	    headers.add("Content-Type", MediaType.APPLICATION_JSON);
		HttpEntity<String> ad = new HttpEntity<String>(new Gson().toJson(sample, Ad.class), headers);

		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign/Sample", 
										HttpMethod.PUT, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody() + ", response ="+response);
		try {
			JSONAssert.assertEquals("200", String.valueOf(response.getStatusCodeValue()), false);
			assertEquals("Sample", response.getBody().getPartner());
			assertEquals(120L, response.getBody().getDuration());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
	
	@Test (dependsOnMethods={"testUpdateSecondCampaignForPartner"})
	public void testDeleteSecondCampaignForPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
	    headers.add("Content-Type", MediaType.APPLICATION_JSON);
		HttpEntity<String> ad = new HttpEntity<String>(null, headers);

		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign/Sample", 
										HttpMethod.DELETE, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody() + ", response ="+response);
		try {
			JSONAssert.assertEquals("200", String.valueOf(response.getStatusCodeValue()), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test (dependsOnMethods={"testDeleteSecondCampaignForPartner"})
	public void testDuplicateDeleteSecondCampaignForPartner() {
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();
	    headers.add("Content-Type", MediaType.APPLICATION_JSON);
		HttpEntity<String> ad = new HttpEntity<String>(null, headers);

		
		ResponseEntity<Ad> response = restTemplate.exchange("http://localhost:8081/adserver/ad/campaign/Sample", 
										HttpMethod.DELETE, ad, Ad.class);
		
		logger.info("Status=" + response.getStatusCodeValue() +", Body=" + response.getBody() + ", response ="+response);
		try {
			JSONAssert.assertEquals("304", String.valueOf(response.getStatusCodeValue()), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
}
