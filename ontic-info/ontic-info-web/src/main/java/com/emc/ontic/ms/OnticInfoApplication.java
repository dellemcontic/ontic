package com.emc.ontic.ms;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.emc.ontic.ms.commons.Greeter;
import com.emc.ontic.ms.commons.domain.ReportRequest;
import com.emc.ontic.ms.commons.domain.Validity;
import com.emc.ontic.ms.data.entity.ReportToBeNotified;
import com.emc.ontic.ms.data.repository.KpiRepository;
import com.emc.ontic.ms.data.repository.ReportToBeNotifiedRepository;
import com.emc.ontic.ms.service.OnticInfoService;
import com.emc.ontic.ms.service.utils.OnticUtils;

@SpringBootApplication
@Controller
@EnableScheduling
public class OnticInfoApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(OnticInfoApplication.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ReportToBeNotifiedRepository reportToBeNotifiedRepo;
	
	@Autowired
	private OnticInfoService onticInfoService;
	
	@Autowired
	private KpiRepository kpiRepository;

    public static void main(String[] args) {
    	
        SpringApplication.run(OnticInfoApplication.class, args);
        
    }
    
    @RequestMapping("/test")
    @ResponseBody
    String home() {
    	
        return Greeter.sayHello();
    }
    
    @Override
    public void onStartup(ServletContext servletContext)
    		throws ServletException {
    	System.out.println("adding app Id !!!");    	
    	System.setProperty("spring.cloud.appId", "ontic-ms");
    	super.onStartup(servletContext);
    }
    
    @Transactional
    //@Scheduled(fixedRate = 60000, initialDelay = 30000)
    @Scheduled(fixedRate = 1000, initialDelay = 1000)
    public void invokeRestService() throws JsonGenerationException, JsonMappingException, IOException {
    	logger.info("OnticInfoApplication::invokeRestService Start ...");
    	
    	//List<ReportRequest> requestList = getReportRequestHardCoded();
    	List<ReportRequest> requestList = getReportRequestList();
    	
    	for(ReportRequest request : requestList) {
    		//restTemplate.postForLocation("http://localhost:8101/deliverinitialdgreport", request);
    		if ((request.getSessionID() == null) || (request.getSessionID().length() == 0)){
    			//URI location = restTemplate.postForLocation(request.getSubscriberUrl() + "/sessions", new ObjectMapper().writeValueAsString(request));
    			HttpHeaders requestHeaders=new HttpHeaders();
    		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    		    String json = new ObjectMapper().writeValueAsString(request);
    		    HttpEntity<String> requestEntity = new HttpEntity<String>(json,requestHeaders);
    		    logger.info("OnticInfoApplication::invokeRestService ReportRequest json: " + json);
				HttpEntity<String> response = restTemplate.exchange(request.getSubscriberUrl() + "/sessions", HttpMethod.POST, requestEntity, String.class);
				HttpHeaders headers = response.getHeaders();
				URI location = headers.getLocation();
    			
				String sessionID = location.getPath().substring(location.getPath().lastIndexOf("/") + 1, location.getPath().length());
    			onticInfoService.deliverInitialDegredationReport(request, sessionID);
    		}else{
    			HttpHeaders requestHeaders=new HttpHeaders();
    		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    		    HttpEntity<String> requestEntity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(request),requestHeaders);
				HttpEntity<String> response = restTemplate.exchange(request.getSubscriberUrl() + "/sessions/" + request.getSessionID(), HttpMethod.PUT, requestEntity, String.class);
    			//restTemplate.postForLocation(request.getSubscriberUrl() + "/session/" + request.getSessionID(), new ObjectMapper().writeValueAsString(request));
				onticInfoService.deliverInitialDegredationReport(request, request.getSessionID());
    		}
    	}
        
        logger.info("OnticInfoApplication::invokeRest Service End...");
    }
            
    
    /**
     * 
     * @return This will return the Degradation report records from the degradation_report_to_be_notified view
     */
    private List<ReportRequest> getReportRequestList() {
    	List<ReportToBeNotified> list = reportToBeNotifiedRepo.findAll();
    	List<ReportRequest> reportReqList = new ArrayList<ReportRequest>();
  	
    	ReportRequest request = null;
    	
    	if(list != null && !list.isEmpty()) {
    		for(ReportToBeNotified report : list) {
    			if(request == null){
    				request = new ReportRequest();
    			// Si se cambia de report
    			}else if (!request.getReportID().equals(report.getReportToBeNotifiedPK().getReportId())){
    				reportReqList.add(request);
    				request = new ReportRequest();
    			}else if ((request.getSessionID() != null) && (!request.getSessionID().equals(report.getReportToBeNotifiedPK().getSessionId()))){
    				reportReqList.add(request);
    				request = new ReportRequest();
    			}else if ((request.getSessionID() != null) && (request.getSessionID().equals(report.getReportToBeNotifiedPK().getSessionId()) && (!request.getSubscriptionID().equals(report.getReportToBeNotifiedPK().getSubscriptionId())))){
    				reportReqList.add(request);
    				request = new ReportRequest();
    			}else if ((request.getSessionID() == null) && (!request.getSubscriptionID().equals(report.getReportToBeNotifiedPK().getSubscriptionId()))){
    				reportReqList.add(request);
    				request = new ReportRequest();
    			}

    			request.setSubscriptionID(report.getReportToBeNotifiedPK().getSubscriptionId());
    			
    			request.setReportID(report.getReportToBeNotifiedPK().getReportId());
    			
    			request.setSessionID(report.getReportToBeNotifiedPK().getSessionId());
    			
    			request.setSubscriberUrl(report.getSubscriberUrl());
    			
    			request.setConfidence(report.getReportConfidence());
    			
    			request.addLocation(report.getReportToBeNotifiedPK().getLocationId());
    			
    			request.addServiceKpi(report.getService(), report.getReportToBeNotifiedPK().getKpiId(), report.getKpiShare());
    			
    			request.addGroup(report.getReportToBeNotifiedPK().getSegmentationId(), report.getSegmentationShare());
    		   			
    			//request.setTimeStamp(OnticUtils.getConvertedDate(report.getReportCreationTime()));
    			request.setTimeStamp(report.getReportCreationTime().toString());
    			
    			request.setFrequency(report.getFrequency());
    			
    			Validity validity = new Validity();
    			//validity.setStart(OnticUtils.getConvertedDate(report.getReportStartTimme()));
    			validity.setStart(report.getReportStartTimme().toString());
    			//validity.setEnd(OnticUtils.getConvertedDate(report.getReportEndTime()));
    			validity.setEnd(report.getReportEndTime().toString());
    			request.setValidity(validity);
    		}
    		if (request != null){
    			reportReqList.add(request);
    		}
    	}
    	
    	return reportReqList;
    }
}
