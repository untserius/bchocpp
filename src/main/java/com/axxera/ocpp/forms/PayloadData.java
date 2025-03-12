package com.axxera.ocpp.forms;

public class PayloadData {
	
	private String refSessionId;
	private String jsonMessage;
	private long networkId;
	private long stationId;
	private long portId;
	private String sessionId;
	private int transactionId;
	private String idTag;
	private String startTime;
	private String requestType;
	private long connectorId;
	private double powerImportValue;
	private double socValue;
	private String powerImportUnit; 
	private String powerType;
	private String meterEndTime;
	private String portStatus;
	private double reservedPower;
	//private boolean optimizedMetervalue;
	private double powerImportAvg;
	
	
	public String getRefSessionId() {
		return refSessionId;
	}
	public void setRefSessionId(String refSessionId) {
		this.refSessionId = refSessionId;
	}
	public long getNetworkId() {
		return networkId;
	}
	public void setNetworkId(long networkId) {
		this.networkId = networkId;
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public double getPowerImportValue() {
		return powerImportValue;
	}
	public void setPowerImportValue(double powerImportValue) {
		this.powerImportValue = powerImportValue;
	}
	public double getSocValue() {
		return socValue;
	}
	public void setSocValue(double socValue) {
		this.socValue = socValue;
	}
	public String getPowerImportUnit() {
		return powerImportUnit;
	}
	public void setPowerImportUnit(String powerImportUnit) {
		this.powerImportUnit = powerImportUnit;
	}
	public String getPowerType() {
		return powerType;
	}
	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	public String getMeterEndTime() {
		return meterEndTime;
	}
	public void setMeterEndTime(String meterEndTime) {
		this.meterEndTime = meterEndTime;
	}
	public String getPortStatus() {
		return portStatus;
	}
	public void setPortStatus(String portStatus) {
		this.portStatus = portStatus;
	}
	public double getReservedPower() {
		return reservedPower;
	}
	public void setReservedPower(double reservedPower) {
		this.reservedPower = reservedPower;
	}
	
	public String getJsonMessage() {
		return jsonMessage;
	}
	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}
	
	public double getPowerImportAvg() {
		return powerImportAvg;
	}
	public void setPowerImportAvg(double powerImportAvg) {
		this.powerImportAvg = powerImportAvg;
	}
	
	@Override
	public String toString() {
		return "PayloadData [refSessionId=" + refSessionId + ", jsonMessage=" + jsonMessage + ", networkId=" + networkId
				+ ", stationId=" + stationId + ", portId=" + portId + ", sessionId=" + sessionId + ", transactionId="
				+ transactionId + ", idTag=" + idTag + ", startTime=" + startTime + ", requestType=" + requestType
				+ ", connectorId=" + connectorId + ", powerImportValue=" + powerImportValue + ", socValue=" + socValue
				+ ", powerImportUnit=" + powerImportUnit + ", powerType=" + powerType + ", meterEndTime=" + meterEndTime
				+ ", portStatus=" + portStatus + ", reservedPower=" + reservedPower + ", powerImportAvg="
				+ powerImportAvg + "]";
	}
	
}
