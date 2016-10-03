package com.emc.ontic.ms.commons.domain;

import java.util.Arrays;

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

	@Override
	public String toString() {
		return "Service [name=" + name + ", kpi=" + Arrays.toString(kpi) + "]";
	}

}
