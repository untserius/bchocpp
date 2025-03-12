package com.axxera.ocpp.service;

import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.FinalData;

public interface chargerCommunication {

	void startTransaction(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessionWithStations,
			WebSocketSession session, Object requestMessage);
	
	

}
