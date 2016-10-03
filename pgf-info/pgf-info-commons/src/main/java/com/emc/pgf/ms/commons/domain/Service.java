package com.emc.pgf.ms.commons.domain;

public class Service {
	
	//Optional field
	private String name;
	
	//Optional field
	private Kpi[] kpi;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Kpi[] getKpi() {
		return kpi;
	}

	public void setKpi(Kpi[] kpi) {
		this.kpi = kpi;
	}

}
