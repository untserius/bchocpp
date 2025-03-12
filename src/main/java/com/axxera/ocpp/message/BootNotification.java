package com.axxera.ocpp.message;

public class BootNotification {

	private String chargePointModel;

	private String chargePointVendor;
	private String chargePointSerialNumber;
	
	private String firmwareVersion;
	
	private String chargeBoxSerialNumber;
	

	public String getChargeBoxSerialNumber() {
		return chargeBoxSerialNumber;
	}

	public void setChargeBoxSerialNumber(String chargeBoxSerialNumber) {
		this.chargeBoxSerialNumber = chargeBoxSerialNumber;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
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

	@Override
	public String toString() {
		return "BootNotification [chargePointModel=" + chargePointModel + ", chargePointVendor=" + chargePointVendor
				+ ", chargePointSerialNumber=" + chargePointSerialNumber + ", firmwareVersion=" + firmwareVersion
				+ ", chargeBoxSerialNumber=" + chargeBoxSerialNumber + "]";
	}

}
