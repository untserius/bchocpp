package com.axxera.ocpp.message;

import java.util.Date;

import com.axxera.ocpp.utils.Utils;

public class StatusNotification {
	
	private long connectorId=0;

	private String errorCode;

	private String status="Inoperative";

	private Date timestamp = Utils.getUTCDate();
	
	private Date connectedBillTime = Utils.getUTCDate();

	private String vendorErrorCode = "";
	
	private String info = "";
	
	private String chargePointSerialNumber;
	
	private String vendorId ;
	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getChargePointSerialNumber() {
		return chargePointSerialNumber;
	}

	public void setChargePointSerialNumber(String chargePointSerialNumber) {
		this.chargePointSerialNumber = chargePointSerialNumber;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getStatus() {
		if(status==null)
		status="Inoperative";
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public String getVendorErrorCode() {
		return vendorErrorCode;
	}

	public void setVendorErrorCode(String vendorErrorCode) {
		this.vendorErrorCode = vendorErrorCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getConnectedBillTime() {
		return connectedBillTime;
	}

	public void setConnectedBillTime(Date connectedBillTime) {
		this.connectedBillTime = connectedBillTime;
	}

	@Override
	public String toString() {
		return "StatusNotification [connectorId=" + connectorId + ", errorCode=" + errorCode + ", status=" + status
				+ ", timestamp=" + timestamp + ", connectedBillTime=" + connectedBillTime + ", vendorErrorCode="
				+ vendorErrorCode + ", info=" + info + ", chargePointSerialNumber=" + chargePointSerialNumber
				+ ", vendorId=" + vendorId + "]";
	}
	
}
