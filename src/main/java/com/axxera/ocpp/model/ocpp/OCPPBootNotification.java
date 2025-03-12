package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_bootNotification") 
public class OCPPBootNotification extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@OneToOne
    @JoinColumn(unique = true)
	@Column(name = "stationId", length = 50)
	private long stationId;
	private String chargeBoxSerialNumber;
	private String chargePointModel;
	private String chargePointVendor;
	private String chargePointSerialNumber;
	private Date bootTime;
	private String firmwareVersion;
	private String requestId;
	private boolean configurationKeys;
	
	public long getStationId() {
		return stationId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getChargeBoxSerialNumber() {
		return chargeBoxSerialNumber;
	}

	public void setChargeBoxSerialNumber(String chargeBoxSerialNumber) {
		this.chargeBoxSerialNumber = chargeBoxSerialNumber;
	}

	public String getChargePointModel() {
		return chargePointModel;
	}

	public void setChargePointModel(String chargePointModel) {
		this.chargePointModel = chargePointModel;
	}

	public String getChargePointVendor() {
		return chargePointVendor;
	}

	public void setChargePointVendor(String chargePointVendor) {
		this.chargePointVendor = chargePointVendor;
	}

	public String getChargePointSerialNumber() {
		return chargePointSerialNumber;
	}

	public void setChargePointSerialNumber(String chargePointSerialNumber) {
		this.chargePointSerialNumber = chargePointSerialNumber;
	}

	public Date getBootTime() {
		return bootTime;
	}

	public void setBootTime(Date bootTime) {
		this.bootTime = bootTime;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public boolean isConfigurationKeys() {
		return configurationKeys;
	}

	public void setConfigurationKeys(boolean configurationKeys) {
		this.configurationKeys = configurationKeys;
	}

	@Override
	public String toString() {
		return "OCPPBootNotification [stationId=" + stationId + ", chargeBoxSerialNumber=" + chargeBoxSerialNumber
				+ ", chargePointModel=" + chargePointModel + ", chargePointVendor=" + chargePointVendor
				+ ", chargePointSerialNumber=" + chargePointSerialNumber + ", bootTime=" + bootTime
				+ ", firmwareVersion=" + firmwareVersion + ", requestId=" + requestId + ", configurationKeys="
				+ configurationKeys + "]";
	}
}
