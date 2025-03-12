package com.axxera.ocpp.charger.forms;

public class startTxnResponse {

	private String status;
	private String parentIdTag;
	private int transactionId;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getParentIdTag() {
		return parentIdTag;
	}
	public void setParentIdTag(String parentIdTag) {
		this.parentIdTag = parentIdTag;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "startTxnResponse [status=" + status + ", parentIdTag=" + parentIdTag + ", transactionId="
				+ transactionId + "]";
	}
}
