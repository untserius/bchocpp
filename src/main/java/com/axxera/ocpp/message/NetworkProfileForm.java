package com.axxera.ocpp.message;

public class NetworkProfileForm {
	
	private long profileId;
	private long stationId; 
	private long portId;
	private int availablePortCount;
	
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
	
	public int getAvailablePortCount() {
		return availablePortCount;
	}
	public void setAvailablePortCount(int availablePortCount) {
		this.availablePortCount = availablePortCount;
	}
	@Override
	public String toString() {
		return "NetworkProfileForm [profileId=" + profileId + ", stationId=" + stationId + ", portId=" + portId
				+ ", availablePortCount=" + availablePortCount + "]";
	}
	
	
}
