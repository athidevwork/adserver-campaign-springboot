/**
 * 
 */
package com.adserver.campaign.service;

import java.util.List;

import com.adserver.campaign.domain.Ad;

/**
 * @author Athi
 *
 */
public interface AdService {
    List<Ad> listAllAdCampaigns();

    Ad getAdByPartner(String partner);

    Ad saveAd(Ad ad);
}
