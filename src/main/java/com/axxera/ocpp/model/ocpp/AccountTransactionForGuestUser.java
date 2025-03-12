package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "account_transaction_for_guestUser")
public class AccountTransactionForGuestUser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean flag;
	private String phone;
	private double revenue;
	private String sessionId;
	private long stationId;

	private String oldRefId;

	@Temporal(TemporalType.DATE)
	@Column(name = "time", length = 10)
	private Date time;

	// private Session sessions;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public String getOldRefId() {
		return oldRefId;
	}

	public void setOldRefId(String oldRefId) {
		this.oldRefId = oldRefId;
	}

	@Override
	public String toString() {
		return "AccountTransactionForGuestUser [flag=" + flag + ", phone=" + phone + ", revenue=" + revenue
				+ ", sessionId=" + sessionId + ", stationId=" + stationId + ", oldRefId=" + oldRefId + ", time=" + time
				+ "]";
	}
}
