package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stripePayment")
public class StripePayment extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private long userId;

	private String paymentIntentId;

	private String clientSecret;

	private String customerId;

	private String ephemeralKey;
	
	private String transactionId;
	
	private String cardId;
	
	private String last4;
	
	private String brand;
	
	private Long expMonth;
	
	private Long expYear;

	private UserPayment userPayment;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPaymentIntentId() {
		return paymentIntentId;
	}

	public void setPaymentIntentId(String paymentIntentId) {
		this.paymentIntentId = paymentIntentId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEphemeralKey() {
		return ephemeralKey;
	}

	public void setEphemeralKey(String ephemeralKey) {
		this.ephemeralKey = ephemeralKey;
	}

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = UserPayment.class)
	@JoinColumn(name = "userPayment_id")
	public UserPayment getUserPayment() {
		return userPayment;
	}

	public void setUserPayment(UserPayment userPayment) {
		this.userPayment = userPayment;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getLast4() {
		return last4;
	}

	public void setLast4(String last4) {
		this.last4 = last4;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(Long expMonth) {
		this.expMonth = expMonth;
	}

	public Long getExpYear() {
		return expYear;
	}

	public void setExpYear(Long expYear) {
		this.expYear = expYear;
	}

	@Override
	public String toString() {
		return "StripePayment [userId=" + userId + ", paymentIntentId=" + paymentIntentId + ", clientSecret="
				+ clientSecret + ", customerId=" + customerId + ", ephemeralKey=" + ephemeralKey + ", transactionId="
				+ transactionId + ", cardId=" + cardId + ", last4=" + last4 + ", brand=" + brand + ", expMonth="
				+ expMonth + ", expYear=" + expYear + ", userPayment=" + userPayment + "]";
	}

}
