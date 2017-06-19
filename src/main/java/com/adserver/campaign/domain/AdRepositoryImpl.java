/**
 * 
 */
package com.adserver.campaign.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

/**
 * @author Athi
 *
 */
@Component
public class AdRepositoryImpl implements AdRepository {

	private ConcurrentHashMap<String, Ad> campaign = new ConcurrentHashMap<String, Ad>();
	Logger logger = Logger.getLogger("AdRepositoryImpl");
	
	@Override
	public void createCampaign() {
		if (campaign.size() == 0) {
			logger.info("Adding some sample Ad's");
			campaign.putIfAbsent("Comcast", new Ad(campaign.size() + 1, "Comcast", 180, "Comcast First Ad Content"));
			campaign.putIfAbsent("Verizon", new Ad(campaign.size() + 1, "Verizon", 150, "Verizon First Ad Content"));
			campaign.putIfAbsent("Time Warner", new Ad(campaign.size() + 1, "Time Warner", 120, "Time Warner First Ad Content"));
		}		
	}
	
	@Override
	public Collection<Ad> listAllAdCampaigns() {
		return campaign.values();
	}
	
	@Override
	public Ad getAdByPartner(String partner) {
		return campaign.get(partner);
	}

	@Override
	public Ad saveAd(Ad ad) {
		long duration = Long.valueOf(ad.getDuration());
		long id = Long.valueOf(campaign.size() + 1);
		Ad newAd = new Ad(id, ad.getPartner(), duration, ad.getContent());
		logger.info("Saved Ad : " + newAd.toString());		
		campaign.putIfAbsent(ad.getPartner(), newAd);
		return newAd;
	}

	@Override
	public Ad updateAd(String partner, Ad ad) {
		Ad currentAd = getAdByPartner(partner);
		//update currentAd with the new ad passed along 		
		currentAd.setContent(ad.getContent());
		currentAd.setDuration(ad.getDuration());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		currentAd.setCreationTime(LocalDateTime.now().format(formatter));
		logger.info("Updated Ad : " + ad.toString());		
		campaign.put(partner, currentAd);
		return currentAd;
	}	
	
	@Override
	public boolean deleteAd(String partner, Ad ad) {
		logger.info("Deleting Ad : " + ad.toString());
		campaign.remove(ad.getPartner());
		if (campaign.containsKey(ad.getPartner()))
			return false;
		else
			return true;
	}	
	
	@Override
	public boolean deleteAllAdCampaigns() {
		logger.info("Deleting All Ad campaigns");
		campaign.clear();
		if (campaign.isEmpty())
			return true;
		else
			return false;
	}
}
