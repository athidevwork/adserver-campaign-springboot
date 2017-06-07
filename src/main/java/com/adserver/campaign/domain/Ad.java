/**
 * 
 */
package com.adserver.campaign.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Athi
 *
 */
@XmlRootElement
public class Ad {
	private long id;
	private String partner;
	private long duration;
	private String content;
	@Autowired(required=false)
	private String creationTime;
	@Autowired(required=false)
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public Ad() {
		super();
	}
	public Ad(String partner, long duration, String content) {
		super();
		this.id = 0;
		this.partner = partner;
		this.duration = duration;
		this.content = content;
		this.creationTime = LocalDateTime.now().format(formatter);
	}
	public Ad(long id, String partner, long duration, String content) {
		super();
		this.id = id;
		this.partner = partner;
		this.duration = duration;
		this.content = content;
		this.creationTime = LocalDateTime.now().format(formatter);
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
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
		return "Ad [id=" + id + ", " + (partner != null ? "partner=" + partner + ", " : "") + "duration=" + duration
				+ ", " + (creationTime != null ? "creationTime=" + creationTime + ", " : "")
				+ (content != null ? "content=" + content : "") + "]";
	}
}
