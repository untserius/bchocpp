package com.axxera.ocpp.webScoket.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.config.EmailServiceImpl;
import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.dao.StatusNotificationDao;
import com.axxera.ocpp.message.MailForm;
import com.axxera.ocpp.model.ocpp.DeviceDetails;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.PushNotification;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.CurrencyConversion;
import com.axxera.ocpp.webSocket.service.StationService;



@Service
public class reservationServiceImpl implements reservationService{
	
	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private StatusNotificationDao statusNotificationDao;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private OCPPMeterValueServiceImpl ocppMeterValueService;
	
	@Autowired
	private OCPPFreeChargingService ocppFreeChargingService;
	
	@Autowired
	private CurrencyConversion currencyConversion;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private OCPPDeviceDetailsService OCPPDeviceDetailsService;

	@Autowired
	private LoggerUtil customLogger;

	@Autowired
	public PushNotification pushNotification;
	
	@Autowired
	private OCPPUserService userService;
	
	@Autowired
	private Utils Utils;
	
	@Override
	public void canncelReservationWhileSNReserved(long portId,Date statusNotifyTime,WebSocketSession session,String stationRefNum) {
		try {
			String timeStamp = utils.stringToDate(statusNotifyTime);
			String str = "select id,ISNULL(reservationId,0) as reservationId,reserveAmount,ISNULL(stationId,'0') as stationId,portId,userId from ocpp_reservation where portId = "+portId+" and '"+timeStamp+"' between startTime and endTime and chargerFaultCase=1 and cancellationFlag=0 order by id desc";
			List<Map<String, Object>> lsData = executeRepository.findAll(str);
			if(lsData.size() > 0) {
				long stationId=Long.valueOf(String.valueOf(lsData.get(0).get("stationId")));
				long reservationId = Long.valueOf(String.valueOf(lsData.get(0).get("reservationId")));
				String uniqueID = Utils.getStationRandomNumber(stationId) + ":CR";
				String msg = "[2,\"" + uniqueID + "\",\"CancelReservation\",{\"reservationId\":" + reservationId + "}]";
				utils.chargerMessage(session, msg, stationRefNum);
				String query = "update ocpp_reservation set cancellationFlag=1 where reservationId ="+reservationId+" and portId="+portId;
				executeRepository.update(query);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void cancelReservationWhileSNAvailable(long portId,Date statusNotifyTime,WebSocketSession session,String stationRefNum) {
		try {
			String timeStamp = utils.stringToDate(statusNotifyTime);
			String str = "select id,ISNULL(reservationId,0) as reservationId,reserveAmount,stationId,portId,userId from ocpp_reservation where portId = "+portId+" and '"+timeStamp+"' between startTime and endTime and chargerFaultCase=0 and flag=1 order by id desc";
			List<Map<String, Object>> lsData = executeRepository.findAll(str);
			if(lsData.size() > 0) {
				long reservationId = Long.valueOf(String.valueOf(lsData.get(0).get("reservationId")));
				long id = Long.valueOf(String.valueOf(lsData.get(0).get("id")));
				Map<String,Object> map = new HashMap<>();
				map.put("reservationId", reservationId);
				map.put("reserveAmount", String.valueOf(lsData.get(0).get("reserveAmount")));
				map.put("stationId", String.valueOf(lsData.get(0).get("stationId")));
				map.put("portId", String.valueOf(lsData.get(0).get("portId")));
				map.put("userId", String.valueOf(lsData.get(0).get("userId")));
				map.put("orgId", 1);
				cancelReservationNotification(map, stationRefNum);
				stationService.updateReservationId(0, Long.valueOf(String.valueOf(lsData.get(0).get("stationId"))), portId, Long.valueOf(String.valueOf(lsData.get(0).get("userId"))), 1,false,"",String.valueOf(reservationId),1,1,id);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateReserveWhileAvailCase(Date statusNotifyTime,Long stnUniId,Long portUniId,boolean ampFlag) {
		try {
			String timeStamp = utils.stringToDate(statusNotifyTime);
			String sql_Query = "select reservationId from ocpp_reservation where endTime >=  '"+timeStamp+"' and "
					+ " startTime <= '"+timeStamp+"' and stationId = "+stnUniId+" and portId = "+portUniId+" and flag = 1";
			List<Map<String, Object>> lsData = executeRepository.findAll(sql_Query);
			if(Optional.ofNullable(lsData).isPresent() && !lsData.isEmpty()) {
//				statusNotificationDao.updateOcppStatusNotification("Reserved",stnUniId,portUniId,ampFlag);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendReservationMail(Map<String,Object> accountsObj,long stationId,String stationRefNum,long portId,String reservationFee, Long userId) {
		try {
		String reservationId="0";
		String startTime="";
		String endTime="";
		String userName="";
		String userCurrencySymbol="";
		String displayName =stationService.getDisplayNameByPortId(portId);
		
		String query="select oc.reservationId,convert(varchar,"
				+ "isnull((select DATEADD(HOUR,CAST(SUBSTRING(replace(z.utc_code,'GMT',''),1,3) as int),DATEADD(MINUTE,CAST(SUBSTRING(replace(z.utc_code,'GMT',''),5,2) as int),oc.startTime)) from zone z where z.zone_id = p.zone_id),oc.startTime), 9)  + ' ' + isnull((select z.zone_name from zone z where z.zone_id = p.zone_id),'UTC') as userstartTime,convert(varchar,\r\n"
				+ "isnull((select DATEADD(HOUR,CAST(SUBSTRING(replace(z.utc_code,'GMT',''),1,3) as int),DATEADD(MINUTE,CAST(SUBSTRING(replace(z.utc_code,'GMT',''),5,2) as int),oc.endTime)) from zone z where z.zone_id = p.zone_id),oc.endTime), 9)  + ' ' + isnull((select z.zone_name from zone z where z.zone_id = p.zone_id),'UTC') as userEndTime from ocpp_reservation oc inner join profile p on oc.userid = p.user_id where oc.stationId= "+stationId+" and oc.portId = "+portId+" and oc.userId= "+userId+" order by oc.id desc";
		List<Map<String, Object>> lsData = executeRepository.findAll(query);
		if(lsData!=null && lsData.size()>0) {
			Map<String, Object> reservationData =lsData.get(0);
			reservationId=String.valueOf(reservationData.get("reservationId"));
			startTime=String.valueOf(reservationData.get("userstartTime"));
			endTime=String.valueOf(reservationData.get("userEndTime"));
		}
		if(accountsObj!=null) {
			userName=String.valueOf(accountsObj.get("accountName"));
			userCurrencySymbol = String.valueOf(accountsObj.get("currencySymbol"));
		}
		String mailQuery="select email from Users where UserId = "+userId;
		String mail=generalDao.getRecordBySql(mailQuery);
		String reservationFeeMail=utils.decimalwithtwoZeros(utils.decimalwithtwodecimals(new BigDecimal(reservationFee)));
		Map<String, Object> tamplateData = new HashMap<String, Object>();
		tamplateData.put("reservationId", reservationId);
		tamplateData.put("startTime", startTime);
		tamplateData.put("endTime", endTime);
		tamplateData.put("stationId", stationRefNum);
		tamplateData.put("connectorId", displayName);
		tamplateData.put("reservationFee", userCurrencySymbol+reservationFeeMail);
		tamplateData.put("userName", userName);
		tamplateData.put("mailType", "reservation");
		tamplateData.put("orgId", 1);
		tamplateData.put("StationId", stationId);
		emailServiceImpl.sendEmail(new MailForm(mail, "Reservation", ""),tamplateData,1,stationRefNum,stationId);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendCancelReservationMail(Map<String,Object> accountsObj,String reservationId,String stationRefNum,long portId,String refund, Long userId,long stationId) {
		try {
			String userName="";
			String userTime="";
			String userCurrencySymbol="";
			if(accountsObj!=null) {
				userName=String.valueOf(accountsObj.get("accountName"));
				userTime=String.valueOf(accountsObj.get("userTime"));
				userCurrencySymbol = String.valueOf(accountsObj.get("currencySymbol"));
			}
			String displayName =stationService.getDisplayNameByPortId(portId);
			String mailQuery="select email from Users where UserId = "+userId;
			String mail=generalDao.getRecordBySql(mailQuery);
			String refundForMail=utils.decimalwithtwoZeros(utils.decimalwithtwodecimals(new BigDecimal(refund)));
			String cancelReservationFee=utils.decimalwithtwoZeros(new BigDecimal(String.valueOf(accountsObj.get("cancelReservationFee"))));
			Map<String, Object> tamplateData = new HashMap<String, Object>();
			tamplateData.put("curDate", userTime);
			tamplateData.put("userName", userName);
			tamplateData.put("reservationId", reservationId);
			tamplateData.put("stationId", stationRefNum);
			tamplateData.put("connectorId",displayName);
			tamplateData.put("refund", userCurrencySymbol+refundForMail);
			tamplateData.put("cancellationFee", userCurrencySymbol+ cancelReservationFee);
			tamplateData.put("reason", "Cancellation");
			tamplateData.put("mailType", "cancelReservation");
			tamplateData.put("orgId", 1);
			tamplateData.put("StationId", stationRefNum);
			emailServiceImpl.sendEmail(new MailForm(mail, "Cancel Reservation", ""),tamplateData,1,stationRefNum,stationId);
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void sendReservationRefundMail(Map<String,Object> accountsObj,String reservationId,String stationRefNum,long portId,String refund, Long userId,long stationId) {
		try {
			String userName="";
			String userTime="";
			String userCurrencySymbol="";
			if(accountsObj!=null) {
				userName=String.valueOf(accountsObj.get("accountName"));
				userTime=String.valueOf(accountsObj.get("userTime"));
				userCurrencySymbol = String.valueOf(accountsObj.get("currencySymbol"));
			}
			String displayName =stationService.getDisplayNameByPortId(portId);
			String mailQuery="select email from Users where UserId = "+userId;
			String mail=generalDao.getRecordBySql(mailQuery);
			String refundMail=utils.decimalwithtwoZeros(utils.decimalwithtwodecimals(new BigDecimal(refund)));
			Map<String, Object> tamplateData = new HashMap<String, Object>();
			tamplateData.put("curDate", userTime);
			tamplateData.put("userName", userName);
			tamplateData.put("reservationId", reservationId);
			tamplateData.put("stationId", stationRefNum);
			tamplateData.put("connectorId",displayName);
			tamplateData.put("refund", userCurrencySymbol+refundMail);
			tamplateData.put("reason", "UnAvailable");
			tamplateData.put("mailType", "reservationRefund");
			tamplateData.put("orgId", 1);
			tamplateData.put("StationId", stationRefNum);
			emailServiceImpl.sendEmail(new MailForm(mail, "Reservation was Cancelled", ""),tamplateData,1,stationRefNum,stationId);
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public String getReservation(long stationId,long portId,long userId) {
		String reservationId=null;
		try {
		String query="select top 1 reservationId,flag from ocpp_reservation where stationId= "+stationId+" and portId ="+portId+" and userId= "+userId+" order by id desc";
		List<Map<String, Object>> lsData = executeRepository.findAll(query);
		if(lsData!=null && lsData.size()>0) {
			reservationId=String.valueOf(lsData.get(0).get("reservationId"));
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reservationId;
	}
	
	@Override
	public void reservationPushNotification(Map<String, Object> reserveData, String reason,String stationRefNum) {
		try {
			Thread th = new Thread() {
				public void run() {
					try {
						long userId=Long.valueOf(String.valueOf(reserveData.get("userId")));
						long orgId = Long.valueOf(String.valueOf(reserveData.get("orgId")));
						long reservationId =Long.valueOf(String.valueOf(reserveData.get("reservationId")));
						long portId = Long.valueOf(String.valueOf(reserveData.get("portId")));
						long stationId =  Long.valueOf(String.valueOf(reserveData.get("stationId")));
						String randomId = utils.getRandomNumber("transactionId");
						String randomId1 = utils.getRandomNumber("transactionId");

						List<DeviceDetails> deviceDetails = OCPPDeviceDetailsService.getDeviceByUser(userId);
						List<String> iOSRecipients = new JSONArray();
						Map<String, Object> orgData = userService.getOrgData(orgId, stationRefNum);
						List<String> androidRecipients = new ArrayList();

						if (deviceDetails != null) {
							deviceDetails.forEach(device -> {
								try {
									if (device.getOrgId() != null && device.getOrgId().equals(orgId)) {
										if (device.getDeviceType().equalsIgnoreCase("Android")) {
											androidRecipients.add(device.getDeviceToken());
										} else if (device.getDeviceType().equalsIgnoreCase("iOS")) {
											iOSRecipients.add(device.getDeviceToken());
											//customLogger.info(stationRefNum, "device orgId : "+device.getOrgId()+" , orgId : "+orgId+" ,iOSRecipients : "+iOSRecipients);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							});

							if (androidRecipients.size()>0) {
								JSONObject info = new JSONObject();
								JSONObject extra = new JSONObject();
								info.put("notificationId", randomId);
								info.put("title", String.valueOf(orgData.get("orgName")));
								info.put("userId", String.valueOf(userId));
								info.put("body", "");
								info.put("action", "Cancel Reservation");
								extra.put("reason",reason);
								extra.put("reservationId",String.valueOf(reservationId));
								extra.put("referNo",stationRefNum);
								extra.put("portId",String.valueOf(portId));
								extra.put("stationId",String.valueOf(stationId));
								info.put("extra",String.valueOf(extra));
								pushNotification.sendMulticastMessage(info, androidRecipients);
								
							}
							if (iOSRecipients.size()>0) {
								JSONObject info = new JSONObject();
								JSONObject extra = new JSONObject();
								info.put("mutable_content", "true");
								info.put("sound", "default");
								info.put("portId", String.valueOf(portId));
								info.put("title", String.valueOf(orgData.get("orgName")));
								info.put("type", "Cancel Reservation");
								info.put("body", "");
								info.put("categoryIdentifier", "notification");
								info.put("referNo",stationRefNum);
								info.put("reservationId",String.valueOf(reservationId));
								info.put("content-available","1");
								info.put("stationId",String.valueOf(stationId));
								info.put("reason",reason);
								pushNotification.sendMulticastMessage(info, iOSRecipients);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void cancelReservationNotification(Map<String,Object> map,String stationRefNum) {
		try {
			String reservationId=String.valueOf(map.get("reservationId"));
			BigDecimal reservationFee=new BigDecimal(String.valueOf(map.get("reserveAmount")));
			long stnUniId = Long.valueOf(String.valueOf(map.get("stationId")));
			long portUniId = Long.valueOf(String.valueOf(map.get("portId")));
			long userId = Long.valueOf(String.valueOf(map.get("userId")));
			Map<String, Object> accountsObj = ocppFreeChargingService.accntsBeanObj(userId);
			Map<String, Object> siteObj = stationService.getSiteDetails(stnUniId);
			String siteCurrency = String.valueOf(siteObj.get("currencyType"));
			Long conncetorId = Long.valueOf(portUniId);
			BigDecimal reservationFee1=reservationFee;
			if (accountsObj != null) {
				String userCurrency = String.valueOf(accountsObj.get("currencyType"));
				BigDecimal currencyRate =new BigDecimal("0") ;
				if (!userCurrency.equalsIgnoreCase(siteCurrency)) {
					reservationFee1 = currencyConversion.convertCurrency(userCurrency, siteCurrency, reservationFee);
					currencyRate=currencyConversion.currencyRate(userCurrency, siteCurrency);
				}
				BigDecimal remainingBal =new BigDecimal(String.valueOf(accountsObj.get("accountBalance"))).add(reservationFee1);
				ocppMeterValueService.insertIntoAccountTransaction(accountsObj, 0.0, "Reservation Fee (Station ID : " + stationRefNum + ") ", 
						Utils.getUtcDateFormate(new Date()), Double.parseDouble(String.valueOf(remainingBal)), "SUCCESS", Utils.getIntRandomNumber(), "0", stationRefNum, "USD",
						userCurrency, Float.parseFloat(String.valueOf(currencyRate)), Double.parseDouble(String.valueOf(reservationFee1)),"Wallet","Credit");
				String reservationFeeForMail=utils.decimalwithtwoZeros(utils.decimalwithtwodecimals(reservationFee1));
				sendReservationRefundMail(accountsObj,reservationId,stationRefNum,conncetorId,reservationFeeForMail,userId,stnUniId);
				reservationPushNotification(map,"UnAvailable",stationRefNum);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void canncelReservationWhileSNReservedForCloud(long portUniId, Date statusNotifyTime, Object object,String stationRefNum) {
		try {
			String timeStamp = utils.stringToDate(statusNotifyTime);
			String str = "select id,ISNULL(reservationId,0) as reservationId,reserveAmount,stationId,portId,userId from ocpp_reservation where portId = "+portUniId+" and '"+timeStamp+"' between startTime and endTime and chargerFaultCase=1 and cancellationFlag=0 order by id desc";
			List<Map<String, Object>> lsData = executeRepository.findAll(str);
			if(lsData.size() > 0) {
				long reservationId = Long.valueOf(String.valueOf(lsData.get(0).get("reservationId")));
				String uniqueID = String.valueOf(UUID.randomUUID()) + ":CR";
				String msg = "[2,\"" + uniqueID + "\",\"CancelReservation\",{\"reservationId\":" + reservationId + "}]";
				//utils.chargerMessage(session, msg, stationRefNum);
				//utils.sendToChargerMsg(stationRefNum, msg, "StatusNotification", uniqueID,0L);
				String query = "update ocpp_reservation set cancellationFlag=1 where reservationId ="+reservationId+" and portId="+portUniId;
				executeRepository.update(query);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
