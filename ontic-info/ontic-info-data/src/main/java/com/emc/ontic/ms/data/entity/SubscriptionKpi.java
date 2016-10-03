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
@NamedQuery(name="SubscriptionKpi.findAll", query="SELECT s FROM SubscriptionKpi s")
@Table(name = "subscription_kpi", schema="ontic")
public class SubscriptionKpi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SubscriptionKpiPK subscriptionKpiPK;

	@Column(name="kpi_id", insertable=false, updatable=false)
	private String kpiId;
	
	@Column(name="subscription_id", insertable=false, updatable=false)
	private String subscriptionId;

	public SubscriptionKpi() {
		
	}

	public SubscriptionKpiPK getSubscriptionKpiPK() {
		return subscriptionKpiPK;
	}

	public void setSubscriptionKpiPK(SubscriptionKpiPK subscriptionKpiPK) {
		this.subscriptionKpiPK = subscriptionKpiPK;
	}

	public String getKpiId() {
		return kpiId;
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

}