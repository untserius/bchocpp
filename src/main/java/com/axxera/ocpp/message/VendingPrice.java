package com.axxera.ocpp.message;

public class VendingPrice {

	private long StationID;
	private String StationRefNum;
	private double VendingPriceValue;
	private long connectorId;
	private String VendingPriceUnit;
	private int CutailmentValue;

	public long getStationID() {
		return StationID;
	}

	public void setStationID(long stationID) {
		StationID = stationID;
	}

	public double getVendingPriceValue() {
		return VendingPriceValue;
	}

	public void setVendingPriceValue(double vendingPriceValue) {
		VendingPriceValue = vendingPriceValue;
	}

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getVendingPriceUnit() {
		return VendingPriceUnit;
	}

	public void setVendingPriceUnit(String vendingPriceUnit) {
		VendingPriceUnit = vendingPriceUnit;
	}

	public int getCutailmentValue() {
		return CutailmentValue;
	}

	public void setCutailmentValue(int cutailmentValue) {
		CutailmentValue = cutailmentValue;
	}

	public String getStationRefNum() {
		return StationRefNum;
	}

	public void setStationRefNum(String stationRefNum) {
		StationRefNum = stationRefNum;
	}

	@Override
	public String toString() {
		return "VendingPrice [StationID=" + StationID + ", StationRefNum=" + StationRefNum + ", VendingPriceValue="
				+ VendingPriceValue + ", connectorId=" + connectorId + ", VendingPriceUnit=" + VendingPriceUnit
				+ ", CutailmentValue=" + CutailmentValue + "]";
	}
}
