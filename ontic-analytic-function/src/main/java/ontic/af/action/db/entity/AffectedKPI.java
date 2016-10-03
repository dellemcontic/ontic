package ontic.af.action.db.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "affected_kpi", schema="ontic")
@NamedQuery(name="AffectedKPI.findAll", query="SELECT a FROM AffectedKPI a")
public class AffectedKPI implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AffectedKPI_PK affectedKPI_PK;
	
	@Column(name="share")
	private int share;

	public AffectedKPI_PK getAffectedKPI_PK() {
		return affectedKPI_PK;
	}

	public void setAffectedKPI_PK(AffectedKPI_PK affectedKPI_PK) {
		this.affectedKPI_PK = affectedKPI_PK;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	
	
	
	

}
