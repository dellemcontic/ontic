package com.emc.ontic.ms.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the session_kpi database table.
 * 
 */
@Embeddable
public class SubscriptionKpiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="kpi_id", insertable=false, updatable=false)
	private String kpiId;

	@Column(name="subscription_id", insertable=false, updatable=false)
	private String subscriptionId;

	public SubscriptionKpiPK() {
		
	}
	
	public String getKpiId() {
		return this.kpiId;
	}
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SubscriptionKpiPK)) {
			return false;
		}
		SubscriptionKpiPK castOther = (SubscriptionKpiPK)other;
		return 
			this.kpiId.equals(castOther.kpiId)
			&& this.subscriptionId.equals(castOther.subscriptionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.kpiId.hashCode();
		hash = hash * prime + this.subscriptionId.hashCode();
		
		return hash;
	}
}