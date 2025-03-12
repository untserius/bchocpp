package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "soc_priority")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", })
public class SocValue extends BaseEntity{

	
	private static final long serialVersionUID = 1L;

	private long networkId;
	private long stationId;
	private long portId;
	private double ActiveLimitApplied;
	private float socValue;
	private String sessionId;
	private long connectorId;		
	private long transactionId;
	private double powerImportValue;
	private boolean powerImportFlag;
	private boolean socFlag;
	public long getNetworkId() {
		return networkId;
	}
	public void setNetworkId(long networkId) {
		this.networkId = networkId;
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
	public double getActiveLimitApplied() {
		return ActiveLimitApplied;
	}
	public void setActiveLimitApplied(double activeLimitApplied) {
		ActiveLimitApplied = activeLimitApplied;
	}
	public float getSocValue() {
		return socValue;
	}
	public void setSocValue(float socValue) {
		this.socValue = socValue;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public double getPowerImportValue() {
		return powerImportValue;
	}
	public void setPowerImportValue(double powerImportValue) {
		this.powerImportValue = powerImportValue;
	}
	public boolean isPowerImportFlag() {
		return powerImportFlag;
	}
	public void setPowerImportFlag(boolean powerImportFlag) {
		this.powerImportFlag = powerImportFlag;
	}
	public boolean isSocFlag() {
		return socFlag;
	}
	public void setSocFlag(boolean socFlag) {
		this.socFlag = socFlag;
	}
	
}
