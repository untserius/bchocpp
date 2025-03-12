package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_statusSendingData")
public class OCPPStatusSendingData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String messageType;

	private String portalReqID;

	private long stationId;

	private String status;

	private String sessionId;

	private long userId;

	private String value;

	private long connectorId;

	private String data;

	private String configurationKey;
	
	private Long requestId;
	
	private Date createdDate;
	
	private String client;
	
	
	@Column(name="response",columnDefinition = "varchar(max)")
	private String response;
	

	public OCPPStatusSendingData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
public OCPPStatusSendingData(String messageType, String portalReqID, long stationId, String status,	
		String sessionId, long userId, String value, long connectorId, String data, String configurationKey) {	
	super();	
	this.messageType = messageType;	
	this.portalReqID = portalReqID;	
	this.stationId = stationId;	
	this.status = status;	
	this.sessionId = sessionId;	
	this.userId = userId;	
	this.value = value;	
	this.connectorId = connectorId;	
	this.data = data;	
	this.configurationKey = configurationKey;	
}
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getPortalReqID() {
		return portalReqID;
	}

	public void setPortalReqID(String portalReqID) {
		this.portalReqID = portalReqID;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getConfigurationKey() {
		return configurationKey;
	}

	public void setConfigurationKey(String configurationKey) {
		this.configurationKey = configurationKey;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "OCPPStatusSendingData [messageType=" + messageType + ", portalReqID=" + portalReqID + ", stationId="
				+ stationId + ", status=" + status + ", sessionId=" + sessionId + ", userId=" + userId + ", value="
				+ value + ", connectorId=" + connectorId + ", data=" + data + ", configurationKey=" + configurationKey
				+ ", requestId=" + requestId + ", createdDate=" + createdDate + ", client=" + client + ", response="
				+ response + "]";
	}
}
