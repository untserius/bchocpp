package com.axxera.ocpp.webSocket.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface promoCodeService {

	Map<String, Object> getPromoCodeBilling(String promoCode);

	Map<String, Object> getRewardDataByUserId(long userId);

	void updateAmtRewardBalance(BigDecimal usedRewards, BigDecimal rewardBalance, long userId, String stnRefNum);

	void updatekWhRewardBalance(BigDecimal usedRewards, BigDecimal rewardBalance, long userId, String stnRefNum);

	Map<String, Object> getSessionElaspedDataForPromoCode(Date currentMeterTime, BigDecimal totalkWh,
			BigDecimal rewardkWh, List<Map<String, Object>> previousMeterValueData,BigDecimal currentDurationInMins);

}
