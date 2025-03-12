package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_startTransaction")
public class OCPPStartTransaction extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long connectorId;
	private String idTag;
	private Double meterStart;
	private long reservationId;
	@Temporal(TemporalType.DATE)
	@Column(name = "timeStamp", length = 10)
	private Date timeStamp;
	@Temporal(TemporalType.DATE)
	@Column(name = "endTime", length = 10)
	private Date endTime;
	private String sessionId;
	private long stationId;
	private long userId;
	private long transactionId;
	private boolean offlineFlag;
	private String emailId;
	private String customerId;
	private String transactionStatus;
	private String RequestedID;
	private double reservationFee;
	private Date plugInStartTime;
	private Date plugInEndTime;
	private boolean unPluged;
	private String paymentType;
	private String rewardType;
    private double rewardValue;
	private long orgId;
	private boolean selfCharging;
	
	
	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	@Column(name = "offlineFlag", columnDefinition = "bit not null default 0")
	public boolean isOfflineFlag() {
		return offlineFlag;
	}

	public void setOfflineFlag(boolean offlineFlag) {
		this.offlineFlag = offlineFlag;
	}

	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	

	public OCPPStartTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OCPPStartTransaction(long connectorId, String idTag, Double meterStart, long reservationId, Date timeStamp,
			Date endTime, String sessionId, long stationId, long userId, long transactionId) {
		super();
		this.connectorId = connectorId;
		this.idTag = idTag;
		this.meterStart = meterStart;
		this.reservationId = reservationId;
		this.timeStamp = timeStamp;
		this.endTime = endTime;
		this.sessionId = sessionId;
		this.stationId = stationId;
		this.userId = userId;
		this.transactionId = transactionId;
	}

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public Double getMeterStart() {
		return meterStart;
	}

	public void setMeterStart(Double meterStart) {
		this.meterStart = meterStart;
	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}


	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRequestedID() {
		return RequestedID;
	}

	public void setRequestedID(String requestedID) {
		RequestedID = requestedID;
	}

	public double getReservationFee() {
		return reservationFee;
	}

	public void setReservationFee(double reservationFee) {
		this.reservationFee = reservationFee;
	}

	public Date getPlugInStartTime() {
		return plugInStartTime;
	}

	public void setPlugInStartTime(Date plugInStartTime) {
		this.plugInStartTime = plugInStartTime;
	}

	public Date getPlugInEndTime() {
		return plugInEndTime;
	}

	public void setPlugInEndTime(Date plugInEndTime) {
		this.plugInEndTime = plugInEndTime;
	}
	
	public boolean isUnPluged() {
		return unPluged;
	}

	public void setUnPluged(boolean unPluged) {
		this.unPluged = unPluged;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public double getRewardValue() {
		return rewardValue;
	}

	public void setRewardValue(double rewardValue) {
		this.rewardValue = rewardValue;
	}

	public boolean isSelfCharging() {
		return selfCharging;
	}

	public void setSelfCharging(boolean selfCharging) {
		this.selfCharging = selfCharging;
	}

	@Override
	public String toString() {
		return "OCPPStartTransaction [connectorId=" + connectorId + ", idTag=" + idTag + ", meterStart=" + meterStart
				+ ", reservationId=" + reservationId + ", timeStamp=" + timeStamp + ", endTime=" + endTime
				+ ", sessionId=" + sessionId + ", stationId=" + stationId + ", userId=" + userId + ", transactionId="
				+ transactionId + ", offlineFlag=" + offlineFlag + ", emailId=" + emailId + ", customerId=" + customerId
				+ ", transactionStatus=" + transactionStatus + ", RequestedID=" + RequestedID + ", reservationFee="
				+ reservationFee + ", plugInStartTime=" + plugInStartTime + ", plugInEndTime=" + plugInEndTime
				+ ", unPluged=" + unPluged + ", paymentType=" + paymentType + ", rewardType=" + rewardType
				+ ", rewardValue=" + rewardValue + ", orgId=" + orgId + ", selfCharging=" + selfCharging + "]";
	}
	
	
}
