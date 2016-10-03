package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the session_location database table.
 * 
 */
@Embeddable
public class SubscriptionLocationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="subscription_id", insertable=false, updatable=false)
	private String subscriptionId;

	@Column(name="location_id", insertable=false, updatable=false)
	private String locationId;

	public SubscriptionLocationPK() {
		
	}
	
	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getLocationId() {
		return this.locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SubscriptionLocationPK)) {
			return false;
		}
		SubscriptionLocationPK castOther = (SubscriptionLocationPK)other;
		return 
			this.subscriptionId.equals(castOther.subscriptionId)
			&& this.locationId.equals(castOther.locationId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.subscriptionId.hashCode();
		hash = hash * prime + this.locationId.hashCode();
		
		return hash;
	}
}