package com.axxera.ocpp.dao;

import java.util.List;
import java.util.Map;

import com.axxera.ocpp.model.ocpp.LocalList;

public interface statusSendingDataDao {
	
	public boolean addData(String messageType, String portalReqID, long stationId, String status, String sessionId,
			long userId, long value, long connectorId, String data, String configurationKey,String requestId,String client) ;

	public boolean updateResetStausInPortal(String uniqueID, String status, String response,Long statusId) ;
	
	public  boolean updateRemoteStopTransaction(String uniqueID, String status,String response) ;

	public void saveSendLocalListData(String idTag, Long stationID, String Version) ;

	public void updateSendLocalListData(String idTag, Long stationID, String Version) ;
	
	public void deleteReservationId(String uniqueID,boolean flag) ;

	public String ocppStatusSendingKey(String uniqueID);

	public void deleteSendLocalListData(Long stationId) ;

	public List<LocalList> fetchSendLocalListData(Long stationId) ;

	public String getSessIdStatusSendingData(String uniqueID) ;

	void addRemoteStopTransaction(long connectorId, String uniqueID, long clientId, String status, String sessionId);

	String getStartTransactionId(Long connectorId, Long StationId);

	Map<String, Object> getStatusSendingData(String uniqueID) ;
}