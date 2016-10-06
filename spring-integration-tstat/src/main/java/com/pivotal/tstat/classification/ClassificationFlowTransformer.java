package com.pivotal.tstat.classification;

import java.net.Inet4Address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import com.pivotal.tstat.ingester.IngesterServer;
import com.pivotal.tstat.ingester.MessageTransformer;
import com.pivotal.tstat.tcp.TstatException;

public class ClassificationFlowTransformer implements MessageTransformer{
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationFlowTransformer.class);

     private Classifier classifier=null;
	
	
	

	public Classifier getClassifier() {
		return classifier;
	}




	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}




	@Override
	public Message<byte[]> transform(Message<?> message) throws TstatException {
		
	
		String mensaje=new String((byte[])message.getPayload());
		MessageHeaders headers= message.getHeaders();
		String ip= (String)headers.get("TSTAT_IP");
		int total=0;
		double good=0;
		double medium=0;
		double bad=0;
	
		
		if (mensaje.length() > 10)
		{
	    	 
    	    String[] vectores = mensaje.split("\\n");
    	    
    	    for (String vector:vectores)
    	    {
    	     
    	    	 LOGGER.debug("Flow:"+vector);
	    	     Classification classification = classifier.predict(vector);  
	    	     
	    	     LOGGER.debug("Classification:"+classification.getPrediction());
	    	     
	    	     if ((classification.getPrediction().compareTo("good")) == 0)
	    	     {
	    	    	 good++;
	    	    	 
	    	     }else if (classification.getPrediction().compareTo("bad") == 0)
	    	     {
	    	    	 bad++;
	    	     }else
	    	     {
	    	    	 medium++;
	    	     }
	    	     total++;
    	   
    	    }
    	    
    	     	  
	   }
		
		String salida;
		if(total !=0)
		{
			salida="{\"workstations\":[{\"ip\":\""+ip+"\",\"flows\":"+total+",\"red\":"+(int) Math.round((bad/(double)total)*100)+",\"green\":"+(int) Math.round((good/(double)total)*100)+",\"orange\":"+(int) Math.round((medium/(double)total)*100)+"}]}";
		}else
		{
			salida="{\"workstations\":[{\"ip\":\""+ip+"\",\"flows\":"+total+",\"red\":"+0+",\"green\":"+0+",\"orange\":"+0+"}]}";
			
		}
		
	   LOGGER.debug(salida);
 	   Message<byte[]> message1= MessageBuilder.withPayload(salida.getBytes())
 			  .setHeader("CLASSIFICATION_TIME", System.currentTimeMillis() )
 			  .build();
 				
 	   return message1;
	}
	
	

}
