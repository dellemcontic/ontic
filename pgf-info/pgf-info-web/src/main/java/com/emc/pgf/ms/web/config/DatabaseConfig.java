package com.emc.pgf.ms.web.config;

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
        int index = getServiceIndex("HawkDB",props); //TODO: User defined service name in PCF
        String url = JsonPath.read(props, "$.user-provided["+index+"].credentials.url");
        String driver = JsonPath.read(props, "$.user-provided["+index+"].credentials.driver");
        String uname = JsonPath.read(props, "$.user-provided["+index+"].credentials.username");
        String password = JsonPath.read(props, "$.user-provided["+index+"].credentials.password");
        return createBasicDataSource(url,driver,uname,password);
    }
	

	@Profile("local")
	@Bean
    public DataSource dataSourcePgf() {
		logger.info("DatabaseConfig::dataSourceAf db.driver {}", environment.getProperty("db.driver"));
		logger.info("DatabaseConfig::dataSourceAf db.username {}", environment.getProperty("db.username"));
		logger.info("DatabaseConfig::dataSourceAf db.password {}", environment.getProperty("db.password"));
		logger.info("DatabaseConfig::dataSourceAf DB_NAME {}", environment.getProperty("DB_NAME"));
		logger.info("DatabaseConfig::dataSourceAf DB_PORT_5432_TCP_PORT {}", environment.getProperty("DB_PORT_5432_TCP_PORT"));
		logger.info("DatabaseConfig::dataSourceAf DB_PORT_5432_TCP_ADDR {}", environment.getProperty("DB_PORT_5432_TCP_ADDR"));
		
		String db_url = environment.getProperty("db.url");
		if ((environment.getProperty("DB_PORT_5432_TCP_PORT") != null) && (environment.getProperty("DB_PORT_5432_TCP_PORT").length() > 0)){
			db_url = "jdbc:postgresql://" + environment.getProperty("DB_PORT_5432_TCP_ADDR") + ":" + environment.getProperty("DB_PORT_5432_TCP_PORT") + "/ontic?searchpath=ontic";
		}
		logger.info("DatabaseConfig::dataSourceAf db.url {}", db_url);
 		
		return createBasicDataSource(db_url,
				environment.getProperty("db.driver"),
				environment.getProperty("db.username"),
				environment.getProperty("db.password"));
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
}
