package com.axxera.ocpp.message;

public class Datatransfer {

	private long stationId;

	private String vendorId;
	
	private String messageId;

	private Object responseData;
	
	private DataPojo data;


	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public DataPojo getData() {
		return data;
	}

	public void setData(DataPojo data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Datatransfer [stationId=" + stationId + ", vendorId=" + vendorId + ", messageId=" + messageId
				+ ", responseData=" + responseData + ", data=" + data + "]";
	}


}
