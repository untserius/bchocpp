package com.axxera.ocpp.model.ocpp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "userPayment")
public class UserPayment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long accountId;
	
	private String uid;

	private String paymentMode;

	private String currencyType;

	private String status;

	private long authorizeAmount;

	private Date authorizeDate;

	private double captureAmount;

	private Date settlementDate;

	private long stationId;

	private long portId;

	private String sessionId;

	private String phone;

	private boolean flag;

	private long orgId;

	private String userType;

	private int count;
	
	private String deviceToken;
	
	private String deviceName;
	
	private String deviceType;
	
	private String email;
	
	private double reversalAmount;
	
	private Set<StripePayment> stripePayment = new HashSet<StripePayment>(0);

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userPayment")
	public Set<StripePayment> getStripePayment() {
		return stripePayment;
	}

	public void setStripePayment(Set<StripePayment> stripePayment) {
		this.stripePayment = stripePayment;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getAuthorizeAmount() {
		return authorizeAmount;
	}

	public void setAuthorizeAmount(long authorizeAmount) {
		this.authorizeAmount = authorizeAmount;
	}

	public Date getAuthorizeDate() {
		return authorizeDate;
	}

	public void setAuthorizeDate(Date authorizeDate) {
		this.authorizeDate = authorizeDate;
	}

	public double getCaptureAmount() {
		return captureAmount;
	}

	public void setCaptureAmount(double captureAmount) {
		this.captureAmount = captureAmount;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getReversalAmount() {
		return reversalAmount;
	}

	public void setReversalAmount(double reversalAmount) {
		this.reversalAmount = reversalAmount;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	@Override
	public String toString() {
		return "UserPayment [accountId=" + accountId + ", uid=" + uid + ", paymentMode="
				+ paymentMode + ", currencyType=" + currencyType + ", status=" + status + ", authorizeAmount="
				+ authorizeAmount + ", authorizeDate=" + authorizeDate + ", captureAmount=" + captureAmount
				+ ", settlementDate=" + settlementDate + ", stationId=" + stationId + ", portId=" + portId
				+ ", sessionId=" + sessionId + ", phone=" + phone + ", flag=" + flag + ", orgId=" + orgId
				+ ", userType=" + userType + ", count=" + count + ", deviceToken=" + deviceToken + ", deviceName="
				+ deviceName + ", deviceType=" + deviceType + ", email=" + email + ", reversalAmount=" + reversalAmount
				+ ", stripePayment=" + stripePayment + "]";
	}


}
