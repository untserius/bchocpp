package com.axxera.ocpp.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class LoggerUtil {

	@Value("${file.logsLocation}")
	private String logsLocation;
	
	@Value("${file.sessionlogsLocation}")
	private String sessionlogsLocation;

	public void info(String stationId, Object message) {
		try {
			Calendar c = Calendar.getInstance();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss.sss 'UTC' yyyy");
			
	   
			File folderPathObj = null;
			BufferedWriter writer = null;
			
			message = dateFormat2.format(c.getTime())+"\t" + message;
			folderPathObj = new File(logsLocation+stationId);
			if (!folderPathObj.exists()) {
				folderPathObj.mkdirs();
			}
			writer = new BufferedWriter(new FileWriter(
					folderPathObj.getAbsolutePath() + "/" + dateFormat.format(c.getTime()) + "_" + stationId + ".txt",
					true) // Set true for append mode
			);
			writer.newLine(); // Add new line
			writer.write("\n" + String.valueOf(message));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void sessionlog(Object sessionid,String stationId, Object request,Object response) {
		try {
			File folderPathObj = null;
			BufferedWriter writer = null;
		
			folderPathObj = new File(sessionlogsLocation + stationId + "/" + sessionid);
			if (!folderPathObj.exists()) {
				folderPathObj.mkdirs();
			}
			writer = new BufferedWriter(new FileWriter(folderPathObj.getAbsolutePath()  +"/"+sessionid+ ".txt",true) // Set true for append mode
			);
			writer.newLine(); // Add new line
			writer.newLine(); // Add new line
			writer.write("\n" + "Request : "+String.valueOf( request));
			writer.write("\n" + "Response : "+String.valueOf( response));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
