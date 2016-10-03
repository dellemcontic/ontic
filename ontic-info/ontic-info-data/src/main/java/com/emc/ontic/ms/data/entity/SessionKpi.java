package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the session_kpi database table.
 * 
 */
@Entity
@NamedQuery(name="SessionKpi.findAll", query="SELECT s FROM SessionKpi s")
@Table(name = "session_kpi", schema="ontic")
public class SessionKpi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SessionKpiPK id;

	@Column(name="kpi_id", insertable=false, updatable=false)
	private String kpiId;
	
	@Column(name="session_id", insertable=false, updatable=false)
	private String sessionId;

	public SessionKpi() {
		
	}

	public SessionKpiPK getId() {
		return id;
	}

	public void setId(SessionKpiPK id) {
		this.id = id;
	}

	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}