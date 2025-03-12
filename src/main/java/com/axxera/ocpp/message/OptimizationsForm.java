package com.axxera.ocpp.message;

import java.util.Date;

public class OptimizationsForm {

	private Double limit;	
	private String chargepointId;
	private long connectorId;	
	private String chargingRateUnit;
	private String chargingProfile;
	private String validFrom;
	private String validTo;
	private String unit;
	private String createdTimeStamp;
	private String startSchedular;
	private String TxDefaultProfile;
	private long networkId;
	private long stationId; 
	private long portId;
	private String profileType;
	private String networkProfileId;
	private String requestType;
	
	
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public long getNetworkId() {
		return networkId;
	}
	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}
	public String getNetworkProfileId() {
		return networkProfileId;
	}
	public void setNetworkProfileId(String networkProfileId) {
		this.networkProfileId = networkProfileId;
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	public Double getLimit() {
		return limit;
	}
	public void setLimit(Double limit) {
		this.limit = limit;
	}
	public String getChargepointId() {
		return chargepointId;
	}
	public void setChargepointId(String chargepointId) {
		this.chargepointId = chargepointId;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public String getChargingRateUnit() {
		return chargingRateUnit;
	}
	public void setChargingRateUnit(String chargingRateUnit) {
		this.chargingRateUnit = chargingRateUnit;
	}
	public String getChargingProfile() {
		return chargingProfile;
	}
	public void setChargingProfile(String chargingProfile) {
		this.chargingProfile = chargingProfile;
	}
	
	
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}	
	public String getTxDefaultProfile() {
		return TxDefaultProfile;
	}
	public void setTxDefaultProfile(String txDefaultProfile) {
		TxDefaultProfile = txDefaultProfile;
	}
	
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	
	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	public String getStartSchedular() {
		return startSchedular;
	}
	public void setStartSchedular(String startSchedular) {
		this.startSchedular = startSchedular;
	} 
	
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	@Override
	public String toString() {
		return "OptimizationsForm [limit=" + limit + ", chargepointId=" + chargepointId + ", connectorId=" + connectorId
				+ ", chargingRateUnit=" + chargingRateUnit + ", chargingProfile=" + chargingProfile + ", validFrom="
				+ validFrom + ", validTo=" + validTo + ", unit=" + unit + ", createdTimeStamp=" + createdTimeStamp
				+ ", startSchedular=" + startSchedular + ", TxDefaultProfile=" + TxDefaultProfile
				+ ", networkProfileId=" + networkProfileId + ", stationId=" + stationId + "]";
	}
	
	
	
}