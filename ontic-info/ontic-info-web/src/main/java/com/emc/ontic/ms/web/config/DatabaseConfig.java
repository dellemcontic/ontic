package com.emc.ontic.ms.web.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;


@Configuration
public class DatabaseConfig {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
	
	@Autowired
	Environment environment;
	
	
	@Autowired
	Cloud cloud ;

	
	@Profile("cloud")
	@Bean
    public DataSource dataSource() {
        
        String props = System.getenv("VCAP_SERVICES");
        int index = getServiceIndex("PostgresDB",props); //TODO: User provided service name in PCF
        String url = JsonPath.read(props, "$.user-provided["+index+"].credentials.url");
        String driver = JsonPath.read(props, "$.user-provided["+index+"].credentials.driver");
        String uname = JsonPath.read(props, "$.user-provided["+index+"].credentials.username");
        String password = JsonPath.read(props, "$.user-provided["+index+"].credentials.password");
        return createBasicDataSource(url,driver,uname,password);
    }
	

	@Profile("local")
	@Bean
    public DataSource dataSourceAf() {
						
		logger.info("DatabaseConfig::dataSourceAf db.driver {}", environment.getProperty("db.driver"));
		String username = environment.getProperty("db.username");
		if(environment.getProperty("AF_POSTGRES_USERNAME") != null){
			username = environment.getProperty("AF_POSTGRES_USERNAME"); 
		}
		logger.info("DatabaseConfig::dataSourceAf db.username {}", username);
		String password = environment.getProperty("db.password");
		if(environment.getProperty("AF_POSTGRES_PASSWORD") != null){
			password = environment.getProperty("AF_POSTGRES_PASSWORD");
		}
		logger.info("DatabaseConfig::dataSourceAf db.password {}", "*********");
		logger.info("DatabaseConfig::dataSourceAf DB_NAME {}", environment.getProperty("DB_NAME"));
		
		String db_url = environment.getProperty("db.url");
		if ((environment.getProperty("AF_POSTGRES_ADDR") != null) && (environment.getProperty("AF_POSTGRES_PORT").length() > 0)){
			db_url = "jdbc:postgresql://" + environment.getProperty("AF_POSTGRES_ADDR") + ":" + environment.getProperty("AF_POSTGRES_PORT") + "/ontic?searchpath=ontic";
		}
		logger.info("DatabaseConfig::dataSourceAf db.url {}", db_url);
 		
		return createBasicDataSource(db_url,
				environment.getProperty("db.driver"),
				username,
				password);
    }
	
	 
	protected BasicDataSource createBasicDataSource(String jdbcUrl, String driverClass, String userName, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }


    private int getServiceIndex(String serviceName,String props) {
        int i=0;
        while(JsonPath.read(props, "$.user-provided["+i+"]")!=null){
            if(JsonPath.read(props, "$.user-provided["+i+"].name").equals(serviceName))break;
            i++;
        }
        return i;
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
