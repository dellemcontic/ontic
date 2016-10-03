package ontic.af.action.db.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable

public class AffectedKPI_PK implements Serializable{

   private String kpi_id;
   private String degradation_report_id;
	public String getKpi_id() {
		return kpi_id;
	}
	public void setKpi_id(String kpi_id) {
		this.kpi_id = kpi_id;
	}
	public String getDegradation_report_id() {
		return degradation_report_id;
	}
	public void setDegradation_report_id(String degradation_report_id) {
		this.degradation_report_id = degradation_report_id;
	}
	   
   
}
