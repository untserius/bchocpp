package com.axxera.ocpp.handler;

import java.net.InetAddress;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.axxera.ocpp.service.chargerAuthenticationService;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.webSocket.service.StationService;

@Component
public class WebSocketClientInterceptor implements HandshakeInterceptor {
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private chargerAuthenticationService chargerAuthenticationService;
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes)  {
		String clientUrl = request.getURI().getPath();
		String clientId = clientUrl.substring(clientUrl.lastIndexOf('/') + 1);
		try{
			String ipAddress = request.getRemoteAddress().getAddress().getHostAddress();
			//If Client Request Form with LocalHost Instead of Ip Address Of System InetAddress currentSystemIpAddress=InetAddress.getLocalHost();
			attributes.put("clientId", clientId);
			attributes.put("ipAddress", ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1") ? InetAddress.getLocalHost().getHostAddress() : ipAddress);
			Date date = new Date();
			attributes.put("connectedTime", date);
			customLogger.info(clientId, "Headers : "+request.getHeaders()+" , URI : "+request.getURI());
			
			long stationId = stationService.getStationUniqId(clientId);
			attributes.put("stnId", stationId);
			//Station Authentication Checking
			if(stationId > 0 && (request.getHeaders().get("Authorization") != null && request.getHeaders().get("Authorization").size() > 0) || (request.getHeaders().get("authorization") != null && request.getHeaders().get("authorization").size() > 0)) {
				String headerAuth = request.getHeaders().get("Authorization").get(0);
				return chargerAuthenticationService.headerValidation(clientId, headerAuth);
			}else if(stationId > 0){
				stationService.updateIntialContactTime(stationId);
				return true;
			}else {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			customLogger.info(clientId, "Exception : "+e.getMessage());
			return true;
		}
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		try {
			String clientUrl = request.getURI().getPath();
			String clientId = clientUrl.substring(clientUrl.lastIndexOf('/') + 1);
			customLogger.info(clientId, "Handshake done successfuly");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
