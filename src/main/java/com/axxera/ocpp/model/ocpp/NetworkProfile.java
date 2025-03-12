package com.axxera.ocpp.model.ocpp;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "network_profile")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", })
public class NetworkProfile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private double maxCapacity;
	private double limit;
	private String profileType;
	private String uniqueId;
	private double maxPower;
	private String powerUnit;
	private double maxThreshold;
	private Long siteId;
	private String referNo;
	private boolean additionalInfo;
	private boolean optimizedMetervalue;
	private String socPrioritization;
	private boolean variablePower;
	private boolean startSessionsWithMeterValues;
	private boolean optimizationFlag;
	private boolean schedulerCharge;
	private boolean closeIdleSessions;

	// @JsonProperty
//	private TimeOfUseRates timeOfUseRates;
	/*
	 * @JsonProperty private Set<FleetNetworkProfile> fleetNetworkProfile = new
	 * HashSet<FleetNetworkProfile>();
	 * 
	 * @JsonProperty private Set<EnergyPrices> energyPrices = new
	 * HashSet<EnergyPrices>();
	 */
	@JsonProperty
	private Set<TimeOfRates> timeofRates = new HashSet<TimeOfRates>();
	@JsonProperty
	private Set<TimeOfLimits> timeofLimits = new HashSet<TimeOfLimits>();

	@JsonProperty
	private Set<Stations_In_NetworkProfile> stations_In_NetworkProfile = new HashSet<Stations_In_NetworkProfile>();

	public NetworkProfile() {
		// TODO Auto-generated constructor stub
	}

	public NetworkProfile(String name, String profileType, String webhook, double maxPower, String powerUnit,
			double maxThreshold, Long siteId, double connectorMinimumPower) {
		this.name = name;
		this.profileType = profileType;
		// this.webhook = webhook;
		this.maxPower = maxPower;
		this.powerUnit = powerUnit;
		this.maxThreshold = maxThreshold;
		this.siteId = siteId;
		// this.connectorMinimumLoad = connectorMinimumPower;

	}

	public boolean isSchedulerCharge() {
		return schedulerCharge;
	}

	public void setSchedulerCharge(boolean schedulerCharge) {
		this.schedulerCharge = schedulerCharge;
	}

	// public boolean isCloseIdleSessions() {
//		return closeIdleSessions;
//	}
//
//	public void setCloseIdleSessions(boolean closeIdleSessions) {
//		this.closeIdleSessions = closeIdleSessions;
//	}
	public boolean isOptimizationFlag() {
		return optimizationFlag;
	}

	public void setOptimizationFlag(boolean optimizationFlag) {
		this.optimizationFlag = optimizationFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public String getLocation() { return location; }
	 * 
	 * public void setLocation(String location) { this.location = location; }
	 */

	public double getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	/*
	 * public double getCurrentPower() { return currentPower; }
	 * 
	 * public void setCurrentPower(double currentPower) { this.currentPower =
	 * currentPower; }
	 * 
	 * public String getTimeZone() { return timeZone; }
	 * 
	 * public void setTimeZone(String timeZone) { this.timeZone = timeZone; }
	 * 
	 * public double getCurrentLimit() { return currentLimit; }
	 * 
	 * public void setCurrentLimit(double currentLimit) { this.currentLimit =
	 * currentLimit; }
	 */

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/*
	 * public String getWebhook() { return webhook; }
	 * 
	 * public void setWebhook(String webhook) { this.webhook = webhook; }
	 */

	@JsonIgnoreProperties
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "netowrkProfile")
	public Set<Stations_In_NetworkProfile> getStations_In_NetworkProfile() {
		return stations_In_NetworkProfile;
	}

	public void setStations_In_NetworkProfile(Set<Stations_In_NetworkProfile> stations_In_NetworkProfile) {
		this.stations_In_NetworkProfile = stations_In_NetworkProfile;
	}

	public double getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(double maxPower) {
		this.maxPower = maxPower;
	}

	public String getPowerUnit() {
		return powerUnit;
	}

	public void setPowerUnit(String powerUnit) {
		this.powerUnit = powerUnit;
	}

	public double getMaxThreshold() {
		return maxThreshold;
	}

	public void setMaxThreshold(double maxThreshold) {
		this.maxThreshold = maxThreshold;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getReferNo() {
		return referNo;
	}

	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}

	@Column(name = "additionalInfo", columnDefinition = "bit default 0 not null ")
	public boolean isAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(boolean additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/*
	 * @JoinColumn(name = "touRates_id")
	 * 
	 * @ManyToOne(fetch = FetchType.EAGER) public TimeOfUseRates getTimeOfUseRates()
	 * { return timeOfUseRates; }
	 * 
	 * public void setTimeOfUseRates(TimeOfUseRates timeOfUseRates) {
	 * this.timeOfUseRates = timeOfUseRates; }
	 */

	/*
	 * @JsonIgnoreProperties
	 * 
	 * @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy =
	 * "netowrkProfile") public Set<FleetNetworkProfile> getFleetNetworkProfile() {
	 * return fleetNetworkProfile; }
	 * 
	 * public void setFleetNetworkProfile(Set<FleetNetworkProfile>
	 * fleetNetworkProfile) { this.fleetNetworkProfile = fleetNetworkProfile; }
	 */

	public boolean isOptimizedMetervalue() {
		return optimizedMetervalue;
	}

	public void setOptimizedMetervalue(boolean optimizedMetervalue) {
		this.optimizedMetervalue = optimizedMetervalue;
	}

	/*
	 * @JsonIgnoreProperties
	 * 
	 * @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy =
	 * "netowrkProfile") public Set<EnergyPrices> getEnergyPrices() { return
	 * energyPrices; }
	 * 
	 * public void setEnergyPrices(Set<EnergyPrices> energyPrices) {
	 * this.energyPrices = energyPrices; }
	 */

	public String getSocPrioritization() {
		return socPrioritization;
	}

	public void setSocPrioritization(String socPrioritization) {
		this.socPrioritization = socPrioritization;
	}

	@JsonIgnoreProperties
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "netowrkProfile")
	public Set<TimeOfRates> getTimeofRates() {
		return timeofRates;
	}

	public void setTimeofRates(Set<TimeOfRates> timeofRates) {
		this.timeofRates = timeofRates;
	}

	public boolean isVariablePower() {
		return variablePower;
	}

	public void setVariablePower(boolean variablePower) {
		this.variablePower = variablePower;
	}

	@JsonIgnoreProperties
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "netowrkProfile")
	public Set<TimeOfLimits> getTimeofLimits() {
		return timeofLimits;
	}

	public void setTimeofLimits(Set<TimeOfLimits> timeofLimits) {
		this.timeofLimits = timeofLimits;
	}

	public boolean isStartSessionsWithMeterValues() {
		return startSessionsWithMeterValues;
	}

	public void setStartSessionsWithMeterValues(boolean startSessionsWithMeterValues) {
		this.startSessionsWithMeterValues = startSessionsWithMeterValues;
	}

	public boolean isCloseIdleSessions() {
		return closeIdleSessions;
	}

	public void setCloseIdleSessions(boolean closeIdleSessions) {
		this.closeIdleSessions = closeIdleSessions;
	}

}
