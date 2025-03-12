package com.axxera.ocpp.message;

import java.util.Set;

public class HeartBeatChangeConfiguration {

	private String stationId;

	
	
	private String key;
	
	private long value;


	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "HeartBeatChangeConfiguration [stationId=" + stationId + ", key=" + key + ", value=" + value + "]";
	}

}
