package com.axxera.ocpp.message;

public class RequestMessage {

	private String clientId;
	private String message;
	private int textMessage;
	private int binaryMessage;
	private int pingMessage;
	private int pongMessage;
	private int onClose;
	private int onOpen;
	private int onError;
	private int undefinedMessage;
	private long transactionId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(int textMessage) {
		this.textMessage = textMessage;
	}

	public int getBinaryMessage() {
		return binaryMessage;
	}

	public void setBinaryMessage(int binaryMessage) {
		this.binaryMessage = binaryMessage;
	}

	public int getPingMessage() {
		return pingMessage;
	}

	public void setPingMessage(int pingMessage) {
		this.pingMessage = pingMessage;
	}

	public int getPongMessage() {
		return pongMessage;
	}

	public void setPongMessage(int pongMessage) {
		this.pongMessage = pongMessage;
	}

	public int getOnClose() {
		return onClose;
	}

	public void setOnClose(int onClose) {
		this.onClose = onClose;
	}

	public int getOnOpen() {
		return onOpen;
	}

	public void setOnOpen(int onOpen) {
		this.onOpen = onOpen;
	}

	public int getOnError() {
		return onError;
	}

	public void setOnError(int onError) {
		this.onError = onError;
	}

	public int getUndefinedMessage() {
		return undefinedMessage;
	}

	public void setUndefinedMessage(int undefinedMessage) {
		this.undefinedMessage = undefinedMessage;
	}
	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
	@Override
	public String toString() {
		return "RequestMessage [clientId=" + clientId + ", message=" + message + ", textMessage=" + textMessage
				+ ", binaryMessage=" + binaryMessage + ", pingMessage=" + pingMessage + ", pongMessage=" + pongMessage
				+ ", onClose=" + onClose + ", onOpen=" + onOpen + ", onError=" + onError + ", undefinedMessage="
				+ undefinedMessage + ", transactionId=" + transactionId + "]";
	}

	
}
