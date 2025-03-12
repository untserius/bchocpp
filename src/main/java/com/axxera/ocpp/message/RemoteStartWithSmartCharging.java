package com.axxera.ocpp.message;

import java.util.ArrayList;
import java.util.List;

import com.axxera.ocpp.model.ocpp.CSChargingProfiles;

public class RemoteStartWithSmartCharging {

	
	private String idTag;

	private long userId;

	private long connectorId;

	private String stationRefNum;

	private String status;
	
	private String version;
	
	private long orgId;
	

	private List<CSChargingProfiles> csChargingProfiles = new ArrayList<CSChargingProfiles>();


	public String getIdTag() {
		return idTag;
	}


	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public long getConnectorId() {
		return connectorId;
	}


	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}


	public String getStationRefNum() {
		return stationRefNum;
	}


	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public long getOrgId() {
		return orgId;
	}


	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}


	public List<CSChargingProfiles> getCsChargingProfiles() {
		return csChargingProfiles;
	}


	public void setCsChargingProfiles(List<CSChargingProfiles> csChargingProfiles) {
		this.csChargingProfiles = csChargingProfiles;
	}


	@Override
	public String toString() {
		return "RemoteStartWithSmartCharging [idTag=" + idTag + ", userId=" + userId + ", connectorId=" + connectorId
				+ ", stationRefNum=" + stationRefNum + ", status=" + status + ", version=" + version + ", orgId="
				+ orgId + ", csChargingProfiles=" + csChargingProfiles + "]";
	}
	
	
}
