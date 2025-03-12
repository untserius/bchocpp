package com.axxera.ocpp.serviceImpl;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.StartTransaction;
import com.axxera.ocpp.model.ocpp.OCPPTransactionData;
import com.axxera.ocpp.service.ChargingIntervalService;
import com.axxera.ocpp.service.LMRequestService;
import com.axxera.ocpp.service.chargerCommunication;
import com.axxera.ocpp.service.chargingSessionService;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPMeterValueServiceImpl;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.StatusNotificationService;
import com.axxera.ocpp.webSocket.service.alertsService;
import com.axxera.ocpp.webSocket.service.failedSessionService;

@Service
public class chargerCommunicationImpl implements chargerCommunication{
	private final static Logger logger = LoggerFactory.getLogger(chargerCommunicationImpl.class);
	
	@Autowired
	private LoggerUtil customLog;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private alertsService alertsService;
	
	@Autowired
	private chargingSessionService chargingSessionService;
	
	@Autowired
	private StatusNotificationService statusNotificationService;
	
	@Autowired
	private OCPPMeterValueServiceImpl ocppMeterValueService;
	
	@Autowired
	private failedSessionService failedSessionService;
	
	@Autowired
	private LMRequestService lmRequestService;
	
	@Autowired
	private ChargingIntervalService chargingIntervalService;
	
	@Override
	public void startTransaction(FinalData finalData, String stationRefNum,Map<String, WebSocketSession> sessionWithStations, WebSocketSession session, Object requestMessage) {
		logger.info("60>>Start:");
		StartTransaction startTrans = finalData.getStartTransaction();
		startTxn sTxn = new startTxn();
		sTxn.setIdTag(startTrans.getIdTag());
		sTxn.setRfidOrPhone(startTrans.getIdTag());
		sTxn.setStnRefNum(stationRefNum);
		sTxn.setConnectorId(startTrans.getConnectorId());
		sTxn.setMeterStartReading(utils.decimalwithFourdecimals(new BigDecimal(startTrans.getMeterStart())).doubleValue());
		sTxn.setStartTime(startTrans.getTimestamp());
		sTxn.setTransactionId(utils.randomIntNumber());
		sTxn.setSt_unqReqId(finalData.getSecondValue());
		sTxn.setChargeSessUniqId(utils.getuuidRandomId());
		sTxn.setOfflineTxn(utils.getOfflineFlag(startTrans.getTimestamp()));
		sTxn.setReserveId(startTrans.getReservationId());
		sTxn.setTxnData(new OCPPTransactionData());
 		String invaliIdResponse = "[3,\""+ sTxn.getSt_unqReqId() + "\",{\"idTagInfo\":{\"status\":\"Invalid\",\"parentIdTag\":\"PARENT\"},\"transactionId\":" + sTxn.getTransactionId() + "}]";
 		String sucessResponse = "[3,\""+ sTxn.getSt_unqReqId() + "\",{\"idTagInfo\":{\"status\":\"Accepted\",\"parentIdTag\":\"PARENT\"},\"transactionId\":" + sTxn.getTransactionId() + "}]";
		try {
			sTxn.setStnObj(stationService.getStnObjByRefNos(stationRefNum,startTrans.getConnectorId()));
			logger.info("start station obj : "+sTxn.getStnObj());
			if(startTrans.getConnectorId()>0 && !sTxn.getStnObj().isEmpty()) {
				sTxn.setActiveTxnData(chargingSessionService.getActiveTxnData(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))), Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))),sTxn.getIdTag()));
				sTxn = chargingSessionService.getRSTTxnData(sTxn);
				chargingSessionService.deleteTxnData(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
				logger.info("sTxn.getRst_clientId() : "+sTxn.getRst_clientId());
//				sTxn = chargingSessionService.siteTimeCheck(sTxn);
				if(sTxn.getRst_clientId() != null && sTxn.getRst_clientId().equalsIgnoreCase("ocpi") ) {
					sTxn.setOcpiTxn(true);
					String response = chargingSessionService.ocpiStartCall(String.valueOf(requestMessage),stationRefNum);
					invaliIdResponse=response;
					utils.chargerMessage(session, response, stationRefNum);
					customLog.info(stationRefNum, "response message : "+response);
					if(response.contains("Accepted") ){
						sTxn.setTxnValid(true);
						sucessResponse=response;
//						statusNotificationService.updateOcppStatusNotification("Charging", Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))), Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))),Boolean.valueOf(String.valueOf(sTxn.getStnObj().get("ampFlag"))));
					}
					customLog.sessionlog(sTxn.getChargeSessUniqId(), stationRefNum, requestMessage, (sTxn.isTxnValid() == true ? sucessResponse : invaliIdResponse));
				}else if(true){
					sTxn = chargingSessionService.siteData(sTxn);
					sTxn = chargingSessionService.startTxnUserValidator(sTxn);
					if(sTxn.isOcpiTxn()) {
						String response = chargingSessionService.ocpiStartCall(String.valueOf(requestMessage),stationRefNum);
						invaliIdResponse=response;
						utils.chargerMessage(session, response, stationRefNum);
						customLog.info(stationRefNum, "RFID ocpi transaction response message : "+response);
						if(response.contains("Accepted") ){
							sTxn.setTxnValid(true);
							sucessResponse=response;
//							statusNotificationService.updateOcppStatusNotification("Charging", Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))), Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))),Boolean.valueOf(String.valueOf(sTxn.getStnObj().get("ampFlag"))));
						}
					}else {
						sTxn = chargingSessionService.startTxnStationValidator(sTxn);
						
						if(sTxn.isTxnValid()) {//Saving charging session data
							utils.chargerMessage(session, sucessResponse, stationRefNum);
							customLog.info(stationRefNum, "response message : "+sucessResponse);
							chargingSessionService.updateChargingSessionData(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
							sTxn = chargingSessionService.insertActiveTxnStartTxn(sTxn);
							sTxn = chargingSessionService.insertIntoSession(sTxn);
							chargingSessionService.insertIntoSessionPricings(sTxn);
							chargingSessionService.insertIntoTransactionData(sTxn);
							chargingSessionService.updatePAYGSessionId(sTxn);
							alertsService.smsPAYGAuthorize(sTxn);
							lmRequestService.sendingRequestsToLM(sTxn,String.valueOf(requestMessage), startTrans);
//							statusNotificationService.updateOcppStatusNotification("Charging", Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))), Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))),Boolean.valueOf(String.valueOf(sTxn.getStnObj().get("ampFlag"))));
							chargingIntervalService.chargingIntervalDataLogs(sTxn);
						}else {
							utils.chargerMessage(session, invaliIdResponse, stationRefNum);
							customLog.info(stationRefNum, "response message : "+invaliIdResponse);
							customLog.info(stationRefNum, "start transaction rejected due to : "+sTxn.getReason());
						}
						ocppMeterValueService.insertMeterValues(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))), Long.parseLong(String.valueOf(sTxn.getTransactionId())), sTxn.getChargeSessUniqId(), Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))),startTrans.getTimestamp(), 0.0, "kWh",0.0, startTrans.getTimestamp(),null,finalData);
						chargingSessionService.insertStartTransaction(sTxn);
						ocppMeterValueService.sendPushNotification(sTxn,"Meter Value");
					}
					customLog.sessionlog(sTxn.getChargeSessUniqId(), stationRefNum, requestMessage, (sTxn.isTxnValid() == true ? sucessResponse : invaliIdResponse));
				}else {
					sTxn.setTxnValid(false);
					//sTxn.setReason("Station/Port is not existed");
					sTxn.setReason("Charger Unavailable");
					utils.chargerMessage(session, invaliIdResponse, stationRefNum);
					customLog.info(stationRefNum, "response message : "+invaliIdResponse);
					customLog.info(stationRefNum, "start transaction rejected due to siteStatus Closed : "+sTxn.getReason());
				
				}
			}else {
				sTxn.setTxnValid(false);
				sTxn.setReason("Station/Port is not existed");
				utils.chargerMessage(session, invaliIdResponse, stationRefNum);
				customLog.info(stationRefNum, "response message : "+invaliIdResponse);
				customLog.info(stationRefNum, "start transaction rejected due to : "+sTxn.getReason());
			}
			failedSessionService.insertIntoFailedSessionsStart(sTxn);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
