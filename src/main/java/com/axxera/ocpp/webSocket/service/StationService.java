package com.axxera.ocpp.webSocket.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.message.ReserveNow;
import com.axxera.ocpp.model.ocpp.AccountTransactions;
import com.axxera.ocpp.model.ocpp.SetChargingProfile;
import com.axxera.ocpp.model.ocpp.Station;



public interface StationService {

	public Map<String, Object> getStnByRefNum(String stationRefNo) ;
	
	public Map<String, Object> getPortByRefNum(long stationRefNo,long portId) ;

	public BigDecimal getReservationFee(Long stationId);

	public void insertReserNowData(ReserveNow now) ;
		
	public long getStationUniqId(String stationId);
	
	public String getstationRefNum(long stationId);
	
	public long getStationConnectorId(long connectorId);

	public Long getstackLevel(long connectorId, long stationId, String chargingProfilePurpose);

	public Long getchargingProfileId(long connectorId, long stationId, String chargingProfilePurpose);

	public void insertChargingProfileData(SetChargingProfile chargingProfile, Long stackLevel, Long chargingProfileId) ;
	
	public String getTransactionId(long connectorId, long stationId);

	long getUserIdOnReservationId(long reserveId, long stnUniId);

	public BigDecimal getCancelReservationFee(Long stationId);

	void updateStationTimes(long stationId) ;

	int updateIntialContactTime(long stationId) ;

	List<String> getStnOwnerMails(long stnUniqId) ;

	List getActiveTransactionIds(long stnUniqId) ;

	boolean transactionsOnInOperativeTimings(long stnUnqId, Date startTime);

	List<Map<String, Object>> pingStnIds(String stnLs);

	long getPortUniId(long stationUniId, long connectorId);

	Map<String, Object> getSiteDetails(long stationId);

	Map<String, Object> getManufacturerName(long stationId);

	void updatingLastUpdatedTime(String newDate, long stationId, long siteId);

	void insertChargingProfileDataForm(OCPPForm chargingProfile, Long stacklevel, Long chargingProfileId);

	Map<String, Object> getPortByRefNumById(long stnId, Long portId);
	
	public String getDisplayNameByPortId(long portId);

	public Map<String, Object> getReservationFeeOngngTxn(Long portId);

	void updateReservation(long flag, long portId);

	void updateReservationId(int flag, long stnId, long portId, Long userId, int whrFlag, boolean activeFlag,
			String sessionId, String reservationId, int cancellationFlag,int chargerFaultCase,long id);

	void updateReservation(int flag, long stnId, long portId, Long userId, int whrFlag, boolean activeFlag,
			String sessionId, String reservationId, int cancellationFlag, int chargerFaultCase);
	
	public Map<String, Object> getStationFlags(long stnUniId);

	void insertchargerActivities(long stationId, String stationRefNum);

	Map<String, Object> getCoordinatesByCoordinateIdId(long coordinateId);

	Map<String, Object> getPortFormatsByPortId(long formatId);

	Map<String, Object> getStnObjByRefNos(String stnRefNo, int connectorId);

	Map<String, Object> getStnObjByUniIds(long stnId,long portId);

	public List<Map<String, Object>> getstnAuth(String referNo);

	boolean getServerHitFromChargerActivities(FinalData finalData, long stnId, String req);

	List<Map<String, Object>> getFleetSessionIdByPortId(long portId);

	List<Map<String, Object>> getStationRefNo(long stationId);

	public Map<String, Object> getPricingDetailsByStnId(long stationId, long tariffid);

	public boolean siteTimingsCheck(long parseLong, Date utcDate);

	long getConnectorId(long stationId);

}
