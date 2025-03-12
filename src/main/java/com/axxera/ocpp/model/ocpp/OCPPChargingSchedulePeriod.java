package com.axxera.ocpp.model.ocpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OCPPChargingSchedulePeriod")
public class OCPPChargingSchedulePeriod  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	
	
	@Column(name = "startPeriod")
	private long startPeriod;
	
	@Column(name = "limit")
	private long limit;
	
	@Column(name = "numberOfPhases")
	private long numberPhases;
	
	@Column(name = "stationId")
	private long stationId;
	
	@Column(name = "portId")
	private long portId;
	
	@Column(name = "chargingProfileId")
	private long chargingProfileId;
	
	/*@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "chargingProfileId")
	private ocppChargingProfile ocppChargingProfile;
	*/
	
	
	
	/*public ocppChargingProfile getOcppChargingProfile() {
		return ocppChargingProfile;
	}
	public void setOcppChargingProfile(ocppChargingProfile ocppChargingProfile) {
		this.ocppChargingProfile = ocppChargingProfile;
	}*/

	public long getChargingProfileId() {
		return chargingProfileId;
	}
	public long getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(long startPeriod) {
		this.startPeriod = startPeriod;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public long getNumberPhases() {
		return numberPhases;
	}
	public void setNumberPhases(long numberPhases) {
		this.numberPhases = numberPhases;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	@Override
	public String toString() {
		return "OCPPChargingSchedulePeriod [startPeriod=" + startPeriod + ", limit=" + limit + ", numberPhases="
				+ numberPhases + ", stationId=" + stationId + ", portId=" + portId + ", chargingProfileId="
				+ chargingProfileId + "]";
	}
	
	
	
	
	
}
