package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_activeSession")
public class OCPPActiveSession {

	protected Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String sessionId;

	private String  stationRefNum;

	private String sessionStatus;
	
	private String URI;
	
	private long disconnectCount;
	
	private Date lastUpdatedTime;
	
	private String ocppServer;
	
	private long oncloseCount;


	public String getStationRefNum() {
		return stationRefNum;
	}

	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}

	public String getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public long getDisconnectCount() {
		return disconnectCount;
	}

	public void setDisconnectCount(long disconnectCount) {
		this.disconnectCount = disconnectCount;
	}

	public String getOcppServer() {
		return ocppServer;
	}

	public void setOcppServer(String ocppServer) {
		this.ocppServer = ocppServer;
	}
	

	public long getOncloseCount() {
		return oncloseCount;
	}

	public void setOncloseCount(long oncloseCount) {
		this.oncloseCount = oncloseCount;
	}

	@Override
	public String toString() {
		return "OCPPActiveSession [id=" + id + ", sessionId=" + sessionId + ", stationRefNum=" + stationRefNum
				+ ", sessionStatus=" + sessionStatus + ", URI=" + URI + ", disconnectCount=" + disconnectCount
				+ ", lastUpdatedTime=" + lastUpdatedTime + ", ocppServer=" + ocppServer + ", oncloseCount="
				+ oncloseCount + "]";
	}

	

}
