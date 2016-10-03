package com.emc.ontic.ms.commons.domain;

import java.util.Arrays;

public class ModifySubscriptionRequest {
	
	//Mandatory field
	private String subsID; 
	
	//Optional field
	private String resourceURI;
	
	//Optional field
	private Integer frequency;
	
	//Optional field
	private String[] location;
	
	//Optional field
	private String service;
	
	//Optional field
	private String[] kpi;

	public String getSubsID() {
		return subsID;
	}

	public void setSubsID(String subsID) {
		this.subsID = subsID;
	}

	public String getResourceURI() {
		return resourceURI;
	}

	public void setResourceURI(String resourceURI) {
		this.resourceURI = resourceURI;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String[] getKpi() {
		return kpi;
	}

	public void setKpi(String[] kpi) {
		this.kpi = kpi;
	}

	@Override
	public String toString() {
		return "ModifySubscriptionRequest [subsID=" + subsID + ", resourceURI="
				+ resourceURI + ", frequency=" + frequency + ", location="
				+ Arrays.toString(location) + ", service=" + service + ", kpi="
				+ Arrays.toString(kpi) + "]";
	} 

}
