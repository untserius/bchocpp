package com.axxera.ocpp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.service.userService;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class userServiceImpl implements userService{
	private final static Logger logger = LoggerFactory.getLogger(userServiceImpl.class);
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Value("${ocpi.url}")
    private String ocpiUrl;
	
	@Autowired
	private LoggerUtil customLog;
	
	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private Utils utils;
	
	@Override
	public Map<String,Object> getOrgData(long orgId,String stationRefNum) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			String query = "select ISNULL(address,'5251 California Ave, STE 150, Irvine, CA - 92617.') as address,email,fromEmail,host,legacykey,logoName,"
					+ " orgId,ISNULL(orgName,'BC Hydro') as orgName,supportEmail,password,isnull(phoneNumber,'949-945-2000') as phoneNumber,port,portalLink, "
					+ " isnull(protocol,'smtp') as protocol,isnull(serverKey,'') as serverKey,isnull(minkWh,0.5) as minkWh from configurationSettings where orgId = '1'";
			list = executeRepository.findAll(query);
			if(list.size() > 0) {
				map = list.get(0);
			}else {
				map.put("orgName", "BC Hydro");
				map.put("orgId", 1);
				map.put("legacykey", "");
				map.put("serverKey", "");
				map.put("email", "no-reply@evgateway.net");
				map.put("phoneNumber", "no-reply@evgateway.net");
				map.put("host", "smtp.office365.com");
				map.put("port", 587);
				map.put("password", "EvGateway@1234!");
				map.put("address", "5251 California Ave, STE 150, Irvine, CA - 92617.");
				map.put("minkWh",0.5);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public Map<String,Object> getDriverGroupIdTag(String idTag) {
		Map<String,Object> map = new HashMap<>();
		try {
			String query = "select 0 as UserId,dgi.rfid,dpg.groupName,CAST(1 as bit) as dgiFlag from [driver_groupRFIDS] dgi inner join driver_profile_groups dpg on dgi.driver_group_id = dpg.id where dgi.rfid = '"+idTag+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("dgiFlag", false);
				map.put("groupName", "");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public Map<String,Object> manualIdCheck(String idTag,long manfId,long stnId,long siteId) {
		logger.info("inside manualIdCheck calling");
		Map<String,Object> map = new HashMap<>();
		map.put("UserId", 0);
		map.put("flag", false);
		map.put("profileType", "");
		map.put("profileName", "");
		map.put("billing", false);
		try {
			int tag_profileId=0;
			String profileType="";
			String query1="select isNull(ip.billing,'0') as billing, ip.profileType,ip.profileName,ip.id as idTagProfileId from idtags_in_profile iip inner join idtag_profiles ip on iip.tag_profileId = ip.id where idTag = '"+idTag+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(query1);
			if(mapData.size() > 0) {
				map.put("billing", Boolean.parseBoolean(String.valueOf(mapData.get(0).get("billing"))));
				tag_profileId=Integer.parseInt(mapData.get(0).get("idTagProfileId").toString());
				profileType = mapData.get(0).get("profileType").toString();
				if(profileType.equalsIgnoreCase("Manufacturer")) {
					String manfactureQuery = "select m.manfName,u.email as manufacturerEmail from idtagprofile_in_manufacture iipm inner join Users u on iipm.manfactureId = u.UserId "
							+ " inner join Usersinroles uir on uir.user_id = u.UserId inner join role r on r.id = uir.role_id inner join user_in_manufacturer uim on uim.UserId = "
							+ " iipm.manfactureId inner join manufacturer m on m.id = uim.manufacturerId where r.rolename = 'Manufacturer' and iipm.idtagProfileId = '"+tag_profileId+"' and m.id = '"+manfId+"'";
					List<Map<String, Object>> mapData1 = executeRepository.findAll(manfactureQuery);
					if(mapData1.size() > 0) {
						map.put("flag", true);
						map.put("profileType", profileType);
						map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
					}
				}else if(profileType.equalsIgnoreCase("General")) {
					String str = "select idTagProfileId from idtagprofile_in_all iia where iia.profileType = 'station' and iia.profileTypeId = '"+stnId+"' and iia.idTagProfileId = '"+tag_profileId+"'";
					List<Map<String, Object>> mapData2 = executeRepository.findAll(str);
					if(mapData2.size() > 0) {
						map.put("flag", true);
						map.put("profileType", profileType);
						map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
					}else {
						str = "select idTagProfileId from idtagprofile_in_all iia where iia.profileType = 'site' and iia.profileTypeId = '"+siteId+"' and iia.idTagProfileId ='"+tag_profileId+"'";
						mapData2 = executeRepository.findAll(str);
						if(mapData2.size() > 0) {
							map.put("flag", true);
							map.put("profileType", profileType);
							map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
						}else {
							str = "select idTagProfileId from Stations_DriverGroups sdg inner join idtagprofile_in_all iia on sdg.driverProfileGroupUnqId = iia.profileTypeId "
									+ " where iia.profileType = 'drivergroup' and stationUnqId = '"+stnId+"' and iia.idTagProfileId = '"+tag_profileId+"'";
							mapData2 = executeRepository.findAll(str);
							if(mapData2.size() > 0) {
								map.put("flag", true);
								map.put("profileType", profileType);
								map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
							}
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("manualIdCheck details : "+map);
		return map;
	}
	@Override
	public Map<String, Object> driverGroupdIdRemoteAuth(long stationId,long userId) {
		Map<String, Object> driverGroupdId = new HashMap<>();
		try {
			String hqlQuery="select dpg.groupName,dpg.id as driverGroupId,((dpg.durationHr * 60)+dpg.durationMins) as freeMins,dpg.freeKwh,((dpg.durationHr * 60)+dpg.durationMins)"
					+ "  as remainingFreeMin,dpg.freeKwh as remainingFreekWh,noZeroBalChecking from driver_profile_groups dpg inner join Stations_DriverGroups "
					+ " sd on dpg.id = sd.driverProfileGroupUnqId inner join driverProfileGroup_userId dp on dp.id=sd.driverProfileGroupUnqId "
					+ " where sd.stationUnqId="+stationId+" and dp.USER_ID="+userId+"";
			logger.info("station user drivergroup query : "+hqlQuery);
			driverGroupdId = executeRepository.findMap(hqlQuery);
			logger.info("station user drivergroup data : "+driverGroupdId);
			if(!driverGroupdId.isEmpty()) {
				String str = "select top 1 id,createdDate,userId,usedFreeMins,usedFreekWhs From freeChargingForDriverGrp Where userId='" + userId+"' and createdDate='"+utils.getDate(new Date())+"' order by id desc";
				logger.info("user remaining free min/kWh query : "+str);
				List<Map<String, Object>> fcdg = executeRepository.findAll(str);
				logger.info("user remaining free min/kWh data : "+fcdg);
				if(fcdg.size() > 0) {
					double freeMins = Double.valueOf(String.valueOf(driverGroupdId.get("freeMins")));
					double freeKwh = Double.valueOf(String.valueOf(driverGroupdId.get("freeKwh")));
					double usedFreeMins = Double.valueOf(String.valueOf(fcdg.get(0).get("usedFreeMins")));
					double usedFreekWhs = Double.valueOf(String.valueOf(fcdg.get(0).get("usedFreekWhs")));
					double remainFreeMins = 0.00;
					double remainFreekWhs = 0.00;
					if(freeMins > usedFreeMins) {
						remainFreeMins = freeMins - usedFreeMins;
					}else {
						remainFreeMins=0.00;
					}
					if(freeKwh > usedFreekWhs) {
						remainFreekWhs = freeKwh - usedFreekWhs;
					}else {
						remainFreekWhs=0.00;
					}
					driverGroupdId.put("remainingFreekWh", remainFreekWhs);
					driverGroupdId.put("remainingFreeMin", remainFreeMins);
					logger.info("user remaining free min/kWh final data : "+driverGroupdId);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return driverGroupdId;
	}
	@Override
	public Map<String, Object> driverGroupdIdStart(long stationId,long userId) {
		Map<String, Object> driverGroupdId = new HashMap<>();
		try {
			String hqlQuery="select dpg.groupName,dpg.id as driverGroupId,((dpg.durationHr * 60)+dpg.durationMins) as freeMins,dpg.freeKwh  from driver_profile_groups dpg inner join Stations_DriverGroups sd on dpg.id = sd.driverProfileGroupUnqId inner join driverProfileGroup_userId dp on dp.id=sd.driverProfileGroupUnqId where sd.stationUnqId="+stationId+" and dp.USER_ID="+userId+"";
			logger.info("station user drivergroup query : "+hqlQuery);
			driverGroupdId = executeRepository.findMap(hqlQuery);
			if(!driverGroupdId.isEmpty()) {
				String str = "select top 1 id,createdDate,userId,usedFreeMins,usedFreekWhs From freeChargingForDriverGrp Where userId='" + userId+"' and createdDate='"+utils.getDate(new Date())+"' order by id desc";
				List<Map<String, Object>> fcdg = executeRepository.findAll(str);
				if(fcdg.size() > 0) {
					double freeMins = Double.valueOf(String.valueOf(driverGroupdId.get("freeMins")));
					double freeKwh = Double.valueOf(String.valueOf(driverGroupdId.get("freeKwh")));
					double usedFreeMins = Double.valueOf(String.valueOf(fcdg.get(0).get("usedFreeMins")));
					double usedFreekWhs = Double.valueOf(String.valueOf(fcdg.get(0).get("usedFreekWhs")));
					if(freeMins > usedFreeMins) {
						
					}else {
						driverGroupdId=null;
					}
					if(freeKwh > usedFreekWhs) {
						
					}else {
						driverGroupdId=null;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return driverGroupdId;
	}

	@Override
	public Map<String,Object> getUserDataByIdTag(String idTag){
		Map<String,Object> map = new HashMap<>();
		try {
			logger.info("idTag : "+idTag);
			long accId = getAccIdOnBaseIdtag(idTag);
			logger.info("accId : "+accId);
			if(accId>0) {
				String str = "select a.id as accid,isnull(a.digitalId,'-') as digitalId,a.accountBalance,a.accountName,a.activeAccount,a.creationDate,"
						+ " isnull(a.currencyType,'USD') as crncy_Code,isnull(a.currencySymbol,'&#36;') as crncy_HexCode,u.UserId,u.email,isnull(p.zone_id,1) as user_timezone,"
						+ " u.uid as uuid,ad.phone as phoneNumber,ad.countryCode from accounts a inner join profile p on a.user_id = p.user_id inner join "
						+ " Users u inner join address ad on u.userId = ad.user_id on a.user_id=u.UserId where a.id="+accId;
				map = executeRepository.findMap(str);
			}
			logger.info("map : "+map);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public startTxn getRfidIdentificationOrPhone(startTxn sTxn) {
		String rfidOrPhone = "0";
		String idTag=sTxn.getIdTag();
		try {
			String queryForRfidOrPhone="select case when phone is null then rfid when phone is not null then phone end as phone from creadential where account_id="+Long.valueOf(String.valueOf(sTxn.getUserObj().get("accid")))+" and (rfid='"+sTxn.getIdTag()+"' or rfidHex='"+sTxn.getIdTag()+"' or phone = '"+sTxn.getIdTag()+"')";
			rfidOrPhone = generalDao.getRecordBySql(queryForRfidOrPhone);
			if(rfidOrPhone==null || rfidOrPhone.equalsIgnoreCase("null") || rfidOrPhone.equalsIgnoreCase("")) {
				char prefix = idTag.charAt(0);
				if(prefix=='0') {
					idTag = idTag.substring(1);
					 queryForRfidOrPhone="select case when phone is null then rfid when phone is not null then phone end as phone from creadential where account_id="+Long.valueOf(String.valueOf(sTxn.getUserObj().get("accid")))+" and (rfid='"+idTag+"' or rfidHex='"+idTag+"' or phone = '"+idTag+"')";
					rfidOrPhone = generalDao.getRecordBySql(queryForRfidOrPhone);
				}
			}
			if(rfidOrPhone==null || rfidOrPhone.equalsIgnoreCase("null") || rfidOrPhone.equalsIgnoreCase("")) {
				rfidOrPhone=sTxn.getIdTag();
			}
			sTxn.setRfidOrPhone(rfidOrPhone);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sTxn;
	}
	@Override
	public String getTxnType(startTxn sTxn) {
		String type="Mobile Application";
		try {
			String queryForRfidOrPhone="select phone from creadential where account_id="+Long.valueOf(String.valueOf(sTxn.getUserObj().get("accid")))+" and (rfid='"+sTxn.getRfidOrPhone()+"' or rfidHex='"+sTxn.getRfidOrPhone()+"' or phone = '"+sTxn.getRfidOrPhone()+"')";
			String rfidOrPhone = generalDao.getRecordBySql(queryForRfidOrPhone);
			if(rfidOrPhone==null || rfidOrPhone.equalsIgnoreCase("null") || rfidOrPhone.equalsIgnoreCase("")) {
				type="RFID";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}
	
	@Override
	public Map<String, Object> getGuestUserType(String phone,long stationId)  {
		Map<String, Object> map = new HashMap<>();
		try {
			String query1 = "select 0 as UserId,id as preAuthId,isnull(authorizeAmount,0) as authorizeAmount,flag as flagValue,authorizeDate as authorizeTimeStamp,email,paymentMode as paymentMethod,'PayV2' as paymentVersion,deviceType,deviceToken,portId,currencyType as crncy_Code,phone as phoneNumber From userPayment Where phone='" + phone + "' and stationId="+stationId+" and flag='1' and userType = 'GuestUser' order By id desc";
			logger.info("guest user query at start : "+query1);
			map = executeRepository.findMap(query1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public boolean rfidOCPIAuthentication(String rfid, String stnRefNum) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("rfid", rfid);
			params.put("referno", stnRefNum);
			customLog.info(stnRefNum, "rfidOCPIAuthentication api request body : " + params);
			String url = ocpiUrl + "ocpi/ocpp/authorize";
			customLog.info(stnRefNum, "rfidOCPIAuthentication api calling : " + url);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);
			customLog.info(stnRefNum, "rfidOCPIAuthentication api request body : " + requestEntity);
			ResponseEntity<String> apicallingPOSTResponse = utils.apicallingPOSTResponse(url, requestEntity);
			customLog.info(stnRefNum, "rfidOCPIAuthentication api apicallingPOSTResponse : " + apicallingPOSTResponse);
			HttpStatus statusCode = apicallingPOSTResponse.getStatusCode();
			int value = statusCode.value();
			customLog.info(stnRefNum, "rfidOCPIAuthentication api value : " + value);
			if(value == 200) {
				String body = apicallingPOSTResponse.getBody();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> jsonMap = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});
				logger.info("ocpi response for authorize api : "+jsonMap);
				if(String.valueOf(jsonMap.get("status")).equalsIgnoreCase("ALLOWED")) {
					return true;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
	}
	@Override
	public long getAccIdOnBaseIdtag(String idTag) {
		long accId = 0;
		try {
			List<Map<String, Object>> findAll = executeRepository.findAll("SELECT account_id FROM creadential c where c.phone='"+idTag+"' or " + " c.rfId='"+idTag+"' or c.rfIdHex='"+idTag+"'");
			if(findAll != null && findAll.size() > 0) {
				accId = Long.valueOf(String.valueOf(findAll.get(0).get("account_id")));
			}else {
				char prefix = idTag.charAt(0);
				if(prefix=='0') {
					idTag = idTag.substring(1);
					findAll = executeRepository.findAll("SELECT account_id FROM creadential c where c.phone='"+idTag+"' or " + " c.rfId='"+idTag+"' or c.rfIdHex='"+idTag+"'");
					if(findAll != null && findAll.size() > 0) {
						accId = Long.valueOf(String.valueOf(findAll.get(0).get("account_id")));
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return accId;
	}
}
