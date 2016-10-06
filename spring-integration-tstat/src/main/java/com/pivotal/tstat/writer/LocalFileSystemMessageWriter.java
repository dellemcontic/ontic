package com.pivotal.tstat.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.messaging.Message;

import com.pivotal.tstat.tcp.TstatException;

public class LocalFileSystemMessageWriter extends AbstractWriter{
	
	@Override
	public void write(Message<byte[]> message) throws TstatException 
	{		
		OutputStream out = null;
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
    	    bufferWritter.write(new String((byte[])message.getPayload()));
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
