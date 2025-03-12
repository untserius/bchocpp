package com.axxera.ocpp.webSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.authrze;
import com.axxera.ocpp.message.Authorize;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.SessionImportedValues;
import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;
import com.axxera.ocpp.model.ocpp.OCPPRemoteStartTransaction;
import com.axxera.ocpp.service.userService;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.currencyConversionService;
import com.axxera.ocpp.webSocket.service.failedSessionService;

@Service
public class AuthorizationService {
	
	private final static Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

	@Autowired
	private LoggerUtil customLogger;

	@Autowired
	private GeneralDao<?, ?>  generalDao;

	@Autowired
	private StationService stationService;
	
	@Autowired
	private currencyConversionService crncyConversionService;
	
	@Autowired
	private failedSessionService failedSessionService;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;
	
	@Autowired
	private userService userService;
	
	@SuppressWarnings("unchecked")
	public void authorization(FinalData data, String stationRefNo,Map<String, WebSocketSession> sessions, WebSocketSession session,long stnUniqId,Object requestMessage) {
		String responseMsg ="";
		authrze auth = new authrze();
		auth.setAuth_uniId(data.getSecondValue());
		auth.setStatus("Invalid");
		auth.setUserId(0);
		auth.setIdTag(data.getAuthorize().getIdTag());
		SessionImportedValues siv = new SessionImportedValues();
		try {
			logger.info(stationRefNo+ " Inside Authorize Request : "+data.getAuthorize());
			
			Authorize authorize = data.getAuthorize();
			if (stnUniqId == 0) {
				auth.setStatus("Invalid");
				auth.setReason("Invalid Charger");
				String expiryDate = Utils.getOneYearUTC();
				responseMsg= "[3,\"" + auth.getAuth_uniId() + "\",{\"idTagInfo\":{\"status\":\"Invalid\",\"expiryDate\":\"" + expiryDate + "\",\"parentIdTag\":\"PARENT\"}}]";
				utils.chargerMessage(session, responseMsg,stationRefNo);
				customLogger.info(stationRefNo, "Response message : "+responseMsg);
			} else if(stnUniqId != 0){
				auth.setStn_obj(stationService.getStnByRefNum(stationRefNo));
				stnUniqId =  Long.valueOf(auth.getStn_obj().get("id").toString());
				auth = getRSTTxnData(auth);
				if(auth.getRst_clientId() != null && auth.getRst_clientId().equalsIgnoreCase("ocpi")) {
					auth.setAuth_validated(true);
					String expiryDate = Utils.getOneYearUTC();
					auth.setStatus("Accepted");
					responseMsg= "[3,\"" + auth.getAuth_uniId() + "\",{\"idTagInfo\":{\"status\":\""+auth.getStatus()+"\",\"expiryDate\":\"" + expiryDate + "\",\"parentIdTag\":\"PARENT\"}}]";
					utils.chargerMessage(session, responseMsg,stationRefNo);
					customLogger.info(stationRefNo, "Response message : "+responseMsg);
				}else {
					auth = getUserValidate(auth);
					auth = getStnValidate(auth);
					if(auth.isAuth_validated()) {
						String expiryDate = Utils.getOneYearUTC();
						auth.setStatus("Accepted");
						responseMsg= "[3,\"" + auth.getAuth_uniId() + "\",{\"idTagInfo\":{\"status\":\""+auth.getStatus()+"\",\"expiryDate\":\"" + expiryDate + "\",\"parentIdTag\":\"PARENT\"}}]";
						utils.chargerMessage(session, responseMsg,stationRefNo);
						customLogger.info(stationRefNo, "Response message : "+responseMsg);
						
//						insertActiveTxnAuth(auth);
						
					}else {
						String expiryDate = Utils.getOneYearUTC();
						auth.setStatus("Invalid");
						responseMsg= "[3,\"" + auth.getAuth_uniId() + "\",{\"idTagInfo\":{\"status\":\"Invalid\",\"expiryDate\":\"" + expiryDate + "\",\"parentIdTag\":\"PARENT\"}}]";
						utils.chargerMessage(session, responseMsg,stationRefNo);
						customLogger.info(stationRefNo, "Response message : "+responseMsg);
					}
				}
			}else {
				String expiryDate = Utils.getOneYearUTC();
				responseMsg= "[3,\"" + auth.getAuth_uniId() + "\",{\"idTagInfo\":{\"status\":\"Invalid\",\"expiryDate\":\"" + expiryDate + "\",\"parentIdTag\":\"PARENT\"}}]";
				utils.chargerMessage(session, responseMsg,stationRefNo);
				customLogger.info(stationRefNo, "Response message : "+responseMsg);
			}
		} catch (Exception e) {
			String expiryDate = Utils.getOneYearUTC();
			responseMsg= "[3,\"" + auth.getAuth_uniId() + "\",{\"idTagInfo\":{\"status\":\"Invalid\",\"expiryDate\":\"" + expiryDate + "\",\"parentIdTag\":\"PARENT\"}}]";
			utils.chargerMessage(session, responseMsg,stationRefNo);
			customLogger.info(stationRefNo, "Response message : "+responseMsg);
			e.printStackTrace();
		}
		if(auth.getRst_clientId() != null && auth.getRst_clientId().equalsIgnoreCase("ocpi")) {
			
		}else {
			failedSessionService.insertIntoFailedSessionsAuth(0, auth.getIdTag(), stnUniqId, 0, auth.getReason(), stationRefNo,Integer.valueOf(String.valueOf(0)) , auth.getUserId(), siv.getUserEmail(),auth.getStatus());
		}
		try {
			esLoggerUtil.insertStationLogs(auth.getAuth_uniId(),"Charger","Authorize",String.valueOf(requestMessage),stationRefNo,responseMsg,auth.getStatus(),stnUniqId,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private authrze getStnValidate(authrze auth) {
		try {
			logger.info("120 >> "+auth.isAuth_validated());
//			boolean siteTimeFlag=stationService.siteTimingsCheck(Long.parseLong(String.valueOf(auth.getStn_obj().get("siteId"))), utils.getUTCDate());
			if(auth.isRegisteredTxn()) {
				double minBalance = crncyConversionService.minBalanceCheck(Long.valueOf(String.valueOf(auth.getUser_obj().get("UserId"))), String.valueOf(auth.getUser_obj().get("currencyType")));
				logger.info("125 >> "+Double.valueOf(String.valueOf(auth.getUser_obj().get("accountBalance"))));
				logger.info("minBalance >> "+minBalance);
				
				if(String.valueOf(auth.getStn_obj().get("stationMode")).equalsIgnoreCase("freeven")) {

					auth.setAuth_validated(true);
				}else if(String.valueOf(auth.getStn_obj().get("stationMode")).equalsIgnoreCase("paymentMode")){

					if(Double.valueOf(String.valueOf(auth.getUser_obj().get("accountBalance"))) >= minBalance) {
						auth.setAuth_validated(true);
					}else {
						Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(Long.valueOf(String.valueOf(auth.getStn_obj().get("id"))),Long.valueOf(String.valueOf(auth.getUser_obj().get("UserId"))));
						logger.info("driverGroupdId : "+driverGroupdId);
						if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0 || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking"))))) {
							auth.setAuth_validated(true);
						}else {
							auth.setReason("Low Balance");
						}
					}
				}else if(String.valueOf(auth.getStn_obj().get("stationMode")).equalsIgnoreCase("drivergroup")){
					Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(Long.valueOf(String.valueOf(auth.getStn_obj().get("id"))),Long.valueOf(String.valueOf(auth.getUser_obj().get("UserId"))));
					if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0 || Double.valueOf(String.valueOf(auth.getUser_obj().get("accountBalance"))) > minBalance) || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking")))) {
						auth.setAuth_validated(true);
					}else {
						auth.setReason("Station not in public group");
					}
				}
			}else if(auth.isUnknownTxn()){
				if(String.valueOf(auth.getStn_obj().get("stationMode")).equalsIgnoreCase("freeven")) {
					auth.setAuth_validated(true);
				}else {
					auth.setReason("Unknown user");
					auth.setAuth_validated(false);
				}
			}else if(auth.isPayAsUGoTxn()) {

				auth.setAuth_validated(true);
			}else if(auth.isOcpiTxn()) {
				auth.setAuth_validated(true);
			}else if(auth.isDriverGrpIdTagTxn()) {
				auth.setAuth_validated(true);
			}else if(auth.isIdtagProfTxn()) {

				auth.setAuth_validated(true);
			}else  {
				auth.setReason("Charger Unavailable");
			}
			
			logger.info("authorize validation 139 >> "+auth.isAuth_validated());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return auth;
	}

	public authrze getUserValidate(authrze auth){
		try {
			auth.setUser_obj(userService.getUserDataByIdTag(auth.getIdTag()));
			if (!auth.getUser_obj().isEmpty()) {
				auth.setRegisteredTxn(true);
			}else {
				Map<String, Object> guestUserType = userService.getGuestUserType(auth.getIdTag(), Long.valueOf(String.valueOf(auth.getStn_obj().get("id"))));
				if(!guestUserType.isEmpty()) {
					auth.setPayAsUGoTxn(true);
					auth.setUser_obj(guestUserType);
				}else {
					Map<String, Object> driverGroupIdTag = userService.getDriverGroupIdTag(auth.getIdTag());
					if(!driverGroupIdTag.isEmpty() && Boolean.valueOf(String.valueOf(driverGroupIdTag.get("dgiFlag")))) {
						auth.setDriverGrpIdTagTxn(true);
					}else {
						logger.info("auth stn obj : "+auth.getStn_obj());
						Map<String, Object> idTagProfs = userService.manualIdCheck(auth.getIdTag(),Long.valueOf(String.valueOf(auth.getStn_obj().get("manfId"))),Long.valueOf(String.valueOf(auth.getStn_obj().get("id"))),Long.valueOf(String.valueOf(auth.getStn_obj().get("siteId"))));
						if(!idTagProfs.isEmpty() && Boolean.valueOf(String.valueOf(idTagProfs.get("flag")))) {
							auth.setIdtagProfTxn(true);
						}else {
							boolean ocpiTxn = userService.rfidOCPIAuthentication(auth.getIdTag(), String.valueOf(auth.getStn_obj().get("referNo")));
							List<Map<String, Object>> ocpiToken = getOCPIToken(auth.getIdTag());
							customLogger.info(String.valueOf(auth.getStn_obj().get("referNo")), "Data from the ocpitoken : "+ocpiToken);
							if(ocpiTxn && ocpiToken.size() > 0) {
								auth.setOcpiTxn(true);
							}else {
								auth.setUnknownTxn(true);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return auth;
	}
	public authrze getRSTTxnData(authrze auth) {
		try {
			OCPPRemoteStartTransaction rst = generalDao.findOne("From OCPPRemoteStartTransaction where " + "idTag ='"
					+ auth.getIdTag() + "' and stationId = '"+auth.getStn_obj().get("id")+"' and createdDate > DATEADD(MInute,-2,getutcdate()) order by id desc", new OCPPRemoteStartTransaction());
			if(rst != null) {
				auth.setRst_clientId(rst.getClientId());
				auth.setSite_orgId(rst.getOrgId());
			}else {
				auth.setSite_orgId(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return auth;
	}
	public List<Map<String, Object>> getOCPIToken(String idTag) {
		List<Map<String, Object>> ls = new ArrayList<>();
		try {
			String str = "select top 1 id,auth_id,contract_id,type,uid,party_id,country_code from ocpi_token where auth_token='"
					+ idTag + "' order by id desc";
			ls = generalDao.getMapData(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
	
	private void insertActiveTxnAuth(authrze auth) {
		try {
			if(auth.getActiveTxnData()==null) {
				OCPPActiveTransaction ocppActiveTransaction = new OCPPActiveTransaction();
				ocppActiveTransaction.setConnectorId(0);
				ocppActiveTransaction.setMessageType("Authorize");
				ocppActiveTransaction.setRfId(auth.getIdTag());
				ocppActiveTransaction.setSessionId("0");
				ocppActiveTransaction.setStationId(Long.valueOf(auth.getStn_obj().get("id").toString()));
				ocppActiveTransaction.setStatus("preparing");
				ocppActiveTransaction.setTransactionId(0);
				ocppActiveTransaction.setUserId(auth.isRegisteredTxn() == true ? Long.valueOf(String.valueOf(auth.getUser_obj().get("UserId"))) : 0);
				ocppActiveTransaction.setRequestedID(auth.getAuth_uniId());
				ocppActiveTransaction.setOrgId(auth.getSite_orgId());
				ocppActiveTransaction.setTimeStamp(utils.getUTCDate());
				generalDao.save(ocppActiveTransaction);
			}else {
				//Update the Session reason to EvDisconnect If any Active Session Available without any Stop Transaction and Get again StartTransaction
				OCPPActiveTransaction activeTransactionObj = auth.getActiveTxnData();
				activeTransactionObj.setRequestedID(auth.getAuth_uniId());
				activeTransactionObj.setRfId(auth.getIdTag());
				activeTransactionObj.setStatus("preparing");
				activeTransactionObj.setUserId(auth.isRegisteredTxn() == true ? Long.valueOf(String.valueOf(auth.getUser_obj().get("UserId"))) : 0);
				activeTransactionObj.setTransactionId(0);
				activeTransactionObj.setSessionId("0");
				activeTransactionObj.setTimeStamp(utils.getUTCDate());
				generalDao.update(activeTransactionObj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
