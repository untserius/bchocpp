package com.axxera.ocpp.message;

public class ClearCache {

	private long StationID;

	private long ConnectorId;

	public long getStationID() {
		return StationID;
	}

	public void setStationID(long stationID) {
		StationID = stationID;
	}

	public long getConnectorId() {
		return ConnectorId;
	}

	public void setConnectorId(long connectorId) {
		ConnectorId = connectorId;
	}

	@Override
	public String toString() {
		return "ClearCache [ConnectorId=" + ConnectorId + ", StationID=" + StationID + "]";
	}

}
