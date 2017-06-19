package com.adserver.campaign;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.adserver.campaign.controller.CampaignController;
import com.adserver.campaign.domain.Ad;
import com.adserver.campaign.domain.AdRepository;
import com.adserver.campaign.service.AdService;
import com.adserver.campaign.service.AdServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JerseyConfiguration.class, AdserverCampaignApplication.class})
@SpringBootTest
@WebMvcTest(value = CampaignController.class, secure=false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdServerMockCampaignControllerTest {
  private MockMvc mockMvc;

  @MockBean
  private Ad ad;
  
  @MockBean
  private AdRepository adRepository;
  
  @MockBean
  private AdServiceImpl adService;

  @InjectMocks
  private CampaignController campaignController;

  @Autowired
  WebApplicationContext wac;
  
  @Before
  public void setup(){
	  System.out.println("test case setup");
      MockitoAnnotations.initMocks(this);
      mockMvc = MockMvcBuilders
              .standaloneSetup(campaignController, adService, adRepository)
              //.addFilters(new CORSFilter())
              .build();
      //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
  }

//=========================================== Get All ads ==========================================

  //@Test
  public void test_get_all_success() throws Exception {
      Collection<Ad> campaign = Arrays.asList(
              new Ad(1, "Comcast", 180, "Comcast First Ad Content"),
              new Ad(2, "Verizon", 120, "Verizon First Ad Content"));

      when(adService.listAllAdCampaigns()).thenReturn(campaign);

      mockMvc.perform(get("/adserver/ad/campaign"))
      .andExpect(status().isNoContent());
      
      mockMvc.perform(get("/adserver/ad"));
      
      mockMvc.perform(get("/adserver/ad/campaign"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
              //.andExpect(jsonPath("$", hasSize(2)))
              .andExpect(jsonPath("$.name", is("Foo")))
              //.andExpect(jsonPath("$.number", notNullValue())
              .andExpect(jsonPath("$[0].id", is(1)))
              .andExpect(jsonPath("$[0].partner", is("Comcast")))
              .andExpect(jsonPath("$[1].id", is(2)))
              .andExpect(jsonPath("$[1].partner", is("Verizon")));

      verify(adService, times(1)).listAllAdCampaigns();
      verifyNoMoreInteractions(adService);
  }// =========================================== Create New ad ========================================

  //@Test
  public void test_create_ad_success() throws Exception {
      Ad ad = new Ad(3, "Sample", 360, "Sample First Ad Content");

      when(adService.getAdByPartner("Sample")).thenReturn(ad);
      when(adService.saveAd(ad)).thenReturn(ad);

      mockMvc.perform(
              post("/adserver/ad/campaign")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(ad)))
              .andExpect(status().isOk());
              //.andReturn(ad);
              //.andExpect(header().string("location", containsString("http://localhost/campaign/")));

      verify(adService, times(1)).getAdByPartner("Sample");
      verify(adService, times(1)).saveAd(ad);
      verifyNoMoreInteractions(adService);
  }

  //@Test
  public void test_create_ad_fail_409_conflict() throws Exception {
      Ad ad = new Ad(3, "Sample", 360, "Sample First Ad Content");

      when(adService.getAdByPartner("Sample") != null).thenReturn(true);

      mockMvc.perform(
              post("/ads")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(ad)))
              .andExpect(status().isConflict());

      verify(adService, times(1)).getAdByPartner("Sample");
      verifyNoMoreInteractions(adService);
  }

  // =========================================== Update Existing ad ===================================

  //@Test
  public void test_update_ad_success() throws Exception {
      Ad ad = new Ad(3, "Sample", 360, "Sample First Ad Content");

      when(adService.getAdByPartner(ad.getPartner())).thenReturn(ad);
      doNothing().when(adService).updateAd("Sample", ad);

      mockMvc.perform(
              put("/adserver/ad/campaign/{partner}", ad.getId())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(ad)))
              .andExpect(status().isOk());

      verify(adService, times(1)).getAdByPartner(ad.getPartner());
      verify(adService, times(1)).updateAd("Sample", ad);
      verifyNoMoreInteractions(adService);
  }

  //@Test
  public void test_update_ad_fail_404_not_found() throws Exception {
      Ad ad = new Ad(4, "TestSample", 360, "Test Sample First Ad Content");

      when(adService.getAdByPartner(ad.getPartner())).thenReturn(null);

      mockMvc.perform(
              put("/adserver/ad/campaign/{partner}", ad.getPartner())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(ad)))
              .andExpect(status().isNotFound());

      verify(adService, times(1)).getAdByPartner(ad.getPartner());
      verifyNoMoreInteractions(adService);
  }

  // =========================================== Delete ad ============================================

  //@Test
  public void test_delete_ad_success() throws Exception {
      Ad ad = new Ad(3, "Sample", 360, "Sample First Ad Content");

      when(adService.getAdByPartner(ad.getPartner())).thenReturn(ad);
      doNothing().when(adService).deleteAd("Sample", ad);

      mockMvc.perform(
              delete("/adserver/ad/campaign/{partner}", ad.getId()))
              .andExpect(status().isOk());

      verify(adService, times(1)).getAdByPartner(ad.getPartner());
      verify(adService, times(1)).deleteAd("Sample", ad);
      verifyNoMoreInteractions(adService);
  }

  //@Test
  public void test_delete_ad_fail_404_not_found() throws Exception {
      Ad ad = new Ad(4, "TestSample", 360, "Test Sample First Ad Content");

      when(adService.getAdByPartner(ad.getPartner())).thenReturn(null);

      mockMvc.perform(
              delete("/adserver/ad/campaign/{partner}", ad.getPartner()))
              .andExpect(status().isNotFound());

      verify(adService, times(1)).getAdByPartner(ad.getPartner());
      verifyNoMoreInteractions(adService);
  }

  // =========================================== CORS Headers ===========================================

  //@Test
  public void test_cors_headers() throws Exception {
      mockMvc.perform(get("/ads"))
              .andExpect(header().string("Access-Control-Allow-Origin", "*"))
              .andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
              .andExpect(header().string("Access-Control-Allow-Headers", "*"))
              .andExpect(header().string("Access-Control-Max-Age", "3600"));
  }

  /*
   * converts a Java object into JSON representation
   */
  public static String asJsonString(final Object obj) {
      try {
          return new ObjectMapper().writeValueAsString(obj);
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }
}
