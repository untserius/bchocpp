package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_resetretrie")
public class OCPPResetretries extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String status;
	private int count;
	private long stationId;
	private String messageType;

	public OCPPResetretries(String status, int count, long stationId, String messageType) {
		super();
		this.status = status;
		this.count = count;
		this.stationId = stationId;
		this.messageType = messageType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return "OCPPResetretries [status=" + status + ", count=" + count + ", stationId=" + stationId + ", messageType="
				+ messageType + "]";
	}

	public OCPPResetretries() {
		super();
		// TODO Auto-generated constructor stub
	}

}
