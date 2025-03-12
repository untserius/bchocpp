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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "session")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "port",
		"accountTransaction" })
public class Session extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String sessionId;
	private String customerId;
	@Temporal(TemporalType.DATE)
	@Column(name = "startTimeStamp", length = 10)
	private Date startTimeStamp;

	@Temporal(TemporalType.DATE)
	@Column(name = "modifiedDate", length = 10)
	private Date modifiedDate;

	private double sessionElapsedInMin;
	private double kilowattHoursUsed;
	private double finalCostInSlcCurrency;
	private double cost;
	private String reasonForTer;
	private String emailId;
	private String driverGroupName;
	private boolean startTxnProgress;
	private Port port;
	private String stationMode;
	private String chargerType;
	private Long userId;
	private String currencyType;
	private String idTagProfileName;
	private String profileType;
	private double currencyRate;
	private boolean masterList;
	private String userCurrencyType;
	private AccountTransactions accountTransaction;
	private Date endTimeStamp;
	private Date creationDate;
	private String sessionStatus;//Completed/Accepted
	private String transactionStatus;//going on/completed/pending
	private String transactionType;//Standard/Advanced
	private String txnType;//Internal use
	private double socStartVal;
	private double socEndVal;
	private String preProdSess;
	private String settlement;
	private String paymentMode;
	private boolean inaccurateTxn;
	private boolean successFlag;
    private boolean selfCharging;
    private String rewardType;
    private double rewardValue;
    private double tax1_amount;
    private double tax1_pct;
    private double tax2_amount;
    private double tax2_pct;
    private double tax3_amount;
    private double tax3_pct;
    private String avg_power;
    private double powerActiveImport_value;
    private String sitename; 
    private String siteType;
    private String txnInitiate;
    private long siteId;
	private boolean energyModify;
	private double actualEnergy;

	public boolean isEnergyModify() {
		return energyModify;
	}

	public void setEnergyModify(boolean energyModify) {
		this.energyModify = energyModify;
	}

	public double getActualEnergy() {
		return actualEnergy;
	}

	public void setActualEnergy(double actualEnergy) {
		this.actualEnergy = actualEnergy;
	}
    

	public double getTax1_pct() {
		return tax1_pct;
	}

	public void setTax1_pct(double tax1_pct) {
		this.tax1_pct = tax1_pct;
	}

	public double getTax2_amount() {
		return tax2_amount;
	}

	public void setTax2_amount(double tax2_amount) {
		this.tax2_amount = tax2_amount;
	}

	public double getTax2_pct() {
		return tax2_pct;
	}

	public void setTax2_pct(double tax2_pct) {
		this.tax2_pct = tax2_pct;
	}

	public double getTax3_amount() {
		return tax3_amount;
	}

	public void setTax3_amount(double tax3_amount) {
		this.tax3_amount = tax3_amount;
	}

	public double getTax3_pct() {
		return tax3_pct;
	}

	public void setTax3_pct(double tax3_pct) {
		this.tax3_pct = tax3_pct;
	}

	public double getTax1_amount() {
		return tax1_amount;
	}

	public void setTax1_amount(double tax1_amount) {
		this.tax1_amount = tax1_amount;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "port_id")
	public Port getPort() {
		return port;
	}

	public void setPort(Port port) {
		this.port = port;
	}

	public Date getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(Date endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	public Date getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Date startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getUserCurrencyType() {
		return userCurrencyType;
	}

	public void setUserCurrencyType(String userCurrencyType) {
		this.userCurrencyType = userCurrencyType;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "accountTransaction_id")
	public AccountTransactions getAccountTransaction() {
		return accountTransaction;
	}

	public void setAccountTransaction(AccountTransactions accountTransaction) {
		this.accountTransaction = accountTransaction;
	}

	public double getSessionElapsedInMin() {
		return sessionElapsedInMin;
	}

	public void setSessionElapsedInMin(double sessionElapsedInMin) {
		this.sessionElapsedInMin = sessionElapsedInMin;
	}

	public double getKilowattHoursUsed() {
		return kilowattHoursUsed;
	}

	public void setKilowattHoursUsed(double kilowattHoursUsed) {
		this.kilowattHoursUsed = kilowattHoursUsed;
	}

	public double getFinalCostInSlcCurrency() {
		return finalCostInSlcCurrency;
	}

	public void setFinalCostInSlcCurrency(double finalCostInSlcCurrency) {
		this.finalCostInSlcCurrency = finalCostInSlcCurrency;
	}

	public String getReasonForTer() {
		return reasonForTer;
	}

	public void setReasonForTer(String reasonForTer) {
		this.reasonForTer = reasonForTer;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDriverGroupName() {
		return driverGroupName;
	}

	public void setDriverGroupName(String driverGroupName) {
		this.driverGroupName = driverGroupName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getStationMode() {
		return stationMode;
	}

	public void setStationMode(String stationMode) {
		this.stationMode = stationMode;
	}

	public String getChargerType() {
		return chargerType;
	}

	public void setChargerType(String chargerType) {
		this.chargerType = chargerType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public double getSocStartVal() {
		return socStartVal;
	}

	public void setSocStartVal(double socStartVal) {
		this.socStartVal = socStartVal;
	}

	public double getSocEndVal() {
		return socEndVal;
	}

	public void setSocEndVal(double socEndVal) {
		this.socEndVal = socEndVal;
	}

	public boolean isStartTxnProgress() {
		return startTxnProgress;
	}

	public void setStartTxnProgress(boolean startTxnProgress) {
		this.startTxnProgress = startTxnProgress;
	}

	public String getPreProdSess() {
		return preProdSess;
	}

	public void setPreProdSess(String preProdSess) {
		this.preProdSess = preProdSess;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getIdTagProfileName() {
		return idTagProfileName;
	}

	public void setIdTagProfileName(String idTagProfileName) {
		this.idTagProfileName = idTagProfileName;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public boolean isInaccurateTxn() {
		return inaccurateTxn;
	}

	public void setInaccurateTxn(boolean inaccurateTxn) {
		this.inaccurateTxn = inaccurateTxn;
	}

	public boolean isSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public double getRewardValue() {
		return rewardValue;
	}

	public void setRewardValue(double rewardValue) {
		this.rewardValue = rewardValue;
	}

	public boolean isSelfCharging() {
		return selfCharging;
	}

	public void setSelfCharging(boolean selfCharging) {
		this.selfCharging = selfCharging;
	}

	public boolean isMasterList() {
		return masterList;
	}

	public void setMasterList(boolean masterList) {
		this.masterList = masterList;
	}


	public String getAvg_power() {
		return avg_power;
	}

	public void setAvg_power(String avg_power) {
		this.avg_power = avg_power;
	}

	public double getPowerActiveImport_value() {
		return powerActiveImport_value;
	}

	public void setPowerActiveImport_value(double powerActiveImport_value) {
		this.powerActiveImport_value = powerActiveImport_value;
	}


	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getTxnInitiate() {
		return txnInitiate;
	}

	public void setTxnInitiate(String txnInitiate) {
		this.txnInitiate = txnInitiate;
	}
	

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public Date getModifiedDate() {return modifiedDate;}

	public void setModifiedDate(Date modifiedDate) {this.modifiedDate = modifiedDate;}

	@Override
	public String toString() {
		return "Session{" +
				"sessionId='" + sessionId + '\'' +
				", customerId='" + customerId + '\'' +
				", startTimeStamp=" + startTimeStamp +
				", modifiedDate=" + modifiedDate +
				", sessionElapsedInMin=" + sessionElapsedInMin +
				", kilowattHoursUsed=" + kilowattHoursUsed +
				", finalCostInSlcCurrency=" + finalCostInSlcCurrency +
				", cost=" + cost +
				", reasonForTer='" + reasonForTer + '\'' +
				", emailId='" + emailId + '\'' +
				", driverGroupName='" + driverGroupName + '\'' +
				", startTxnProgress=" + startTxnProgress +
				", port=" + port +
				", stationMode='" + stationMode + '\'' +
				", chargerType='" + chargerType + '\'' +
				", userId=" + userId +
				", currencyType='" + currencyType + '\'' +
				", idTagProfileName='" + idTagProfileName + '\'' +
				", profileType='" + profileType + '\'' +
				", currencyRate=" + currencyRate +
				", masterList=" + masterList +
				", userCurrencyType='" + userCurrencyType + '\'' +
				", accountTransaction=" + accountTransaction +
				", endTimeStamp=" + endTimeStamp +
				", creationDate=" + creationDate +
				", sessionStatus='" + sessionStatus + '\'' +
				", transactionStatus='" + transactionStatus + '\'' +
				", transactionType='" + transactionType + '\'' +
				", txnType='" + txnType + '\'' +
				", socStartVal=" + socStartVal +
				", socEndVal=" + socEndVal +
				", preProdSess='" + preProdSess + '\'' +
				", settlement='" + settlement + '\'' +
				", paymentMode='" + paymentMode + '\'' +
				", inaccurateTxn=" + inaccurateTxn +
				", successFlag=" + successFlag +
				", selfCharging=" + selfCharging +
				", rewardType='" + rewardType + '\'' +
				", rewardValue=" + rewardValue +
				", tax1_amount=" + tax1_amount +
				", tax1_pct=" + tax1_pct +
				", tax2_amount=" + tax2_amount +
				", tax2_pct=" + tax2_pct +
				", tax3_amount=" + tax3_amount +
				", tax3_pct=" + tax3_pct +
				", avg_power='" + avg_power + '\'' +
				", powerActiveImport_value=" + powerActiveImport_value +
				", sitename='" + sitename + '\'' +
				", siteType='" + siteType + '\'' +
				", txnInitiate='" + txnInitiate + '\'' +
				", siteId=" + siteId +
				", energyModify=" + energyModify +
				", actualEnergy=" + actualEnergy +
				'}';
	}
}
