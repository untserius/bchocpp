package com.axxera.ocpp.webSocket.service;

public interface OCPPActiveSessionService {

	public boolean insertActiceSession(String sessionId, String status, String stationRefNum,String uri) ;

	public boolean deleteActiveSession(String stationRefNum,String uri) ;

	void updateActiveTransaction(String stationRefNum);

}
