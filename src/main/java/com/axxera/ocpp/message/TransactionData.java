package com.axxera.ocpp.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionData {
	
	private List<SampledValue> sampledValue = new ArrayList<SampledValue>();

	private Date  timestamp;

	public List<SampledValue> getSampledValue() {
		return sampledValue;
	}

	public void setSampledValue(List<SampledValue> sampledValue) {
		this.sampledValue = sampledValue;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "TransactionData [sampledValue=" + sampledValue + ", timestamp=" + timestamp + "]";
	}
	
	

}
