package com.axxera.ocpp.webSocket;

import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.FinalData;

public interface HeartbeatService {

	void heartBeatInterval(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessions,
			WebSocketSession session, long stationId,Object requestMessage);

	boolean addHeartBeat(long clientId,WebSocketSession session,String stationRefNum);

	void stationActiveRecords(long stationId, String referNo);

}
