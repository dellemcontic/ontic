package com.ontic.pgf.mitigation;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontic.pgf.mitigation.interfaces.activation.PlanActivationReq;
import com.ontic.pgf.mitigation.interfaces.deactivation.PlanDeactivationReq;



import javax.servlet.http.HttpServletRequest;


@RestController
@EnableAutoConfiguration

public class PGFMitigationController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	private PGFMitigation plansMitigation;
	
	
	// Mitigation Plan Activation
	
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseEntity<?> activation(@RequestBody PlanActivationReq plans, HttpServletRequest request) {
    	
    	log.info("------------------Activation Order------------------------");
    	log.info("Received plan activation with timestamp:"+plans.getTimestamp());
    	ObjectMapper mapper = new ObjectMapper();
    	 try {
			log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(plans));
		} catch (JsonProcessingException e) {
	
			log.error(e.toString());
			 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500 
		}
    	   	
     	 
    	 if( plansMitigation.executePlans(plans.getPlans())){
    	 
	    	 String id = PGFMitigationImpl.generateRandomString(17);
	    	 HttpHeaders httpHeaders = new HttpHeaders();
	    	 httpHeaders.add("Location",request.getRequestURL().toString()+"/"+id);
	    	 log.info("Return:(302); Created. Order ID:"+id);
	    	 log.info("----------------------------------------------------");
	    	 
	    	 return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);//302 
    	 }
    	 else
    	 {
    		 log.info("Return:(500) Internal Server Error");
	    	 log.info("----------------------------------------------------");
    		 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500 
    	 }
    	 
    }

    
    // Mitigation Plan Deactivation

	@RequestMapping(value="/plans/{plan_id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivation(@PathVariable("plan_id") String planId,@RequestBody PlanDeactivationReq plans) {
		log.info("------------------Deactivation Order------------------------");
		log.info("Plan ID:"+planId);
		
		ObjectMapper mapper = new ObjectMapper();
   	 try {
			log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(plans));
		} catch (JsonProcessingException e) {
			
			log.error(e.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500 
		}
		
   	 log.info("Return:(200) OK");
	 log.info("----------------------------------------------------");
	   return new ResponseEntity<>(HttpStatus.OK);//200 OK 
	}
	
	
	@RequestMapping(value="/orders/{order_id}/plans/{plan_id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivation(@PathVariable("order_id") String orderId,@PathVariable("plan_id") String planId,@RequestBody PlanDeactivationReq plans) {
		log.info("------------------Deactivation Order------------------------");
		log.info("Plan ID:"+planId);
		log.info("OrderID:"+orderId);
		
		ObjectMapper mapper = new ObjectMapper();
   	 try {
			log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(plans));
		} catch (JsonProcessingException e) {
			
			log.error(e.toString());
			 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500 
		}
		
		 
		 
	   return new ResponseEntity<>(HttpStatus.OK);//200 OK 
	}
	
	  
}
