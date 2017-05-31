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
	
	@Override
	public Ad getAdByPartner(String partner) {
		return campaign.get(partner);
	}

	@Override
	public boolean saveAd(Ad ad) {
		ad.setId(campaign.size() + 1);
		campaign.putIfAbsent(ad.getPartner(), ad);
		return true;
	}

}
