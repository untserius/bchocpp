package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.StatusNotificationDao;
import com.axxera.ocpp.forms.remoteStart;
import com.axxera.ocpp.model.ocpp.Credentials;
import com.axxera.ocpp.service.userService;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.CurrencyConversion;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.currencyConversionService;
import com.axxera.ocpp.webSocket.service.remoteStartService;

@Service
public class remoteStartServiceImpl implements remoteStartService {
	
	static Logger logger = LoggerFactory.getLogger(remoteStartServiceImpl.class);
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private currencyConversionService crncyConversionService;
	
	@Autowired
	private OCPPAccountAndCredentialService ocppAccountAndCredentialService;
	
	@Autowired
	private userService userService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private StatusNotificationDao statusNotificationDao;
	
	@Override
	public remoteStart getRemoteStartUserValidate(remoteStart rst) {
		try{
			if(rst.getIdTag() != null && !rst.getIdTag().equalsIgnoreCase("null") && !rst.getIdTag().contains(" ") && !rst.getIdTag().equalsIgnoreCase("") ) {
				if(rst.getRst_rcvd_client().equalsIgnoreCase("Portal")) {
					Credentials credentials = ocppAccountAndCredentialService.getCredentialByrfId(rst.getIdTag());
					if(Optional.ofNullable(credentials).isPresent()) {
						rst.setUserObj(userService.getUserDataByIdTag(rst.getIdTag()));
						rst.setRegisteredTxn(true);
					}else if(String.valueOf(rst.getStnObj().get("preProduction")).equalsIgnoreCase("1")){
						Map<String, Object> userIdOnBaseRfid = ocppAccountAndCredentialService.getUserIdOnBaseRfid(rst.getIdTag());
						if(Long.valueOf(String.valueOf(userIdOnBaseRfid.get("user_id"))) > 0) {
							rst.setPreProdTxn(true);
						}
					}else {
						Map<String, Object> paygUserDetails = userService.getGuestUserType(rst.getIdTag(), Long.valueOf(String.valueOf(rst.getStationId())));
						if(paygUserDetails != null && !paygUserDetails.isEmpty()) {
							rst.setUserObj(paygUserDetails);
							rst.setPayAsUGoTxn(true);
						}else {
							Map<String, Object> driverGroupIdTag = ocppAccountAndCredentialService.getDriverGroupIdTag(rst.getIdTag());
							if(Boolean.valueOf(String.valueOf(driverGroupIdTag.get("dgiFlag")))) {
								rst.setDriverGrpIdTagTxn(true);
							}else {
								Map<String, Object> manualIdTag = ocppAccountAndCredentialService.manualIdCheck(rst.getIdTag(),Long.valueOf(String.valueOf(rst.getStnObj().get("manfId"))),rst.getStationId(),Long.valueOf(String.valueOf(rst.getSiteObj().get("siteId"))));
								logger.info("83 >> manualIdTag : "+manualIdTag);
								if(Boolean.valueOf(manualIdTag.get("flag").toString())) {
									rst.setIdTagProfileTxn(true);
								}else {
									rst.setUnknownTxn(true);
									rst.setRst_reason("Invalid Phone Number");
								}
							}
						}
					}
				}else if(rst.getRst_rcvd_client().equalsIgnoreCase("Android") || rst.getRst_rcvd_client().equalsIgnoreCase("iOS")){
					logger.info("remote start clientId android/ios case");
					Credentials credentials = ocppAccountAndCredentialService.getCredentialByrfId(rst.getIdTag());
					if(Optional.ofNullable(credentials).isPresent()) {
						rst.setUserObj(userService.getUserDataByIdTag(rst.getIdTag()));
						rst.setRegisteredTxn(true);
					}else {
						Map<String, Object> paygUserDetails = userService.getGuestUserType(rst.getIdTag(), Long.valueOf(String.valueOf(rst.getStationId())));
						if(paygUserDetails != null && !paygUserDetails.isEmpty()) {
							rst.setUserObj(paygUserDetails);
							rst.setPayAsUGoTxn(true);
						}else {
							rst.setUnknownTxn(true);
						}
					}
				}else if(rst.getRst_rcvd_client().equalsIgnoreCase("OCPI")) {
					rst.setRst_Valid(false);
					rst.setRst_reason("Invalid Client");
				}else if(rst.getRst_rcvd_client().equalsIgnoreCase("Scheduler")) {
					Credentials credentials = ocppAccountAndCredentialService.getCredentialByrfId(rst.getIdTag());
					if(Optional.ofNullable(credentials).isPresent()) {
						rst.setUserObj(userService.getUserDataByIdTag(rst.getIdTag()));
						rst.setRegisteredTxn(true);
					}else if(String.valueOf(rst.getStnObj().get("preProduction")).equalsIgnoreCase("1")){
						Map<String, Object> userIdOnBaseRfid = ocppAccountAndCredentialService.getUserIdOnBaseRfid(rst.getIdTag());
						if(Long.valueOf(String.valueOf(userIdOnBaseRfid.get("user_id"))) > 0) {
							rst.setPreProdTxn(true);
						}
					}else {
						Map<String, Object> paygUserDetails = userService.getGuestUserType(rst.getIdTag(), Long.valueOf(String.valueOf(rst.getStationId())));
						if(paygUserDetails != null && !paygUserDetails.isEmpty()) {
							rst.setUserObj(paygUserDetails);
							rst.setPayAsUGoTxn(true);
						}else {
							Map<String, Object> driverGroupIdTag = ocppAccountAndCredentialService.getDriverGroupIdTag(rst.getIdTag());
							if(Boolean.valueOf(String.valueOf(driverGroupIdTag.get("dgiFlag")))) {
								rst.setDriverGrpIdTagTxn(true);
							}else {
								Map<String, Object> manualIdTag = ocppAccountAndCredentialService.manualIdCheck(rst.getIdTag(),Long.valueOf(String.valueOf(rst.getStnObj().get("manfId"))),rst.getStationId(),Long.valueOf(String.valueOf(rst.getSiteObj().get("siteId"))));
								logger.info("83 >> manualIdTag : "+manualIdTag);
								if(Boolean.valueOf(manualIdTag.get("flag").toString())) {
									rst.setIdTagProfileTxn(true);
								}else {
									rst.setUnknownTxn(true);
									rst.setRst_reason("Invalid Phone Number");
								}
							}
						}
					}
				}else {
					rst.setRst_Valid(false);
					rst.setRst_reason("Invalid Client");
				}
			}else {
				rst.setUnknownTxn(false);
				rst.setRst_Valid(false);
				rst.setRst_reason("Invalid Phone Number");
			}
			logger.info("118 >> remote reason : "+rst.getRst_reason());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	@Override
	public remoteStart getRemoteStartStnValidate(remoteStart rst) {
		try{
			logger.info("RemoteStart155:");
			List<Map<String, Object>> portStatusLs = statusNotificationDao.getPortStatus(rst.getStationId(), rst.getPortId());
			if(portStatusLs.size() > 0) {
				String portStatus = String.valueOf(portStatusLs.get(0).get("status"));
				portStatus = (((portStatus.equalsIgnoreCase("Available")) || (portStatus.equalsIgnoreCase("Preparing"))	|| portStatus.equalsIgnoreCase("Removed")	|| portStatus.equalsIgnoreCase("Planned")) ? "Available" :
					portStatus.equalsIgnoreCase("Inoperative") ? "Unavailable" :
						portStatus.equalsIgnoreCase("Blocked") ? "Faulted" :
							portStatus.equalsIgnoreCase("Charging") ? "Occupied" :
								portStatus.equalsIgnoreCase("Reserved") ? "Reserved" :
									portStatus.equalsIgnoreCase("DriverGroup") ? "Station not in public group" : "Station Disconnected");
//				boolean siteTimeFlag=stationService.siteTimingsCheck(Long.parseLong(String.valueOf(rst.getSiteObj().get("siteId"))), utils.getUTCDate());
				if ((portStatus.equalsIgnoreCase("Available") || (portStatus.equalsIgnoreCase("Preparing")) || portStatus.equalsIgnoreCase("Removed") || portStatus.equalsIgnoreCase("Planned"))) {
					rst = remotStartBalanceValidate(rst);
				}else if(portStatus.equalsIgnoreCase("Reserved")){
					rst.setRst_reason("Reserved");
				}else {
					rst.setRst_reason("Charger Unavailable");
				}
			}else {
				rst.setRst_reason("Unavailable");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	@Override
	public remoteStart remotStartBalanceValidate(remoteStart rst)  {
		try {
			String stationMode = rst.getStnObj().get("stationMode").toString();
			logger.info("rst station mode : "+stationMode);
			logger.info("rst stationrefNum : "+rst.getStnObj().get("stnRefNum").toString());
			logger.info("rst registered user : "+rst.isRegisteredTxn());
			logger.info("rst payment mode : "+rst.getRst_paymentType());
			if(rst.isRegisteredTxn()) {
				if (stationMode.equalsIgnoreCase("Freeven")) {
					customLogger.info(String.valueOf(rst.getStnObj().get("stnRefNum")), "Inside station free billing case .....");					
					rst.setRst_reason("");
					rst.setRst_Valid(true);
				} else if (stationMode.equalsIgnoreCase("paymentMode") && rst.getRst_paymentType().equalsIgnoreCase("Wallet")) {
					customLogger.info(String.valueOf(rst.getStnObj().get("stnRefNum")), "Inside station Payment mode billing calculation case ....."+stationMode);
					double minBalance = crncyConversionService.minBalanceCheck(Long.valueOf(String.valueOf(rst.getUserObj().get("UserId"))), String.valueOf(rst.getUserObj().get("currencyType")));
					if(Double.valueOf(String.valueOf(rst.getUserObj().get("accountBalance"))) >= minBalance) {
						rst.setRst_Valid(true);
					}else {
						Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(rst.getStationId(), Long.valueOf(rst.getUserObj().get("UserId").toString()));
						if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0  || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking"))))) {
							rst.setRst_Valid(true);
						}else {
							rst.setRst_Valid(false);
							rst.setRst_reason("Low balance");
						}
					}
				}else if(stationMode.equalsIgnoreCase("drivergroup") && rst.getRst_paymentType().equalsIgnoreCase("Wallet")){
					customLogger.info(String.valueOf(rst.getStnObj().get("stnRefNum")), "Inside station Driver Group mode billing calculation case ....."+stationMode);
					Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(rst.getStationId(), Long.valueOf(rst.getUserObj().get("UserId").toString()));
					logger.info("rst station & user drivergroup data : "+driverGroupdId);
					double minBalance = crncyConversionService.minBalanceCheck(Long.valueOf(String.valueOf(rst.getUserObj().get("UserId"))), String.valueOf(rst.getUserObj().get("currencyType")));
					if(Double.valueOf(String.valueOf(rst.getUserObj().get("accountBalance"))) >= minBalance && driverGroupdId != null && !driverGroupdId.isEmpty()) {
						rst.setRst_Valid(true);
					}else if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0  || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking"))))){
						rst.setRst_Valid(true);
					} else {
						rst.setRst_reason("Station not in public group");
						rst.setRst_Valid(false);
					}
				}else if(rst.getRst_paymentType().equalsIgnoreCase("Card")) {
					rst.setRst_reason("");
					rst.setRst_Valid(true);
				}else if(stationMode.equalsIgnoreCase("paymentMode") && rst.getRst_paymentType().equalsIgnoreCase("Reward")) {
					rst.setRst_reason("");
					rst.setRst_Valid(true);
				}else if(stationMode.equalsIgnoreCase("drivergroup") && rst.getRst_paymentType().equalsIgnoreCase("Reward")) {
					customLogger.info(String.valueOf(rst.getStnObj().get("stnRefNum")), "Inside station Driver Group mode billing calculation Reward case ....."+stationMode);
					Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(rst.getStationId(), Long.valueOf(rst.getUserObj().get("UserId").toString()));
					logger.info("rst station & user drivergroup Reward data : "+driverGroupdId);
					if(driverGroupdId != null && !driverGroupdId.isEmpty()) {
						rst.setRst_Valid(true);
					}else if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0 || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking"))))){
						rst.setRst_Valid(true);
					} else {
						rst.setRst_reason("Station not in public group");
						rst.setRst_Valid(false);
					}
				}
			}else if(rst.isPayAsUGoTxn()) {
				if (stationMode.equalsIgnoreCase("Freeven")) {
					customLogger.info(String.valueOf(rst.getStnObj().get("stnRefNum")), "payg station free billing case .....");					
					rst.setRst_reason("");
					rst.setRst_Valid(true);
				} else if (stationMode.equalsIgnoreCase("paymentMode")) {
					customLogger.info(String.valueOf(rst.getStnObj().get("stnRefNum")), "payg station Payment mode billing calculation case ....."+stationMode);
					rst.setRst_Valid(true);
				}else if(stationMode.equalsIgnoreCase("drivergroup")){
					customLogger.info(String.valueOf(rst.getStnObj().get("stnRefNum")), "payg station Driver Group mode billing calculation case ....."+stationMode);
					rst.setRst_reason("Station not in public group");
					rst.setRst_Valid(false);
				}
			}else if(rst.isIdTagProfileTxn()) {
				rst.setRst_reason("");
				rst.setRst_Valid(true);
			}else if(rst.isDriverGrpIdTagTxn()) {
				rst.setRst_reason("");
				rst.setRst_Valid(true);
			}else if(rst.isPreProdTxn()) {
				rst.setRst_reason("");
				rst.setRst_Valid(true);
			}else if(rst.isUnknownTxn()) {
				if(stationMode.equalsIgnoreCase("Freeven")) {
					rst.setRst_reason("");
					rst.setRst_Valid(true);
				}else {
					rst.setRst_reason("Invalid Phone Number");
					rst.setRst_Valid(false);
				}
			}else {
				rst.setRst_reason("Invalid Phone Number");
				rst.setRst_Valid(false);
			}
			//double accountBalance = Double.valueOf(rst.getUserObj().get("accountBalance").toString());
			//Long accountId=Long.valueOf(rst.getUserObj().get("accid").toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}
	
}
