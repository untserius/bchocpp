package com.axxera.ocpp.message;

public class Authorize {

	private String idTag = "";
	private String paymentmode;
	private String ccdata;
	private String maskeddata;
	private String cardType;
	private String decrypteddataLen;
	private String ksn;
	private String type;
	private String drCode;
	private String pricecode;
	private String vendorid;
	private String paymentcode;
	private String phone;

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public String getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getCcdata() {
		return ccdata;
	}

	public void setCcdata(String ccdata) {
		this.ccdata = ccdata;
	}

	public String getMaskeddata() {
		return maskeddata;
	}

	public void setMaskeddata(String maskeddata) {
		this.maskeddata = maskeddata;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getDecrypteddataLen() {
		return decrypteddataLen;
	}

	public void setDecrypteddataLen(String decrypteddataLen) {
		this.decrypteddataLen = decrypteddataLen;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDrCode() {
		return drCode;
	}

	public void setDrCode(String drCode) {
		this.drCode = drCode;
	}

	public String getPricecode() {
		return pricecode;
	}

	public void setPricecode(String pricecode) {
		this.pricecode = pricecode;
	}

	public String getVendorid() {
		return vendorid;
	}

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public String getPaymentcode() {
		return paymentcode;
	}

	public void setPaymentcode(String paymentcode) {
		this.paymentcode = paymentcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Authorize [idTag=" + idTag + ", paymentmode=" + paymentmode + ", ccdata=" + ccdata + ", maskeddata="
				+ maskeddata + ", cardType=" + cardType + ", decrypteddataLen=" + decrypteddataLen + ", ksn=" + ksn
				+ ", type=" + type + ", drCode=" + drCode + ", pricecode=" + pricecode + ", vendorid=" + vendorid
				+ ", paymentcode=" + paymentcode + ", phone=" + phone + "]";
	}
}
