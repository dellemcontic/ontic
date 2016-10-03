package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the subscription database table.
 * 
 */
@Entity
@NamedQuery(name="SessionLocation.findAll", query="SELECT s FROM SessionLocation s")
@Table(name = "session_location", schema="ontic")
public class SessionLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SessionLocationPK sessionLocationPK;

	@Column(name="location_id", insertable=false, updatable=false)
	private String locationId;
	
	@Column(name="session_id", insertable=false, updatable=false)
	private String sessionId;

	public SessionLocation() {
		
	}

	public SessionLocationPK getSessionLocationPK() {
		return sessionLocationPK;
	}

	public void setSessionLocationPK(SessionLocationPK sessionLocationPK) {
		this.sessionLocationPK = sessionLocationPK;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}