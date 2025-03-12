package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_unlockConnector")
public class OCPPUnlockConnector extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long portId;

	private long stationId;


	private String messageType;
	
	private String reqId;

	public OCPPUnlockConnector(long portId, long stationId, String messageType) {
		super();
		this.portId = portId;
		this.stationId = stationId;
		this.messageType = messageType;
	}


	public long getPortId() {
		return portId;
	}



	public void setPortId(long portId) {
		this.portId = portId;
	}



	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	
	public OCPPUnlockConnector() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OCPPUnlockConnector [portId=" + portId + ", stationId=" + stationId + ", messageType=" + messageType
				+ ", reqId=" + reqId + "]";
	}
	
	

}
