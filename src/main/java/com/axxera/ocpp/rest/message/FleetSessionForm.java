package com.axxera.ocpp.rest.message;

import java.util.Date;

public class FleetSessionForm {
	

	private String	sessionId;
	private String	chargerName;	
	private long	connectorId	;
	private Date	sessionStart;
	private Date	sessionEnd;	
	private String RFID;
	private double activeLimit;
	private String status;
	private Long profileId;	
	private Long stationId;
	private Long portId;
	private long siteId;
	private String siteName;
	private long refSessionId;
	private float peakPower;
	private float socValue;
	private String priority;
	private float powerImportValue;

	private float socValueToStorInSocTable;
	
	private boolean optimizedMetervalue;
	
	public boolean isOptimizedMetervalue() {
		return optimizedMetervalue;
	}
	public void setOptimizedMetervalue(boolean optimizedMetervalue) {
		this.optimizedMetervalue = optimizedMetervalue;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getChargerName() {
		return chargerName;
	}
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public Date getSessionStart() {
		return sessionStart;
	}
	public void setSessionStart(Date sessionStart) {
		this.sessionStart = sessionStart;
	}
	public Date getSessionEnd() {
		return sessionEnd;
	}
	public void setSessionEnd(Date sessionEnd) {
		this.sessionEnd = sessionEnd;
	}
	public String getRFID() {
		return RFID;
	}
	public void setRFID(String rFID) {
		RFID = rFID;
	}
	public double getActiveLimit() {
		return activeLimit;
	}
	public void setActiveLimit(double activeLimit) {
		this.activeLimit = activeLimit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public Long getPortId() {
		return portId;
	}
	public void setPortId(Long portId) {
		this.portId = portId;
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public long getRefSessionId() {
		return refSessionId;
	}
	public void setRefSessionId(long refSessionId) {
		this.refSessionId = refSessionId;
	}
	public float getPeakPower() {
		return peakPower;
	}
	public void setPeakPower(float peakPower) {
		this.peakPower = peakPower;
	}
	public float getSocValue() {
		return socValue;
	}
	public void setSocValue(float socValue) {
		this.socValue = socValue;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public float getPowerImportValue() {
		return powerImportValue;
	}
	public void setPowerImportValue(float powerImportValue) {
		this.powerImportValue = powerImportValue;
	}
	public float getSocValueToStorInSocTable() {
		return socValueToStorInSocTable;
	}
	public void setSocValueToStorInSocTable(float socValueToStorInSocTable) {
		this.socValueToStorInSocTable = socValueToStorInSocTable;
	}
	@Override
	public String toString() {
		return "FleetSessionForm [sessionId=" + sessionId + ", chargerName=" + chargerName + ", connectorId="
				+ connectorId + ", sessionStart=" + sessionStart + ", sessionEnd=" + sessionEnd + ", RFID=" + RFID
				+ ", activeLimit=" + activeLimit + ", status=" + status + ", profileId=" + profileId + ", stationId="
				+ stationId + ", portId=" + portId + ", siteId=" + siteId + ", siteName=" + siteName + ", refSessionId="
				+ refSessionId + ", peakPower=" + peakPower + ", socValue=" + socValue + ", priority=" + priority
				+ ", powerImportValue=" + powerImportValue + ", socValueToStorInSocTable=" + socValueToStorInSocTable
				+ ", optimizedMetervalue=" + optimizedMetervalue + "]";
	}
	
	
	
}
