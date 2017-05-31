/**
 * 
 */
package com.adserver.campaign.domain;

/**
 * @author Athi
 *
 */
public interface AdRepository {
	Ad getAdByPartner (String partner);
	//List<Ad> getAllCampaigns();
	boolean saveAd(Ad ad);
}
