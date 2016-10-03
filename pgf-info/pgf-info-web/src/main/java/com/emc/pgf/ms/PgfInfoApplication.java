package com.emc.pgf.ms;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emc.pgf.ms.commons.Greeter;

@SpringBootApplication
@Controller
public class PgfInfoApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
    	
        SpringApplication.run(PgfInfoApplication.class, args);
        
    }
    
    
    @RequestMapping("/test")
    @ResponseBody
    String home() {
    	
        return Greeter.sayHello();
    }
    
    @Override
    public void onStartup(ServletContext servletContext)
    		throws ServletException {
    	System.out.println("adding app Id !!!");    	
    	System.setProperty("spring.cloud.appId", "pgf-ms");
    	super.onStartup(servletContext);
    }
}
