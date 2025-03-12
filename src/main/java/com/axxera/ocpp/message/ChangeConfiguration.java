package com.axxera.ocpp.message;


public class ChangeConfiguration {

	private long stationId;
	
	private String key;
	
	private String value;
	
	private String priceText;
	private String priceTextOffline;
    private ChargingPrice chargingPrice;
	
	public long getStationId() {
		return stationId;
	}



	public void setStationId(long stationId) {
		this.stationId = stationId;
	}



	public String getKey() {
		return key;
	}



	public void setKey(String key) {
		this.key = key;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}
    


	public String getPriceText() {
		return priceText;
	}



	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}



	public String getPriceTextOffline() {
		return priceTextOffline;
	}



	public void setPriceTextOffline(String priceTextOffline) {
		this.priceTextOffline = priceTextOffline;
	}



	public ChargingPrice getChargingPrice() {
		return chargingPrice;
	}



	public void setChargingPrice(ChargingPrice chargingPrice) {
		this.chargingPrice = chargingPrice;
	}



	@Override
	public String toString() {
		return "ChangeConfiguration [stationId=" + stationId + ", key=" + key + ", value=" + value + ", priceText="
				+ priceText + ", priceTextOffline=" + priceTextOffline + ", chargingPrice=" + chargingPrice + "]";
	}

	
}
