package com.ontic.pgf.mitigation;


import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Component

@ConfigurationProperties(prefix="PGF")
public class PGFConfiguration {
     
	
	
	private String sshUser;
	private String sshPass;
	private String rabbit_exchange;
	private String rabbit_routingkey;
	
	


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

	
	
   
}