package com.emc.ontic.ms.aop;

import java.sql.Timestamp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.emc.ontic.ms.data.entity.Activity;
import com.emc.ontic.ms.data.repository.ActivityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Aspect
@Component
public class RequestMonitor {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestMonitor.class);
	
	@Autowired
	ActivityRepository activityRepo;
	
	@Around(value = "execution(* com.emc.ontic.ms.controller.OnticInfoController.*(..)) && @annotation(requestMapping) && @annotation(responseStatus)")
	public Object logResponse(ProceedingJoinPoint joinPoint, RequestMapping requestMapping,	ResponseStatus responseStatus) {
		logger.info("RequestMonitor::logResponse invoked..........");
		
		Object retVal = null;
        try {
            logger.info("Before executing controller method");
            
            Object[] args = joinPoint.getArgs();
            Object obj = args[0];
            
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(obj);
            
            logger.info("Request JSON : {}", json);
            
            saveActivity("in", json, requestMapping.value()[0]);
            
            retVal = joinPoint.proceed();
            
            logger.info("After executing controller method");
            
            saveActivity("out", responseStatus.value().toString(), requestMapping.value()[0]);
        } 
        catch(Throwable e) {
            logger.error("Execution error");
            saveActivity("out", e.getMessage(), requestMapping.value()[0]);
        }
        
        return retVal;
		
	}
	
	private void saveActivity(String direction, String json, String url) {
		Activity activity = new Activity();
        activity.setDirection(direction);
        activity.setJsonMessage(json);
        activity.setUrl(url);
        activity.setRequestTime(new Timestamp(System.currentTimeMillis()));
        
        activityRepo.save(activity);
        
        logger.info("Saved activity record...");
	}

}
