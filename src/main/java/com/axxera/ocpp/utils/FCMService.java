package com.axxera.ocpp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FCMService {
	static Logger logger = LoggerFactory.getLogger(FCMService.class);

	public static float finalcostwithtwodecimals(float final_Cost) {
		try {
			logger.info("FCMService.finalcostwithtwodecimals() - [ " + final_Cost + " ]");
			String finalcostString = String.valueOf(final_Cost);
			String finalcostbeforedecimals = finalcostString.split("\\.")[0];
			String finalcostafterdecimals = finalcostString.substring(finalcostString.indexOf(".")).substring(1, 3);
			String finalcoststringcombined = finalcostbeforedecimals + "." + finalcostafterdecimals;
			final_Cost = Float.parseFloat(finalcoststringcombined);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return final_Cost;
	}

	public static String utcTime() {
		String finalUtctime = null;
		try {
			Date date = new Date();
			logger.info("FCMService.utcTime() - [ " + date + " ]");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			String utctime = dateFormat.format(date);
			finalUtctime = utctime + "Z";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return finalUtctime;
	}
}
