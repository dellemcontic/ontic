package com.emc.ontic.websocket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScheduleTask {
	
	@Value("${spark.finaloutput.folder}")
	private String rootFolder = "";
	
	private File lastFileAnalyzed = null;

    @Autowired
    private SimpMessagingTemplate template;
    
    private String getRootFolder(){
    	return this.rootFolder;
    }

    // this will send a message to an endpoint on which a client can subscribe
    @Scheduled(fixedRate = 1000)
    public void trigger() throws MessagingException, JsonGenerationException, JsonMappingException, IOException, InterruptedException {
    	List<String> contents = readSparkAnalyzedFileAsFlows();
    	ObjectMapper mapper = new ObjectMapper();
    	
    	if (contents != null){
    		long sleep = Math.round(new Double(60000) / new Double(contents.size()));

    		for (String line : contents){
    			StrTokenizer tokenizer = new StrTokenizer(line, " ");
            	String [] array = tokenizer.getTokenArray();
            	//IPInfo info = new IPInfo(array[0], array[142], array[139], array[140], array[141], array[138], array[144], new Integer(contents.size()).toString(), sleep);
            	IPInfo info = new IPInfo(array[0], array[2], array[2], array[2], array[2], array[2], array[2], new Integer(contents.size()).toString(), sleep);
            	//this.template.convertAndSend("/topic/message", info.getAvgBitRate());
            	this.template.convertAndSend("/topic/message", mapper.writeValueAsString(info));
            	Thread.sleep(sleep);
    		}
    	}
    }
    
    private class TestBedStatus{
    	
    	private List<Workstation> workstations =  new ArrayList<Workstation>();
    	
    	public void setWorkstations(List<Workstation> workstations){
    		this.workstations = workstations;
    	}
    	
    	public List<Workstation> getWorkstations(){
    		return this.workstations;
    	}
    }
    
    private class Workstation {
		private String ip = "";
		private int flows = 0;
		private int red = 0;
		private int green = 0;
		private int orange = 0;
		
		public Workstation(String ip, int flows, int red, int orange, int green){
			this.ip = ip;
			this.flows = flows;
			this.red = red;
			this.orange = orange;
			this.green = green;
		}
		
		public String getIp(){
			return this.ip;
		}
		
		public void setIp(String value){
			this.ip = value;
		}
		
		public int getFlows(){
			return this.flows;
		}
		
		public void setFlows(int value){
			this.flows = value;
		}
		
		public int getRed(){
			return this.red;
		}
		
		public void setRed(int value){
			this.red = value;
		}
		
		public int getOrange(){
			return this.orange;
		}
		
		public void setOrange(int value){
			this.orange = value;
		}
		
		public int getGreen(){
			return this.green;
		}
		
		public void setGreen(int value){
			this.green = value;
		}
	}
    
    private class IPInfo{
    	private String ip = null;
    	private String avgBitRate = null;
    	private String bitRateClient = null;
    	private String bitRateServer = null;
    	private String avgRttClient = null;
    	private String avgRttServer = null;
    	private String firstPayload = null;
    	private String flowsPerMinute = null;
    	private long sleep = 0;
    	
    	public IPInfo(String ip, String bitRateClient, String avgBitRate, String avgRttClient, String avgRttServer, String bitRateServer, String firstPayload, String flowsPerMinute, long sleep){
    		this.ip = ip;
    		this.avgBitRate = avgBitRate;
    		this.bitRateClient = bitRateClient;
    		this.bitRateServer = bitRateServer;
    		this.avgRttClient = avgRttClient;
    		this.avgRttServer = avgRttServer;
    		this.firstPayload = firstPayload;
    		this.sleep = sleep;
    		this.flowsPerMinute = flowsPerMinute;
    	}
    	
    	public String getIp(){
    		return this.ip;
    	}
    	
    	public String getAvgBitRate(){
    		return this.avgBitRate;
    	}
    	
    	public String getFirstPayload(){
    		return this.firstPayload;
    	}
    	
    	public String getBitRateClient(){
    		return this.bitRateClient;
    	}
    	
    	public String getBitRateServer(){
    		return this.bitRateServer;
    	}
    	
    	public long getSleep(){
    		return this.sleep;
    	}
    	
    	public String getAvgRttClient(){
    		return this.avgRttClient;
    	}
    	
    	public String getAvgRttServer(){
    		return this.avgRttServer;
    	}
    	
    	public String getFlowsPerMinute(){
    		return this.flowsPerMinute;
    	}
    }
    
    private String correctNumber(String value){
    	String result = null;
    	if (value.length() == 1){
    		result = "0" + value;
    	}else{
    		result = value;
    	}
    	return result;
    }
    
    private List<String> readSparkAnalyzedFileAsFlows (){
    	List<String> contents = null;
    	String pattern = "(\\d+)_(\\d+)_(\\d+)_(\\d+)_(\\d+)_(\\d+)_log_video_complete_analyzed";
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Pattern r = Pattern.compile(pattern);
    	File fileToProcess = null;
    	
    	try {
    		Date date = null;
    		if (lastFileAnalyzed == null){
    			date = formatter.parse("1977-02-16 08:45:00");
    		}else{
    			if (lastFileAnalyzed.isFile()) {
    		        Matcher m = r.matcher(lastFileAnalyzed.getName());
    		        if (m.find()) {
    		        	String year = m.group(1);
    		        	String month = correctNumber(m.group(2));
    		    		String day = correctNumber(m.group(3));
    		    		String hour = correctNumber(m.group(4));
    		    		String minute = correctNumber(m.group(5));
    		    		String second = correctNumber(m.group(6));
    		    		date = formatter.parse(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
    		        }
    		    }
    		}
    		
       		File[] files = new File(getRootFolder()).listFiles();
       		
    		// Get last file in the folder
       		if (files != null){
       			for (File file : files) {
        		    if (file.isFile()) {
        		        Matcher m = r.matcher(file.getName());
        		        if (m.find()) {
        		        	String year = m.group(1);
        		        	String month = correctNumber(m.group(2));
        		    		String day = correctNumber(m.group(3));
        		    		String hour = correctNumber(m.group(4));
        		    		String minute = correctNumber(m.group(5));
        		    		String second = correctNumber(m.group(6));
        		    		
        		    		Date dateAux = formatter.parse(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
        		    		if (dateAux.after(date)){
        		    			date = dateAux;
        		    			fileToProcess = file;
        		    		}
        		        }
        		    }
        		}
        		if (fileToProcess != null){
        			contents = FileUtils.readLines(fileToProcess);
        			lastFileAnalyzed = fileToProcess;
        		}
       		} else {
       			System.out.println("No files on the folder : " + getRootFolder());
       		}
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
        	e.printStackTrace();
		}
    	
    	return contents;
    }
}
