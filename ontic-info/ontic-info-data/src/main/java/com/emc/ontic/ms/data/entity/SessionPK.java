package com.emc.ontic.ms.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the session database table.
 * 
 */
@Embeddable
public class SessionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="degradation_report_id", insertable=false, updatable=false)
	private String degradationReportId;

	@Column(name="subscriber_id", insertable=false, updatable=false)
	private Integer subscriberId;

	public SessionPK() {
	}
	public String getDegradationReportId() {
		return this.degradationReportId;
	}
	public void setDegradationReportId(String degradationReportId) {
		this.degradationReportId = degradationReportId;
	}
	public Integer getSubscriberId() {
		return this.subscriberId;
	}
	public void setSubscriberId(Integer subscriberId) {
		this.subscriberId = subscriberId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SessionPK)) {
			return false;
		}
		SessionPK castOther = (SessionPK)other;
		return 
			this.degradationReportId.equals(castOther.degradationReportId)
			&& this.subscriberId.equals(castOther.subscriberId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.degradationReportId.hashCode();
		hash = hash * prime + this.subscriberId.hashCode();
		
		return hash;
	}
}