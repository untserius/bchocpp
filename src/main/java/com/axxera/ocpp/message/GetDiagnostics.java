package com.axxera.ocpp.message;

import java.util.Date;

public class GetDiagnostics {
	private long stationId;

	private String location;
	private String retries;

	private String retryInterval;

	private String StartDate;
	private String EndDate;

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

	public String getRetries() {
		return retries;
	}

	public void setRetries(String retries) {
		this.retries = retries;
	}

	public String getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(String retryInterval) {
		this.retryInterval = retryInterval;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	@Override
	public String toString() {
		return "GetDiagnostics [stationId=" + stationId + ", location=" + location + ", retries=" + retries
				+ ", retryInterval=" + retryInterval + ", StartDate=" + StartDate + ", EndDate=" + EndDate + "]";
	}

}
