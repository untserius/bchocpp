package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_reservation")
public class OCPPReservation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long stationId;

	private long portId;

	@Temporal(TemporalType.DATE)
	@Column(name = "startTime", length = 10)
	private Date startTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "endTime", length = 10)
	private Date endTime;

	private String idTag;

	private long reservationId;

	private String sessionId;

	private String reqId;
	private long userId;

	private boolean flag;
	
	private boolean activeFlag;
	
	private String transactionSessionId;
	
	private boolean cancellationFlag;
	
	private boolean chargerFaultCase;
	
	private double reserveAmount;
	
	private double cancellationFee;

	public OCPPReservation(long stationId, long portId, Date startTime, Date endTime, String idTag,
			long reservationId, String sessionId, long userId, boolean flag) {
		super();
		this.stationId = stationId;
		this.portId = portId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.idTag = idTag;
		this.reservationId = reservationId;
		this.sessionId = sessionId;
		this.userId = userId;
		this.flag = flag;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}


	

	

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
	public OCPPReservation() {
		super();
		// TODO Auto-generated constructor stub
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

	public boolean isChargerFaultCase() {
		return chargerFaultCase;
	}

	public void setChargerFaultCase(boolean chargerFaultCase) {
		this.chargerFaultCase = chargerFaultCase;
	}

	@Override
	public String toString() {
		return "OCPPReservation [stationId=" + stationId + ", portId=" + portId + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", idTag=" + idTag + ", reservationId=" + reservationId + ", sessionId="
				+ sessionId + ", reqId=" + reqId + ", userId=" + userId + ", flag=" + flag + ", activeFlag="
				+ activeFlag + ", transactionSessionId=" + transactionSessionId + ", cancellationFlag="
				+ cancellationFlag + ", chargerFaultCase=" + chargerFaultCase + ", reserveAmount=" + reserveAmount
				+ ", cancellationFee=" + cancellationFee + "]";
	}
}
