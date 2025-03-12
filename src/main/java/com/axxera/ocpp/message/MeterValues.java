package com.axxera.ocpp.message;

import java.util.ArrayList;
import java.util.List;

public class MeterValues {

	private long connectorId;

	private long transactionId;
	
	private String timestampStr;

	private List<MeterValue> meterValues = new ArrayList<MeterValue>();

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public List<MeterValue> getMeterValues() {
		return meterValues;
	}

	public void setMeterValues(List<MeterValue> meterValues) {
		this.meterValues = meterValues;
	}

	public String getTimestampStr() {
		return timestampStr;
	}

	public void setTimestampStr(String timestampStr) {
		this.timestampStr = timestampStr;
	}

	@Override
	public String toString() {
		return "MeterValues [connectorId=" + connectorId + ", transactionId=" + transactionId + ", timestampStr="
				+ timestampStr + ", meterValues=" + meterValues + "]";
	}

	

}
