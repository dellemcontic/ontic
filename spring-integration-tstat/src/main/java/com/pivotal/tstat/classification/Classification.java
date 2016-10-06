package com.pivotal.tstat.classification;

public class Classification {
	private String flow;
	private String classification;
	
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getPrediction() {
		return classification;
	}
	public void setPrediction(String prediction) {
		this.classification = prediction;
	}
	@Override
	public String toString() {
		return "Prediction [flow=" + flow + ", classification=" + classification + "]";
	}
	
	

}
