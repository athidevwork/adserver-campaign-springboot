/**
 * 
 */
package com.adserver.campaign;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.adserver.campaign.controller.CampaignController;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * @author Athi
 *
 */
@Configuration
@ApplicationPath("adserver")
public class JerseyConfiguration extends ResourceConfig {
	  public JerseyConfiguration() {
		  
	  }
	 
	  @PostConstruct
	  public void setUp() {
	    register(CampaignController.class);
	    register(GenericExceptionMapper.class);
	    register(JacksonJaxbJsonProvider.class);
	  }
}
