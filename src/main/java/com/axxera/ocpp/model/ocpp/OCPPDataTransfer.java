package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_dataTransfer")
public class OCPPDataTransfer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long connectorId;
	private String messageId;

	private String mobileNumber;
	private long ccTransactionId;
	private long ocppTransactionId;

	private double price;

	private long vendor;

	@Temporal(TemporalType.DATE)
	@Column(name = "startTimeStamp", length = 10)
	private Date startTimeStamp;

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public long getCcTransactionId() {
		return ccTransactionId;
	}

	public void setCcTransactionId(long ccTransactionId) {
		this.ccTransactionId = ccTransactionId;
	}

	public long getOcppTransactionId() {
		return ocppTransactionId;
	}

	public void setOcppTransactionId(long ocppTransactionId) {
		this.ocppTransactionId = ocppTransactionId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getVendor() {
		return vendor;
	}

	public void setVendor(long vendor) {
		this.vendor = vendor;
	}

	public Date getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Date startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	@Override
	public String toString() {
		return "OCPPDataTransfer [connectorId=" + connectorId + ", messageId=" + messageId + ", mobileNumber="
				+ mobileNumber + ", ccTransactionId=" + ccTransactionId + ", ocppTransactionId=" + ocppTransactionId
				+ ", price=" + price + ", vendor=" + vendor + ", startTimeStamp=" + startTimeStamp + "]";
	}

}
