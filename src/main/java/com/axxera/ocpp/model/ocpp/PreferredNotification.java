package com.axxera.ocpp.model.ocpp;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "preferredNotification")
public class PreferredNotification extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private boolean rfidStatus;

	private boolean sessionEnds;

	private boolean stationAvailability;

	private long userId;

	public boolean isRfidStatus() {
		return rfidStatus;
	}

	public void setRfidStatus(boolean rfidStatus) {
		this.rfidStatus = rfidStatus;
	}

	public boolean isSessionEnds() {
		return sessionEnds;
	}

	public void setSessionEnds(boolean sessionEnds) {
		this.sessionEnds = sessionEnds;
	}

	public boolean isStationAvailability() {
		return stationAvailability;
	}

	public void setStationAvailability(boolean stationAvailability) {
		this.stationAvailability = stationAvailability;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PreferredNotification [rfidStatus=" + rfidStatus + ", sessionEnds=" + sessionEnds
				+ ", stationAvailability=" + stationAvailability + ", userId=" + userId + "]";
	}

}
