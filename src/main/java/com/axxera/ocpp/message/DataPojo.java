package com.axxera.ocpp.message;

import java.util.HashMap;

public class DataPojo {

	private String url;

	private String time;

	private String StationMode;
	
	private String idToken;
	
	private String priceText;

	private String info;
	
	private HashMap data;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStationMode() {
		return StationMode;
	}

	public void setStationMode(String stationMode) {
		StationMode = stationMode;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public HashMap getData() {
		return data;
	}

	public void setData(HashMap data) {
		this.data = data;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getPriceText() {
		return priceText;
	}

	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}

	@Override
	public String toString() {
		return "DataPojo [url=" + url + ", time=" + time + ", StationMode=" + StationMode + ", idToken=" + idToken
				+ ", priceText=" + priceText + ", info=" + info + ", data=" + data + "]";
	}
}
