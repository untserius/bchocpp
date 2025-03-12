package com.axxera.ocpp.message;

public class Status {

	private String status;
	
	private String fileName;
	
	private String listVersion;
	
	private String data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;

	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getListVersion() {
		return listVersion;
	}

	public void setListVersion(String listVersion) {
		this.listVersion = listVersion;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Status [status=" + status + ", fileName=" + fileName + ", listVersion=" + listVersion + ", data=" + data
				+ "]";
	}

}
