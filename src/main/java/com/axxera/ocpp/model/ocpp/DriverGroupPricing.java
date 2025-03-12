package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "driver_group_price")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "driverProfileGroup" })
public class DriverGroupPricing extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String chargerType;

	private double transactionFee;

	private double vendingPrice;

	private String vendingPriceUnit;

	private DriverProfileGroup driverProfileGroup;
	
    private double vendingPricePerUnit1;
	
	private double vendingPricePerUnit2;
	
	private String vendingPriceUnit1;
	
	private String vendingPriceUnit2;
	
	public DriverGroupPricing(Object chargerType, Object transactionFee, Object vendingPrice, Object vendingPriceUnit) {
		super();
		this.chargerType = (String) chargerType;
		this.transactionFee = (double) transactionFee;
		this.vendingPrice = (double) vendingPrice;
		this.vendingPriceUnit = (String) vendingPriceUnit;
	}

	public DriverGroupPricing() {
		super();
	}

	public String getChargerType() {
		return chargerType;
	}

	public void setChargerType(String chargerType) {
		this.chargerType = chargerType;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public double getVendingPrice() {
		return vendingPrice;
	}

	public void setVendingPrice(double vendingPrice) {
		this.vendingPrice = vendingPrice;
	}

	public String getVendingPriceUnit() {
		return vendingPriceUnit;
	}

	public void setVendingPriceUnit(String vendingPriceUnit) {
		this.vendingPriceUnit = vendingPriceUnit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	public DriverProfileGroup getDriverProfileGroup() {
		return driverProfileGroup;
	}

	public void setDriverProfileGroup(DriverProfileGroup driverProfileGroup) {
		this.driverProfileGroup = driverProfileGroup;
	}

	public double getVendingPricePerUnit1() {
		return vendingPricePerUnit1;
	}

	public void setVendingPricePerUnit1(double vendingPricePerUnit1) {
		this.vendingPricePerUnit1 = vendingPricePerUnit1;
	}

	public double getVendingPricePerUnit2() {
		return vendingPricePerUnit2;
	}

	public void setVendingPricePerUnit2(double vendingPricePerUnit2) {
		this.vendingPricePerUnit2 = vendingPricePerUnit2;
	}

	public String getVendingPriceUnit1() {
		return vendingPriceUnit1;
	}

	public void setVendingPriceUnit1(String vendingPriceUnit1) {
		this.vendingPriceUnit1 = vendingPriceUnit1;
	}

	public String getVendingPriceUnit2() {
		return vendingPriceUnit2;
	}

	public void setVendingPriceUnit2(String vendingPriceUnit2) {
		this.vendingPriceUnit2 = vendingPriceUnit2;
	}

	@Override
	public String toString() {
		return "DriverGroupPricing [chargerType=" + chargerType + ", transactionFee=" + transactionFee
				+ ", vendingPrice=" + vendingPrice + ", vendingPriceUnit=" + vendingPriceUnit + ", driverProfileGroup="
				+ driverProfileGroup + ", vendingPricePerUnit1=" + vendingPricePerUnit1 + ", vendingPricePerUnit2="
				+ vendingPricePerUnit2 + ", vendingPriceUnit1=" + vendingPriceUnit1 + ", vendingPriceUnit2="
				+ vendingPriceUnit2 + "]";
	}
	
	

}
