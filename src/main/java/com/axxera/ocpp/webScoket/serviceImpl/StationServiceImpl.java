package com.axxera.ocpp.webScoket.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.config.EmailServiceImpl;
import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.dao.StationDao;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.message.ReserveNow;
import com.axxera.ocpp.message.StatusNotification;
import com.axxera.ocpp.message.StopTransaction;
import com.axxera.ocpp.model.ocpp.OCPPChargingProfile;
import com.axxera.ocpp.model.ocpp.OCPPChargingSchedulePeriod;
import com.axxera.ocpp.model.ocpp.OCPPReservation;
import com.axxera.ocpp.model.ocpp.SetChargingProfile;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StationService;

@Service
public class StationServiceImpl implements StationService {

	Logger logger = LoggerFactory.getLogger(StationServiceImpl.class);

	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private  StationDao stationDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private Utils Utils;

	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	public Map<String,Object> getStnByRefNum(String stationRefNo)  {
		Map<String,Object> map = new HashMap<>();
		try {
			List<Map<String, Object>> mapData = executeRepository.findAll("select isNull(st.scheduleMaintenance,'0') as scheduleMaintenance,st.stationAvailStatus,st.id,st.referNo,isNull(st.supportMailFlag,'0') as supportMailFlag,"
					+ "ISNULL(CASE when (st.stationMode = 'null') Then 'Normal' ELSE st.stationMode END,'freeven') as stationMode,"
					+ " isnull(st.stationFreeDuration,'0') as stationFreeDuration,isnull(st.greetingMail,'') as greetingMail ,isnull(st.greetingMessage,'') as greetingMessage,"
					+ " isnull(st.graceTime,'0') as graceTime ,isnull(st.connectedTime,'0') as connectedTime,isnull(st.connectedTimeFlag,'0') as connectedTimeFlag,"
					+ " isnull(st.connectedTimeUnits,'Hr') as connectedTimeUnits, isnull(st.stationMaxSessiontime,'0') as stationMaxSessiontime,"
					+ " ISNULL(st.exceedingHours,'0') as exceedingHours, ISNULL(st.exceedingMints,'0') as exceedingMints,ISNULL(st.stationAddress,'-') as stationAddress,"
					+ " st.autocharging,st.preProduction,isnull(st.mailEnable,'Disable') as mailEnable,isnull(st.portQuantity,0) as portQuantity,st.stationName,m.id as manfId,m.manfname,st.creationDate,st.ampFlag as ampFlag,st.capacity,ISNULL(st.siteId,0) as siteId from "
					+ " station st inner join manufacturer m on st.manufacturerId = m.id where st.referNo = '"+stationRefNo+"'");
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public Map<String,Object> getStnObjByUniIds(long stnId,long portId){
		Map<String,Object> map = new HashMap<>();
		try {
			List<Map<String, Object>> mapData = executeRepository.findAll("select st.id as stnId,st.referNo as stnRefNum,isNull(st.supportMailFlag,'0') as supportMailFlag,"
					+ " ISNULL(CASE when (st.stationMode = 'null') Then 'paymentMode' ELSE st.stationMode END,'freeven') as stationMode,"
					+ " isnull(st.stationMaxSessiontime,'0') as stationMaxSessiontime,"
					+ " ISNULL(st.exceedingHours,'0') as exceedingHours, ISNULL(st.exceedingMints,'0') as exceedingMints,ISNULL(st.stationAddress,'-') as stationAddress,"
					+ " st.autocharging,st.preProduction,isnull(st.mailEnable,'Disable') as mailEnable,isnull(st.portQuantity,0) as portQuantity,st.stationName,"
					+ " m.id as manfId,m.manfname,st.creationDate,st.ampFlag as ampFlag,st.capacity,ISNULL(st.siteId,0) as siteId,p.id as portId,connector_id,station_id,"
					+ " isnull(chargerType,'AC') as chargerType,isnull(displayName,'Port-1') as displayName,"
					+ " (select displayName from connectorType where id = ISNULL(standard,1)) as connectorType,isnull(power_type,1) as power_type,"
					+ "  p.Amperage,p.capacity as portCapacity,p.uuid,p.format,p.power_type,p.standard,isnull(powerSharing,'Y') as powerSharing "
					+ " from station st inner join manufacturer m on st.manufacturerId = m.id inner join port p on p.station_Id = st.id where st.id = '"+stnId+"' and p.id='"+portId+"'");
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public Map<String,Object> getStnObjByRefNos(String stnRefNo,int connectorId){
		Map<String,Object> map = new HashMap<>();
		try {
			List<Map<String, Object>> mapData = executeRepository.findAll("select st.id as stnId,st.referNo,isNull(st.supportMailFlag,'0') as supportMailFlag,"
					+ " ISNULL(CASE when (st.stationMode = 'null') Then 'paymentMode' ELSE st.stationMode END,'freeven') as stationMode,"
					+ " isnull(st.stationMaxSessiontime,'0') as stationMaxSessiontime,"
					+ " ISNULL(st.exceedingHours,'0') as exceedingHours, ISNULL(st.exceedingMints,'0') as exceedingMints,ISNULL(st.stationAddress,'-') as stationAddress,"
					+ " st.autocharging,st.preProduction,isnull(st.mailEnable,'Disable') as mailEnable,isnull(st.portQuantity,0) as portQuantity,ISNULL(st.stationName,'-') as stationName,"
					+ " m.id as manfId,m.manfname,st.creationDate,st.ampFlag as ampFlag,st.capacity,ISNULL(st.siteId,0) as siteId,p.id as portId,connector_id,station_id,"
					+ " isnull(chargerType,'AC') as chargerType,isnull(displayName,'Port-1') as displayName,"
					+ " (select displayName from connectorType where id = ISNULL(standard,1)) as connectorType,isnull(power_type,1) as power_type,"
					+ "  p.Amperage,isNull(p.capacity,'0') as portCapacity,p.uuid,p.format,p.power_type,p.standard "
					+ " from station st inner join manufacturer m on st.manufacturerId = m.id inner join port p on p.station_Id = st.id where st.referNo = '"+stnRefNo+"' "
					+ " and p.connector_id='"+connectorId+"'");
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String,Object> getPortByRefNum(long stnId,long portId)  {
		Map<String,Object> map = new HashMap<>();
		try {
			List<Map<String, Object>> mapData = executeRepository.findAll("select id,connector_id,station_id,isnull(chargerType,'AC') as chargerType,isnull(displayName,'Port-1') as displayName,capacity,(select displayName from connectorType where id = ISNULL(standard,1)) as connectorType,isnull(power_type,1) as power_type,p.Amperage,p.capacity,p.uuid,p.format,p.power_type,p.standard  from port p  where station_id = '"+stnId+"' and connector_id = '"+portId+"'");
			
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public Map<String,Object> getPortByRefNumById(long stnId,Long portId)  {
		Map<String,Object> map = new HashMap<>();
		try {
			String str = "select id,connector_id,station_id ,isnull(chargerType,'AC') as chargerType, "
					+ " isnull(displayName,'Port-1') as displayName,capacity,(select displayName from connectorType where id = "
					+ " ISNULL(standard,1)) as connectorType,isnull(power_type,1) as power_type from port "
					+ " where id = '"+portId+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(str);
			
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
		
	public BigDecimal getReservationFee(Long stationId) {
		BigDecimal parseDouble = new BigDecimal("0.00");
		try {
			String getReservationFee="select IsNull(convert(varchar,reservationFee,20),0) from station where id="+stationId+"";
			String recordBySql = generalDao.getRecordBySql(getReservationFee);
			parseDouble =new BigDecimal(recordBySql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return parseDouble;
	}
	
	public BigDecimal getCancelReservationFee(Long stationId) {
		BigDecimal parseDouble = new BigDecimal("0.00");
		try {
			String getCancelReservationFee="select IsNull(convert(varchar,cancelReservationFee,20),0) from station where id="+stationId+"";
			
			String recordBySql = generalDao.getRecordBySql(getCancelReservationFee);
			parseDouble =new BigDecimal(recordBySql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return parseDouble;
	}

	@Override
	public void insertReserNowData(ReserveNow reserveNow)  {
		try {
			OCPPReservation ocppReserveNowData = new OCPPReservation();
			ocppReserveNowData.setStationId(reserveNow.getStationId());
			ocppReserveNowData.setPortId(reserveNow.getConnectorId());
			ocppReserveNowData.setIdTag(reserveNow.getIdTag());
			ocppReserveNowData.setStartTime(reserveNow.getStarTime());
			ocppReserveNowData.setReservationId(reserveNow.getReservationId());
			ocppReserveNowData.setUserId(reserveNow.getUserId());
			ocppReserveNowData.setEndTime(reserveNow.getEndTime());
			ocppReserveNowData.setReqId(reserveNow.getUniqueId());
			ocppReserveNowData.setSessionId(reserveNow.getSessionId());
			ocppReserveNowData.setFlag(false); 
			ocppReserveNowData.setActiveFlag(reserveNow.isActiveFlag());
			ocppReserveNowData.setCancellationFlag(reserveNow.isCancellationFlag());
			ocppReserveNowData.setReserveAmount(reserveNow.getReserveAmount());
			generalDao.save(ocppReserveNowData);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public long getStationUniqId(String stationRefNum) {
		long stationUniqId = 0;
		try {
			stationUniqId = stationDao.getStationUniqId(stationRefNum);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return stationUniqId;
	}

	@Override
	public String getstationRefNum(long stationId) {
		String stationRefnum = null;
		try {
			String queryForStationRefNo="select isNull(referNo,0) from station where id="+stationId+"";
			
			stationRefnum = generalDao.getRecordBySql(queryForStationRefNo);
			stationRefnum = stationRefnum==null ? "0" : stationRefnum;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return stationRefnum;
	}
	@Override
	public long getConnectorId(long portId) {
		long connectorId = 0;
		try {
			String queryForStationRefNo="select ISNULL(connector_id,'1') AS connector_id from port where id="+portId+"";
			List<Map<String,Object>> list=executeRepository.findAll(queryForStationRefNo);
			if(list.size()>0) {
				connectorId=Long.parseLong(String.valueOf(list.get(0).get("connector_id")));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return connectorId;
	}
	
	@Override
	public long getStationConnectorId(long connectorId) {
		long connectorNum = 1;
		try {
			String queryforConnectorDisplay="select ISNULL(connector_id,'1') AS connector_id from port where id="+connectorId+"";
			String connId = String.valueOf(executeRepository.findAll(queryforConnectorDisplay).get(0).get("connector_id"));
			
			if(connId!=null) {
				connectorNum = connId.contains("1") ? 1 :connId.contains("2") ? 2 :connId.contains("3") ? 3 : 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return connectorNum;
	}
	
	@Override
	public void updateReservation(int flag,long stnId,long portId,Long userId,int whrFlag,boolean activeFlag,String sessionId,String reservationId,int cancellationFlag,int chargerFaultCase) {
		try {
			executeRepository.update("update ocpp_reservation set flag = '"+flag+"' , activeFlag = '"+activeFlag+"', transactionSessionId = '"+sessionId+"',cancellationFlag="+cancellationFlag+",chargerFaultCase="+chargerFaultCase+" where stationId = '"+stnId+"' and portId = '"+portId+"' and userId = '"+userId+"' and reservationId = '"+reservationId+"' and flag = '"+whrFlag+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateReservationId(int flag,long stnId,long portId,Long userId,int whrFlag,boolean activeFlag,String sessionId,String reservationId,int cancellationFlag,int chargerFaultCase,long id) {
		try {
			executeRepository.update("update ocpp_reservation set flag = '"+flag+"' , activeFlag = '"+activeFlag+"', transactionSessionId = '"+sessionId+"',cancellationFlag="+cancellationFlag+",chargerFaultCase="+chargerFaultCase+" where id="+id);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateReservation(long flag,long portId) {
		try {
			executeRepository.update("update ocpp_reservation set flag = '"+flag+"',activeFlag = '"+flag+"' where portId = '"+portId+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Long getstackLevel(long connectorId, long stationId, String chargingProfilePurpose) {
		long stackLevel = 0;
		try {
			String queryforConnectorDisplay="SELECT distinct ISNULL(MAX(stackLevel),0) AS stacklevel FROM ocpp_chargingProfile WHERE "
					+ " stationId = '"+stationId+"' AND portId = '"+connectorId+"' AND chargingProfilePurpose = '"+chargingProfilePurpose+"'";
			String output = String.valueOf(executeRepository.findAll(queryforConnectorDisplay).get(0).get("stacklevel"));		
			stackLevel = Long.parseLong(output);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return stackLevel;
	}
	
	@Override
	public Long getchargingProfileId(long connectorId, long stationId, String chargingProfilePurpose) {
		Long chargingProfileId = 0l;
		try {
			String queryforConnectorDisplay="SELECT distinct ISNULL(MAX(chargingProfileId),0) AS chargingProfileId FROM ocpp_chargingProfile WHERE "
					+ " stationId = '"+stationId+"' AND portId = '"+connectorId+"' AND chargingProfilePurpose = '"+chargingProfilePurpose+"'";
			String output = String.valueOf(executeRepository.findAll(queryforConnectorDisplay).get(0).get("chargingProfileId"));		
			chargingProfileId = Long.parseLong(output);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return chargingProfileId;
	}
	
	@Override
	public void insertChargingProfileData(SetChargingProfile chargingProfile,Long stacklevel,Long chargingProfileId)  {
		try {

			OCPPChargingProfile ocppChargingProfile = new OCPPChargingProfile();
			
			ocppChargingProfile.setStationId(chargingProfile.getStationId());
			ocppChargingProfile.setPortId(chargingProfile.getConnectorId());
			ocppChargingProfile.setChargingProfileId(chargingProfileId);
			ocppChargingProfile.setChargingProfileKind(chargingProfile.getCsChargingProfiles().iterator().next().getChargingProfileKind());
			ocppChargingProfile.setChargingProfilePurpose(chargingProfile.getCsChargingProfiles().iterator().next().getChargingProfilePurpose());
			ocppChargingProfile.setChargingRateUnit(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingRateUnit());
			ocppChargingProfile.setDuration(String.valueOf(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getDuration()));
			ocppChargingProfile.setRecurrencyKind(chargingProfile.getCsChargingProfiles().iterator().next().getRecurrencyKind());
			ocppChargingProfile.setStackLevel(stacklevel);
			ocppChargingProfile.setValidFrom(chargingProfile.getCsChargingProfiles().iterator().next().getValidFrom());
			ocppChargingProfile.setValidTo(chargingProfile.getCsChargingProfiles().iterator().next().getValidTo());
			
			generalDao.save(ocppChargingProfile);
			
			OCPPChargingSchedulePeriod OCPPChargingSchedulePeriod = new OCPPChargingSchedulePeriod();
			OCPPChargingSchedulePeriod.setStationId(chargingProfile.getStationId());
			OCPPChargingSchedulePeriod.setPortId(chargingProfile.getConnectorId());
			OCPPChargingSchedulePeriod.setChargingProfileId(chargingProfileId);
			OCPPChargingSchedulePeriod.setLimit(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingSchedulePeriod().iterator().next().getLimit());
			OCPPChargingSchedulePeriod.setNumberPhases(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingSchedulePeriod().iterator().next().getNumberPhases());
			OCPPChargingSchedulePeriod.setStartPeriod(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingSchedulePeriod().iterator().next().getStartPeriod());
			generalDao.save(OCPPChargingSchedulePeriod);
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insertChargingProfileDataForm(OCPPForm chargingProfile,Long stacklevel,Long chargingProfileId)  {
		try {

			OCPPChargingProfile ocppChargingProfile = new OCPPChargingProfile();
			
			ocppChargingProfile.setStationId(chargingProfile.getStationId());
			ocppChargingProfile.setPortId(chargingProfile.getConnectorId());
			ocppChargingProfile.setChargingProfileId(chargingProfileId);
			ocppChargingProfile.setChargingProfileKind(chargingProfile.getCsChargingProfiles().iterator().next().getChargingProfileKind());
			ocppChargingProfile.setChargingProfilePurpose(chargingProfile.getCsChargingProfiles().iterator().next().getChargingProfilePurpose());
			ocppChargingProfile.setChargingRateUnit(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingRateUnit());
			ocppChargingProfile.setDuration(String.valueOf(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getDuration()));
			ocppChargingProfile.setRecurrencyKind(chargingProfile.getCsChargingProfiles().iterator().next().getRecurrencyKind());
			ocppChargingProfile.setStackLevel(stacklevel);
			ocppChargingProfile.setValidFrom(chargingProfile.getCsChargingProfiles().iterator().next().getValidFrom());
			ocppChargingProfile.setValidTo(chargingProfile.getCsChargingProfiles().iterator().next().getValidTo());
			
			generalDao.save(ocppChargingProfile);
			
			OCPPChargingSchedulePeriod OCPPChargingSchedulePeriod = new OCPPChargingSchedulePeriod();
			OCPPChargingSchedulePeriod.setStationId(chargingProfile.getStationId());
			OCPPChargingSchedulePeriod.setPortId(chargingProfile.getConnectorId());
			OCPPChargingSchedulePeriod.setChargingProfileId(chargingProfileId);
			OCPPChargingSchedulePeriod.setLimit(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingSchedulePeriod().iterator().next().getLimit());
			OCPPChargingSchedulePeriod.setNumberPhases(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingSchedulePeriod().iterator().next().getNumberPhases());
			OCPPChargingSchedulePeriod.setStartPeriod(chargingProfile.getCsChargingProfiles().iterator().next().getChargingSchedule().iterator().next().getChargingSchedulePeriod().iterator().next().getStartPeriod());
			generalDao.save(OCPPChargingSchedulePeriod);
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionId(long connectorId, long stationId) {
		String output = "";
		try {
			String queryforConnectorDisplay=" Select top 1 transactionId as transactionId from ocpp_activeTransaction where stationId = '"+stationId+"' and connectorId = '"+connectorId+"' order by id desc ";
			List<Map<String, Object>> data = executeRepository.findAll(queryforConnectorDisplay);
			
			if(data.isEmpty()) {
				 output = "0";
			}else {
				 output = String.valueOf(data.get(0).get("transactionId"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	@Override
	public long getUserIdOnReservationId(long reserveId,long stnUniId) {
		long userId = 0;
		try {
			String singleRecord = generalDao.getSingleRecord("SELECT userId From OCPPReservation WHERE reservationId = "+reserveId+" and stationId = "+stnUniId+"");
			if(singleRecord != null && singleRecord !="null" && !singleRecord.equalsIgnoreCase("singleRecord"))
				userId = Long.valueOf(singleRecord);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void updateStationTimes(long stationId)  {
		try {
			String utcDateFormate = Utils.getUTCDateString();
			stationDao.updateStationTimes(utcDateFormate,stationId);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public int updateIntialContactTime(long stationId)  {
		int i = 0;
		try {
			long siteId = Long.valueOf(String.valueOf(getSiteDetails(stationId).get("siteId")));
			String utcDateFormate = Utils.getUTCDateString();
			i = stationDao.updateIntialContactTime(utcDateFormate,stationId,siteId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	@Override
	public List<String> getStnOwnerMails(long stnUniqId)  {
		return stationDao.getStnOwnerMails(stnUniqId);
	}
	
	@Override
	public List getActiveTransactionIds(long stnUniqId) {
		return stationDao.getActiveTransactionIds(stnUniqId);
	}
	
	@Override
	public boolean transactionsOnInOperativeTimings(long stnUnqId,Date startTime) {
		return stationDao.transactionsOnInOperativeTimings(stnUnqId, startTime);
	}
	
	@Override
	public List<Map<String,Object>>pingStnIds(String stnLs){
		return stationDao.pingStnIds(stnLs);
	};
	
	@Override
	public long getPortUniId(long stationUniId,long connectorId) {
		return stationDao.getPortUniqId(stationUniId, connectorId);
	}

	@Override
	public Map<String,Object> getCoordinatesByCoordinateIdId(long coordinateId){
		Map<String, Object> map = new HashMap<>();
        try {
            String str = "select latitude,longitude from geoLocation where id ="+coordinateId;
            List<Map<String, Object>> mapData = executeRepository.findAll(str);
            if(mapData.size() > 0) {
                map = mapData.get(0);
            }else {
            	map.put("ocpiflag", "0");
                map.put("currencySymbol", "&#36;");
                map.put("currencyType", "USD");
                map.put("siteId", "0");
                map.put("processingFee", "0.00");
                map.put("siteName", "-");
                map.put("saleTexPerc", "0.00");
                map.put("uuid", "0");
                map.put("streetName", "");
                map.put("city", "");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return map;
	}
	@Override
	public Map<String,Object> getPortFormatsByPortId(long formatId){
		Map<String, Object> map = new HashMap<>();
        try {
            String str = "select name from connectorFormat where id="+formatId;
            List<Map<String, Object>> mapData = executeRepository.findAll(str);
            if(mapData.size() > 0) {
                map = mapData.get(0);
            }else {
            	map.put("name", "CABLE");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return map;
	}
		
	@Override
    public Map<String, Object> getSiteDetails(long stationId) {
        Map<String, Object> map = new HashMap<>();
        try {
            String str = "select isnull(si.currencySymbol,'&#36;') as currencySymbol,si.streetName,si.city,si.state,si.postal_code,ISNULL(si.currencyType,'USD') as currencyType, isNull(si.siteId,'0') as siteId,si.siteName as siteName,isnull(si.uuid,'0') as uuid,si.ocpiflag,si.coordinateId,si.country  from station st inner join site si on st.siteId = si.siteId where st.id = '"+stationId+"'";
            List<Map<String, Object>> mapData = executeRepository.findAll(str);
            if(mapData.size() > 0) {
                map = mapData.get(0);
            }else {
            	map.put("ocpiflag", "0");
                map.put("currencySymbol", "&#36;");
                map.put("currencyType", "USD");
                map.put("siteId", "0");
                map.put("processingFee", "0.00");
                map.put("siteName", "-");
                map.put("saleTexPerc", "0.00");
                map.put("uuid", "0");
                map.put("streetName", "");
                map.put("city", "");
                map.put("country", "");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return map;
	}
	@Override
	public Map<String,Object> getManufacturerName(long stationId){
		Map<String, Object> map = new HashMap<>();
		try {
			String str = "select m.manfname AS manufacturer from station st inner join manufacturer m on st.manufacturerId = m.id where st.id = "+stationId+"";
			List<Map<String, Object>> mapData = executeRepository.findAll(str);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("manufacturer", "");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public void updatingLastUpdatedTime(String newDate, long stationId, long siteId) {
		try {
			stationDao.updatingLastUpdatedTime(newDate,stationId,siteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getDisplayNameByPortId(long PortId) {
		String displayName = null;
		try {
			String query = "select isnull(displayName,'Port-1') as displayName from port where id="+PortId;
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				displayName=String.valueOf(mapData.get(0).get("displayName"));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return displayName;
	}
	
	@Override
	public Map<String, Object> getReservationFeeOngngTxn(Long portId) {
		Map<String,Object> map = new HashMap<>();
		try {
			String getReservationFee="select top 1 IsNull(ora.reserveAmount,0) as reserveAmount,ora.portId as portId,isnull(p.displayName,'Port-1') as displayName,"
					+ "isnull(ora.cancellationFee,0) as cancellationFee, ora.userId as userId,ora.stationId as stationId,"
					+ "ora.reservationId as reservationId,isnull(u.orgId,1) orgId,ora.cancellationFlag "
					+ "from ocpp_reservation ora inner join port p on p.id = ora.portId inner join users u on u.userid=ora.userId "
					+ "where ora.portId="+portId+" and ora.activeFlag=1 order by ora.id desc";

			List<Map<String, Object>> mapData = executeRepository.findAll(getReservationFee);
			if(mapData.size() > 0) {
				map=mapData.get(0);
			}		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return map;
	}
		
	@Override 
	public Map<String, Object> getStationFlags(long stnUniId){
		Map<String, Object> flags = new HashMap<String, Object>();
		try {
			
			String query=("select pingFlag, changeConfig, triggerMessageFlag, faultedMailFlag,configurationKeys,supportMailFlag from chargerActivities where stationId = '"+stnUniId+"'");
		    List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				flags = mapData.get(0);
			}else {
				flags.put("pingFlag", 0);
				flags.put("changeConfig", 0);
				flags.put("triggerMessageFlag", 0);
				flags.put("faultedMailFlag", 0);
				flags.put("configurationKeys", 0);
				flags.put("supportMailFlag", 0);
			}
			     
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flags ;
	}
	
	@Override 
	public void insertchargerActivities(long stationId,String stationRefNum) {
		try {
			String query = "select stationId from chargerActivities where stationId= " + stationId ;
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if (mapData.size() == 0) {
				String insertQuery = "insert into chargerActivities (pingFlag, changeConfig, triggerMessageFlag, faultedMailFlag,configurationKeys, supportMailFlag,stationId,faultedMailDate,portId,statusDate,status) values('"
						+ 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 1 + "','"+stationId+"','"+ Utils.getDate(new Date()) +"',0,'"+Utils.getDate(new Date())+"','')";
				executeRepository.update(insertQuery);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<Map<String, Object>> getstnAuth(String referNo) {
		try {
			String str = "select st.id as stationId,os.password,ISNULL(os.profileName,'None') as profileName,os.userName,os.connectionType from ocpp_security os right join station st on os.stationid = st.id where st.referNo='"+referNo+"'";
			return executeRepository.findAll(str);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean getServerHitFromChargerActivities(FinalData finalData, long stnId, String req) {
		boolean flag=true;
		try {
			//String req = finalData.getStartTransaction()!=null ? "StartTransaction": finalData.getStopTransaction()!=null?"StopTransaction":finalData.getStatusNotification()!=null?"StatusNotification":null;
			if(req.equalsIgnoreCase("StatusNotification")) {
				StatusNotification statusNoti = finalData.getStatusNotification();
				long portId=0;
				String status = statusNoti.getStatus().equalsIgnoreCase("Unavailable") ? "Inoperative"
						: statusNoti.getStatus().equalsIgnoreCase("Finishing") ? "Removed"
						: statusNoti.getStatus().equalsIgnoreCase("Faulted") ? "Blocked"
						: statusNoti.getStatus().equalsIgnoreCase("Preparing") ? "Planned"
						: statusNoti.getStatus();
				if(statusNoti!=null &&statusNoti.getConnectorId()>0) {
					String query="select sn.status,p.id as portId from statusNotification sn inner join port p on p.id=sn.port_id where p.connector_id='"+statusNoti.getConnectorId()+"' and p.station_id='"+stnId+"' order by sn.id desc";
					List<Map<String,Object>> list = executeRepository.findAll(query);
					if(list.size()>0) {
						if(status.equalsIgnoreCase(String.valueOf(list.get(0).get("status")))) {
							flag=false;
						}
						portId=Long.parseLong(String.valueOf(list.get(0).get("portId")));
					}
				}
//				else if(statusNoti!=null && (statusNoti.getConnectorId()==0)){
//					String sql = "select time from ocpp_settings where orgId = 1";
//					long value = Long.valueOf(executeRepository.getRecordBySqlStr(sql,"time"));
//					
//					String query="select status from chargerActivities where stationId='"+stnId+"' and portId=0 and status='"+status+"' and DATEDIFF(MINUTE,isNull(statusDate,GETUTCDATE()),GETUTCDATE())<'"+value+"'";
//					List<Map<String,Object>> list = executeRepository.findAll(query);
//					if(list.size()>0) {
//						flag=false;
//					}else {
//						String update="update chargerActivities set status='"+status+"',statusDate='"+Utils.getUTCDateString()+"',portId=0 where stationId='"+stnId+"'";
//						executeRepository.update(update);
//					}
//				}
//				if(zeroFlag && status.equalsIgnoreCase("Available")) {
//					String sql = "select time from ocpp_settings where orgId = 1";
//					long value = Long.valueOf(executeRepository.getRecordBySqlStr(sql,"time"));
//					
//					String query="select status from chargerActivities where stationId='"+stnId+"' and portId=0 and status='"+status+"' and DATEDIFF(MINUTE,isNull(statusDate,GETUTCDATE()),GETUTCDATE())<'"+value+"'";
//					List<Map<String,Object>> list = executeRepository.findAll(query);
//					if(list.size()>0) {
//						flag=true;
//					}
//				}
				if(!flag) {
					String query="select transactionId from ocpp_TransactionData where  portId='"+portId+"'";
					List<Map<String,Object>> list = executeRepository.findAll(query);
					if(list.size()==0) {
						flag=false;
						String query1="select transactionId from ocpi_TransactionData where  portId='"+portId+"'";
						List<Map<String,Object>> list1 = executeRepository.findAll(query1);
						if(list1.size()>0) {
							flag=true;
						}
					}else {
						flag=true;
					}
				}
			}else if(req.equalsIgnoreCase("StopTransaction")) {
				
				String lm="select id from station where id="+stnId+" and ampFlag=1";
				List<Map<String,Object>> lmList=executeRepository.findAll(lm);
				if(lmList.size()>0) {
					flag=true;
				}else {
					StopTransaction stopTransaction=finalData.getStopTransaction();
					String query="select transactionId from ocpp_TransactionData where  transactionId='"+stopTransaction.getTransactionId()+"'";
					List<Map<String,Object>> list = executeRepository.findAll(query);
					if(list.size()==0) {
						flag=false;
						String query1="select transactionId from ocpi_TransactionData where  transactionId='"+stopTransaction.getTransactionId()+"'";
						List<Map<String,Object>> list1 = executeRepository.findAll(query1);
						if(list1.size()>0) {
							flag=true;
						}
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public List<Map<String, Object>> getFleetSessionIdByPortId(long portId) {
		List<Map<String, Object>> lsMap = new ArrayList<>();
		try {
			String queryForTransactionId = "select isnull(transactionId,0) as transactionId from fleet_sessions where  portId ="+ portId+" and status ='Active'" ;
			lsMap = generalDao.getMapData(queryForTransactionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsMap;
	}
	@Override
	public List<Map<String, Object>> getStationRefNo(long stationId) {
		List<Map<String, Object>> mapData=new ArrayList<>();
		try {
			String ampId = "Select referNo, chagerType  from station where id =  " + stationId;
			mapData = generalDao.getMapData(ampId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapData;
	}

	@Override
	public Map<String, Object> getPricingDetailsByStnId(long stationId, long tariffid) {
		Map<String,Object> map = new HashMap<>();
		try {
		//	String chargerType = String.valueOf(sTxn.getStnObj().get("chargerType")).contains("DC") == true ? "DC" : "AC";
			logger.info("stationId:"+stationId);
			logger.info("tariff_Id:"+tariffid);
		    if(tariffid>0) {
		    	//Long tariffId = tariff_Id;
		    	logger.info("TariffId");
				String prices = "DECLARE @json nvarchar(max); WITH src (n) AS (	"
						+ "	select distinct t.id as tariffId,t.max_price_id,t.min_price_id,t.name as tariffName,CONVERT(VARCHAR,t.start_date_time, 127) + 'Z' as startTime,CONVERT(VARCHAR,t.end_date_time, 127) + 'Z' as endTime, 	"
						+ "	cost_info=(	select (JSON_QUERY(( select "
						+ "	(JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' and tet.name='Standard' and tp.type='Time' for json path, WITHOUT_ARRAY_WRAPPER))) as 'time', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' and tet.name='Standard' and tp.type='energy' for json path, WITHOUT_ARRAY_WRAPPER)))as 'energy', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' and tet.name='Standard' and tp.type='flat' for json path, WITHOUT_ARRAY_WRAPPER))) as 'flat', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' and tet.name='Standard' and tp.type='parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'standard', "
						+ " (JSON_QUERY((select (JSON_QUERY((select LEFT(tp.price , 10) as price,"+0+" as idleBillCap,tp.step_size as step,tp.type,LEFT(tr.graceperiod,10) as gracePeriod,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' and tet.name='Aditional' and tr.restrictionType='idle charge' for json path, WITHOUT_ARRAY_WRAPPER))) as 'idleCharge', "
						+ " (JSON_QUERY((select LEFT(tp.vat , 10) as 'percnt',tp.type,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' and tet.name='Aditional' and tr.restrictionType='Rate Rider' for json path, WITHOUT_ARRAY_WRAPPER))) as 'rateRider', "
						+ " tax=(select LEFT(tp.vat , 10) as 'percnt',tp.type as 'name',tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' and tet.name='Aditional' and tr.restrictionType='tax' for json path) for json path, WITHOUT_ARRAY_WRAPPER))) as 'aditional' for json path) "
						+ " from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffid+"' for json path) SELECT @json = src.n FROM src  SELECT @json as 'prices'";
				Map<String, Object> pricesObj = executeRepository.findMap(prices);
				logger.info("prices data : "+pricesObj);
				map=pricesObj;
		    }
		    else {
		    	logger.info("stationid with TariffId");
		    String tariffProfileQuery = "select st.tariffId,t.max_price_id,t.min_price_id,t.name as tariffName,t.start_date_time,t.end_date_time from station_in_tariff st inner join tariff t on t.id=st.tariffId where st.stationId='"+stationId+"' order by st.id desc";
			logger.info("tariff tariffProfileQuery : "+tariffProfileQuery);
			Map<String, Object> tariffs = executeRepository.findMap(tariffProfileQuery);
			logger.info("tariffId : "+tariffs);
		   
			if(!tariffs.isEmpty()) {
				Long tariffId = Long.valueOf(String.valueOf(tariffs.get("tariffId")));
				String prices = "DECLARE @json nvarchar(max); WITH src (n) AS (	"
						+ "	select distinct t.id as tariffId,t.max_price_id,t.min_price_id,t.name as tariffName,CONVERT(VARCHAR,t.start_date_time, 127) + 'Z' as startTime,CONVERT(VARCHAR,t.end_date_time, 127) + 'Z' as endTime, 	"
						+ "	cost_info=(	select (JSON_QUERY(( select "
						+ "	(JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='Time' for json path, WITHOUT_ARRAY_WRAPPER))) as 'time', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='energy' for json path, WITHOUT_ARRAY_WRAPPER)))as 'energy', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='flat' for json path, WITHOUT_ARRAY_WRAPPER))) as 'flat', "
						+ " (JSON_QUERY((select LEFT(tp.price , 10) as price,tp.step_size as step,tp.type,0 as tax_excl,0 as tax_incl from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id  inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Standard' and tp.type='parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'parking' for json path, WITHOUT_ARRAY_WRAPPER))) as 'standard', "
						+ " (JSON_QUERY((select (JSON_QUERY((select LEFT(tp.price , 10) as price,"+0+" as idleBillCap,tp.step_size as step,tp.type,LEFT(tr.graceperiod,10) as gracePeriod,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='idle charge' for json path, WITHOUT_ARRAY_WRAPPER))) as 'idleCharge', "
						+ " (JSON_QUERY((select LEFT(tp.vat , 10) as 'percnt',tp.type,tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='Rate Rider' for json path, WITHOUT_ARRAY_WRAPPER))) as 'rateRider', "
						+ " tax=(select LEFT(tp.vat , 10) as 'percnt',tp.type as 'name',tr.restrictionType from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  inner join tariff_restictions tr on tr.id = te.restrictions where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' and tet.name='Aditional' and tr.restrictionType='tax' for json path) for json path, WITHOUT_ARRAY_WRAPPER))) as 'aditional' for json path) "
						+ " from tariff t inner join tariff_element_type tet on tet.tariff_id=t.id inner join tariff_element te on te.id=tet.element_id inner join tariff_priceComponent tp on tp.element_id=te.id  where '"+Utils.getDate(new Date()) +"' between convert(date,t.start_date_time) and convert(date,t.end_date_time) and t.id='"+tariffId+"' for json path) SELECT @json = src.n FROM src  SELECT @json as 'prices'";
				logger.info("prices query : "+prices);
				Map<String, Object> pricesObj = executeRepository.findMap(prices);
				logger.info("prices data : "+pricesObj);
				map=pricesObj;
			//	sTxn.getTxnData().setBillingCases(sTxn.getTxnData().getBillingCases().equalsIgnoreCase("TOU+Rewards") ? sTxn.getTxnData().getBillingCases() : "TOU");
			//	sTxn.getTxnData().setTariff_prices(String.valueOf(pricesObj.get("prices")));
			//	sTxn.getTxnData().setTariffId(tariffId);
			}
		}
			//else {
		//		sTxn.getTxnData().setBillingCases("Freeven");
		//	}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return map;
	
		
	}
	@Override
	public boolean siteTimingsCheck(long siteId,Date timeStamp) {
		boolean flag=true;		
		try {
			String query="select isNull(OPEN24X7,'1') as OPEN24X7 from site where siteId='"+siteId+"'";
			String timeFlag=executeRepository.getRecordBySqlStr(query,"OPEN24X7");
			logger.info("timeFlag : "+timeFlag);
			if(timeFlag!=null && !timeFlag.equalsIgnoreCase("null") && !Boolean.parseBoolean(timeFlag)) {
				flag=false;
				String time="select CASE "
						+ " WHEN (st.openingTime < st.closingTime) and (GETUTCDATE() >= CAST(CONVERT(varchar, GETUTCDATE(), 23) + ' '+st.openingTime AS datetime2(7)) and GETUTCDATE() <= CAST(CONVERT(varchar, GETUTCDATE(), 23) + ' '+st.closingTime AS datetime2(7))) THEN 'Open' "
						+ " WHEN (st.openingTime > st.closingTime) and (GETUTCDATE() >= CAST(CONVERT(varchar, GETUTCDATE(), 23) + ' '+st.openingTime AS datetime2(7)) OR GETUTCDATE() <= CAST(CONVERT(varchar, GETUTCDATE(), 23) + ' '+st.closingTime AS datetime2(7))) THEN 'Open' "
						+ " WHEN (st.openingTime = st.closingTime) and (GETUTCDATE() >= CAST(CONVERT(varchar, GETUTCDATE(), 23) + ' '+st.openingTime AS datetime2(7)) OR GETUTCDATE() <= CAST(CONVERT(varchar, GETUTCDATE(), 23) + ' '+st.closingTime AS datetime2(7))) THEN 'Open' "
						+ " ELSE 'Closed' END siteStatus from site_timing st inner join site_timing_days std on std.siteTimingId=st.id where st.siteId='"+siteId+"' and std.dayId = DATEPART(WEEKDAY,GETUTCDATE())";
				
				logger.info("time query : "+time);
				List<Map<String,Object>> list=executeRepository.findAll(time);
				for(Map<String,Object> map : list) {
					if(String.valueOf(map.get("siteStatus")).equalsIgnoreCase("Open")) {
						flag = true;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
