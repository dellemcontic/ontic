package ontic.af.action.db.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "affected_location", schema="ontic")
@NamedQuery(name="AffectedLocation.findAll", query="SELECT a FROM AffectedLocation a")
public class AffectedLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AffectedLocation_PK affectedLocation_PK;

	public AffectedLocation_PK getAffectedLocation_PK() {
		return affectedLocation_PK;
	}

	public void setAffectedLocation_PK(AffectedLocation_PK affectedLocation_PK) {
		this.affectedLocation_PK = affectedLocation_PK;
	}
	
	

	
	
	
	

}
