package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "free_charging")
public class FreeCharging extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long stationId;

	private String idTag;

	@Temporal(TemporalType.DATE)
	@Column(name = "chargingDate", length = 10)
	private Date chargingDate;
	private double duration;
	private long sessionId;

	private long accountId;
	private long portId;

	private long freeHours;

	private boolean flag;

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public Date getChargingDate() {
		return chargingDate;
	}

	public void setChargingDate(Date chargingDate) {
		this.chargingDate = chargingDate;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public long getFreeHours() {
		return freeHours;
	}

	public void setFreeHours(long freeHours) {
		this.freeHours = freeHours;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "FreeCharging [stationId=" + stationId + ", idTag=" + idTag + ", chargingDate=" + chargingDate
				+ ", duration=" + duration + ", sessionId=" + sessionId + ", accountId=" + accountId + ", portId="
				+ portId + ", freeHours=" + freeHours + ", flag=" + flag + "]";
	}

}
