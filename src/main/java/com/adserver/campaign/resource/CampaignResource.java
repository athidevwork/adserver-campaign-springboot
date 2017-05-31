package com.adserver.campaign.resource;

import java.util.List;
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
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adserver.campaign.domain.Ad;
import com.adserver.campaign.service.AdService;

@Path("/ad")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CampaignResource {
	
	@Autowired
	private AdService adService;
	
	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Got into CampaignResource";
	}

	Logger logger = Logger.getLogger("CampaignResource");
	
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
        System.out.println("Creating Ad for partner " + ad.getPartner());
  
        Status statusInfo = adService.saveAd(partner, ad);
        if (statusInfo == Status.CREATED)
        	return Response.ok().entity(adService.getAdByPartner(partner)).build();
        else if (statusInfo == Status.CONFLICT)
        	return Response.ok().entity("Active Ad for the partner already exists.").build();
        else
        	return Response.serverError().entity("Server Error Occurred").build();
    }
	
	@PUT
	@Path("/{partner}")
	public Response updateAd (@PathParam("partner") String partner) {  
        Status statusInfo = adService.updateAd(partner, currentAd);
        
        //TODO
        if (statusInfo == Status.CREATED)
        	return Response.ok().entity(adService.getAdByPartner(partner)).build();
        else if (statusInfo == Status.CONFLICT)
        	return Response.noContent().build();
        else
        	return Response.serverError().entity("Server Error Occurred").build();        
    }
	
	@DELETE
	@Path("/{partner}")
	public Response deleteAd(@PathParam("partner") String partner) {
        System.out.println("Fetching & Deleting Ad for partner " + partner);
 
        Status statusInfo = adService.deleteAd(partner);
        //TODO
        if (statusInfo == Status.CREATED)
        	return Response.ok().entity(adService.getAdByPartner(partner)).build();
        else if (statusInfo == Status.CONFLICT)
        	return Response.noContent().build();
        else
        	return Response.serverError().entity("Server Error Occurred").build();   
    }
	
	@DELETE
	@Path("/init")
	public Response deleteAllAdCampaigns() {
        System.out.println("Deleting All Ad Campaigns");
 
        Status statusInfo =  adService.deleteAllCampaigns();
        //TODO
        if (statusInfo == Status.CREATED)
        	return Response.ok().entity(adService.getAdByPartner(partner)).build();
        else if (statusInfo == Status.CONFLICT)
        	return Response.noContent().build();
        else
        	return Response.serverError().entity("Server Error Occurred").build();  
    }
}
