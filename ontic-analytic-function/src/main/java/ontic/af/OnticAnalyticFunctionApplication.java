package ontic.af;

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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ImportResource("classpath:ontic-af-context.xml")
@EnableAutoConfiguration
@ComponentScan

public class OnticAnalyticFunctionApplication implements CommandLineRunner{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OnticAnalyticFunctionApplication.class);

		 @Autowired
	    private ApplicationContext ctx;
	    
		/**
		 * Load the Spring Integration Application Context
		 *
		 * @param args - command line arguments
		 */
		public static void main(final String... args) {
			SpringApplication app = new SpringApplication(OnticAnalyticFunctionApplication.class);
			app.setWebEnvironment(false);
			app.run(args);
		}

		public void run(String... args) throws Exception {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("\n========================================================="
						  + "\n                                                         "
						  + "\n          Starting Ontic Analytic Function node-                "
						  + "\n                                                         "
						  + "\n=========================================================" );
			}
			
			
		}
}
