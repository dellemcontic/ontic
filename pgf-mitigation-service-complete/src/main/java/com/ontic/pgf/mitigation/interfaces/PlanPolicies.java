package com.ontic.pgf.mitigation.interfaces;

import java.util.List;

public class PlanPolicies {
	private String plan_ref;
	private List<Policy> policies;
	public String getPlan_ref() {
		return plan_ref;
	}
	public void setPlan_ref(String plan_ref) {
		this.plan_ref = plan_ref;
	}
	public List<Policy> getPolicies() {
		return policies;
	}
	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
	}
	
	
}