package com.axxera.ocpp.model.ocpp;

public class GetCompositeSchedule {
	
	private long stationId;
	private String duration;
	private long connectorId;
	private String chargingRateUnit;
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	@Override
	public String toString() {
		return "GetCompositeSchedule [stationId=" + stationId + ", duration=" + duration + ", connectorId="
				+ connectorId + ", chargingRateUnit=" + chargingRateUnit + "]";
	}

	

}
