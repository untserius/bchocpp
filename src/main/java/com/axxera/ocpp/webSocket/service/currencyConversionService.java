package com.axxera.ocpp.webSocket.service;

import java.math.BigDecimal;

public interface currencyConversionService {

	double currencyConvert(double amount, String currencyFrom, String currencyTo);

	double minBalanceCheck(long userId, String userCurrencyCheck);

	BigDecimal currencyRate(String userCurrency, String siteCurrency);

}
