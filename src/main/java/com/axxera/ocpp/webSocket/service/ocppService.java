package com.axxera.ocpp.webSocket.service;

import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.rest.message.ResponseMessage;

public interface ocppService {

	ResponseMessage features(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);
	
	ResponseMessage appFeatures(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage featuresRemoteSocketController(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage ocpiFeatures(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	ResponseMessage changeAvailability(OCPPForm of, Map<String, WebSocketSession> sessionswithstations);

	void fcm(OCPPForm ocppForm);

	void fcmStop();

}
