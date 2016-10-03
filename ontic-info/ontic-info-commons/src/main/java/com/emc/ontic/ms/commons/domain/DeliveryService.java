package com.emc.ontic.ms.commons.domain;

import java.util.Arrays;

public class DeliveryService {
	
	//Optional field
	private String name;
	
	//Optional field
	private String[] kpi;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getKpi() {
		return kpi;
	}

	public void setKpi(String[] kpi) {
		this.kpi = kpi;
	}

	@Override
	public String toString() {
		return "DeliveryService [name=" + name + ", kpi="
				+ Arrays.toString(kpi) + "]";
	}

}
