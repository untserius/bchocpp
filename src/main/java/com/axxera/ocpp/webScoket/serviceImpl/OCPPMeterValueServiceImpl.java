package com.axxera.ocpp.webScoket.serviceImpl;

import java.math.BigDecimal;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.SessionImportedValues;
import com.axxera.ocpp.model.ocpp.AccountTransactions;
import com.axxera.ocpp.model.ocpp.ActiveAndSessionForChargingActivityData;
import com.axxera.ocpp.model.ocpp.DeviceDetails;
import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;
import com.axxera.ocpp.model.ocpp.OCPPMeterValuesPojo;
import com.axxera.ocpp.model.ocpp.PrePordSession;
import com.axxera.ocpp.model.ocpp.Session;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.PushNotification;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.sessionDataService;

@Service
public class OCPPMeterValueServiceImpl {
	static Logger logger = LoggerFactory.getLogger(OCPPMeterValueServiceImpl.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;

	@Autowired
	private ExecuteRepository executeRepository;

	@Autowired
	public PushNotification pushNotification;

	@Autowired
	private OCPPDeviceDetailsService OCPPDeviceDetailsService;

	@Autowired
	private LoggerUtil customLogger;

	@Value("${kWh.Alert}")
	protected double kWhAlert;

	@Value("${duration.AlertInMins}")
	protected double durationAlertInMins;

	@Value("${revenue.Alert}")
	protected double revenueAlert;

	@Value("${mobileServerUrl}")
	protected String mobileServerUrl;

	@Value("${mobileAuthKey}")
	private String mobileAuthKey;

	@Autowired
	private OCPPUserService userService;

	@Autowired
	private Utils utils;

	@Autowired
	private EsLoggerUtil esLoggerUtil;

	public AccountTransactions insertIntoAccountTransaction(Map<String, Object> account, double amtDebit,
			String comment, Date utcTime, double remainingBalance, String status, String sessionId, String customerId,
			String stationRefNum, String currencyType, String usercurrencyType, float currencyRate, double refundAmount,
			String paymentMode, String transactionType) {
		AccountTransactions accountTransactionObj = null;
		try {
			remainingBalance = Double.parseDouble(
					String.valueOf(utils.decimalwithtwodecimals(new BigDecimal(String.valueOf(remainingBalance)))));
			String queryForacc = "FROM AccountTransactions WHERE sessionId = '" + sessionId + "' order by id desc";
			accountTransactionObj = generalDao.findOne(queryForacc, new AccountTransactions());
			String utctime = Utils.getUTCDateString();
			String query = "";
			long execute = 0;
			if (paymentMode != null && (paymentMode.contains("card") || paymentMode.equalsIgnoreCase("card"))) {
				paymentMode = "Credit Card";
			}
			if (accountTransactionObj == null) {
				query = "insert into account_transaction (amtCredit,amtDebit,comment,createTimeStamp,modifiedDate,currentBalance,customerId,"
						+ " customerIdAtStationType,status,account_id,sessionId,currencyType,currencyRate,lastUpdatedTime,paymentMode,transactionType,uid) values ('"
						+ refundAmount + "','" + amtDebit + "','" + comment + "'," + " '" + utctime + "','"+utctime+"','"
						+ remainingBalance + "','" + customerId + "','" + 1l + "','" + status + "','"
						+ account.get("accid") + "','" + sessionId + "','" + usercurrencyType + "','" + currencyRate
						+ "','" + utctime + "' ,'" + paymentMode + "','" + transactionType + "','"+utils.getuuidRandomId()+"')";
				execute = executeRepository.update(query);
				logger.info(stationRefNum + " , inserted into acc txn table : " + query);

			} else {
				query = "update account_transaction set amtCredit='0',amtDebit='" + amtDebit + "',comment='" + comment
						+ "'," + " currentBalance='" + remainingBalance + "',status='" + status + "'," + " account_id='"
						+ account.get("accid") + "',sessionId='" + sessionId + "',currencyType='" + usercurrencyType
						+ "',currencyRate='" + currencyRate + "',lastUpdatedTime='" + utctime + "' ,paymentMode='"
						+ paymentMode + "',transactionType='" + transactionType + "',modifiedDate='" +utctime+ "' where id = '"
						+ accountTransactionObj.getId() + "'";
				long updateSQL = Long.valueOf(executeRepository.update(query));
				if (updateSQL > 0) {
					execute = updateSQL;
				}
			}
			if (execute > 0) {
				accountTransactionObj = generalDao.findOne(queryForacc, new AccountTransactions());
			}
			if (!paymentMode.equalsIgnoreCase("Credit Card") && !paymentMode.equalsIgnoreCase("Card")) {
				String queryForAccountBalanceUpdate = "update Accounts set accountBalance=" + remainingBalance
						+ ", modifiedDate= GETUTCDATE() where id=" + account.get("accid");
				logger.info(stationRefNum + " , update accnts table : " + queryForAccountBalanceUpdate);
				generalDao.updateHqlQuiries(queryForAccountBalanceUpdate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountTransactionObj;

	}

	public boolean insertMeterValues(Long connectorId, Long transactionId, String randomSessionId, long stationId,
			Date meterValueTimeStamp, Double currentMeterValue, String currentImportUnit, Double finalcost,
			Date startTransTimeStamp, SessionImportedValues sessionImportedValues, FinalData finalData) {
		try {
			if (sessionImportedValues == null) {
				sessionImportedValues = new SessionImportedValues();
			}
			OCPPMeterValuesPojo meterValuesPojo = new OCPPMeterValuesPojo();
			meterValuesPojo.setPortId(connectorId);
			meterValuesPojo.setFinalCost(finalcost);
			meterValuesPojo.setSessionId(randomSessionId);
			meterValuesPojo.setStartTimeStamp(startTransTimeStamp);
			meterValuesPojo.setTransactionId(transactionId);
			meterValuesPojo.setUnit(currentImportUnit);
			meterValuesPojo.setValue(currentMeterValue);
			meterValuesPojo.setStationId(stationId);
			meterValuesPojo.setTimeStamp(meterValueTimeStamp);
			meterValuesPojo.setSoc(Double.valueOf(sessionImportedValues.getSoCValue().equalsIgnoreCase("null") ? "0"
					: sessionImportedValues.getSoCValue()));
			meterValuesPojo.setPowerActiveImport(
					Double.valueOf(sessionImportedValues.getPowerActiveImportValue().equalsIgnoreCase("null") ? "0"
							: sessionImportedValues.getPowerActiveImportValue()));
			meterValuesPojo.setEnergyActiveExportRegisterUnit(
					sessionImportedValues.getEnergyActiveExportRegisterUnit() == null ? "-"
							: sessionImportedValues.getEnergyActiveExportRegisterUnit().equalsIgnoreCase("null") ? "-"
									: sessionImportedValues.getEnergyActiveExportRegisterUnit());
			meterValuesPojo.setEnergyActiveImportRegisterUnit(
					sessionImportedValues.getEnergyActiveImportRegisterUnitES() == null ? "kWh"
							: sessionImportedValues.getEnergyActiveImportRegisterUnitES().equalsIgnoreCase("null")
									? "kWh"
									: sessionImportedValues.getEnergyActiveImportRegisterUnitES());
			meterValuesPojo.setEnergyReactiveImportRegisterUnit(
					sessionImportedValues.getEnergyReactiveImportRegisterUnit() == null ? "-"
							: sessionImportedValues.getEnergyReactiveImportRegisterUnit().equalsIgnoreCase("null") ? "-"
									: sessionImportedValues.getEnergyReactiveImportRegisterUnit());
			meterValuesPojo.setEnergyReactiveExportRegisterUnit(
					sessionImportedValues.getEnergyReactiveExportRegisterUnit() == null ? "-"
							: sessionImportedValues.getEnergyReactiveExportRegisterUnit().equalsIgnoreCase("null") ? "-"
									: sessionImportedValues.getEnergyReactiveExportRegisterUnit());
			meterValuesPojo.setEnergyActiveExportIntervalUnit(
					sessionImportedValues.getEnergyActiveExportIntervalUnit() == null ? "-"
							: sessionImportedValues.getEnergyActiveExportIntervalUnit().equalsIgnoreCase("null") ? "-"
									: sessionImportedValues.getEnergyActiveExportIntervalUnit());
			meterValuesPojo.setEnergyActiveImportIntervalUnit(
					sessionImportedValues.getEnergyActiveImportIntervalUnit() == null ? "-"
							: sessionImportedValues.getEnergyActiveImportIntervalUnit().equalsIgnoreCase("null") ? "-"
									: sessionImportedValues.getEnergyActiveImportIntervalUnit());
			meterValuesPojo.setEnergyReactiveExportIntervalUnit(
					sessionImportedValues.getEnergyActiveExportIntervalUnit() == null ? "-"
							: sessionImportedValues.getEnergyActiveExportIntervalUnit().equalsIgnoreCase("null") ? "-"
									: sessionImportedValues.getEnergyActiveExportIntervalUnit());
			meterValuesPojo.setEnergyReactiveImportIntervalUnit(
					sessionImportedValues.getEnergyReactiveImportIntervalUnit() == null ? "-"
							: sessionImportedValues.getEnergyReactiveImportIntervalUnit().equalsIgnoreCase("null") ? "-"
									: sessionImportedValues.getEnergyReactiveImportIntervalUnit());
			meterValuesPojo.setPowerActiveExportUnit(sessionImportedValues.getPowerActiveExportUnit() == null ? "-"
					: sessionImportedValues.getPowerActiveExportUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getPowerActiveExportUnit());
			meterValuesPojo.setPowerActiveImportUnit(sessionImportedValues.getPowerActiveImportUnitES() == null ? "kW"
					: sessionImportedValues.getPowerActiveImportUnitES().equalsIgnoreCase("null") ? "kW"
							: sessionImportedValues.getPowerActiveImportUnitES());
			meterValuesPojo.setPowerOfferedUnit(sessionImportedValues.getPowerOfferedUnit() == null ? "-"
					: sessionImportedValues.getPowerOfferedUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getPowerOfferedUnit());
			meterValuesPojo.setPowerReactiveExportUnit(sessionImportedValues.getPowerReactiveExportUnit() == null ? "-"
					: sessionImportedValues.getPowerReactiveExportUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getPowerReactiveExportUnit());
			meterValuesPojo.setPowerReactiveImportUnit(sessionImportedValues.getPowerReactiveImportUnit() == null ? "-"
					: sessionImportedValues.getPowerReactiveImportUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getPowerReactiveImportUnit());
			meterValuesPojo.setPowerFactorUnit(sessionImportedValues.getPowerFactorUnit() == null ? "-"
					: sessionImportedValues.getPowerFactorUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getPowerFactorUnit());
			meterValuesPojo.setCurrentImportUnit(sessionImportedValues.getCurrentImportUnit() == null ? "A"
					: sessionImportedValues.getCurrentImportUnit().equalsIgnoreCase("null") ? "A"
							: sessionImportedValues.getCurrentImportUnit());
			meterValuesPojo.setCurrentExportUnit(sessionImportedValues.getCurrentExportUnit() == null ? "-"
					: sessionImportedValues.getCurrentExportUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getCurrentExportUnit());
			meterValuesPojo.setCurrentOfferedUnit(sessionImportedValues.getCurrentOfferedUnit() == null ? "-"
					: sessionImportedValues.getCurrentOfferedUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getCurrentOfferedUnit());
			meterValuesPojo.setVoltageUnit(sessionImportedValues.getVoltageUnit() == null ? "-"
					: sessionImportedValues.getVoltageUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getVoltageUnit());
			meterValuesPojo.setFrequencyUnit(sessionImportedValues.getFrequencyUnit() == null ? "-"
					: sessionImportedValues.getFrequencyUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getFrequencyUnit());
			meterValuesPojo.setTemperatureUnit(sessionImportedValues.getTemperatureUnit() == null ? "-"
					: sessionImportedValues.getTemperatureUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getTemperatureUnit());
			meterValuesPojo.setSoCUnit(sessionImportedValues.getSoCUnit() == null ? "Percent"
					: sessionImportedValues.getSoCUnit().equalsIgnoreCase("null") ? "Percent"
							: sessionImportedValues.getSoCUnit());
			meterValuesPojo.setRPMUnit(sessionImportedValues.getRPMUnit() == null ? "-"
					: sessionImportedValues.getRPMUnit().equalsIgnoreCase("null") ? "-"
							: sessionImportedValues.getRPMUnit());

			meterValuesPojo.setEnergyActiveExportRegisterValue(
					sessionImportedValues.getEnergyActiveExportRegisterValue() == null ? 0.0
							: sessionImportedValues.getEnergyActiveExportRegisterValue().equalsIgnoreCase("null") ? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyActiveExportRegisterValue())));
			meterValuesPojo.setEnergyActiveImportRegisterValue(
					sessionImportedValues.getEnergyActiveImportRegisterValueES() == null ? 0.0
							: sessionImportedValues.getEnergyActiveImportRegisterValueES().equalsIgnoreCase("null")
									? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyActiveImportRegisterValueES())));
			meterValuesPojo.setEnergyReactiveExportRegisterValue(
					sessionImportedValues.getEnergyReactiveExportRegisterValue() == null ? 0.0
							: sessionImportedValues.getEnergyReactiveExportRegisterValue().equalsIgnoreCase("null")
									? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyReactiveExportRegisterValue())));
			meterValuesPojo.setEnergyReactiveImportRegisterValue(
					sessionImportedValues.getEnergyReactiveImportRegisterValue() == null ? 0.0
							: sessionImportedValues.getEnergyReactiveImportRegisterValue().equalsIgnoreCase("null")
									? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyReactiveImportRegisterValue())));
			meterValuesPojo.setEnergyActiveExportIntervalValue(
					sessionImportedValues.getEnergyActiveExportIntervalValue() == null ? 0.0
							: sessionImportedValues.getEnergyActiveExportIntervalValue().equalsIgnoreCase("null") ? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyActiveExportIntervalValue())));
			meterValuesPojo.setEnergyActiveImportIntervalValue(
					sessionImportedValues.getEnergyActiveImportIntervalValue() == null ? 0.0
							: sessionImportedValues.getEnergyActiveImportIntervalValue().equalsIgnoreCase("null") ? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyActiveImportIntervalValue())));
			meterValuesPojo.setEnergyReactiveExportIntervalValue(
					sessionImportedValues.getEnergyReactiveExportIntervalValue() == null ? 0.0
							: sessionImportedValues.getEnergyReactiveExportIntervalValue().equalsIgnoreCase("null")
									? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyReactiveExportIntervalValue())));
			meterValuesPojo.setEnergyReactiveImportIntervalValue(
					sessionImportedValues.getEnergyReactiveImportIntervalValue() == null ? 0.0
							: sessionImportedValues.getEnergyReactiveImportIntervalValue().equalsIgnoreCase("null")
									? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyReactiveImportIntervalValue())));
			meterValuesPojo.setPowerActiveExportValue(sessionImportedValues.getPowerActiveExportValue() == null ? 0.0
					: sessionImportedValues.getPowerActiveExportValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getPowerActiveExportValue())));
			meterValuesPojo.setPowerActiveImportValue(sessionImportedValues.getPowerActiveImportValueES() == null ? 0.0
					: sessionImportedValues.getPowerActiveImportValueES().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getPowerActiveImportValueES())));
			meterValuesPojo.setPowerOfferedValue(sessionImportedValues.getPowerOfferedValue() == null ? 0.0
					: sessionImportedValues.getPowerOfferedValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getPowerOfferedValue())));
			meterValuesPojo.setPowerReactiveExportValue(sessionImportedValues.getPowerReactiveExportValue() == null
					? 0.0
					: sessionImportedValues.getPowerReactiveExportValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getPowerReactiveExportValue())));
			meterValuesPojo.setPowerReactiveImportValue(sessionImportedValues.getPowerReactiveImportValue() == null
					? 0.0
					: sessionImportedValues.getPowerReactiveImportValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getPowerReactiveImportValue())));
			meterValuesPojo.setPowerFactorValue(sessionImportedValues.getPowerFactorValue() == null ? 0.0
					: sessionImportedValues.getPowerFactorValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getPowerFactorValue())));
			meterValuesPojo.setCurrentImportValue(sessionImportedValues.getCurrentImportValue() == null ? 0.0
					: sessionImportedValues.getCurrentImportValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getCurrentImportValue())));
			meterValuesPojo.setCurrentExportValue(sessionImportedValues.getCurrentExportValue() == null ? 0.0
					: sessionImportedValues.getCurrentExportValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getCurrentExportValue())));
			meterValuesPojo.setCurrentOfferedValue(sessionImportedValues.getCurrentOfferedValue() == null ? 0.0
					: sessionImportedValues.getCurrentOfferedValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getCurrentOfferedValue())));
			meterValuesPojo.setVoltageValue(sessionImportedValues.getVoltageValue() == null ? 0.0
					: sessionImportedValues.getVoltageValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getVoltageValue())));
			meterValuesPojo.setFrequencyValue(sessionImportedValues.getFrequencyValue() == null ? 0.0
					: sessionImportedValues.getFrequencyValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getFrequencyValue())));
			meterValuesPojo.setTemperatureValue(sessionImportedValues.getTemperatureValue() == null ? 0.0
					: sessionImportedValues.getTemperatureValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getTemperatureValue())));
			meterValuesPojo.setSoCValue(sessionImportedValues.getSoCValue() == null ? 0.0
					: sessionImportedValues.getSoCValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getSoCValue())));
			meterValuesPojo.setRPMValue(sessionImportedValues.getRPMValue() == null ? 0.0
					: sessionImportedValues.getRPMValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getRPMValue())));
			meterValuesPojo.setEnergyActiveImportRegisterDiffValue(
					sessionImportedValues.getEnergyActiveImportRegisterDiffValue() == null ? 0.0
							: sessionImportedValues.getEnergyActiveImportRegisterDiffValue().equalsIgnoreCase("null")
									? 0.0
									: Double.parseDouble(String
											.valueOf(sessionImportedValues.getEnergyActiveImportRegisterDiffValue())));
			meterValuesPojo
					.setPowerActiveImportDiffValue(sessionImportedValues.getPowerActiveImportDiffValue() == null ? 0.0
							: sessionImportedValues.getPowerActiveImportDiffValue().equalsIgnoreCase("null") ? 0.0
									: Double.parseDouble(
											String.valueOf(sessionImportedValues.getPowerActiveImportDiffValue())));
			meterValuesPojo.setCurrentImportDiffValue(sessionImportedValues.getCurrentImportDiffValue() == null ? 0.0
					: sessionImportedValues.getCurrentImportDiffValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getCurrentImportDiffValue())));
			meterValuesPojo.setSoCDiffValue(sessionImportedValues.getSoCDiffValue() == null ? 0.0
					: sessionImportedValues.getSoCDiffValue().equalsIgnoreCase("null") ? 0.0
							: Double.parseDouble(String.valueOf(sessionImportedValues.getSoCDiffValue())));
			try {
				esLoggerUtil.createOcppMeterLogsIndex(meterValuesPojo, "Charger", finalData.getSecondValue(),
						startTransTimeStamp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void updateSessionsData(long connectorid) {
		try {
			Thread th = new Thread() {
				public void run () {
					try {
						Thread.sleep(500);
						String deleteActiveTrans2 = "delete from ocpp_activeTransaction where connectorId in(" + connectorid + ")";
						executeRepository.update(deleteActiveTrans2);
						Thread.sleep(500);
						String deleteActiveTrans = "Update session set reasonForTer ='EVDisconnected',transactionStatus='completed', modifiedDate=GETUTCDATE() where reasonForTer = 'InSession' and port_id = "
								+ connectorid + " ";
						executeRepository.update(deleteActiveTrans);
					}catch (Exception e) {
						logger.info(""+e.getMessage());
					}
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPushNotification(startTxn sTxn, String NoftyType) {
		try {
			Thread thread = new Thread() {
				public void run() {
					try {
						JSONObject extra = new JSONObject();
						List<String> deviceTokens=new ArrayList();
						if (sTxn.isRegisteredTxn()) {
							List<DeviceDetails> deviceDetails = OCPPDeviceDetailsService
									.getDeviceByUser(Long.valueOf(String.valueOf(sTxn.getUserObj().get("userId"))));
							if (deviceDetails != null) {
								deviceDetails.forEach(device -> {
									try {
										if (device.getDeviceType().equalsIgnoreCase("Android")) {
											if(!String.valueOf(device.getDeviceToken()).equalsIgnoreCase("")) {
												deviceTokens.add(device.getDeviceToken());
											}
										}
										if (device.getDeviceType().equalsIgnoreCase("ios")) {
//											iOSRecipients.add(device.getDeviceToken());
										}

									} catch (Exception e) {
										e.printStackTrace();
									}
								});
							}
						} else if (sTxn.isPayAsUGoTxn()) {
							String deviceType = String.valueOf(sTxn.getUserObj().get("deviceType"));
							String deviceToken = String.valueOf(sTxn.getUserObj().get("deviceToken"));
							if (deviceType != null && deviceType.equalsIgnoreCase("Android")) {
								deviceTokens.add(deviceToken);
							} else if (deviceType != null && deviceType.equalsIgnoreCase("iOS")) {
//								iOSRecipients.add(deviceToken);
							}
						}
						Map<String, Object> orgData = userService.getOrgData(sTxn.getSite_orgId(),String.valueOf(sTxn.getStnObj().get("referNo")));
						JSONObject info = new JSONObject();
						extra.put("stationId", Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))));
						extra.put("stationName", String.valueOf(sTxn.getStnObj().get("referNo")));
						extra.put("portId", Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
						extra.put("sessionId", sTxn.getChargeSessUniqId());
						info.put("action", NoftyType);
						info.put("notificationId", utils.getIntRandomNumber(9));
						info.put("extra", String.valueOf(extra));
						info.put("body", "");
						info.put("userId",String.valueOf(sTxn.isRegisteredTxn() ? Long.valueOf(String.valueOf(sTxn.getUserObj().get("userId"))) : 0));
						if(deviceTokens.size()>0) {
							pushNotification.sendMulticastMessage(info, deviceTokens);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getActiveAndSessionForChargingActivityData(String sessionId) {
		boolean flag = false;
		try {
			List<ActiveAndSessionForChargingActivityData> findAll = generalDao.findAll(
					"FROM ActiveAndSessionForChargingActivityData where sessionId='" + sessionId + "'",
					new ActiveAndSessionForChargingActivityData());
			if (findAll.size() > 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public OCPPActiveTransaction activeTransactions(long connectorId, long transactionId, long stationId,
			String msgType, Long userId, String idTag, String sessionId, String sessionStatus, String status,
			String statusMobile, double meterStart, String portalStation, String requestedID, String stnRfrNo,
			String appVersion, long orgId, String paymentType, String rewardType, boolean selfCharging) {
		OCPPActiveTransaction activeTransactionObj = null;
		try {
			activeTransactionObj = generalDao.findOne(
					"From OCPPActiveTransaction where connectorId=" + connectorId + " and " + "stationId =" + stationId
							+ "and messageType='" + msgType + "' and userId =" + userId
							+ " and Status in ('Accepted','Preparing','Charging') order by id desc",
					new OCPPActiveTransaction());

			if (activeTransactionObj == null) {
				OCPPActiveTransaction ocppActiveTransaction = new OCPPActiveTransaction();
				ocppActiveTransaction.setConnectorId(connectorId);
				ocppActiveTransaction.setMessageType(msgType);
				ocppActiveTransaction.setRfId(idTag);
				ocppActiveTransaction.setSessionId(sessionId);
				ocppActiveTransaction.setStationId(stationId);
				ocppActiveTransaction.setStatus(status);
				ocppActiveTransaction.setTransactionId(transactionId);
				ocppActiveTransaction.setUserId(userId);
				ocppActiveTransaction.setRequestedID(requestedID);
				ocppActiveTransaction.setOrgId(orgId);
				ocppActiveTransaction.setTimeStamp(utils.getUTCDate());
				generalDao.save(ocppActiveTransaction);
			} else {
				// Update the Session reason to EvDisconnect If any Active Session Available
				// without any Stop Transaction and Get again StartTransaction
				activeTransactionObj.setRfId(idTag);
				activeTransactionObj.setUserId(userId);
				activeTransactionObj.setTransactionId(transactionId);
				activeTransactionObj.setSessionId(sessionId);
				activeTransactionObj.setTimeStamp(utils.getUTCDate());
				generalDao.update(activeTransactionObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activeTransactionObj;
	}

	public void deleteRejectedStation(String uniqueID) {
		String deleteQuery = "Delete from ocpp_activeTransaction where requestedID ='" + uniqueID + "'";
		executeRepository.update(deleteQuery);
	}

	public List<Map<String, Object>> getActiveTxnsBasedOnPortId(long stnId, long portId) {
		List<Map<String, Object>> ls = new ArrayList<>();
		try {
			String str = "select rfid,transactionId from ocpp_activeTransaction where stationId=" + stnId
					+ " and connectorid=" + portId + "";
			ls = executeRepository.findAll(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
}