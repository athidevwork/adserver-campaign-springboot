/**
 * 
 */
package com.adserver.campaign.service;

import java.util.List;

import com.adserver.campaign.domain.Ad;

/**
 * @author User
 *
 */
public class AdServiceImpl implements AdService {

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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.adserver.campaign.service.AdService#saveAd(com.adserver.campaign.domain.Ad)
	 */
	/**
	 * saveAd - Save an Ad for a partner
	 */
	@Override
	public Ad saveAd(Ad ad) {

        /*policy.setPolicyNumberId(policies.size() + 1);
        policies.put(policy.getPolicyNo(), policy);*/

        return ad;
	}

}
