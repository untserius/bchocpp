package com.axxera.ocpp.model.ocpp;

import java.util.ArrayList;
import java.util.List;

public class SetChargingProfile {

	private long connectorId;

	private String version;
	
	private long orgId;
	
	private long stationId;
	
	
	private List<CSChargingProfiles> csChargingProfiles = new ArrayList<CSChargingProfiles>();

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public List<CSChargingProfiles> getCsChargingProfiles() {
		return csChargingProfiles;
	}

	public void setCsChargingProfiles(List<CSChargingProfiles> csChargingProfiles) {
		this.csChargingProfiles = csChargingProfiles;
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

	
	
	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	@Override
	public String toString() {
		return "SetChargingProfile [connectorId=" + connectorId + ", version=" + version + ", orgId=" + orgId
				+ ", csChargingProfiles=" + csChargingProfiles + "]";
	}

	
	
	
}
