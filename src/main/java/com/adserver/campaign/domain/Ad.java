/**
 * 
 */
package com.adserver.campaign.domain;

import java.time.LocalDateTime;

/**
 * @author Athi
 *
 */
public class Ad {
	private long id;
	private String partner;
	private long duration;
	private LocalDateTime creationTime;
	private String content;
	
	public Ad(long id, String partner, int duration, String content) {
		super();
		this.id = id;
		this.partner = partner;
		this.duration = duration;
		this.content = content;
		this.creationTime = LocalDateTime.now();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Ad [id=" + id + ", partner=" + partner + ", duration=" + duration + ", content=" + content + "]";
	}
}
