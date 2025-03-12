package com.axxera.ocpp.service;

import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.OptimizationsForm;


public interface AmpControllerService {

	void sendOptimizationForEVG(OptimizationsForm optimizationData, Map<String, WebSocketSession> sessionswithstations);

}
