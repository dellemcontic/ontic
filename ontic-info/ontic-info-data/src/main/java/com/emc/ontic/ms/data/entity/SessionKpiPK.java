package com.emc.ontic.ms.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the session_kpi database table.
 * 
 */
@Embeddable
public class SessionKpiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="kpi_id", insertable=false, updatable=false)
	private String kpiId;

	@Column(name="session_id", insertable=false, updatable=false)
	private String sessionId;

	public SessionKpiPK() {
		
	}
	
	public String getKpiId() {
		return this.kpiId;
	}
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}
	public String getSessionId() {
		return this.sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SessionKpiPK)) {
			return false;
		}
		SessionKpiPK castOther = (SessionKpiPK)other;
		return 
			this.kpiId.equals(castOther.kpiId)
			&& this.sessionId.equals(castOther.sessionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.kpiId.hashCode();
		hash = hash * prime + this.sessionId.hashCode();
		
		return hash;
	}
}