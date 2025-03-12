package com.axxera.ocpp.forms;

public class TransactionForm {
	
	private String message="";
	
	private String clientId="0";

	private String messageType="";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return "TransactionForm [message=" + message + ", clientId=" + clientId + ", messageType=" + messageType + "]";
	}
	
	
}
