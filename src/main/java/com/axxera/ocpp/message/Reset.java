package com.axxera.ocpp.message;

public class Reset {
	private String type;
	private String stationRefNum;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStationRefNum() {
		return stationRefNum;
	}

	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}

	@Override
	public String toString() {
		return "Reset [type=" + type + ", stationRefNum=" + stationRefNum + "]";
	}



}
