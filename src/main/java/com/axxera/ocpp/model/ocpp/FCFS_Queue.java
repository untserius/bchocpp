package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "fcfs_queue")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", })
public class FCFS_Queue extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private long stationId;
	private long portId;
	private long networkId;
	private boolean flag;
	private Date startTime;
	private boolean meterValueFlag;
	
	public FCFS_Queue() {
		// TODO Auto-generated constructor stub
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
	public long getNetworkId() {
		return networkId;
	}
	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date string) {
		this.startTime = string;
	}
	public boolean isMeterValueFlag() {
		return meterValueFlag;
	}
	public void setMeterValueFlag(boolean meterValueFlag) {
		this.meterValueFlag = meterValueFlag;
	}
	@Override
	public String toString() {
		return "FCFS_Queue [stationId=" + stationId + ", portId=" + portId + ", networkId=" + networkId + ", flag="
				+ flag + ", startTime=" + startTime + ", meterValueFlag=" + meterValueFlag + "]";
	}
	
	
	
}
