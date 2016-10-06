package com.pivotal.tstat.writer;


import java.io.IOException;
import org.springframework.messaging.Message;
import com.pivotal.tstat.tcp.TstatException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdfsMessageWriter extends AbstractWriter{
	
	private FileSystem _fileSystem;
	
	
	public HdfsMessageWriter(Configuration configuration) throws TstatException
	{
		
		//Configuration configuration = new Configuration();
		configuration.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		configuration.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		configuration.set("dfs.replication", "1");
		//configuration.set("dfs.support.append", "true");
		
		try {
			_fileSystem = FileSystem.get(configuration);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TstatException(e.getMessage());
		}
	}
	
	public FileSystem getFileSystem()
	{
	     
		return _fileSystem;
	}
		
	@Override
	public void write(Message<byte[]> message) throws TstatException 
	{	
		   
		
		FSDataOutputStream fsDataOutputStream = null;
		Path name = null;
		
		byte[] payload=(byte[])message.getPayload();
		try {
		 if (payload.length > 0)
		 {
			
				
				String path=getDestinationFileName(message);
				name = new Path(path);
				System.out.println("URI:"+this.getFileSystem().getUri().toString());
				System.out.println(message);
				System.out.println(name);
				
				if (!this.getFileSystem().exists(name))
				{
					
					fsDataOutputStream = this.getFileSystem().create(name);
					
				} else
				{
					
					fsDataOutputStream = this.getFileSystem().append(name);
				}
				fsDataOutputStream.write(payload);
			
		 	}
        } 
        catch (IOException e)
        {
        
        	System.out.println(e.getMessage());
          	System.out.println(e.toString());
        	throw new TstatException(e.getMessage());
        }
		catch (Exception e)
		{
			
			System.out.println(e.getMessage());
			System.out.println(e.toString());
			throw new TstatException(e.getMessage());	
		}
        finally 
        {
        	IOUtils.closeStream(fsDataOutputStream);
        }
    }
}
