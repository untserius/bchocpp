package com.axxera.ocpp.webSocket.service;

import java.math.BigDecimal;
import java.util.Map;

import com.axxera.ocpp.message.SessionImportedValues;
import com.axxera.ocpp.model.ocpp.OCPPSessionsData;

public interface sessionDataService {

	void insertingSessionData(SessionImportedValues sessionImportedValues,OCPPSessionsData sessData,BigDecimal promoCodeUsedTime);

	long getSessionWattsUsage(String sessionId,double currentMeterReading,long stationId,long portId);

	OCPPSessionsData getSessionData(String sessionId);

	boolean getsuccessFlags(BigDecimal totalkWh,BigDecimal sessionElapsedMins,long orgId,String stationRefNum, String revenue,String sessionId,String siteCurrency,long stationId);

	void chargingSessionsSummaryMail(Map<String, Object> map);
	
}
