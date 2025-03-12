package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_heartBeat")
public class OCPPHeartbeat extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long stationId;

	@Temporal(TemporalType.DATE)
	@Column(name = "heartBeatTime", length = 10)
	private Date heartBeatTime;

	
	private int reportingMailFlag;
	private int triggerMessage=0;
	
	
	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public Date getHeartBeatTime() {
		return heartBeatTime;
	}

	public void setHeartBeatTime(Date heartBeatTime) {
		this.heartBeatTime = heartBeatTime;
	}

	@Column(name="reportingMailFlag",columnDefinition = "numeric(19,0) default 0 not null")
	public int getReportingMailFlag() {
		return reportingMailFlag;
	}

	public void setReportingMailFlag(int reportingMailFlag) {
		this.reportingMailFlag = reportingMailFlag;
	}

	public int getTriggerMessage() {
		return triggerMessage;
	}

	public void setTriggerMessage(int triggerMessage) {
		this.triggerMessage = triggerMessage;
	}

	@Override
	public String toString() {
		return "OCPPHeartbeat [stationId=" + stationId + ", heartBeatTime=" + heartBeatTime + ", reportingMailFlag="
				+ reportingMailFlag + ", triggerMessage=" + triggerMessage + "]";
	}

	

}
