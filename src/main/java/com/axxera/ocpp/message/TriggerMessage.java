package com.axxera.ocpp.message;

public class TriggerMessage {

	private long stationId;

	private Long connectorId;

	private String requestedMessage;

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public Long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(Long connectorId) {
		this.connectorId = connectorId;
	}

	public String getRequestedMessage() {
		return requestedMessage;
	}

	public void setRequestedMessage(String requestedMessage) {
		this.requestedMessage = requestedMessage;
	}

	@Override
	public String toString() {
		return "TriggerMessage [stationId=" + stationId + ", connectorId=" + connectorId + ", requestedMessage="
				+ requestedMessage + "]";
	}



}
