package com.ontic.pgf.mitigation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;



@SpringBootApplication
@Configuration
@ImportResource("classpath:app-context.xml")
@EnableAutoConfiguration
@ComponentScan

public class Application {
	 	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
   
}
