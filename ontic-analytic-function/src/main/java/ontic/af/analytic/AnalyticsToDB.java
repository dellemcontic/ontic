package ontic.af.analytic;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import ontic.af.OnticAnalyticFunctionApplication;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsToDB.class);
	private static Hashtable<String, Timestamp> bad_analysis = new Hashtable<String, Timestamp>();
	private static Hashtable<String, Timestamp> good_analysis = new Hashtable<String, Timestamp>();
	private static Hashtable<String, String> report_send = new Hashtable<String, String>();
    private String minutesInBad;
    private String threshold;
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
	
    
	
	public String getMinutesInBad() {
		return minutesInBad;
	}

	public void setMinutesInBad(String minutesInBad) {
		this.minutesInBad = minutesInBad;
	}
		

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	@Override
	public void analyze(Message<byte[]> message) { 
	 
	try{
		
		
		 
	   String classification_input= new String(message.getPayload());
	   Timestamp classification_time  = getClassificationTime(classification_input);
	   String cell= getClassificationCell(classification_input);
	   
	 
	   
		if(getFlows(classification_input) >= 1)
		{
			if(isGoodClassification(classification_input))
			{
				LOGGER.info("Received good analysis:"+classification_time+". Cell:"+cell);
				setGoodAnalysis(cell, classification_time);
				
				
			}else
			{
								
				LOGGER.info("Received BAD analysis:"+classification_time+". Cell:"+cell);
				Timestamp bad_time= setBadAnalysis(cell, classification_time);
			    Calendar old_bad_time = Calendar.getInstance();
				old_bad_time.setTimeInMillis(bad_time.getTime());
				old_bad_time.add(Calendar.MINUTE,Integer.parseInt(this.getMinutesInBad()));
				
				LOGGER.info("Time to remediation :"+new Timestamp(old_bad_time.getTimeInMillis()));
				if(classification_time.getTime()  >= old_bad_time.getTimeInMillis() )
				{
					Calendar report_time = Calendar.getInstance();
					Timestamp creation_time= new  Timestamp(this.toUTC(report_time.getTimeInMillis()));
					report_time.add(Calendar.MINUTE,2);
					Timestamp start_time= new  Timestamp(this.toUTC(report_time.getTimeInMillis()));
					report_time.add(Calendar.MINUTE,28);
					Timestamp end_time= new  Timestamp(this.toUTC(report_time.getTimeInMillis()));
					Random random= new Random();
					int randomInteger = random.nextInt(10); //(0..10)
				    double confidence= 0.9+(randomInteger * 0.01);
				    String report_id=(UUID.randomUUID()).toString();
					saveReport(report_id,creation_time,start_time,end_time,confidence,"video_qoe",cell);
					bad_analysis.remove(cell);
					bad_analysis.put(cell, end_time);
					report_send.put(cell,report_id);
				}
				
				
			}
		}
		
	}catch (Exception e)
	{
	    e.printStackTrace();
		LOGGER.error(e.toString());
		
	}
		
		
	}
	

	
	
	private Timestamp setBadAnalysis(String location, Timestamp time)
	{
		if (good_analysis.containsKey(location))
		{
			good_analysis.remove(location);
		}
		if(bad_analysis.containsKey(location))
		{
			
			return (Timestamp) bad_analysis.get(location);
		}else
		{
			bad_analysis.put(location, time);
			return time;
		}
		
	}
	
	private void setGoodAnalysis(String location, Timestamp time)
	{
		if(report_send.containsKey(location))
		{
			if(!good_analysis.containsKey(location))
			{
				good_analysis.put(location, time);
				
			}else
			{
				//Comprueba timestamp 
				Calendar past_good_time = Calendar.getInstance();
				past_good_time.setTimeInMillis(((Timestamp) good_analysis.get(location)).getTime());
				past_good_time.add(Calendar.MINUTE,Integer.parseInt(this.getMinutesInBad()));
				long good_limit_time=past_good_time.getTimeInMillis();
				long actual_good_time=time.getTime();
				if(actual_good_time >= good_limit_time )
				{
					//Elimina report
					degradationReportRepo.delete(report_send.get(location));
					report_send.remove(location);
					good_analysis.remove(location);
					bad_analysis.remove(location);
				}
				
			}
			
		}else
		{
		  if(bad_analysis.containsKey(location))
		  {
			  bad_analysis.remove(location);
		  }
		}
	   
	}
	
	private Timestamp getClassificationTime(String classification){
		int time_index=classification.indexOf("timestamp\":");
		String time_value =classification.substring(time_index+12,classification.indexOf(",\"workstations\"")-1);
		String[] tiempo = time_value.split("_");
		Calendar out= Calendar.getInstance();
		out.set(Integer.parseInt(tiempo[0]),
		 Integer.parseInt(tiempo[1])-1,
		 Integer.parseInt(tiempo[2]),
		 Integer.parseInt(tiempo[3]),
		 Integer.parseInt(tiempo[4]),0);
		
		return new Timestamp(out.getTimeInMillis());
	}
	private int  getFlows(String classification)
	{
		int flow_index=classification.indexOf("weighted_Total\":");
		
		String flow_value =classification.substring(flow_index+16,classification.lastIndexOf("}"));
				
		int flowNumber=0;
		try{
			flowNumber=Integer.parseInt(flow_value);
		}catch(NumberFormatException e) 
		{
			return 0;
		}
		
		return flowNumber;
		
		
	}
	private String  getClassificationCell(String classification)
	{
		int cell_index=classification.indexOf("name\":");
		String cell_value =classification.substring(cell_index+7,classification.indexOf(",\"id\"")-1);
		return cell_value;
		
		
		
	}
	private boolean isGoodClassification(String classification)
	{
		int green_index=classification.indexOf("weighted_Green\":");
		String green_per_cent =classification.substring(green_index+16,classification.indexOf(",\"weighted_Medium\""));
		LOGGER.info("Good analisys percentage:"+green_per_cent);
		int greenNumber=0;
		int umbral=0;
		try{
			
			greenNumber=Integer.parseInt(green_per_cent);
			umbral=Integer.parseInt(getThreshold());
			
			
		}catch(NumberFormatException e) 
		{
			return true;
	    }
		
		if(greenNumber < umbral )
		{
			return false;
		} else
		{
		  return true;
		}
		
		
	}
	
	 private  long toUTC(long timestamp) {
		    Calendar cal = Calendar.getInstance();
		    int offset = cal.getTimeZone().getOffset(timestamp);
		    return timestamp + offset;
	}
	
	private void saveReport(String report_id, Timestamp creationTime, Timestamp startTime, Timestamp endTime, Double confidence,  String affectedKPI_Id, String cell_name)
	{
		
		DegradationReport report = new DegradationReport();
		
		
		report.setId(report_id);
		report.setCreationDateTime(creationTime);
		report.setStartDateTime(startTime);
		report.setEndDateTime(endTime);
		
		report.setConfidence(confidence);
		
		saveDegradationReport(report);
		
		
		AffectedKPI_PK affectedKPI_PK = new AffectedKPI_PK();
		affectedKPI_PK.setKpi_id(affectedKPI_Id);
		affectedKPI_PK.setDegradation_report_id(report_id);
		AffectedKPI affectedPKI= new AffectedKPI();
		affectedPKI.setAffectedKPI_PK(affectedKPI_PK);
		saveAffectedKPI(affectedPKI);
		
		List<Location> locations=  locationRepo.findAll();
		
		for (int i=0;i<locations.size();i++)
		{   
			Location location= locations.get(i);
			if(location.getCell_name().compareTo(cell_name) == 0)
			{
				// Affected locations
				
				AffectedLocation_PK affectedLocation_PK = new AffectedLocation_PK();
				affectedLocation_PK.setLocation_id(location.getId());
				affectedLocation_PK.setDegradation_report_id(report_id);
				AffectedLocation affectedLocation= new AffectedLocation();
				affectedLocation.setAffectedLocation_PK(affectedLocation_PK);
				affectedLocation.setCell_id(location.getCell_id());
			 
				// Afected Segmentations
				AffectedSegmentation_PK affectedSegmentation_PK = new AffectedSegmentation_PK();
				affectedSegmentation_PK.setSegmentation_id(location.getSegmentation_id());
				affectedSegmentation_PK.setDegradation_report_id(report_id);
				
				
				AffectedSegmentation affectedSegmentation= new AffectedSegmentation();
				affectedSegmentation.setAffectedSegmentation_PK(affectedSegmentation_PK);
				affectedSegmentation.setShare(100);
				
				saveAffected(affectedLocation, affectedSegmentation);
			}
		}
		
	
	
	
		

	}
	
	@Transactional
	
	public void saveDegradationReport(DegradationReport report)
	{
		degradationReportRepo.save(report);
		
	}
	
	@Transactional
	
	public void saveAffectedKPI(AffectedKPI affectedKPI)
	{ 
					
		affectedKPIRepo.save(affectedKPI);
		
   }
	
	
	@Transactional
	
	public void saveAffected(AffectedLocation affectedLocation, AffectedSegmentation affectedSegmentation)
	{ 
					
		 affectedLocationRepo.save(affectedLocation);
		 affectedSegmentationRepo.save(affectedSegmentation);
		
		
	}



	

	
	
	
		

}
