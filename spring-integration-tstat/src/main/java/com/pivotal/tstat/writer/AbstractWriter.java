package com.pivotal.tstat.writer;

import java.io.File;
import java.util.UUID;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public abstract class AbstractWriter implements Writer{
	
	private String _basePath = null;
	
	private String _baseFileName = null;
	
	private String _fileSuffix = null;
	
	public void setBasePath(String value)
	{
		_basePath = value;
	}
	
	public String getBasePath()
	{
		return _basePath;
	}
	
	public void setBaseFileName(String value)
	{
		_baseFileName = value;
	}
	
	public String getBaseFileName()
	{
		return _baseFileName;
	}
	
	public void setFileSuffix(String value)
	{
		_fileSuffix = value;
	}
	
	public String getFileSuffix()
	{
		return _fileSuffix;
	}
	
	protected String getDestinationFileName(Message<byte[]> message)
	{
		MessageHeaders headers= message.getHeaders();
		String filename= (String)headers.get("TSTAT_TIME");
		
		return getBasePath () + File.separator + getBaseFileName() + "_" +filename+ "." + getFileSuffix();
	}
}
