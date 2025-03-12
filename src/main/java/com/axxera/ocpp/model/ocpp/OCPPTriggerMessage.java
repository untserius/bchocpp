package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_triggerMessage")
public class OCPPTriggerMessage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private int count;

	private long stationId;

	private String status;

	private String triggerMessage;

	
	
	
	public OCPPTriggerMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OCPPTriggerMessage(int count, long stationId, String status, String triggerMessage) {
		super();
		this.count = count;
		this.stationId = stationId;
		this.status = status;
		this.triggerMessage = triggerMessage;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTriggerMessage() {
		return triggerMessage;
	}

	public void setTriggerMessage(String triggerMessage) {
		this.triggerMessage = triggerMessage;
	}

	@Override
	public String toString() {
		return "OCPPTriggerMessage [count=" + count + ", stationId=" + stationId + ", status=" + status
				+ ", triggerMessage=" + triggerMessage + "]";
	}

}
