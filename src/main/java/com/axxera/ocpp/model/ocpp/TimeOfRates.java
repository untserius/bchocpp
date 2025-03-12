package com.axxera.ocpp.model.ocpp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "timeofRates")
public class TimeOfRates extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private NetworkProfile netowrkProfile;
	
	private double variablePower;
	private String startTime;
	private String endTime;
	private String validDate;
	private String uniqueId;
	private String days;
	private boolean flag;
	private String startDate;
	private String endDate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "profileId")
	public NetworkProfile getNetowrkProfile() {
		return netowrkProfile;
	}
	public void setNetowrkProfile(NetworkProfile netowrkProfile) {
		this.netowrkProfile = netowrkProfile;
	}
	public double getVariablePower() {
		return variablePower;
	}
	public void setVariablePower(double variablePower) {
		this.variablePower = variablePower;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	
}