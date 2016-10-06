package com.pivotal.tstat.tcp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.springframework.integration.file.DefaultDirectoryScanner;

public class RecursiveNewestLeafOnlyDirectoryScanner extends DefaultDirectoryScanner{
	
	private volatile String filenameRegex = null; 
	
	private Pattern pattern = null;
	
	public void setFilenameRegex(String value){
		this.filenameRegex = value;
		pattern = Pattern.compile(filenameRegex);
	}

	public String getFilenameRegex(){
		
		return this.filenameRegex;
	}
	

	protected File[] listEligibleFiles(File directory) throws IllegalArgumentException {

	
		File[] rootFiles = directory.listFiles();
		Arrays.sort(rootFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		List<File> files = new ArrayList<File>(rootFiles.length);
		for (File rootFile : rootFiles) {
			if (rootFile.isDirectory()) {
				files.addAll(Arrays.asList(listEligibleFiles(rootFile)));
			}
			else {
				
				Matcher matcher = pattern.matcher(rootFile.getName());
				if (matcher.matches()){
					files.add(rootFile);
				}
			}
		}
		int file_number=files.size();
		//return files.toArray(new File[files.size()]);
		if (file_number >= 2)
		{ //Return the file prior last file (-2)
			return new File[]{files.get(files.size()-2)}; 
		}else
		{
		   return files.toArray(new File[files.size()]);
		}
	}
}
