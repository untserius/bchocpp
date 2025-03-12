package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.service.chargerAuthenticationService;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.webSocket.service.StationService;


@Service
public class chargerAuthenticationServiceImpl implements chargerAuthenticationService{
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Override
	public boolean headerValidation(String clientId,String clientAuthKey)  {
		try {
			List<Map<String, Object>> stnAuth = stationService.getstnAuth(clientId);
			if(stnAuth != null && stnAuth.size() > 0) {
				String password = String.valueOf(stnAuth.get(0).get("password"));
				String profileName = String.valueOf(stnAuth.get(0).get("profileName"));
				String userName = String.valueOf(stnAuth.get(0).get("userName"));
				String connectionType = String.valueOf(stnAuth.get(0).get("connectionType"));
				
				String clientUsername = "";
				String clientPassword = "";
				
				clientAuthKey = new String(Base64.decodeBase64(clientAuthKey.replace("Basic ", "")));
				
				String[] clientCredentials = clientAuthKey.split(":", 2);
				if(clientCredentials.length >= 2) {
					clientUsername = clientCredentials[0];
					clientPassword = clientCredentials[1];
				}
				
				customLogger.info(clientId, "security profileName : "+profileName);
				if(profileName.equalsIgnoreCase("None")) {
					customLogger.info(clientId, "Authenticated");
					return true;
				}else if(profileName.equalsIgnoreCase("Profile1")) {
					if(userName.equalsIgnoreCase(clientUsername) && password.equalsIgnoreCase(clientPassword)) {
						customLogger.info(clientId, "Profile1 : Authenticated");
						return true;
					}else {
						customLogger.info(clientId, "Profile1 : Authentication failed");
						return false;
					}
				}else if (profileName.equalsIgnoreCase("Profile2")) {
					if(userName.equalsIgnoreCase(clientUsername) && password.equalsIgnoreCase(clientPassword)) {
						customLogger.info(clientId, "Profile2 : Authenticated");
						return true;
					}else {
						customLogger.info(clientId, "Profile2 : Authentication failed");
						return false;
					}
				}else if(profileName.equalsIgnoreCase("Profile3")) {
					customLogger.info(clientId, "Profile3 : Authenticated");
					return true;
				}else {
					return true;
				}
			}else {
				return true;
			}			
		}catch(Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
}