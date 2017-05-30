/**
 * 
 */
package com.adserver.campaign.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Athi
 *
 */
@Entity
public class Ad {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String partnerId;
	private int duration;
	private String content;
	
	public Ad(long id, String partnerId, int duration, String content) {
		super();
		this.id = id;
		this.partnerId = partnerId;
		this.duration = duration;
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Ad [id=" + id + ", partnerId=" + partnerId + ", duration=" + duration + ", content=" + content + "]";
	}
}
