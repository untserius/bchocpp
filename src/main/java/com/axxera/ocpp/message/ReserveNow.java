package com.axxera.ocpp.message;

import java.util.Date;

public class ReserveNow {

	private long stationId;
	
	private String stationRefNum;

	private Long ConnectorId;
	
	private String IdTag;

	private long ReservationId;

	private String ExpiryDate;
	
	private String uniqueId;
	
	private Date endTime;
	
	private Date starTime;
	
	private Long userId;
	
	private String sessionId;
	
	private boolean activeFlag;
	
	private String transactionSessionId;
	
	private boolean cancellationFlag;
	
	private double reserveAmount;
	
	private double cancellationFee;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public Long getConnectorId() {
		return ConnectorId;
	}

	public void setConnectorId(Long connectorId) {
		ConnectorId = connectorId;
	}

	public String getIdTag() {
		return IdTag;
	}

	public void setIdTag(String idTag) {
		IdTag = idTag;
	}

	public long getReservationId() {
		return ReservationId;
	}

	public void setReservationId(long reservationId) {
		ReservationId = reservationId;
	}

	public String getExpiryDate() {
		return ExpiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		ExpiryDate = expiryDate;
	}
	

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStarTime() {
		return starTime;
	}

	public void setStarTime(Date starTime) {
		this.starTime = starTime;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getStationRefNum() {
		return stationRefNum;
	}

	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getTransactionSessionId() {
		return transactionSessionId;
	}

	public void setTransactionSessionId(String transactionSessionId) {
		this.transactionSessionId = transactionSessionId;
	}

	public boolean isCancellationFlag() {
		return cancellationFlag;
	}

	public void setCancellationFlag(boolean cancellationFlag) {
		this.cancellationFlag = cancellationFlag;
	}

	public double getReserveAmount() {
		return reserveAmount;
	}

	public void setReserveAmount(double reserveAmount) {
		this.reserveAmount = reserveAmount;
	}

	public double getCancellationFee() {
		return cancellationFee;
	}

	public void setCancellationFee(double cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	@Override
	public String toString() {
		return "ReserveNow [stationId=" + stationId + ", stationRefNum=" + stationRefNum + ", ConnectorId="
				+ ConnectorId + ", IdTag=" + IdTag + ", ReservationId=" + ReservationId + ", ExpiryDate=" + ExpiryDate
				+ ", uniqueId=" + uniqueId + ", endTime=" + endTime + ", starTime=" + starTime + ", userId=" + userId
				+ ", sessionId=" + sessionId + ", activeFlag=" + activeFlag + ", transactionSessionId="
				+ transactionSessionId + ", cancellationFlag=" + cancellationFlag + ", reserveAmount=" + reserveAmount
				+ ", cancellationFee=" + cancellationFee + "]";
	}
}
