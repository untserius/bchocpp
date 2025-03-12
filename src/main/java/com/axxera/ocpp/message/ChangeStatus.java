package com.axxera.ocpp.message;

public class ChangeStatus {

	private long StationID;

	public long getStationID() {
		return StationID;
	}

	public void setStationID(long stationID) {
		StationID = stationID;
	}

	@Override
	public String toString() {
		return "ChangeStatus [StationID=" + StationID + "]";
	}

}
