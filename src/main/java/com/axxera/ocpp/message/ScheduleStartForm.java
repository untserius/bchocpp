package com.axxera.ocpp.message;

public class ScheduleStartForm {
	private long profileId; 
	private long stationId;
	private long portId; 
	private String idTag;
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	@Override
	public String toString() {
		return "ScheduleStartForm [profileId=" + profileId + ", stationId=" + stationId + ", portId=" + portId
				+ ", idTag=" + idTag + "]";
	}
	
}
