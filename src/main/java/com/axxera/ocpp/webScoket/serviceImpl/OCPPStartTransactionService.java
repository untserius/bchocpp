package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.OCPPStartTransaction;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.webSocket.service.StationService;


@Service
public class OCPPStartTransactionService {

	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	public boolean startTransaction(String uniqueID, long connectorId, String idTag, double meterStart,long reservationId, Date timestamp, String sessionId,
			long clientId, long userId, long transactionId, boolean offlineFlag,String emailId,String customerId,String transactionStatus,double reservationFee,
			Date plugInStart,String paymentType,String rewardType,boolean selfCharging)
			 {
		try {
			
			OCPPStartTransaction startTrans = new OCPPStartTransaction();
			startTrans.setConnectorId(connectorId);
			startTrans.setEndTime(timestamp);
			startTrans.setIdTag(idTag);
			startTrans.setMeterStart(meterStart);
			startTrans.setReservationId(reservationId);
			startTrans.setSessionId(sessionId);
			startTrans.setStationId(clientId);
			startTrans.setTimeStamp(timestamp);
			startTrans.setTransactionId(transactionId);
			startTrans.setUserId(userId);
			startTrans.setOfflineFlag(offlineFlag);
			startTrans.setEmailId(emailId);
			startTrans.setCustomerId(customerId);
			startTrans.setTransactionStatus(transactionStatus);
			startTrans.setRequestedID(uniqueID);
			startTrans.setReservationFee(reservationFee);
			startTrans.setPlugInStartTime(plugInStart);
			startTrans.setPlugInEndTime(timestamp);
			startTrans.setPaymentType(paymentType);
			startTrans.setRewardType(rewardType);
			startTrans.setSelfCharging(selfCharging);
			generalDao.save(startTrans);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public  OCPPStartTransaction getStartTrans(long clientId, long connectorId, long sessionId)  {
		OCPPStartTransaction findOne = null;
		try {
			// TODO Auto-generated method stub

			findOne = generalDao.findOne("From OCPPStartTransaction Where stationId=" + clientId + " AND connectorId="
					+ connectorId + " AND sessionId=" + sessionId, new OCPPStartTransaction());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return findOne;
	}
	
	
	public boolean getOfflineFlag(String timeStamp) {
		try {
			String singleRecord = generalDao.getSingleRecord("SELECT DATEDIFF(minute,'"+timeStamp+"',GETUTCDATE())");
			
			int parseInt = Integer.parseInt(singleRecord);
			
			if(parseInt>10)
				return true;
			
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
		
	public OCPPStartTransaction getStartTransaction(Long transactionId,Long connectorId,Long stationId)  {
		//return generalDao.findOneBySQLQuery("select * from ocpp_startTransaction where transactionId="+transactionId+" and connectorId="+connectorId+" and stationId="+stationId+"  and transactionStatus='Accepted' order by id desc" , new OCPPStartTransaction());
		try {
			return generalDao.findOne("FROM OCPPStartTransaction OS WHERE OS.transactionId="+transactionId+" and OS.connectorId="+connectorId+" and OS.stationId="+stationId+"  and OS.transactionStatus='Accepted' order by OS.id desc" , new OCPPStartTransaction());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
		
	public OCPPStartTransaction getStartTransactionWithRfid(Long transactionId,Long stationId)  {
		try {
			return generalDao.findOneBySQLQuery("select * from ocpp_startTransaction where transactionId="+transactionId+" and stationId="+stationId+" order by id desc" , new OCPPStartTransaction());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public double getReservationFeeForBill(Long stnUniId,long portUniId,long
	 * userId,Map<String,Object>) { double reservationFee = 0.00; try {
	 * 
	 * if(userReserved) { reservationFee =
	 * Double.parseDouble(String.valueOf(stationService.getReservationFee(stnUniId))
	 * ); } }catch (Exception e) { e.printStackTrace(); } return reservationFee; }
	 */
	public Map<String,Object> getSessionData(Long transactionId){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			String query="select isnull(s.cost,'0.0') as finalEnergyCost1,isnull(s.cost2,'0.0') as finalEnergyCost2,isnull(s.finalCostInSlcCurrency,'0.0') as finalCost,isnull(s.portPrice,'0.0') as portPrice,"
					+ " isnull(s.portPriceUnit,'kWh') as portPriceUnit,isnull(s.kilowattHoursUsed,'0.0') as kilowattHoursUsed,"
					+ " ROUND(isnull(s.sessionElapsedInMin,'0.0'), 2) as sessionElapsedInMin,port_id as portId from session s inner join ocpp_startTransaction os on s.sessionId=os.sessionId where os.transactionId='"+transactionId+"'";
			List list=executeRepository.findAll(query);
			if(list.size()>0&&list!=null) {
				map=executeRepository.findAll(query).get(0);
			}else {
				map.put("finalCost", "0.0");
				map.put("portPrice", "0.0");
				map.put("portPriceUnit", "kWh");
				map.put("kilowattHoursUsed", "0.0");
				map.put("sessionElapsedInMin", "0.0");
				map.put("portId", "0");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return map;
	}
		
}
