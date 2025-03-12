package com.axxera.ocpp.message;

public class ChargingPrice {
	
	
	private double kWhPrice;
	
	private double hourPrice;
	
	private double flatFee;
	
	public double getkWhPrice() {
		return kWhPrice;
	}
	public void setkWhPrice(double kWhPrice) {
		this.kWhPrice = kWhPrice;
	}
	public double getHourPrice() {
		return hourPrice;
	}
	public void setHourPrice(double hourPrice) {
		this.hourPrice = hourPrice;
	}
	public double getFlatFee() {
		return flatFee;
	}
	public void setFlatFee(double flatFee) {
		this.flatFee = flatFee;
	}
	@Override
	public String toString() {
		return "ChargingPrice [kWhPrice=" + kWhPrice + ", hourPrice=" + hourPrice + ", flatFee=" + flatFee + "]";
	}
	

}
