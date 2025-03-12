package com.axxera.ocpp.message;

public class SignedFirmwareStatusNotification {
	private int requestId;
	private String status;
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SignedFirmwareStatusNotification [requestId=" + requestId + ", status=" + status + "]";
	}
}
