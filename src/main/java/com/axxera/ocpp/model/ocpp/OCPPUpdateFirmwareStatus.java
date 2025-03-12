package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_updateFirmwareStatus")
public class OCPPUpdateFirmwareStatus extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "crtDate", length = 10)
	private Date crtDate;

	private long stationId;

	private String location;

	private int NoOfTimesChargePointDownloaded;

	private String retryIntervalSec;

	public OCPPUpdateFirmwareStatus(Date crtDate, long stationId, String location, int noOfTimesChargePointDownloaded,
			String retryIntervalSec) {
		super();
		this.crtDate = crtDate;
		this.stationId = stationId;
		this.location = location;
		NoOfTimesChargePointDownloaded = noOfTimesChargePointDownloaded;
		this.retryIntervalSec = retryIntervalSec;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNoOfTimesChargePointDownloaded() {
		return NoOfTimesChargePointDownloaded;
	}

	public void setNoOfTimesChargePointDownloaded(int noOfTimesChargePointDownloaded) {
		NoOfTimesChargePointDownloaded = noOfTimesChargePointDownloaded;
	}

	public String getRetryIntervalSec() {
		return retryIntervalSec;
	}

	public void setRetryIntervalSec(String retryIntervalSec) {
		this.retryIntervalSec = retryIntervalSec;
	}

	@Override
	public String toString() {
		return "OCPPUpdateFirmwareStatus [crtDate=" + crtDate + ", stationId=" + stationId + ", location=" + location
				+ ", NoOfTimesChargePointDownloaded=" + NoOfTimesChargePointDownloaded + ", retryIntervalSec="
				+ retryIntervalSec + "]";
	}

	public OCPPUpdateFirmwareStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

}
