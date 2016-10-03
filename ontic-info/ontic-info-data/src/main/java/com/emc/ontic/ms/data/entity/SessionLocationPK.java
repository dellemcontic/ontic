package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the session_location database table.
 * 
 */
@Embeddable
public class SessionLocationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="session_id", insertable=false, updatable=false)
	private String sessionId;

	@Column(name="location_id", insertable=false, updatable=false)
	private String locationId;

	public SessionLocationPK() {
		
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getLocationId() {
		return this.locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SessionLocationPK)) {
			return false;
		}
		SessionLocationPK castOther = (SessionLocationPK)other;
		return 
			this.sessionId.equals(castOther.sessionId)
			&& this.locationId.equals(castOther.locationId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sessionId.hashCode();
		hash = hash * prime + this.locationId.hashCode();
		
		return hash;
	}
}