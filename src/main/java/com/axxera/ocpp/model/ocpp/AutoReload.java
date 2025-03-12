package com.axxera.ocpp.model.ocpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "autoReload")
public class AutoReload extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "amount", length = 16)
	private double amount;

	@Column(name = "lowBalance", length = 16)
	private double lowBalance;

	private String cardNo;
	
	private String cardId;
	
	private String paymentId;
	
	@Column(name = "accountId", nullable = false)
	private long accountId;
	
	private String currencyType;
	
	@Column(name = "userId", nullable = false)
	private long userId;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getLowBalance() {
		return lowBalance;
	}

	public void setLowBalance(double lowBalance) {
		this.lowBalance = lowBalance;
	}

	
	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Override
	public String toString() {
		return "AutoReload [amount=" + amount + ", lowBalance=" + lowBalance + ", cardNo=" + cardNo + ", cardId="
				+ cardId + ", paymentId=" + paymentId + ", accountId=" + accountId + ", currencyType=" + currencyType
				+ ", userId=" + userId + "]";
	}	
}