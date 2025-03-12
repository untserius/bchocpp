package com.axxera.ocpp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PGEMessageService {

	static Logger logger = LoggerFactory.getLogger(PGEMessageService.class);

	public static String getNotification(String URL) {

		logger.info("PGEMessageService.getNotification() -  []");
		String output = "";
		String response = "";

		try {
			URL url = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			if (conn.getResponseCode() != 200)
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			output = br.readLine();
			if (output != null) {

				logger.info("PGEMessageService.getNotification() -output  [" + output + "]");

				response = response + output;

				if (!response.equalsIgnoreCase("Accepted"))
					response = "Rejected";

				conn.disconnect();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
