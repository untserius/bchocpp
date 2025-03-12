package com.axxera.ocpp.model.ocpp;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "creadential")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "account" })
public class Credentials extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rfid;
	private String phone;
	private String rfidHex;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiryDate", length = 10)
	private Date expiryDate;

	private String status;

	private Accounts account;

	private String chargingObject;

	private String oldRefId;

	// private long orgId;

	private String cardType;

	public Credentials() {

	}

	public Credentials(String phone, Accounts account) {
		this.phone = phone;
		this.account = account;
		// this.orgId = orgId;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRfidHex() {
		return rfidHex;
	}

	public void setRfidHex(String rfidHex) {
		this.rfidHex = rfidHex;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(name = "account_id")
	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}

	public String getChargingObject() {
		return chargingObject;
	}

	public void setChargingObject(String chargingObject) {
		this.chargingObject = chargingObject;
	}

	public String getOldRefId() {
		return oldRefId;
	}

	public void setOldRefId(String oldRefId) {
		this.oldRefId = oldRefId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/*
	 * @Column(name = "orgId", length = 10) public long getOrgId() { return orgId; }
	 * public void setOrgId(long orgId) { this.orgId = orgId; }
	 */

	@Override
	public String toString() {
		return "Credentials [rfid=" + rfid + ", phone=" + phone + ", rfidHex=" + rfidHex + ", expiryDate=" + expiryDate
				+ ", status=" + status + ", account=" + account + ", chargingObject=" + chargingObject + ", oldRefId="
				+ oldRefId + ",  cardType=" + cardType + "]";
	}

}
