package com.axxera.ocpp.model.ocpp;

import java.util.ArrayList;
import java.util.List;

public class CSChargingProfiles {

	private long chargingProfileId;
	private long stackLevel;
	private String chargingProfilePurpose;
	private String chargingProfileKind;
	private String  recurrencyKind;
	private String validFrom;
	private String validTo;
	private long transactionId;
	private List<ChargingSchedule> chargingSchedule = new ArrayList<ChargingSchedule>();
	
	public long getChargingProfileId() {
		return chargingProfileId;
	}
	public void setChargingProfileId(long chargingProfileId) {
		this.chargingProfileId = chargingProfileId;
	}
	public long getStackLevel() {
		return stackLevel;
	}
	public void setStackLevel(long stackLevel) {
		this.stackLevel = stackLevel;
	}
	public String getChargingProfilePurpose() {
		return chargingProfilePurpose;
	}
	public void setChargingProfilePurpose(String chargingProfilePurpose) {
		this.chargingProfilePurpose = chargingProfilePurpose;
	}
	public String getChargingProfileKind() {
		return chargingProfileKind;
	}
	public void setChargingProfileKind(String chargingProfileKind) {
		this.chargingProfileKind = chargingProfileKind;
	}
	public String getRecurrencyKind() {
		return recurrencyKind;
	}
	public void setRecurrencyKind(String recurrencyKind) {
		this.recurrencyKind = recurrencyKind;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	public void chargingSchedule(String validTo) {
		this.validTo = validTo;
	}
	public List<ChargingSchedule> getChargingSchedule() {
		return chargingSchedule;
	}
	public void setChargingSchedule(List<ChargingSchedule> chargingSchedule) {
		this.chargingSchedule = chargingSchedule;
	}
	
	
	
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "CSChargingProfiles [chargingProfileId=" + chargingProfileId + ", stackLevel=" + stackLevel
				+ ", chargingProfilePurpose=" + chargingProfilePurpose + ", chargingProfileKind=" + chargingProfileKind
				+ ", recurrencyKind=" + recurrencyKind + ", validFrom=" + validFrom + ", validTo=" + validTo
				+ ", transactionId=" + transactionId + ", chargingSchedule=" + chargingSchedule + "]";
	}
	
	
}
