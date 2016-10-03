package ontic.af.action.db.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable

public class AffectedSegmentation_PK implements Serializable{

   private String segmentation_id;
   private String degradation_report_id;
   
	public String getSegmentation_id() {
		return segmentation_id;
	}
	public void setSegmentation_id(String segmentation_id) {
		this.segmentation_id = segmentation_id;
	}
	public String getDegradation_report_id() {
		return degradation_report_id;
	}
	public void setDegradation_report_id(String degradation_report_id) {
		this.degradation_report_id = degradation_report_id;
	}
	
   
   
	   
   
}
