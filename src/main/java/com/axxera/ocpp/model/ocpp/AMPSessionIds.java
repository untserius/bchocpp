package com.axxera.ocpp.model.ocpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AMPSessionIds")
public class AMPSessionIds extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="ampSessionId",length=150)
	private String ampSessionId;
	public String getAmpSessionId() {
		return ampSessionId;
	}
	public void setAmpSessionId(String ampSessionId) {
		this.ampSessionId = ampSessionId;
	}

	@Column(name="StationID",length=50)
	private long stationId;
	public void setStationId(long stationId)
	{
		this.stationId=stationId;
	}
	public long getStationId()
	{
		return stationId;
	}
	
	@Column(name="sessionId",length=100)
	private String sessionId;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}

