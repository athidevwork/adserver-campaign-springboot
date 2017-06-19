package com.adserver.campaign.controller;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adserver.campaign.domain.Ad;
import com.adserver.campaign.service.AdServiceImpl;

@Component
@Path("/ad")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CampaignController {
	
	@Autowired
	private AdServiceImpl adService;

	Logger logger = Logger.getLogger("CampaignController");
	
	@GET
	public Response home() {
		adService.createCampaign();
		return Response.ok().entity("You have reached the Campaigns Home").build();
	}
	
	/** 
	 * listAllAdCampaigns - List all active campaigns.
	 * @return Response
	 */
	@GET
	@Path("/campaign")
	public Response listAllAdCampaigns() {
		Collection<Ad> adList = adService.listAllAdCampaigns();
		if(adList.isEmpty()){
			return Response.noContent().build();
		}
		return Response.ok().entity(adList).build();
	}

	/**
	 * getAd - Gets an active ad for a partner.
	 * @param partner
	 * @return Response
	 */
	@GET
	@Path("/campaign/{partner}")
	public Response getAd(@PathParam("partner") String partner) {
        logger.info("Fetching User with id " + partner);
        Ad ad = adService.getAdByPartner(partner);
        if (ad == null) {
            System.out.println("Ad for partner " + partner + " not found");
            return Response.noContent().build();
        }
        return Response.ok().entity(ad).build();
    }
	
	/**
	 * createAd - Creates a new Ad based on input
	 * @param ad
	 * @return Response
	 */
	@POST
	@Path("/campaign")
	public Response createAd (Ad ad) {
		logger.info("Creating Ad for partner " + ad.getPartner());
  
        if (adService.isAnAdActiveForPartner(ad.getPartner())) {
            logger.info("An active Ad  for partner " + ad.getPartner() + " already exists.");
            return Response.notModified().entity("Active Ad for the partner already exists.").build();
        }
        
        Ad newAd = adService.saveAd(ad);
        if (newAd != null)
        	return Response.ok().entity(newAd).build();
        else
        	return Response.serverError().entity("Server Error Occurred during add").build();
    }
	
	/**
	 * updateAd - updates a partner ad.
	 * @param partner
	 * @param ad
	 * @return Response
	 */
	@PUT
	@Path("/campaign/{partner}")
	public Response updateAd (@PathParam("partner") String partner, Ad ad) { 
		logger.info("Updating ad for partner " + partner);
        Ad currentAd = adService.getAdByPartner(partner);
        
        if (currentAd==null) {
        	logger.info("Ad for partner " + partner + " not found");
            return Response.notModified().entity("Ad for partner " + partner + " not found").build();
        }
        
        Ad updatedAd = adService.updateAd(partner, ad);
        
        if (updatedAd != null)
        	return Response.ok().entity(updatedAd).build();
        else
        	return Response.serverError().entity("Server Error Occurred during update").build();        
    }
	
	/**
	 * deleteAd - Deletes a partner ad.
	 * @param partner
	 * @param ad
	 * @return Response
	 */
	@DELETE
	@Path("/campaign/{partner}")
	public Response deleteAd(@PathParam("partner") String partner, Ad ad) {
		logger.info("Fetching & Deleting Ad for partner " + partner);
 
        Ad currentAd = adService.getAdByPartner(partner);
        if (currentAd == null) {
        	logger.info("Unable to delete. Ad for partner " + partner + " not found");
            return Response.notModified().entity("Ad for partner " + partner + " not found").build();
        }
        
        if (adService.deleteAd(partner, ad))
        	return Response.ok().entity(adService.getAdByPartner(partner)).build();
        else
        	return Response.serverError().entity("Server Error Occurred during delete").build();   
    }
	
	/**
	 * deleteAllAdCampaigns - deletes all active ad's
	 * @return Response
	 */
	@DELETE
	@Path("/init")
	public Response deleteAllAdCampaigns() {
		logger.info("Deleting All Ad Campaigns");
 
        if (adService.deleteAllCampaigns())
        	return Response.noContent().build();
        else
        	return Response.serverError().entity("Server Error Occurred").build();  
    }
}
