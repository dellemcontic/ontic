package com.emc.pgf.ms.commons.domain;

import java.util.Arrays;

public class PgfRequest {
	
	private String reportID;
	
	private String timeStamp;
	
	private String[] location;
	
	private Integer confidence;
	
	private Service service;
	
	private Validity validity;
	
	private Group[] groups;

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}

	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Validity getValidity() {
		return validity;
	}

	public void setValidity(Validity validity) {
		this.validity = validity;
	}

	public Group[] getGroups() {
		return groups;
	}

	public void setGroups(Group[] groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "PgfRequest [reportID=" + reportID + ", timeStamp=" + timeStamp
				+ ", location=" + Arrays.toString(location) + ", confidence="
				+ confidence + ", service=" + service + ", validity="
				+ validity + ", groups=" + Arrays.toString(groups) + "]";
	}

}
