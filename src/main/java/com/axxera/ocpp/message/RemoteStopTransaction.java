package com.axxera.ocpp.message;

public class RemoteStopTransaction {

	private String idTag;

	private long userId;

	private long connectorId;

	private String stationRefNum;
	
	private String version;
	
	private long orgId;
	
	private long transactionId;

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

	
	
	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "RemoteStopTransaction [idTag=" + idTag + ", userId=" + userId + ", connectorId=" + connectorId
				+ ", stationRefNum=" + stationRefNum + ", version=" + version + ", orgId=" + orgId + ", transactionId="
				+ transactionId + "]";
	}
}
