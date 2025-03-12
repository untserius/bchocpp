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
//@Indexed
@Table(name = "account_transaction")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "account" })
public class AccountTransactions extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "createTimeStamp", length = 10)
	private Date createTimeStamp;
	private double amtDebit;
	private double amtCredit;
	private double currentBalance;
	private String status;
	private String comment;
	private String oldRefId;
	private Accounts account;
	private String transactionType;
	private String currencyType;
	private String transactionId;
	private double currencyRate;
	private String currencySymbol;
	private Date lastUpdatedTime;

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public double getAmtDebit() {
		return amtDebit;
	}

	public void setAmtDebit(double amtDebit) {
		this.amtDebit = amtDebit;
	}

	public double getAmtCredit() {
		return amtCredit;
	}

	public void setAmtCredit(double amtCredit) {
		this.amtCredit = amtCredit;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(name = "account_id")
	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}
	public String getOldRefId() {
		return oldRefId;
	}

	public void setOldRefId(String oldRefId) {
		this.oldRefId = oldRefId;
	}

	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
}