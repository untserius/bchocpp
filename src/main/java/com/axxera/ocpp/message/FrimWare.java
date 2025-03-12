package com.axxera.ocpp.message;

import java.util.Date;

public class FrimWare {

	private long stationId;

	private String location;
	
	private int retries;

	private String retryInterval;

	private String retrieveDate;
	
	private Date storingUtcDate;

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public String getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(String retryInterval) {
		this.retryInterval = retryInterval;
	}

	public String getRetrieveDate() {
		return retrieveDate;
	}

	public void setRetrieveDate(String retrieveDate) {
		this.retrieveDate = retrieveDate;
	}
	
	public Date getStoringUtcDate() {
		return storingUtcDate;
	}

	public void setStoringUtcDate(Date storingUtcDate) {
		this.storingUtcDate = storingUtcDate;
	}

	@Override
	public String toString() {
		return "FrimWare [stationId=" + stationId + ", location=" + location + ", retries=" + retries
				+ ", retryInterval=" + retryInterval + ", retrieveDate=" + retrieveDate + "]";
	}



}
