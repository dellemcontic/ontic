package com.emc.ontic.dashboard;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class AbstractWriter implements Writer{
	
	private static final int SIZE = 10;
	
	private String _file = null;
	
	public void setFile(String value)
	{
		_file = value;
	}
	
	public String getFile()
	{
		return _file;
	}
	
	public FlowsMessage writeToFile(String message) throws IOException{
		FileWriter file = null;
		FlowsMessage flows = new FlowsMessage();

		try {
			JSONObject obj = new JSONObject(new String(message));
		
			JSONParser parser = new JSONParser();
				
			JSONArray array = (JSONArray) parser.parse(new FileReader(getFile()));
			while(array.size() >= SIZE){
				array.remove(array.size() - SIZE);
			}
			
			array.add(obj);
			file = new FileWriter(getFile()); 
			
			file.write(array.toJSONString());
		} catch (JSONException e) {
			System.out.println("Error reading the json ... " + e.toString());
		} catch (ParseException e) {
			System.out.println("Error parsing the json file (array)... " + e.toString());
		} finally{
			if(file != null){
				file.close();
			}
		}
		return flows;
	}
}