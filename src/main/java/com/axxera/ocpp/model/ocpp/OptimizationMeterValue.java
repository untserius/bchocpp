package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "optimization_meter")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", })
public class OptimizationMeterValue extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private long networkId;
	private long stationId;
	private long portId;
	private String powerActiveImportUnit;
	private double powerActiveImport;
	private double ActiveLimitApplied;
	private boolean powerActiveImportFlag;
	private boolean DemandFlag;
	private String sessionId;
	private long	connectorId	;
	private long transactionId;
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
	public String getPowerActiveImportUnit() {
		return powerActiveImportUnit;
	}
	public void setPowerActiveImportUnit(String powerActiveImportUnit) {
		this.powerActiveImportUnit = powerActiveImportUnit;
	}
	public double getPowerActiveImport() {
		return powerActiveImport;
	}
	public void setPowerActiveImport(double powerActiveImport) {
		this.powerActiveImport = powerActiveImport;
	}
	public double getActiveLimitApplied() {
		return ActiveLimitApplied;
	}
	public void setActiveLimitApplied(double activeLimitApplied) {
		ActiveLimitApplied = activeLimitApplied;
	}
	public boolean isPowerActiveImportFlag() {
		return powerActiveImportFlag;
	}
	public void setPowerActiveImportFlag(boolean powerActiveImportFlag) {
		this.powerActiveImportFlag = powerActiveImportFlag;
	}
	public boolean isDemandFlag() {
		return DemandFlag;
	}
	public void setDemandFlag(boolean demandFlag) {
		DemandFlag = demandFlag;
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


	
	
}
