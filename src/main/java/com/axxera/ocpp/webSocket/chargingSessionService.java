package com.axxera.ocpp.webSocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;

@Service
public class chargingSessionService {
	
	@Autowired
	private Utils Utils;
	
	@Autowired
	private LoggerUtil customLogger;
	
	@SuppressWarnings("unused")
	public void meterVal(FinalData finalData, String stationRefNum,Map<String, WebSocketSession> sessions,WebSocketSession session, Object requestMessage,long stnId)  {
		
		try {
			Utils.transactionCalling(String.valueOf(requestMessage),stationRefNum,"MeterValue",stnId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
			String uniqueId = finalData.getSecondValue();
			String response = "[3,\"" + uniqueId + "\",{}]";
			Utils.chargerMessage(session, response,stationRefNum);
			customLogger.info(stationRefNum, "Response Message : "+response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
