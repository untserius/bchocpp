package com.axxera.ocpp.webScoket.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.promoCodeService;

@Service
public class promoCodeServiceImpl implements promoCodeService{
	private final static Logger LOGGER = LoggerFactory.getLogger(promoCodeServiceImpl.class);
	
	@Autowired
	private ExecuteRepository executeRepositoy;
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private Utils utils;

	
	@Override
	public Map<String,Object> getRewardDataByUserId(long userId) {
		Map<String,Object> map = new HashMap<>();
		try {
			String str = "select amount,kWh from promocode_reward where userId="+userId;
			List<Map<String, Object>> findAll = executeRepositoy.findAll(str);
			if(findAll.size() > 0) {
				map = findAll.get(0);
			}else {
				map.put("amount", "0");
				map.put("kWh", "0");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String,Object> getPromoCodeDataBySessionId(String sessionId) {
		Map<String,Object> map = new HashMap<>();
		try {
			String str = "select amount,amountType from promoCodeHistory where sessionId='"+sessionId+"'";
			List<Map<String, Object>> findAll = executeRepositoy.findAll(str);
			if(findAll.size() > 0) {
				map = findAll.get(0);
			}else {
				map.put("amountType", "");
				map.put("amount", "0");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String,Object> getPromoCodeBilling(String promoCode) {
		Map<String,Object> map = new HashMap<>();
		try {
			String str = "select pc.pname,pc.amountType,pc.amount,'true' as promoCodeFlag from promoCode_in_codes pic inner join promoCode pc on pic.promoId = pc.id where pic.promoCode = '"+promoCode+"'";
			List<Map<String, Object>> findAll = executeRepositoy.findAll(str);
			if(findAll.size() > 0) {
				map = findAll.get(0);
			}else {
				map.put("pname", "");
				map.put("amount", "0");
				map.put("amountType", "");
				map.put("promoCodeFlag", "false");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public void updateAmtRewardBalance(BigDecimal usedRewards,BigDecimal rewardBalance,long userId,String stnRefNum) {
		try {
			BigDecimal remainingAmt = rewardBalance.subtract(usedRewards).setScale(2, BigDecimal.ROUND_UP);
			String update = "update promoCode_reward set amount='"+remainingAmt+"' where userId="+userId;
			executeRepositoy.update(update);
			customLogger.info(stnRefNum, "update promoCode balance amt query : "+update);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updatekWhRewardBalance(BigDecimal usedRewards,BigDecimal rewardBalance,long userId,String stnRefNum) {
		try {
			BigDecimal remainingkWh = rewardBalance.subtract(usedRewards).setScale(2, BigDecimal.ROUND_UP);
			String update = "update promoCode_reward set kWh='"+remainingkWh+"' where userId="+userId;
			executeRepositoy.update(update);
			customLogger.info(stnRefNum, "update promoCode balance kWh query : "+update);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Map<String,Object> getSessionElaspedDataForPromoCode(Date currentMeterTime,BigDecimal totalkWh,BigDecimal rewardkWh,List<Map<String, Object>> previousMeterValueData,BigDecimal currentDurationInMins){
		Map<String,Object> map = new HashMap<>();
		try {
			LOGGER.info("previousMeterValueData : "+previousMeterValueData);
			if(previousMeterValueData.size() > 0 && Double.valueOf(String.valueOf(previousMeterValueData.get(0).get("promoCodeUsedTime"))) == 0) {
				Map<String, Double> timeDifferenceInMilisecondsForStart_MesterStart = utils.getTimeDifferenceInMiliSec(utils.stringToDate(String.valueOf(previousMeterValueData.get(0).get("previousMeterEndTimeStamp"))),currentMeterTime);
				BigDecimal sessionElapsedMinutesInStartMeterTimeStamp =new BigDecimal(String.valueOf(timeDifferenceInMilisecondsForStart_MesterStart.get("Minutes")));
				BigDecimal firstHalf = sessionElapsedMinutesInStartMeterTimeStamp.divide(new BigDecimal("2"), 9, RoundingMode.HALF_UP);
				BigDecimal subtract = currentDurationInMins.subtract(firstHalf);
				map.put("promoCodeUsedTime", subtract);
				map.put("remaningPromoCodeUsedTime", firstHalf);
			}else {
				map.put("promoCodeUsedTime", Double.valueOf(String.valueOf(previousMeterValueData.get(0).get("promoCodeUsedTime"))));
				map.put("remaningPromoCodeUsedTime", currentDurationInMins.subtract(new BigDecimal(String.valueOf(previousMeterValueData.get(0).get("promoCodeUsedTime")))).setScale(9, BigDecimal.ROUND_UP));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("map getSessionElaspedDataForPromoCode : "+map);
		return map;
	}
	
}
