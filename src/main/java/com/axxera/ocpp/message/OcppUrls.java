package com.axxera.ocpp.message;

public class OcppUrls {

	private long stationId;

	private String url;

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "OcppUrls [stationId=" + stationId + ", url=" + url + "]";
	}
	

}
