package com.axxera.ocpp.message;

public class FirmwareStatusNotification {

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FirmwareStatusNotification [status=" + status + "]";
	}

}
