package com.pivotal.tstat.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.messaging.Message;

import com.pivotal.tstat.classification.Classification;
import com.pivotal.tstat.classification.Classifier;
import com.pivotal.tstat.tcp.TstatException;

public class LocalFileSystemClassificationWriter  extends AbstractWriter {
	
	private Classifier predictor=null;
	
	
	public Classifier getPredictor() {
		return predictor;
	}



	public void setPredictor(Classifier predictor) {
		this.predictor = predictor;
	}



	@Override
	public void write(Message<byte[]> message) throws TstatException 
	{		
	
		BufferedWriter bufferWritter = null;
		
		try {
	        String destinationFile = getDestinationFileName(message);
	        System.out.println("Escribiendo en:"+destinationFile);
	        File file = new File(destinationFile);
	        // while (file.exists())
	        //{
	        //	destinationFile = getDestinationFileName(message);
	        //	file = new File(destinationFile);
	        //}
	        
	        if(!file.exists()){
    			file.createNewFile();
    		}
    		
    		//true = append file
    		FileWriter fileWritter = new FileWriter(destinationFile,true);
    	    bufferWritter = new BufferedWriter(fileWritter);
    	    String mensaje=new String((byte[])message.getPayload());
    	    if (mensaje.length() > 10){
    	 
	    	    
	    	    String[] vectores = mensaje.split("\\n");
	    	    for (String vector:vectores)
	    	    {
	    	    
	    	     Classification prediction = predictor.predict(vector);  	   
	    	     bufferWritter.write(prediction.getFlow()+"#"+prediction.getPrediction());
	    	     bufferWritter.newLine();
	    	    }
    	     
    	     
    	   }
    	    
       
    	  
    	    fileWritter.flush();
    	    
        
            //out = new FileOutputStream(file);
            //out.write((byte[])message.getPayload());
        } 
        catch (IOException e)
        {
        	System.out.println("Error:"+e.getMessage());
        	throw new TstatException(e.getMessage());
        }
        finally 
        {
            if (bufferWritter != null) 
            {
                try 
                {
					//out.close();
                	bufferWritter.close();
				} 
                catch (IOException e) 
                {
				}
            }
        }
    }

	
	
}
