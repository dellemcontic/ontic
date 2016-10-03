package ontic.af.action.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "degradation_report", schema="ontic")
@NamedQuery(name="DegradationReport.findAll", query="SELECT a FROM DegradationReport a")
public class DegradationReport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@javax.persistence.Id
	@javax.persistence.Column(name="id")
	private String id;
	
	@Column(name="creation_datetime")
	private Timestamp creationDateTime;
	
	@Column(name="end_datetime")
	private Timestamp endDateTime;
	
	@Column (name="confidence")
	private Double confidence;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Timestamp creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public Timestamp getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
	
	

}
