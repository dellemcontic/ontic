package com.emc.ontic.ms.commons.domain;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.ontic.ms.commons.domain.ReportRequest.ReportRequestGroup;
import com.emc.ontic.ms.commons.domain.ReportRequest.ReportRequestKpiServiceValue;

public class ReportRequestSerializer extends JsonSerializer<ReportRequest>{
	
	private static final Logger logger = LoggerFactory.getLogger(ReportRequestSerializer.class);
	
	@Override
	public void serialize(ReportRequest report, JsonGenerator jgen, SerializerProvider arg2) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
        jgen.writeStringField("reportID", report.getReportID());
        jgen.writeNumberField("confidence", report.getConfidence());
        //Validity write
        jgen.writeFieldName("validity");
        jgen.writeStartObject();
        //jgen.writeStringField("start", report.getValidity().getStart());
        jgen.writeNumberField("start", new Long(report.getValidity().getStart()).longValue());
        //jgen.writeStringField("end", report.getValidity().getEnd());
        jgen.writeNumberField("end", new Long(report.getValidity().getEnd()).longValue());
        jgen.writeEndObject();
        //Service Write
        
        jgen.writeFieldName("service");
        jgen.writeStartObject();
        for (String service : report.getServices().keySet()){
        	jgen.writeStringField("name", service);
        	jgen.writeFieldName("kpi");
        	jgen.writeStartArray();
        	ArrayList<ReportRequestKpiServiceValue> kpis = report.getServices().get(service);
        	for (int i=0; i<kpis.size(); i++){
        		jgen.writeStartObject();
        		jgen.writeStringField("name",kpis.get(i).getName());
        		jgen.writeNumberField("value",kpis.get(i).getShare());
        		jgen.writeEndObject();
        	}
        	jgen.writeEndArray();
        }
        jgen.writeEndObject();
        //jgen.writeStringField("timestamp", report.getTimeStamp());
        jgen.writeNumberField("timestamp", new Long(report.getTimeStamp()).longValue());
        jgen.writeFieldName("groups");
        jgen.writeStartArray();
        HashMap<String,ReportRequestGroup> groups = report.getGroups();
        for (String group : groups.keySet()){
    		jgen.writeStartObject();
    		jgen.writeStringField("name", groups.get(group).getName());
    		jgen.writeNumberField("share", groups.get(group).getShare());
    		jgen.writeEndObject();
    	}
        jgen.writeEndArray();
        jgen.writeFieldName("location");
        jgen.writeStartArray();
        for (String location : report.getLocations().keySet()){
    		jgen.writeString(location);
    	}
        jgen.writeEndArray();
        jgen.writeEndObject();
	}
}