package com.axxera.ocpp.message;

public class ChangeAvailability {

	private long connectorId;
	private String type;
	private long stationId;

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	@Override
	public String toString() {
		return "ChangeAvailability [connectorId=" + connectorId + ", type=" + type + ", stationId=" + stationId + "]";
	}

}
