package com.axxera.ocpp.webSocket.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.model.ocpp.ChargerDefaultConfiguration;

public interface StatusDefaultConfigService {

	void getCompareDataDcc(FinalData data, List<ChargerDefaultConfiguration> getdatafromCDC, WebSocketSession session, String stationRefNum);

	

}
