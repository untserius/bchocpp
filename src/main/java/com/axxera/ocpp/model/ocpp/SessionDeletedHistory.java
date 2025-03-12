package com.axxera.ocpp.model.ocpp;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "sessionDeletedHistory")
/*@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "port",
		"accountTransaction" })*/
public class SessionDeletedHistory implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	private String sessionId;
	
	//private String oldRefId;

	// private String customerId;

	@Temporal(TemporalType.DATE)
	@Column(name = "startTimeStamp", length = 10)
	private Date startTimeStamp;

	private double portPrice;
	private int portPriceUnit;
	private boolean masterList;
	private String OldRefId;

	/*
	 * private double cost; private double transactionFee; private double value;
	 * private String unit;
	 */

	private double reqDurationInMin;
	private double sessionElapsedInMin;
	private double kilowattHoursUsed;
	private double finalCostInSlcCurrency;
	private double avgSessionLineFreqInHz;
	private double cost;
	private double transactionFee;
	private String reasonForTer;
	private String authorizationStatus;
	private String emailId;
	private String currentImport_Value;
	private String energyActImportReg_Value;
	private String currentImport_Context;
	private String currentImport_format;
	private String currentImport_location;
	private String driverGroupName;
	//private Long transactionId;
	private long port;
	
	

	@Temporal(TemporalType.DATE)
	private Date creationDate;

	
	public long getPort() {
		return port;
	}

	public void setPort(long port) {
		this.port = port;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Date startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public double getPortPrice() {
		return portPrice;
	}

	public void setPortPrice(double portPrice) {
		this.portPrice = portPrice;
	}

	public int getPortPriceUnit() {
		return portPriceUnit;
	}

	public void setPortPriceUnit(int portPriceUnit) {
		this.portPriceUnit = portPriceUnit;
	}

	public boolean isMasterList() {
		return masterList;
	}

	public void setMasterList(boolean masterList) {
		this.masterList = masterList;
	}

	/*
	 * public double getCost() { return cost; }
	 * 
	 * public void setCost(double cost) { this.cost = cost; }
	 * 
	 * public double getTransactionFee() { return transactionFee; }
	 * 
	 * public void setTransactionFee(double transactionFee) { this.transactionFee =
	 * transactionFee; }
	 * 
	 * public double getValue() { return value; }
	 * 
	 * public void setValue(double value) { this.value = value; }
	 * 
	 * public String getUnit() { return unit; }
	 * 
	 * public void setUnit(String unit) { this.unit = unit; }
	 */
	/*@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "accountTransaction_id")
	public AccountTransactions getAccountTransaction() {
		return accountTransaction;
	}

	public void setAccountTransaction(AccountTransactions accountTransaction) {
		this.accountTransaction = accountTransaction;
	}
*/
	public double getReqDurationInMin() {
		return reqDurationInMin;
	}

	public void setReqDurationInMin(double reqDurationInMin) {
		this.reqDurationInMin = reqDurationInMin;
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

	public double getAvgSessionLineFreqInHz() {
		return avgSessionLineFreqInHz;
	}

	public void setAvgSessionLineFreqInHz(double avgSessionLineFreqInHz) {
		this.avgSessionLineFreqInHz = avgSessionLineFreqInHz;
	}

	public String getReasonForTer() {
		return reasonForTer;
	}

	public void setReasonForTer(String reasonForTer) {
		this.reasonForTer = reasonForTer;
	}

	public String getAuthorizationStatus() {
		return authorizationStatus;
	}

	public void setAuthorizationStatus(String authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCurrentImport_Value() {
		return currentImport_Value;
	}

	public void setCurrentImport_Value(String currentImport_Value) {
		this.currentImport_Value = currentImport_Value;
	}

	public String getEnergyActImportReg_Value() {
		return energyActImportReg_Value;
	}

	public void setEnergyActImportReg_Value(String energyActImportReg_Value) {
		this.energyActImportReg_Value = energyActImportReg_Value;
	}

	public String getCurrentImport_Context() {
		return currentImport_Context;
	}

	public void setCurrentImport_Context(String currentImport_Context) {
		this.currentImport_Context = currentImport_Context;
	}

	public String getCurrentImport_format() {
		return currentImport_format;
	}

	public void setCurrentImport_format(String currentImport_format) {
		this.currentImport_format = currentImport_format;
	}

	public String getCurrentImport_location() {
		return currentImport_location;
	}

	public void setCurrentImport_location(String currentImport_location) {
		this.currentImport_location = currentImport_location;
	}

	public String getDriverGroupName() {
		return driverGroupName;
	}

	public void setDriverGroupName(String driverGroupName) {
		this.driverGroupName = driverGroupName;
	}

	public String getOldRefId() {
		return OldRefId;
	}

	public void setOldRefId(String oldRefId) {
		OldRefId = oldRefId;
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

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	@Override
	public String toString() {
		return "SessionDeletedHistory [sessionId=" + sessionId + ", startTimeStamp=" + startTimeStamp + ", portPrice="
				+ portPrice + ", portPriceUnit=" + portPriceUnit + ", masterList=" + masterList + ", OldRefId="
				+ OldRefId + ", reqDurationInMin=" + reqDurationInMin + ", sessionElapsedInMin=" + sessionElapsedInMin
				+ ", kilowattHoursUsed=" + kilowattHoursUsed + ", finalCostInSlcCurrency=" + finalCostInSlcCurrency
				+ ", avgSessionLineFreqInHz=" + avgSessionLineFreqInHz + ", cost=" + cost + ", transactionFee="
				+ transactionFee + ", reasonForTer=" + reasonForTer + ", authorizationStatus=" + authorizationStatus
				+ ", emailId=" + emailId + ", currentImport_Value=" + currentImport_Value
				+ ", energyActImportReg_Value=" + energyActImportReg_Value + ", currentImport_Context="
				+ currentImport_Context + ", currentImport_format=" + currentImport_format + ", currentImport_location="
				+ currentImport_location + ", driverGroupName=" + driverGroupName + ", port=" + port + ", creationDate="
				+ creationDate + "]";
	}
	
	

}
