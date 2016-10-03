package com.emc.pgf.ms.commons.domain;

public class ModifyDeliveryConditionsRequest {
	
	//Mandatory field
	private String sessionID;
	
	//Optional field
	private Service service;
	
	//Optional field
	private String[] location;
	
	//Optional field
	private Long frequency;

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
