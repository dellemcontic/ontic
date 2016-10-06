package com.ontic.pgf.mitigation;


import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix="PGF")
public class PGFConfiguration {
     
	@Value("#{'${PGF.locations_name}'.split(',')}") 
    private List<String> locations_name;
	
	@Value("#{'${PGF.locations}'.split(',')}") 
    private List<String> locations;
	
	@Value("#{'${PGF.hosts}'.split(',')}") 
	private List<String> hosts;
	
	private String sshUser;
	private String sshPass;
	private String rabbit_exchange;
	private String rabbit_routingkey;
	
	
    public String getLocationHost(String location){
     
    	int location_index= locations.indexOf(location);
    	return hosts.get(location_index);
    	
    }
    
    public String getLocationName(String location){
        
    	int location_index= locations.indexOf(location);
    	return locations_name.get(location_index);
    	
    }
    
   
    
  
	

	public List<String> getLocations() {
		return locations;
	}






	public void setLocations(List<String> locations) {
		this.locations = locations;
	}






	public List<String> getHosts() {
		return hosts;
	}






	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}






	public String getSshUser() {
		return sshUser;
	}






	public void setSshUser(String sshUser) {
		this.sshUser = sshUser;
	}






	public String getSshPass() {
		return sshPass;
	}






	public void setSshPass(String sshPass) {
		this.sshPass = sshPass;
	}




	public List<String> getLocations_name() {
		return locations_name;
	}

	public void setLocations_name(List<String> locations_name) {
		this.locations_name = locations_name;
	}

	public String getRabbit_exchange() {
		return rabbit_exchange;
	}

	public void setRabbit_exchange(String rabbit_exchange) {
		this.rabbit_exchange = rabbit_exchange;
	}

	public String getRabbit_routingkey() {
		return rabbit_routingkey;
	}

	public void setRabbit_routingkey(String rabbit_routingkey) {
		this.rabbit_routingkey = rabbit_routingkey;
	}

	@Bean(name="pGFMitigationBean")
	 public PGFMitigation pgfMitigationImpl() 
	 {
	     return new PGFMitigationImpl(this);
	 }
	
   
}