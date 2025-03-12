package com.axxera.ocpp.model.ocpp;

public class ClearChargingProfile {
	
	private long stationId;
	private String chargingProfilePurpose;
	private long connectorId;
	private long stackLevel;
	private long chargingProfileId;
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public String getChargingProfilePurpose() {
		return chargingProfilePurpose;
	}
	public void setChargingProfilePurpose(String chargingProfilePurpose) {
		this.chargingProfilePurpose = chargingProfilePurpose;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public long getStackLevel() {
		return stackLevel;
	}
	public void setStackLevel(long stackLevel) {
		this.stackLevel = stackLevel;
	}
	public long getChargingProfileId() {
		return chargingProfileId;
	}
	public void setChargingProfileId(long chargingProfileId) {
		this.chargingProfileId = chargingProfileId;
	}
	@Override
	public String toString() {
		return "ClearChargingProfile [stationId=" + stationId + ", chargingProfilePurpose=" + chargingProfilePurpose
				+ ", connectorId=" + connectorId + ", stackLevel=" + stackLevel + ", chargingProfileId="
				+ chargingProfileId + "]";
	}
	
	
	
}
