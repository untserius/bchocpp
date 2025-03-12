package com.axxera.ocpp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StationDao {

	public void updateStationTimes(String utcDateFormate,long stationId) ;

	public long getStationUniqId(String stationId);

	int updateIntialContactTime(String newDate,long stationId,long siteId);

	List<String> getStnOwnerMails(long stnUniqId) ;

	public List getActiveTransactionIds(long stnUniqId) ;

	boolean transactionsOnInOperativeTimings(long stnUnqId, Date startTime);

	List<Map<String, Object>> pingStnIds(String stnLs);

	long getPortUniqId(long stationId, long connectorId);

	void updatingLastUpdatedTime(String newDate, long stationId, long siteId);
}
