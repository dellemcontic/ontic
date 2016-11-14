package com.ontic.pgf.mitigation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.ontic.pgf.mitigation.interfaces.Parameter;
import com.ontic.pgf.mitigation.interfaces.Plan;
import com.ontic.pgf.mitigation.interfaces.PlanPolicies;
import com.ontic.pgf.mitigation.interfaces.Policy;

import com.ontic.pgf.mitigation.db.entity.Location;
import com.ontic.pgf.mitigation.db.repository.LocationRepository;

import com.jcraft.jsch.ChannelExec;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement

public class PGFMitigationImpl implements PGFMitigation{
	
	@Value("${rabbitmq.exchange}")
	private String rabbit_exchange;
	@Value("${rabbitmq.routingkey}")
	private String rabbit_routingkey;
	
	private String sshUser;
	private String sshPass;
	

	
	@Autowired
	LocationRepository locationRepo;
	
	@Autowired
    RabbitTemplate rabbitTemplate;
	
		
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	

	
	
	public  boolean executePlans(List <Plan>plans){
	 try {
		Iterator <Plan> itr = plans.iterator();
    	 while(itr.hasNext())
    	 {
    	   Plan plan=itr.next();
    	  
    	   log.info("Executing plan_id:"+plan.getPlan_id());
    	   List<String> locations=plan.getLocations();
    	   PlanPolicies plan_policies= plan.getPlan();
    	   List<Policy> policies = plan_policies.getPolicies();
 
    	   if(locations != null)
    	   {
    		   Iterator <String> loc = locations.iterator();
    		   
    		   while(loc.hasNext())
    	    	 {
    			  
    			   String location=loc.next();
    			   String cell_name=getLocation(location);
    			   if(cell_name!=null)
    			   {
    				   Iterator <Policy> policies_iterator = policies.iterator();
    				   while(policies_iterator.hasNext())
    				   {
    					   Policy policy= policies_iterator.next();
    					   String policy_name=policy.getPolicy();
    					   String policy_group=policy.getGroup();
    					   Parameter policy_param =policy.getParameters();
    					   
    					   if(policy_name.compareTo("bandwidth_throttling")==0)
    					   {
    						   log.info("Applying policy "+ policy_name+" to group "+policy_group+" in cell "+ cell_name);
    						   int limit =policy_param.getLimit();
    						   String routing_location= "actuator_"+policy_group+"_"+cell_name.toLowerCase();
    						   int upload_speed=limit;
    		    			   int download_speed =limit;
    		    			   String message="{\"upload_speed\":"+upload_speed+",\"download_speed\":"+download_speed+"}";
    		    			   log.info("Actuator:"+routing_location+".Mitigation order:"+message);
    		    			   sendMessageMQ(message,routing_location+"_routing");
    		    			   // Web message
    		    			   java.util.Date time= new java.util.Date();
    		    		       String mensaje="Mitigation plan:"+plan.getPlan_id()+". Cell:"+cell_name+" .Group:"+policy_group+" .Policy:"+policy_name+" Limit:"+limit;
    		    		       String json_message="{\"timestamp\":\""+time.toString()+"\",\"notification\":\""+mensaje+"\"}";
    		    		       sendMessageMQ(json_message,getRabbit_routingkey());
    		    		       log.info("Web message: "+json_message);
    						   
    					   }
    					   
    				   }
				   }else
    			   {
    				   log.error("Cell name:"+cell_name+"doesnt exist in DB");
    				   return false;
    			   }
    			 
    	    	 }
    	   }else{
    		   log.error("Locations is null");
    	   }
    	
    	  }
	 }catch (Exception e)
	 {
		 log.error(e.toString());
	 }
    	 
  
		return true;
    	
	}
	
	String getLocation(String loc)
	{
		
		List<Location> locations=  locationRepo.findAll();
	
		
		for (int i=0;i<locations.size();i++)
		{   
			Location location= locations.get(i);
		
			if(location.getTechnology() != null )
			{
				
					if((location.getTechnology()).compareTo(loc)==0)
					{
						return location.getCell_name();
					}
							
			} 
		}
		
	  return null;		
		
	}
	private  boolean changeNetworkParams(String ip_location) 
	{
		
	    String host= ip_location;
	    String user= getSshUser();
	    String pass= getSshPass();
	    
		return cmdExecSSH(host, user, pass, "/sbin/wondershaper clear eth0" );
		
	}
	
	private boolean cmdExecSSH(String host, String user, String password, String cmd ){
		String s = null;
		Channel channel=null;
		Session session=null;
      
		log.debug("SSH. Conecting with host:"+host+"....");
		try{

		 
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();
		session=jsch.getSession(user, host, 22);

		session.setPassword(password);

		session.setConfig(config);

		session.connect();

		log.info("Connected "+user+"@"+ host);

		 

		channel=session.openChannel("exec");

		((ChannelExec)channel).setCommand(cmd);

		channel.setInputStream(null);

		((ChannelExec)channel).setErrStream(System.err);
		InputStream in=channel.getInputStream();

		channel.connect();

		byte[] tmp=new byte[1024];

		while(true)
		{

			while(in.available()>0)
			{

					int i=in.read(tmp, 0, 1024);

					if(i<0)break;

					String line = new String(tmp, 0, i);

					s = (s == null)?line:(s+"\n"+line);

			}

			if(channel.isClosed())

				break;

			try{Thread.sleep(1000);}catch(Exception ee){}

		}

		
      
		log.info("Ejecutado["+host+"]: "+cmd);
		log.info("Resultado["+host+"]: "+s);
	
		

		} catch (JSchException jse) {
            System.out.println("Jsch failure during running " + cmd +"."+ jse.toString());
            return false;
      } catch (IOException ex) {
    	  System.out.println("IO failure during running " + cmd);
    	      return false;
      } finally {
    	   if(channel != null)
        	channel.disconnect();
    	   if(session != null)
    		session.disconnect();
      }
		

		return (true);

		}

	


	 

	    public static String generateRandomString(int length) {
		  Random random = new Random((new Date()).getTime());
	      char[] values = {'A','B','C','D','E','F','G','H','I','J',
	               'K','L','M','N','O','P','Q','R','S','T',
	               'U','V','W','X','Y','Z','0','1','2','3',
	               '4','5','6','7','8','9'};

	      String out = "";

	      for (int i=0;i<length;i++) {
	          int idx=random.nextInt(values.length);
	          out += values[idx];
	      }
	      return out;
	    }
	
	    
	    public void sendMessageMQ(String message,String routing_key)
	    {
	    	try{
	    		rabbitTemplate.convertAndSend( getRabbit_exchange(),routing_key, message.getBytes());
	    	}catch (Exception e)
	    	{
	    		log.error(e.toString());
	    	}
	   
	    }
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
