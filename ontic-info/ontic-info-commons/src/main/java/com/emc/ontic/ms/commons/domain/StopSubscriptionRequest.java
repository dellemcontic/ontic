package com.emc.ontic.ms.commons.domain;

public class StopSubscriptionRequest {
	
	//Mandatory field
	private String subsID;

	public String getSubsID() {
		return subsID;
	}

	public void setSubsID(String subsID) {
		this.subsID = subsID;
	}

	@Override
	public String toString() {
		return "StopSubscriptionRequest [subsID=" + subsID + "]";
	}

}
