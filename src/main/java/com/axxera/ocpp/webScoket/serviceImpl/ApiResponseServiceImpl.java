package com.axxera.ocpp.webScoket.serviceImpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.StatusNotificationDao;
import com.axxera.ocpp.forms.remoteStart;
import com.axxera.ocpp.message.ChargingPrice;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.message.RemoteStartWithSmartCharging;
import com.axxera.ocpp.message.RemoteStopTransaction;
import com.axxera.ocpp.message.ReserveNow;
import com.axxera.ocpp.model.ocpp.ChargingSchedulePeriod;
import com.axxera.ocpp.model.ocpp.Credentials;
import com.axxera.ocpp.model.ocpp.LocalList;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.rest.message.ResponseMessage;
import com.axxera.ocpp.service.LMRequestService;
import com.axxera.ocpp.service.userService;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.JSONDataParser;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.CurrencyConversion;
import com.axxera.ocpp.webSocket.service.ApiResponseService;
import com.axxera.ocpp.webSocket.service.OCPPActiveTransactionsService;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.StatusNotificationService;
import com.axxera.ocpp.webSocket.service.StatusSendingDataService;
import com.axxera.ocpp.webSocket.service.failedSessionService;
import com.axxera.ocpp.webSocket.service.remoteStartService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApiResponseServiceImpl implements ApiResponseService {

	private final static Logger logger = LoggerFactory.getLogger(ApiResponseServiceImpl.class);
	ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private StatusSendingDataService statusSendingDataService;
	
	@Autowired
	private OCPPChangeAvailabilityService OCPPChangeAvailabilityService;

	@Autowired
	private OCPPRemoteStartTransactionService ocppRemoteStartTransactionService;
	
	@Autowired
	private failedSessionService failedSessionService; 

	@Autowired
	private StationService stationService;
	
	@Autowired
	private OCPPUnlockConnectorService OCPPUnlockConnectorService;
	
	@Autowired
	private OCPPAccountAndCredentialService ocppAccountAndCredentialService;
	
	@Autowired
	private StatusNotificationService statusNotificationService;
	
	@Autowired
	private OCPPFreeChargingService ocppFreeChargingService;

	@Autowired
	private LoggerUtil customeLogger;
	
	@Autowired
	private LMRequestService lmRequestService;
	
	@Autowired
	private Utils Utils;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private JSONDataParser jsonDataParser;
	
	@Autowired
	private propertiesServiceImpl propertiesService;
	
	@Autowired
	private OCPPActiveTransactionsService ocppActiveTransactionsService;
	
	@Autowired
	private CurrencyConversion currencyConversion;
	
	@Autowired
	private remoteStartService remoteStartService;
	
	@Autowired
	private OCPPResetService ocppResetService;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;
	
	@Autowired
	private OCPPMeterValueServiceImpl ocppMeterValueService;
	
	@Autowired
	private StatusNotificationDao statusNotificationDao;
	
	@Autowired
	private userService userService;
	
	@Override
	public ResponseMessage reset(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
		try {
			final long stationId = of.getStationId();
			final long connectorId = of.getConnectorId();
			final String requestType = of.getRequestType();
			final String stationRefNum = stationService.getstationRefNum(stationId);
			customeLogger.info(stationRefNum, "reset request received from : "+of.getClientId());
			final String clientId = of.getClientId();
			response.setClientId(stationRefNum);
			final String value = of.getType();
			String uuId = Utils.getStationRandomNumber(stationId);
			String uniqueID = null;
			Date utcDate = Utils.getUtcDateFormate(new Date());
			try {
				if (value.equalsIgnoreCase("Soft")) {
					uniqueID = uuId + ":SR";
				} else {
					uniqueID = uuId + ":HR";
				}
				String message = "[2,\"" + uniqueID + "\",\"Reset\",{\"type\":\"" + value + "\"}]";
				WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
				ocppResetService.addReset(uniqueID, value, utcDate);
				if (webSocketSessionObj != null && webSocketSessionObj.isOpen()) {
					customeLogger.info(stationRefNum, "Response Send to Charger :-" + message);
					Utils.chargerMessage(webSocketSessionObj, message, stationRefNum);
					response.setStatus("Accepted");
					esLoggerUtil.insertLongs(uniqueID, "Portal", value + requestType, message, stationRefNum,stationId,"Inprogress",0);
				} else {
					customeLogger.info(stationRefNum, "Station Disconnected : ");
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
					esLoggerUtil.insertLongs(uniqueID, "Portal", value + requestType, message, stationRefNum,stationId,"Station Disconnected",0);
				}
				if (response.getStatus().equalsIgnoreCase("Accepted")) {
					statusSendingDataService.addData(value + requestType, uniqueID, stationId,"Inprogress", "", 0, 0, connectorId, "", "", "",clientId);
					int timeout = 90;
					String responseStatus = "";
					String responseMessage="";
					while (timeout > 0) {
						Thread.sleep(3000);
						Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
						timeout -= 3;
						responseStatus = String.valueOf(statusFromRequest.get("status"));
						responseMessage=String.valueOf(statusFromRequest.get("response"));
						if (!responseStatus.equalsIgnoreCase("Inprogress")) {
							timeout = 0;
						}
					}
					responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
					if(!responseStatus.equalsIgnoreCase("Accepted")) {
						response.setStatus("Rejected");
						response.setMessage(responseMessage);
					}
					else {
						response.setStatus("Accepted");
						response.setMessage("");
					}
					
				} else {
					customeLogger.info(stationRefNum, "201 >> Station is not connected to Ocpp Server for Reset");
					Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
					Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
					statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
				}
				response.setTimestamp(new Date());
				response.setStatusCode(HttpStatus.OK.value());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage changeAvailability(OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {
			final String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "changeAvailability request received from : "+of.getClientId()+" , portId : "+of.getConnectorId()+" , type : "+of.getType());
			response.setClientId(stationRefNum);
			final long stationId = of.getStationId();
			final long portId = of.getConnectorId();
			final long connectorId = stationService.getStationConnectorId(portId);
			final String value = of.getType();
			String uniqueID = Utils.getStationRandomNumber(stationId)  + ":CA";
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String portStatus = statusNotificationService.getStatus(stationId,portId);
			String msg = "[2,\"" + uniqueID.toString() + "\",\"ChangeAvailability\",{\"connectorId\":" + connectorId
					+ ",\"type\":\"" + value + "\"}]";
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && portStatus != null && webSocketSessionObj.isOpen()) {
				if (portStatus != null && !portStatus.equalsIgnoreCase("")) {
					OCPPChangeAvailabilityService.addChangeAvailabiliy(portId, uniqueID,value, Utils.getUtcDateFormate(new Date()), stationId);
					customeLogger.info(stationRefNum, "Change Availablity Response Message : " + msg);
					Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
					response.setStatus("Accepted");
					esLoggerUtil.insertLongs(uniqueID,"Portal","ChangeAvailability",msg,stationRefNum,stationId,"Inprogress",connectorId);
				} else {
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
					esLoggerUtil.insertLongs(uniqueID,"Portal","ChangeAvailability",msg,stationRefNum,stationId,"Station Disconnected",connectorId);
				}
			} else {
				OCPPChangeAvailabilityService.addChangeAvailabiliy(portId, uniqueID, value, Utils.getUtcDateFormate(new Date()), stationId);
				customeLogger.info(stationRefNum, "Change Availablity Response Message : " + msg);
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for ChangeAvailablility");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","ChangeAvailability",msg,stationRefNum,stationId,"Station Disconnected",connectorId);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("ChangeAvailability", uniqueID, stationId, "Inprogress","", 0, 0, portId, "", "", "","Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for ChangeAvailablility");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage changeConfiguration(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response=new ResponseMessage();
		try {
			String priceText = null;
			String priceTextOffline = null;
			ChargingPrice chargingPrice = null;
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "changeConfiguration request received from : "+of.getClientId()+" , portId : "+of.getConnectorId()+" , key : "+of.getKey()+" , value : "+of.getValue());
			response.setClientId(stationRefNum);
			final long stationId = of.getStationId();
			final long portId = of.getConnectorId();
			final String key = of.getKey();
			String value=of.getValue();

			String uniqueID = "";
			uniqueID =Utils.getStationRandomNumber(stationId)  + ":CNF";

			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String msg = "[3,\"" + uniqueID + "\",{\"status\":\"StationDisconnected\"}]";
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()) {

				if(key.equalsIgnoreCase("DefaultPrice")) {
					value="{\\\"priceText\\\":\\\""+priceText+"\\\",\\\"priceTextOffline\\\":\\\""+priceTextOffline+"\\\",\\\"chargingPrice\\\":{\\\"kWhPrice\\\":"+chargingPrice.getkWhPrice()+",\\\"hourPrice\\\":"+chargingPrice.getHourPrice()+",\\\"flatFee\\\":"+chargingPrice.getFlatFee()+"}";
				}

				msg = "[2,\"" + uniqueID + "\",\"ChangeConfiguration\",{\"key\":\"" + key + "\",\"value\":\"" + value + "\"}]";

				customeLogger.info(stationRefNum, "Inside the change configuration Response Message send to charger :- " + msg);
				esLoggerUtil.insertLongs(uniqueID,"Portal","ChangeConfiguration",msg,stationRefNum,stationId,"Inprogress",0);
				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
				response.setStatus("Accepted");
			} else {
				if(key.equalsIgnoreCase("DefaultPrice")) {
					value="{\\\"priceText\\\":\\\""+priceText+"\\\",\\\"priceTextOffline\\\":\\\""+priceTextOffline+"\\\",\\\"chargingPrice\\\":{\\\"kWhPrice\\\":"+chargingPrice.getkWhPrice()+",\\\"hourPrice\\\":"+chargingPrice.getHourPrice()+",\\\"flatFee\\\":"+chargingPrice.getFlatFee()+"}";
				}
				msg = "[2,\"" + uniqueID + "\",\"ChangeConfiguration\",{\"key\":\"" + key + "\",\"value\":\"" + value + "\"}]";
				esLoggerUtil.insertLongs(uniqueID,"Portal","ChangeConfiguration",msg,stationRefNum,stationId,"Station Disconnected",0);
					
				customeLogger.info(stationRefNum, "Inside the change configuration Response not Message send to charger due to StationDisconnected : " + msg);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("ChangeConfiguration", uniqueID, stationId, "Inprogress", "", 0, 0, portId, "", key, value, "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for ChangeConfiguration");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	

	@Override
	public ResponseMessage unlockConnector(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "unlockConnector request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long stationId = of.getStationId();
			long portId = of.getConnectorId();
			long connectorId = stationService.getStationConnectorId(portId);
			String setting = of.getRequestType();
			String uniqueID = "";
			String reason = "Invalid StationId";
			uniqueID = Utils.getStationRandomNumber(stationId) + ":Unlk";
			reason = "StationDisconnected";
			
			
			OCPPUnlockConnectorService.addUnlockConnector(portId, "UnlockConnector", stationId,
					uniqueID);

			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String msg = "";
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()) {				

				msg = "[2,\"" + uniqueID.toString() + "\",\"UnlockConnector\",{\"connectorId\":" + connectorId + "}]";

				customeLogger.info(stationRefNum, "inside change Unlock connector Response send to Charger :-" + msg);
				esLoggerUtil.insertLongs(uniqueID,"Portal","UnlockConnector",msg,stationRefNum,stationId,"Inprogress",0);
				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
				response.setStatus("Accepted");
			} else {
				customeLogger.info(stationRefNum, "Station Not connected to ocpp Server For unlock connector Request");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","UnlockConnector",msg,stationRefNum,stationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("UnlockConnector", uniqueID, stationId, "Inprogress","", 0, 0, portId, "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for UnlockConnector");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	

	@Override
	public ResponseMessage getConfiguration(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {
			long stationId=of.getStationId();
			long portId=of.getConnectorId();
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "getConfiguration requesr received from : "+of.getClientId()+" , portId : "+of.getConnectorId()+" , message Id : "+of.getMessageId());
			response.setClientId(stationRefNum);
			String uniqueID =  Utils.getStationRandomNumber(stationId) + ":GC";
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String msg = "[3,\"" + uniqueID + "\",{\"status\":\"StationDisconnected\"}]";
			if ( of.getKey().equalsIgnoreCase("AllKeys")) {
				msg = "[2,\"" + uniqueID + "\",\"GetConfiguration\",{}]";				
			} else  {
				msg = "[2,\"" + uniqueID + "\",\"GetConfiguration\",{\"key\":[\"" + of.getKey() + "\"]}]";
			} 
			logger.info("GC sessionswithstations : "+sessionswithstations);
			logger.info("GC stationRefNum : "+stationRefNum);
			logger.info("GC webSocketSessionObj : "+webSocketSessionObj);
			if(webSocketSessionObj != null && webSocketSessionObj.isOpen()) {
				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
				customeLogger.info(stationRefNum, "Request of Get configuration : " + msg);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueID,"Portal","GetConfiguration",msg,stationRefNum,stationId,"Inprogress",0);
			} else {
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");	
				esLoggerUtil.insertLongs(uniqueID,"Portal","UnlockConnector",msg,stationRefNum,stationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("GetConfiguration", uniqueID,stationId, "Inprogress","", 0, 0,portId, "Not supported", of.getKey(), "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					logger.info("responseMessage : "+responseMessage);
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseMessage;
				if(!responseStatus.equalsIgnoreCase("Inprogress")) {
					response.setStatus("Accepted");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for GetConfiguration");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage getDiagnostics(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)  {
		ResponseMessage response=new ResponseMessage();
		try {
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "getDiagnostics request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long StationId = of.getStationId();
			long portId = of.getConnectorId();
			String setting = of.getRequestType();
			String startDate = of.getStartDate();
			String endDate = of.getEndDate();

			String uniqueID = "";
			uniqueID = Utils.getStationRandomNumber(StationId) + ":GD";
			String reason = "StationDisconnected";			
			
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String msg = "";
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()) {
				Map<String, Object> stationdetails= stationService.getStnByRefNum(stationRefNum); 
				String location = propertiesService.getPropety("getDiagnosticsLocation");
				location=location+stationRefNum+"/";
				msg = "[2,\"" + uniqueID.toString() + "\",\"GetDiagnostics\",{\"location\":\"" + location
						+ "\",\"retries\":" + of.getRetries() + ",\"retryInterval\":" + of.getRetriesIntervals()
						+ ",\"startTime\":\"" + of.getStartDate() + "\",\"stopTime\":\"" + of.getEndDate() + "\"}]";
				statusNotificationDao.createFtpPath(stationRefNum);
				statusNotificationDao.insertdiagnosticsFilesLocation(String.valueOf(stationdetails.get("manfId")), StationId, location, uniqueID);

				customeLogger.info(stationRefNum, "GetDiagnostics response send to Ocpp server :-" + msg);
				esLoggerUtil.insertLongs(uniqueID,"Portal","GetDiagnostics",msg,stationRefNum,StationId,"Inprogress",0);
				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
				response.setStatus("Accepted");
			} else {
				customeLogger.info(stationRefNum, "GetDiagnostics response send to Ocpp server :-" + msg);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","UnlockConnector",msg,stationRefNum,StationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("GetDiagnostics", uniqueID, StationId, "Inprogress", "", 0, 0, portId, "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(responseStatus.equalsIgnoreCase("Rejected")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage(responseMessage);
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for GetDiagnostics");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage updateFirmWare(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations){
		ResponseMessage response=new ResponseMessage();
		try {

			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "updateFirmWare request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long StationId = of.getStationId();
			long portId = of.getConnectorId();
			String uniqueID = "";
			uniqueID = Utils.getStationRandomNumber(StationId) + ":UF";

			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String msg = "";
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()) {

			

				msg = "[2,\"" + uniqueID + "\",\"UpdateFirmware\",{\"location\":\"" + of.getFileName()
						+ "\",\"retrieveDate\":\"" + of.getRetrieveDate() + "\"}]";

				customeLogger.info(stationRefNum, "UpdateFirmware Request send to charger :-" + msg);
				esLoggerUtil.insertLongs(uniqueID,"Portal","UpdateFirmware",msg,stationRefNum,StationId,"Inprogress",0);
				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
				response.setStatus("Accepted");

			} else {
				esLoggerUtil.insertLongs(uniqueID,"Portal","UpdateFirmware",msg,stationRefNum,StationId,"Station Disconnected",0);
				customeLogger.info(stationRefNum, "UpdateFirmware Request Not send to charger Due to server Disconnected:-");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("UpdateFirmware", uniqueID, StationId, "Inprogress", "",
						0, 0,portId, "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for UpdateFirmware");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage clearCache(OCPPForm of,Map<String, WebSocketSession> sessionswithstations)  {
		ResponseMessage response=new ResponseMessage();
		try {
			
			final String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "clearCache request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			final long StationId = of.getStationId();
			final long portId = of.getConnectorId();

			String uniqueID = "";
			uniqueID = Utils.getStationRandomNumber(StationId) + ":CC";

			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String msg = "[2,\"" + uniqueID + "\",\"ClearCache\",{}]";;
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()) {				
				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
				customeLogger.info(stationRefNum, "Inside clearCache Reqsponse Send to Charger :-" + msg);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueID,"Portal","clearcache",msg,stationRefNum,StationId,"Inprogress",0);
			} else {
				customeLogger.info(stationRefNum, "Inside clearCache Reqsponse Not Send to Charger due to Server Disconnected :-");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","clearcache",msg,stationRefNum,StationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("clearcache", uniqueID, StationId, "Inprogress", "", 0,
						0, portId, "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for ClearCache");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}


	@Override
	public ResponseMessage reserveNow(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)  {
		ResponseMessage response=new ResponseMessage();
		try {
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "reserveNow request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long StationId = of.getStationId();
			long portId = of.getConnectorId();
			String setting = of.getRequestType();
			String rfidPhno = of.getIdTag();
			String uniqueID = "";
			long chargerConnectorId = stationService.getStationConnectorId(portId);
			uniqueID = Utils.getStationRandomNumber(StationId) + ":RN";
			String reason = "";
			int reservationId = ThreadLocalRandom.current().nextInt(10000);
			Map<String, Object> userObj = new HashMap<>();
			customeLogger.info(stationRefNum, "stationRefNum : " + stationRefNum);
			ReserveNow reserveNow =new ReserveNow();
			reserveNow.setReservationId(reservationId);
			ResponseEntity<String> result = null;
			boolean reserveNowValid = false;
			double accountBalance = 0.00;
			long accId = ocppFreeChargingService.getAccIdOnBaseIdtag(rfidPhno);
			long userId = ocppFreeChargingService.getUserIdOnBaseAccId(accId);
			List<Map<String,Object>> stationStatusa = statusNotificationDao.getPortStatus(StationId, portId);
			String stationStatus = "Inoperative";
			if(stationStatusa.size() > 0) {
				stationStatus = String.valueOf(stationStatusa.get(0).get("status"));
			}
			String reserveNowStatus = stationStatus.equalsIgnoreCase("Charging") ? "Occupied"
					: stationStatus.equalsIgnoreCase("Reserved") ? "Reserved"
							: stationStatus.equalsIgnoreCase("Unavailable") ? "Station Disconnected"
									: stationStatus.equalsIgnoreCase("Available") ? "Inprogress" : "Rejected";

			String msg = "[3,\"" + uniqueID + "\",{\"status\":\"" + reserveNowStatus + "\"}]";
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String message="Station Disconnected";
			if (userId != 0) {
				customeLogger.info(stationRefNum, "port Status : " + stationStatus);
				if (stationStatus.equalsIgnoreCase("Available")) {
					Map<String,Object> mapData = stationService.getStnByRefNum(stationRefNum) ;
					userObj = ocppFreeChargingService.accntsBeanObj(userId);
					accountBalance = Double.valueOf(userObj.get("accountBalance").toString());
					String userCurrencyCheck = String.valueOf(userObj.get("currencyType"));
					int reservationDuration = currencyConversion.getReservationDuration(userId);
					Map<String, Object> siteObj = stationService.getSiteDetails(StationId);
					String siteCurrency = String.valueOf(siteObj.get("currencyType"));
					BigDecimal reservationFee=new BigDecimal(String.valueOf(mapData.get("reservationFee")));
					double userReservationFee=Double.parseDouble(String.valueOf(reservationFee));
					if(!userCurrencyCheck.equalsIgnoreCase(siteCurrency)) {
					 userReservationFee = Double.parseDouble(String.valueOf(currencyConversion.convertCurrency(userCurrencyCheck, siteCurrency, reservationFee)));
					}
					if (accountBalance >= userReservationFee) {
						Date startTime = Utils.getUtcDateFormate(new Date());
						String ExpiryDate = Utils.addMin(reservationDuration);
						customeLogger.info(stationRefNum, "ExpiryDate : " + ExpiryDate);
						Date endtime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(ExpiryDate);
						reserveNow.setStarTime(startTime);
						reserveNow.setIdTag(rfidPhno);
						reserveNow.setEndTime(endtime);
						reserveNow.setUniqueId(uniqueID);
						reserveNow.setUserId(userId);
						reserveNow.setStationId(StationId);
						reserveNow.setConnectorId(of.getConnectorId());
						reserveNow.setReserveAmount(Double.valueOf(String.valueOf(mapData.get("reservationFee"))));
						//reserveNow.setCancellationFee(Double.valueOf(String.valueOf(mapData.get("cancelReservationFee"))));
						stationService.insertReserNowData(reserveNow);
						msg = "[2,\"" + uniqueID + "\",\"ReserveNow\",{\"connectorId\":" + chargerConnectorId + ""
								+ ",\"idTag\":\"" + rfidPhno + "\"," + "\"reservationId\":" + reservationId + ""
								+ ",\"expiryDate\":\"" + ExpiryDate + "\"}]";
						reserveNowValid = true;
						esLoggerUtil.insertLongs(uniqueID,"Portal","ReserveNow",msg,stationRefNum,StationId,"Inprogress",chargerConnectorId);
					} else {
						reserveNowStatus = "InSufficient funds";
						message="Low balance";
						customeLogger.info(stationRefNum, "Reservation InSufficient funds");
					}
				} else {
					customeLogger.info(stationRefNum, "Reservation response not Send due to : " + stationStatus);
					reserveNowStatus = stationStatus=="null"?"Station Disconnected":stationStatus;
					message= stationStatus=="null"?"Station Disconnected":stationStatus;
				}
			} else {
				reserveNowStatus = "InvalidRFID";
				message="Invaid Phone Number";
				customeLogger.info(stationRefNum, "InvalidRFID : " + rfidPhno);
			}
			if (reserveNowValid && webSocketSessionObj != null && webSocketSessionObj.isOpen()) {
				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
				customeLogger.info(stationRefNum, "Reservation response Send to Charger : " + msg);
				response.setStatus("Accepted");
			} else {
				response.setMessage(message);
				response.setStatus("Rejected");
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("ReserveNow", uniqueID, StationId, reserveNowStatus, "",userId, 0, portId, "", "",String.valueOf(reserveNow.getReservationId()), "Portal");
				int timeout = 85;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
				
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for ReserveNow");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage(message);
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage triggerMessage(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {

			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "triggerMessage request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long StationId = of.getStationId();
			long portId = of.getConnectorId();
			long connectorId = stationService.getStationConnectorId(portId);
			String setting = of.getRequestType();
			String Value = of.getKey();
			String uniqueID = "";
			uniqueID = Utils.getStationRandomNumber(StationId) + ":TM";

			String msg = "[2,\"" + uniqueID + "\",\"TriggerMessage\",{\"requestedMessage\":\"" + of.getKey() + "\",\"connectorId\":" + connectorId + "}]";
			if (Optional.ofNullable((sessionswithstations.get(stationRefNum))).isPresent() && sessionswithstations.get(stationRefNum).isOpen()) {
				customeLogger.info(stationRefNum, "TriggerMessage response to Send Message :-" + msg);
				Utils.chargerMessage(sessionswithstations.get(stationRefNum), msg, stationRefNum);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueID,"Portal","TriggerMessage",msg,stationRefNum,StationId,"Inprogress",connectorId);
			} else {
				customeLogger.info(stationRefNum, "TriggerMessage response to Send Message :-" + msg);
				customeLogger.info(stationRefNum, "TriggerMessage response nto to Send to charger due to StationDisconnect :-");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","TriggerMessage",msg,stationRefNum,StationId,"Station Disconnected",connectorId);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("TriggerMessage", uniqueID, StationId, "Inprogress", "", 0, 0, portId, "",of.getKey(), "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for TriggerMessage");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage getLocalListVersion(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "getLocalListVersion request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long stationId = of.getStationId();
			long portId = of.getConnectorId();
			String setting = of.getRequestType();
			String uniqueId = "";
			uniqueId = Utils.getStationRandomNumber(stationId) + ":GLLV";

			String msg = "[2,\"" + uniqueId + "\",\"GetLocalListVersion\",{}]";

			if (Optional.ofNullable((sessionswithstations.get(stationRefNum))).isPresent()
					&& sessionswithstations.get(stationRefNum).isOpen()) {

				Utils.chargerMessage(sessionswithstations.get(stationRefNum), msg, stationRefNum);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueId,"Portal","GetLocalListVersion",msg,stationRefNum,stationId,"Inprogress",0);
			} else {
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueId,"Portal","GetLocalListVersion",msg,stationRefNum,stationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("GetLocalListVersion", uniqueId, stationId, "Inprogress", "", 0, 0, portId, "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueId);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					if(responseMessage.equalsIgnoreCase("Time Out")) {
						response.setStatus("Rejected");
					}else {
						response.setStatus("Accepted");
					}
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for GetLocalListVersion");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueId);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueId, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage cancelReservation(OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {
			final String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "cancel reservation request received from : "+of.getClientId()+" , portId : "+of.getConnectorId()+" , reservation Id : "+of.getReservationId());
			response.setClientId(stationRefNum);
			response.setClientId(stationRefNum);
			final long stationId = of.getStationId();
			final long portId = of.getConnectorId();
			String uniqueID = "";
			uniqueID = Utils.getStationRandomNumber(stationId) + ":CR";
			final long reservationId = of.getReservationId();
			long userId = stationService.getUserIdOnReservationId(reservationId, stationId);
			String msg = "[2,\"" + uniqueID + "\",\"CancelReservation\",{\"reservationId\":" + reservationId + "}]";
			if (Optional.ofNullable((sessionswithstations.get(stationRefNum))).isPresent() && sessionswithstations.get(stationRefNum).isOpen()) {
				customeLogger.info(stationRefNum, "Reservation cancel Request Message Send to charger : " + msg);
				Utils.chargerMessage(sessionswithstations.get(stationRefNum), msg, stationRefNum);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueID,"Portal","CancelReservation",msg,stationRefNum,stationId,"Inprogress",0);
			} else {
				customeLogger.info(stationRefNum,"Reservation cancel Request Message not Send to charger due to StationDisconnected");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","CancelReservation",msg,stationRefNum,stationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("CancelReservation", uniqueID, stationId, "Inprogress","", userId, 0, portId, "", "",String.valueOf(reservationId), "Portal");
				int timeout = 85;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for CancelReservation");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage sendLocalList(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {

			String msg = "";			
			String reason = "";
			String remarks = "StationDisconnected";
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "sendLocalList request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long StationId = of.getStationId();
			long portId = of.getConnectorId();
			String setting = of.getRequestType();
			String Value = of.getType();
			String uniqueId = "";			
			uniqueId = Utils.getStationRandomNumber(StationId) + ":SL";	
				
				List<Map<String, Object>> listOfArray = new ArrayList<Map<String, Object>>();

				JSONObject idTagInfo = new JSONObject();

				//idTagInfo.put("expiryDate", "2020-12-31T23:59:59.000Z");
				idTagInfo.put("expiryDate", Utils.getOneYearUTC());
				idTagInfo.put("parentIdTag", "PTAG");
				idTagInfo.put("status", "Accepted");
				of.getLocalAuthorizationLists().forEach(localListData -> {

					// Map<String, Object> localListmap=new HashMap<String, Object>();
					JSONObject localListmap = new JSONObject();
					localListmap.put("idTag", localListData);
					localListmap.put("idTagInfo", idTagInfo);
					listOfArray.add(localListmap);
				});
			
					msg = "[2,\"" + uniqueId + "\",\"SendLocalList\",{\"listVersion\":" + of.getListVersion()
							+ ",\"localAuthorizationList\":" + listOfArray + ",\"updateType\":\""
							+ of.getType() + "\"}]";

				List<LocalList> LocalList = statusSendingDataService.fetchSendLocalListData(StationId);
				statusSendingDataService.deleteSendLocalListData(StationId);

//				if (LocalList != null && of.getType().equalsIgnoreCase("Differential")) {
//					LocalList.forEach(list -> {
//						LocalAuthorizationList localAuthorizationLists = new LocalAuthorizationList();
//						localAuthorizationLists.setIdTag(list.getIdTag());
//						of.getLocalAuthorizationLists().add(localAuthorizationLists);
//					});
//					localList.setLocalAuthorizationLists(localList.getLocalAuthorizationLists());
//				}

				// List of LocalListData
				of.getLocalAuthorizationLists().forEach(localListData -> {

					try {
						// Map<String, Object> localListmap=new HashMap<String, Object>();
						JSONObject localListmap = new JSONObject();
						localListmap.put("idTag", of.getIdTag());
						localListmap.put("idTagInfo", idTagInfo);

						// customeLogger.info(stationId, "inside the SendLocal List Data" );
						statusSendingDataService.saveSendLocalListData(localListData, StationId,
								String.valueOf(of.getListVersion()));
					} catch (Exception e) {
						e.printStackTrace();
					}

//					try {
//						if(localList.getUpdateType().equalsIgnoreCase("full")) {
//							statusSendingDataService.saveSendLocalListData(localListData.getIdTag(), stationId, listVersion);
//						}else {
//							statusSendingDataService.saveSendLocalListData(localListData.getIdTag(), stationId, listVersion);
//							statusSendingDataService.updateSendLocalListData(localListData.getIdTag(), stationId,listVersion);
//						}
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}

				}); 
			if (Optional.ofNullable((sessionswithstations.get(stationRefNum))).isPresent()
					&& sessionswithstations.get(stationRefNum).isOpen()) {
				customeLogger.info(stationRefNum, "send local List Response Send to Charger :-" + msg);

				Utils.chargerMessage((sessionswithstations.get(stationRefNum)), msg, stationRefNum);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueId,"Portal","SendLocalList",msg,stationRefNum,StationId,"Inprogress",0);
			} else {
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueId,"Portal","SendLocalList",msg,stationRefNum,StationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("SendLocalList", uniqueId, StationId, "Inprogress", "", 0, 0, portId, "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueId);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for SendLocalList");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueId);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueId, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseMessage dataTransfer(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {
			
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "dataTransfer requesr received from : "+of.getClientId()+" , portId : "+of.getConnectorId()+" , message Id : "+of.getMessageId());
			response.setClientId(stationRefNum);
			long stationId = of.getStationId();
			long portId = of.getConnectorId();
			String setting = of.getRequestType();
			String vendorId = of.getVendorId();
			String messageId = of.getMessageId();
			String uniqueID = "";
			uniqueID =  Utils.getStationRandomNumber(stationId) + ":DT";
			String msg="";
			
			Field[] declaredFields = of.getData().getClass().getDeclaredFields();

			//Object data = datatransfer.getResponseData();
			String configurationKey = "";
			String configurationValue = "";
			String configurationKey2 = "";
			String configurationValue2 = "";

			if (messageId.equalsIgnoreCase("MaxSessionTimeOut")) {
				configurationValue = of.getData().getTime();
				configurationKey = declaredFields[1].getName();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			} else if (messageId.equalsIgnoreCase("OCPPEndpointToBackend")) {
				configurationKey = declaredFields[0].getName();
				configurationValue = of.getData().getUrl();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			} else if (messageId.equalsIgnoreCase("stationMode")) {
				configurationKey = declaredFields[2].getName();
				configurationValue = of.getData().getStationMode();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			} else if (messageId.equalsIgnoreCase("evbGPSNotification")) {
				configurationKey = declaredFields[3].getName();
				configurationValue = of.getData().getInfo();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\""
						+ messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			}else if (messageId.equalsIgnoreCase("SetUserPrice")) {
				configurationKey = declaredFields[3].getName();
				configurationValue = of.getData().getInfo();
				configurationKey2 = declaredFields[4].getName();
				configurationValue2 = of.getData().getPriceText();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\",\"" + configurationKey2 + "\":\"" + configurationValue2 +"\"}}]";
			} else {
				customeLogger.info(stationRefNum, "Data Transafer invalid case");
				configurationValue = of.getData().getInfo();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":" + configurationValue + "}]";

			}
			if (Optional.ofNullable((sessionswithstations.get(stationRefNum))).isPresent() && sessionswithstations.get(stationRefNum).isOpen()) {
				customeLogger.info(stationRefNum, "Data Transafer Response Send to Charger :-" + msg);
				Utils.chargerMessage(sessionswithstations.get(stationRefNum), msg, stationRefNum);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueID,"Portal","DataTransfer",msg,stationRefNum,stationId,"Inprogress",0);
			} else {
				customeLogger.info(stationRefNum, "Data Transafer Response not Send to Charger due to stationDisconnected :-");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","DataTransfer",msg,stationRefNum,stationId,"Station Disconnected",0);
			} 
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("DataTransfer", uniqueID, stationId, "InProgress","", 0,0, portId, messageId, configurationKey, configurationValue, "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for DataTransfer");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage custom(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {

			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "custom request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long stationId = of.getStationId();
			long portId = of.getConnectorId();
			String setting = of.getRequestType();
			String Value = of.getKey();
			//uniqueID = UUID.randomUUID().toString() + ":CSTM";
			String msg =  Value ;
			FinalData finalData = null;
			finalData = jsonDataParser.getData(msg, stationRefNum,null);
			String uniqueID = finalData.getSecondValue();
			if(msg==null) {
				msg ="";
				response.setStatus("Rejected");
			}
			if (Optional.ofNullable((sessionswithstations.get(stationRefNum))).isPresent() && sessionswithstations.get(stationRefNum).isOpen() && Value!=null && !Value.equals("")) {
				customeLogger.info(stationRefNum, "Custom Request Send to Charger :-" + msg);
				Utils.chargerMessage((sessionswithstations.get(stationRefNum)), msg, stationRefNum);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueID,"Portal","Custom",msg,stationRefNum,stationId,"Inprogress",0);
			} else {
				customeLogger.info(stationRefNum, "Custom Request not Send to Charger due to stationDisconnected ");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","Custom",msg,stationRefNum,stationId,"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("Custom", uniqueID, stationId, "InProgress", "", 0,0, portId, "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for Custom");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseMessage setChargingProfile(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		String msg = "";
		try {

			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "setChargingProfile request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long StationId = of.getStationId();
			long portId = of.getConnectorId();
			String setting = of.getRequestType();
			String chargingprofileInfo = of.getChargingProfileInfo();
			String validfrom = of.getValidFrom();
			String validTo = of.getValidTo();
			String chargingSchedule = of.getChargingSchedule();
			String Duration = of.getDuration();
			String StartPeriod = of.getStartPeriod();
			long limit = of.getLimit();
			String phno = of.getIdTag();
			String uniqueID = "";
			uniqueID = Utils.getStationRandomNumber(StationId) + ":SCP";
			String reason = "StationDisconnected";
			String message="";
			boolean flag=true;
			long connectorId = stationService.getStationConnectorId(portId);
			String chargingProfilepurpose = of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose().toString();
			String transactionId = stationService.getTransactionId(portId,StationId);
			List<ChargingSchedulePeriod> chargeScheduledPeriodList = new ArrayList<>(
					of.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator()
							.next().getChargingSchedulePeriod());
			Long stackLevel = stationService.getstackLevel(of.getConnectorId(),
					of.getStationId(),
					of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose());
			stackLevel = stackLevel + 1;
			Long chargingProfileId = stationService.getchargingProfileId(of.getConnectorId(),
					of.getStationId(),
					of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose());
			chargingProfileId = chargingProfileId + 1;
			stationService.insertChargingProfileDataForm(of, stackLevel, chargingProfileId);


			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);			
			Integer duration = (int) of.getCsChargingProfiles().iterator().next().getChargingSchedule()
						.iterator().next().getDuration();
				if (chargingProfilepurpose.equalsIgnoreCase("ChargePointMaxProfile")) {
					customeLogger.info(stationRefNum, "Inside ChargerPointMax Profile");
					if (String.valueOf(chargeScheduledPeriodList.iterator().next().getLimit()).equalsIgnoreCase("A")) {
						msg = "[2,\"" + uniqueID.toString()
								+ "\",\"SetChargingProfile\",{\"connectorId\":0,\"csChargingProfiles\":{\"chargingProfileId\":"
								+ chargingProfileId + ",\"stackLevel\":" + stackLevel + ","
								+ "\"chargingProfilePurpose\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
								+ "\"," + "\"chargingProfileKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfileKind()
								+ "\"" + ",\"recurrencyKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getRecurrencyKind() + "\","
								+ "\"validFrom\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidFrom() + "\","
								+ "\"validTo\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidTo() + "\""
								+ ",\"chargingSchedule\":" + "{\"duration\":" + of.getCsChargingProfiles()
										.iterator().next().getChargingSchedule().iterator().next().getDuration()
								+ "," + "\"chargingRateUnit\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingSchedule()
										.iterator().next().getChargingRateUnit()
								+ "\",\"duration\":" + duration + ",\"chargingSchedulePeriod\":[{\"startPeriod\":"
								+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
								+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
								+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";

					} else {
						String utcDateTime = Utils.getUTC();
						msg = "[2,\"" + uniqueID.toString()
								+ "\",\"SetChargingProfile\",{\"connectorId\":0,\"csChargingProfiles\":{\"chargingProfileId\":"
								+ chargingProfileId + ",\"stackLevel\":" + stackLevel + ","
								+ "\"chargingProfilePurpose\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
								+ "\"," + "\"chargingProfileKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfileKind()
								+ "\"" + ",\"recurrencyKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getRecurrencyKind() + "\","
								+ "\"validFrom\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidFrom() + "\","
								+ "\"validTo\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidTo() + "\""
								+ ",\"chargingSchedule\":" + "{\"startSchedule\":\"" + utcDateTime + "\","
								+ "\"chargingRateUnit\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingSchedule()
										.iterator().next().getChargingRateUnit()
								+ "\",\"duration\":" + duration + ",\"chargingSchedulePeriod\":[{\"startPeriod\":"
								+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
								+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
								+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";

					}
					
				} else if (chargingProfilepurpose.equalsIgnoreCase("TxDefaultProfile")
						|| chargingProfilepurpose == "TxDefaultProfile") {

					if (String.valueOf(chargeScheduledPeriodList.iterator().next().getLimit()).equalsIgnoreCase("A")) {
						msg = "[2,\"" + uniqueID.toString() + "\",\"SetChargingProfile\",{\"connectorId\":"
								+ connectorId + ",\"csChargingProfiles\":{\"chargingProfileId\":" + chargingProfileId
								+ ",\"stackLevel\":" + stackLevel + "," + "\"chargingProfilePurpose\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
								+ "\"," + "\"chargingProfileKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfileKind()
								+ "\"" + ",\"recurrencyKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getRecurrencyKind() + "\","
								+ "\"validFrom\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidFrom() + "\","
								+ "\"validTo\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidTo() + "\""
								+ ",\"chargingSchedule\":" + "{\"duration\":" + of.getCsChargingProfiles()
										.iterator().next().getChargingSchedule().iterator().next().getDuration()
								+ "," + "\"chargingRateUnit\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingSchedule()
										.iterator().next().getChargingRateUnit()
								+ "\",\"duration\":" + duration + "," + ",\"chargingSchedulePeriod\":[{\"startPeriod\":"
								+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
								+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
								+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";

					} else {
						String utcDateTime = Utils.getUTC();
						msg = "[2,\"" + uniqueID.toString() + "\",\"SetChargingProfile\",{\"connectorId\":"
								+ connectorId + ",\"csChargingProfiles\":{\"chargingProfileId\":" + chargingProfileId
								+ ",\"stackLevel\":" + stackLevel + "," + "\"chargingProfilePurpose\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
								+ "\"," + "\"chargingProfileKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfileKind()
								+ "\"" + ",\"recurrencyKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getRecurrencyKind() + "\","
								+ "\"validFrom\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidFrom() + "\","
								+ "\"validTo\":\""
								+ of.getCsChargingProfiles().iterator().next().getValidTo() + "\""
								+ ",\"chargingSchedule\":" + "{\"startSchedule\":\"" + utcDateTime + "\","
								+ "\"chargingRateUnit\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingSchedule()
										.iterator().next().getChargingRateUnit()
								+ "\",\"duration\":" + duration + ",\"chargingSchedulePeriod\":[{\"startPeriod\":"
								+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
								+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
								+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";

					}
				} else if (chargingProfilepurpose.equalsIgnoreCase("TxProfile")) {

					if (String.valueOf(chargeScheduledPeriodList.iterator().next().getLimit()).equalsIgnoreCase("A")) {
						msg = "[2,\"" + uniqueID.toString() + "\",\"SetChargingProfile\",{\"connectorId\":"
								+ connectorId + ",\"csChargingProfiles\":{\"chargingProfileId\":" + chargingProfileId
								+ ",\"stackLevel\":" + stackLevel + ",\"transactionId\":" + transactionId + ","
								+ "\"chargingProfilePurpose\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
								+ "\"," + "\"chargingProfileKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfileKind()
								+ "\"" + ",\"chargingSchedule\":" + "{\"duration\":"
								+ of.getCsChargingProfiles().iterator().next().getChargingSchedule()
										.iterator().next().getDuration()
								+ "," + "\"chargingRateUnit\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingSchedule()
										.iterator().next().getChargingRateUnit()
								+ "\",\"duration\":" + duration + ",\"chargingSchedulePeriod\":[{\"startPeriod\":"
								+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
								+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
								+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";

					} else {
						String utcDateTime = Utils.getUTC();
						msg = "[2,\"" + uniqueID.toString() + "\",\"SetChargingProfile\",{\"connectorId\":"
								+ connectorId + ",\"csChargingProfiles\":{\"chargingProfileId\":" + chargingProfileId
								+ ",\"stackLevel\":" + stackLevel + ",\"transactionId\":" + transactionId + ","
								+ "\"chargingProfilePurpose\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
								+ "\"," + "\"chargingProfileKind\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingProfileKind()
								+ "\"" + ",\"chargingSchedule\":" + "{\"startSchedule\":\"" + utcDateTime + "\","
								+ "\"chargingRateUnit\":\""
								+ of.getCsChargingProfiles().iterator().next().getChargingSchedule()
										.iterator().next().getChargingRateUnit()
								+ "\",\"duration\":" + duration + ",\"chargingSchedulePeriod\":[{\"startPeriod\":"
								+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
								+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
								+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";

					}

				} else {
					customeLogger.info(stationRefNum,
							"Station is not connected to Ocpp Server for Sending chargingprofile");
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
					flag=false;
				}
				customeLogger.info(stationRefNum, "Chargingprofile message : " + msg);
				if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()&&flag) {
					Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
					response.setStatus("Accepted");
					esLoggerUtil.insertLongs(uniqueID,"Portal","SetChargingProfile",msg,stationRefNum,StationId,"Inprogress",0);
				} else {
					customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for Semdomh chargingprofile");
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
					esLoggerUtil.insertLongs(uniqueID,"Portal","SetChargingProfile",msg,stationRefNum,StationId,"Station Disconnected",0);
		    	}
				if(response.getStatus().equalsIgnoreCase("Accepted")) {
					statusSendingDataService.addData("SetChargingProfile", uniqueID, StationId, "Inprogress","", 0, 0, of.getConnectorId(), "", "", "","Portal");
					int timeout = 90;
					String responseStatus="";
					String responseMessage="";
					while (timeout > 0) {
						Thread.sleep(3000);
						Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
						timeout -= 3;
						responseStatus = String.valueOf(statusFromRequest.get("status"));
						responseMessage=String.valueOf(statusFromRequest.get("response"));
						//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
						if (!responseStatus.equalsIgnoreCase("Inprogress")) {
							timeout = 0;
						}
					}
					responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
					if(!responseStatus.equalsIgnoreCase("Accepted")) {
						response.setStatus("Rejected");
						response.setMessage(responseMessage);
					}
					else {
						response.setStatus("Accepted");
						response.setMessage("");
					}
				}else {
					customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for SetChargingProfile");
					Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
					Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
					statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
				}
				response.setTimestamp(new Date());
				response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessage clearChargingProfile(OCPPForm of,Map<String, WebSocketSession> sessionswithstations)   {
		ResponseMessage response=new ResponseMessage();
		try {
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "clearChargingProfile request received from : "+of.getClientId()+" , portId : "+of.getConnectorId()+" , profile Id"+of.getProfileId()+" , chargingProfilePurpose : "+of.getChargingProfilePurpose());
			response.setClientId(stationRefNum);
			final long StationId = of.getStationId();
			final long portId = of.getConnectorId();
			final String chargingProfilePurpose = of.getChargingProfilePurpose();
			final int stackLevel = of.getStackLevel();
			final int profileId = of.getProfileId();
			final long connector_Id = stationService.getStationConnectorId(portId);
			String uniqueId = "";
			uniqueId = Utils.getStationRandomNumber(StationId) + ":CCP";
			String msg = "";
			
				if (chargingProfilePurpose.equalsIgnoreCase("All")) {
					msg = "[2,\"" + uniqueId + "\",\"ClearChargingProfile\",{}]";
				} else {
					msg = "[2,\"" + uniqueId + "\",\"ClearChargingProfile\",{\"connectorId\":" + connector_Id
							+ ",\"chargingProfilePurpose\":\"" + chargingProfilePurpose + "\",\"stackLevel\":"
							+ stackLevel + ",\"id\":"
							+ profileId + "}]";
				}
				if (Optional.ofNullable((sessionswithstations.get(stationRefNum))).isPresent() && sessionswithstations.get(stationRefNum).isOpen()) {
					customeLogger.info(stationRefNum, "ClearChargingProfile response to Send Message :-" + msg);
					Utils.chargerMessage(sessionswithstations.get(stationRefNum), msg, stationRefNum);
					String query = "Delete from ocpp_chargingProfile where stationId ='" + StationId + "'";
					executeRepository.update(query);
					response.setStatus("Accepted");
					esLoggerUtil.insertLongs(uniqueId,"Portal","ClearChargingProfile",msg,stationRefNum,StationId,"Inprogress",0);
				} else {
					customeLogger.info(stationRefNum, "ClearChargingProfile response nto to Send to charger due to StationDisconnect :-");
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
					esLoggerUtil.insertLongs(uniqueId,"Portal","ClearChargingProfile",msg,stationRefNum,StationId,"StationDisconnect",0);
			   }
				if(response.getStatus().equalsIgnoreCase("Accepted")) {
					statusSendingDataService.addData("ClearChargingProfile", uniqueId, StationId, "Inprogress","", 0, 0,portId, "", "", "", "Portal");
					int timeout = 90;
					String responseStatus="";
					String responseMessage="";
					while (timeout > 0) {
						Thread.sleep(3000);
						Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueId);
						timeout -= 3;
						responseStatus = String.valueOf(statusFromRequest.get("status"));
						responseMessage=String.valueOf(statusFromRequest.get("response"));
						//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
						if (!responseStatus.equalsIgnoreCase("Inprogress")) {
							timeout = 0;
						}
					}
					responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
					if(!responseStatus.equalsIgnoreCase("Accepted")) {
						response.setStatus("Rejected");
						response.setMessage(responseMessage);
					}
					else {
						response.setStatus("Accepted");
						response.setMessage("");
					}
				}else {
					customeLogger.info(stationRefNum, "Station is not connected to Ocpp Server for ClearChargingProfile");
					Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueId);
					Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
					statusSendingDataService.updateResetStausInPortal(uniqueId, "StationDisconnected", "", statusId);
					response.setMessage("Station Disconnected");
					response.setStatus("Rejected");
				}
				response.setTimestamp(new Date());
				response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	@Override
	public ResponseMessage remoteStartPowerSharging(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
		try {
			if(of.getConnectorId() == 0) {
				String query = "select p.id,m.manfname,p.status from port p inner join station st inner join manufacturer m on st.manufacturerId = m.id on p.station_id = st.id where p.station_Id="+of.getStationId()+" ";
						//+ " and (p.status = 'Available' or p.status ='preparing' or p.status ='finishing' or p.status ='Removed' or p.status ='planned')" ;
				List<Map<String, Object>> findAll = executeRepository.findAll(query);
				List<ResponseMessage> responseLs = new ArrayList<>();
				if(findAll.size() > 0 && String.valueOf(findAll.get(0).get("manfname")).equalsIgnoreCase("FLO")) {
					boolean charging = findAll.stream().flatMap(map -> map.values().stream()).allMatch(value -> String.valueOf(value).equalsIgnoreCase("Charging"));
					if(charging) {
						ResponseMessage temp = new ResponseMessage();
						temp.setStatus("Rejected");
						temp.setMessage("Occupied");
						responseLs.add(temp);
					}else {
						List<Map<String, Object>> filteredMaps = findAll.stream()
				                .filter(map -> map.containsValue("Available") || map.containsValue("Preparing") || map.containsValue("Planned") || map.containsValue("Finishing") || map.containsValue("Removed"))
				                .collect(Collectors.toList());
						if(filteredMaps.size() > 0) {
							final OCPPForm ofs = of;
							Long connectorId = Long.valueOf(String.valueOf(findAll.get(0).get("id")));
							ofs.setConnectorId(connectorId);
							ResponseMessage temp = remoteStart(ofs,sessionswithstations);
							logger.info("rst 1724>> response : "+temp);
							responseLs.add(temp);
						}else {
							ResponseMessage temp = new ResponseMessage();
							temp.setStatus("Rejected");
							temp.setMessage("Unavailable");
							responseLs.add(temp);
						}
						
					}
				}else {
					for(Map<String,Object>map : findAll) {
						final OCPPForm ofs = of;
						Long connectorId = Long.valueOf(String.valueOf(map.get("id")));
						ofs.setConnectorId(connectorId);
						
						Thread th = new Thread () {
							public void run() {
								ResponseMessage temp = remoteStart(ofs,sessionswithstations);
								logger.info("rst 1733>> response : "+temp);
								responseLs.add(temp);
							}
						};
						th.start();
						try {
							th.join();
						}catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
				for(ResponseMessage rs : responseLs){
					if(rs.getStatus().equalsIgnoreCase("Accepted")) {
						response = rs;
					}
				};
				if(response.getStatus().equalsIgnoreCase("Rejected")) {
					for(ResponseMessage rs : responseLs){
						if(!rs.getMessage().equalsIgnoreCase("")) {
							response.setMessage(rs.getMessage());
						}
					};
				}
			}else {
				ResponseMessage remoteStart = remoteStart(of,sessionswithstations);
				logger.info("rst 1752>> response : "+remoteStart);
				response = remoteStart;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public ResponseMessage remoteStart(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
		response.setApiRequestId(of.getApiRequestId());
		remoteStart rstForm = new remoteStart();
		try {
			rstForm.setStn_referNo(stationService.getstationRefNum(of.getStationId()));
			customeLogger.info(rstForm.getStn_referNo(), "remoteStart request received from : "+of.getClientId());
			response.setClientId(of.getClientId() == null ? "" : of.getClientId());
			rstForm.setStationId(of.getStationId());
			rstForm.setSelfCharging(of.isSelfCharging());
			rstForm.setPortId(of.getConnectorId());
			rstForm.setRst_unqReqId(Utils.getStationRandomNumber(rstForm.getStationId()) + ":RST");
			rstForm.setRst_msg("");
			rstForm.setRst_paymentType(of.getPaymentType());
			rstForm.setConnectorId(1);
			rstForm.setStn_orgId(of.getOrgId());
			rstForm.setRst_rcvd_client(of.getClientId() == null ? "" : of.getClientId());
			rstForm.setIdTag(of.getIdTag());
			rstForm.setRst_reason("");
			rstForm.setRst_rewardType(of.getRewardType());
			try {
				rstForm.setStnObj(stationService.getStnObjByUniIds(rstForm.getStationId(),rstForm.getPortId()));
				rstForm.setSiteObj(stationService.getSiteDetails(rstForm.getStationId()));
				rstForm = remoteStartService.getRemoteStartUserValidate(rstForm);
				WebSocketSession chargerWebSessionObj = sessionswithstations.get(rstForm.getStn_referNo());
				if (rstForm.getStnObj() == null || rstForm.getStnObj().size() == 0 || rstForm.getStnObj().isEmpty()) {
					rstForm.setRst_reason("InValid Station");
					rstForm.setRst_Valid(false);
				} else if(rstForm.getStnObj().size() != 0){
					rstForm.setConnectorId(Long.valueOf(String.valueOf(rstForm.getStnObj().get("connector_id"))));
					rstForm.setPowerSharging(String.valueOf(rstForm.getStnObj().get("powerSharing")).equalsIgnoreCase("N") ? false : true);
					rstForm = remoteStartService.getRemoteStartStnValidate(rstForm);
				}
				ocppRemoteStartTransactionService.addRemoteStartTransaction(rstForm);
				logger.info("rst valid : "+rstForm.isRst_Valid());
				if(rstForm.isRst_Valid()) {
					if(chargerWebSessionObj != null && chargerWebSessionObj.isOpen()) {
						Thread.sleep(2000);
						rstForm.setRst_msg("[2,\"" + rstForm.getRst_unqReqId() + "\",\"RemoteStartTransaction\",{\"connectorId\":" + rstForm.getConnectorId() + ",\"idTag\":\"" + rstForm.getIdTag() + "\"}]");
						Utils.chargerMessage(chargerWebSessionObj, rstForm.getRst_msg(), rstForm.getStn_referNo());
						response.setStatus("Accepted");
						ocppActiveTransactionsService.insertIntoActiveTransactions(rstForm);
						esLoggerUtil.insertLongs(rstForm.getRst_unqReqId(),"Portal","RemoteStartTransaction",rstForm.getRst_msg(),rstForm.getStn_referNo(),rstForm.getStationId(),"Inprogress",Long.valueOf(String.valueOf(rstForm.getStnObj().get("connector_id"))));
					}else {
						logger.info("charger is not connected");
						response.setStatus("Rejected");
						response.setMessage("Station Disconnected");
						esLoggerUtil.insertLongs(rstForm.getRst_unqReqId(),"Portal","RemoteStartTransaction",rstForm.getRst_msg(),rstForm.getStn_referNo(),rstForm.getStationId(),"Station Disconnected",Long.valueOf(String.valueOf(rstForm.getStnObj().get("connector_id"))));
					}
				} else {
					response.setStatus("Rejected");
					response.setMessage(rstForm.getRst_reason());
				}
				if(response.getStatus().equalsIgnoreCase("Accepted")) {
					Thread.sleep(1000);
					statusSendingDataService.addData("RemoteStartTransaction", rstForm.getRst_unqReqId(),rstForm.getStationId(), "Inprogress", "", Long.valueOf(String.valueOf(rstForm.getUserObj() == null ? 0 : rstForm.getUserObj().get("UserId"))), 0,rstForm.getPortId(), "", "","",of.getClientId());
					int timeout = 85;
					String responseStatus="";
					String responseMessage="Rejected";
					while (timeout > 0) {
						Thread.sleep(3000);
						Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(rstForm.getRst_unqReqId());
						timeout -= 3;
						responseStatus = String.valueOf(statusFromRequest.get("status"));
						responseMessage=String.valueOf(statusFromRequest.get("response"));
						if (!responseStatus.equalsIgnoreCase("Inprogress")) {
							timeout = 0;
						}
					}
					responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
					rstForm.setRst_reason(responseMessage);
					response.setMessage("");
					if(!responseStatus.equalsIgnoreCase("Accepted")) {
						rstForm.setRst_Valid(false);
						response.setStatus("Rejected");
						response.setMessage(responseMessage);
						ocppMeterValueService.deleteRejectedStation(rstForm.getRst_unqReqId());
					}
				}else {
					statusSendingDataService.addData("RemoteStartTransaction", rstForm.getRst_unqReqId(),rstForm.getStationId(),response.getMessage(), "", Long.valueOf(String.valueOf(rstForm.getUserObj() != null ? rstForm.getUserObj().get("UserId") : 0)), 0,rstForm.getPortId(), "", "","",of.getClientId());
					customeLogger.info(rstForm.getStn_referNo(), response.getMessage());
					Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(rstForm.getRst_unqReqId());
					Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
					statusSendingDataService.updateResetStausInPortal(rstForm.getRst_unqReqId(), response.getMessage(), "", statusId);
				}
				failedSessionService.insertIntoFailedSessionsRST(rstForm);
				response.setTimestamp(new Date());
				response.setStatusCode(HttpStatus.OK.value());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus("Rejected");
				statusSendingDataService.addData("RemoteStartTransaction", rstForm.getRst_unqReqId(),0, "Error occured", "", 0, 0,0, "", "","",of.getClientId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public ResponseMessage remoteStartWithSmartCharging(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
		try {
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "remoteStartWithSmartCharging request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			long stationId = of.getStationId();
			String uniqueID =  Utils.getStationRandomNumber(stationId)+ ":RST";
			String status = "Rejected";
			String msg = "";
			Map<String, Object> manFacturerUserBean = new HashMap<>();
			boolean preProduction = false;
			Map<String, Object> driverGroupIdTag = new HashMap<>();
			boolean driverGrpIdTag = false;
			boolean manualIdTagCheck = false;
			String siteId = "0";
			Map<String, Object> manualIdTag = new HashMap<>();
			String message="Station Disconnected";
			try {
				String reason = "";
				Long orgId = of.getOrgId();
				long portUniId = 0;
				String appVersion = "v1";
				long userId = 0;
				String idTag = of.getIdTag();
				Map<String, Object> stationObj = stationService.getStnByRefNum(stationRefNum);
				long stationConnectorId = 1;
				boolean unKnownUser=false;
				RemoteStartWithSmartCharging remoteStartReqst = new RemoteStartWithSmartCharging();
				remoteStartReqst.setConnectorId(of.getConnectorId());
				remoteStartReqst.setIdTag(idTag);
				remoteStartReqst.setOrgId(of.getOrgId());
				remoteStartReqst.setStationRefNum(stationRefNum);
				remoteStartReqst.setVersion("V1");
				WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
				String webSocketSessionId=webSocketSessionObj==null?"":webSocketSessionObj.getId();
				
				Map<String,Object> user = null;
				if (stationObj == null || stationObj.size() == 0 || stationObj.isEmpty()) {
					reason = "InValid Station";
					message="InValid Station";
				} else if(stationObj.size() != 0){
					 stationId = Long.valueOf(stationObj.get("id").toString());
					 portUniId = remoteStartReqst.getConnectorId();
					preProduction = stationObj.get("preProduction").toString().equalsIgnoreCase("1") ? true : stationObj.get("preProduction").toString().equalsIgnoreCase("true") ? true : false;
					
					stationConnectorId = stationService.getStationConnectorId(remoteStartReqst.getConnectorId());
					Credentials credentials = ocppAccountAndCredentialService.getCredentialByrfId(idTag);
					//Fetching User Object Using rfid or Phone
					if(!Optional.ofNullable(credentials).isPresent() && preProduction) {
						manFacturerUserBean = ocppFreeChargingService.getUserIdOnBaseRfid(idTag);
						userId = Long.valueOf(String.valueOf(manFacturerUserBean.get("user_id")));
					}else {
						Map<String, Object> siteObj = stationService.getSiteDetails(stationId);
						siteId = String.valueOf(siteObj.get("siteId"));
						long accId = ocppFreeChargingService.getAccIdOnBaseIdtag(idTag);
						userId = ocppFreeChargingService.getUserIdOnBaseAccId(accId);
						driverGroupIdTag = ocppFreeChargingService.getDriverGroupIdTag(idTag);
						if(driverGroupIdTag.size()>0) {
							driverGrpIdTag = Boolean.valueOf(String.valueOf(driverGroupIdTag.get("dgiFlag")));
						}
						manualIdTag=userService.manualIdCheck(idTag,Long.valueOf(String.valueOf(stationObj.get("manfId"))),stationId,Long.valueOf(siteId));
						manualIdTagCheck= Boolean.valueOf(manualIdTag.get("flag").toString());
						if(userId == 0 && !driverGrpIdTag && !manualIdTagCheck && stationObj.get("stationMode").toString().equalsIgnoreCase("Freeven")) {
							unKnownUser = true;
						}else {
							logger.info("unKnownUser : "+unKnownUser);
						}
					}
					//station Status
					List<Map<String,Object>> stationStatusa = statusNotificationDao.getPortStatus(stationId, portUniId);
					String stationStatus = "Inoperative";
					if(stationStatusa.size() > 0) {
						stationStatus = String.valueOf(stationStatusa.get(0).get("status"));
					}
					
					reason = webSocketSessionObj == null ? "StationDisconnected" :
							((stationStatus.equalsIgnoreCase("Available")) || (stationStatus.equalsIgnoreCase("Preparing"))	|| stationStatus.equalsIgnoreCase("Removed")	|| stationStatus.equalsIgnoreCase("Planned")) ? "Finishing" :
							stationStatus.equalsIgnoreCase("Inoperative") ? "UnAvailable" :
							stationStatus.equalsIgnoreCase("Blocked") ? "Faulted" :
							stationStatus.equalsIgnoreCase("Charging") ? "Charging" :
							stationStatus.equalsIgnoreCase("DriverGroup") ? "Rejected Due to driverGroups" : "StationDisconnected";
					//for Checking user Releated to EvgGateway Or not
					if(Optional.ofNullable(credentials).isPresent()  && userId!=0 && !driverGrpIdTag && !manualIdTagCheck && !unKnownUser) {
						user = ocppFreeChargingService.accntsBeanObj(userId);
						
						boolean userReserved = ocppFreeChargingService.getReservationFlag(userId,stationId,portUniId);
						//Inserting the data Into OcppStartTransaction
						ocppRemoteStartTransactionService.addRemoteStartTransaction(remoteStartReqst.getConnectorId(), idTag,"", stationId, userId, uniqueID, webSocketSessionId,of.getPaymentType());
					
						
						if ((stationStatus.equalsIgnoreCase("Available") || (stationStatus.equalsIgnoreCase("Preparing") && !userReserved)
								|| stationStatus.equalsIgnoreCase("Removed") || stationStatus.equalsIgnoreCase("Planned")) && Optional.ofNullable(webSocketSessionObj).isPresent()) {
							
						//	map = remotStartTransaction(stationObj, user, remoteStartReqst, sessionWithStations, uniqueID.toString(),session,portUniId,stationConnectorId,appVersion,orgId);
							status = "Accepted";
							reason = "";
						} else if (((stationStatus.equalsIgnoreCase("Reserved") && userReserved) && Optional.ofNullable(webSocketSessionObj).isPresent()) || (userReserved && Optional.ofNullable(webSocketSessionObj).isPresent())) {
							
							if(ocppFreeChargingService.getReservationFlag(userId,stationId,portUniId)) {
								//map = remotStartTransaction(stationObj, user, remoteStartReqst, sessionWithStations, uniqueID.toString(),session,portUniId,stationConnectorId,appVersion,orgId);	
								status = "Accepted";
								reason = "";
							}else{
								reason="Reserved";
								status="Reserved";
								message="Reserved";
							}
						}else {
							reason=reason.replace("[", "").replace("]", "");
							status=reason;
						}
					} else if( !preProduction && !driverGrpIdTag && !manualIdTagCheck && !unKnownUser) {
						// GuestUserImplementation and In valid MobileNumber
						Map<String, Object> userType = ocppFreeChargingService.getUserType(remoteStartReqst.getIdTag(),stationId);
						Boolean guestUserCheck = Boolean.valueOf(userType.get("flagValue").toString());
						if(guestUserCheck && (stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing") 
									|| stationStatus.equalsIgnoreCase("Removed")|| stationStatus.equalsIgnoreCase("Planned")) && Optional.ofNullable(webSocketSessionObj).isPresent() && (!stationObj.get("stationMode").toString().equalsIgnoreCase("driverGroup"))) {
								
								
								Optional<WebSocketSession> checkNullable = Optional.ofNullable(webSocketSessionObj);
								if (checkNullable.isPresent()) {
									
									ocppMeterValueService.activeTransactions(portUniId, 0,stationId, "RemoteStartTransaction", userId, remoteStartReqst.getIdTag(),
											webSocketSessionId,"Inprogress", "Preparing", "", 0, "",uniqueID,stationRefNum,appVersion,orgId,"Wallet","",false);
									
									reason = "Guest User";
									status = "Accepted";
								} else {
									reason="Invalid PhoneNumber";
									status = "Invalid PhoneNumber";
									message="Invaid Phone Number";
								}
								
						} else {
							reason = guestUserCheck == false ? "Invalid RFID/PhoneNumber" : (stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing")  
									|| stationStatus.equalsIgnoreCase("Removed")) == false ? stationStatus : (stationObj.get("stationMode").toString().equalsIgnoreCase("driverGroup")) == true ? "Allows driver group members" : Optional.ofNullable(webSocketSessionObj).isPresent() == false ? "StationDisconnected" : "Invalid RFID/PhoneNumber" ;
							status = "Rejected";
							message=reason;
						}
					}else if(preProduction) {
						//pre production test user case
						if(userId != 0 && (stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing") 
									|| stationStatus.equalsIgnoreCase("Removed")|| stationStatus.equalsIgnoreCase("Planned")) && Optional.ofNullable(webSocketSessionObj).isPresent() && (!stationObj.get("stationMode").toString().equalsIgnoreCase("driverGroup"))) {
								
								Optional<WebSocketSession> checkNullable = Optional.ofNullable(webSocketSessionObj);
								if (checkNullable.isPresent()) {
									
									ocppMeterValueService.activeTransactions(portUniId, 0,stationId, "RemoteStartTransaction", userId, remoteStartReqst.getIdTag(),
											webSocketSessionId,"Inprogress", "Preparing", "", 0, "",uniqueID,stationRefNum,appVersion,orgId,"Wallet","",false);
									
									reason = "Guest User";
									status = "Accepted";
								} else {
									reason="Invalid PhoneNumber";
									status = "Invalid PhoneNumber";
									message="Invalid Phone Number";
								}
								
						} else {
							reason = userId == 0 ? "Invalid RFID/PhoneNumber" : (stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing")  
									|| stationStatus.equalsIgnoreCase("Removed")) == false ? stationStatus : (stationObj.get("stationMode").toString().equalsIgnoreCase("driverGroup")) == true ? "Allows driver group members" : Optional.ofNullable(webSocketSessionObj).isPresent() == false ? "StationDisconnected" : "Invalid RFID/PhoneNumber" ;
							status = "Rejected";
							message=reason;
						}
					}else if(driverGrpIdTag){
						if((stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing") 
								|| stationStatus.equalsIgnoreCase("Removed")|| stationStatus.equalsIgnoreCase("Planned")) && 
								Optional.ofNullable(webSocketSessionObj).isPresent()) {
							
							Optional<WebSocketSession> checkNullable = Optional.ofNullable(webSocketSessionObj);
							if (checkNullable.isPresent()) {
								
								ocppMeterValueService.activeTransactions(portUniId, 0,stationId, "RemoteStartTransaction", userId, remoteStartReqst.getIdTag(),
										webSocketSessionId,"Inprogress", "Preparing", "", 0, "",uniqueID,stationRefNum,appVersion,orgId,"Wallet","",false);
								
								reason = "";
								status = "Accepted";
							} else {
								reason = "";
								status = "Rejected";
							}
							
					}
					}else if(manualIdTagCheck) {
						if((stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing") 
								|| stationStatus.equalsIgnoreCase("Removed")|| stationStatus.equalsIgnoreCase("Planned")) && 
								Optional.ofNullable(webSocketSessionObj).isPresent()) {
							
							Optional<WebSocketSession> checkNullable = Optional.ofNullable(webSocketSessionObj);
							if (checkNullable.isPresent()) {
								
								ocppMeterValueService.activeTransactions(portUniId, 0,stationId, "RemoteStartTransaction", userId, remoteStartReqst.getIdTag(),
										webSocketSessionId,"Inprogress", "Preparing", "", 0, "",uniqueID,stationRefNum,appVersion,orgId,"Wallet","",false);
								
								reason = "";
								status = "Accepted";
							} else {
								reason = "";
								status = "Rejected";
							}
						
					}
						ocppRemoteStartTransactionService.addRemoteStartTransaction(remoteStartReqst.getConnectorId(), idTag,status, stationId, userId, uniqueID, webSocketSessionId,of.getPaymentType());
					}else if (unKnownUser) {
						if((stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing") 
								|| stationStatus.equalsIgnoreCase("Removed")|| stationStatus.equalsIgnoreCase("Planned")) && 
								Optional.ofNullable(webSocketSessionObj).isPresent()) {
							
							Optional<WebSocketSession> checkNullable = Optional.ofNullable(webSocketSessionObj);
							if (checkNullable.isPresent()) {
								
								ocppMeterValueService.activeTransactions(portUniId, 0,stationId, "RemoteStartTransaction", userId, remoteStartReqst.getIdTag(),
										webSocketSessionId,"Inprogress", "Preparing", "", 0, "",uniqueID,stationRefNum,appVersion,orgId,"Wallet","",false);
								
								reason = "";
								status = "Accepted";
							} else {
								reason = "";
								status = "Rejected";
							}
						}else {
							reason = (stationStatus.equalsIgnoreCase("Available") || stationStatus.equalsIgnoreCase("Preparing")  
									|| stationStatus.equalsIgnoreCase("Removed")) == false ? stationStatus : (stationObj.get("stationMode").toString().equalsIgnoreCase("driverGroup")) == true ? "Allows driver group members" : Optional.ofNullable(webSocketSessionObj).isPresent() == false ? "StationDisconnected" : "Invalid RFID/PhoneNumber" ;
							status = "Rejected";
							message=reason;
						}
					}
					
					//Storing the OcppStatusSending For PortalPurpose
					
					//ocppStatusSendingDataService.updateResetStausInPortal(uniqueID, updateResetStatusForPortal,"RemoteStopTransaction");
				}
				response.setMessage(message);
				String[] split = uniqueID.split(":",2);
				String reqId = null;
				if(split.length == 2) {
					reqId = split[0];
				}
				//String transactionId = stationService.getTransactionId(chargingProfile.getConnectorId(),
						//chargingProfile.getStationId());
				List<ChargingSchedulePeriod> chargeScheduledPeriodList = new ArrayList<>(remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator()
								.next().getChargingSchedulePeriod());
				//Long stackLevel = stationService.getstackLevel(remoteStartReqst.getConnectorId(),
						//remoteStartReqst.getStationId(),
						//remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingProfilePurpose());
				//stackLevel = stackLevel + 1;
				//Long chargingProfileId = stationService.getchargingProfileId(remoteStartReqst.getConnectorId(),
					//	remoteStartReqst.getStationId(),
					//	remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingProfilePurpose());
				//chargingProfileId = chargingProfileId + 1;
				//stationService.insertChargingProfileData(chargingProfile, stackLevel, chargingProfileId);
				Integer duration = (int) remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingSchedule()
						.iterator().next().getDuration();
//			if(status.equalsIgnoreCase("Accepted")) {
//				String utcDateTime = Utils.getUTC();
//							msg = "[2,\"" + uniqueID + "\",\"RemoteStartTransaction\",{\"connectorId\":" + stationConnectorId 
//								+ ",\"idTag\":\"" + remoteStartReqst.getIdTag() 
//								+ "\",\"csChargingProfiles\":{\"chargingProfileId\":1"
//								+ ",\"stackLevel\":1," + "\"chargingProfilePurpose\":\""
//								+ remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
//								+ "\"," + "\"chargingProfileKind\":\""
//								+ remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingProfileKind()
//								+ "\"" + ",\"recurrencyKind\":\""
//								+ remoteStartReqst.getCsChargingProfiles().iterator().next().getRecurrencyKind() + "\","
//								+ "\"validFrom\":\""
//								+ remoteStartReqst.getCsChargingProfiles().iterator().next().getValidFrom() + "\","
//								+ "\"validTo\":\""
//								+ remoteStartReqst.getCsChargingProfiles().iterator().next().getValidTo() + "\""
//								+ ",\"chargingSchedule\":" + "{\"startSchedule\":\"" + utcDateTime + "\","
//								+ "\"chargingRateUnit\":\""
//								+ remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingSchedule()
//										.iterator().next().getChargingRateUnit()
//								+ "\",\"duration\":"+duration+",\"chargingSchedulePeriod\":[{\"startPeriod\":"
//								+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
//								+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
//								+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";
//				session = webSocketSessionObj;
//				Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
//				customLogger.info(stationRefNum, "Remote Start Transaction Request Accpted .....");
//			} 
				 if(status.equalsIgnoreCase("Accepted") && webSocketSessionObj != null && webSocketSessionObj.isOpen()) {
					 String utcDateTime = Utils.getUTC();
						msg = "[2,\"" + uniqueID + "\",\"RemoteStartTransaction\",{\"connectorId\":" + stationConnectorId 
							+ ",\"idTag\":\"" + remoteStartReqst.getIdTag() 
							+ "\",\"csChargingProfiles\":{\"chargingProfileId\":1"
							+ ",\"stackLevel\":1," + "\"chargingProfilePurpose\":\""
							+ remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingProfilePurpose()
							+ "\"," + "\"chargingProfileKind\":\""
							+ remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingProfileKind()
							+ "\"" + ",\"recurrencyKind\":\""
							+ remoteStartReqst.getCsChargingProfiles().iterator().next().getRecurrencyKind() + "\","
							+ "\"validFrom\":\""
							+ remoteStartReqst.getCsChargingProfiles().iterator().next().getValidFrom() + "\","
							+ "\"validTo\":\""
							+ remoteStartReqst.getCsChargingProfiles().iterator().next().getValidTo() + "\""
							+ ",\"chargingSchedule\":" + "{\"startSchedule\":\"" + utcDateTime + "\","
							+ "\"chargingRateUnit\":\""
							+ remoteStartReqst.getCsChargingProfiles().iterator().next().getChargingSchedule()
									.iterator().next().getChargingRateUnit()
							+ "\",\"duration\":"+duration+",\"chargingSchedulePeriod\":[{\"startPeriod\":"
							+ chargeScheduledPeriodList.iterator().next().getStartPeriod() + "," + "\"limit\":"
							+ chargeScheduledPeriodList.iterator().next().getLimit() + "," + "\"numberPhases\":"
							+ chargeScheduledPeriodList.iterator().next().getNumberPhases() + "}]}}}]";				
						Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
						esLoggerUtil.insertLongs(uniqueID,"Portal","RemoteStartTransaction",msg,stationRefNum,stationId,"Inprogress",0);
						customeLogger.info(stationRefNum, "Remote Start Transaction Request Accpted .....");
						response.setStatus("Accepted");
				 	} else if(reason.equalsIgnoreCase("Allows driver group members")){
						response.setStatus("Rejected");
						response.setMessage("Station not in public group");
					} else if(reason.equalsIgnoreCase("Invalid RFID/PhoneNumber")){
						response.setStatus("Rejected");
						response.setMessage("Invalid Phone Number");
					}else if(reason.equalsIgnoreCase("InSufficient Balance")) {
						response.setStatus("Rejected");
						response.setMessage("Low balance");
					}else{
						response.setStatus("Rejected");
						if(status.equalsIgnoreCase("Charging")) {
							response.setMessage("EVSE_OCCUPIED");
						}else if(status.equalsIgnoreCase("UnAvailable") || status.equalsIgnoreCase("Inoperative")) {
							response.setMessage("EVSE_INOPERATIVE");
						}else {
							response.setMessage("Station Disconnected");
						}
					}
					
					if(response.getStatus().equalsIgnoreCase("Accepted")) {
						statusSendingDataService.addData("RemoteStartTransaction", uniqueID,stationId, reason, "", userId, 0,portUniId, "", "","","Portal");
						int timeout = 90;
						String responseStatus="";
						String responseMessage="";
						while (timeout > 0) {
							Thread.sleep(3000);
							Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
							timeout -= 3;
							responseStatus = String.valueOf(statusFromRequest.get("status"));
							responseMessage=String.valueOf(statusFromRequest.get("response"));
							//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
							if (!responseStatus.equalsIgnoreCase("Inprogress")) {
								timeout = 0;
							}
						}
						responseStatus = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
						response.setStatus(responseStatus);
						response.setMessage("");
						if(responseStatus.equalsIgnoreCase("Rejected")) {
							response.setMessage("Rejected");
						}
					}else {
						customeLogger.info(stationRefNum, response.getMessage());
						Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
						Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
						statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
						response.setMessage(response.getMessage());
						response.setStatus("Rejected");
					}
					response.setTimestamp(new Date());
					response.setStatusCode(HttpStatus.OK.value());
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatus("Rejected");
					statusSendingDataService.addData("RemoteStartTransaction", uniqueID,0, "Error occured", "", 0, 0,0, "", "","","Portal");
				}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		logger.info("end remoteStartWithSmartCharging");
		return response;
	}

	@Override
	public ResponseMessage remoteStopPowerSharing(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
		try {
			String query = "select p.id from port p inner join station st on p.station_id = st.id where station_id='"+of.getStationId()+"' and powerSharing='N'";
			List<Map<String, Object>> findAll = executeRepository.findAll(query);
			if(findAll != null && findAll.size() > 0) {
				List<ResponseMessage> responseLs = new ArrayList<>();
				for(Map<String,Object>map : findAll) {
					final OCPPForm ofs = of;
					Long connectorId = Long.valueOf(String.valueOf(map.get("id")));
					ofs.setConnectorId(connectorId);
					
					Thread th = new Thread () {
						public void run() {
							ResponseMessage temp = remoteStopTransaction(ofs,sessionswithstations);
							logger.info("rstp 2251>> response : "+temp);
							responseLs.add(temp);
						}
					};
					th.start();
					try {
						th.join();
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
				for(ResponseMessage rs : responseLs){
					if(rs.getStatus().equalsIgnoreCase("Accepted")) {
						response = rs;
					}
				};
				if(response.getStatus().equalsIgnoreCase("Rejected")) {
					for(ResponseMessage rs : responseLs){
						if(!rs.getMessage().equalsIgnoreCase("")) {
							response.setMessage(rs.getMessage());
						}
					};
				}
			}else {
				response = remoteStopTransaction(of,sessionswithstations);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	@Override
	public ResponseMessage remoteStopTransaction(final OCPPForm of,Map<String, WebSocketSession> sessionswithstations)  {
		ResponseMessage response=new ResponseMessage();
		try {
			String sessionId=Utils.getRandomNumber("RSTP");
			long stationUniqueId = of.getStationId();
			String uniqueID = Utils.getStationRandomNumber(stationUniqueId) + ":RSTP";
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "remotStopTransaction request received from : "+of.getClientId());
			response.setClientId(stationRefNum);
			RemoteStopTransaction remoteStopeTrans = new RemoteStopTransaction();
			remoteStopeTrans.setConnectorId(of.getConnectorId());
			remoteStopeTrans.setIdTag(of.getIdTag());
			remoteStopeTrans.setOrgId(1);
			remoteStopeTrans.setStationRefNum(stationRefNum);
			
			String status="Rejected";
			String reason = "StationDisconnected";
			String msg = "";
			String startTransactionId = "null";
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			String webSocketSessionId=webSocketSessionObj==null?"":webSocketSessionObj.getId();	
			long connectorId=0;
			if(stationUniqueId!=0) {
				statusSendingDataService.addRemoteStopTransaction(remoteStopeTrans.getConnectorId(), uniqueID,stationUniqueId, null, webSocketSessionId);
				startTransactionId = statusSendingDataService.getStartTransactionId(remoteStopeTrans.getConnectorId(),stationUniqueId);
				connectorId=stationService.getConnectorId(of.getConnectorId());
				if(startTransactionId!=null && !startTransactionId.equalsIgnoreCase("null")&& !startTransactionId.equalsIgnoreCase("0")) {
					//statusNotificationService.updateOcppStatusNotification("Removed", stationUniqueId, remoteStopeTrans.getConnectorId(),false);
					status = "Accepted";
				} else {
					reason = "Invalid Stop Transaction";
					response.setMessage("UnAvailable");
				}
			}else {
				reason = "Invalid stationId";
				response.setMessage(reason);
			}			
			String reqId = uniqueID.split(":", 2)[0];			
			if(status.equalsIgnoreCase("Accepted") && webSocketSessionObj != null && webSocketSessionObj.isOpen()) {
				msg = "[2,\"" + uniqueID.toString() + "\",\"RemoteStopTransaction\",{\"transactionId\":"+ startTransactionId + "}]";
				Utils.chargerMessage(webSocketSessionObj, msg,stationRefNum);
				esLoggerUtil.insertLongs(uniqueID,"Portal","RemoteStopTransaction",msg,stationRefNum,of.getStationId(),"Inprogress",connectorId);
				response.setStatus("Accepted");
				lmRequestService.deleteIndividualScheduleTime(of.getConnectorId());
			} else {
				response.setStatus("Rejected");
				response.setMessage("Station Disconnected");
				esLoggerUtil.insertLongs(uniqueID,"Portal","RemoteStopTransaction",msg,stationRefNum,of.getStationId(),"Station Disconnected",connectorId);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData("RemoteStopTransaction", uniqueID, stationUniqueId,"Inprogress", "", 0l, 0, remoteStopeTrans.getConnectorId(), "", "","","Portal");
				int timeout = 85;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, response.getMessage(), "", statusId);		
				response.setMessage(response.getMessage());
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("end remoteStopTransaction");
		return response;
	}

	@Override
	public ResponseMessage getCompositeSchedule(final OCPPForm of, Map<String, WebSocketSession> sessionswithstations) {
		logger.info("start getCompositeSchedule");
		ResponseMessage response = new ResponseMessage();
		try {
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "compositeSchedule request received from : "+of.getClientId()+" , portId : "+of.getConnectorId()+" , ChargingRateUnit : "+of.getChargingRateUnit());
			response.setClientId(stationRefNum);
			String value = of.getType();
			long stationId=of.getStationId();
			String uniqueID =  Utils.getStationRandomNumber(stationId) + ":GCSR";
			Date utcDate = Utils.getUtcDateFormate(new Date());
			long connectorId = stationService.getStationConnectorId(of.getConnectorId());
			ResponseEntity<String> result = null;
			String message = "[2,\"" + uniqueID
					+ "\",\"GetCompositeSchedule\",{\"connectorId\":" + connectorId + ",\"duration\":"
					+ of.getDuration() + ",\"chargingRateUnit\":\""
					+ of.getChargingRateUnit() + "\"" + "}]";
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			ocppResetService.addReset(uniqueID, value, utcDate);
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()) {			
				customeLogger.info(stationRefNum, "Response Send to Charger :-" + message);
				Utils.chargerMessage(webSocketSessionObj, message, stationRefNum);
				response.setStatus("Accepted");
				esLoggerUtil.insertLongs(uniqueID,"Portal",of.getRequestType(),message,stationRefNum,of.getStationId(),"Inprogress",0);
			} else {		
				customeLogger.info(stationRefNum, "Station Disconnected : ");
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
				esLoggerUtil.insertLongs(uniqueID,"Portal",of.getRequestType(),message,stationRefNum,of.getStationId(),"Station Disconnected",0);
			}
			if(response.getStatus().equalsIgnoreCase("Accepted")) {
				statusSendingDataService.addData(of.getRequestType(), uniqueID, of.getStationId(), "Inprogress", "", 0,
						0, of.getConnectorId(), "", "", "", "Portal");
				int timeout = 90;
				String responseStatus="";
				String responseMessage="";
				while (timeout > 0) {
					Thread.sleep(3000);
					Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(uniqueID);
					timeout -= 3;
					responseStatus = String.valueOf(statusFromRequest.get("status"));
					responseMessage=String.valueOf(statusFromRequest.get("response"));
					//remarks = String.valueOf(statusFromRequest.get(0).get("remarks"));
					if (!responseStatus.equalsIgnoreCase("Inprogress")) {
						timeout = 0;
					}
				}
				responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
				if(!responseStatus.equalsIgnoreCase("Accepted")) {
					response.setStatus("Rejected");
					response.setMessage(responseMessage);
				}
				else {
					response.setStatus("Accepted");
					response.setMessage("");
				}
			}else {
				customeLogger.info(stationRefNum, "2992 >> Station is not connected to Ocpp Server");
				Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
				Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
				statusSendingDataService.updateResetStausInPortal(uniqueID, "StationDisconnected", "", statusId);
				response.setMessage("Station Disconnected");
				response.setStatus("Rejected");
			}
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("end getCompositeSchedule");
		return response;
	}
	@Override
    public ResponseMessage vendingPriceUpdate(OCPPForm of, Map<String, WebSocketSession> sessionswithstations) {
		logger.info("start vendingPriceUpdate");
    	ResponseMessage response = new ResponseMessage();
    	response.setStatus("Rejected");
		try {
			long stationId=of.getStationId();
			long portId=of.getConnectorId();
			long stationConnectorId = stationService.getStationConnectorId(portId);
			String stationRefNum = stationService.getstationRefNum(of.getStationId());
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			if (Optional.ofNullable(webSocketSessionObj).isPresent() && webSocketSessionObj.isOpen()) {
				Map<String,Object> station = stationService.getStnByRefNum(stationRefNum);
				if(station!=null && String.valueOf(station.get("manfname")).equalsIgnoreCase("EVBox") && Boolean.parseBoolean((String.valueOf(station.get("ctep"))))) {
					Double transactionFee = Double.valueOf(String.valueOf(station.get("transactionFee")));
					Map<String, Object> portObj = stationService.getPortByRefNum(stationId, stationConnectorId);
				}
				response.setStatus("Accepted");
			} 
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("end vendingPriceUpdate");
		return response;
    }

	@Override
	public ResponseMessage remoteStartOCPI(OCPPForm of, Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
    	response.setStatus("Rejected");
		try {
			String stnRefNum = stationService.getstationRefNum(of.getStationId());
			if(stnRefNum != null && !stnRefNum.equalsIgnoreCase("null") && !stnRefNum.equalsIgnoreCase("")) {
				statusSendingDataService.addData("RemoteStartTransaction", of.getRequestId(),of.getStationId(), "Inprogress", "", 0, 0,of.getConnectorId(), "", "","",of.getClientId());
				if(Utils.isJSONValid(of.getMessage())) {
					WebSocketSession webSocketSession = sessionswithstations.get(stnRefNum);
					if(webSocketSession != null && webSocketSession.isOpen()) {
						Utils.chargerMessage(webSocketSession, of.getMessage(), stnRefNum);
						
						int timeout = 85;
						String responseStatus="";
						String responseMessage="Rejected";
						while (timeout > 0) {
							Thread.sleep(3000);
							Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(of.getRequestId());
							timeout -= 3;
							responseStatus = String.valueOf(statusFromRequest.get("status"));
							responseMessage=String.valueOf(statusFromRequest.get("response"));
							if (!responseStatus.equalsIgnoreCase("Inprogress")) {
								timeout = 0;
							}
						}
						if(responseStatus != null && responseStatus.equalsIgnoreCase("Accepted")) {
							responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
							response.setMessage("");
							response.setStatus("Accepted");
						}else {
							response.setStatus("Rejected");
							response.setMessage("Charger Rejected");
						}
					}else {
						response.setMessage("StationDisconnected");
					}
				}else {
					response.setMessage("Invalid JSON message");
				}
			}else {
				response.setMessage("Invalid Station");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		response.setRequestType("RemoteStart");
		response.setTimestamp(Utils.getUTCDate());

		return response;
	}
	@Override
	public ResponseMessage remoteStopOCPI(OCPPForm of, Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
    	response.setStatus("Rejected");
		try {
			String stnRefNum = stationService.getstationRefNum(of.getStationId());
			if(stnRefNum != null && !stnRefNum.equalsIgnoreCase("null") && !stnRefNum.equalsIgnoreCase("")) {
				if(Utils.isJSONValid(of.getMessage())) {
					WebSocketSession webSocketSession = sessionswithstations.get(stnRefNum);
					if(webSocketSession != null && webSocketSession.isOpen()) {
						Utils.chargerMessage(webSocketSession, of.getMessage(), stnRefNum);
						
						int timeout = 85;
						String responseStatus="";
						String responseMessage="Rejected";
						while (timeout > 0) {
							Thread.sleep(3000);
							Map<String, Object> statusFromRequest = statusSendingDataService.getStatusSendingData(of.getRequestId());
							timeout -= 3;
							responseStatus = String.valueOf(statusFromRequest.get("status"));
							responseMessage=String.valueOf(statusFromRequest.get("response"));
							if (!responseStatus.equalsIgnoreCase("Inprogress")) {
								timeout = 0;
							}
						}
						if(responseStatus != null && responseStatus.equalsIgnoreCase("Accepted")) {
							responseMessage = responseStatus.equalsIgnoreCase("Inprogress") == true ? "Time Out" : responseStatus;
							response.setMessage("");
							response.setStatus("Accepted");
						}else {
							response.setStatus("Rejected");
							response.setMessage("Charger Rejected");
						}
					}else {
						response.setMessage("StationDisconnected");
					}
				}else {
					response.setMessage("Invalid JSON message");
				}
			}else {
				response.setMessage("Invalid Station");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		response.setRequestType("RemoteStop");
		response.setTimestamp(Utils.getUTCDate());

		return response;
	}

	
	@Override
	public ResponseMessage defaultPrice(OCPPForm of, Map<String, WebSocketSession> sessionswithstations) {
		logger.info("start defaultPrice...");
		ResponseMessage response = new ResponseMessage();
		response.setStatus("Rejected");
		try {
			final String stationRefNum = stationService.getstationRefNum(of.getStationId());
			customeLogger.info(stationRefNum, "reset request received from : " + of.getClientId());
			Map<String, Object> station = stationService.getStnByRefNum(stationRefNum);
			WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNum);
			boolean connectedTimeFlag = false;
			//startTxn sTxn = new startTxn();
			String manfname = station.get("manfname").toString();
			if (manfname.equalsIgnoreCase("FLO")) {
			Map<String, Object> pricingDetailsByStnId = stationService.getPricingDetailsByStnId(of.getStationId(), of.getTariffId());
			logger.info("pricingDetailsByStnId:" + pricingDetailsByStnId);
			String uniqueID = "";
			uniqueID = Utils.getStationRandomNumber(of.getStationId()) + ":DT";
			if (pricingDetailsByStnId != null) {
				logger.info("getPricingDetailsByStnId");
				if (pricingDetailsByStnId.get("prices") != null) {
					JsonNode prices = objectMapper.readTree(String.valueOf(pricingDetailsByStnId.get(("prices"))));
					double Stdprice = 0.00;
					double idleChargePrice = 0.00;
					String taxName = "";
					double step = 0.00;
					String StepSize = "";
					double tax=0.00;
					if (prices.size() > 0) {
						logger.info("prices>2839:" + prices);
						JsonNode costinfo = objectMapper.readTree(String.valueOf(prices.get(0).get("cost_info")));
						if (costinfo.size() > 0) {

							logger.info("cost_info:" + costinfo);
							JsonNode standard = objectMapper.readTree(String.valueOf(costinfo.get(0).get("standard")));
							logger.info("standard:" + standard);
							JsonNode stdEnergy = objectMapper.readTree(String.valueOf(standard.get("energy")));
							
							JsonNode flat = objectMapper.readTree(String.valueOf(standard.get("flat")));
							
							JsonNode time = objectMapper.readTree(String.valueOf(standard.get("time")));

							/*
							 * JsonNode Parking =
							 * objectMapper.readTree(String.valueOf(standard.get("Parking")));
							 * 
							 * if(Parking.size() > 0) { Stdprice = Parking.get("price").asDouble(); step =
							 * Parking.get("step").asDouble(); }
							 */
							// JsonNode Parking =
							// objectMapper.readTree(String.valueOf(standard.get("Parking")));
							/** for all prices **/
							logger.info("stdEnergy: " + stdEnergy);
							logger.info("flat:" + flat);
							logger.info("time:" + time);
							// System.out.println("parking:"+Parking);
							if (stdEnergy.size() > 0 && flat.size() > 0 && time.size() > 0)
							// 0 && Parking.size() > 0)
							{
								logger.info("all prices applied");
								if (stdEnergy.size() > 0) {
									Stdprice = stdEnergy.get("price").asDouble();
									step = stdEnergy.get("step").asDouble();
									StepSize = "kWh";
									logger.info(Stdprice + " , Stdprice units : " + step);
								}
							} else {
								logger.info("individual prices applied");
								if (stdEnergy.size() > 0) {
									Stdprice = stdEnergy.get("price").asDouble();
									step = stdEnergy.get("step").asDouble();
									StepSize = "kWh";
									logger.info(Stdprice + " , Stdprice units : " + step);
								}

								if (time.size() > 0) {
									Stdprice = time.get("price").asDouble();
									step = time.get("step").asDouble();
									if (step == 3600) {
										StepSize = "Per Hour";
									} else {
										StepSize = "Per Minute";
									}
								}

								if (flat.size() > 0) {
									Stdprice = flat.get("price").asDouble();
									step = flat.get("step").asDouble();
									StepSize = "";

								}

							}

							JsonNode aditional = objectMapper
									.readTree(String.valueOf(costinfo.get(0).get("aditional")));
							logger.info("aditional price:" + aditional);
							JsonNode idleCharge = objectMapper.readTree(String.valueOf(aditional.get("idleCharge")));
							if (idleCharge.size() > 0) {
								connectedTimeFlag =true;
								logger.info("idleCharge:" + idleCharge);
								idleChargePrice = idleCharge.get("price").asDouble();
							}
							if (aditional.size() > 0) {
								JsonNode taxJsonLs = objectMapper.readTree(String.valueOf(aditional.get("tax")));
								logger.info("taxJsonLs price:" + taxJsonLs);
								if (taxJsonLs.size() > 0) {
									for (int i = 0; i < taxJsonLs.size(); i++) {
										JsonNode taxJsonMap = objectMapper.readTree(String.valueOf(taxJsonLs.get(i)));
										taxName = taxJsonMap.get("name").asText();
										tax=     taxJsonMap.get("percnt").asDouble();
										logger.info("taxName2935>:" + taxName);
									}
								}
							}
							logger.info("price:" + Stdprice);
							logger.info("idleCharge:" + idleChargePrice);
							logger.info("taxName:" + taxName);
							logger.info("tax:"+tax);
							logger.info(Stdprice + " , Stdprice units : " + step);
							logger.info("StepSize:"+StepSize);
							
							String msg = "";
							if (connectedTimeFlag == true) {
								msg = "[2,\"" + uniqueID
										+ "\",\"DataTransfer\",{\"readonly\":false,\"value\":\"{\\\"priceText\\\":\\\"<en>"
										+ Stdprice + "\\/"+StepSize+" + " + tax + "+" + idleChargePrice + "<\\/en><fr>"
										+ Stdprice + "\\/"+StepSize+" + " + tax + "+" + idleChargePrice+""
										+ "<\\/fr>\\\",\\\"priceTextOffline\\\":\\\"<en><\\/en><fr><\\/fr>\\\"}\",\"key\":\"DefaultPrice\"}]";

								
							} else {
								msg = "[2,\"" + uniqueID
										+ "\",\"DataTransfer\",{\"readonly\":false,\"value\":\"{\\\"priceText\\\":\\\"<en>"
										+ Stdprice + "\\/" +StepSize +"+" + tax + "<\\/en><fr>" + Stdprice + "\\/"+StepSize+"+"+ tax+""
										+ "<\\/fr>\\\",\\\"priceTextOffline\\\":\\\"<en><\\/en><fr><\\/fr>\\\"}\",\"key\":\"DefaultPrice\"}]";

							}
							logger.info("msg:" + msg);
							Utils.chargerMessage(webSocketSessionObj, msg, stationRefNum);
							response.setStatus("Accepted");
						}
					}
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
