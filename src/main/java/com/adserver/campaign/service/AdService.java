/**
 * 
 */
package com.adserver.campaign.service;

import java.util.Collection;

import com.adserver.campaign.domain.Ad;

/**
 * @author Athi
 *
 */
public interface AdService {
	void createCampaign(); 
	
    Collection<Ad> listAllAdCampaigns();

    Ad getAdByPartner(String partner);

    Ad saveAd(Ad ad);
    
    Ad updateAd(String partner, Ad ad);
    
    boolean deleteAd(String partner, Ad ad);
    
    boolean deleteAllCampaigns();

	boolean isAnAdActiveForPartner(String partner);

}
