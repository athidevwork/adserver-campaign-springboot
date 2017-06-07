/**
 * 
 */
package com.adserver.campaign.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adserver.campaign.domain.Ad;
import com.adserver.campaign.domain.AdRepositoryImpl;

/**
 * @author User
 *
 */
@Component
public class AdServiceImpl implements AdService {

	Logger logger = Logger.getLogger("AdServiceImpl");
	
	@Autowired
	AdRepositoryImpl adRepository;
	
	@Override
	public Collection<Ad> listAllAdCampaigns() {
		return adRepository.listAllAdCampaigns();
	}

	@Override
	public Ad getAdByPartner(String partner) {
		return adRepository.getAdByPartner(partner);
	}

	@Override
	public boolean isAnAdActiveForPartner(String partner) {
		Ad currentAd = getAdByPartner(partner);
		if (currentAd != null) {
			LocalDateTime currentDate = LocalDateTime.now();
	
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime creationDate = LocalDateTime.parse(currentAd.getCreationTime(), formatter);
			
			Duration duration = Duration.between(creationDate, currentDate);
			if (duration.getSeconds() > currentAd.getDuration())  
				return false;
			else
				return true;
		}
		return false;
	}
	
	@Override
	public Ad saveAd(Ad ad) {
        Ad createdAd = adRepository.saveAd(ad);
		if (createdAd != null)
			return createdAd;
		else
			return null;
	}	
	
	@Override
	public Ad updateAd(String partner, Ad ad) {        
        Ad updatedAd = adRepository.updateAd(partner, ad);
		if (updatedAd != null)
			return updatedAd;
		else
			return null;        
	}
	
	@Override
	public Status deleteAd(String partner, Ad ad) {        
		if (adRepository.deleteAd(partner, ad))
			return Status.NO_CONTENT;
		else
			return Status.INTERNAL_SERVER_ERROR;  
	}
	
	@Override
	public Status deleteAllCampaigns() {
		if (adRepository.deleteAllAdCampaigns())
			return Status.NO_CONTENT;
		else
			return Status.INTERNAL_SERVER_ERROR;  
	}
}
