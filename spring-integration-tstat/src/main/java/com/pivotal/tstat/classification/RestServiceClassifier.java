package com.pivotal.tstat.classification;

import java.io.UnsupportedEncodingException;
import java.util.Base64;


import org.springframework.web.client.RestTemplate;

public class RestServiceClassifier implements Classifier{
	
	private String web_url= null;
	

	public RestServiceClassifier(String _URL)
	{
		web_url=_URL;
	}
	
	@Override
	public Classification predict (String tstatVector)
	{
		 RestTemplate restTemplate = new RestTemplate();
		 String base64Vector= null;
		try {
			base64Vector = Base64.getEncoder().encodeToString(tstatVector.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			
		}
		 
		 String get_url=web_url+"/"+base64Vector;
		 
		
		Classification result=  restTemplate.getForObject(get_url, Classification.class);
		
		 return result;
	   
	}

}
