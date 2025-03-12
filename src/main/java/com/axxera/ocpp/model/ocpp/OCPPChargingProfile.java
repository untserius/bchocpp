package com.axxera.ocpp.model.ocpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "ocpp_chargingProfile")
public class OCPPChargingProfile  extends BaseEntity  {
	
	private long chargingProfileId;
	private long stationId;
	private long portId;
	private long stackLevel;
	private String chargingProfilePurpose;
	private String chargingProfileKind;
	private String recurrencyKind;
	private String validFrom;
	private String validTo;
	private String duration;
	private String chargingRateUnit;
	/*private List<OCPPChargingSchedulePeriod> ocppChargingSchedulePeriod;*/
	
	
	
	private static final long serialVersionUID = 1L;
	
	public long getChargingProfileId() {
		return chargingProfileId;
	}
	public void setChargingProfileId(long chargingProfileId) {
		this.chargingProfileId = chargingProfileId;
	}
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	
	@Column(name = "portId")
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	
	@Column(name = "stackLevel",length=50)
	public long getStackLevel() {
		return stackLevel;
	}
	public void setStackLevel(long l) {
		this.stackLevel = l;
	}
	
	@Column(name = "chargingProfilePurpose",length=50)
	public String getChargingProfilePurpose() {
		return chargingProfilePurpose;
	}
	public void setChargingProfilePurpose(String chargingProfilePurpose) {
		this.chargingProfilePurpose = chargingProfilePurpose;
	}
	
	
	@Column(name = "chargingProfileKind",length=50)
	public String getChargingProfileKind() {
		return chargingProfileKind;
	}
	public void setChargingProfileKind(String chargingProfileKind) {
		this.chargingProfileKind = chargingProfileKind;
	}
	
	
	@Column(name = "recurrencyKind",length=50)
	public String getRecurrencyKind() {
		return recurrencyKind;
	}
	public void setRecurrencyKind(String recurrencyKind) {
		this.recurrencyKind = recurrencyKind;
	}
	
	@Column(name = "validFrom",length=50)
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	
	@Column(name = "validTo",length=50)
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	
	@Column(name = "duration")
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	@Column(name = "chargingRateUnit",length=50)
	public String getChargingRateUnit() {
		return chargingRateUnit;
	}
	public void setChargingRateUnit(String chargingRateUnit) {
		this.chargingRateUnit = chargingRateUnit;
	}
	@Override
	public String toString() {
		return "OCPPChargingProfile [chargingProfileId=" + chargingProfileId + ", stationId=" + stationId + ", portId="
				+ portId + ", stackLevel=" + stackLevel + ", chargingProfilePurpose=" + chargingProfilePurpose
				+ ", chargingProfileKind=" + chargingProfileKind + ", recurrencyKind=" + recurrencyKind + ", validFrom="
				+ validFrom + ", validTo=" + validTo + ", duration=" + duration + ", chargingRateUnit="
				+ chargingRateUnit + "]";
	}
}


