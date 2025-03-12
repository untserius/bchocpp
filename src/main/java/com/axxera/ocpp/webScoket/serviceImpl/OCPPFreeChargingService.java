package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.repository.ExecuteRepository;



@Service
public class OCPPFreeChargingService {
	

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(OCPPFreeChargingService.class);
	
	
	public Map<String, Object> getUserType(String phone,long stationId)  {
		Map<String, Object> map = new HashMap<>();
		try {
			String query1 = "select id as preAuthId,authorizeAmount,flag as flagValue,authorizeDate as authorizeTimeStamp,email,paymentMode as paymentMethod,'PayV2' as paymentVersion From userPayment Where phone='" + phone + "' and stationId="+stationId+" and flag='1' and userType = 'GuestUser' order By id desc";
			logger.info("mapData1 : "+query1);
			List<Map<String, Object>> mapData1 = generalDao.getMapData(query1);
			if(mapData1.size() > 0) {
				map = mapData1.get(0);
			}else {
				map.put("paymentVersion", "None");
				map.put("preAuthId", 0);
				map.put("flagValue", "0");
				map.put("paymentMethod", "NA");
				map.put("authorizeTimeStamp", "2001-08-03 10:02:08.000");
				map.put("authorizeAmount", "0");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	public boolean getReservationFlag(long userId,long stationId,Long portId)  {
		boolean val = false;
		try {
			String reservationId = generalDao.getRecordBySql("select reservationId From ocpp_reservation Where userId='" + userId + "' "
					+ "and stationId="+stationId+" and portId="+portId+" and flag=1 and endTime >= GETUTCDATE() order By id desc");
			if(reservationId!=null && reservationId != "null" && reservationId != " " && reservationId != "") {
				val = true;
			}else {
				val = false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	public Map<String,Object> accntsBeanObj(long userid){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String query = "select a.id as accid,a.accountBalance,a.accountName,a.activeAccount,a.autoReload,a.creationDate,a.lowBalanceFlag,a.oldRefId,a.user_id,a.notificationFlag,"
					+ "isnull(a.currencyType,'USD') as currencyType,isnull(a.currencySymbol,'&#36;') as currencySymbol,u.UserId,u.email,convert(varchar,isnull((select DATEADD(HOUR,"
					+ "CAST(SUBSTRING(replace(z.utc_code,'GMT',''),1,3) as int),DATEADD(MINUTE,CAST(SUBSTRING(replace(z.utc_code,'GMT',''),5,2) as int),getutcdate())) from zone z "
					+ "where z.zone_id = p.zone_id),GETUTCDATE()), 9)  + ' ' + isnull((select z.zone_name from zone z where z.zone_id = p.zone_id),'UTC') as userTime,u.uid as uuid from accounts a "
					+ "inner join profile p on a.user_id = p.user_id inner join  Users u  on a.user_id=u.UserId where a.user_id= '"+ userid +"' ";
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("accid", "0");
				map.put("accountBalance", "0");
				map.put("accountName", "");
				map.put("activeAccount", "0");
				map.put("creationDate", "");
				map.put("lowBalanceFlag", "0");
				map.put("oldRefId", "0");
				map.put("user_id", "0");
				map.put("notificationFlag", "0");
				map.put("currencyType", "USD");
				map.put("currencySymbol", "");
				map.put("UserId", "0");
				map.put("email", "");
				map.put("userTime", "");
				map.put("uuid", "");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public void updateStationInoperativeFlag(String stationRefNum, String uniqueID, String status) {
		try {

			if(status.equalsIgnoreCase("Accepted")) {
				Thread.sleep(1000);
				String sqlQuery = "select top 1 case when (isNull(nullif(configurationkey,'' ),'Operative'))='Inoperative' then 1 else 0 end as inOperativeFlag from ocpp_statusSendingData where portalReqID='"+uniqueID+"' order by id desc";
				List<Map<String, Object>> mapData = executeRepository.findAll(sqlQuery);
				Thread.sleep(1000);
				if(mapData.size()>0) {
					String inOperativeFlag = String.valueOf(mapData.get(0).get("inOperativeFlag"));
					if(inOperativeFlag.equalsIgnoreCase("1")) {
						String query = "Update Session set reasonForTer ='EVDisconnected',transactionStatus='completed' where reasonForTer = 'InSession'";
						executeRepository.update(query);
						Thread.sleep(1000);
						String query1 = "update station set inOperativeRequestSent= 1 where referNo='"+stationRefNum+"'";
						executeRepository.update(query1);
					}else if(inOperativeFlag.equalsIgnoreCase("0")){
						String query = "update station set inOperativeRequestSent= 0 where referNo='"+stationRefNum+"'";
						executeRepository.update(query);
					}
				}else {
					String query = "update station set inOperativeRequestSent= 0 where referNo='"+stationRefNum+"'";
					executeRepository.update(query);
				}
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public long getAccIdOnBaseIdtag(String idTag) {
		long userId = 0;
		try {
			String query = generalDao.getRecordBySql("SELECT account_id FROM creadential c where c.phone='"+idTag+"' or " + " c.rfId='"+idTag+"' or c.rfIdHex='"+idTag+"'");
			if(query == null || query.equalsIgnoreCase("null") || query.equalsIgnoreCase(" ")) {
				query = "0";
			}
			userId = Long.valueOf(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	public long getUserIdOnBaseAccId(long accId) {
		long userId = 0;
		try {
			String query = generalDao.getRecordBySql("select a.user_id from accounts a inner join users u on u.userId = a.user_id inner join profile p "
					+ " on p.user_id = a.user_id where a.id = '"+accId+"' and p.status = 'Active'");
			if(query == null || query.equalsIgnoreCase("null") || query.equalsIgnoreCase(" ")) {
				query = "0";
			}
			userId = Long.valueOf(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	public Map<String,Object> getUserIdOnBaseRfid(String rfid) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String query = "select user_id,u.email from preproduction_rfids pr inner join users u on pr.user_Id = u.userId where pr.rfid = '"+rfid+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("user_id", 0);
				map.put("email", "");
			}
			return map;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String,Object> getDriverGroupIdTag(String idTag) {
		Map<String,Object> map = new HashMap<>();
		try {
			String query = "select dgi.rfid,dpg.groupName,CAST(1 as bit) as dgiFlag from [driver_groupRFIDS] dgi inner join driver_profile_groups dpg on dgi.driver_group_id = dpg.id where dgi.rfid = '"+idTag+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("dgiFlag", false);
				map.put("groupName", "");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
