package com.axxera.ocpp.message;

public class SessionFormForSocketController {
	
	private String stationRefNum;
	private int connectorId;
	private String idTag;
	private long transactionId;
	private double energyDeliveredInKwh;
	private double meterStart;
	private double meterStop;
	private double socStart;
	private double socEnd;
	private String startTimeStamp;
	private String endTimeStamp;
	private double durationInMinutes;
	private String reasonForTermination;
	
	public String getStationRefNum() {
		return stationRefNum;
	}
	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}
	public int getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(int connectorId) {
		this.connectorId = connectorId;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public double getEnergyDeliveredInKwh() {
		return energyDeliveredInKwh;
	}
	public void setEnergyDeliveredInKwh(double energyDeliveredInKwh) {
		this.energyDeliveredInKwh = energyDeliveredInKwh;
	}
	public double getMeterStart() {
		return meterStart;
	}
	public void setMeterStart(double meterStart) {
		this.meterStart = meterStart;
	}
	public double getMeterStop() {
		return meterStop;
	}
	public void setMeterStop(double meterStop) {
		this.meterStop = meterStop;
	}
	public double getSocStart() {
		return socStart;
	}
	public void setSocStart(double socStart) {
		this.socStart = socStart;
	}
	public double getSocEnd() {
		return socEnd;
	}
	public void setSocEnd(double socEnd) {
		this.socEnd = socEnd;
	}
	public String getStartTimeStamp() {
		return startTimeStamp;
	}
	public void setStartTimeStamp(String startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	public String getEndTimeStamp() {
		return endTimeStamp;
	}
	public void setEndTimeStamp(String endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}
	public double getDurationInMinutes() {
		return durationInMinutes;
	}
	public void setDurationInMinutes(double durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
	public String getReasonForTermination() {
		return reasonForTermination;
	}
	public void setReasonForTermination(String reasonForTermination) {
		this.reasonForTermination = reasonForTermination;
	}
	@Override
	public String toString() {
		return "SessionFormForSocketController [stationRefNum=" + stationRefNum + ", connectorId=" + connectorId
				+ ", idTag=" + idTag + ", transactionId=" + transactionId + ", energyDeliveredInKwh="
				+ energyDeliveredInKwh + ", meterStart=" + meterStart + ", meterStop=" + meterStop + ", socStart="
				+ socStart + ", socEnd=" + socEnd + ", startTimeStamp=" + startTimeStamp + ", endTimeStamp="
				+ endTimeStamp + ", durationInMinutes=" + durationInMinutes + ", reasonForTermination="
				+ reasonForTermination + "]";
	}
}
