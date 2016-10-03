package com.emc.ontic.ms.commons.domain;

import java.util.Date;

public class ReportToBeNotified {
	
	private String reportId;
	
	private Double reportConfidence;
	
	private Date reportCreationTime;
	
	private Date reportStartTimme;
	
	private Date reportEndTime;
	
	private String service;
	
	private String kpiId;
	
	private Integer kpiShare;
	
	private String locationId;
	
	private String segmentationId;
	
	private Integer segmentationShare;
	
	private Long subscriberId;
	
	private String subscriberUrl;
	
	private String subscriptionId;
	
	private String sessionId;

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public Double getReportConfidence() {
		return reportConfidence;
	}

	public void setReportConfidence(Double reportConfidence) {
		this.reportConfidence = reportConfidence;
	}

	public Date getReportCreationTime() {
		return reportCreationTime;
	}

	public void setReportCreationTime(Date reportCreationTime) {
		this.reportCreationTime = reportCreationTime;
	}

	public Date getReportStartTimme() {
		return reportStartTimme;
	}

	public void setReportStartTimme(Date reportStartTimme) {
		this.reportStartTimme = reportStartTimme;
	}

	public Date getReportEndTime() {
		return reportEndTime;
	}

	public void setReportEndTime(Date reportEndTime) {
		this.reportEndTime = reportEndTime;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public Integer getKpiShare() {
		return kpiShare;
	}

	public void setKpiShare(Integer kpiShare) {
		this.kpiShare = kpiShare;
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

	public Integer getSegmentationShare() {
		return segmentationShare;
	}

	public void setSegmentationShare(Integer segmentationShare) {
		this.segmentationShare = segmentationShare;
	}

	public Long getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(Long subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getSubscriberUrl() {
		return subscriberUrl;
	}

	public void setSubscriberUrl(String subscriberUrl) {
		this.subscriberUrl = subscriberUrl;
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

}
