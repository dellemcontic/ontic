package com.emc.pgf.ms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.emc.pgf.ms.commons.domain.PgfRequest;

@RestController
public class PgfInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(PgfInfoController.class);
	
	Map<String, String> sessionReportMap = new HashMap<String, String>();
    
    @RequestMapping(value="/sessions", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void readDegredationReport(@RequestBody PgfRequest request, HttpServletRequest req, HttpServletResponse resp) {
    	logger.info("PgfInfoController::readDegredationReport Start");
    	
    	logger.info("PgfRequest values {}", request);
    	
    	String sessionID = sessionReportMap.get(request.getReportID());
    	
    	if(sessionID == null) {
    		sessionID = UUID.randomUUID().toString();
    		logger.info("sessionID does not exist so creating new sessionID {}", sessionID);
    		sessionReportMap.put(request.getReportID(), sessionID);
    	} else {
    		logger.info("sessionID exists so returning existing sessionID {}", sessionID);
    	}
    	
    	//determine the location of the newly created session
    	resp.addHeader("Location", getLocationForChildResource(req, sessionID));
    	
    	logger.info("PgfInfoController::readDegredationReport End");
    	
    }
    
    @RequestMapping(value = "/sessions/{sessionid}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void readDegredationReport1(@PathVariable String sessionid, @RequestBody PgfRequest request) {
    	logger.info("PgfInfoController::readDegredationReport1 Start");
    	
    	logger.info("PgfRequest values {}", request);
    	
    	logger.info("PgfInfoController::readDegredationReport1 End");
    }
    
    private String getLocationForChildResource(HttpServletRequest req, String sessionID) {
    	StringBuffer url = req.getRequestURL();
    	
    	String childUrl = url.append("/").append(sessionID).toString();
    	logger.info("Child resource url : {}", childUrl);
    	
    	return childUrl;
    }

}
