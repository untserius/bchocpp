package com.axxera.ocpp.dao;

import java.util.List;
import java.util.Map;

import com.axxera.ocpp.message.StatusNotification;
import com.axxera.ocpp.model.ocpp.NotifyMe;

public interface StatusNotificationDao {

	String getStatus(long stationId, long connectorId) ;

	List<NotifyMe> getNotifications(long clientId) ;

	public void deleteNotification(long clientId) ;

	void updateStatusNotification(long clientId, String uniqueId, StatusNotification statusNoti,
			long stationConnectorId, Map<String, Object> station,String stationStatus,List<Map<String, Object>> portStatus) ;

	void updateInoperativeFlag(long StationId, long connectorid) ;

	void updatingStatusInStatusNotificationlist(String stationIds) ;

	boolean getReservationId(Long id, int reservationId, long stationId, String stnRefNum);

	void updateStatusNotificationAvailable(long clientId, String uniqueId, StatusNotification statusNoti,
			long stationConnectorId, Map<String, Object> station, Map<String, Object> portObj,String portStatus,List<Map<String, Object>> portStatusDB);

	void updateStatusNotificationUnAvailable(long clientId, String uniqueId, StatusNotification statusNoti,
			long stationConnectorId, Map<String, Object> station, Map<String, Object> portObj, String portStatus,List<Map<String, Object>> portStatusDB);

	List<Map<String, Object>> getPortStatus(long stationId, long connectorId);

	boolean getfaultMailCheck(long stnId,String status,String Date);
	
	void updatePortStatus(long portUniId, String portStatus);

	void insertdiagnosticsFilesLocation(String manufacturerId, long stnId, String location, String uniqueID);

	void updateDiagnosticsFilesLocation(String fileName, String uniqueID);

	void createFtpPath(String stationReferNo);

	void updateTimeInChargerAcivites(long stnId, String date);

	void updateStatusApiLoadManagement(String portStatus, long portId, Long stationId,boolean ampFlag);

	void updateOcppStatusNotification(String Status, Long stationId, Long connectorId,boolean ampFlag);

	void insertPortErrorStatus(StatusNotification statusNoti, long stnId, long portUniId, String clientId, String reqId,
			boolean scheduleMaintenance);
}
