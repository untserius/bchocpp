package com.axxera.ocpp.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.message.TransactionForm;
import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;
import com.axxera.ocpp.model.ocpp.OCPPRemoteStartTransaction;
import com.axxera.ocpp.model.ocpp.OCPPStartTransaction;
import com.axxera.ocpp.model.ocpp.OCPPTransactionData;
import com.axxera.ocpp.model.ocpp.Port;
import com.axxera.ocpp.model.ocpp.Session;
import com.axxera.ocpp.model.ocpp.session_pricings;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.rest.message.ResponseMessage;
import com.axxera.ocpp.service.chargingSessionService;
import com.axxera.ocpp.service.userService;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.currencyConversionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class chargingSessionServiceImpl implements chargingSessionService{
	Logger logger = LoggerFactory.getLogger(chargingSessionServiceImpl.class);
	
	@Autowired
	private GeneralDao<?, ?>  generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private Utils utils;
	
	@Value("${ocpi.url}")
	private String ocpiUrl;
	
	private final RestTemplate restTemplate;
	
	private final RestTemplate transactionrestTemplate1;
	public chargingSessionServiceImpl(@Qualifier("transactionrestTemplate1") RestTemplate restTemplate1,@Qualifier("restTemplate") RestTemplate restTemplate) {
	        this.transactionrestTemplate1 = restTemplate1;
	        this.restTemplate = restTemplate;
	}
	
	@Autowired
	private userService userService;
	
	@Autowired
	private currencyConversionService crncyConversionService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public startTxn startTxnUserValidator(startTxn sTxn) {
		try {
			sTxn.setUserObj(userService.getUserDataByIdTag(sTxn.getIdTag()));
			if (!sTxn.getUserObj().isEmpty()) {
				sTxn.setRegisteredTxn(true);
				sTxn = userService.getRfidIdentificationOrPhone(sTxn);
			}else {
				Map<String, Object> guestUserType = userService.getGuestUserType(sTxn.getIdTag(), Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))));
				logger.info("guestUserType : "+guestUserType);
				if(!guestUserType.isEmpty()) {
					guestUserType.put("accountName", "Customer");
					guestUserType.put("user_timezone", timeZone(String.valueOf(sTxn.getSiteObj().get("timeZone"))));
					guestUserType.put("crncy_HexCode", String.valueOf(sTxn.getSiteObj().get("crncy_HexCode")));
					sTxn.setPayAsUGoTxn(true);
					sTxn.setUserName(String.valueOf(guestUserType.get("email")));
					sTxn.setPaymentType(String.valueOf(guestUserType.get("paymentMethod")));
					sTxn.setPaygId(Long.valueOf(String.valueOf(guestUserType.get("preAuthId"))));
					sTxn.setUserObj(guestUserType);
				}else {
					Map<String, Object> driverGroupIdTag = userService.getDriverGroupIdTag(sTxn.getIdTag());
					if(!driverGroupIdTag.isEmpty() && Boolean.valueOf(String.valueOf(driverGroupIdTag.get("dgiFlag")))) {
						sTxn.setDriverGrpIdTagTxn(true);
						sTxn.setDriverGroupName(String.valueOf(driverGroupIdTag.get("groupName")));
						sTxn.setUserName(String.valueOf(driverGroupIdTag.get("groupName")));
						sTxn.setUserObj(driverGroupIdTag);
					}else {
						Map<String, Object> idTagProfs = userService.manualIdCheck(sTxn.getIdTag(),Long.valueOf(String.valueOf(sTxn.getStnObj().get("manfId"))),Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))),Long.valueOf(String.valueOf(sTxn.getStnObj().get("siteId"))));
						if(!idTagProfs.isEmpty() && Boolean.valueOf(String.valueOf(idTagProfs.get("flag")))) {
							sTxn.setUserObj(idTagProfs);
							sTxn.setUserName(String.valueOf(idTagProfs.get("profileName")));
							sTxn.setIdtagProfTxn(true);
						}else {
							boolean ocpiTxn = userService.rfidOCPIAuthentication(sTxn.getIdTag(), String.valueOf(sTxn.getStnObj().get("referNo")));
							if(ocpiTxn) {
								sTxn.setOcpiTxn(true);
							}else {
								sTxn.setUnknownTxn(true);
							}
						}
					}
				}
			}
			sTxn.setCurrencyRate(sTxn.isRegisteredTxn() == true ? Float.parseFloat(String.valueOf(crncyConversionService.currencyRate(String.valueOf(sTxn.getUserObj().get("crncy_Code")),String.valueOf(sTxn.getSiteObj().get("crncy_Code"))))) : 1);
		}catch (Exception e) {	
			e.printStackTrace();
		}
		return sTxn;
	}
	
	@Override
	public startTxn startTxnStationValidator(startTxn sTxn) {
		try {
			if(sTxn.getPaymentType().equalsIgnoreCase("Wallet") && sTxn.isRegisteredTxn()) {
				sTxn.setTxnInitiate(userService.getTxnType(sTxn));
				double minBalance = crncyConversionService.minBalanceCheck(Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))), String.valueOf(sTxn.getUserObj().get("currencyType")));
				if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("freeven")) {
					sTxn.setTxnValid(true);
					sTxn = pricingDetailsByStnId(sTxn);
					sTxn.getTxnData().setBillingCases("Freeven");
				}else if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("paymentMode")) {
					if(Double.valueOf(String.valueOf(sTxn.getUserObj().get("accountBalance"))) >= minBalance || sTxn.isOfflineTxn()) { // Offline changes added
						sTxn.setTxnValid(true);
						Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))),Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))));
						logger.info("driverGroupdId : "+driverGroupdId);
						if(driverGroupdId != null && !driverGroupdId.isEmpty()) {
							sTxn = freeCostDetailsByDriverGroup(sTxn,driverGroupdId);
							sTxn = pricingDetailsByDriverGroupId(sTxn);
						}else {
							sTxn = pricingDetailsByStnId(sTxn);
						}
					}else {
						Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))),Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))));
						logger.info("driverGroupdId : "+driverGroupdId);
						if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0 || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking"))))|| sTxn.isOfflineTxn()) {
							sTxn.setTxnValid(true);
							sTxn = freeCostDetailsByDriverGroup(sTxn,driverGroupdId);
							sTxn = pricingDetailsByDriverGroupId(sTxn);
						}else {
							sTxn.setReason("Low Balance");
						}
					}
				}else if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("drivergroup")) {
					Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))),Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))));
					if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0 || Double.valueOf(String.valueOf(sTxn.getUserObj().get("accountBalance"))) >= minBalance  || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking")))) || sTxn.isOfflineTxn()) {
						sTxn.setTxnValid(true);
						sTxn = freeCostDetailsByDriverGroup(sTxn,driverGroupdId);
						sTxn = pricingDetailsByDriverGroupId(sTxn);
					}

					else {

						sTxn.setReason("Station not in public group");
						sTxn.setTxnValid(false);
					}
				}
				logger.info("sTxn : "+sTxn.isTxnValid());
			}else if(sTxn.getPaymentType().equalsIgnoreCase("Reward") && sTxn.isRegisteredTxn()) {
				sTxn.setTxnInitiate(userService.getTxnType(sTxn));
				if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("freeven")) {
					sTxn.setTxnValid(true);
					sTxn = pricingDetailsByStnId(sTxn);
					sTxn.getTxnData().setBillingCases("Freeven");
				}else if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("paymentMode")) {
					Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))),Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))));
					logger.info("driverGroupdId : "+driverGroupdId);
					if(driverGroupdId != null && !driverGroupdId.isEmpty()) {
						sTxn = getRewardDataByUserId(sTxn);
						sTxn = freeCostDetailsByDriverGroup(sTxn,driverGroupdId);
						sTxn = pricingDetailsByDriverGroupId(sTxn);
						sTxn.setTxnValid(true);
					}else {
						sTxn = getRewardDataByUserId(sTxn);
						sTxn = pricingDetailsByStnId(sTxn);
						sTxn.setTxnValid(true);
					}
				}else if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("drivergroup")) {
					Map<String, Object> driverGroupdId = userService.driverGroupdIdRemoteAuth(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))),Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))));
					if(driverGroupdId != null && !driverGroupdId.isEmpty() && (Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreekWh"))) > 0 || Double.valueOf(String.valueOf(driverGroupdId.get("remainingFreeMin"))) > 0 || Boolean.valueOf(String.valueOf(driverGroupdId.get("noZeroBalChecking"))))) {
						sTxn.setTxnValid(true);
						sTxn = getRewardDataByUserId(sTxn);
						sTxn = freeCostDetailsByDriverGroup(sTxn,driverGroupdId);
						sTxn = pricingDetailsByDriverGroupId(sTxn);
					}else {
						sTxn.setReason("Station not in public group");
						sTxn.setTxnValid(false);
					}
				}
			}else if(sTxn.isPayAsUGoTxn()){
				sTxn.setTxnInitiate("Guest User");
				if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("freeven")) {
					sTxn.setTxnValid(true);
					sTxn = pricingDetailsByStnId(sTxn);
					sTxn.getTxnData().setBillingCases("Freeven");
				}else {
					sTxn = pricingDetailsByStnId(sTxn);
					sTxn.setTxnValid(true);
				}
			}else if(sTxn.isIdtagProfTxn()){
				sTxn.setTxnInitiate("IdTag");
				sTxn.setTxnValid(true);
				sTxn = pricingDetailsByStnId(sTxn);
				sTxn.getTxnData().setBillingCases("Freeven");
			}else if(sTxn.isDriverGrpIdTagTxn()){
				sTxn.setTxnInitiate("Driver Group");
				sTxn.setTxnValid(true);
				sTxn = pricingDetailsByStnId(sTxn);
				sTxn.getTxnData().setBillingCases("Freeven");
			}else if(sTxn.isUnknownTxn()){
				sTxn.setTxnInitiate("Unknown user");
				if(String.valueOf(sTxn.getStnObj().get("stationMode")).equalsIgnoreCase("freeven")) {
					sTxn.setTxnValid(true);
					sTxn = pricingDetailsByStnId(sTxn);
					sTxn.getTxnData().setBillingCases("Freeven");
				}else {
					sTxn.setReason("Unknown user");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	public startTxn freeCostDetailsByDriverGroup(startTxn sTxn, Map<String,Object> driverGrpDetails) {
		try {
			// Get current free pricing limits
			Double currentFreeMins = Double.valueOf(String.valueOf(driverGrpDetails.get("freeMins")));
			Double currentFreeKwh = Double.valueOf(String.valueOf(driverGrpDetails.get("freeKwh")));

			List<Map<String, Object>> freeLs = new ArrayList<>();
			Map<String,Object> freeMap = new HashMap<>();
			freeMap.put("freeMins", currentFreeMins);
			freeMap.put("freeKwh", currentFreeKwh);
			freeMap.put("usedFreeKwhs", 0.00);
			freeMap.put("usedFreeMins", 0.00);
			freeLs.add(freeMap);

			// Get total used for the day
			String query = "select top 1 usedFreekWhs, usedFreeMins " +
					"from freeChargingForDriverGrp where userId='" + Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))) + "' " +
					"and createdDate='" + utils.getDate(sTxn.getStartTime()) + "' " +
					"order by id desc";

			List<Map<String, Object>> dailyUsage = generalDao.getMapData(query);

			if (!dailyUsage.isEmpty()) {
				// Get current day's usage
				double usedFreeKwh = Double.valueOf(String.valueOf(dailyUsage.get(0).get("usedFreekWhs")));
				double usedFreeMins = Double.valueOf(String.valueOf(dailyUsage.get(0).get("usedFreeMins")));

				// Check if there's still room for free usage under current limits
				if ((currentFreeKwh - usedFreeKwh) > 0 || (currentFreeMins - usedFreeMins) > 0) {
					sTxn.getTxnData().setBillingCases(sTxn.getTxnData().getBillingCases().equalsIgnoreCase("TOU+Rewards") ?
							"TOU+Free+Rewards" : "TOU+Free");
				}
			} else {
				// First transaction of the day
				sTxn.getTxnData().setBillingCases(sTxn.getTxnData().getBillingCases().equalsIgnoreCase("TOU+Rewards") ?
						"TOU+Free+Rewards" : "TOU+Free");
			}

			// Set driver group details
			sTxn.setDriverGroupId(Long.valueOf(String.valueOf(driverGrpDetails.get("driverGroupId"))));
			sTxn.setDriverGroupName(String.valueOf(driverGrpDetails.get("groupName")));
			sTxn.getTxnData().setDriverGroupId(Long.valueOf(String.valueOf(driverGrpDetails.get("driverGroupId"))));
			sTxn.getTxnData().setDgNoZeroBal(Boolean.valueOf(String.valueOf(driverGrpDetails.get("noZeroBalChecking"))));
			sTxn.getTxnData().setFree_prices(objectMapper.writeValueAsString(freeLs));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	public startTxn getRewardDataByUserId(startTxn sTxn) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			List<Map<String, Object>> findAll = new ArrayList<Map<String, Object>>();
			if(sTxn.getRewardType() != null && sTxn.getRewardType().equalsIgnoreCase("Amount")) {
				String str = "select amount,0 as kWh from promocode_reward where userId="+Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId")));
				findAll = generalDao.getMapData(str);
			}else if(sTxn.getRewardType() != null && sTxn.getRewardType().equalsIgnoreCase("kWh")) {
				String str = "select 0 as amount,kWh as kWh from promocode_reward where userId="+Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId")));
				findAll = generalDao.getMapData(str);
			}
			if(findAll.size() > 0) {
				map = findAll.get(0);
			}else {
				map.put("amount", 0);
				map.put("kWh", 0);
			}
			map.put("usedTime", 0);
			map.put("usedkWh", 0);
			map.put("usedAmount", 0);
			map.put("lastUpdatedTime", utils.stringToDate(sTxn.getStartTime()));
			list.add(map);
			sTxn.getTxnData().setReward(objectMapper.writeValueAsString(list));
			sTxn.getTxnData().setBillingCases("TOU+Rewards");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	public startTxn pricingDetailsByDriverGroupId(startTxn sTxn) {
		try {
			String chargerType = String.valueOf(sTxn.getStnObj().get("chargerType")).contains("DC") == true ? "DC" : "AC";
			String tariffProfileQuery = "select td.tariffId,t.max_price_id,t.min_price_id,t.name as tariffName,t.start_date_time,t.end_date_time from tariff_in_drivergroup td inner join tariff t on t.id=td.tariffId where td.driverGroupId='"+sTxn.getDriverGroupId()+"' and (td.chargerType='Both' or td.chargerType='"+chargerType+"' ) order by td.id desc";
			logger.info("tariff tariffProfileQuery : "+tariffProfileQuery);
			Map<String, Object> tariffs = executeRepository.findMap(tariffProfileQuery);
			logger.info("tariffId : "+tariffs);
			String idleCapQuery = "select idleBillCap from ocpp_settings where orgId="+sTxn.getSite_orgId();
			Map<String, Object>idleCapData  = executeRepository.findMap(idleCapQuery);
			logger.info("idleCapData : "+idleCapData);
			if(!tariffs.isEmpty()) {
				Long tariffId = Long.valueOf(String.valueOf(tariffs.get("tariffId")));
				String prices = "DECLARE @json nvarchar(max); WITH src (n) AS (	"
						+ "	select distinct t.id as tariffId,t.max_price_id,t.min_price_id,t.name as tariffName,CONVERT(VARCHAR,t.start_date_time, 127) + 'Z' as startTime,CONVERT(VARCHAR,t.end_date_time, 127) + 'Z' as endTime, 	"
						+ "	cost_info=(	select (JSON_QUERY(( select "
						+ "	(JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='Time' for json path, WITHOUT_ARRAY_WRAPPER))) as 'time', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='energy' for json path, WITHOUT_ARRAY_WRAPPER)))as 'energy', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='flat' for json path, WITHOUT_ARRAY_WRAPPER))) as 'flat', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'standard', "
						+ " (JSON_QUERY((select (JSON_QUERY((select LEFT(tp.price , 10) as price,"+idleCapData.get("idleBillCap")+" as idleBillCap,tp.step_size as step,tp.type,LEFT(tr.graceperiod,10) as gracePeriod,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='idle charge' for json path, WITHOUT_ARRAY_WRAPPER))) as 'idleCharge', "
						+ " (JSON_QUERY((select LEFT(tp.vat , 10) as 'percnt',tp.type,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='Rate Rider' for json path, WITHOUT_ARRAY_WRAPPER))) as 'rateRider', "
						+ " tax=(select LEFT(tp.vat , 10) as 'percnt',0 as 'chargingAmount',0 as 'idleAmount',0 as 'amount',tp.type as 'name',tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='tax' for json path) for json path, WITHOUT_ARRAY_WRAPPER))) as 'aditional' for json path) "
						+ " from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' for json path) SELECT @json = src.n FROM src  SELECT @json as 'prices'";
				logger.info("prices query : "+prices);
				Map<String, Object> pricesObj = executeRepository.findMap(prices);
				logger.info("prices data : "+pricesObj);
				OCPPTransactionData txnData = sTxn.getTxnData();
				txnData.setBillingCases(txnData.getBillingCases().equalsIgnoreCase("TOU+Free+Rewards") ? txnData.getBillingCases() : txnData.getBillingCases().equalsIgnoreCase("TOU+Free") ? txnData.getBillingCases() : "TOU");
				txnData.setTariff_prices(String.valueOf(pricesObj.get("prices")));
				txnData.setTariffId(tariffId);
				sTxn.setTxnData(txnData);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	
	public startTxn pricingDetailsByStnId(startTxn sTxn) {
		try {
			String chargerType = String.valueOf(sTxn.getStnObj().get("chargerType")).contains("DC") == true ? "DC" : "AC";
			String tariffProfileQuery = "select st.tariffId,t.max_price_id,t.min_price_id,t.name as tariffName,t.start_date_time,t.end_date_time from station_in_tariff st inner join tariff t on t.id=st.tariffId where st.stationId='"+Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId")))+"' order by st.id desc";
			logger.info("tariff tariffProfileQuery : "+tariffProfileQuery);
			Map<String, Object> tariffs = executeRepository.findMap(tariffProfileQuery);
			logger.info("tariffId : "+tariffs);
			
			String idleCapQuery = "select idleBillCap from ocpp_settings where orgId="+sTxn.getSite_orgId();
			Map<String, Object>idleCapData  = executeRepository.findMap(idleCapQuery);
			logger.info("idleCapData : "+idleCapData);
			if(!tariffs.isEmpty()) {
				Long tariffId = Long.valueOf(String.valueOf(tariffs.get("tariffId")));
				String prices = "DECLARE @json nvarchar(max); WITH src (n) AS (	"
						+ "	select distinct t.id as tariffId,t.max_price_id,t.min_price_id,t.name as tariffName,CONVERT(VARCHAR,t.start_date_time, 127) + 'Z' as startTime,CONVERT(VARCHAR,t.end_date_time, 127) + 'Z' as endTime, 	"
						+ "	cost_info=(	select (JSON_QUERY(( select "
						+ "	(JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='Time' for json path, WITHOUT_ARRAY_WRAPPER))) as 'time', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='energy' for json path, WITHOUT_ARRAY_WRAPPER)))as 'energy', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='flat' for json path, WITHOUT_ARRAY_WRAPPER))) as 'flat', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'standard', "
						+ " (JSON_QUERY((select (JSON_QUERY((select LEFT(tp.price , 10) as price,"+idleCapData.get("idleBillCap")+" as idleBillCap,tp.step_size as step,tp.type,LEFT(tr.graceperiod,10) as gracePeriod,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='idle charge' for json path, WITHOUT_ARRAY_WRAPPER))) as 'idleCharge', "
						+ " (JSON_QUERY((select LEFT(tp.vat , 10) as 'percnt',tp.type,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='Rate Rider' for json path, WITHOUT_ARRAY_WRAPPER))) as 'rateRider', "
						+ " tax=(select LEFT(tp.vat , 10) as 'percnt',0 as 'chargingAmount',0 as 'idleAmount',0 as 'amount',tp.type as 'name',tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='tax' for json path) for json path, WITHOUT_ARRAY_WRAPPER))) as 'aditional' for json path) "
						+ " from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+utils.stringToDate(sTxn.getStartTime())+"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' for json path) SELECT @json = src.n FROM src  SELECT @json as 'prices'";
				logger.info("prices query : "+prices);
				Map<String, Object> pricesObj = executeRepository.findMap(prices);
				logger.info("prices data : "+pricesObj);
				sTxn.getTxnData().setBillingCases(sTxn.getTxnData().getBillingCases().equalsIgnoreCase("TOU+Rewards") ? sTxn.getTxnData().getBillingCases() : "TOU");
				sTxn.getTxnData().setTariff_prices(String.valueOf(pricesObj.get("prices")));
				sTxn.getTxnData().setTariffId(tariffId);
			}else {
				sTxn.getTxnData().setBillingCases("Freeven");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	
	@Override
	public startTxn insertIntoSession(startTxn sTxn) {
		Session sess = new Session();
		try {
			sess.setCost(0);
			sess.setFinalCostInSlcCurrency(0);
			sess.setCurrencyRate(sTxn.getCurrencyRate());
			sess.setCurrencyType(String.valueOf(sTxn.getSiteObj().get("crncy_Code")));
			
			Port port = new Port();
			port.setId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
			sess.setPort(port);
			
			sess.setCustomerId(sTxn.getRfidOrPhone());
			sess.setMasterList(false);
			sess.setCreationDate(utils.getUTCDate());
			sess.setStartTimeStamp(sTxn.getStartTime());
			sess.setSessionId(sTxn.getChargeSessUniqId());
			sess.setEndTimeStamp(sTxn.getStartTime());
			sess.setChargerType(String.valueOf(sTxn.getStnObj().get("chargerType")));
			sess.setSessionElapsedInMin(0);
			sess.setKilowattHoursUsed(0);
			sess.setReasonForTer(sTxn.isOfflineTxn() ? "OfflineTransaction" : "Insession");
			sess.setEmailId(sTxn.isRegisteredTxn() ? String.valueOf(sTxn.getUserObj().get("email")) : sTxn.isUnknownTxn() == true ? "Unknown Free" : sTxn.getUserName());
			sess.setDriverGroupName(sTxn.getDriverGroupName());
			sess.setIdTagProfileName(sTxn.isIdtagProfTxn() ? sTxn.getUserName() : null);
			sess.setStartTxnProgress(true);
			sess.setStationMode(String.valueOf(sTxn.getStnObj().get("stationMode")));
			sess.setUserId(sTxn.isRegisteredTxn() == true ? Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))) : 0);
			sess.setSelfCharging(sTxn.isSelfCharging());
			sess.setSettlement("Inprogress");
			sess.setTransactionType("Standard");
			sess.setTransactionStatus("pending");
			sess.setSessionStatus("Accepted");
			sess.setTxnType(sTxn.getDriverGroupId()>0?"driverGrpUser" : sTxn.isRegisteredTxn() ? "RegisterdUser" : sTxn.isPayAsUGoTxn() ? "PAYG" : sTxn.isIdtagProfTxn() ? "idTagProfile" :sTxn.isDriverGrpIdTagTxn() ? "driverGroupIdTag" : "Unknown");
			sess.setUserCurrencyType(sTxn.isRegisteredTxn() ? String.valueOf(sTxn.getUserObj().get("crncy_Code")) : sTxn.isPayAsUGoTxn() ? String.valueOf(sTxn.getUserObj().get("crncy_Code")) : String.valueOf(sTxn.getSiteObj().get("crncy_Code")));
			sess.setPaymentMode(sTxn.getPaymentType());
			sess.setSocStartVal(0);
			sess.setSocEndVal(0);
			sess.setRewardType(sTxn.getRewardType());
			sess.setPowerActiveImport_value(0);
			sess.setSuccessFlag(false);
			sess.setSitename(String.valueOf(sTxn.getSiteObj().get("siteName")));
			sess.setSiteId(Long.parseLong(String.valueOf(sTxn.getSiteObj().get("siteId"))));
			sess.setSiteType(String.valueOf(sTxn.getSiteObj().get("bcHydroSiteType")));
			sess.setTxnInitiate(sTxn.getTxnInitiate()==null ?"Mobile Application" : sTxn.getTxnInitiate());
			sess = generalDao.save(sess);
		}catch (Exception e) {
			e.printStackTrace();
		}
		sTxn.setSession(sess);
		return sTxn;
	}
	@Override
	public OCPPTransactionData insertIntoTransactionData(startTxn sTxn) {
		OCPPTransactionData txnData = sTxn.getTxnData();
		try {
			Map<String, Object> orgData = userService.getOrgData(1, sTxn.getStnRefNum());
			if(txnData == null) {
				txnData = new OCPPTransactionData();
				txnData.setBillingCases("Freeven");
			}
			Map<String,Object> map=maximumRevenuekWh(Long.valueOf(String.valueOf(sTxn.getSite_orgId())));
			double maximumRevenue=Double.parseDouble(String.valueOf(map.get("maximumRevenue")));
			double maxkWh=Double.parseDouble(String.valueOf(map.get("maxkWh")));
			txnData.setPortId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
			txnData.setSessionId(sTxn.getChargeSessUniqId());
			txnData.setDriverGroupId(sTxn.getDriverGroupId());
			txnData.setRstp(false);
			txnData.setStn_obj(String.valueOf(new JSONObject(sTxn.getStnObj())));
			txnData.setUser_obj(String.valueOf(new JSONObject(sTxn.getUserObj())));
			txnData.setStationMode(String.valueOf(sTxn.getStnObj().get("stationMode")));
			txnData.setTransactionId(sTxn.getTransactionId());
			txnData.setOrgId(Long.valueOf(String.valueOf(sTxn.getSite_orgId())));
			txnData.setUserType(sTxn.isRegisteredTxn() ? "RegisteredUser" : sTxn.isPayAsUGoTxn() ? "PAYG" : sTxn.isUnknownTxn() ? "Unknown" : sTxn.isIdtagProfTxn() ? "IdTagProfs" :sTxn.isDriverGrpIdTagTxn() ? "DriverGrpIdTagProfs" :null);
			txnData.setPaymentMode(sTxn.getPaymentType());
			txnData.setMinkWhEnergy(Double.parseDouble(String.valueOf(orgData.get("minkWh"))));
			txnData.setSite_obj(String.valueOf(new JSONObject(sTxn.getSiteObj())));
			txnData.setMeterStart(sTxn.getMeterStartReading());
			txnData.setIdleStartTime(sTxn.getStartTime());
			txnData.setStop(false);
			txnData.setIdleStatus("start");
			txnData.setMaximumRevenue(maximumRevenue);
			txnData.setMaxkWh(maxkWh);
			txnData.setkWhStatus("");
			txnData.setOfflineTransaction(sTxn.isOfflineTxn());
			txnData.setReasonForTer("Insession");
			logger.info("186 >> txnData : "+txnData);
			txnData = generalDao.save(txnData);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return txnData;
	}
	public Map<String,Object> maximumRevenuekWh(Long orgId) {
		Map<String,Object> map=new HashMap();
		try {
			String query="select maximumRevenue,maxkWh from ocpp_settings where orgId='"+orgId+"'";
			List<Map<String,Object>> list=executeRepository.findAll(query);
			if(list.size()>0) {
				map.put("maximumRevenue", Double.parseDouble(String.valueOf(list.get(0).get("maximumRevenue"))));
				map.put("maxkWh", Double.parseDouble(String.valueOf(list.get(0).get("maxkWh"))));
			}else {
				map.put("maximumRevenue", 150);
				map.put("maxkWh", 200);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public void insertIntoSessionPricings(startTxn sTxn) {
		try {
			session_pricings sp = new session_pricings();
			sp.setCost_info(sTxn.getTxnData() == null ? null : sTxn.getTxnData().getTariff_prices()==null? null : !sTxn.getTxnData().getTariff_prices().equalsIgnoreCase("null") ? sTxn.getTxnData().getTariff_prices() : null);
			sp.setSessionId(sTxn.getChargeSessUniqId());
			sp.setTransactionId(sTxn.getTransactionId());
			sp.setMetervaluesCount(0);
			logger.info("session pricings : "+sp);
			generalDao.save(sp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void deleteActiveSessionData(long portId) {
		try {
			String deleteActiveTrans2 = "delete from ocpp_activeTransaction where connectorId in(" + portId + ")";
			executeRepository.update(deleteActiveTrans2);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateChargingSessionData(Long portId) {
		try {
			String deleteActiveTrans = "Update session set reasonForTer ='EVDisconnected',transactionStatus='completed' where reasonForTer = 'InSession' and port_id = " + portId + " ";
			executeRepository.update(deleteActiveTrans);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public startTxn getRSTTxnData(startTxn sTxn) {
		try {
			OCPPRemoteStartTransaction rst = generalDao.findOne("From OCPPRemoteStartTransaction where idTag ='"
					+ sTxn.getIdTag() + "' and stationId = '"+sTxn.getStnObj().get("stnId")+"' and createdDate > DATEADD(MInute,-2,getutcdate()) order by id desc", new OCPPRemoteStartTransaction());
			if(rst != null) {
				sTxn.setSelfCharging(rst.isSelfCharging());
				sTxn.setPaymentType(rst.getPaymentType());
				sTxn.setRewardType(rst.getRewardType());
				sTxn.setRst_clientId(rst.getClientId());
				sTxn.setSite_orgId(rst.getOrgId());
				sTxn.setPaymentType(rst.getPaymentType());
				sTxn.setPowerSharing(rst.isPowerSharing());
			}else {
				sTxn.setSite_orgId(1);
				sTxn.setPaymentType("Wallet");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	@Override
	public OCPPActiveTransaction getActiveTxnData(long stnId,long portId,String idTag) {
		OCPPActiveTransaction activeTransactionObj = null;
		try {
			activeTransactionObj = generalDao.findOne("From OCPPActiveTransaction where connectorId=" + portId + " and " + "stationId ="
					+ stnId + " and Status in ('Accepted','Preparing','Charging') order by id desc", new OCPPActiveTransaction());
			logger.info("activeTransactionObj>>530:"+activeTransactionObj);
			if(activeTransactionObj==null) {
				activeTransactionObj = generalDao.findOne("FROM OCPPActiveTransaction where rfId='"+idTag+"' and stationId ="+ stnId + " and Status in ('Accepted','Preparing','Charging') and (messageType='Authorize') and timeStamp>= DATEADD(minute,-2,GETUTCDATE()) order by id desc", new OCPPActiveTransaction());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return activeTransactionObj;
	}
	@Override
	public startTxn insertActiveTxnStartTxn(startTxn sTxn) {
		try {
			if (!sTxn.isOfflineTxn()){ // Offline transaction Change
				if (sTxn.getActiveTxnData() == null) {
					OCPPActiveTransaction ocppActiveTransaction = new OCPPActiveTransaction();
					ocppActiveTransaction.setConnectorId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
					ocppActiveTransaction.setMessageType("StartTransaction");
					ocppActiveTransaction.setRfId(sTxn.getIdTag());
					ocppActiveTransaction.setSessionId(sTxn.getChargeSessUniqId());
					ocppActiveTransaction.setStationId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))));
					ocppActiveTransaction.setStatus("charging");
					ocppActiveTransaction.setTransactionId(sTxn.getTransactionId());
					ocppActiveTransaction.setUserId(sTxn.isRegisteredTxn() == true ? Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))) : 0);
					ocppActiveTransaction.setRequestedID(sTxn.getSt_unqReqId());
					ocppActiveTransaction.setOrgId(sTxn.getSite_orgId());
					ocppActiveTransaction.setTimeStamp(utils.getUTCDate());
					generalDao.save(ocppActiveTransaction);
				} else {
					//Update the Session reason to EvDisconnect If any Active Session Available without any Stop Transaction and Get again StartTransaction
					OCPPActiveTransaction activeTransactionObj = sTxn.getActiveTxnData();
					activeTransactionObj.setRequestedID(sTxn.getSt_unqReqId());
					activeTransactionObj.setRfId(sTxn.getIdTag());
					activeTransactionObj.setStatus("charging");
					activeTransactionObj.setUserId(sTxn.isRegisteredTxn() == true ? Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))) : 0);
					activeTransactionObj.setTransactionId(sTxn.getTransactionId());
					activeTransactionObj.setSessionId(sTxn.getChargeSessUniqId());
					activeTransactionObj.setTimeStamp(utils.getUTCDate());
					activeTransactionObj.setConnectorId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
					generalDao.update(activeTransactionObj);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	@Override
	public boolean insertStartTransaction(startTxn sTxn){
		try {
			OCPPStartTransaction startTrans = new OCPPStartTransaction();
			startTrans.setConnectorId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
			startTrans.setEndTime(sTxn.getStartTime());
			startTrans.setIdTag(sTxn.getIdTag());
			startTrans.setMeterStart(sTxn.getMeterStartReading());
			startTrans.setReservationId(sTxn.getReserveId());
			startTrans.setSessionId(sTxn.getChargeSessUniqId());
			startTrans.setStationId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))));
			startTrans.setTimeStamp(sTxn.getStartTime());
			startTrans.setTransactionId(sTxn.getTransactionId());
			startTrans.setUserId(sTxn.isRegisteredTxn() == true ? Long.valueOf(String.valueOf(sTxn.getUserObj().get("UserId"))) : 0);
			startTrans.setOfflineFlag(sTxn.isOfflineTxn());
			startTrans.setEmailId(sTxn.isRegisteredTxn() == true ? String.valueOf(sTxn.getUserObj().get("email")) : "NA");
			startTrans.setCustomerId(sTxn.getIdTag());
			startTrans.setTransactionStatus(sTxn.isTxnValid() == true ? "Accepted" : "Rejected");
			startTrans.setRequestedID(sTxn.getSt_unqReqId());
			startTrans.setReservationFee(0);
			startTrans.setPaymentType(sTxn.getPaymentType());
			startTrans.setRewardType(sTxn.getRewardType());
			startTrans.setSelfCharging(sTxn.isSelfCharging());
			generalDao.save(startTrans);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public startTxn siteData(startTxn sTxn) {
		try {
			String str = "select isnull(si.currencySymbol,'&#36;') as crncy_HexCode,si.streetName,si.city,si.state,si.postal_code,ISNULL(si.currencyType,'USD') as crncy_Code, isNull(si.siteId,'0') as siteId,si.siteName as siteName, isnull(si.uuid,'0') as uuid,si.ocpiflag,si.coordinateId,si.country,isNUll(si.timeZone,'PDT') as timeZone,bcHydroSiteType  from station st inner join site si on st.siteId = si.siteId where st.id = '"+sTxn.getStnObj().get("stnId")+"'";
            List<Map<String, Object>> mapData = executeRepository.findAll(str);
            if(mapData.size() > 0) {
            	sTxn.setSiteObj(mapData.get(0));
            }else {
            	Map<String,Object> map = new HashMap<>();
            	map.put("ocpiflag", "0");
                map.put("crncy_HexCode", "&#36;");
                map.put("crncy_Code", "USD");
                map.put("siteId", "0");
                map.put("siteName", "-");
                map.put("timeZone", "PDT");
                map.put("uuid", "0");
                map.put("streetName", "");
                map.put("city", "");
                map.put("country", "");
                map.put("bcHydroSiteType", "");
                sTxn.setSiteObj(map);
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}

	@Override
	public void updatePAYGSessionId(startTxn sTxn) {
		try {
			if(sTxn.isPayAsUGoTxn()) {
				Map<String, Object> userObj = sTxn.getUserObj();
				logger.info("450 >> "+String.valueOf(userObj.get("portId")));
				if(String.valueOf(userObj.get("portId")).equalsIgnoreCase("0")) {
					executeRepository.update("update userPayment set sessionId='"+sTxn.getChargeSessUniqId()+"',portId="+Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId")))+" where id="+sTxn.getPaygId());
				}else {
					executeRepository.update("update userPayment set sessionId='"+sTxn.getChargeSessUniqId()+"' where id="+sTxn.getPaygId());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String ocpiStartCall(String requestMessage,String stnRefNum) {
		String response = "123"; 
		try {
					try {
						String url= ocpiUrl + "ocpi/ocpp/start";
						TransactionForm transactionForm=new TransactionForm();
						transactionForm.setClientId(stnRefNum);
						transactionForm.setMessage(requestMessage);
						HttpHeaders headers = new HttpHeaders();
						headers.set("Content-Type", "application/json");
						HttpEntity<TransactionForm> requestEntity = new HttpEntity<TransactionForm>(transactionForm, headers);
						ResponseEntity<ResponseMessage> postForEntity = transactionrestTemplate1.postForEntity(url, requestEntity, ResponseMessage.class);
						logger.info("start postForEntity : "+postForEntity);
						if(postForEntity.getStatusCodeValue() == 200) {
							response = postForEntity.getBody().getMessage();
						}
						logger.info("start response : "+response);
					}catch(Exception e) {
						e.printStackTrace();
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response ;
	}
	@Override
	public long timeZone(String timeZone) {
		long zone=8;
		try {
			String query="select isNull(zone_id,'8') as zone_id from zone where zone_name='"+timeZone+"'";
			List<Map<String,Object>> list=executeRepository.findAll(query);
			if(list.size()>0) {
				zone=Long.parseLong(String.valueOf(list.get(0).get("zone_id")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return zone;
	}
	@Override
	public void deleteTxnData(long portId) {
		try {
			String query="update ocpp_TransactionData set idleStatus='Available' where portId='"+portId+"'";
			executeRepository.update(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public startTxn siteTimeCheck(startTxn sTxn) {
		try {
			boolean siteTimeFlag=stationService.siteTimingsCheck(Long.parseLong(String.valueOf(sTxn.getStnObj().get("siteId"))), utils.getUTCDate());
			if(!siteTimeFlag) {
				OCPPActiveTransaction activeTransactionObj = generalDao.findOne("FROM OCPPActiveTransaction where rfId='"+sTxn.getIdTag()+"' and stationId ="+ Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))) + " and Status in ('Accepted','Preparing','Charging') and (messageType='Authorize' or (messageType='RemoteStartTransaction' and connectorId='"+Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId")))+"' )) and timeStamp>= DATEADD(minute,-2,GETUTCDATE()) order by id desc", new OCPPActiveTransaction());
				if(activeTransactionObj!=null) {
					siteTimeFlag=true;
					sTxn.setActiveTxnData(activeTransactionObj);
				}
			}
			logger.info("siteTimeFlag>>713 : "+siteTimeFlag);
//			sTxn.setSiteTimeFlag(siteTimeFlag);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}

}
