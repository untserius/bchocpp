package com.axxera.ocpp.webSocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.DiagnosticsStatusNotification;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.FirmwareStatusNotification;
import com.axxera.ocpp.message.SecurityEventNotification;
import com.axxera.ocpp.message.SignCertificate;
import com.axxera.ocpp.message.SignedFirmwareStatusNotification;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StatusSendingDataService;

@Service
public class FirmwareStatusNotificationService {
	
	@Autowired
	private LoggerUtil customeLogger;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;
	
	@Autowired
	private Utils Utils;
	
	public void updateFirmwareStatusNotification(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessions,
			WebSocketSession session,Object requestMessage,long stationId) {
		String uniqueID = finalData.getSecondValue();
		String msg = "[3,\"" + uniqueID + "\",{}]";
		try {
			FirmwareStatusNotification firmStatus = finalData.getFirmwareStatusNotification();
			
			customeLogger.info(stationRefNum, "Inside the the UpdateFirmWare Response : "+msg);
			
			Utils.chargerMessage(session, msg,stationRefNum);
		}catch (Exception e) {
			e.printStackTrace();
		}
	    try {
			esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","FirmwareStatusNotification",String.valueOf(requestMessage),stationRefNum,msg,"Accepted",stationId,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateDiagnosticsStatusNotification(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessionswithstations, 
			WebSocketSession session,Object requestMessage,long stationId) {
		String msg = "";
		try {
			DiagnosticsStatusNotification diagnosticsStatusNotification = finalData.getDiagnosticsStatusNotification();

			String uniqueID = finalData.getSecondValue();
			
			msg = "[3,\"" + uniqueID + "\",{}]";
			
			customeLogger.info(stationRefNum, "Inside the the diagnosticsStatusNotification Response : "+msg);
			
			Utils.chargerMessage(session, msg,stationRefNum);
	    	
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","DiagnosticsStatusNotification",String.valueOf(requestMessage),stationRefNum,msg,"Accepted",stationId,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSignCertificate(FinalData finalData, String stationRefNum,
			Map<String, WebSocketSession> sessionswithstations, WebSocketSession session,Object requestMessage,long stationId) {
		String msg = "";
		try {
			SignCertificate sc = finalData.getSignCertificate();

			String uniqueID = finalData.getSecondValue();
			
			msg = "[3,\"" + uniqueID + "\",{\"status\":\"Accepted\"}]";
			
			customeLogger.info(stationRefNum, "Inside the the SignCertificate Response : "+msg);
			
			Utils.chargerMessage(session, msg,stationRefNum);
		    
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","SignCertificate",String.valueOf(requestMessage),stationRefNum,msg,"Accepted",stationId,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSecurityEventNotification(FinalData finalData, String stationRefNum,Map<String, WebSocketSession> sessionswithstations,
			WebSocketSession session,Object requestMessage,long stationId) {
		String msg = "";
		try {
			SecurityEventNotification sen = finalData.getSecurityEventNotification();

			String uniqueID = finalData.getSecondValue();
			
			msg = "[3,\"" + uniqueID + "\",{}]";
			
			customeLogger.info(stationRefNum, "Inside the the SecurityEventNotification Response : "+msg);
			
			Utils.chargerMessage(session, msg,stationRefNum);
	    	
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","SecurityEventNotification",String.valueOf(requestMessage),stationRefNum,msg,"Accepted",stationId,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLogStatusNotification(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessionswithstations, WebSocketSession session,
			 Object requestMessage,long stationId) {
		String msg = "";
		try {
			SecurityEventNotification sen = finalData.getSecurityEventNotification();

			String uniqueID = finalData.getSecondValue();
			
			msg = "[3,\"" + uniqueID + "\",{}]";
			
			customeLogger.info(stationRefNum, "Inside the the LogStatusNotification Response : "+msg);
			
			Utils.chargerMessage(session, msg,stationRefNum);
	    	
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","LogStatusNotification",String.valueOf(requestMessage),stationRefNum,msg,"Accepted",stationId,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSignedFirmwareStatusNotification(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessionswithstations,
			WebSocketSession session,Object requestMessage,long stationId) {
		String msg = "";
		try {
			SignedFirmwareStatusNotification sfsn = finalData.getSignedFirmwareStatusNotification();

			String uniqueID = finalData.getSecondValue();
			
			msg = "[3,\"" + uniqueID + "\",{}]";
			
			customeLogger.info(stationRefNum, "Inside the the SignedFirmwareStatusNotification Response : "+msg);
			
			Utils.chargerMessage(session, msg,stationRefNum);
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","SignedFirmwareStatusNotification",String.valueOf(requestMessage),stationRefNum,msg,"Accepted",stationId,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
