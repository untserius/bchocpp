package com.axxera.ocpp.webScoket.serviceImpl;

import static com.axxera.ocpp.message.ChargePointErrorCode.ConnectorLockFailure;
import static com.axxera.ocpp.message.ChargePointErrorCode.EVCommunicationError;
import static com.axxera.ocpp.message.ChargePointErrorCode.GroundFailure;
import static com.axxera.ocpp.message.ChargePointErrorCode.HighTemperature;
import static com.axxera.ocpp.message.ChargePointErrorCode.InternalError;
import static com.axxera.ocpp.message.ChargePointErrorCode.LocalListConflict;
import static com.axxera.ocpp.message.ChargePointErrorCode.NoError;
import static com.axxera.ocpp.message.ChargePointErrorCode.OtherError;
import static com.axxera.ocpp.message.ChargePointErrorCode.OverCurrentFailure;
import static com.axxera.ocpp.message.ChargePointErrorCode.OverVoltage;
import static com.axxera.ocpp.message.ChargePointErrorCode.PowerMeterFailure;
import static com.axxera.ocpp.message.ChargePointErrorCode.PowerSwitchFailure;
import static com.axxera.ocpp.message.ChargePointErrorCode.ReaderFailure;
import static com.axxera.ocpp.message.ChargePointErrorCode.ResetFailure;
import static com.axxera.ocpp.message.ChargePointErrorCode.UnderVoltage;
import static com.axxera.ocpp.message.ChargePointErrorCode.WeakSignal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.config.EmailServiceImpl;
import com.axxera.ocpp.dao.StatusNotificationDao;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.MailForm;
import com.axxera.ocpp.message.StatusNotification;
import com.axxera.ocpp.model.ocpp.NotifyMe;
import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.PushNotification;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.OCPPActiveTransactionsService;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.StatusNotificationService;
import com.axxera.ocpp.webSocket.service.ocpiService;

@Service
public class StatusNotificationServiceImpl implements StatusNotificationService {

	private final static Logger logger = LoggerFactory.getLogger(StatusNotificationServiceImpl.class);

	@Autowired
	private PushNotification pushNotification;

	@Autowired
	private LoggerUtil customLogger;

	@Autowired
	private OCPPUserService userService;

	@Autowired
	private OCPPActiveTransactionsService ocppActiveTransactionsService;

	@Autowired
	private OCPPDeviceDetailsService ocppDeviceDetailsService;

	@Autowired
	private OCPPMeterValueServiceImpl ocppMeterValueServiceImpl;

	@Autowired
	private StationService stationService;

	@Autowired
	private Utils utils;

	@Autowired
	private propertiesServiceImpl propertiesServiceImpl;

	@Autowired
	private reservationService reservationService;

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Autowired
	private StatusNotificationDao statusNotificationDao;
	
	@Autowired
	private ocpiService ocpiService;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;

	@Override
	public String getStatus(long stationId, long connectorId) {
		return statusNotificationDao.getStatus(stationId, connectorId);
	}

	@Override
	public List<NotifyMe> getNotifications(long clientId) {
		return statusNotificationDao.getNotifications(clientId);
	}

	public void sendStationNotifications(Long stationId, String message, String NotifyType, String stationRefNum,String stationName) {
		try {
			List<NotifyMe> notifications = getNotifications(stationId);
			List<Map<String, Object>> orgDataList = userService.configProperties();
			Map<String, Object> map = new HashMap<>();
			List<String> deviceTokens=new ArrayList();
			if (notifications != null) {
				notifications.forEach(Notify -> {
					deviceTokens.add(Notify.getDeviceToken());
				});
			}
			if(deviceTokens.size()>0) {
				JSONObject info = new JSONObject();
				info.put("type", "Notify Me");
				info.put("categoryIdentifier", "notification");
				info.put("stationId", String.valueOf(stationId));
				info.put("referNo", stationRefNum);
				info.put("stationName", stationName);
				info.put("notificationId", utils.getRandomNumber(""));
				pushNotification.sendMulticastMessage(info, deviceTokens);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateOcppStatusNotification(String Status, Long stationId, Long connectorId,boolean ampFlag) {
		try {
			statusNotificationDao.updateOcppStatusNotification(Status, stationId, connectorId,ampFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void statusNotification(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessions,WebSocketSession session,Object requestMessage,Long stationId) {
		String msg = "";
		Long stationUniId = 0l;
		StatusNotification statusNoti = finalData.getStatusNotification();
		List<Map<String,Object>> portStatusDB = new ArrayList<>();
		try {
			boolean multipleReq=stationService.getServerHitFromChargerActivities(finalData,stationId,"StatusNotification");
			if(multipleReq) {
				String stationAvailStatus="";
				try {
					String uniqueId = String.valueOf(utils.uuid()).replaceAll("-", "").replaceAll("_", "");
					msg = "[3,\"" + finalData.getSecondValue() + "\",{}]";
					utils.chargerMessage(session, msg, stationRefNum);
					customLogger.info(stationRefNum, "Response Message : " + msg);
					Map<String, Object> portObj = null;
					Map<String, Object> stationObj = new HashMap<>();
					Map<String, Object> siteObj = new HashMap<>();
					stationObj = stationService.getStnByRefNum(stationRefNum);
					if (stationObj.size() > 0) {
						stationAvailStatus=String.valueOf(stationObj.get("stationAvailStatus"));
						stationUniId = Long.valueOf(String.valueOf(stationObj.get("id")));
						portObj = stationService.getPortByRefNum(stationUniId, statusNoti.getConnectorId());
						long portUniId = 0;
						
						String stationStatus = statusNoti.getStatus().equalsIgnoreCase("Unavailable") ? "Inoperative"
								: statusNoti.getStatus().equalsIgnoreCase("Finishing") ? "Removed"
								: statusNoti.getStatus().equalsIgnoreCase("Faulted") ? "Blocked"
								: statusNoti.getStatus().equalsIgnoreCase("Preparing") ? "Planned"
								: statusNoti.getStatus();
						siteObj = stationService.getSiteDetails(stationUniId);
						long siteId = Long.valueOf(String.valueOf(siteObj.get("siteId")));
						portUniId = Long.valueOf(String.valueOf(portObj.get("id")).equalsIgnoreCase("null") ? "0" : String.valueOf(portObj.get("id")));
						if (portUniId > 0) {
							portStatusDB = statusNotificationDao.getPortStatus(stationUniId, portUniId);
							if (statusNoti.getStatus().equalsIgnoreCase("Available")) {

								statusNotificationDao.updateStatusNotificationAvailable(stationUniId, uniqueId, statusNoti, portUniId, stationObj,portObj, stationStatus,portStatusDB);
								sendStationNotifications(stationUniId, "The charger is available now", "Notify Me",stationRefNum,String.valueOf(stationObj.get("stationName")));
								statusNotificationDao.updateStatusApiLoadManagement("Available",portUniId,stationUniId,Boolean.valueOf(String.valueOf(stationObj.get("ampFlag"))));
								reservationService.cancelReservationWhileSNAvailable(portUniId, statusNoti.getTimestamp(), session, stationRefNum);
								ocppActiveTransactionsService.updatingInCompletedTrasactions(stationRefNum, stationUniId,portUniId);
								ocppActiveTransactionsService.deleteActiveTransactionAndNotifications(stationUniId, portUniId);
								ocppActiveTransactionsService.idleChargeBilling(stationUniId, portUniId,statusNoti.getConnectedBillTime());
								ocppActiveTransactionsService.idleChargeBillingForOCPI(stationUniId, portUniId,statusNoti.getConnectedBillTime());
								
							}
							if (statusNoti.getStatus().equalsIgnoreCase("Reserved")) {
								reservationService.canncelReservationWhileSNReserved(portUniId, statusNoti.getTimestamp(), session, stationRefNum);
							}
							if (statusNoti.getStatus().equalsIgnoreCase("Unavailable") || statusNoti.getStatus().equalsIgnoreCase("Inoperative")) {
								statusNotificationDao.updateStatusNotificationUnAvailable(stationUniId, uniqueId, statusNoti, portUniId, stationObj,portObj, stationStatus,portStatusDB);
							}
							if(statusNoti.getStatus().equalsIgnoreCase("Faulted") || statusNoti.getStatus().equalsIgnoreCase("Preparing") || statusNoti.getStatus().equalsIgnoreCase("SuspendedEVSE")|| 
									statusNoti.getStatus().equalsIgnoreCase("SuspendedEV") || statusNoti.getStatus().equalsIgnoreCase("Finishing")|| statusNoti.getStatus().equalsIgnoreCase("Charging")) {
								ocppActiveTransactionsService.faultedIdleBilling(stationUniId, portUniId,statusNoti.getStatus(),statusNoti.getConnectedBillTime());
							}
							
							if (statusNoti.getStatus().equalsIgnoreCase("Preparing") && statusNoti.getConnectorId() != 0) {
								if (Boolean.valueOf(String.valueOf(stationObj.get("autocharging"))) && !Boolean.valueOf(String.valueOf(stationObj.get("ampFlag")))) {
									List<Map<String, Object>> activeTxnsBasedOnPortId = ocppMeterValueServiceImpl.getActiveTxnsBasedOnPortId(siteId, portUniId);
									if(activeTxnsBasedOnPortId.size() == 0) {
										String phoneNumber = propertiesServiceImpl.getPropety("autocharging");
										String uniqueID = utils.getStationRandomNumber(stationUniId);
										uniqueID = uniqueID.concat(":RST");
										String rstMsg = "[2,\"" + uniqueID + "\",\"RemoteStartTransaction\",{\"connectorId\":"+ statusNoti.getConnectorId() + ",\"idTag\":\"" + phoneNumber + "\"}]";
										Thread.sleep(2000);
										utils.chargerMessage(session, rstMsg, stationRefNum);
										customLogger.info(stationRefNum, "Remote start request send to charger in case of auto charge : " + rstMsg + "");
										ocppMeterValueServiceImpl.activeTransactions(portUniId, 0l,stationUniId, "RemoteStartTransaction", 0l, phoneNumber,
												"0123456789","Inprogress", "Preparing", "", 0, "",uniqueID,stationRefNum,"v2",1,"Wallet","",false);
									}else {
										customLogger.info(stationRefNum, "station already In charging with : "+activeTxnsBasedOnPortId);
									}
								}
							}
							if (statusNoti.getStatus().equalsIgnoreCase("Charging")) {
								OCPPActiveTransaction ocppActiveTrans = ocppActiveTransactionsService.getTrancation(stationUniId, portUniId);
								
								if (ocppActiveTrans != null) {
									String messageType = ocppActiveTrans.getMessageType();
									
									if (messageType != null || messageType != " ") {
										ocppActiveTransactionsService.updateActiveTrnsactions(stationUniId,statusNoti.getStatus(), portUniId, "status");
									}
								}
							}
							if (statusNoti.getStatus().equalsIgnoreCase("Preparing")
									|| statusNoti.getStatus().equalsIgnoreCase("Charging")
									|| statusNoti.getStatus().equalsIgnoreCase("SuspendedEVSE")
									|| statusNoti.getStatus().equalsIgnoreCase("SuspendedEV")
									|| statusNoti.getStatus().equalsIgnoreCase("Finishing")
									|| statusNoti.getStatus().equalsIgnoreCase("Reserved")
									|| statusNoti.getStatus().equalsIgnoreCase("Faulted")) {
								statusNotificationDao.updateStatusNotification(stationUniId, uniqueId, statusNoti, portUniId, stationObj,stationStatus,portStatusDB);
							}
							if (portStatusDB.size() > 0 && !String.valueOf(portStatusDB.get(0).get("status")).equalsIgnoreCase(stationStatus)) {
								String utcDateFormate = Utils.getUTCDateString();
								stationService.updatingLastUpdatedTime(utcDateFormate, stationUniId, siteId);
								ocpiService.postlastupdated(portUniId,Boolean.valueOf(String.valueOf(siteObj.get("ocpiflag"))));
							}
						} 
						if ((statusNoti.getStatus().equalsIgnoreCase("Faulted")
								//|| statusNoti.getStatus().equalsIgnoreCase("SuspendedEVSE")
								|| statusNoti.getStatus().equalsIgnoreCase("SuspendedEV"))
								&& String.valueOf(stationService.getStationFlags(stationUniId).get("faultedMailFlag")).equalsIgnoreCase("true")) {
							boolean faultMailCheck = statusNotificationDao.getfaultMailCheck(stationUniId,statusNoti.getStatus(),utils.getDate(statusNoti.getTimestamp()));
							if (faultMailCheck) {
								String errorCodeDescription = statusNoti.getErrorCode().equalsIgnoreCase("OverCurrentFailure")? OverCurrentFailure: statusNoti.getErrorCode().equalsIgnoreCase("ConnectorLockFailure")? ConnectorLockFailure
								: statusNoti.getErrorCode().equalsIgnoreCase("EVCommunicationError")? EVCommunicationError: statusNoti.getErrorCode().equalsIgnoreCase("GroundFailure")? GroundFailure
								: statusNoti.getErrorCode().equalsIgnoreCase("HighTemperature")? HighTemperature: statusNoti.getErrorCode().equalsIgnoreCase("InternalError")? InternalError
								: statusNoti.getErrorCode().equalsIgnoreCase("LocalListConflict")? LocalListConflict: statusNoti.getErrorCode().equalsIgnoreCase("NoError")? NoError
								: statusNoti.getErrorCode().equalsIgnoreCase("OtherError")? OtherError: statusNoti.getErrorCode().equalsIgnoreCase("OverVoltage")? OverVoltage
								: statusNoti.getErrorCode().equalsIgnoreCase("PowerMeterFailure")? PowerMeterFailure: statusNoti.getErrorCode().equalsIgnoreCase("PowerSwitchFailure")? PowerSwitchFailure
								: statusNoti.getErrorCode().equalsIgnoreCase("ReaderFailure")? ReaderFailure: statusNoti.getErrorCode().equalsIgnoreCase("ResetFailure")? ResetFailure: statusNoti.getErrorCode().equalsIgnoreCase("UnderVoltage")? UnderVoltage
								: statusNoti.getErrorCode().equalsIgnoreCase("WeakSignal")? WeakSignal: statusNoti.getErrorCode();
								Map<String, Object> tamplateData = new HashMap<String, Object>();
								long orgId = 1;
								tamplateData.put("stationStatus", "Station went to " + statusNoti.getStatus() + " State.");
								tamplateData.put("status","Below Mentioned Details are " + statusNoti.getStatus() + " Station Details.");
								tamplateData.put("orgId", orgId);
								tamplateData.put("mailType", "portStatus");
								tamplateData.put("StationId", stationRefNum);
								tamplateData.put("connectorId", "Port-" + statusNoti.getConnectorId());
								tamplateData.put("curDate", String.valueOf(new Date()));
								tamplateData.put("errorCode",statusNoti.getErrorCode() == null ? "-" : statusNoti.getErrorCode());
								tamplateData.put("vendorErrorCode",statusNoti.getVendorErrorCode() == null ? "-":statusNoti.getVendorErrorCode().equalsIgnoreCase("") ? "-":statusNoti.getVendorErrorCode());
								tamplateData.put("errorCodeDescription", errorCodeDescription);
								Map<String, Object> manufacturerName = stationService.getManufacturerName(stationUniId);
								String mails = propertiesServiceImpl.getPropety("internalWorkMails");
								List<String> ownerMails = stationService.getStnOwnerMails(stationUniId);
								if (!ownerMails.isEmpty()) {
									long stnId = stationUniId;
									ownerMails.forEach(mail -> {
										String mailSubject = stationRefNum + " Station went to " + statusNoti.getStatus()+ " state " + String.valueOf(manufacturerName).replace("{", "").replace("}", "");
										emailServiceImpl.sendEmail(new MailForm(mail, mailSubject, ""), tamplateData, orgId,stationRefNum,stnId);
									});
								}
								String mailSubject = stationRefNum + " Station went to " + statusNoti.getStatus() + " state "+ String.valueOf(manufacturerName).replace("{", "").replace("}", "");
								emailServiceImpl.sendEmail(new MailForm(mails, mailSubject, ""), tamplateData, orgId,stationRefNum,stationUniId);
								
								statusNotificationDao.updateTimeInChargerAcivites(stationUniId, utils.getDate(statusNoti.getTimestamp()));
							}
						}
						if(portUniId > 0) {
							boolean scheduleMaintenance=Boolean.parseBoolean(String.valueOf(stationObj.get("scheduleMaintenance")));
							statusNotificationDao.insertPortErrorStatus(statusNoti,stationUniId,portUniId,"Charger",uniqueId,scheduleMaintenance);
						}
//						if (statusNoti.getConnectorId() == 0 && (statusNoti.getStatus().equalsIgnoreCase("Available")
//								|| statusNoti.getStatus().equalsIgnoreCase("Preparing")
//								|| statusNoti.getStatus().equalsIgnoreCase("Charging")
//								|| statusNoti.getStatus().equalsIgnoreCase("SuspendedEVSE")
//								|| statusNoti.getStatus().equalsIgnoreCase("SuspendedEV")
//								|| statusNoti.getStatus().equalsIgnoreCase("Finishing")
//								|| statusNoti.getStatus().equalsIgnoreCase("Reserved")
//								|| statusNoti.getStatus().equalsIgnoreCase("Faulted")
//								|| statusNoti.getStatus().equalsIgnoreCase("UnAvailable")
//								|| statusNoti.getStatus().equalsIgnoreCase("Inoperative"))) {
//							int portQnty = Integer.valueOf(String.valueOf(stationObj.get("portQuantity")));
//							long notifyConntoerId = statusNoti.getConnectorId();
//							while (notifyConntoerId <= portQnty) {
//								notifyConntoerId++;
//								if (notifyConntoerId <= 3 && notifyConntoerId != 0) {
//									portUniId = stationService.getPortUniId(stationUniId, notifyConntoerId);
//									if (portUniId > 0) {
//										portStatusDB = statusNotificationDao.getPortStatus(stationUniId, portUniId);
//										statusNotificationDao.insertPortErrorStatus(statusNoti,stationUniId,portUniId,"Charger",uniqueId);
//										statusNotificationDao.updateStatusNotification(stationUniId, uniqueId, statusNoti, portUniId, stationObj,stationStatus,portStatusDB);
//										
//										ocpiService.postlastupdated(portUniId,Boolean.valueOf(String.valueOf(siteObj.get("ocpiflag"))));
//										
//										if(statusNoti.getStatus().equalsIgnoreCase("Available")) {
//											ocppActiveTransactionsService.deleteActiveTransactionAndNotifications(stationUniId, portUniId);
//											ocppActiveTransactionsService.updatingInCompletedTrasactions(stationRefNum, stationUniId,portUniId);
//											ocppActiveTransactionsService.idleChargeBilling(stationUniId, portUniId,statusNoti.getConnectedBillTime());
//											ocppActiveTransactionsService.idleChargeBillingForOCPI(stationUniId, portUniId,statusNoti.getConnectedBillTime());
//											sendStationNotifications(stationUniId, "The charger is available now", "Notify Me",stationRefNum,String.valueOf(stationObj.get("stationName")));
//										}
//										if(statusNoti.getStatus().equalsIgnoreCase("Faulted") || statusNoti.getStatus().equalsIgnoreCase("Preparing") || statusNoti.getStatus().equalsIgnoreCase("SuspendedEVSE")|| 
//												statusNoti.getStatus().equalsIgnoreCase("SuspendedEV") || statusNoti.getStatus().equalsIgnoreCase("Finishing")|| statusNoti.getStatus().equalsIgnoreCase("Charging")) {
//											ocppActiveTransactionsService.faultedIdleBilling(stationUniId, portUniId,statusNoti.getStatus(),statusNoti.getConnectedBillTime());
//										}
//									}
//								}
//							}
//						}
						String utcDateFormate = Utils.getUTCDateString();
						stationService.updatingLastUpdatedTime(utcDateFormate, stationUniId, siteId);
						
						String notificationId=Utils.getIntRandomNumber();
						sendNotificationForPortStatus("","Port Status",stationRefNum,notificationId,stationUniId,portUniId,siteId);
					}
				    
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","StatusNotification",String.valueOf(requestMessage),stationRefNum,msg,statusNoti.getStatus(),stationUniId,statusNoti.getConnectorId());
				}catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				msg = "[3,\"" + finalData.getSecondValue() + "\",{}]";
				utils.chargerMessage(session, msg, stationRefNum);
				customLogger.info(stationRefNum, "Response Message : " + msg);
//				customLogger.info(stationRefNum, "Received Same Status : "+statusNoti.getStatus());
				if(statusNoti.getStatus().equalsIgnoreCase("Available")) {
					long portUniId=0;
					Map<String,Object> portObj = stationService.getPortByRefNum(stationId, statusNoti.getConnectorId());
					portUniId = Long.valueOf(String.valueOf(portObj.get("id")).equalsIgnoreCase("null") ? "0" : String.valueOf(portObj.get("id")));
					ocppActiveTransactionsService.deleteActiveTransactionAndNotifications(stationId, portUniId);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void sendNotificationForPortStatus(String message, String NoftyType, String stationRefNum, String notificationId, long stationId, long portId,long siteId) {
		try {
			Thread thread = new Thread() {
				public void run() {
					try {
						List<Map<String, Object>> deviceDetails = ocppDeviceDetailsService.getDeviceDetailsByStation(stationId,siteId);
						List userId=new ArrayList();
						List<String> deviceTokens=new ArrayList();
						if (deviceDetails != null) {
							Map<String, Object> orgData = userService.getOrgData(1, stationRefNum);
							deviceDetails.forEach(device -> {
								if (String.valueOf(device.get("deviceType")).equalsIgnoreCase("Android")) {
									deviceTokens.add(String.valueOf(device.get("deviceToken")));
									userId.add(device.get("userId"));
								}
								if (String.valueOf(device.get("deviceType")).equalsIgnoreCase("iOS")) {
//									iOSRecipients.add(String.valueOf(device.get("deviceToken")));
									userId.add(device.get("userId"));
								}
							});
							JSONObject info = new JSONObject();
							JSONObject extra = new JSONObject();
							extra.put("stationId", String.valueOf(stationId));
							extra.put("stationName", stationRefNum);
							extra.put("portId", String.valueOf(portId));
							extra.put("siteId", String.valueOf(siteId));
							info.put("sound", "default");
							info.put("action", NoftyType);
							info.put("notificationId", notificationId);
							info.put("extra", String.valueOf(extra));
							info.put("title", String.valueOf(orgData.get("orgName")));
							info.put("body", message);
							info.put("userId","0");
							if(deviceTokens.size()>0) {
								pushNotification.sendMulticastMessage(info, deviceTokens);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			//customLogger.info(stationRefNum, "pushnotification is called : " );
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
