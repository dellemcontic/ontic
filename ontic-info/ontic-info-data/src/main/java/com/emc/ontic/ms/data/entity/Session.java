package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the session database table.
 * 
 */
@Entity
@NamedQuery(name="Session.findAll", query="SELECT s FROM Session s")
@Table(name = "session", schema="ontic")
public class Session implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "degradation_report_id")
	private String degradationReportId;
	
	@Column(name = "subscription_id")
	private String subscriptionId;
	
	@Column(name="last_notification")
	private Timestamp lastNotification;
	
	@Column(name = "frequency")
	private Integer frequency;

	public Session() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDegradationReportId() {
		return degradationReportId;
	}

	public void setDegradationReportId(String degradationReportId) {
		this.degradationReportId = degradationReportId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Timestamp getLastNotification() {
		return lastNotification;
	}

	public void setLastNotification(Timestamp lastNotification) {
		this.lastNotification = lastNotification;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

}