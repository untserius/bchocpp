package com.axxera.ocpp.webScoket.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.config.EmailServiceImpl;
import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.message.MailForm;
import com.axxera.ocpp.message.SessionImportedValues;
import com.axxera.ocpp.model.ocpp.OCPPSessionsData;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.sessionDataService;


@Service
public class sessionDataServiceImpl implements sessionDataService{
	private final static Logger logger = LoggerFactory.getLogger(sessionDataServiceImpl.class);
	
	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private propertiesServiceImpl propertiesServiceImpl;
	
	@Autowired
	private Utils utils;
	
	@Value("${transaction.url}")
	private String transactionURL;
	
	@Override
	public OCPPSessionsData getSessionData(String sessionId) {
		OCPPSessionsData sessData = null;
		try {
			sessData = generalDao.findOne("FROM OCPPSessionsData WHERE sessionid = '"+sessionId+"'", new OCPPSessionsData());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sessData;
	}
	
	@Override
	public void insertingSessionData(SessionImportedValues sessionImportedValues,OCPPSessionsData sessData,BigDecimal promoCodeUsedTime) {
		try {
			
			if(sessionImportedValues!=null) {
				if(sessionImportedValues.getEnergyActiveImportRegisterUnit().equalsIgnoreCase("Wh")) {
					sessionImportedValues.setLastTransactionMeterValue(sessionImportedValues.getLastTransactionMeterValue()/1000);
				}else if(sessionImportedValues.getEnergyActiveImportRegisterUnit().equalsIgnoreCase("W")) {
					sessionImportedValues.setLastTransactionMeterValue(sessionImportedValues.getLastTransactionMeterValue()/3600000);
				}
			}
			if(sessData == null) {
				sessData = new OCPPSessionsData();
				sessData.setWattSecondsUsed(sessionImportedValues.getTotalKwUsed());
				sessData.setWattSecondsUsedCount(0);
				sessData.setFirstMeterValue(true);
				sessData.setLowBalanceFlag(false);
				sessData.setNotificationFlag(false);
				sessData.setPromoCodeUsedTime(promoCodeUsedTime.doubleValue());
			}else {
				if(sessData.getWattSecondsUsed() == sessionImportedValues.getTotalKwUsed()) {
					sessData.setWattSecondsUsed(sessionImportedValues.getTotalKwUsed());
					sessData.setWattSecondsUsedCount(sessData.getWattSecondsUsedCount() + 1);
				}else {
					sessData.setWattSecondsUsed(sessionImportedValues.getTotalKwUsed());
					sessData.setWattSecondsUsedCount(0);
				}
				if(sessData.isFirstMeterValue()) {
					sessData.setFirstMeterValue(false);
					sessData.setFirstReading(sessionImportedValues.getStartTransactionMeterValue());
				}else {
					sessData.setFirstReading(sessData.getFirstReading());
				}
				sessData.setLowBalanceFlag(sessData.isLowBalanceFlag());
				sessData.setNotificationFlag(sessData.isNotificationFlag());
			}
			sessData.setEnergyCounsumptionFlag(sessionImportedValues.isEnergyConsumptionFlag());// ok
			sessData.setConnectorId(sessionImportedValues.getPortId());
			sessData.setStartTime(sessionImportedValues.getStartTransTimeStamp());
			sessData.setEndTime(sessionImportedValues.getMeterValueTimeStatmp());
			sessData.setSessionId(sessionImportedValues.getRandomSessionId());
			sessData.setUnit("kWh");
			sessData.setIdTag(sessionImportedValues.getCustomerId());
			sessData.setTransactionId(sessionImportedValues.getTransactionId());
			sessData.setKilowattHoursUsed(sessionImportedValues.getTotalKwUsed());
			sessData.setRevenue(sessionImportedValues.getFinalCostInslcCurrency());
			sessData.setStationId(sessionImportedValues.getStationId());
			sessData.setLastReading(sessionImportedValues.getLastTransactionMeterValue());
			sessData.setEnergyActiveImportRegisterUnit("kWh");
			logger.info("updated into 63 : "+promoCodeUsedTime);
			sessData.setPromoCodeUsedTime(promoCodeUsedTime.doubleValue());
			generalDao.saveOrupdate(sessData);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public long getSessionWattsUsage(String sessionId,double currentMeterReading,long stationId,long portId) {
		long val = 0;
		try {
			String sql = "select wattSecondsUsedCount from ocpp_sessionData where wattSecondsUsed = '"+currentMeterReading+"' and sessionId = '"+sessionId+"' and stationId = '"+stationId+"' and connectorId = '"+portId+"' order by id desc";
			String recordBySql = generalDao.getRecordBySql(sql);
			if(recordBySql != null && !recordBySql.equalsIgnoreCase("") && !recordBySql.equalsIgnoreCase("null")) {
				val = Long.valueOf(recordBySql);
				val=val+1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public boolean getsuccessFlags(BigDecimal totalkWh,BigDecimal sessionElapsedMins,long orgId,String stationRefNum, String revenue,String sessionId,String siteCurrency,long stationId) {
		boolean reason=true;
		try {
//			reason = reasonForTermination.equalsIgnoreCase("EmergencyStop") ? true :reasonForTermination.equalsIgnoreCase("Local") ? true
//					            :reasonForTermination.equalsIgnoreCase("HardReset") ? true :reasonForTermination.equalsIgnoreCase("Remote") ? true
//					            : reasonForTermination.equalsIgnoreCase("SoftReset") ? true :reasonForTermination.equalsIgnoreCase("UnlockCommand") ? true :false;
			if(Double.valueOf(String.valueOf(totalkWh)) < 0.25 && Double.valueOf(String.valueOf(sessionElapsedMins)) < 5) {
				reason = false;
				try {
					String duration=Utils.getTimeFormate(Double.parseDouble(String.valueOf(sessionElapsedMins)));
					Map<String, Object> transactionDetails = new HashMap<String, Object>();
					transactionDetails.put("heading", "UnSuccessFul Transaction Alert");
					transactionDetails.put("curDate", String.valueOf(new Date()));
					transactionDetails.put("EnergyUsage", totalkWh+"kWh");
					transactionDetails.put("Duration", duration+"(HH:MM:SS)");
					transactionDetails.put("sessionId", sessionId);
					transactionDetails.put("revenue", siteCurrency+revenue);
					transactionDetails.put("StationId", stationRefNum);
					transactionDetails.put("mailType", "unsuccessAlert");
					transactionDetails.put("orgId", orgId);
					
					String mailSubject = "UnSuccessFul Transaction SessionId : "+sessionId;
					String mails = propertiesServiceImpl.getPropety("internalWorkMails");
					emailServiceImpl.sendEmail(new MailForm(mails, mailSubject, ""), transactionDetails, orgId, stationRefNum,stationId);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
					            		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return reason;
	}

	@Override
	public void chargingSessionsSummaryMail(Map<String, Object> params) {
		String url = transactionURL + "actions/sessionSummaryMail";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);
		utils.apicallingPOST(url, requestEntity);
	}
}
