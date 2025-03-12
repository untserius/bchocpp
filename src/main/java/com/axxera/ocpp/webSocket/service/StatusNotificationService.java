package com.axxera.ocpp.webSocket.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.model.ocpp.NotifyMe;



public interface StatusNotificationService {

	public  String getStatus(long stationId, long connectorId) ;
	
	public  List<NotifyMe> getNotifications(long clientId) ;
	
	public void sendStationNotifications(Long stationId,String message,String NotifyType,String stnRefNum,String stationName) ;
	
	public void updateOcppStatusNotification(String Status,Long stationId,Long connectorId,boolean ampFlag);

	public void sendNotificationForPortStatus(String string, String string2, String stationRefNum,
			String intRandomNumber, long stationId, long id,long siteId);

	void statusNotification(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessions,
			WebSocketSession session, Object requestMessage, Long stationId);

}
