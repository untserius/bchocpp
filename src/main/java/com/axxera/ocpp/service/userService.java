package com.axxera.ocpp.service;

import java.util.Map;

import com.axxera.ocpp.forms.startTxn;

public interface userService {

	Map<String, Object> getUserDataByIdTag(String idTag);

	long getAccIdOnBaseIdtag(String idTag);

	Map<String, Object> getGuestUserType(String phone, long stationId);

	boolean rfidOCPIAuthentication(String rfid, String stnRefNum);

	Map<String, Object> getDriverGroupIdTag(String idTag);

	Map<String, Object> manualIdCheck(String idTag, long manfId, long stnId, long siteId);

	Map<String, Object> driverGroupdIdRemoteAuth(long stationId, long userId);

	Map<String, Object> driverGroupdIdStart(long stationId, long userId);

	Map<String, Object> getOrgData(long orgId, String stationRefNum);

	startTxn getRfidIdentificationOrPhone(startTxn sTxn);

	String getTxnType(startTxn sTxn);

}
