package com.axxera.ocpp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.dao.statusSendingDataDao;
import com.axxera.ocpp.model.ocpp.LocalList;
import com.axxera.ocpp.model.ocpp.OCPPRemoteStopTransaction;
import com.axxera.ocpp.model.ocpp.OCPPStatusSendingData;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.Utils;


@Service
public class statusSendingDataDaoImpl implements statusSendingDataDao{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(statusSendingDataDaoImpl.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private Utils utils;
	
	@Override
	public boolean updateResetStausInPortal(String uniqueID, String status,String response,Long statusId)  {
		try {
			String updateSQl ="update ocpp_statusSendingData set status='"+status+"' , response='"+response+"' where portalReqID='"+uniqueID+"' and id = '"+statusId+"'";
			executeRepository.update(updateSQl);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void deleteReservationId(String value,boolean flag)  {
		try {
			String deleteReservQuery="update ocpp_reservation set flag=0,cancellationFlag ='"+flag+"', activeFlag='false' where reservationId = '"+value+"' and flag =1";
			executeRepository.update(deleteReservQuery);	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addData(String messageType, String portalReqID, long stationId, String status, String sessionId,long userId, long requestId, long connectorId, String data, String configurationKey,String value,String client)  {
		try {
			OCPPStatusSendingData sendingData = new OCPPStatusSendingData();
			sendingData.setConfigurationKey(configurationKey);
			sendingData.setConnectorId(connectorId);
			sendingData.setData(data);
			sendingData.setMessageType(messageType);
			sendingData.setPortalReqID(portalReqID);
			sendingData.setSessionId(sessionId);
			sendingData.setStationId(stationId);
			sendingData.setStatus(status);
			sendingData.setUserId(userId);
			sendingData.setRequestId(requestId);
			sendingData.setValue(value);
			sendingData.setCreatedDate(utils.getUTCDate());
			sendingData.setClient(client);
			generalDao.save(sendingData);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String getSessIdStatusSendingData(String uniqueID)  {
		String val = null;
		try {
			String sqlQuery = "select top 1 sessionId from ocpp_statusSendingData where portalReqId = '"+uniqueID+"' order by id desc";
			val = generalDao.getRecordBySql(sqlQuery);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public Map<String,Object> getStatusSendingData(String uniqueID)  { 
		Map<String,Object> map = new HashMap<>();
		try {
			String sqlQuery = "select top 1 id,sessionId,userId,value,connectorId,isnull(client,'') as client,status,response from ocpp_statusSendingData where portalReqId = '"+uniqueID+"' order by id desc";
			List<Map<String, Object>> mapData = executeRepository.findAll(sqlQuery);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("sessionId", 0);
				map.put("userId", 0);
				map.put("value", 0);
				map.put("connectorId", 0);
				map.put("id", 0);
				map.put("status","Inprogress");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public boolean updateRemoteStopTransaction(String uniqueID, String status,String response)  {
		try {
			executeRepository.update("update ocpp_remoteStopTransaction set status='"+status+"' where requestID='"+uniqueID+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void saveSendLocalListData(String idTag,Long stationID,String Version )  {
		try {
			LocalList locallist = new LocalList();
			locallist.setIdTag(idTag);
			locallist.setStationId(stationID);
			locallist.setVersionNumber(Version);
			generalDao.save(locallist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<LocalList> fetchSendLocalListData(Long stationID) {
		List<LocalList> findAll = new ArrayList<LocalList>();
		try {
			findAll = generalDao.findAll("From LocalList Where stationId=" + stationID, new LocalList());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return findAll;
	}
	
	@Override
	public void deleteSendLocalListData(Long stationID) {
		try {
			String deleteLocalListData="delete from local_list where stationId="+stationID+"";
			executeRepository.update(deleteLocalListData);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateSendLocalListData(String idTag, Long stationID, String Version)  {
		try {
			String hql="Update LocalList set VersionNumber = '"+Version+"' where StationId = "+stationID+"";
			executeRepository.update(hql);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String ocppStatusSendingKey(String requestId) {
		String val = null;
		try {
			String sqlQuery="select value from ocpp_statusSendingData where configurationKey='StationMode' and portalReqID='"+requestId+"'";
			val = generalDao.getRecordBySql(sqlQuery);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public void addRemoteStopTransaction(long connectorId, String uniqueID, long clientId, String status,String sessionId)  {
		try {
			Thread th = new Thread() {
				public void run() {
					OCPPRemoteStopTransaction ocppRemoteStopTransaction = new OCPPRemoteStopTransaction();
					ocppRemoteStopTransaction.setRequestID(uniqueID);
					ocppRemoteStopTransaction.setSessionId(sessionId);
					ocppRemoteStopTransaction.setStationId(clientId);
					ocppRemoteStopTransaction.setStatus(status);
					ocppRemoteStopTransaction.setTransactionId(connectorId);
					generalDao.save(ocppRemoteStopTransaction);
				}
			};
			th.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getStartTransactionId(Long connectorId,Long StationId) {
		String startTransactionId = null;
		try {
//			startTransactionId = generalDao.getRecordBySql("select transactionId as transactionId from ocpp_startTransaction "
//					+ " where connectorId="+connectorId+" and stationId="+StationId+" order by id desc");
			
			startTransactionId = generalDao.getRecordBySql("select os.transactionId from ocpp_activeTransaction oa inner join ocpp_startTransaction os "
					+ " on oa.sessionId = os.sessionId where os.stationId='"+StationId+"' and os.connectorId='"+connectorId+"' order by os.id desc");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return startTransactionId;
	}
}
