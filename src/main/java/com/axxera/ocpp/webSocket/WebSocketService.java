//package com.axxera.ocpp.webSocket;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import com.axxera.ocpp.message.FinalData;
//import com.axxera.ocpp.utils.JSONDataParser;
//import com.axxera.ocpp.utils.LoggerUtil;
//import com.axxera.ocpp.webSocket.service.OCPPActiveSessionService;
//
//@Service
//public class WebSocketService {
//
//	private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);
//
//	@Autowired
//	private AuthorizationService authorizationService;
//
//	@Autowired
//	private BootNotificationService bootNotificationService;
//
//	@Autowired
//	private MeterValuesService meterValuesService;
//
//	@Autowired
//	private FirmwareStatusNotificationService firmwareStatusNotificationService;
//
//	@Autowired
//	private StatusNotificationService statusNotificationService;
//
//	@Autowired
//	private TransactionService transactionService;
//
//	@Autowired
//	private HeartbeatService heartbeatService;
//
//	@Autowired
//	private PortIntervalService portIntervalService;
//
//	@Autowired
//	private ResponseService responceService;
//
//	@Autowired
//	private OCPPActiveSessionService ocppActiveSessionService;
//	
//	@Autowired
//	private RemoteTransactionService remoteTransactionService;
//	
//	@Autowired
//	private LoggerUtil customeLogger;
//	
//	private  final Map<String, WebSocketSession> sessionsWithStations = Collections.synchronizedMap(new HashMap<String,WebSocketSession>());
//	
//	public void onOpen(WebSocketSession session){
//		// TODO Auto-generated method stub
//
//		String clientId = session.getAttributes().get("clientId").toString();
//
//		if (!clientId.equalsIgnoreCase("Portal") && !clientId.equalsIgnoreCase("Android") && !clientId.equalsIgnoreCase("Ios"))
//			ocppActiveSessionService.insertActiceSession(session.getId(), "OPENED", Long.parseLong(clientId));
//		
//		
//		sessionsWithStations.put(clientId, session);
//		
//	}
//
//	public void onMessage(WebSocketSession session, Map<String, WebSocketSession> sessions, WebSocketMessage<?> message)
//			{
//		// TODO Auto-generated method stub
//		String clientId = session.getAttributes().get("clientId");//ipAddress
//
//		customeLogger.info(clientId, "Received Message :- "+message.getPayload());
//		customeLogger.info(clientId, "client Ip Address :-"+session.getAttributes().get("ipAddress"));
//		FinalData finalData = JSONDataParser.getData(message.getPayload());
//		
//		if (!clientId.equalsIgnoreCase("Portal") && !clientId.equalsIgnoreCase("Android") && !clientId.equalsIgnoreCase("Ios")) {
//			
//			if (finalData.getFirstValue().equals(new Long(2))) {
//
//				logger.info("WebSocketService.parseData() - Request - [2]");
//
//				/* str3.equals("Authorize") */ 
//				if (finalData.getAuthorize() != null)
//					authorizationService.authorization(finalData, Long.parseLong(clientId), sessions, session);
//				/* BootNotification */
//				else if (finalData.getBootNotification() != null)
//					bootNotificationService.bootNotification(finalData, Long.parseLong(clientId), sessions, session);
//				/* MeterValues */
//				else if (finalData.getMeterValues() != null)
//					meterValuesService.meterVal(finalData, Long.parseLong(clientId), sessions, session);
//				/* Firmware StatusNotification */
//				else if (finalData.getFirmwareStatusNotification() != null)
//					firmwareStatusNotificationService.notification(finalData, Long.parseLong(clientId), sessions,
//							session);
//				/* StatusNotification */
//				else if (finalData.getStatusNotification() != null)
//					statusNotificationService.notification(finalData, Long.parseLong(clientId), sessions, session);
//
//				/* StartTransaction */
//				else if (finalData.getStartTransaction() != null)
//					transactionService.startTransaction(finalData, Long.parseLong(clientId), sessions, session);
//				/* StopTransaction */
//				else if (finalData.getStopTransaction() != null)
//					transactionService.stopTransaction(finalData, Long.parseLong(clientId), sessions, session);
//				/* Heartbeat */
//				else if (finalData.getHeartbeat() != null)
//					heartbeatService.heartBeatInterval(finalData, Long.parseLong(clientId), sessions, session);
//				/* PortInterval */
//				else if (finalData.getPortInterval() != null)
//					portIntervalService.start(finalData, Long.parseLong(clientId), sessions, session);
//
//			} 	else if (finalData.getFirstValue().equals(new Long(3))) 
//				responceService.threeResponse(finalData, sessions, session,Long.parseLong(clientId));
//				else if (finalData.getFirstValue().equals(new Long(4))) 
//				responceService.fourResponse(finalData, sessions, session,Long.parseLong(clientId));
//			
//		}
//		else	{
//			
//			/* Request From Portal & Apps */
//			
//			
//				if (finalData.getFirstValue().equals(new Long(1001))) {
//					responceService.hardResetResponse(finalData, sessions, session,message.getPayload());
//				}else if (finalData.getFirstValue().equals(new Long(1002))) {
//					logger.info("WebSocketService.parseData() - SR - [1002]");
//					responceService.softResetResponse(finalData, sessions, session,message.getPayload());
//				}else if (finalData.getFirstValue().equals(new Long(1003))) {
//					logger.info("WebSocketService.parseData() - ChangeConfig - [1003]");
//					responceService.changeConfigResponse(finalData, sessions, session);
//				}else if (finalData.getFirstValue().equals(new Long(1004))) {
//				 logger.info("WebSocketService.parseData() - RST - [1004]");
//				remoteTransactionService.remoteStart(finalData,clientId,sessions, session);
//			  	}else if (finalData.getFirstValue().equals(new Long(1005))) {
//				logger.info("WebSocketService.parseData() - RSTP - [1005]");
//				remoteTransactionService.remotStopTransaction(finalData,clientId,sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(1006))) {
//					logger.info("WebSocketService.parseData() - ChangeAvailability - [1006]");
//					responceService.changeAvailability(finalData, sessions, session);
//				}else if (finalData.getFirstValue().equals(new Long(1007))) {
//					logger.info("WebSocketService.parseData() - UnlockConnector - [1007]");
//					responceService.unlockConnector(finalData, sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(1008))) {
//					logger.info("WebSocketService.parseData() - Configuration - [1008]");
//					responceService.getConfiguration(finalData, sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(1012))) {
//					logger.info("WebSocketService.parseData() - ClearCache - [1012]");
//					responceService.clearcache(finalData, sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(1050))) {
//					logger.info("WebSocketService.parseData() - GetDiagnostics - [1050]");
//					responceService.getDiagnostics(finalData, sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(1055))) {
//					logger.info("WebSocketService.parseData() - Firmware - [1055]");
//					responceService.updateFirmware(finalData, sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(2369))) {
//					logger.info("WebSocketService.parseData() - ChangeStatus - [2369]");
//					responceService.changeStatus(finalData, sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(1064))) {
//					logger.info("WebSocketService.parseData() - SiteUpdate - [1064]");
//					responceService.siteUpdate(finalData, sessions, session);
//				} else if (finalData.getFirstValue().equals(new Long(2020))) {
//					logger.info("WebSocketService.parseData() - VendingPrice - [2020]");
//					responceService.vendingPrice(finalData, sessions, session);
//				} else if(finalData.getFirstValue().equals(new Long(1056))) {
//					logger.info("websocketservice LocalAuthorization - [1056]");
//					responceService.sendLocalList(finalData, sessions, session);////need to confirm the functionality old code having some issues
//				} else if(finalData.getFirstValue().equals(new Long(1080))) {
//					logger.info("websocketservice reservation - [1080]");
//					responceService.reservation(finalData, sessions, session); 
//				}else if(finalData.getFirstValue().equals(new Long(1085))) {
//					logger.info("websocketservice TriggerMessage - [1085]");
//					responceService.triggerMessage(finalData, sessions, session);//ok
//				}else if(finalData.getFirstValue().equals(new Long(1013))) {
//					logger.info("websocketservice GetLocalListVersion - [1013]");
//					responceService.getLocalListVersion(finalData, sessions, session);
//				}else if(finalData.getFirstValue().equals(new Long(1081))) {
//					logger.info("websocketservice dataTransfer - [1081]");
//					responceService.dataTransfer(finalData, sessions, session);//not completed
//				}else if(finalData.getFirstValue().equals(new Long(1014))) {
//					logger.info("websocketservice cancelReservation - [1014]");
//					responceService.cancelReservation(finalData, sessions, session);
//				}
//		}
//	}
//
//	public void onClose(WebSocketSession session, CloseStatus closeStatus) {
//		// TODO Auto-generated method stub
//		String clientId = session.getAttributes().get("clientId");
//
//		if (!clientId.equalsIgnoreCase("Portal") && !clientId.equalsIgnoreCase("Android")&& !clientId.equalsIgnoreCase("Ios"))
//			ocppActiveSessionService.deleteActiveSession(Long.parseLong(clientId));
//		
//		
//	}
//
//	public void onError(WebSocketSession session, Throwable exception) {
//		// TODO Auto-generated method stub
//		String clientId = session.getAttributes().get("clientId");
//
//		if (!clientId.equalsIgnoreCase("Portal") && !clientId.equalsIgnoreCase("Android") && !clientId.equalsIgnoreCase("Ios"))
//			ocppActiveSessionService.deleteActiveSession(Long.parseLong(clientId));
//		
//	}
//
//}
