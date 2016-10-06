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
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.ontic.pgf.mitigation.interfaces.Plan;
import com.ontic.pgf.mitigation.interfaces.PlanPolicies;
import com.ontic.pgf.mitigation.interfaces.Policy;
import com.jcraft.jsch.ChannelExec;



public class PGFMitigationImpl implements PGFMitigation{
	
	private PGFConfiguration configuration;
	
	@Autowired
    RabbitTemplate rabbitTemplate;
	
		
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public PGFMitigationImpl(PGFConfiguration config )
	{
		configuration=config;
	}
	public  boolean executePlans(List <Plan>plans){
		Iterator <Plan> itr = plans.iterator();
    	 while(itr.hasNext())
    	 {
    	   Plan plan=itr.next();
    	  
    	   log.info("Executing plan_id:"+plan.getPlan_id());
    	   List<String> locations=plan.getLocations();
    	   PlanPolicies plan_policies= plan.getPlan();
    	   List<Policy> policies = plan_policies.getPolicies();
    	   String location_list="";
    	   if(locations != null)
    	   {
    		   Iterator <String> loc = locations.iterator();
    		   
    		   while(loc.hasNext())
    	    	 {
    			  
    			   String location=loc.next();
    			   log.info("Applying policy at location:"+configuration.getLocationName(location));
    			   location_list=location_list+ configuration.getLocationName(location)+",";
    			   if(location != null)
    				   changeNetworkParams(location);
    			 
    	    	 }
    	   }else{
    		   log.info("Locations is null");
    	   }
    	 location_list=location_list.substring(0, location_list.lastIndexOf(","));
    	 java.util.Date time= new java.util.Date();
      	 String mensaje="Mitigation plan:"+plan.getPlan_id()+". Policy:"+(policies.get(0)).getPolicy()+" .Locations:"+location_list;
      	 String json_message="{\"timestamp\":\""+time.toString()+"\",\"notification\":\""+mensaje+"\"}";
      	 sendMessageMQ(json_message);
      	 log.info(json_message);
    		   
    	  }
    	 
    	
    
    	 
		
		return true;
    	
	}
	
	private  boolean changeNetworkParams(String location) 
	{
		
	    String host= configuration.getLocationHost(location);
	    String user= configuration.getSshUser();
	    String pass= configuration.getSshPass();
	    
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
	
	    
	    public void sendMessageMQ(String message)
	    {
	    	
	    	rabbitTemplate.convertAndSend( configuration.getRabbit_exchange(), configuration.getRabbit_routingkey(), message.getBytes());
	   
	    }

}
