package com.pivotal.tstat.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/writer-tstat-context.xml")
//@ImportResource("file:C:/Eclipse/spring-tool-suite-3.7.3/workspace/spring-integration-tstat/src/main/resources/writer-tstat-context.xml")
@EnableAutoConfiguration
@ComponentScan
public class WriterServer implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(WriterServer.class);

    @Autowired
    private ApplicationContext ctx;
    
	/**
	 * Load the Spring Integration Application Context
	 *
	 * @param args - command line arguments
	 */
	public static void main(final String... args) {
		SpringApplication app = new SpringApplication(WriterServer.class);
		app.setWebEnvironment(false);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("\n========================================================="
					  + "\n                                                         "
					  + "\n          Starting a Writter node                 "
					  + "\n                                                         "
					  + "\n=========================================================" );
		}
	}
}