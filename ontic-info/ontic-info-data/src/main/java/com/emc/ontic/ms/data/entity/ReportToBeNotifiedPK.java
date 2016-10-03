package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReportToBeNotifiedPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="report_id", insertable = false, updatable = false)
	private String reportId;

	@Column(name="kpi_id", insertable = false, updatable = false)
	private String kpiId;
	
	@Column(name="location_id", insertable = false, updatable = false)
	private String locationId;
	
	@Column(name="segmentation_id", insertable = false, updatable = false)
	private String segmentationId;

	@Column(name="subscriber_id", insertable = false, updatable = false)
	private Long subscriberId;
	
	@Column(name="subscription_id", insertable = false, updatable = false)
	private String subscriptionId;
	
	@Column(name="session_id", insertable = false, updatable = false)
	private String sessionId;
	
	public ReportToBeNotifiedPK() {
		
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getSegmentationId() {
		return segmentationId;
	}

	public void setSegmentationId(String segmentationId) {
		this.segmentationId = segmentationId;
	}

	public Long getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(Long subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReportToBeNotifiedPK)) {
			return false;
		}
		ReportToBeNotifiedPK castOther = (ReportToBeNotifiedPK)other;
		return 
			this.kpiId.equals(castOther.kpiId)
			&& this.reportId.equals(castOther.reportId)
			&& this.locationId.equals(castOther.locationId)
			&& this.segmentationId.equals(castOther.segmentationId)
			&& this.subscriberId.equals(castOther.subscriberId)
			&& this.subscriptionId.equals(castOther.subscriptionId)
			&& this.sessionId.equals(castOther.sessionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.kpiId.hashCode();
		hash = hash * prime + this.reportId.hashCode();
		hash = hash * prime + this.locationId.hashCode();
		hash = hash * prime + this.segmentationId.hashCode();
		hash = hash * prime + this.subscriberId.hashCode();
		hash = hash * prime + this.subscriptionId.hashCode();
		hash = hash * prime + this.sessionId.hashCode();
		
		return hash;
	}

}
