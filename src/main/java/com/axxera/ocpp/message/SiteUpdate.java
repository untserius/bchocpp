package com.axxera.ocpp.message;

public class SiteUpdate {
	private long SiteID;
	private String CutailmentValue;
	private String VendingPriceUnit;
	private String VendingPriceValue;

	public long getSiteID() {
		return SiteID;
	}

	public void setSiteID(long siteID) {
		SiteID = siteID;
	}

	public String getCutailmentValue() {
		return CutailmentValue;
	}

	public void setCutailmentValue(String cutailmentValue) {
		CutailmentValue = cutailmentValue;
	}

	public String getVendingPriceUnit() {
		return VendingPriceUnit;
	}

	public void setVendingPriceUnit(String vendingPriceUnit) {
		VendingPriceUnit = vendingPriceUnit;
	}

	public String getVendingPriceValue() {
		return VendingPriceValue;
	}

	public void setVendingPriceValue(String vendingPriceValue) {
		VendingPriceValue = vendingPriceValue;
	}

	@Override
	public String toString() {
		return "SiteUpdate [SiteID=" + SiteID + ", CutailmentValue=" + CutailmentValue + ", VendingPriceUnit="
				+ VendingPriceUnit + ", VendingPriceValue=" + VendingPriceValue + "]";
	}

}
