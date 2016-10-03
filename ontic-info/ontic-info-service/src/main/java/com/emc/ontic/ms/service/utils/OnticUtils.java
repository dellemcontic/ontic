package com.emc.ontic.ms.service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OnticUtils {
	
	public static String getConvertedDate(Date dateTime) {

	    String formattedDate = null;
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	    if (dateTime != null) {
	      formattedDate = format.format(dateTime);
	    }

	    return formattedDate;
	  }

}
