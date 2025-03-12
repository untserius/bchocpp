package com.axxera.ocpp.serviceImpl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.model.es.OCPPChargingIntervalData;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.service.ChargingIntervalService;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChargingIntervalServiceImpl implements ChargingIntervalService {
	
	private final static Logger logger = LoggerFactory.getLogger(ChargingIntervalServiceImpl.class);
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void chargingIntervalDataLogs(startTxn sTxn) {
		try {
			String vendingUnit="kWh";
			double vendingPrice=0.0;
			
			if(sTxn.getTxnData()!=null && sTxn.getTxnData().getBillingCases()!=null ) {
				if(sTxn.getTxnData().getTariff_prices()!=null) {
					JsonNode tariff = objectMapper.readTree(sTxn.getTxnData().getTariff_prices());
					if(tariff.size()>0) {
						JsonNode prices = objectMapper.readTree(String.valueOf(tariff.get(0).get("cost_info")));
						if(prices.size()>0) {
							JsonNode standard = objectMapper.readTree(String.valueOf(prices.get(0).get("standard")));
							if(standard.size()>0) {
								JsonNode time = objectMapper.readTree(String.valueOf(standard.get("time")));
								if(time.size()>0) {
									vendingPrice = time.get("price").asDouble();
									if(time.get("step").asDouble()==3600) {
										vendingUnit="Hr";
									}else {
										vendingUnit="Min";
									}
								}
								JsonNode energy = objectMapper.readTree(String.valueOf(standard.get("energy")));
								if(energy.size()>0) {
									vendingUnit="kWh";
									vendingPrice=energy.get("price").asDouble();
								}
							}
						}
					}
				}
			}
			String stnRefNum = sTxn.getStnRefNum();
			Long userId = sTxn.isRegisteredTxn() == true ? Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))) : 0;
			OCPPChargingIntervalData ocppChargingIntervalData = new OCPPChargingIntervalData();
			ocppChargingIntervalData.setId(utils.getuuidRandomId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId")))));
			ocppChargingIntervalData.setStationId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))));
			ocppChargingIntervalData.setPortId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
			ocppChargingIntervalData.setSessionId(String.valueOf(sTxn.getSession().getId()));
			ocppChargingIntervalData.setStartTimestamp(sTxn.getStartTime());
			ocppChargingIntervalData.setEndTimestamp(sTxn.getStartTime());
			ocppChargingIntervalData.setNextIntervalTimestamp(nextIntervalTime(sTxn.getStartTime(), stnRefNum));
			ocppChargingIntervalData.setIdTag(sTxn.getSession().getCustomerId());
			ocppChargingIntervalData.setPortPriceUnit(vendingUnit);
			ocppChargingIntervalData.setPortPrice(vendingPrice);
			ocppChargingIntervalData.setReasonForTermination("Insession");
			ocppChargingIntervalData.setUserId(userId);
			ocppChargingIntervalData.setkWUsed(0);
			ocppChargingIntervalData.setSpent(0);
			ocppChargingIntervalData.setTransactionCost(0);
			ocppChargingIntervalData.setIntervalDuration(0);
			ocppChargingIntervalData.setPowerActiveImport(sTxn.getSession().getPowerActiveImport_value());
			ocppChargingIntervalData.setStartInterval(startIntervalTime(sTxn.getStartTime(), stnRefNum));
			ocppChargingIntervalData.setEndInterval(endIntervalTime(sTxn.getStartTime(), stnRefNum));
			esLoggerUtil.createOCPPChargingIntervalDataIndex(ocppChargingIntervalData);
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public Date nextIntervalTime(Date data, String stnRefNum) {
		try {
			String sql = "select datepart(minute, '" + utils.stringToDate(data) + "') as current_minu,datepart(HOUR, '"
					+ utils.stringToDate(data) + "') as current_hr,datepart(SECOND, '" + utils.stringToDate(data)
					+ "') as current_sec";
			Map<String, Object> map = executeRepository.findAll(sql).get(0);
			double meterMins = Double.valueOf(String.valueOf(map.get("current_minu")));
			double meterHrs = Double.valueOf(String.valueOf(map.get("current_hr")));
			double meterSec = Double.valueOf(String.valueOf(map.get("current_sec")));
			Date meterDate = utils.getDateFrmt(data);

			if ((meterMins >= 0.00 || meterMins >= 60.00) && meterMins < 15.00) {
				meterMins = 15.00;
			} else if (meterMins >= 15.00 && meterMins < 30.00) {
				meterMins = 30.00;
			} else if (meterMins >= 30.00 && meterMins < 45.00) {
				meterMins = 45.00;
			} else if (meterMins >= 45.00 && (meterMins >= 0.00 || meterMins <= 60.00)) {
				meterMins = 60.00;
				// meterHrs = meterHrs + 1;
			} /*
				 * else if(meterSec > 0) { meterMins = 60.00; meterHrs = meterHrs + 1; }
				 */
			sql = "SELECT DATEADD(mi, " + meterMins + ",DATEADD(hour, " + meterHrs + ",'"
					+ utils.stringToDate(meterDate) + "')) as ElapsedTime";
			Map<String, Object> map2 = executeRepository.findAll(sql).get(0);
			data = utils.stringToDate(String.valueOf(map2.get("ElapsedTime")));
		} catch (Exception e) {
			logger.error("",e);
		}
		return data;
	}
	
	public Date startIntervalTime(Date data, String stnRefNum) {
		try {
			String sql = "select datepart(minute, '" + utils.stringToDate(data) + "') as current_minu,datepart(HOUR, '"
					+ utils.stringToDate(data) + "') as current_hr,datepart(SECOND, '" + utils.stringToDate(data)
					+ "') as current_sec";
			Map<String, Object> map = executeRepository.findAll(sql).get(0);
			double meterMins = Double.valueOf(String.valueOf(map.get("current_minu")));
			double meterHrs = Double.valueOf(String.valueOf(map.get("current_hr")));
			double meterSec = Double.valueOf(String.valueOf(map.get("current_sec")));
			Date meterDate = utils.getDateFrmt(data);

			if ((meterMins >= 0.00 || meterMins >= 60.00) && meterMins < 15.00) {
				meterMins = 0.00;
			} else if (meterMins >= 15.00 && meterMins < 30.00) {
				meterMins = 15.00;
			} else if (meterMins >= 30.00 && meterMins < 45.00) {
				meterMins = 30.00;
			} else if (meterMins >= 45.00 && (meterMins >= 0.00 || meterMins <= 60.00)) {
				meterMins = 45.00;
				// meterHrs = meterHrs + 1;
			} /*
				 * else if(meterSec > 0) { meterMins = 60.00; meterHrs = meterHrs + 1; }
				 */
			sql = "SELECT DATEADD(mi, " + meterMins + ",DATEADD(hour, " + meterHrs + ",'"
					+ utils.stringToDate(meterDate) + "')) as ElapsedTime";
			Map<String, Object> map2 = executeRepository.findAll(sql).get(0);
			data = utils.stringToDate(String.valueOf(map2.get("ElapsedTime")));
		} catch (Exception e) {
			logger.error("",e);
		}
		return data;
	}
	public Date endIntervalTime(Date data, String stnRefNum) {
		try {
			String sql = "select datepart(minute, '" + utils.stringToDate(data) + "') as current_minu,datepart(HOUR, '"
					+ utils.stringToDate(data) + "') as current_hr,datepart(SECOND, '" + utils.stringToDate(data)
					+ "') as current_sec";
			Map<String, Object> map = executeRepository.findAll(sql).get(0);
			double meterMins = Double.valueOf(String.valueOf(map.get("current_minu")));
			double meterHrs = Double.valueOf(String.valueOf(map.get("current_hr")));
			double meterSec = Double.valueOf(String.valueOf(map.get("current_sec")));
			Date meterDate = utils.getDateFrmt(data);

			if (meterMins < 15.00) {
				meterMins = 14.00;
			} else if (meterMins >= 15.00 && meterMins < 30.00) {
				meterMins = 29.00;
			} else if (meterMins >= 30.00 && meterMins < 45.00) {
				meterMins = 44.00;
			} else if (meterMins >= 45.00 && (meterMins >= 0.00 || meterMins <= 60.00)) {
				meterMins = 59.00;
				// meterHrs = meterHrs + 1;
			} /*
				 * else if(meterSec > 0) { meterMins = 60.00; meterHrs = meterHrs + 1; }
				 */
			sql = "SELECT DATEADD(SECOND, 59,DATEADD(mi, " + meterMins + ",DATEADD(hour, " + meterHrs + ",'"
					+ utils.stringToDate(meterDate) + "'))) as ElapsedTime";
			Map<String, Object> map2 = executeRepository.findAll(sql).get(0);
			data = utils.stringToDate(String.valueOf(map2.get("ElapsedTime")));
		} catch (Exception e) {
			logger.error("",e);
		}
		return data;
	}
}
