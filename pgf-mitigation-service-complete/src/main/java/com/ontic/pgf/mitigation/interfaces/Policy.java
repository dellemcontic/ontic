package com.ontic.pgf.mitigation.interfaces;

import java.util.List;

public class Policy {
	
	private String group;
	private String policy;
	private Parameter parameters;
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public Parameter getParameters() {
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	
	
	
	
	
}