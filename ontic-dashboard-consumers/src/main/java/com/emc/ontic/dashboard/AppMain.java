package com.emc.ontic.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.emc.ontic.dashboard")
public class AppMain{

    public static void main(String[] args) throws Exception{
    	
    	SpringApplication.run(AppMain.class, args);

    }
}

