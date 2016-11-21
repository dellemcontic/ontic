package ontic.af.analytic;


import java.io.IOException;
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
import ontic.af.analytic.schema.Cell;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper; 

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
	   LOGGER.debug(classification_input);
	   Cell cell = null;
	   ObjectMapper mapper = new ObjectMapper();
	  
	   try {
	         
	           cell = mapper.readValue(classification_input, Cell.class);
	           
	           	      
	       }  catch (Exception e) {
		       System.out.println(e.toString());
	       }
       
	   Timestamp classification_time  = getClassificationTime(cell);
	   String cellName= getClassificationCell(cell);
	   
	  
		if(getFlows(cell) >= 1)
		{
			if(isGoodClassification(cell))
			{
				LOGGER.info("Received good analysis:"+classification_time+". Cell:"+cellName);
				setGoodAnalysis(cellName, classification_time);
				
				
			}else
			{
								
				LOGGER.info("Received BAD analysis:"+classification_time+". Cell:"+cellName);
				Timestamp bad_time= setBadAnalysis(cellName, classification_time);
			    Calendar old_bad_time = Calendar.getInstance();
				old_bad_time.setTimeInMillis(bad_time.getTime());
				old_bad_time.add(Calendar.MINUTE,Integer.parseInt(this.getMinutesInBad()));
				
				LOGGER.info("Time to remediation :"+new Timestamp(old_bad_time.getTimeInMillis()));
				if(classification_time.getTime()  >= old_bad_time.getTimeInMillis() )
				{
					LOGGER.info("Creating a report");
					Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					currentTime.set(Calendar.ZONE_OFFSET, TimeZone.getTimeZone("UTC").getRawOffset());
					Calendar report_time = Calendar.getInstance();
					report_time.set(Calendar.HOUR_OF_DAY, currentTime.get(Calendar.HOUR_OF_DAY));
					
					Timestamp creation_time= new  Timestamp(report_time.getTimeInMillis());
					report_time.add(Calendar.MINUTE,2);
					Timestamp start_time= new  Timestamp(report_time.getTimeInMillis());
					report_time.add(Calendar.MINUTE,28);
					Timestamp end_time= new  Timestamp(report_time.getTimeInMillis());
					Random random= new Random();
					int randomInteger = random.nextInt(10); //(0..10)
				    double confidence= 0.9+(randomInteger * 0.01);
				    String report_id=(UUID.randomUUID()).toString();
					saveReport(report_id,creation_time,start_time,end_time,confidence,"video_qoe",cellName);
					bad_analysis.remove(cellName);
					bad_analysis.put(cellName, end_time);
					report_send.put(cellName,report_id);
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
					//degradationReportRepo.delete(report_send.get(location));
					//Actualiza report
					DegradationReport report= degradationReportRepo.findOne(report_send.get(location));
					Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					currentTime.set(Calendar.ZONE_OFFSET, TimeZone.getTimeZone("UTC").getRawOffset());
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.HOUR_OF_DAY, currentTime.get(Calendar.HOUR_OF_DAY));
					cal.add(Calendar.MINUTE, 1);
					report.setStartDateTime(new Timestamp(cal.getTimeInMillis()));
					report.setEndDateTime(new Timestamp(cal.getTimeInMillis()));
					degradationReportRepo.save(report);
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
	
	private Timestamp getClassificationTime(Cell cell){
		String time_value =cell.getTimestamp();
		String[] tiempo = time_value.split("_");
		Calendar out= Calendar.getInstance();
		out.set(Integer.parseInt(tiempo[0]),
		 Integer.parseInt(tiempo[1])-1,
		 Integer.parseInt(tiempo[2]),
		 Integer.parseInt(tiempo[3]),
		 Integer.parseInt(tiempo[4]),0);
		
		return new Timestamp(out.getTimeInMillis());
	}
	private int  getFlows(Cell cell)
	{
		
		
		return cell.getWeighted_Total();
				
		
		
		
	}
	private String  getClassificationCell(Cell cell)
	{
			
		return cell.getName();
		
	}
	private boolean isGoodClassification(Cell cell)
	{
	
		
		int greenNumber=cell.getWeighted_Green();
		
		int umbral=0;
		try{
			
			
			umbral=Integer.parseInt(getThreshold());
			
			
		}catch(NumberFormatException e) 
		{
			return true;
	    }
		
		LOGGER.info("Good analisys percentage:"+greenNumber+". Treshold:"+umbral);
		if(greenNumber < umbral )
		{
			return false;
		} else
		{
		  return true;
		}
		
		
	}
	
	/* private  long toUTC(long timestamp) {
		  
		  final long localTimeZoneoffset = TimeZone.getDefault().getOffset(0L);
		  final long dstOffset = TimeZone.getDefault().getDSTSavings();

		 long offsetOftime = TimeZone.getDefault().getOffset(timestamp);
		 long timeinmilli = 0L; 
		 if(offsetOftime != localTimeZoneoffset)
		    timeinmilli = timestamp+localTimeZoneoffset+dstOffset;
		 else
		    timeinmilli = timestamp+localTimeZoneoffset;
		 return timeinmilli;
	}*/
	
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
		
		int share_acumulated=0;
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
				Random rand = new Random();
				
				// For 5 segmentations ---> randomize share
				int share=0;
				if( i==4)
				{
					share=100 -share_acumulated;
				
			    }else
			    {
			    	 share= rand.nextInt(20) + 15; // Random 15->20
			    }
				
				affectedSegmentation.setShare(share);
				share_acumulated=share_acumulated+share;
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
