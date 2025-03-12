package com.axxera.ocpp.message;

public class GetConfiguration {

	private long stationID;
	private String key;

	public long getStationID() {
		return stationID;
	}

	public void setStationID(long stationID) {
		this.stationID = stationID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Configuration [stationID=" + stationID + ", key=" + key + "]";
	}

}
