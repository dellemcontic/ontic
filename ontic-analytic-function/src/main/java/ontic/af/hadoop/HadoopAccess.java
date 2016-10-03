package ontic.af.hadoop;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ontic.af.OnticAnalyticFunctionApplication;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;



public class HadoopAccess {

	private Configuration configuration = null;
	private String basePath = null;
	
	static Path prevLastFile=null;
	private static final Logger LOGGER = LoggerFactory.getLogger(HadoopAccess.class);
	  
	public HadoopAccess(Configuration configuration)
	{
		this.configuration=configuration;
	}
	
	
	public String getBasePath() {
		return basePath;
	}


	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}



	public byte[] getLastFileContent()
	{
		 
	    FileSystem hdfs = null;
		 List<FileStatus> files = null;
		try {
			Path directory = new Path(basePath);
			hdfs = FileSystem.get(configuration);
			files = getAllFilePath(directory, hdfs);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			 e.printStackTrace();
			 return null;
		}
		
		
		FileStatus[] fileStatus=files.toArray(new FileStatus[files.size()]);
		
	    if (files.size()> 1)
	    {
		    Arrays.sort(fileStatus, new Comparator<FileStatus>(){
		        public int compare(FileStatus f1, FileStatus f2)
		        {
		            return Long.valueOf(f1.getModificationTime()).compareTo(f2.getModificationTime());
		        } });
	    }
	    
	    
	 
	   Path lastFile= fileStatus[fileStatus.length - 1].getPath() ;
     
	  
		if ( prevLastFile==null || (lastFile.toString()).compareTo(prevLastFile.toString()) !=0 )
		{
			// Read File
			  FSDataInputStream in;
			try {
				
				in = (hdfs.open(lastFile));
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				  byte[] b = new byte[1024];
				    int numBytes = 0;
				    while ((numBytes = in.read(b)) > 0) {
				    	 out.write(b, 0, numBytes);
				    }
				 prevLastFile=lastFile;
				 LOGGER.info("Analyzing:"+lastFile.getName());
				 return  out.toByteArray();
				 
			} catch (IOException e) {
				
				e.printStackTrace();
				return null;
			}
			  
		}
		else
			return null;
	}
	
	private  List<FileStatus> getAllFilePath(Path filePath, FileSystem fs) throws FileNotFoundException, IOException {
	    List< FileStatus> fileList = new ArrayList<FileStatus>();
	    FileStatus[] fileStatus = fs.listStatus(filePath);
	    for (FileStatus fileStat : fileStatus) {
	        if (fileStat.isDir()) {
	            fileList.addAll(getAllFilePath(fileStat.getPath(), fs));
	        } else {
	            fileList.add(fileStat);
	        }
	    }
	    return fileList;
	}
	
}
