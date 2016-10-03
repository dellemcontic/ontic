package com.emc.ontic.ms.commons.domain;

import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = ReportRequestSerializer.class)
public class ReportRequest implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	public class ReportRequestGroup{
		private String name = null;
		private Integer share = null;
		
		public ReportRequestGroup (String name, Integer share){
			this.name = name;
			this.share = share;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		public String getName(){
			return this.name;
		}
		
		public void setShare(Integer share){
			this.share = share;
		}
		
		public Integer getShare(){
			return this.share;
		}
	}
	
	public class ReportRequestKpiServiceValue{
		private String name = null;
		private Integer share = null;
		
		public ReportRequestKpiServiceValue(String name, Integer share){
			this.name = name;
			this.share = share;
		}
		
		public String getName(){
			return this.name;
		}
		
		public void setName(String name){
			this.name = name;
		}
	
		public Integer getShare(){
			return this.share;
		}
		
		public void setShare(Integer share){
			this.share = share;
		}
	}
	
	private String subscriptionID;
	
	private String subscriberUrl;
	
	private String reportID;
	
	private String timeStamp;
	
	private HashMap<String,String> locations = new HashMap<String,String>();
		
	private Double confidence;
	
	private HashMap<String,ArrayList<ReportRequestKpiServiceValue>> services = new HashMap<String,ArrayList<ReportRequestKpiServiceValue>>();

	private Validity validity;
	
	private HashMap<String,ReportRequestGroup> groups = new HashMap<String,ReportRequestGroup>();
	
	private String sessionID;
	
	private Integer frequency;
	
	public HashMap<String,ReportRequestGroup> getGroups(){
		return this.groups;
	}

	public HashMap<String,String> getLocations(){
		return this.locations;
	}
	
	public HashMap<String,ArrayList<ReportRequestKpiServiceValue>> getServices(){
		return this.services;
	}
	
	public void addGroup (String name, Integer share){
		if (!groups.containsKey(name)){
			groups.put(name, new ReportRequestGroup(name,share));
		}
	}
	
	public void addServiceKpi(String service, String kpi, Integer share){
		if (!this.services.containsKey(service)){
			ArrayList<ReportRequestKpiServiceValue> serviceKpis = new ArrayList<ReportRequestKpiServiceValue>();
			serviceKpis.add(new ReportRequestKpiServiceValue(kpi, share));
			this.services.put(service, serviceKpis);
		}else{
			this.services.get(service).add(new ReportRequestKpiServiceValue(kpi, share));
		}
	}
	
	public void addLocation(String location){
		if (!this.locations.containsKey(location)){
			locations.put(location, location);
		}
	}
	
	public String getSubscriberUrl() {
		return subscriberUrl;
	}
	
	public void setSubscriberUrl(String subscriberUrl) {
		this.subscriberUrl = subscriberUrl;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	
	public String getReportID() {
		return this.reportID;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	public Validity getValidity() {
		return validity;
	}

	public void setValidity(Validity validity) {
		this.validity = validity;
	}
	
	public String getSessionID() {
		return sessionID;
	}
	
	public void setSessionID(String sessionID) {
		if (sessionID.equals("NULL")){
			this.sessionID = null;
		}else{
			this.sessionID = sessionID;
		}
	}
	
	public String getSubscriptionID() {
		return subscriptionID;
	}
	
	public void setSubscriptionID(String subscriptionID) {
		this.subscriptionID = subscriptionID;
	}
	
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
		
	/*@Override
	public String toString() {
		return "ReportRequest [reportID=" + reportID + ", timeStamp="
				+ timeStamp + ", location=" + Arrays.toString(location)
				+ ", confidence=" + confidence + ", service=" + service
				+ ", validity=" + validity + ", groups="
				+ Arrays.toString(groups) + "]";
	}*/
}
