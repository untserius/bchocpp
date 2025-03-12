package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

public interface reservationService {
	
	public abstract void updateReserveWhileAvailCase(Date statusNotifyTime,Long stnUniId,Long portUniId,boolean ampFlag);
	
	public void sendReservationMail(Map<String,Object> accountsObj,long stationId,String stationRefNum,long portId,String reservationFee, Long userId);
	
	public void sendCancelReservationMail(Map<String,Object> accountsObj,String reservationId,String stationRefNum,long portId,String refund, Long userId,long stationId);

	public void sendReservationRefundMail(Map<String, Object> accountsObj, String reservationId, String stationRefNum,
			long portId, String refund, Long userId,long stationId);

	String getReservation(long stationId, long portId, long userId);

	public abstract void reservationPushNotification(Map<String, Object> reserveData, String reason, String stationRefNum);

	void cancelReservationNotification(Map<String, Object> map, String stationRefNum);

	void canncelReservationWhileSNReserved(long portId, Date statusNotifyTime, WebSocketSession session,
			String stationRefNum);

	void cancelReservationWhileSNAvailable(long portId, Date statusNotifyTime, WebSocketSession session,
			String stationRefNum);

	void canncelReservationWhileSNReservedForCloud(long portUniId, Date timestamp, Object object,
			String stationRefNum);

}
