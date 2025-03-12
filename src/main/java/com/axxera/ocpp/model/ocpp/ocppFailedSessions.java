package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ocppFailedChargingSessions")
public class ocppFailedSessions extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 55846220630225199L;
	
	private long sessionId;
	private String stage;
	private String idTag;
	private long stationId;
	private long portId;
	private String stnRefNum;
	private int connectorId;
	private String reason;
	private String email;
	private Date creationTime;
	private long userId;
	private boolean flag;
	private String requestId;
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getStnRefNum() {
		return stnRefNum;
	}
	public void setStnRefNum(String stnRefNum) {
		this.stnRefNum = stnRefNum;
	}
	public int getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(int connectorId) {
		this.connectorId = connectorId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	@Override
	public String toString() {
		return "ocppFailedSessions [sessionId=" + sessionId + ", stage=" + stage + ", idTag=" + idTag + ", stationId="
				+ stationId + ", portId=" + portId + ", stnRefNum=" + stnRefNum + ", connectorId=" + connectorId
				+ ", reason=" + reason + ", email=" + email + ", creationTime=" + creationTime + ", userId=" + userId
				+ ", flag=" + flag + ", requestId=" + requestId + "]";
	}
}
