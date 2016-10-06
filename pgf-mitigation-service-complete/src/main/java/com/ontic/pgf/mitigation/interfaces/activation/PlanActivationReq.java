package com.ontic.pgf.mitigation.interfaces.activation;

import java.util.List;

import com.ontic.pgf.mitigation.interfaces.*;

public class PlanActivationReq {
	
	private long  timestamp;
	private List<Plan> plans;

	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public List<Plan> getPlans() {
		return plans;
	}
	

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	
	

}
