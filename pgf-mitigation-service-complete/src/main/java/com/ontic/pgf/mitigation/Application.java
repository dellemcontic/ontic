package com.ontic.pgf.mitigation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
@EnableConfigurationProperties(com.ontic.pgf.mitigation.PGFConfiguration.class)

public class Application {
	 	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
   
}
