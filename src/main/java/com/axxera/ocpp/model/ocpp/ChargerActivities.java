package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "chargerActivities")
@JsonIgnoreProperties(ignoreUnknown = true, value = "station")
public class ChargerActivities extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1062177926709570057L;
	
	@OneToOne
    @JoinColumn(unique = true)
	
	private Station station;
	
	private boolean pingFlag;
	
	private boolean changeConfig;
	
	private boolean triggerMessageFlag;
	
	private boolean faultedMailFlag;
	
	private Date faultedMailDate;
	
	private boolean configurationKeys;
	
	private boolean supportMailFlag;
	
    private long portId;
	
	private Date statusDate;
	
	private String status;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = Station.class)
	@JoinColumn(name = "stationId")
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public boolean isPingFlag() {
		return pingFlag;
	}
	public void setPingFlag(boolean pingFlag) {
		this.pingFlag = pingFlag;
	}
	public boolean isChangeConfig() {
		return changeConfig;
	}
	public void setChangeConfig(boolean changeConfig) {
		this.changeConfig = changeConfig;
	}
	public boolean isTriggerMessageFlag() {
		return triggerMessageFlag;
	}
	public void setTriggerMessageFlag(boolean triggerMessageFlag) {
		this.triggerMessageFlag = triggerMessageFlag;
	}
	public boolean isFaultedMailFlag() {
		return faultedMailFlag;
	}
	public void setFaultedMailFlag(boolean faultedMailFlag) {
		this.faultedMailFlag = faultedMailFlag;
	}
	public boolean isConfigurationKeys() {
		return configurationKeys;
	}
	public void setConfigurationKeys(boolean configurationKeys) {
		this.configurationKeys = configurationKeys;
	}
	public boolean isSupportMailFlag() {
		return supportMailFlag;
	}
	public void setSupportMailFlag(boolean supportMailFlag) {
		this.supportMailFlag = supportMailFlag;
	}
	public Date getFaultedMailDate() {
		return faultedMailDate;
	}
	public void setFaultedMailDate(Date faultedMailDate) {
		this.faultedMailDate = faultedMailDate;
	}
	@Column(name="portId",columnDefinition = "numeric(19,0) default 0")
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ChargerActivities [station=" + station + ", pingFlag=" + pingFlag + ", changeConfig=" + changeConfig
				+ ", triggerMessageFlag=" + triggerMessageFlag + ", faultedMailFlag=" + faultedMailFlag
				+ ", faultedMailDate=" + faultedMailDate + ", configurationKeys=" + configurationKeys
				+ ", supportMailFlag=" + supportMailFlag + ", portId=" + portId + ", statusDate=" + statusDate
				+ ", status=" + status + "]";
	}
}
