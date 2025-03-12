package com.axxera.ocpp.webScoket.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.CurrencyRate;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.webSocket.service.currencyConversionService;


@Service
public class currencyConvesionServiceImpl implements currencyConversionService{
	
	private final static Logger logger = LoggerFactory.getLogger(currencyConvesionServiceImpl.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;

	@Override
	public double currencyConvert(double amount, String currencyFrom, String currencyTo) {
		double val=0.00;
		try {
			CurrencyRate currencyRates = generalDao.findOneHQLQuery(new CurrencyRate(),"From CurrencyRate WHERE currency_code = '" + currencyFrom + "'");
			DecimalFormat df = new DecimalFormat("#.###");
			val = amount;
			try {
				if(currencyRates != null) {
					val = Double.valueOf(df.format(amount / currencyRates.getCurrency_rate()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public double minBalanceCheck(long userId ,String userCurrencyCheck) {
		double authAmt = 5.0;
		try {
			String str = "select ISNULL(lowBalance,5) as lowBalance from Users u inner join app_config_setting acs on u.orgId = acs.orgId where UserId = "+userId;
			List<Map<String, Object>> mapData = executeRepository.findAll(str);
			if(mapData.size() > 0) {
				authAmt = Double.valueOf(mapData.get(0).get("lowBalance").toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return authAmt;
	}
	@Override
	public BigDecimal currencyRate(String userCurrency, String siteCurrency) {
		BigDecimal usercurrencyRateInUSD = new BigDecimal("0.00");
		BigDecimal sitecurrencyRateInUSD = new BigDecimal("0.00");
		BigDecimal userCurrencyRate =new BigDecimal("1.00");
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

			if(!userCurrency.equalsIgnoreCase(siteCurrency)) {
				userCurrencyRate = sitecurrencyRateInUSD.multiply(usercurrencyRateInUSD);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return userCurrencyRate;
	}
}
