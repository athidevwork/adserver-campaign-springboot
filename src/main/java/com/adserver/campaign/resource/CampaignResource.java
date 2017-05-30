package com.adserver.campaign.resource;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adserver.campaign.domain.Ad;
import com.adserver.campaign.service.AdService;

@Path("/ad")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CampaignResource {
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private AdService adService;
	
	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Got into CampaignController";
	}

	Logger logger = Logger.getLogger("CampaignController");
	
	/** 
	 * listAllAdCampaigns - List all the campaigns.
	 * @return
	 */
	@GET
	@Path("/campaigns")
	public Response listAllAdCampaigns() {
		List<Ad> adList = adService.listAllAdCampaigns();
		if(adList.isEmpty()){
			return Response.noContent().build();
		}
		return Response.ok().entity(adList).build();
	}

	@GET
	@Path("/{partner}")
	public Response getAd(@PathParam("partner") String partner) {
        System.out.println("Fetching User with id " + partner);
        Ad ad = adService.getAdByPartner(partner);
        if (ad == null) {
            System.out.println("Ad for partner " + partner + " not found");
            return Response.noContent().build();
        }
        return Response.ok().entity(ad).build();
    }
	
	@POST
	@Path("/{partner}")
	public Response createAd (@PathParam("partner") String partner, Ad ad) {
        System.out.println("Creating Ad for partner " + ad.getPartnerId());
 
        if (adService.doesAdExist(ad) {
            logger.info("An Ad  with partner " + ad.getPartnerId() + " already exists.");
            return Response.ok().entity("Ad for the partner already exists.").build();
        }
 
        /*adService.saveAd(ad);
        return Response.ok().build();*/
        return adService.saveAd(ad);
    }
	
	@PUT
	@Path("/{partner}")
	public Response updateAd (@PathParam("partner") String partner) {
		
        System.out.println("Updating Ad for partner" + partner);
         
        Ad currentAd = adService.findById(partner);
         
        if (currentAd==null) {
            System.out.println("Ad for partner " + partner + " not found");
            return Response.noContent().build();
        }
         
        /*adService.updateAd(currentAd);
        return Response.ok().build();*/
        return adService.updateAd(currentAd);
    }
	
	@DELETE
	@Path("/{partner}")
	public Response deleteAd(@PathParam("partner") String partner) {
        System.out.println("Fetching & Deleting Ad for partner " + partner);
 
        Ad ad = adService.findById(partner);
        if (ad == null) {
            System.out.println("Unable to delete. Ad for partner " + partner + " not found");
            return Response.noContent().build();
        }
 
        return adService.deleteUserById(id);
        //return Response.ok().build();
    }
	
	@DELETE
	@Path("/deleteAll")
	public Response deleteAllAdCampaigns() {
        System.out.println("Deleting All Ad Campaigns");
 
        return adService.deleteAllCampaigns();
        //return Response.noContent().build();
    }
 
	/**
	 * {

"partner_id": "unique_string_representing_partner',

"duration": "int_representing_campaign_duration_in_seconds_from_now"

"ad_content": "string_of_content_to_display_as_ad"

}
	 */
}
