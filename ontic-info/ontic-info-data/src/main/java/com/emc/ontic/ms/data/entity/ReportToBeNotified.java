package com.emc.ontic.ms.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name="ReportToBeNotified.findAll", query="SELECT r FROM ReportToBeNotified r")
@Table(name = "degradation_report_to_be_notified", schema="ontic")
public class ReportToBeNotified implements Serializable{
	
	private static final long serialVersionUID = 1L;
    	
	@EmbeddedId
	private ReportToBeNotifiedPK reportToBeNotifiedPK;
	
	@Column(name="report_confidence", insertable = false, updatable = false)
	private Double reportConfidence;
	
	@Column(name="report_creation_time", insertable = false, updatable = false)
	private Long reportCreationTime;
	
	@Column(name="report_start_datetime", insertable = false, updatable = false)
	private Long reportStartTimme;
	
	@Column(name="report_end_datetime", insertable = false, updatable = false)
	private Long reportEndTime;
	
	@Column(name="service", insertable = false, updatable = false)
	private String service;
	
	@Column(name="kpi_share", insertable = false, updatable = false)
	private Integer kpiShare;
	
	@Column(name="segmentation_share", insertable = false, updatable = false)
	private Integer segmentationShare;

	@Column(name="subscriber_url", insertable = false, updatable = false)
	private String subscriberUrl;
	
	@Column(name="frequency", insertable = false, updatable = false)
	private Integer frequency;
	
	public ReportToBeNotified(){
		
	}
	
	public ReportToBeNotifiedPK getReportToBeNotifiedPK() {
		return reportToBeNotifiedPK;
	}

	public void setReportToBeNotifiedPK(ReportToBeNotifiedPK reportToBeNotifiedPK) {
		this.reportToBeNotifiedPK = reportToBeNotifiedPK;
	}

	public Double getReportConfidence() {
		return reportConfidence;
	}

	public void setReportConfidence(Double reportConfidence) {
		this.reportConfidence = reportConfidence;
	}

	public Long getReportCreationTime() {
		return reportCreationTime;
	}

	public void setReportCreationTime(Long reportCreationTime) {
		this.reportCreationTime = reportCreationTime;
	}

	public Long getReportStartTimme() {
		return reportStartTimme;
	}

	public void setReportStartTimme(Long reportStartTimme) {
		this.reportStartTimme = reportStartTimme;
	}

	public Long getReportEndTime() {
		return reportEndTime;
	}

	public void setReportEndTime(Long reportEndTime) {
		this.reportEndTime = reportEndTime;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Integer getKpiShare() {
		return kpiShare;
	}

	public void setKpiShare(Integer kpiShare) {
		this.kpiShare = kpiShare;
	}

	public Integer getSegmentationShare() {
		return segmentationShare;
	}

	public void setSegmentationShare(Integer segmentationShare) {
		this.segmentationShare = segmentationShare;
	}

	public String getSubscriberUrl() {
		return subscriberUrl;
	}

	public void setSubscriberUrl(String subscriberUrl) {
		this.subscriberUrl = subscriberUrl;
	}
	
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
}
