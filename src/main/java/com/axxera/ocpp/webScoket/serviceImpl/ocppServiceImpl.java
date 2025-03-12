package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.rest.message.ResponseMessage;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.ApiResponseService;
import com.axxera.ocpp.webSocket.service.StatusNotificationService;
import com.axxera.ocpp.webSocket.service.ocppService;

@Service
public class ocppServiceImpl implements ocppService {
	
	@Autowired
	private ApiResponseService responceService;
	
	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private StatusNotificationService statusNotificationService;
	
	private boolean fcmFlag=true;
	
//	@Autowired
//	private RemoteSocketControllerService remoteSocketControllerService;
	
	@Override
	public ResponseMessage features(OCPPForm of,Map<String, WebSocketSession> sessionswithstations)  {
		ResponseMessage response = new ResponseMessage();
		try {
			if (String.valueOf(of.getRequestType()).equalsIgnoreCase("CancelReservation")) {
				response = responceService.cancelReservation(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ChangeAvailability")) {
				response = responceService.changeAvailability(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ChangeConfiguration")) {
				response = responceService.changeConfiguration(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("Clearcache")) {
				response = responceService.clearCache(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ClearChargingProfile")) {
				response = responceService.clearChargingProfile(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("DataTransfer")) {
				response = responceService.dataTransfer(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetConfiguration")) {
				response = responceService.getConfiguration(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetCompositeSchedule")) {
				response = responceService.getCompositeSchedule(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetDiagnostics")) {
				response = responceService.getDiagnostics(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetLocalListVersion")) {
				response = responceService.getLocalListVersion(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStart")) {
				//response = responceService.remoteStart(of,sessionswithstations);
				response = responceService.remoteStartPowerSharging(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStartWithSmartCharging")) {
				response = responceService.remoteStartWithSmartCharging(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStop")) {
				//response = responceService.remoteStopTransaction(of,sessionswithstations);
				response = responceService.remoteStopPowerSharing(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ReserveNow")) {
				response = responceService.reserveNow(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("Reset")) {
				response = responceService.reset(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("SendLocalList")) {
				response = responceService.sendLocalList(of,sessionswithstations);
			}  else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("SetChargingProfile")) {
				response = responceService.setChargingProfile(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("TriggerMessage")) {
				response = responceService.triggerMessage(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("UnlockConnector")) {
				response = responceService.unlockConnector(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("UpdateFirmWare")) {
				response = responceService.updateFirmWare(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("vendingPriceUpdate")) {
				response = responceService.vendingPriceUpdate(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("Custom")) {
				response = responceService.custom(of,sessionswithstations);
			}  else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("DefaultPrice")) {
				response = responceService.defaultPrice(of,sessionswithstations);
			} 
			else {
				response.setMessage("Invalid input");
				response.setStatus("Rejected");
				response.setTimestamp(new Date());
				response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseMessage changeAvailability(OCPPForm of,Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
		try {
			long stationId=of.getStationId();
			String query="select id from port where station_id="+stationId;
			List<Map<String,Object>> list=executeRepository.findAll(query);
			for(Map<String,Object> data : list) {
				Thread thread = new Thread() {
					public void run() {
						OCPPForm ocppForm=new OCPPForm();
						ocppForm.setEndTimeStamp(of.getEndTimeStamp());
						ocppForm.setStationId(of.getStationId());
						ocppForm.setClientId(of.getClientId());
						ocppForm.setConnectorId(Long.parseLong(String.valueOf(data.get("id"))));
						ocppForm.setType(of.getRequestType());
						responceService.changeAvailability(ocppForm,sessionswithstations);
					}
				};
				thread.start();
				Thread.sleep(3000);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public ResponseMessage appFeatures(OCPPForm of,Map<String, WebSocketSession> sessionswithstations)  {
		ResponseMessage response = new ResponseMessage();
		try {
			String query  = "select c.phone from creadential c inner join accounts a on a.id=c.account_id inner join users u on u.userid=a.user_id where u.uid='"+of.getUuid()+"' and name='Mobile Application'";
			String idTag = String.valueOf(generalDao.getRecordBySql(query));
			if (String.valueOf(of.getRequestType()).equalsIgnoreCase("CancelReservation")) {
				response = responceService.cancelReservation(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStop")) {
				//response = responceService.remoteStopTransaction(of,sessionswithstations);
				response = responceService.remoteStopPowerSharing(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ReserveNow")) {
				of.setIdTag(idTag);
				response = responceService.reserveNow(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStart")) {
				if(idTag == null || idTag.equalsIgnoreCase("") || idTag.equalsIgnoreCase("0") || idTag.equalsIgnoreCase("null")) {
					String query1  = "select phone from userPayment where uid='"+of.getUuid()+"' and userType = 'GuestUser' ";
					String idTag2 = String.valueOf(generalDao.getRecordBySql(query1));
					if(idTag2 == null || idTag2.equalsIgnoreCase("") || idTag2.equalsIgnoreCase("0") || idTag2.equalsIgnoreCase("null")) {
						of.setIdTag(null);
					}else {
						of.setIdTag(idTag2);
					}
				}else {
					of.setIdTag(idTag);
				}
				response = responceService.remoteStartPowerSharging(of,sessionswithstations);
			} else {
				response.setMessage("Invalid input");
				response.setStatus("Rejected");
				response.setTimestamp(new Date());
				response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			}
		}catch(Exception e) {
			response.setMessage("Invalid input");
			response.setStatus("Rejected");
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public ResponseMessage ocpiFeatures(OCPPForm of,Map<String, WebSocketSession> sessionswithstations)  {
		ResponseMessage response = new ResponseMessage();
		try {
			if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStart")) {
				response = responceService.remoteStartOCPI(of,sessionswithstations);
			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStop")) {
				response = responceService.remoteStopTransaction(of,sessionswithstations);
			} else {
				response.setMessage("Invalid input");
				response.setStatus("Rejected");
				response.setTimestamp(new Date());
				response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			}
		}catch(Exception e) {
			response.setMessage("Invalid input");
			response.setStatus("Rejected");
			response.setTimestamp(new Date());
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			e.printStackTrace();
		}
		return response;
	}
	
//	@Override
//	public ResponseMessage socketControllerFeatures(SessionFormForSocketController sessionForm,
//			Map<String, WebSocketSession> sessionswithstations) {
//		
////		private String stationRefNum;
////		private int connectorId;
////		private String idTag;
////		private long transactionId;
////		private double energyDeliveredInKwh;
////		private double meterStart;
////		private double meterStop;
////		private double socStart;
////		private double socEnd;
////		private String startTimeStamp;
////		private String endTimeStamp;
////		private String durationInMinutes;
////		private String reasonForTermination;
//		
//		
//		long userId = ocppFreeChargingService.getUserIdOnBaseIdtag(sessionForm.getIdTag());
//		long accountId = ocppFreeChargingService.getAccIdOnBaseIdtag(sessionForm.getIdTag());
//		//AccountTransactions accountTransactions = stationService.getAccountTransactions(accountId);
//		long stationId = stationService.getStationUniqId(sessionForm.getStationRefNum());
//		long portId = stationService.getPortUniId(stationId, sessionForm.getConnectorId());
//		
//		//&& accountTransactions!=null 
//		
//		
//		
//		
//		if(userId>0 &&accountId>0 && stationId>0 && portId>0 ) {
//			Port port = stationService.getPort(portId);
//			Session session = new Session();
//			session.setStartTimeStamp(utils.stringToDate(sessionForm.getStartTimeStamp()));
//			session.setEndTimeStamp(utils.stringToDate(sessionForm.getEndTimeStamp()));
//			session.setPort(port);
//			session.setSocEndVal(sessionForm.getSocEnd());
//			session.setSocStartVal(sessionForm.getSocEnd());
//			session.setKilowattHoursUsed(sessionForm.getEnergyDeliveredInKwh());
//			session.setSessionElapsedInMin(sessionForm.getDurationInMinutes()); ;
//			session.setReasonForTer(sessionForm.getReasonForTermination());
//			session.setCustomerId(sessionForm.getIdTag());
//			session.setUserId(userId);
//			//session.setAccountTransaction(accountTransactions);
//			generalDao.save(session);
//		}
//		return null;
//	}

	@Override
	public ResponseMessage featuresRemoteSocketController(OCPPForm of,
			Map<String, WebSocketSession> sessionswithstations) {
		ResponseMessage response = new ResponseMessage();
		try {
//			if (String.valueOf(of.getRequestType()).equalsIgnoreCase("CancelReservation")) {
//				response = remoteSocketControllerService.cancelReservation(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ChangeAvailability")) {
//				response = remoteSocketControllerService.changeAvailability(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ChangeConfiguration")) {
//				response = remoteSocketControllerService.changeConfiguration(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("Clearcache")) {
//				response = remoteSocketControllerService.clearCache(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ClearChargingProfile")) {
//				response = remoteSocketControllerService.clearChargingProfile(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("DataTransfer")) {
//				response = remoteSocketControllerService.dataTransfer(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetConfiguration")) {
//				response = remoteSocketControllerService.getConfiguration(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetCompositeSchedule")) {
//				response = remoteSocketControllerService.getCompositeSchedule(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetDiagnostics")) {
//				response = remoteSocketControllerService.getDiagnostics(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("GetLocalListVersion")) {
//				response = remoteSocketControllerService.getLocalListVersion(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStart")) {
//				response = remoteSocketControllerService.remoteStart(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStartWithSmartCharging")) {
//				response = remoteSocketControllerService.remoteStartWithSmartCharging(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("RemoteStop")) {
//				response = remoteSocketControllerService.remotStopTransaction(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("ReserveNow")) {
//				response = remoteSocketControllerService.reserveNow(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("Reset")) {
//				response = remoteSocketControllerService.reset(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("SendLocalList")) {
//				response = remoteSocketControllerService.sendLocalList(of,sessionswithstations);
//			}  else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("SetChargingProfile")) {
//				response = remoteSocketControllerService.setChargingProfile(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("TriggerMessage")) {
//				response = remoteSocketControllerService.triggerMessage(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("UnlockConnector")) {
//				response = remoteSocketControllerService.unlockConnector(of,sessionswithstations);
//			}  else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("UpdateFirmWare")) {
//				response = remoteSocketControllerService.updateFirmWare(of,sessionswithstations);
//			} else if (String.valueOf(of.getRequestType()).equalsIgnoreCase("Custom")) {
//				response = remoteSocketControllerService.custom(of,sessionswithstations);
//			} else {
//				response.setMessage("Invalid input");
//				response.setStatus("Rejected");
//				response.setTimestamp(new Date());
//				response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public void fcmStop() {
		try {
			fcmFlag=false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fcm(OCPPForm ocppForm) {
		try {
			while(fcmFlag) {
				Thread.sleep(ocppForm.getStackLevel());
				String notificationId=Utils.getIntRandomNumber();
				statusNotificationService.sendNotificationForPortStatus("", "Port Status", ocppForm.getStnRefNum(), notificationId, ocppForm.getStationId(),ocppForm.getConnectorId() ,ocppForm.getUserId() );
			}
			fcmFlag=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
