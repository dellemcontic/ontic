package com.ontic.pgf.mitigation.interfaces;

import java.util.List;


public class Plan
{
	private String plan_id;
	private PlanPolicies plan;
	private TimingContraints timing_constraints ;
	private List<String> locations;
	
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public PlanPolicies getPlan() {
		return plan;
	}
	public void setPlan(PlanPolicies plan) {
		this.plan = plan;
	}
	
	
	
	
	public TimingContraints getTiming_constraints() {
		return timing_constraints;
	}
	public void setTiming_constraints(TimingContraints timing_constraints) {
		this.timing_constraints = timing_constraints;
	}
	public List<String> getLocations() {
		return locations;
	}
	public void setLocations(List<String> locations) {
		this.locations = locations;
	}
	
	
	
	
	
	
}
