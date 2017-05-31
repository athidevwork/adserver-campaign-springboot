/**
 * 
 */
package com.adserver.campaign.domain;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Athi
 *
 */
public class AdRepositoryImpl implements AdRepository {

	private ConcurrentHashMap<String, Ad> campaign = new ConcurrentHashMap<String, Ad>();
	Logger logger = Logger.getLogger("AdRepositoryImpl");
	
	@Override
	public List<Ad> listAllAdCampaigns() {
		if (campaign.size() == 0) {
			//create some test ad's
			logger.info("Adding some sample Ad's");
			campaign.putIdAbsent(new Ad(1, 'Comcast', int 180, 'Comcast Ad Content1'));
			campaign.putIdAbsent(new Ad(1, 'Verizon', int 150, 'Verizon Ad Content1'));
			campaign.putIdAbsent(new Ad(1, 'Time Warner', int 120, 'Time WarnerAd Content1'));
		}
		return campaign.getValues();
	}
	
	@Override
	public Ad getAdByPartner(String partner) {
		return campaign.get(partner);
	}

	@Override
	public boolean saveAd(Ad ad) {
		logger.info("Save Ad : " ad.toString());
		ad.setId(campaign.size() + 1);
		campaign.putIfAbsent(ad.getPartner(), ad);
		return true;
	}

	@Override
	public boolean updateAd(Ad ad) {
		logger.info("Update Ad : " ad.toString());
		Ad currentAd = campaign.getAdByPartner(partner);
		//update currentAd with the new ad passed along 
		currentAd.setContent(ad.getContent());
		currentAd.setDuration(ad.getDuration());
		currentAd.setCreationTime(ad.getCreationTime());
		campaign.put(partner, ad);
		return true;
	}	
	
	@Override
	public boolean deleteAd(Ad ad) {
		logger.info("Delete Ad : " ad.toString());
		return campaign.remove(ad.getPartner());
	}	
	
	@Override
	public boolean deleteAllAdCampaigns() {
		logger.info("Delete All Ad campaigns : " ad.toString());
		campaign.clear();
	}
}
