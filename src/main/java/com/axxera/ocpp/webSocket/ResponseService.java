package com.axxera.ocpp.webSocket;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.StatusNotificationDao;
import com.axxera.ocpp.message.ConfigurationKey;
import com.axxera.ocpp.message.Datatransfer;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.StationConfig;
import com.axxera.ocpp.model.es.StationLogs;
import com.axxera.ocpp.model.ocpp.ChargerDefaultConfiguration;
import com.axxera.ocpp.model.ocpp.OCPPRemoteStartTransaction;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.service.LMRequestService;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPFreeChargingService;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPMeterValueServiceImpl;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPRemoteStartTransactionService;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPResetService;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPStartTransactionService;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPStationConfigurationForBootNotificationService;
import com.axxera.ocpp.webScoket.serviceImpl.reservationService;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.StatusDefaultConfigService;
import com.axxera.ocpp.webSocket.service.StatusNotificationService;
import com.axxera.ocpp.webSocket.service.StatusSendingDataService;
import com.axxera.ocpp.webSocket.service.ocpiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResponseService {

	private final static Logger logger = LoggerFactory.getLogger(ResponseService.class);

	@Autowired
	private StatusSendingDataService statusSendingDataService;

	@Autowired
	private StatusDefaultConfigService statusDefaultConfigService;
	
	@Autowired
	private OCPPStartTransactionService oCPPStartTransactionService;

	@Autowired
	private OCPPRemoteStartTransactionService ocppRemoteStartTransactionService;

	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private ExecuteRepository executeRepository;

	@Autowired
	private StationService stationService;

	@Autowired
	private ocpiService ocpiService;

	@Autowired
	private StatusNotificationService statusNotificationService;

	@Autowired
	private OCPPFreeChargingService ocppFreeChargingService;

	@Autowired
	private CurrencyConversion currencyConversion;

	@Autowired
	private OCPPMeterValueServiceImpl ocppMeterValueService;

	@Autowired
	private OCPPResetService ocppResetService;

	@Autowired
	private OCPPStationConfigurationForBootNotificationService OCPPStationConfigurationForBootNotificationService;

	@Autowired
	private Utils Utils;

	@Autowired
	private EsLoggerUtil esLoggerUtil;

	@Autowired
	private reservationService reservationService;

	@Autowired
	private StatusNotificationDao statusNotificationDao;
	
	@Autowired
	private LMRequestService lmRequestService;

	public void threeResponse(final FinalData data, CopyOnWriteArrayList<WebSocketSession> sessions, WebSocketSession session, String stationRefNum, String message, Long stationId) {
		try {
			Thread.sleep(2000);
			
			String uniqueID = data.getSecondValue();
			String[] splitString = data.getSecondValue().split(":", 2);
			String resType = "";
			if(splitString.length>=2){
				resType = splitString[1];
			}
			String status = String.valueOf(data.getStatus().getStatus()).equalsIgnoreCase("null") ? "" : data.getStatus().getStatus();
			String responseFromCharger = String.valueOf(message);
			if(!data.getFirstValue().equals(new Long(3))) {
				status="Rejected";
				responseFromCharger = "";
			}
			Map<String, Object> statusSendingMap = statusSendingDataService.getStatusSendingData(uniqueID);
			Long statusId = Long.valueOf(String.valueOf(statusSendingMap.get("id")));
			logger.info("statusId : "+statusId);


			if (resType.equalsIgnoreCase("RST")) {
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);
				OCPPRemoteStartTransaction ocppRemoteStartTransaction = ocppRemoteStartTransactionService.getSession(uniqueID);
				if(ocppRemoteStartTransaction != null) {
					ocppRemoteStartTransactionService.updateRemoteStartTransaction(uniqueID, status);
					lmRequestService.updateLMIndividualScheduleTime(ocppRemoteStartTransaction.getConnectorId());
				}
				
			} else if (resType.equalsIgnoreCase("SCP")) {
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("CCP")) {
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("GCSR")) {
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("RSTP")) {

				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

				statusSendingDataService.updateRemoteStopTransaction(uniqueID, status, responseFromCharger);

				ocppRemoteStartTransactionService.updateRemoteStartTransaction(uniqueID, status);

			} else if (resType.equalsIgnoreCase("CA")) {

				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

				ocppFreeChargingService.updateStationInoperativeFlag(stationRefNum, uniqueID, status);

			} else if (resType.equalsIgnoreCase("CR")) {
				String reqId = uniqueID.split(":", 2)[0];
				ocpiService.updateOcpiRequestStatus(reqId, status.toUpperCase());
				if (status.equalsIgnoreCase("Accepted")) {

					Map<String, Object> stationObj = stationService.getSiteDetails(stationId);
					String siteCurrency = String.valueOf(stationObj.get("currencyType"));
					//Double reservationFee=stationService.getReservationFee(stationId);//oldgetCancelReservationFee

					BigDecimal refundAmount=new BigDecimal("0.00");
					Long conncetorId = Long.valueOf(String.valueOf(statusSendingMap.get("connectorId")));

					Map<String,Object> reserveData=stationService.getReservationFeeOngngTxn(conncetorId);
					if(reserveData.size() > 0 && !Boolean.parseBoolean((String.valueOf(reserveData.get("cancellationFlag"))))) {
						customLogger.info(stationRefNum, "Cancel Reservation process");
						BigDecimal reservationFee=new BigDecimal(String.valueOf(reserveData.get("reserveAmount")));
						BigDecimal cancelReservationFee = stationService.getCancelReservationFee(stationId);
						String query = "update ocpp_reservation set cancellationFee ="+cancelReservationFee+" where reservationId ="+Long.valueOf(String.valueOf(reserveData.get("reservationId")));
						executeRepository.update(query);
						Map<String, Object> accountsObj = ocppFreeChargingService.accntsBeanObj(Long.valueOf(String.valueOf(statusSendingMap.get("userId"))));
						if(accountsObj!=null) {
							String userCurrency = String.valueOf(accountsObj.get("currencyType"));
							BigDecimal currencyRate = currencyConversion.currencyRate(userCurrency, siteCurrency);

							BigDecimal reservationFee1=reservationFee;
							BigDecimal cancelReservationFee1=cancelReservationFee;
							BigDecimal remainingBal = new BigDecimal(String.valueOf(accountsObj.get("accountBalance")));
							if(!userCurrency.equalsIgnoreCase(siteCurrency)) {
								reservationFee1=currencyConversion.convertCurrency(userCurrency,siteCurrency,new BigDecimal(String.valueOf(reservationFee)));
								cancelReservationFee1=currencyConversion.convertCurrency(userCurrency,siteCurrency,new BigDecimal(String.valueOf(cancelReservationFee)));
							}
							if(Double.parseDouble(String.valueOf(reservationFee1))>Double.parseDouble(String.valueOf(cancelReservationFee1))) {
								remainingBal = remainingBal.add(reservationFee1).subtract(cancelReservationFee1);
								refundAmount=reservationFee1.subtract(cancelReservationFee1);
							}
							accountsObj.put("cancelReservationFee",cancelReservationFee1);
							ocppMeterValueService.insertIntoAccountTransaction(accountsObj, 0.0,
									"Cancel Reservation Fee (StationId : " + stationRefNum + ") ", Utils.getUtcDateFormate(new Date()),
									Double.parseDouble(String.valueOf(remainingBal)), "SUCCESS", Utils.getIntRandomNumber(), "0", stationRefNum, "USD",
									userCurrency, Float.parseFloat(String.valueOf(currencyRate)),Double.parseDouble(String.valueOf(refundAmount)),"Wallet","Credit");
						}
						statusSendingDataService.deleteReservationId(String.valueOf(statusSendingMap.get("value")),true);
						String refundAmountForMail=Utils.decimalwithtwoZeros(Utils.decimalwithtwodecimals(refundAmount));
						reservationService.sendCancelReservationMail(accountsObj,String.valueOf(statusSendingMap.get("value")),stationRefNum,conncetorId,refundAmountForMail,Long.parseLong(String.valueOf(statusSendingMap.get("userId"))),stationId);
						reservationService.reservationPushNotification(reserveData,"Cancelled",stationRefNum);
					}else {
						customLogger.info(stationRefNum, "Reservation cancelled already");
					}
				}
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);
			} else if (resType.equalsIgnoreCase("GLLV")) {

				status = responseFromCharger;
				statusSendingDataService.updateResetStausInPortal(uniqueID,String.valueOf(status), responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("HR") || resType.equalsIgnoreCase("SR")) {

				ocppResetService.updateResetStatus(uniqueID, status);
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("GC")) {
				try {
					List<Map<String, Object>>stationCFBdata =executeRepository.findAll("select defaultConfig from stationConfigForBootNotf "+  "where stationId = "+stationId);
					Map<String,Object> map = new HashMap<>();
					if(stationCFBdata.size() > 0) {
						map = stationCFBdata.get(0);	
						Boolean defaultConfigValue= Boolean.valueOf(String.valueOf(map.get("defaultConfig")));
						status = String.valueOf(data.getThirdValue());
						if(data.getConfigurationKey()!=null && data.getConfigurationKey().size()>0) {
							status = String.valueOf(data.getThirdValue());
						}
						if(defaultConfigValue){
							insertStnConfig(stationRefNum, data, "GC", "", "", session);
						}
					}
					statusSendingDataService.updateResetStausInPortal(uniqueID, String.valueOf("Accepted"),responseFromCharger, statusId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (resType.equalsIgnoreCase("DCC")) {
				/**getting charger default configuration**/
				List<ChargerDefaultConfiguration> getdatafromCDC = statusSendingDataService.getDefaultChargerConfiguration(); //LIST1
				//data LIST 2 
				statusDefaultConfigService.getCompareDataDcc(data,getdatafromCDC,session,stationRefNum);
			} else if (resType.equalsIgnoreCase("UF")) {

				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("RN")) {


				String reqId = uniqueID.split(":", 2)[0];


				ocpiService.updateOcpiRequestStatus(reqId, status.toUpperCase());
				Long conncetorId = Long.valueOf(String.valueOf(statusSendingMap.get("connectorId")));
				List<Map<String,Object>> stationStatusa = statusNotificationDao.getPortStatus(stationId, conncetorId);
				String stationStatus = "Inoperative";
				if(stationStatusa.size() > 0) {
					stationStatus = String.valueOf(stationStatusa.get(0).get("status"));
				}
				Map<String, Object> siteObj = stationService.getSiteDetails(stationId);
				String siteCurrency=String.valueOf(siteObj.get("currencyType"));

				if (stationStatus.equalsIgnoreCase("Charging")) {
					status = "Occupied";
				}
				if (status.equalsIgnoreCase("Occupied") || status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("Faulted")) {
					statusSendingDataService.deleteReservationId(String.valueOf(statusSendingMap.get("value")),false);
				} else if (status.equalsIgnoreCase("Accepted")) {
					if (stationStatus.equalsIgnoreCase("Available")) {
//						statusNotificationService.updateOcppStatusNotification("Reserved", stationId, conncetorId,false);
					}
					BigDecimal reservationFee = stationService.getReservationFee(stationId);
					Map<String,Object> accountsObj =ocppFreeChargingService.accntsBeanObj(Long.parseLong(String.valueOf(statusSendingMap.get("userId"))));		 
					String userCurrency=String.valueOf(accountsObj.get("currencyType"));
					BigDecimal reservationFee1=reservationFee;
					if(!userCurrency.equalsIgnoreCase(siteCurrency)) {
						reservationFee1=currencyConversion.convertCurrency(userCurrency,siteCurrency,new BigDecimal(String.valueOf(reservationFee)));
					}
					BigDecimal remainingBal=new BigDecimal(String.valueOf(accountsObj.get("accountBalance"))).subtract(reservationFee1);
					BigDecimal currencyRate=currencyConversion.currencyRate(userCurrency,siteCurrency);
					ocppMeterValueService.insertIntoAccountTransaction(accountsObj,Double.parseDouble(String.valueOf(reservationFee1)),"Reservation Fee (StationId : "+stationRefNum+")",Utils.getUtcDateFormate(new Date()),Double.parseDouble(String.valueOf(remainingBal)),"SUCCESS" ,Utils.getIntRandomNumber(),"0",stationRefNum,"",String.valueOf(accountsObj.get("currencyType")),Float.parseFloat(String.valueOf(currencyRate)),0.0,"Wallet","Session");
					stationService.updateReservation(1, stationId, conncetorId,Long.valueOf(String.valueOf(statusSendingMap.get("userId"))), 0,true,null,String.valueOf(statusSendingMap.get("value")),0,0);
					String reservationFeeForMail=Utils.decimalwithtwoZeros(Utils.decimalwithtwodecimals(reservationFee1));
					reservationService.sendReservationMail(accountsObj,stationId,stationRefNum,conncetorId,reservationFeeForMail,Long.parseLong(String.valueOf(statusSendingMap.get("userId"))));
				}
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);
			} else if (resType.equalsIgnoreCase("TM")) {
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("DT")) {
				String ocppStationMode = statusSendingDataService.ocppStatusSendingKey(uniqueID);
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("Unlk")) {

				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("CNF")) {

				try {

					// insertStnConfig(stationRefNum, data, "CNF",uniqueID,status,session);
					statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (resType.equalsIgnoreCase("SL")) {
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("CC")) {

				statusSendingDataService.updateResetStausInPortal(uniqueID, "Accepted", responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("GD")) {

				String fileName = data.getStatus().getStatus();
				if(status.equalsIgnoreCase("Rejected")) {
					fileName=null;
				}
				if (fileName == null || fileName.isEmpty()) {
					statusSendingDataService.updateResetStausInPortal(uniqueID, "Rejected", responseFromCharger,
							statusId);
				} else {
					statusSendingDataService.updateResetStausInPortal(uniqueID, fileName, responseFromCharger,
							statusId);
					statusNotificationDao.updateDiagnosticsFilesLocation(fileName,uniqueID);
				}
			} else if (resType.equalsIgnoreCase("VPH")) {

				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			} else if (resType.equalsIgnoreCase("CSTM")) {

				status = "Accepted";
				statusSendingDataService.updateResetStausInPortal(uniqueID, status, responseFromCharger, statusId);

			}

			
			if(data.getSecondValue() != null && data.getSecondValue().contains(":")) {
				String[] sedVal = data.getSecondValue().split(":");
				String id = "0";
				if(sedVal.length > 1) {
					id = sedVal[0];
				}
				StationLogs sl = new StationLogs();
				sl.setId(id);
				sl.setStatus(status);
				sl.setResponse(responseFromCharger);
				esLoggerUtil.updateOCPP(sl);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("null")
	public void dummyResponse(FinalData data, CopyOnWriteArrayList<WebSocketSession> sessions, WebSocketSession session,
			String clientid, String message) {
		try {
			customLogger.info(clientid, "Received Dummy message : " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String insertStnConfig(String stationRefNum, FinalData data, String type, String uniqueID, String ccStatus, WebSocketSession session) {
		String status = "";
		try {
			long stationId = stationService.getStationUniqId(stationRefNum);
			if(stationId > 0) {
				StationConfig stnConfigBoot = new StationConfig();
				for (ConfigurationKey config : data.getConfigurationKey()) {
					if (config.getKey().equalsIgnoreCase("AllowOfflineTxForUnknownId")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setAllowOfflineTxForUnknownId(Boolean.valueOf(value));
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("AuthorizationCacheEnabled")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setAuthorizationCacheEnabled(Boolean.valueOf(value));
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("AuthorizeRemoteTxRequests")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setAuthorizeRemoteTxRequests(Boolean.valueOf(value));
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("BlinkRepeat")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setBlinkRepeat(Integer.valueOf(value));
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("ClockAlignedDataInterval")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setClockAlignedDataInterval(Integer.valueOf(value));
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("ConnectionTimeOut")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setConnectionTimeOut(Integer.valueOf(value));
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("ConnectorPhaseRotation")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setConnectorPhaseRotation(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("ConnectorPhaseRotationMaxLength")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setConnectorPhaseRotationMaxLength(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("LightIntensity")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setLightIntensity(Integer.valueOf(value));
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("MeterValuesAlignedData")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMeterValuesAlignedData(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("MeterValuesAlignedDataMaxLength")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMeterValuesAlignedDataMaxLength(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("MeterValuesSampledData")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMeterValuesSampledData(value);
						status = String.valueOf(value);

					}
					if (config.getKey().equalsIgnoreCase("MeterValuesSampledDataMaxLength")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMeterValuesSampledDataMaxLength(value);
						status = String.valueOf(value);

					}
					if (config.getKey().equalsIgnoreCase("StopTxnAlignedData")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setStopTxnAlignedData(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("StopTxnAlignedDataMaxLength")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setStopTxnAlignedDataMaxLength(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("StopTxnSampledData")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setStopTxnSampledData(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("StopTxnSampledDataMaxLength")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setStopTxnSampledDataMaxLength(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("SupportedFeatureProfiles")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setSupportedFeatureProfiles(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("SupportedFeatureProfilesMaxLength")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setSupportedFeatureProfilesMaxLength(value);
						status = String.valueOf(value);
					}
					if (config.getKey().equalsIgnoreCase("TransactionMessageRetryInterval")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setTransactionMessageRetryInterval(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("HeartbeatInterval")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setHeartbeatInterval(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("MeterValueSampleInterval")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMeterValueSampleInterval(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("GetConfigurationMaxKeys")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setGetConfigurationMaxKeys(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("MinimumStatusDuration")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMinimumStatusDuration(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("NumberOfConnectors")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setNumberOfConnectors(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("ResetRetries")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setResetRetries(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("StopTransactionOnEVSideDisconnect")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setStopTransactionOnEVSideDisconnect(Boolean.parseBoolean(value));
						status = String.valueOf(Boolean.parseBoolean(value));
					}
					if (config.getKey().equalsIgnoreCase("StopTransactionOnInvalidId")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setStopTransactionOnInvalidId(Boolean.parseBoolean(value));
						status = String.valueOf(Boolean.parseBoolean(value));
					}
					if (config.getKey().equalsIgnoreCase("TransactionMessageAttempts")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setTransactionMessageAttempts(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("UnlockConnectorOnEVSideDisconnect")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setUnlockConnectorOnEVSideDisconnect(Boolean.valueOf(value));
						status = String.valueOf((value));
					}
					//				if (config.getKey().equalsIgnoreCase("ChargeBoxName")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setChargeBoxName((value));
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("ChargeBoxTimeZone")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setChargeBoxTimeZone((value));
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("ContactCenter")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setContactCenter((value));
					//					status = String.valueOf((value));
					//
					//				}
					//				if (config.getKey().equalsIgnoreCase("AvailablePaymentType")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setAvailablePaymentType((value));
					//					status = String.valueOf((value));
					//
					//				}
					//				if (config.getKey().equalsIgnoreCase("FreevendEnabled")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setFreevendEnabled((value));
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("FreevendIdTag")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setFreevendIdTag((value));
					//					status = String.valueOf((value));
					//				}
					if (config.getKey().equalsIgnoreCase("LocalAuthorizeOffline")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setLocalAuthorizeOffline(Boolean.valueOf(value));
						status = String.valueOf((value));
					}
					if (config.getKey().equalsIgnoreCase("LocalPreAuthorize")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setLocalPreAuthorize(Boolean.valueOf(value));
						status = String.valueOf((value));
					}
					if (config.getKey().equalsIgnoreCase("MaxEnergyOnInvalidId")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMaxEnergyOnInvalidId((Integer.parseInt(value)));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("MeterValuesAlignedData")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMeterValuesAlignedData(value);
						status = String.valueOf((value));
					}
					if (config.getKey().equalsIgnoreCase("SupportedFeatureProfiles")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setSupportedFeatureProfiles(value);
						status = String.valueOf((value));
					}
					//				if (config.getKey().equalsIgnoreCase("CoreRemoteTrigger")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setCoreRemoteTrigger(value);
					//					status = String.valueOf((value));
					//				}
					if (config.getKey().equalsIgnoreCase("WebSocketPingInterval")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setWebSocketPingInterval(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("ReserveConnectorZeroSupported")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setReserveConnectorZeroSupported(Boolean.valueOf(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("ChargeProfileMaxStackLevel")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setChargeProfileMaxStackLevel(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					if (config.getKey().equalsIgnoreCase("ChargingScheduleAllowedChargingRateUnit")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setChargingScheduleAllowedChargingRateUnit(value);
						status = String.valueOf((value));
					}
					if (config.getKey().equalsIgnoreCase("ChargingScheduleMaxPeriods")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setChargingScheduleMaxPeriods(Integer.parseInt(value));
						status = String.valueOf((value));
					}
					if (config.getKey().equalsIgnoreCase("ConnectorSwitch3to1PhaseSupported")) {
						String value = Utils.strValidBit(config.getValue());
						stnConfigBoot.setConnectorSwitch3to1PhaseSupported(Boolean.valueOf(value));
						status = String.valueOf(0);
					}
					if (config.getKey().equalsIgnoreCase("MaxChargingProfilesInstalled")) {
						String value = Utils.strValid(config.getValue());
						stnConfigBoot.setMaxChargingProfilesInstalled(Integer.parseInt(value));
						status = String.valueOf(Integer.parseInt(value));
					}
					//				if (config.getKey().equalsIgnoreCase("MaxChargingTimeEnabled")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setMaxChargingTimeEnabled(value);
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("MaxChargingTime")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setMaxChargingTime(value);
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("MaxSOCLimit")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setMaxSOCLimit(value);
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("CreditCardIdTag")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setCreditCardIdTag(value);
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("QRCodeContent")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setQRCodeContent(value);
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("RatedPMax")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setRatedPMax(value);
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("SilentFreevendEnabled")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setSilentFreevendEnabled(value);
					//					status = String.valueOf((value));
					//				}
					//				if (config.getKey().equalsIgnoreCase("AuthorizationRequired")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setAuthorizationRequired(Boolean.valueOf(value));
					//					status = String.valueOf(value);
					//				}
					//				if (config.getKey().equalsIgnoreCase("NonAuthorizedTag")) {
					//					String value = Utils.strValid(config.getValue());
					//					stnConfigBoot.setNonAuthorizedTag(value);
					//					status = String.valueOf(value);
					//				}
				}
				OCPPStationConfigurationForBootNotificationService.insertData(stnConfigBoot, stationId, stationRefNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public void dataTransfer(FinalData finalData, Map<String, WebSocketSession> webSocketSessionwithStationId,WebSocketSession session, String reqFrom,
			 Object receiveMsg, String clientId,long stationUniId) {
		Datatransfer datatransfer = finalData.getDatatransfer();
		Long stationId = datatransfer.getStationId();
		String uniqueID = finalData.getSecondValue();
		String stationRefNum = "0";
		String msg = "";
		try {
			Map<String, Object> stnObj = new HashMap<>();
			stnObj = stationService.getStnByRefNum(clientId);
			stationRefNum = stationId == 0 ? clientId : stationService.getstationRefNum(stationId);
			Field[] declaredFields = datatransfer.getData().getClass().getDeclaredFields();
			Object data = datatransfer.getResponseData();
			String configurationKey = "";
			String configurationValue = "";
			String configurationKey2 = "";
			String configurationValue2 = "";
			String vendorId = datatransfer.getVendorId();
			String messageId = datatransfer.getMessageId();			

			if (messageId.equalsIgnoreCase("MaxSessionTimeOut")) {
				configurationValue = datatransfer.getData().getTime();
				configurationKey = declaredFields[1].getName();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			} else if (messageId.equalsIgnoreCase("OCPPEndpointToBackend")) {
				configurationKey = declaredFields[0].getName();
				configurationValue = datatransfer.getData().getUrl();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			} else if (messageId.equalsIgnoreCase("stationMode")) {
				configurationKey = declaredFields[2].getName();
				configurationValue = datatransfer.getData().getStationMode();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			} else if (messageId.equalsIgnoreCase("SetUserPrice")) {
				configurationKey = declaredFields[3].getName();
				configurationValue = datatransfer.getData().getIdToken();
				configurationKey2 = declaredFields[4].getName();
				configurationValue2 = datatransfer.getData().getPriceText();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\",\"" + configurationKey2 + "\":\"" + configurationValue2 +"\"}}]";
			} else if (messageId.equalsIgnoreCase("evbGPSNotification")) {
				configurationKey = declaredFields[3].getName();
				configurationValue = datatransfer.getData().getInfo();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\""
						+ messageId + "\",\"data\":{\"" + configurationKey + "\":\"" + configurationValue + "\"}}]";
			}else {

				configurationValue = datatransfer.getData().getInfo();
				msg = "[2,\"" + uniqueID + "\",\"DataTransfer\",{\"vendorId\":\"" + vendorId + "\",\"messageId\":\"" + messageId + "\",\"data\":" + configurationValue + "}]";
				customLogger.info(stationRefNum, "Data Transafer invalid case");
			}
			if (Optional.ofNullable((webSocketSessionwithStationId.get(stationRefNum))).isPresent() && reqFrom.equalsIgnoreCase("portal") && webSocketSessionwithStationId.get(stationRefNum).isOpen()) {
				statusSendingDataService.addData("DataTransfer", uniqueID, stationId, "InProgress", session.getId(), 0,finalData.getFirstValue(), 0, messageId, configurationKey, configurationValue, clientId);
				customLogger.info(stationRefNum, "Data Transafer Response Send to Charger :-" + msg);
				Utils.chargerMessage((webSocketSessionwithStationId.get(stationRefNum)), msg, stationRefNum);
				esLoggerUtil.insertLongs(uniqueID,clientId,"DataTransfer",String.valueOf(receiveMsg),stationRefNum,stationUniId,"Inprogress",0);
			} else if (reqFrom.equalsIgnoreCase("portal")) {
				customLogger.info(stationRefNum,"Data Transafer Response not Send to Charger due to stationDisconnected :-");
				msg = "[3,\"" + String.valueOf(uniqueID) + "\",{\"status\":\"StationDisconnected\"}]";
				Utils.chargerMessage(session, msg, stationRefNum);
				esLoggerUtil.insertLongs(uniqueID,clientId,"DataTransfer",String.valueOf(receiveMsg),stationRefNum,stationUniId,"Station Disconnected",0);
			} else if (reqFrom.equalsIgnoreCase("charger")) {
				String dataStr = String.valueOf(data);
				if (dataStr.length() <= 254) {
					statusSendingDataService.addData("DataTransfer", uniqueID, stationId, "InProgress", session.getId(),0, finalData.getFirstValue(), 0, String.valueOf(data), configurationKey, configurationValue,clientId);
				} else {

				}
				if(messageId.equalsIgnoreCase("SignCertificate")) {
					msg = "[3,\"" + String.valueOf(uniqueID) + "\",{\"status\":\"Accepted\"}]";
				}else {
					msg = "[3,\"" + String.valueOf(uniqueID) + "\",{\"status\":\"Accepted\"}]";
				}
				customLogger.info(stationRefNum, "Data Transafer Response Send to Charger :-" + msg);
				Utils.chargerMessage(session, msg, stationRefNum);
				esLoggerUtil.updateLogs(uniqueID,"Success",msg);

				if(messageId.equalsIgnoreCase("ConnectorUnplugged")) {
					Double transactionFee = Double.valueOf(String.valueOf(stnObj.get("transactionFee")));
					String reqId =  Utils.getStationRandomNumber(stationId);
					HashMap readValue = new ObjectMapper().readValue(String.valueOf(datatransfer.getData().getInfo()), HashMap.class);
					Long transactionId=Long.parseLong(String.valueOf(readValue.get("transactionId")));
					Map<String,Object> map=oCPPStartTransactionService.getSessionData(transactionId);
					Map<String,Object> portObj = stationService.getPortByRefNumById(stationId,Long.valueOf(String.valueOf(map.get("portId"))));
				}
			}
		} catch (Exception e) {
			customLogger.info(stationRefNum, "Data Transafer Response Send to Charger :-" + msg);
			e.printStackTrace();
		}
	}
}
