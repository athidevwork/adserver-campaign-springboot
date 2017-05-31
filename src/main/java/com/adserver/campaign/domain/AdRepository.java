/**
 * 
 */
package com.adserver.campaign.domain;

/**
 * @author Athi
 *
 */
public interface AdRepository {
	List<Ad> listAllAdCampaigns();
	Ad getAdByPartner(String partner);	
	boolean saveAd(Ad ad);
	boolean deleteAd(Ad ad);
	boolean deleteAllAdCampaigns();
}
