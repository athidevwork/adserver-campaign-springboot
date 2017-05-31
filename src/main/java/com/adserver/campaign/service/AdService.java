/**
 * 
 */
package com.adserver.campaign.service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.adserver.campaign.domain.Ad;

/**
 * @author Athi
 *
 */
public interface AdService {
    List<Ad> listAllAdCampaigns();

    Ad getAdByPartner(String partner);

    Status saveAd(String partner, Ad ad);

	boolean isAnAdActiveForPartner(String partner);

}
