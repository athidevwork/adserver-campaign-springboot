/**
 * 
 */
package com.adserver.campaign.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.adserver.campaign.domain.Ad;
import com.adserver.campaign.domain.AdRepository;

/**
 * @author User
 *
 */
public class AdServiceImpl implements AdService {

	Logger logger = Logger.getLogger("AdServiceImpl");
	
	@Autowired
	AdRepository adRepository;
	
	/* (non-Javadoc)
	 * @see com.adserver.campaign.service.AdService#listAllAdCampaigns()
	 */
	@Override
	public List<Ad> listAllAdCampaigns() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.adserver.campaign.service.AdService#getProductByPartner(java.lang.String)
	 */
	@Override
	public Ad getAdByPartner(String partner) {
		return adRepository.getAdByPartner(partner);
	}

	@Override
	public boolean isAnAdActiveForPartner(String partner) {
		Ad currentAd = getAdByPartner(partner);
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime creationDate = currentAd.getCreationTime();
		
		Duration duration = Duration.between(creationDate, currentDate);
		if (duration.getSeconds() > currentAd.getDuration())  
			return false;
		else
			return true;
	}
	
	@Override
	public Status saveAd(String partner, Ad ad) {		
        if (isAnAdActiveForPartner(partner)) {
            logger.info("An active Ad  for partner " + ad.getPartner() + " already exists.");
            return Status.CONFLICT;
        }
        
		if (adRepository.saveAd(ad))
			return Status.CREATED;
		else
			return Status.INTERNAL_SERVER_ERROR;
	}	
}
