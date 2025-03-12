package com.axxera.ocpp.message;

public class IdTagInfo {

	private String expiryDate;
	
	private String parentIdTag;
	
	private String status;
	
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getParentIdTag() {
		return parentIdTag;
	}

	public void setParentIdTag(String parentIdTag) {
		this.parentIdTag = parentIdTag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "IdTagInfo [expiryDate=" + expiryDate + ", parentIdTag=" + parentIdTag + ", status=" + status + "]";
	}



}
