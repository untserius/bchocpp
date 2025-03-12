package com.axxera.ocpp.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.message.OptimizationsForm;
import com.axxera.ocpp.message.ScheduleStartForm;
import com.axxera.ocpp.model.es.StationLogs;
import com.axxera.ocpp.rest.message.ResponseMessage;
import com.axxera.ocpp.service.AmpControllerService;
import com.axxera.ocpp.service.chargerCommunication;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.JSONDataParser;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.AuthorizationService;
import com.axxera.ocpp.webSocket.BootNotificationService;
import com.axxera.ocpp.webSocket.FirmwareStatusNotificationService;
import com.axxera.ocpp.webSocket.HeartbeatService;
import com.axxera.ocpp.webSocket.ResponseService;
import com.axxera.ocpp.webSocket.TransactionService;
import com.axxera.ocpp.webSocket.chargingSessionService;
import com.axxera.ocpp.webSocket.service.OCPPActiveSessionService;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.StatusNotificationService;
import com.axxera.ocpp.webSocket.service.ocppService;

@Component
public class WebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
	
	@Autowired
	private chargerCommunication chargerCommunication;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private BootNotificationService bootNotificationService;

	@Autowired
	private chargingSessionService chargingSessionService;

	@Autowired
	private FirmwareStatusNotificationService firmwareStatusNotificationService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private HeartbeatService heartbeatService;

	@Autowired
	private ResponseService responceService;

	@Autowired
	private OCPPActiveSessionService ocppActiveSessionService;

	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;

	@Autowired
	private StationService stationService;

	@Autowired
	private StatusNotificationService statusNotificationService;

	@Autowired
	private JSONDataParser jsonDataParser;
		
	@Autowired
	private ocppService ocppService;
	
	@Autowired
	private AmpControllerService ampControllerService;
	
	@Autowired
	private Utils utils;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

	public static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();
	private static final Map<String, WebSocketSession> sessionsWithStations = Collections.synchronizedMap(new ConcurrentHashMap<String, WebSocketSession>());

	@Override // Look Like the OnOpen
	public void afterConnectionEstablished(WebSocketSession session) {
		try {
			String clientId = String.valueOf(session.getAttributes().get("clientId"));
			customLogger.info(clientId, "called onOpen");
			customLogger.info(clientId, "client Ip Address : " + session.getAttributes().get("ipAddress")+ " , clientId : " + clientId + " , sessionId : " + session.getId());
			if (!clientId.equalsIgnoreCase("Portal") && !clientId.equalsIgnoreCase("Android") && !clientId.equalsIgnoreCase("Ios")) {
				ocppActiveSessionService.insertActiceSession(session.getId(), "OPENED", clientId,String.valueOf(session.getUri()));
			}
			WebSocketSession webSocketSession = sessionsWithStations.get(clientId);
			if (webSocketSession != null && !clientId.equalsIgnoreCase("Portal") && !clientId.equalsIgnoreCase("Android") && !clientId.equalsIgnoreCase("Ios")) {
				sessionsWithStations.remove(clientId);
				sessions.remove(webSocketSession);

				sessionsWithStations.put(clientId, session);
				sessions.add(session);
				StationLogs log = new StationLogs();
				log.setId(String.valueOf(UUID.randomUUID()));
				log.setRequestType("onOpen");
				log.setStnRefNum(clientId);
				log.setClientId(clientId);
				log.setStatus("success");
				esLoggerUtil.esInfo(log);
			} else {
				sessionsWithStations.put(clientId, session);
				sessions.add(session);
			}
			customLogger.info(clientId, "onOpen called successfuly");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override // Look Like the On Message
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
		String clientId = null;
		FinalData finalData = null;
		try {
			clientId = String.valueOf(session.getAttributes().get("clientId"));
			customLogger.info(clientId, "Received Message : " + String.valueOf(message.getPayload()));
			sessionsWithStations.put(clientId, session);
			if (message instanceof TextMessage) {
				try {
					finalData = jsonDataParser.getData(String.valueOf(message.getPayload()), clientId, session);
					long stationId = stationService.getStationUniqId(clientId);
					
					/* Updating Heart beat Time */
					if (finalData != null && (finalData.getFirstValue().equals(new Long(2)) || finalData.getFirstValue() == 2)) {
						
						/* Authorize */
						if (finalData.getAuthorize() != null) {
							authorizationService.authorization(finalData, clientId, sessionsWithStations, session,stationId, message.getPayload());
						}
						/* StartTransaction */
						else if (finalData.getStartTransaction() != null) {
							chargerCommunication.startTransaction(finalData, clientId, sessionsWithStations, session, message.getPayload());
						}
						/* MeterValues */
						else if (finalData.getMeterValues() != null) {
							chargingSessionService.meterVal(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
						}
						/* StopTransaction */
						else if (finalData.getStopTransaction() != null) {
							transactionService.stopTransaction(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
						}
						/* BootNotification */
						else if (finalData.getBootNotification() != null) {
							bootNotificationService.bootNotification(finalData, clientId, sessionsWithStations, session,stationId, message.getPayload());
						}
						/* Firmware StatusNotification */
						else if (finalData.getFirmwareStatusNotification() != null) {
							firmwareStatusNotificationService.updateFirmwareStatusNotification(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
						}
						/* StatusNotification */
						else if (finalData.getStatusNotification() != null) {
							statusNotificationService.statusNotification(finalData, clientId, sessionsWithStations,session, message.getPayload(),stationId);
						}
						/* Heartbeat */
						else if (finalData.getHeartbeat() != null) {
							heartbeatService.heartBeatInterval(finalData, clientId, sessionsWithStations, session,stationId, message.getPayload());
						}
						else if (finalData.getDatatransfer() != null) {
							responceService.dataTransfer(finalData, sessionsWithStations, session, "charger",message.getPayload(), clientId,stationId);
						}
						else if (finalData.getDiagnosticsStatusNotification() != null) {
							firmwareStatusNotificationService.updateDiagnosticsStatusNotification(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
						}
						else if(finalData.getSignCertificate() != null) {
							firmwareStatusNotificationService.updateSignCertificate(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
						}
						else if(finalData.getSecurityEventNotification() != null) {
							firmwareStatusNotificationService.updateSecurityEventNotification(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
						}
						else if(finalData.getLogStatusNotification() != null) {
							firmwareStatusNotificationService.updateLogStatusNotification(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
						}
					 	else if(finalData.getSignedFirmwareStatusNotification() != null) {
							firmwareStatusNotificationService.updateSignedFirmwareStatusNotification(finalData, clientId, sessionsWithStations, session, message.getPayload(),stationId);
					 	}
					} 
					else if(finalData != null && (finalData.getFirstValue().equals(new Long(3)) || finalData.getFirstValue() == 3)){
						responceService.threeResponse(finalData, sessions, session, clientId, String.valueOf(message.getPayload()), stationId);
					} 
					heartbeatService.stationActiveRecords(stationId,clientId);
					stationService.updateStationTimes(stationId);				
					heartbeatService.addHeartBeat(stationId,session,clientId);			
						
				}catch (Exception e) {
					e.printStackTrace();
				}
			} else if (message instanceof BinaryMessage) {
				customLogger.info(clientId, "Received BinaryMessage : " + message);
			} else if (message instanceof PingMessage) {
				customLogger.info(clientId, "Received PingMessage : " + message);
				PongMessage pm = new PongMessage();
				customLogger.info(clientId, "send PongMessage : " + pm.getPayload());
				session.sendMessage(pm);
			} else if (message instanceof PongMessage) {
				customLogger.info(clientId, "Received PongMessage");
				//long stationId = stationService.getStationUniqId(clientId);
				//stationService.updateStationTimes(stationId);
				//heartbeatService.addHeartBeat(stationId,session,clientId,stationObj);
			} else {
				customLogger.info(clientId, "Received UnKnown Message : " + message);
				customLogger.info(clientId, "payload : " + message.getPayload());
			}
		} catch (Exception e) {
			customLogger.info(clientId, "Error case charger message : " + String.valueOf(message));
			customLogger.info("Error", "Error case charger message : " + String.valueOf(message));
			e.printStackTrace();
		}
	}

	
	  @Override //Look Like the OnClose 
	  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		  try {
			  String clientId = String.valueOf(session.getAttributes().get("clientId"));
			  customLogger.info(clientId,"inside onclose Session disconnected. with Session : "+ session.getId()+" close reason : "+closeStatus); 
			  if(!clientId.equalsIgnoreCase("Portal") && !clientId.equalsIgnoreCase("Android")&& !clientId.equalsIgnoreCase("Ios")) {
				  	customLogger.info(clientId, "this is "+clientId+" , so session is not removed");
				  	ocppActiveSessionService.deleteActiveSession(clientId,String.valueOf(session.getUri())); 
				  	ocppActiveSessionService.updateActiveTransaction(clientId);
				 
				  	StationLogs log = new StationLogs();
				  	log.setId(String.valueOf(UUID.randomUUID()));
				  	log.setRequestType("onClose");
				  	log.setStnRefNum(clientId);
				  	log.setClientId(clientId);
				  	log.setStatus("success");
				 	esLoggerUtil.esInfo(log);
			  }else {
				  customLogger.info(clientId,"this is "+clientId+" , so session is removed");
				  sessionsWithStations.remove(clientId); 
				  sessions.remove(session); 
			  }
				
		  }catch(Exception e) {
			  e.printStackTrace(); 
		  } 
	  }
	 

	
	@Override //Look Like the On Error 
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		try {
			String clientId = String.valueOf(session.getAttributes().get("clientId"));
			customLogger.info(clientId, "inside onerror : " + exception);
			//customLogger.info(clientId, "getLocalizedMessage : " + exception.getLocalizedMessage());
			//customLogger.info(clientId, "getMessage : " + exception.getMessage());
			//customLogger.info(clientId, "getCause : " + exception.getCause());
			//customLogger.info(clientId, "getStackTrace : " + exception.getStackTrace());
			//customLogger.info(clientId, "toString : " + exception);
			//customLogger.info(clientId, "onerror fillInStackTrace : " + exception.fillInStackTrace());
			StationLogs log = new StationLogs();
			log.setId(String.valueOf(UUID.randomUUID()));
			log.setRequestType("onError");
			log.setStnRefNum(clientId);
			log.setClientId("charger");
			log.setStatus("success");
			esLoggerUtil.esInfo(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	@Override
	public List<String> getSubProtocols() {
		List<String> li = new ArrayList<>();
		try {
			li.add("ocpp1.6"); 
			li.add("ocpp1.9");
			li.add("OS3P1.0");
			li.add("ocpp1.1");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}

	public void sessionRemoval() {
		try {
			sessions.forEach(session -> {
				if (!session.isOpen()) {
					sessions.remove(session);
				}
			});
			Iterator<Map.Entry<String, WebSocketSession>> iterator = sessionsWithStations.entrySet().iterator();
	        while (iterator.hasNext()) {
	            Map.Entry<String, WebSocketSession> entry = iterator.next();
	            if (entry.getValue() != null && !entry.getValue().isOpen()) {
	                iterator.remove();
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void newRquest(Map<String,Object> map) {
		try {
			String clientId = String.valueOf(map.get("clientId"));
			String message = String.valueOf(map.get("message"));
			WebSocketSession webSocketSession = sessionsWithStations.get(clientId);
			if(webSocketSession != null && webSocketSession.isOpen()) {
				customLogger.info(clientId,"message sent : "+message);
				webSocketSession.sendMessage(new TextMessage(message));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void multiRequestHitting(List<Map<String,String>> map) {
		try {
			map.forEach(mapData -> {
				Map<String,Object> map1=new HashMap();
				String stationRefNum = String.valueOf(mapData.get("clientId"));
				String reqType = String.valueOf(mapData.get("message"));
				
				map1.put("message", reqType);
				map1.put("clientId",stationRefNum);
				
				newRquest(map1);
				
			});}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResponseMessage features(OCPPForm of) {
		ResponseMessage response=new ResponseMessage();
		try {
			response = ocppService.features(of,sessionsWithStations);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public ResponseMessage changeAvailability(OCPPForm of) {
		ResponseMessage response=new ResponseMessage();
		try {
			response= ocppService.changeAvailability(of,sessionsWithStations);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public ResponseMessage ocpiFeatures(OCPPForm of) {
		ResponseMessage response=new ResponseMessage();
		try {
			response = ocppService.ocpiFeatures(of,sessionsWithStations);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public ResponseMessage appFeatures(OCPPForm of) {
		ResponseMessage response=new ResponseMessage();
		try {
			response = ocppService.appFeatures(of,sessionsWithStations);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public ResponseMessage multiFeatures(OCPPForm of) {
		ResponseMessage Response = new ResponseMessage();
		try {
			if (of != null) {
				List<Long> data = of.getStationIdList();
				Map<String,Object> res = new HashMap<>();
				LOGGER.info("start time : "+utils.getUTCDateString());
				data.forEach(stationId -> {
					Thread thread = new Thread() {
						public void run() {
							LOGGER.info("process time : "+utils.getUTCDateString());
							ResponseMessage singleRes = new ResponseMessage();
							long portid = of.getConnectorId();
							OCPPForm newForm = new OCPPForm();
							newForm = of;
							
							Map<String, Object> connectorId = stationService.getPortByRefNum(stationId, portid);
							if (connectorId != null && !connectorId.isEmpty()) {
								long portId = Long.parseLong(String.valueOf(connectorId.get("id")));

								newForm.setStationId(stationId);
								newForm.setConnectorId(portId);
								LOGGER.info("process newForm : "+newForm);
								singleRes = ocppService.features(newForm, sessionsWithStations);
							}
							res.put(singleRes.getClientId(), singleRes.getStatus()+" "+singleRes.getMessage());
						}
					};
					thread.start();
				});
				LOGGER.info("time : "+new Date());
				int timeout = 300;
				while (timeout > 0) {
					Thread.sleep(3000);
					timeout -= 3;
					LOGGER.info("data size : "+data.size()+" , res.size() : "+res.size()+" , res : "+res);
					if (data.size() == res.size()) {
						timeout = 0;
					}
				}
				Response.setStatus("Accepted");
				Response.setMessage(String.valueOf(res).replaceAll("=", ":").replace("{", "").replace("}", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response;
	}
	
	public void scheduleChareStartTransaction(ScheduleStartForm scheduleStartData) {
		LOGGER.info("start scheduleChareStartTransaction");
		try {
		      LOGGER.info("web Socket Handler scheduleChareStartTransaction"); 
			//ampControlService.scheduleStart(scheduleStartData, sessionsWithStations);
			scheduleStart(scheduleStartData, sessionsWithStations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("end scheduleChareStartTransaction");
	}

	public void scheduleChareStopTransaction(ScheduleStartForm scheduleStartData) {
		LOGGER.info("start scheduleChareStopTransaction");
		try {
			 LOGGER.info("web Socket Handler scheduleChareStopTransaction"); 
				//ampControlService.scheduleStop(scheduleStartData, sessionsWithStations);
				scheduleStop(scheduleStartData, sessionsWithStations);
		}catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("end scheduleChareStopTransaction");
	}
	
	private void
	scheduleStart
	(ScheduleStartForm scheduleStartData, Map<String, WebSocketSession> sessionswithstations) {
		LOGGER.info("start scheduleStart");
		try {
			long profileId = scheduleStartData.getProfileId();
			long stationId = scheduleStartData.getStationId();
			long portId = scheduleStartData.getPortId();
			String idTag = scheduleStartData.getIdTag();
			LOGGER.info("idTag : " + idTag);
			LOGGER.info("stationId : " + stationId);
			OCPPForm of = new OCPPForm();
			of.setStationId(stationId);
			of.setConnectorId(portId);
			of.setRequestType("RemoteStart");
			of.setIdTag(idTag);
			of.setOrgId(1);
			of.setClientId("Scheduler");
			features(of);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("end scheduleStart");
	}
	
	public void scheduleStop(ScheduleStartForm scheduleStartData,Map<String, WebSocketSession> sessionswithstations) {
		LOGGER.info("start scheduleStop");
		try {
			long profileId = scheduleStartData.getProfileId();
			long stationId=scheduleStartData.getStationId();
			long portId=scheduleStartData.getPortId();
			String idTag = scheduleStartData.getIdTag();
			LOGGER.info("stationId : "+stationId);
			OCPPForm of = new OCPPForm();
			of.setStationId(stationId);
			of.setConnectorId(portId);
			of.setRequestType("RemoteStop");
		   features(of);
		}catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("end scheduleStop");
	}
	public void ampSessionBaseFunctionsForEVG(OptimizationsForm optimizationData) {
		LOGGER.info("start ampSessionBaseFunctionsForEVG");
		try {
			ampControllerService.sendOptimizationForEVG(optimizationData, sessionsWithStations);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
