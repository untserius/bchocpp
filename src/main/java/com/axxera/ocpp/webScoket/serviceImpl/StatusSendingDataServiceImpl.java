package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.axxera.ocpp.model.ocpp.ScheduledMaintenance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.dao.statusSendingDataDao;
import com.axxera.ocpp.model.ocpp.ChargerDefaultConfiguration;
import com.axxera.ocpp.model.ocpp.LocalList;
import com.axxera.ocpp.webSocket.service.StatusSendingDataService;

@Service
public class StatusSendingDataServiceImpl implements StatusSendingDataService {

	static Logger logger = LoggerFactory.getLogger(StatusSendingDataServiceImpl.class);

	@Autowired
	private statusSendingDataDao statusSendingDataDao;
	
	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Override
	public boolean updateResetStausInPortal(String uniqueID, String status,String response,Long statusId)  {
		return statusSendingDataDao.updateResetStausInPortal(uniqueID, status, response, statusId);
	}

	public void deleteReservationId(String value,boolean flag)  {
		statusSendingDataDao.deleteReservationId(value,flag);		
	}

	@Override
	public boolean addData(String messageType, String portalReqID, long stationId, String status, String sessionId,
			long userId, long requestId, long connectorId, String data, String configurationKey,String value,String client)  {
		return statusSendingDataDao.addData(messageType, portalReqID, stationId, status, sessionId, userId, requestId, connectorId, data, configurationKey, value, client);
	}

	@Override
	public String getSessIdStatusSendingData(String uniqueID)  { 
		return statusSendingDataDao.getSessIdStatusSendingData(uniqueID);
	}
	
	@Override
	public Map<String,Object> getStatusSendingData(String uniqueID)  {
		return statusSendingDataDao.getStatusSendingData(uniqueID);
	}

	public  boolean updateRemoteStopTransaction(String uniqueID, String status,String response)  {
		return statusSendingDataDao.updateRemoteStopTransaction(uniqueID,status,response);
	}

	@Override
	public void saveSendLocalListData(String idTag,Long stationID,String Version )  {
		statusSendingDataDao.saveSendLocalListData(idTag, stationID, Version);
	}
	
	@Override
	public List<LocalList> fetchSendLocalListData(Long stationID) {
		return statusSendingDataDao.fetchSendLocalListData(stationID);
	}
	
	@Override
	public void deleteSendLocalListData(Long stationID) {
		statusSendingDataDao.deleteSendLocalListData(stationID);
	}

	@Override
	public void updateSendLocalListData(String idTag, Long stationID, String Version)  {
		statusSendingDataDao.updateSendLocalListData(idTag,stationID,Version);
	}
	
	@Override
	public String ocppStatusSendingKey(String requestId) {
		return statusSendingDataDao.ocppStatusSendingKey(requestId);
	}
	
	@Override
	public void addRemoteStopTransaction(long connectorId, String uniqueID, long clientId, String status,String sessionId)  {
		statusSendingDataDao.addRemoteStopTransaction(connectorId,uniqueID,clientId,status,sessionId); 
	}
	
	@Override
	public String getStartTransactionId(Long connectorId,Long StationId) {
		return statusSendingDataDao.getStartTransactionId(connectorId, StationId);
	}
	
	@Override
	public List<ChargerDefaultConfiguration> getDefaultChargerConfiguration() {
		String query ="select * from charger_Default_Configuration";
		List< ChargerDefaultConfiguration> DCClist = generalDao.findAllHQLQuery(new ChargerDefaultConfiguration(), "FROM ChargerDefaultConfiguration");
		logger.info("fetching DCC data >>213: " + DCClist);
		return  DCClist;
	}

//	@Override
//	public void deleteFromScheduledMaintenance(long portId) {
//		try {
//			String deleteQuery = "DELETE FROM ScheduledMaintenance WHERE portId = " + portId;
//			generalDao.updateHqlQuiries(deleteQuery);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void insertIntoScheduledMaintenance(long portId, long stationId, Date endTimeStamp) {
//		try {
//			ScheduledMaintenance sm = new ScheduledMaintenance();
//			sm.setStnId(stationId);
//			sm.setPortId(portId);
//			sm.setEndTimeStamp(endTimeStamp);
//			sm.setResponse("Inprogress");
//
//			generalDao.save(sm);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void updateResponseInMaintenance(long portId, String response) {
//		try {
//			String updateQuery = "UPDATE ScheduledMaintenance SET response = '" + response + "' WHERE portId = " + portId;
//			generalDao.updateHqlQuiries(updateQuery);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
