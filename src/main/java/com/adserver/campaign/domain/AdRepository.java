/**
 * 
 */
package com.adserver.campaign.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Athi
 *
 */
public interface AdRepository extends CrudRepository<Ad, Integer> {
	Ad getByPartnerId (String partner);
	//List<Ad> getAllCampaigns();
}
