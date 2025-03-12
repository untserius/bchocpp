package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_remoteStopTransaction")
public class OCPPRemoteStopTransaction extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long transactionId;

	private String status;

	private String portalStation;
	
	private String requestID;

	private String sessionId;
	

	private long stationId;


	public OCPPRemoteStopTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getPortalStation() {
		return portalStation;
	}


	public void setPortalStation(String portalStation) {
		this.portalStation = portalStation;
	}


	@Override
	public String toString() {
		return "OCPPRemoteStopTransaction [transactionId=" + transactionId + ", status=" + status + ", portalStation="
				+ portalStation + ", requestID=" + requestID + ", sessionId=" + sessionId + ", stationId=" + stationId
				+ "]";
	}

}
