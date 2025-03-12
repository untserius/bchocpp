package com.axxera.ocpp.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.config.FCM;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;

@Service
public class PushNotification {

	final String apiURL = "https://fcm.googleapis.com/v1/projects/bc-hydro-e89b3/messages:send";
	
	@Autowired
	private LoggerUtil customeLogger;
	
	@Value("${google.service}")
	private String googleService;
	
	@Autowired
	private FCM fcm;
	
	static Logger logger = LoggerFactory.getLogger(PushNotification.class);
	
	@SuppressWarnings("unchecked")
	public void pushNotificationForIosAndroid(String recipients,String deviceName,JSONObject msgInfo,String stationRefNum,String appKey,String type) {
		try{
			//String serverKey = deviceName == null ? androidServerKey : deviceName.equalsIgnoreCase("Android") ? androidServerKey : deviceName.equalsIgnoreCase("ios") ? iosServerKey : iosServerKey;
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + appKey);
			conn.setRequestProperty("Content-Type","application/json");
			JSONObject json = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("token", recipients);
			message.put("data", msgInfo);
			if(deviceName.equalsIgnoreCase("Android")) {
				message.put("data", msgInfo);
			}else {
				message.put("data",msgInfo);
				JSONObject apns = new JSONObject();
				JSONObject payload = new JSONObject();
				JSONObject aps = new JSONObject();
				JSONObject alert = new JSONObject();
				alert.put("title", "BC Hydro");
				alert.put("body", "");
				aps.put("alert", alert);
				aps.put("mutable-content", 1);
				aps.put("content-available", 1);
				aps.put("sound", "default");
				payload.put("aps", aps);
				apns.put("payload", payload);
				message.put("apns", apns);
			}
			json.put("message", message);
			//customeLogger.info(stationRefNum, "PushNotification json : "+json);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(String.valueOf(json));
			wr.flush();
		
			int responseCode = conn.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			if (responseCode == 200) {
				customeLogger.info(stationRefNum, "PushNotification -  [Notification sent successfully] : "+type);
			}
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendMulticastMessage(Map<String, String> data,List<String> deviceTokens){
 
		try {
			AndroidConfig androidConfig = fcm.android();
			ApnsConfig apnsConfig = fcm.apns();
			MulticastMessage multiCast = MulticastMessage.builder().addAllTokens(deviceTokens).putAllData(data)
					.setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).build();
			List<SendResponse> responses = FirebaseMessaging.getInstance().sendEachForMulticast(multiCast).getResponses();
			if(responses != null) {
				FirebaseMessagingException exception = responses.get(0).getException();
				if(exception != null && (String.valueOf(exception.getMessagingErrorCode()).equalsIgnoreCase("InvalidRegistration") || String.valueOf(exception.getMessagingErrorCode()).equalsIgnoreCase("MismatchSenderId"))) {
					fcm.initialize();
				}
			}
		}catch(Exception e) {
			logger.error("",e);
		}
	
 
	}
}
