package com.axxera.ocpp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.Authorize;
import com.axxera.ocpp.message.BootNotification;
import com.axxera.ocpp.message.ConfigurationKey;
import com.axxera.ocpp.message.Custom;
import com.axxera.ocpp.message.DataPojo;
import com.axxera.ocpp.message.Datatransfer;
import com.axxera.ocpp.message.DiagnosticsStatusNotification;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.FirmwareStatusNotification;
import com.axxera.ocpp.message.HeartBeatChangeConfiguration;
import com.axxera.ocpp.message.Heartbeat;
import com.axxera.ocpp.message.LogStatusNotification;
import com.axxera.ocpp.message.MeterValue;
import com.axxera.ocpp.message.MeterValues;
import com.axxera.ocpp.message.SampledValue;
import com.axxera.ocpp.message.SecurityEventNotification;
import com.axxera.ocpp.message.SignCertificate;
import com.axxera.ocpp.message.SignedFirmwareStatusNotification;
import com.axxera.ocpp.message.StartTransaction;
import com.axxera.ocpp.message.Status;
import com.axxera.ocpp.message.StatusNotification;
import com.axxera.ocpp.message.StopTransaction;
import com.axxera.ocpp.message.TransactionData;
import com.axxera.ocpp.message.UnknownKey;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class JSONDataParser {
	
	static Logger logger = LoggerFactory.getLogger(JSONDataParser.class);

	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private Utils utils;
	
	@SuppressWarnings("rawtypes")
	public FinalData getData(String jsonData,String clientId,WebSocketSession session) {
		FinalData finalData = new FinalData();
		try {
		boolean jsonValid = utils.isJSONValid(jsonData);
		if (jsonValid) {
			try {
				JSONParser jsonParser = new JSONParser();
				Object mainObj = jsonParser.parse(jsonData);
				JSONArray jsonArry = (JSONArray) mainObj;
				List<SampledValue> sampledValues = new ArrayList<SampledValue>();
				List<MeterValue> metervalueData = new ArrayList<MeterValue>();
				List<TransactionData> transactionData = new ArrayList<TransactionData>();
				List<ConfigurationKey> configurationKeys = new ArrayList<ConfigurationKey>();
				List<UnknownKey> unknownKeys = new ArrayList<UnknownKey>();
				Status st = new Status();
				finalData.setFirstValue(9l);
				for (Object object1 : jsonArry) {
					finalData.setFirstValue(Long.valueOf(String.valueOf(jsonArry.get(0))));
					finalData.setSecondValue(String.valueOf(jsonArry.get(1)));
					if (object1 != null) {
						if (object1.equals("MeterValues")) {
							MeterValues meterValues = new MeterValues();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getValue() instanceof JSONArray) {
											for (Object object : (JSONArray) me.getValue()) {
												MeterValue meterValue = new MeterValue();
												if (object instanceof JSONObject) {
													Iterator iter1 = ((JSONObject) object).entrySet().iterator();
													while (iter1.hasNext()) {
														Map.Entry me1 = (Map.Entry) iter1.next();
														if (me1.getValue() instanceof JSONArray) {
															for (Object obj : (JSONArray) me1.getValue()) {
																if (obj instanceof JSONObject) {
																	SampledValue sampledValue = new SampledValue();
																	Iterator<?> iter2 = ((JSONObject) obj).entrySet()
																			.iterator();
																	while (iter2.hasNext()) {
																		Map.Entry me2 = (Map.Entry) iter2.next();
																		if (me2.getKey().equals("phase"))
																			sampledValue.setPhase((String) me2.getValue());
																		if (me2.getKey().equals("unit"))
																			sampledValue.setUnit((String) me2.getValue());
																		if (me2.getKey().equals("context"))
																			sampledValue
																					.setContext((String) me2.getValue());
																		if (me2.getKey().equals("format"))
																			sampledValue.setFormat((String) me2.getValue());
																		if (me2.getKey().equals("measurand"))
																			sampledValue
																					.setMeasurand((String) me2.getValue());
																		if (me2.getKey().equals("location"))
																			sampledValue
																					.setLocation((String) me2.getValue());
																		if (me2.getKey().equals("value"))
																			sampledValue.setValue(
																					String.valueOf(me2.getValue()));
																	}
																	sampledValues.add(sampledValue);
																}
															}
														} else {
															if (me1.getKey().equals("timestamp")) {
																if (me1.getValue() == null || me1.getValue() == "" || me1.getValue().equals("")) {
																	meterValue.setTimestamp(utils.getUTCDate());
																	meterValues.setTimestampStr(Utils.getUTCDateString());
																} else {
																	Date startTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(me1.getValue()).replace("T", " ").replace("Z", ""));
																	meterValue.setTimestamp(startTimeStamp);
																	meterValues.setTimestampStr(utils.stringToDate(startTimeStamp));
																}
															}

														}
													}
													meterValue.setSampledValue(sampledValues);
													metervalueData.add(meterValue);
												}
											}
										} else {
											if (me.getKey().equals("connectorId")) {
												if (me.getValue() == null || me.getValue() == "") {
													meterValues.setConnectorId(0);
												} else {
													long key = ((long) me.getValue());
													meterValues.setConnectorId((int) key);
												}

											}
											if (me.getKey().equals("transactionId")) {
												if (me.getValue() == null || me.getValue() == "") {
													meterValues.setTransactionId(0);
												} else {
													long key = ((long) me.getValue());
													meterValues.setTransactionId((int) key);
												}

											}
										}
									}
									meterValues.setMeterValues(metervalueData);
								}
							}
							finalData.setMeterValues(meterValues);
						} else if (object1.equals("HeartBeatChangeConfiguration")) {
							HeartBeatChangeConfiguration heartBeatConfig = new HeartBeatChangeConfiguration();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();

										if (me.getKey().equals("value"))
											heartBeatConfig.setValue(Long.parseLong(String.valueOf(me.getValue())));

										if (me.getKey().equals("key"))
											heartBeatConfig.setKey(String.valueOf(me.getValue()));

										if (me.getKey().equals("stationids")) {

											heartBeatConfig.setStationId(String.valueOf(me.getValue()));

										}

									}
								}
							}
							finalData.setHeartBeatChangeConfiguration(heartBeatConfig);
						} else if (object1.equals("StartTransaction")) {
							StartTransaction startTransaction = new StartTransaction();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									String idTag = "";
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("reservationId")) {
											if (me.getValue() == null || me.getValue() == "") {
												startTransaction.setReservationId(0);
											}else {
												startTransaction.setReservationId(Long.valueOf(String.valueOf(me.getValue())));
											}
										}
										if (me.getKey().equals("connectorId")) {
											if (me.getValue() == null || me.getValue() == "") {
												startTransaction.setConnectorId(0);
											}else {
												startTransaction.setConnectorId(Integer.valueOf(String.valueOf(me.getValue())));
											}
										}
										if (me.getKey().equals("meterStart")) {
											if (me.getValue() == null || me.getValue() == "") {
												startTransaction.setMeterStart(0.0);
											}else {
												startTransaction.setMeterStart(Double.parseDouble(String.valueOf(me.getValue())));
											}
										}
										if (me.getKey().equals("idTag")) {
											if (String.valueOf(me.getValue()).equalsIgnoreCase("null") || String.valueOf(me.getValue()).equalsIgnoreCase("")) {
												startTransaction.setIdTag(utils.getRandomNumber(""));
											} else {
												startTransaction.setIdTag(String.valueOf(me.getValue()));
											}
											idTag = startTransaction.getIdTag();
										}
										if (me.getKey().equals("timestamp")) {
											if (me.getValue() == null || me.getValue() == "" || me.getValue().equals("")) {
												startTransaction.setTimestamp(utils.getUTCDate());
												startTransaction.setTimestampStr(Utils.getUTCDateString());
											} else {
												Date startTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(me.getValue()).replace("T", " ").replace("Z", ""));
												startTransaction.setTimestamp(startTimeStamp);
												startTransaction.setTimestampStr(utils.stringToDate(startTimeStamp));
											}
										}
										if (idTag.contains("ccIdTag")) {
											JSONObject authData = (JSONObject) jsonParser.parse(idTag);
											if (authData instanceof JSONObject) {
												Iterator au = ((JSONObject) authData).entrySet().iterator();
												while (au.hasNext()) {
													Map.Entry aut = (Map.Entry) au.next();
													String ccid = "";
													if (aut.getKey().equals("ccIdTag")) {
														ccid = String.valueOf(aut.getValue());
													}
													JSONObject ccidTag = (JSONObject) jsonParser.parse(ccid);
													if (ccidTag instanceof JSONObject) {
														Iterator cc = ((JSONObject) ccidTag).entrySet().iterator();
														while (cc.hasNext()) {
															Map.Entry ccInfo = (Map.Entry) cc.next();
															if (ccInfo.getKey().equals("CCDATA")) {
																startTransaction.setCcdata(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("maskedData")) {
																startTransaction.setMaskeddata(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("cardType")) {
																startTransaction.setCardType(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("decryptedDataLen")) {
																startTransaction.setDecrypteddataLen(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("KSN")) {
																startTransaction.setKsn(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("type")) {
																startTransaction.setType(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("drCode")) {
																startTransaction.setDrCode(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("priceCode")) {
																startTransaction.setPricecode(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("vendorId")) {
																startTransaction.setVendorid(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("paymentcode")) {
																startTransaction.setPaymentcode(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("phone")) {
																startTransaction.setPhone(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("paymentmode")) {
																startTransaction.setPaymentmode(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
														}
													}
												}
											}
										}
									}
								}
							}
							finalData.setStartTransaction(startTransaction);
						} else if (object1.equals("Authorize")) {
							Authorize auth = new Authorize();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										String authInfo = "";
										if (me.getKey().equals("idTag")) {
											authInfo = String.valueOf(me.getValue());
											if (authInfo.equalsIgnoreCase("null") || authInfo.equalsIgnoreCase("")) {
												auth.setIdTag(utils.getRandomNumber(""));
											} else {
												auth.setIdTag(String.valueOf(me.getValue()));
											}
										}
										if (authInfo.contains("ccIdTag")) {
											JSONObject authData = (JSONObject) jsonParser.parse(authInfo);
											if (authData instanceof JSONObject) {
												Iterator au = ((JSONObject) authData).entrySet().iterator();
												while (au.hasNext()) {
													Map.Entry aut = (Map.Entry) au.next();
													String ccid = "";
													if (aut.getKey().equals("ccIdTag")) {
														ccid = String.valueOf(aut.getValue());
													}
													JSONObject ccidTag = (JSONObject) jsonParser.parse(ccid);
													if (ccidTag instanceof JSONObject) {
														Iterator cc = ((JSONObject) ccidTag).entrySet().iterator();
														while (cc.hasNext()) {
															Map.Entry ccInfo = (Map.Entry) cc.next();
															if (ccInfo.getKey().equals("CCDATA")) {
																auth.setCcdata(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("maskedData")) {
																auth.setMaskeddata(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("cardType")) {
																auth.setCardType(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("decryptedDataLen")) {
																auth.setDecrypteddataLen(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("KSN")) {
																auth.setKsn(gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("type")) {
																auth.setType(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("drCode")) {
																auth.setDrCode(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("priceCode")) {
																auth.setPricecode(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("vendorId")) {
																auth.setVendorid(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
															if (ccInfo.getKey().equals("paymentcode")) {
																auth.setPaymentcode(gson.toJson(String
																		.valueOf(ccInfo.getValue()).replace("\"", "")));
															}
															if (ccInfo.getKey().equals("phone")) {
																auth.setPhone(gson.toJson(String.valueOf(ccInfo.getValue())
																		.replace("\"", "")));
															}
															if (ccInfo.getKey().equals("paymentmode")) {
																auth.setPaymentmode(
																		gson.toJson(String.valueOf(ccInfo.getValue())));
															}
														}
													}
												}
											}
										}
									}
								}
							}
							finalData.setAuthorize(auth);
						} else if (object1.equals("StatusNotification")) {

							StatusNotification statusNotification = new StatusNotification();

							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();

										if (me.getKey().equals("connectorId")) {
											long key = ((long) me.getValue());
											statusNotification.setConnectorId((int) key);
										}
										if (me.getKey().equals("errorCode"))
											statusNotification.setErrorCode((String) me.getValue());

										if (me.getKey().equals("status")) {
											statusNotification.setStatus((String) me.getValue());
										}
										if (me.getKey().equals("timestamp")) {
											if (me.getValue() == null || me.getValue() == "" || me.getValue().equals("")) {
												statusNotification.setTimestamp(utils.getUTCDate());
												statusNotification.setConnectedBillTime(utils.getUTCDate());
											} else {
												statusNotification.setTimestamp(utils.getUTCDate());
												statusNotification.setConnectedBillTime(utils.stringToDate(String.valueOf(me.getValue()).replace("T", " ").replace("Z", "")));
											}
										}
										if (me.getKey().equals("vendorErrorCode")) {
											statusNotification.setVendorErrorCode(String.valueOf(me.getValue()));
										}
										if (me.getKey().equals("info")) {
											statusNotification.setInfo(String.valueOf(me.getValue()));
										}
										if (me.getKey().equals("vendorId")) {
											statusNotification.setVendorId(String.valueOf(me.getValue()));
										}
									}
								}
							}
							finalData.setStatusNotification(statusNotification);
						} else if (object1.equals("StopTransaction")) {
							StopTransaction stopTransaction = new StopTransaction();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator<?> iter = ((JSONObject) o).entrySet().iterator();
									String idTag = "";
									while (iter.hasNext()) {
										try {

											Map.Entry me = (Map.Entry) iter.next();
											if (me.getKey().equals("transactionId"))
												stopTransaction
														.setTransactionId((Long.parseLong(String.valueOf(me.getValue()))));
											if (me.getKey().equals("reason"))
												stopTransaction.setReason((String) me.getValue());
											if (me.getKey().equals("idTag")) {
												idTag = (String) me.getValue();
												stopTransaction.setIdTag(idTag);
											}
											if (me.getKey().equals("timestamp")) {
												if (me.getValue() == null || me.getValue() == ""
														|| me.getValue().equals("")) {
													stopTransaction.setTimeStamp(utils.getUTCDate());
													stopTransaction.setTimestampStr(Utils.getUTCDateString());
												} else {
													Date startTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
															String.valueOf(me.getValue()).replace("T", " ").replace("Z", ""));
													stopTransaction.setTimeStamp(startTimeStamp);
													stopTransaction.setTimestampStr(String.valueOf(me.getValue()));
												}
											}
											if (me.getKey().equals("meterStop"))
												stopTransaction.setMeterStop(Double.parseDouble(String.valueOf(me.getValue())));
											
											
											
											
											if (me.getKey().equals("transactionData")) {
//												stopTransaction.setTransactionData((List) me.getValue());
											
											if (me.getValue() instanceof JSONArray) {
												for (Object object : (JSONArray) me.getValue()) {
													TransactionData transaction = new TransactionData();
													if (object instanceof JSONObject) {
														Iterator iter1 = ((JSONObject) object).entrySet().iterator();
														while (iter1.hasNext()) {
															Map.Entry me1 = (Map.Entry) iter1.next();
															if (me1.getValue() instanceof JSONArray) {
																for (Object obj : (JSONArray) me1.getValue()) {
																	if (obj instanceof JSONObject) {
																		SampledValue sampledValue = new SampledValue();
																		Iterator<?> iter2 = ((JSONObject) obj).entrySet()
																				.iterator();
																		while (iter2.hasNext()) {
																			Map.Entry me2 = (Map.Entry) iter2.next();
																			if (me2.getKey().equals("phase"))
																				sampledValue.setPhase((String) me2.getValue());
																			if (me2.getKey().equals("unit"))
																				sampledValue.setUnit((String) me2.getValue());
																			if (me2.getKey().equals("context"))
																				sampledValue
																						.setContext((String) me2.getValue());
																			if (me2.getKey().equals("format"))
																				sampledValue.setFormat((String) me2.getValue());
																			if (me2.getKey().equals("measurand"))
																				sampledValue
																						.setMeasurand((String) me2.getValue());
																			if (me2.getKey().equals("location"))
																				sampledValue
																						.setLocation((String) me2.getValue());
																			if (me2.getKey().equals("value"))
																				sampledValue.setValue(
																						String.valueOf(me2.getValue()));
																		}
																		sampledValues.add(sampledValue);
																	}
																}
															} else {
																if (me1.getKey().equals("timestamp")) {
																	if (me1.getValue() == null || me1.getValue() == ""
																			|| me1.getValue().equals("")) {
																		transaction.setTimestamp(utils.getUTCDate());
																	} else {
																		Date startTimeStamp = new SimpleDateFormat(
																				"yyyy-MM-dd HH:mm:ss")
																				.parse(String.valueOf(me1.getValue())
																						.replace("T", " ").replace("Z", ""));
																		transaction.setTimestamp(startTimeStamp);
																	}
																}

															}
														}
														transaction.setSampledValue(sampledValues);
														transactionData.add(transaction);
													}
												}
											}
											stopTransaction.setTransactionData(transactionData);
											}

											if (idTag.contains("ccIdTag")) {
												stopTransaction.setIdTag("ccIdTag");
												JSONObject authData = (JSONObject) jsonParser.parse(idTag);
												if (authData instanceof JSONObject) {
													Iterator au = ((JSONObject) authData).entrySet().iterator();
													while (au.hasNext()) {
														Map.Entry aut = (Map.Entry) au.next();
														String ccid = "";
														if (aut.getKey().equals("ccIdTag")) {
															ccid = String.valueOf(aut.getValue());
														}
														JSONObject ccidTag = (JSONObject) jsonParser.parse(ccid);
														if (ccidTag instanceof JSONObject) {
															Iterator cc = ((JSONObject) ccidTag).entrySet().iterator();
															while (cc.hasNext()) {
																Map.Entry ccInfo = (Map.Entry) cc.next();
																if (ccInfo.getKey().equals("CCDATA")) {
																	stopTransaction.setCcdata(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("maskedData")) {
																	stopTransaction.setMaskeddata(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("cardType")) {
																	stopTransaction.setCardType(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("decryptedDataLen")) {
																	stopTransaction.setDecrypteddataLen(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("KSN")) {
																	stopTransaction.setKsn(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("type")) {
																	stopTransaction.setType(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("drCode")) {
																	stopTransaction.setDrCode(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("priceCode")) {
																	stopTransaction.setPricecode(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("vendorId")) {
																	stopTransaction.setVendorid(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
																if (ccInfo.getKey().equals("paymentcode")) {
																	stopTransaction.setPaymentcode(gson.toJson(String
																			.valueOf(ccInfo.getValue()).replace("\"", "")));
																}
																if (ccInfo.getKey().equals("phone")) {
																	stopTransaction.setPhone(gson.toJson(String
																			.valueOf(ccInfo.getValue()).replace("\"", "")));
																}
																if (ccInfo.getKey().equals("paymentmode")) {
																	stopTransaction.setPaymentmode(
																			gson.toJson(String.valueOf(ccInfo.getValue())));
																}
															}
														}
													}
												}
											}

										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}
							finalData.setStopTransaction(stopTransaction);
						} else if (object1.equals("Heartbeat") || String.valueOf(object1).equalsIgnoreCase("Heartbeat")) {
							Heartbeat heartbeat = new Heartbeat();
							heartbeat.setId("1");
							finalData.setHeartbeat(heartbeat);
						} else if (object1.equals("SecurityEventNotification")) {
							SecurityEventNotification sen = new SecurityEventNotification();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("type"))
											sen.setType((String) me.getValue());
										if (me.getKey().equals("timestamp"))
											sen.setTimestamp((String) me.getValue());
									}
								}
							}
							finalData.setSecurityEventNotification(sen);
						} else if (object1.equals("LogStatusNotification")) {
							LogStatusNotification lsn = new LogStatusNotification();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("type"))
											lsn.setRequestId((int) me.getValue());
										if (me.getKey().equals("timestamp"))
											lsn.setStatus((String) me.getValue());
									}
								}
							}
							finalData.setLogStatusNotification(lsn);
						} else if (object1.equals("SignedFirmwareStatusNotification")) {
							SignedFirmwareStatusNotification sfsn = new SignedFirmwareStatusNotification();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("type"))
											sfsn.setRequestId((int) me.getValue());
										if (me.getKey().equals("timestamp"))
											sfsn.setStatus((String) me.getValue());
									}
								}
							}
							finalData.setSignedFirmwareStatusNotification(sfsn);
						} else if (object1.equals("BootNotification")) {
							BootNotification bootNotification = new BootNotification();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("chargePointModel"))
											bootNotification.setChargePointModel((String) me.getValue());
										if (me.getKey().equals("chargePointVendor"))
											bootNotification.setChargePointVendor((String) me.getValue());
										if (me.getKey().equals("chargePointSerialNumber"))
											bootNotification.setChargePointSerialNumber((String) me.getValue());
										if (me.getKey().equals("chargeBoxSerialNumber"))
											bootNotification.setChargeBoxSerialNumber((String) me.getValue());
										if (me.getKey().equals("firmwareVersion"))
											bootNotification.setFirmwareVersion((String) me.getValue());

									}
								}
							}
							finalData.setBootNotification(bootNotification);
						} else if (String.valueOf(object1).contains("GC") || String.valueOf(object1).contains("DCC")) {
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getValue() instanceof JSONArray) {
											if (me.getKey().equals("configurationKey")) {
												for (Object obj : (JSONArray) me.getValue()) {
													ConfigurationKey conf = new ConfigurationKey();
													if (obj instanceof JSONObject) {
														Iterator it = ((JSONObject) obj).entrySet().iterator();
														while (it.hasNext()) {
															Map.Entry m = (Map.Entry) it.next();
															if (m.getKey().equals("readonly"))
																conf.setReadonly((Boolean) m.getValue());
															if (m.getKey().equals("value")) {
																conf.setValue(String.valueOf(m.getValue()));
																st.setStatus(String.valueOf(m.getValue()));
															}
															if (m.getKey().equals("key"))
																conf.setKey((String) m.getValue());
														}
														configurationKeys.add(conf);
													}
												}
											} else if (me.getKey().equals("unknownKey")) {
												for (Object obj : (JSONArray) me.getValue()) {
													UnknownKey unk = new UnknownKey();
													if (obj instanceof JSONObject) {
														Iterator it = ((JSONObject) obj).entrySet().iterator();
														while (it.hasNext()) {
															Map.Entry m = (Map.Entry) it.next();
															if (m.getKey().equals("readonly"))
																unk.setReadonly((Boolean) m.getValue());
															if (m.getKey().equals("value"))
																unk.setValue((String) m.getValue());
															if (m.getKey().equals("key"))
																unk.setKey((String) m.getValue());
														}
														unknownKeys.add(unk);
													}
												}

											}
										}
									}
									finalData.setThirdValue(String.valueOf(o));
								}
							}
							finalData.setConfigurationKey(configurationKeys);
							finalData.setUnknownKey(unknownKeys);
							finalData.setStatus(st);
						} else if (String.valueOf(object1).contains("Datatransfer")
								|| String.valueOf(object1).contains("DataTransfer")) {
							Datatransfer datatransfer = new Datatransfer();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("vendorId"))
											datatransfer.setVendorId((String) me.getValue());
										if (me.getKey().equals("messageId"))
											datatransfer.setMessageId((String) me.getValue());
										if (me.getKey().equals("stationId"))
											datatransfer.setStationId(Long.parseLong(String.valueOf(me.getValue())));
										if (me.getKey().equals("data")) {
											datatransfer.setResponseData(me.getValue());
											DataPojo datp = new DataPojo();
											if (clientId.equalsIgnoreCase("portal")) {
												HashMap readValue = new ObjectMapper().readValue(String.valueOf(me.getValue()), HashMap.class);
												datp.setIdToken((String)readValue.get("idToken"));
												datp.setPriceText((String)readValue.get("priceText"));
												datp.setStationMode((String)readValue.get("StationMode"));
												datp.setTime((String)readValue.get("time"));
												datp.setUrl((String)readValue.get("url"));
												datp.setInfo(String.valueOf(me.getValue()));
												datatransfer.setData(datp);
											} else {
												datp.setInfo(String.valueOf(me.getValue()));
												datatransfer.setData(datp);
											}
										}
									}
								}
							}
							finalData.setDatatransfer(datatransfer);
						} else if (String.valueOf(object1).contains("FirmwareStatusNotification")) {
							FirmwareStatusNotification firmwareStatus = new FirmwareStatusNotification();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("status"))
											firmwareStatus.setStatus((String) me.getValue());
									}

								}
							}
							finalData.setFirmwareStatusNotification(firmwareStatus);

						} else if (String.valueOf(object1).contains("DiagnosticsStatusNotification")) {
							DiagnosticsStatusNotification diagnosticsStatus = new DiagnosticsStatusNotification();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("status"))
											diagnosticsStatus.setStatus((String) me.getValue());
									}

								}
							}
							finalData.setDiagnosticsStatusNotification(diagnosticsStatus);

						} else if (String.valueOf(object1).contains("customRequest")) {
							Custom cstm = new Custom();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("stationId"))
											cstm.setStnRefNo(String.valueOf(me.getValue()));
										if (me.getKey().equals("message"))
											cstm.setMessage(String.valueOf(me.getValue()));
										if (me.getKey().equals("version"))
											cstm.setVersion(String.valueOf(me.getValue()));
										if (me.getKey().equals("orgId"))
											cstm.setOrgId(Long.valueOf(String.valueOf(me.getValue())));
									}
								}
							}
							finalData.setCustom(cstm);
						} else if (String.valueOf(object1).contains("SignCertificate")) {
							SignCertificate sc = new SignCertificate();
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										sc.setCertificate(String.valueOf(iter.next()));
									}
								}
							}
							finalData.setSignCertificate(sc);
						}
						else {
							for (Object o : jsonArry) {
								st.setStatus(String.valueOf(jsonArry));
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();

									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (String.valueOf(me.getKey()).equalsIgnoreCase("status")) {
											st.setStatus((String) me.getValue());
										}
										if (String.valueOf(me.getKey()).equalsIgnoreCase("fileName")) {
											st.setStatus(String.valueOf(me.getValue()));
										}
										if (String.valueOf(me.getKey()).equalsIgnoreCase("listVersion")) {
											st.setStatus(String.valueOf(me.getValue()));
										}
										if (String.valueOf(me.getKey()).equalsIgnoreCase("data")) {
											st.setData(String.valueOf(me.getValue()));
										}
										if (String.valueOf(me.getKey()).equalsIgnoreCase("NotSupported")) {
											st.setData("NotSupported");
										}
										if (String.valueOf(me.getKey()).equalsIgnoreCase("value")) {
											st.setData(String.valueOf(me.getValue()));
										}
									}
								}
							}
							if (jsonArry.contains("NotSupported")) {
								st.setData("NotSupported");
							}
							finalData.setStatus(st);
						}

						if (clientId.equalsIgnoreCase("Portal") || clientId.equalsIgnoreCase("Android")
								|| clientId.equalsIgnoreCase("Ios") || clientId.equalsIgnoreCase("ocpi")) {
							for (Object o : jsonArry) {
								if (o instanceof JSONObject) {
									Iterator iter = ((JSONObject) o).entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry me = (Map.Entry) iter.next();
										if (me.getKey().equals("stationID") || me.getKey().equals("StationId")
												|| me.getKey().equals("stationId") || me.getKey().equals("StationID")) {
											finalData.setStnReferNum(
													String.valueOf(me.getValue()).equalsIgnoreCase("null") ? "Error"
															: String.valueOf(me.getValue()));
										} else {
											finalData.setStnReferNum(clientId);
										}
									}
								}
							}
						}
					}else {
						customLogger.info("Error", "invalid request/response from "+clientId+": "+jsonData+" / final data obj : "+finalData);
					}
				}
			}catch (Exception e) {
				customLogger.info("Error", "invalid request/response from "+clientId+": "+jsonData+" / final data obj : "+finalData);
				e.printStackTrace();
			}
		}else {
			finalData.setFirstValue(0l);
			finalData.setStnReferNum(clientId);
			customLogger.info("Error", "invalid json format request from "+clientId+" : "+jsonData+" / final data obj : "+finalData);
		}
		return finalData;
		}catch(Exception e) {
			e.printStackTrace();
			customLogger.info("Error", "getData method at stationId : "+clientId+" / exception name : "+e+" / Received Message : "+jsonData+" / final data obj : "+finalData);
		}
		return finalData;
	}
	
	public long getUniqConnectorId(String stnReferNo ,String connectorId) {
		long val = 0;
		try {
			customLogger.info(stnReferNo,"inside method : "+stnReferNo+" , connectorId "+connectorId);
			String uniqPortIdQuery="select p.id from port p inner join station s on p.station_id = s.id "
					+ " where s.referNo='"+stnReferNo+"' and p.connector_id='"+connectorId+"'";
			customLogger.info(stnReferNo,"uniqPortIdQuery : "+uniqPortIdQuery);
			List<Map<String, Object>> findAll = executeRepository.findAll(uniqPortIdQuery);
			if(findAll.size() > 0) {
				val = Long.valueOf(String.valueOf(findAll.get(0).get("id")));
			}else {
				val = 0;
			}
			customLogger.info(stnReferNo,"uniqPortId : "+val);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return val;
	}
}