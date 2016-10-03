package ontic.af.action.db.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "affected_segmentation", schema="ontic")
@NamedQuery(name="AffectedSegmentation.findAll", query="SELECT a FROM AffectedSegmentation a")
public class AffectedSegmentation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AffectedSegmentation_PK affectedSegmentation_PK;
	
	@Column(name="share")
	private int share;

	

	public AffectedSegmentation_PK getAffectedSegmentation_PK() {
		return affectedSegmentation_PK;
	}

	public void setAffectedSegmentation_PK(AffectedSegmentation_PK affectedSegmentation_PK) {
		this.affectedSegmentation_PK = affectedSegmentation_PK;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	
	
	
	

}
