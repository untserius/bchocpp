package com.axxera.ocpp.model.ocpp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "timeofLimits")
public class TimeOfLimits  extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private  double variablePower;
	private String startTime;
	private String endTime;
	private String uniqueId;
	private long capacityId;
	@JsonIgnore
	private NetworkProfile netowrkProfile;
	
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
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public long getCapacityId() {
		return capacityId;
	}
	public void setCapacityId(long capacityId) {
		this.capacityId = capacityId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "profileId")
	public NetworkProfile getNetowrkProfile() {
		return netowrkProfile;
	}
	public void setNetowrkProfile(NetworkProfile netowrkProfile) {
		this.netowrkProfile = netowrkProfile;
	}
	@Override
	public String toString() {
		return "TimeOfLimits [variablePower=" + variablePower + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", uniqueId=" + uniqueId + ", capacityId=" + capacityId + ", netowrkProfile=" + netowrkProfile + "]";
	}
	
	
}
