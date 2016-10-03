package ontic.af.action.db.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable

public class AffectedLocation_PK implements Serializable{

   private String location_id;
   private String degradation_report_id;
   
	public String getLocation_id() {
		return location_id;
	}
	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}
	public String getDegradation_report_id() {
		return degradation_report_id;
	}
	public void setDegradation_report_id(String degradation_report_id) {
		this.degradation_report_id = degradation_report_id;
	}
	
	   
   
}
