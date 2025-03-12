package com.axxera.ocpp.webSocket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.repository.ExecuteRepository;

@Service
public class CurrencyConversion {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CurrencyConversion.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;

	public BigDecimal convertCurrency(String ToCurrency, String FromCurrency, BigDecimal needToDebit) {
		try {
			BigDecimal currencyInUsd = new BigDecimal("0.00");
			BigDecimal currency = new BigDecimal("0.00");
			String query = "select c.currency_rate from currency_rate c where currency_code = '" + FromCurrency + "'";
			List querry = generalDao.findSQLQuery(query);
			if (querry.size() > 0) {
				currencyInUsd = new BigDecimal(String.valueOf(querry.get(0)));
				needToDebit=needToDebit.divide(currencyInUsd, 5, RoundingMode.HALF_UP);
			}
			String query1 = "select c.currency_rate from currency_rate c where currency_code = '" + ToCurrency + "'";
			querry = generalDao.findSQLQuery(query1);
			if (querry.size() > 0) {
				currency = new BigDecimal(String.valueOf(querry.get(0)));
				needToDebit=needToDebit.multiply(currency);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return needToDebit;
	}

	public BigDecimal currencyRate(String userCurrency, String siteCurrency) {
		BigDecimal usercurrencyRateInUSD = new BigDecimal("0.00");
		BigDecimal sitecurrencyRateInUSD = new BigDecimal("0.00");
		BigDecimal userCurrencyRate =new BigDecimal("0.00");
		try {
			String query = "select currency_rate from currency_rate where currency_code ='" + userCurrency + "'";
			List querry = generalDao.findSQLQuery(query);
			if (querry.size() > 0) {
				usercurrencyRateInUSD = new BigDecimal(String.valueOf(querry.get(0)));				
			}
			String query1 = "select currency_rate from currency_rate where currency_code ='" + siteCurrency + "'";
			querry = generalDao.findSQLQuery(query1);
			if (querry.size() > 0) {
				sitecurrencyRateInUSD = new BigDecimal(String.valueOf(querry.get(0)));
				sitecurrencyRateInUSD=new BigDecimal("1").divide(sitecurrencyRateInUSD, 5, RoundingMode.HALF_UP);
			}

			userCurrencyRate = sitecurrencyRateInUSD.multiply(usercurrencyRateInUSD);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return userCurrencyRate;
	}

	public Double lowBalanceCheck(String userCurrencyCheck) {
		double minBalance =0.0;
		try {
			if (userCurrencyCheck.equalsIgnoreCase("USD")) {
				minBalance = 1.0;
			}
			if (userCurrencyCheck.equalsIgnoreCase("INR")) {
				minBalance = 80.0;
			}
			if (userCurrencyCheck.equalsIgnoreCase("CAD")) {
				minBalance = 1.30;
			}
			if (userCurrencyCheck.equalsIgnoreCase("CRC")) {
				minBalance = 670.00;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return minBalance;

	}

//	public double MinBalanceCheck(String userCurrencyCheck) {
//		double minBalance = 0.00;
//		try {
//			if (userCurrencyCheck.equalsIgnoreCase("USD")) {
//				minBalance = 5.00;
//			}
//			if (userCurrencyCheck.equalsIgnoreCase("INR")) {
//				minBalance = 400;
//			}
//			if (userCurrencyCheck.equalsIgnoreCase("CAD")) {
//				minBalance = 7;
//			}
//			if (userCurrencyCheck.equalsIgnoreCase("CRC")) {
//				minBalance = 3340;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return minBalance;
//	}
	public double MinBalanceCheck(long userId ,String userCurrencyCheck) {
		double authAmt = 5.0;
		try {
			String str = "select ISNULL(lowBalance,5) as lowBalance from Users u inner join app_config_setting acs on u.orgId = acs.orgId where UserId = "+userId;
			List<Map<String, Object>> mapData = executeRepository.findAll(str);
			if(mapData.size() > 0) {
				authAmt = Double.valueOf(mapData.get(0).get("lowBalance").toString());
				LOGGER.info("Minimum balance DB : "+authAmt);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return authAmt;
	}
	
	public int getReservationDuration(long userId) {
		int authAmt = 60;
		try {
			String str = "select reservationTime from Users u inner join app_config_setting acs on u.orgId = acs.orgId where UserId = "+userId;
			List<Map<String, Object>> mapData = executeRepository.findAll(str);
			if(mapData.size() > 0) {
				authAmt = Integer.valueOf(mapData.get(0).get("reservationTime").toString());
				LOGGER.info("reservation duration : "+authAmt);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return authAmt;
	}
	public double maxRevenueCheck(String userCurrencyCheck,Double revenueALret) {
		double authAmt = 5.0;
		try {
			String str = "select (currency_rate * "+revenueALret+") as maxRevenue from currency_rate where currency_code='"+userCurrencyCheck+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(str);
			if(mapData.size() > 0) {
				authAmt = Double.valueOf(mapData.get(0).get("maxRevenue").toString());
				LOGGER.info("maxRevenueCheck balance DB : "+authAmt);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return authAmt;
	}
	
}	
	
	