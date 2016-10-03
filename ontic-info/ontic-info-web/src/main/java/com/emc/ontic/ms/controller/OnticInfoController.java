package com.emc.ontic.ms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.emc.ontic.ms.commons.constants.ControllerActions;
import com.emc.ontic.ms.commons.domain.ModifyDeliveryConditionsRequest;
import com.emc.ontic.ms.commons.domain.ModifySubscriptionRequest;
import com.emc.ontic.ms.commons.domain.StartSubscriptionRequest;
import com.emc.ontic.ms.commons.domain.StopDeliveryConditionsRequest;
import com.emc.ontic.ms.commons.domain.StopSubscriptionRequest;
import com.emc.ontic.ms.service.OnticInfoService;

@RestController
public class OnticInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(OnticInfoController.class);
	
	@Autowired
	private OnticInfoService onticInfoService;
	
	@Autowired
	RestTemplate restTemplate;
	
    @RequestMapping(value = ControllerActions.START_SUBSCRIPTION_ACTION, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void startSubscription(@RequestBody StartSubscriptionRequest request) throws Exception {
    	logger.info("OnticInfoController::startSubscription Start");
    	
    	logger.info("StartSubscriptionRequest values {}", request);
    	
    	onticInfoService.startSubscription(request);
    	
    	logger.info("OnticInfoController::startSubscription End");
    	
    }
    
    @RequestMapping(value = ControllerActions.MODIFY_SUBSCRIPTION_ACTION, method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void modifySubscription(@PathVariable String subsid, @RequestBody ModifySubscriptionRequest request) {
    	logger.info("OnticInfoController::modifySubscription Start");
    	
    	request.setSubsID(subsid);
    	
    	logger.info("ModifySubscriptionRequest values {}", request);
    	
    	onticInfoService.modifySubscription(request);
    	
    	logger.info("OnticInfoController::modifySubscription End");
    }
    
    @RequestMapping(value = ControllerActions.STOP_SUBSCRIPTION_ACTION, method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void stopSubscription(@PathVariable String subsid, @RequestBody StopSubscriptionRequest request) {
    	logger.info("OnticInfoController::stopSubscription Start");
    	
    	request.setSubsID(subsid);
    	
    	logger.info("StopSubscriptionRequest values {}", request);
    	
    	onticInfoService.stopSubscription(request);
    	
    	logger.info("OnticInfoController::stopSubscription End");
    }
    
    @RequestMapping(value = ControllerActions.MODIFY_DELIVERY_CONDITIONS_ACTION, method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void modifyDegredationReportDeliveryConditions(@PathVariable String sessionid, @RequestBody ModifyDeliveryConditionsRequest request) {
    	logger.info("OnticInfoController::modifyDegredationReportDeliveryConditions Start");
    	
    	request.setSessionID(sessionid);
    	
    	logger.info("ModifyDeliveryConditionsRequest values : {}", request);
    	
    	onticInfoService.modifyDegredationReportDeliveryConditions(request);
    	
    	logger.info("OnticInfoController::modifyDegredationReportDeliveryConditions End");
    }
    
    @RequestMapping(value = ControllerActions.STOP_DELIVERY_CONDITIONS_ACTION, method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void stopDegredationReportDeliveryConditions(@PathVariable String sessionid, @RequestBody StopDeliveryConditionsRequest request) {
    	logger.info("OnticInfoController::stopDegredationReportDeliveryConditions Start");
    	
    	logger.info("StopDeliveryConditionsRequest values : {}", request);
    	
    	request.setSessionID(sessionid);
    	
    	onticInfoService.stopDegredationReportDeliveryConditions(request);
    	
    	logger.info("OnticInfoController::stopDegredationReportDeliveryConditions End");
    }
}
