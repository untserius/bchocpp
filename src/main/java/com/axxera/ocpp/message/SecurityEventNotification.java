package com.axxera.ocpp.message;

public class SecurityEventNotification {
	private String type;
	private String timestamp;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "SecurityEventNotification [type=" + type + ", timestamp=" + timestamp + "]";
	}
}
