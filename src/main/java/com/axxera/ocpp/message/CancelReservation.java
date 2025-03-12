package com.axxera.ocpp.message;

public class CancelReservation {

	private long stationId;
	
	private String stationRefNum;

	private Long reservationId;

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public String getStationRefNum() {
		return stationRefNum;
	}

	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}

	@Override
	public String toString() {
		return "CancelReservation [stationId=" + stationId + ", stationRefNum=" + stationRefNum + ", reservationId="
				+ reservationId + "]";
	}
}
