package com.emc.ontic.ms.commons.domain;

import java.util.Arrays;

public class ModifyDeliveryConditionsRequest {
	
	//Mandatory field
	private String sessionID;
	
	//Optional field
	private DeliveryService service;
	
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

	public DeliveryService getService() {
		return service;
	}

	public void setService(DeliveryService service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return "ModifyDeliveryConditionsRequest [sessionID=" + sessionID
				+ ", service=" + service + ", location="
				+ Arrays.toString(location) + ", frequency=" + frequency + "]";
	}

}
