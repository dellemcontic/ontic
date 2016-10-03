package com.emc.ontic.ms.commons.domain;

public class StopDeliveryConditionsRequest {
	
	//Mandatory field
	private String sessionID;

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	@Override
	public String toString() {
		return "StopDeliveryConditionsRequest [sessionID=" + sessionID + "]";
	}

}
