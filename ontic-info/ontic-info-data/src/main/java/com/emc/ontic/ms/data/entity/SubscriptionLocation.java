package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the subscription database table.
 * 
 */
@Entity
@NamedQuery(name="SubscriptionLocation.findAll", query="SELECT s FROM SubscriptionLocation s")
@Table(name = "subscription_location", schema="ontic")
public class SubscriptionLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SubscriptionLocationPK subscriptionLocationPK;

	@Column(name="location_id", insertable=false, updatable=false)
	private String locationId;
	
	@Column(name="subscription_id", insertable=false, updatable=false)
	private String subscriptionId;

	public SubscriptionLocation() {
		
	}

	public SubscriptionLocationPK getSubscriptionLocationPK() {
		return subscriptionLocationPK;
	}

	public void setSubscriptionLocationPK(
			SubscriptionLocationPK subscriptionLocationPK) {
		this.subscriptionLocationPK = subscriptionLocationPK;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

}