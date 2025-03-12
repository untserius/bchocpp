package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_stopTransaction")
public class OCPPStopTransaction extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long connectorId;
	private String idTag;
	private Double meterStop;
	private String reason;
	@Temporal(TemporalType.DATE)
	@Column(name = "timeStamp", length = 10)
	private Date timeStamp;
	private String sessionId;

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public Double getMeterStop() {
		return meterStop;
	}

	public void setMeterStop(Double meterStop) {
		this.meterStop = meterStop;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "OCPPStopTransaction [connectorId=" + connectorId + ", idTag=" + idTag + ", meterStop=" + meterStop
				+ ", reason=" + reason + ", timeStamp=" + timeStamp + ", sessionId=" + sessionId + "]";
	}

}
