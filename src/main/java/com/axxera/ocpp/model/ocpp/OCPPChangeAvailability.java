package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_changeAvailability")
public class OCPPChangeAvailability extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long stationId;

	private long connectorId;
	private String modeType;

	@Temporal(TemporalType.DATE)
	@Column(name = "startTimeStamp", length = 10)
	private Date startTimeStamp;

	@Column(name="PortalReqID",length=50)
	private String portalReqID;
	
	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public Date getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Date startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public String getPortalReqID() {
		return portalReqID;
	}

	public void setPortalReqID(String portalReqID) {
		this.portalReqID = portalReqID;
	}

	@Override
	public String toString() {
		return "OCPPChangeAvailability [stationId=" + stationId + ", connectorId=" + connectorId + ", modeType="
				+ modeType + ", startTimeStamp=" + startTimeStamp + ", portalReqID=" + portalReqID + "]";
	}

}
