package com.axxera.ocpp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.dao.StationDao;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.Utils;


@Service
public class StationDaoImpl implements StationDao{

	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	static Logger LOGGER = LoggerFactory.getLogger(StationDaoImpl.class);
	
	@Override
	public void updateStationTimes(String newDate,long stationId)  {
		try {
			if(stationId > 0) {
				String updateStationQuery="update Station set stationTimeStamp='"+newDate+"' where id="+stationId+"";
				executeRepository.update(updateStationQuery);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int updateIntialContactTime(String newDate,long stationId,long siteId) {
		int updateSqlQuiries = 0;
		try {
			String sql = "update Station set intialContactTime=(ISNULL(intialContactTime,'"+newDate+"')),stationAvailStatus='Active' where id="+stationId+" and stationAvailStatus='Not installed';";
			updateSqlQuiries = executeRepository.update(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return updateSqlQuiries;
	}
	@Override
	public void updatingLastUpdatedTime(String newDate,long stationId,long siteId) {
		try {
			String updateSql = "update station set lastUpdatedDate = '"+newDate+"' where id = "+stationId+";";
			
			updateSql += "update site set lastUpdatedDate = '"+newDate+"' where siteId = "+siteId+";";
			
			updateSql += "update port set lastUpdatedDate = '"+newDate+"' where station_id = "+stationId+";";
			executeRepository.update(updateSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public long getStationUniqId(String stationRefNum) {
		long val = 0;
		try {
			String uniId="select id as id from station where referNo='"+stationRefNum+"'";
			List<Map<String, Object>> queryForList = executeRepository.findAll(uniId);
			if(queryForList.size() > 0) {
				val = Long.valueOf(String.valueOf(queryForList.get(0).get("id")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	@Override
	public long getPortUniqId(long stationId,long connectorId) {
		long val = 0;
		try {
			String uniId="select id as id from port where connector_Id = "+connectorId+" and station_Id = "+stationId+"";
			List<Map<String, Object>> queryForList = executeRepository.findAll(uniId);
			if(queryForList.size() > 0) {
				val = Long.valueOf(String.valueOf(queryForList.get(0).get("id")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	@Override
	public List<String> getStnOwnerMails(long stnUniqId)  {
		List<String> data = new ArrayList<>();
		try {
			String uniId="select ownerMailId from station_owner_mailAlert where stationUnqId ="+stnUniqId;
			data = generalDao.findSQLQuery(uniId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List getActiveTransactionIds(long stnUniqId)  {
		// TODO Auto-generated method stub
		List data = new ArrayList<>();
		try {
			String uniId = "select st.transactionId from ocpp_startTransaction st inner join ocpp_activeTransaction at on st.transactionId = at.transactionId inner join statusNotification sn on sn.port_id = st.connectorId where sn.status = 'Charging' and st.stationId = '"+stnUniqId+"'";
			data = generalDao.findSQLQuery(uniId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@Override
	public boolean transactionsOnInOperativeTimings(long stnUnqId,Date startTime) {
		boolean flag = false;
		try {
			String startTimeformat = Utils.getTimeFrmt(startTime);
			String query = "select id from station where id = "+stnUnqId+" and convert(varchar, '"+startTimeformat+"', 108) between stationInperativeFrom and stationInOperativeTo";
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() == 0) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public List<Map<String,Object>> pingStnIds(String stnLs){
		List<Map<String,Object>> ls = new ArrayList<>();
		try {
			String query = "select referNo from station s INNER JOIN chargerActivities ca on s.id= ca.stationId  where s.referNo in ("+stnLs+") and ca.pingFlag = 1";
			ls = executeRepository.findAll(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
}
