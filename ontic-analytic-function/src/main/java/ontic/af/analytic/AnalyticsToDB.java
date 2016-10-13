package ontic.af.analytic;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import ontic.af.action.db.entity.AffectedKPI;
import ontic.af.action.db.entity.AffectedKPI_PK;
import ontic.af.action.db.entity.AffectedLocation;
import ontic.af.action.db.entity.AffectedLocation_PK;
import ontic.af.action.db.entity.AffectedSegmentation;
import ontic.af.action.db.entity.AffectedSegmentation_PK;
import ontic.af.action.db.entity.DegradationReport;
import ontic.af.action.db.entity.Location;
import ontic.af.action.db.repository.AffectedKPIRepository;
import ontic.af.action.db.repository.AffectedLocationRepository;
import ontic.af.action.db.repository.AffectedSegmentationRepository;
import ontic.af.action.db.repository.DegradationReportRepository;
import ontic.af.action.db.repository.LocationRepository;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement

public class AnalyticsToDB implements AnalyticFunctionIF {
	
	private static Hashtable<String, Timestamp> analysis = new Hashtable<String, Timestamp>();
	@Autowired
	DegradationReportRepository degradationReportRepo;
	
	@Autowired
	AffectedKPIRepository affectedKPIRepo;
	
	@Autowired
	AffectedLocationRepository affectedLocationRepo;
	
	@Autowired
	AffectedSegmentationRepository affectedSegmentationRepo;
	
	@Autowired
	LocationRepository locationRepo;
	

	@Override
	public void analyze(Message<byte[]> message) { 
	 
		MessageHeaders headers= message.getHeaders();
	   long classification_time= (long) headers.get("CLASSIFICATION_TIME");
	   String classification_input= new String(message.getPayload());
				
	   String ip= getClassificationIP(classification_input);
	   String location=getLocationByIP(ip);
		
		if(getFlows(classification_input) >= 1)
		{
			if(isGoodClassification(classification_input))
			{
			
				setGoodAnalysis(ip, new Timestamp(classification_time));
				
				
			}else
			{
								
				Timestamp bad_time= setBadAnalysis(ip, new Timestamp(classification_time));
				System.out.println(bad_time);
				Calendar old_bad_time = Calendar.getInstance();
				old_bad_time.setTimeInMillis(bad_time.getTime());
				old_bad_time.add(Calendar.MINUTE,2);
				
				
				if(classification_time  >= old_bad_time.getTimeInMillis() )
				{
					Calendar report_time = Calendar.getInstance();
					Timestamp creation_time= new  Timestamp(report_time.getTimeInMillis());
					report_time.add(Calendar.MINUTE,2);
					Timestamp start_time= new  Timestamp(report_time.getTimeInMillis());
					report_time.add(Calendar.MINUTE,28);
					Timestamp end_time= new  Timestamp(report_time.getTimeInMillis());
				    
					saveReport((UUID.randomUUID()).toString(),creation_time,start_time,end_time,80.0,"video_quoe",location, "Gold");
					analysis.remove(ip);
					analysis.put(ip, end_time);
				}
				
				
			}
		}
		
		
		
		
	}
	
	private String getLocationByIP(String IP)
	{
		List<Location> locations=  locationRepo.findAll();
		Location location_no_remarks=null;
		
		for (int i=0;i<locations.size();i++)
		{   Location location= locations.get(i);
		
			if(location.getRemarks() != null )
			{
				if( location.getRemarks().compareTo("") !=0)
				{
						
					if((location.getRemarks()).compareTo(IP)==0)
					{
						return location.getTechnology();
					}
				}else
				{
					location_no_remarks=location;
				}
				
					
			} else
			{
			
				location_no_remarks=location;
			}
		}
		
		
		if(location_no_remarks != null){
			location_no_remarks.setRemarks(IP);
			locationRepo.save(location_no_remarks);
			return location_no_remarks.getTechnology();
		}else
			return null;
	}
	
	
	private Timestamp setBadAnalysis(String location, Timestamp time)
	{
		
		if(analysis.containsKey(location))
		{
			
			return (Timestamp) analysis.get(location);
		}else
		{
			analysis.put(location, time);
			return time;
		}
		
	}
	
	private void setGoodAnalysis(String location, Timestamp time)
	{
		if(analysis.containsKey(location))
		{
			analysis.remove(location);
		}
	   
	}
	
	
	private int  getFlows(String classification)
	{
		int flow_index=classification.indexOf("flows\":");
		String flow_value =classification.substring(flow_index+7,classification.indexOf(",\"red\""));
		
		int flowNumber=0;
		try{
			flowNumber=Integer.parseInt(flow_value);
		}catch(NumberFormatException e) 
		{
			return 0;
		}
		
		return flowNumber;
		
		
	}
	private String  getClassificationIP(String classification)
	{
		int ip_index=classification.indexOf("ip\":");
		String ip_value =classification.substring(ip_index+5,classification.indexOf(",\"flows\"")-1);
		return ip_value;
		
		
		
	}
	private boolean isGoodClassification(String classification)
	{
		int green_index=classification.indexOf("green\":");
		String green_per_cent =classification.substring(green_index+7,classification.indexOf(",\"orange\""));
		int greenNumber=0;
		try{
			greenNumber=Integer.parseInt(green_per_cent);
		}catch(NumberFormatException e) 
		{
			return true;
	    }
		System.out.println(greenNumber);
		if(greenNumber < 100)
		{
			return false;
		} else
		{
		  return true;
		}
		
		
	}
	
	
	private void saveReport(String report_id, Timestamp creationTime, Timestamp startTime, Timestamp endTime, Double confidence,  String affectedKPI_Id, String affectedLocationId, String affectedSegmentationId)
	{
		DegradationReport report = new DegradationReport();
		
		
		
		report.setId(report_id);
		report.setCreationDateTime(creationTime);
		report.setStartDateTime(startTime);
		report.setEndDateTime(endTime);
		
		report.setConfidence(confidence);
		
		
		AffectedKPI_PK affectedKPI_PK = new AffectedKPI_PK();
		affectedKPI_PK.setKpi_id(affectedKPI_Id);
		affectedKPI_PK.setDegradation_report_id(report_id);
		AffectedKPI affectedPKI= new AffectedKPI();
		affectedPKI.setAffectedKPI_PK(affectedKPI_PK);
		
		AffectedLocation_PK affectedLocation_PK = new AffectedLocation_PK();
		affectedLocation_PK.setLocation_id(affectedLocationId);
		affectedLocation_PK.setDegradation_report_id(report_id);
		AffectedLocation affectedLocation= new AffectedLocation();
		affectedLocation.setAffectedLocation_PK(affectedLocation_PK);
		
		
		AffectedSegmentation_PK affectedSegmentation_PK = new AffectedSegmentation_PK();
		affectedSegmentation_PK.setSegmentation_id( affectedSegmentationId);
		affectedSegmentation_PK.setDegradation_report_id(report_id);
		AffectedSegmentation affectedSegmentation= new AffectedSegmentation();
		affectedSegmentation.setAffectedSegmentation_PK(affectedSegmentation_PK);
		saveDegradationReport(report);
		
		saveAffected (affectedPKI, affectedLocation, affectedSegmentation);
		

	}
	
	@Transactional
	
	public void saveDegradationReport(DegradationReport report)
	{
		degradationReportRepo.save(report);
		
	}
	
	@Transactional
	
	public void saveAffected(AffectedKPI affectedKPI,AffectedLocation affectedLocation, AffectedSegmentation affectedSegmentation)
	{ 
					
		affectedKPIRepo.save(affectedKPI);
		affectedLocationRepo.save(affectedLocation);
		affectedSegmentationRepo.save(affectedSegmentation);
		
		
	}

	

	
	
	
		

}
