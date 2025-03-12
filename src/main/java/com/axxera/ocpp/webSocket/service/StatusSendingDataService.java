package com.axxera.ocpp.webSocket.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.axxera.ocpp.model.ocpp.ChargerDefaultConfiguration;
import com.axxera.ocpp.model.ocpp.LocalList;
import com.axxera.ocpp.model.ocpp.OCPPStatusSendingData;

public interface StatusSendingDataService {

	public boolean addData(String messageType, String portalReqID, long stationId, String status, String sessionId,
			long userId, long value, long connectorId, String data, String configurationKey,String requestId,String client) ;

	public  boolean updateRemoteStopTransaction(String uniqueID, String status,String response) ;

	public void saveSendLocalListData(String idTag, Long stationID, String Version) ;

	public void updateSendLocalListData(String idTag, Long stationID, String Version) ;
	
	public void deleteReservationId(String uniqueID,boolean flag) ;

	public String ocppStatusSendingKey(String uniqueID);

	public void deleteSendLocalListData(Long stationId) ;

	public List<LocalList> fetchSendLocalListData(Long stationId) ;

	public String getSessIdStatusSendingData(String uniqueID) ;

	void addRemoteStopTransaction(long connectorId, String uniqueID, long clientId, String status, String sessionId)
			;

	String getStartTransactionId(Long connectorId, Long StationId);

	Map<String, Object> getStatusSendingData(String uniqueID) ;

	boolean updateResetStausInPortal(String uniqueID, String status, String response, Long statusId) ;

	public List<ChargerDefaultConfiguration> getDefaultChargerConfiguration();

//	void deleteFromScheduledMaintenance(long portId);
//
//	void insertIntoScheduledMaintenance(long portId, long stationId, Date endTimeStamp);
//
//	public void updateResponseInMaintenance(long portId, String response);
}
