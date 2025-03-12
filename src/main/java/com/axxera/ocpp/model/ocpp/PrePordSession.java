
package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "preprod_session")

public class PrePordSession extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String sessionId = "";
	private long sessionUniId = 0;
	private String customerId = "";

	@Temporal(TemporalType.DATE)
	@Column(name = "startTimeStamp", length = 10)
	private Date startTimeStamp;

	private double portPrice = 0;
	private String portPriceUnit = "";
	private boolean masterList = false;
	private String OldRefId = "";

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
	private Long NoOfDG;

	private double actualPrice;
	private double usedGraceTime;
	private double oneTimeFeeCost;

	private boolean maxSessionFlag;

	private String stationMode;
	private String chargerType;

	private Integer driverGroups;
	private double durationMinsofSmeEnergy;
	private double costOfSmeEnergy;
	@JsonIgnore
	private long port_id;
	private long accountTransaction_id;
	private double powerActiveImport_value;
	private double reservationFee;

	@Temporal(TemporalType.DATE)
	private Date endTimeStamp;

	private Long userId;

	private double socStartVal;
	private double socEndVal;

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

	public double getPortPrice() {
		return portPrice;
	}

	public void setPortPrice(double portPrice) {
		this.portPrice = portPrice;
	}

	public String getPortPriceUnit() {
		return portPriceUnit;
	}

	public void setPortPriceUnit(String portPriceUnit) {
		this.portPriceUnit = portPriceUnit;
	}

	public boolean isMasterList() {
		return masterList;
	}

	public void setMasterList(boolean masterList) {
		this.masterList = masterList;
	}

	public double getReqDurationInMin() {
		return reqDurationInMin;
	}

	public long getPort_id() {
		return port_id;
	}

	public void setPort_id(long port_id) {
		this.port_id = port_id;
	}

	public long getAccountTransaction_id() {
		return accountTransaction_id;
	}

	public void setAccountTransaction_id(long accountTransaction_id) {
		this.accountTransaction_id = accountTransaction_id;
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

	public Long getNoOfDG() {
		return NoOfDG;
	}

	public void setNoOfDG(Long noOfDG) {
		NoOfDG = noOfDG;
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

	@Column(name = "actualPrice", columnDefinition = "double precision default 0 not null")
	public double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

	@Column(name = "usedGraceTime", columnDefinition = "double precision default 0 not null")
	public double getUsedGraceTime() {
		return usedGraceTime;
	}

	public void setUsedGraceTime(double usedGraceTime) {
		this.usedGraceTime = usedGraceTime;
	}

	@Column(name = "oneTimeFeeCost", columnDefinition = "double precision default 0 not null")
	public double getOneTimeFeeCost() {
		return oneTimeFeeCost;
	}

	public void setOneTimeFeeCost(double oneTimeFeeCost) {
		this.oneTimeFeeCost = oneTimeFeeCost;
	}

	@Column(name = "maxSessionFlag", columnDefinition = "bit default 1 not null ")
	public boolean isMaxSessionFlag() {
		return maxSessionFlag;
	}

	public void setMaxSessionFlag(boolean maxSessionFlag) {
		this.maxSessionFlag = maxSessionFlag;
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

	public Integer getDriverGroups() {
		return driverGroups;
	}

	public void setDriverGroups(Integer driverGroups) {
		this.driverGroups = driverGroups;
	}

	@Column(name = "durationMinsofSmeEnergy", columnDefinition = "double precision default 0 not null")
	public double getDurationMinsofSmeEnergy() {
		return durationMinsofSmeEnergy;
	}

	public void setDurationMinsofSmeEnergy(double durationMinsofSmeEnergy) {
		this.durationMinsofSmeEnergy = durationMinsofSmeEnergy;
	}

	@Column(name = "costOfSmeEnergy", columnDefinition = "double precision default 0 not null")
	public double getCostOfSmeEnergy() {
		return costOfSmeEnergy;
	}

	public void setCostOfSmeEnergy(double costOfSmeEnergy) {
		this.costOfSmeEnergy = costOfSmeEnergy;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "userId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public double getPowerActiveImport_value() {
		return powerActiveImport_value;
	}

	public void setPowerActiveImport_value(double powerActiveImport_value) {
		this.powerActiveImport_value = powerActiveImport_value;
	}

	public double getReservationFee() {
		return reservationFee;
	}

	public void setReservationFee(double reservationFee) {
		this.reservationFee = reservationFee;
	}

	public long getSessionUniId() {
		return sessionUniId;
	}

	public void setSessionUniId(long sessionUniId) {
		this.sessionUniId = sessionUniId;
	}
	
}
