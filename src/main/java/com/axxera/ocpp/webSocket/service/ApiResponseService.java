package com.axxera.ocpp.webSocket.service;

import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.rest.message.ResponseMessage;

public interface ApiResponseService {

	ResponseMessage reset(OCPPForm of,Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage changeAvailability(OCPPForm of,Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage changeConfiguration(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage clearChargingProfile(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage setChargingProfile(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage custom(OCPPForm of,Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage dataTransfer(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage sendLocalList(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage cancelReservation(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage triggerMessage(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage reserveNow(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage clearCache(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage updateFirmWare(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage unlockConnector(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage getConfiguration(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage getDiagnostics(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage getLocalListVersion(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage remoteStart(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage remoteStopTransaction(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) ;

	ResponseMessage remoteStartWithSmartCharging(OCPPForm of,Map<String, WebSocketSession> sessionswithstations);
	
	ResponseMessage getCompositeSchedule(OCPPForm of,Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage vendingPriceUpdate(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage remoteStartOCPI(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage remoteStopOCPI(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage remoteStartPowerSharging(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage remoteStopPowerSharing(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage defaultPrice(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

}
