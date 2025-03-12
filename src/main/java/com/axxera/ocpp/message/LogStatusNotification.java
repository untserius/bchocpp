package com.axxera.ocpp.message;

public class LogStatusNotification {
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
		return "LogStatusNotification [requestId=" + requestId + ", status=" + status + "]";
	}
}
