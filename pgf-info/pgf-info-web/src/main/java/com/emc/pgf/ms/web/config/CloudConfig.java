package com.emc.pgf.ms.web.config;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ServiceScan
@Configuration
public class CloudConfig {

	@Bean
	public Cloud cloud() {
		try {
			return new CloudFactory().getCloud();
		} catch (CloudException e) {
			throw e;
		}
	}

	@Bean
	public ApplicationInstanceInfo applicationInstanceInfo() {
		return cloud().getApplicationInstanceInfo();
	}

}