package com.axxera.ocpp.message;

public class UnlockConnector {

	private long connectorId;

	private String stationRefNum;
	
	private long stationId;

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getStationRefNum() {
		return stationRefNum;
	}

	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	@Override
	public String toString() {
		return "UnlockConnector [connectorId=" + connectorId + ", stationRefNum=" + stationRefNum + ", stationId="
				+ stationId + "]";
	}
}
