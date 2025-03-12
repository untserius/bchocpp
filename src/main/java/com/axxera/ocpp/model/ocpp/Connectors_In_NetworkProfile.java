package com.axxera.ocpp.model.ocpp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "connectors_in_networkprofile")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "stationId", "hibernateLazyInitializer", "handler", })
public class Connectors_In_NetworkProfile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private double maxCapacity;
	private String name;
	private String uniqueId;
	private Long connectorId;
	private Stations_In_NetworkProfile stationId;
	private Long portId;
	private double maxAmps;
	private String portStatus;
	private Long ref_StationId;
	private Long profileId;
	private boolean optFlag;
	private double currentLimit;

	public double getCurrentLimit() {
		return currentLimit;
	}

	public void setCurrentLimit(double currentLimit) {
		this.currentLimit = currentLimit;
	}

	public double getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(Long connectorId) {
		this.connectorId = connectorId;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "stationId")
	public Stations_In_NetworkProfile getStationId() {
		return stationId;
	}

	public void setStationId(Stations_In_NetworkProfile stationId) {
		this.stationId = stationId;
	}

	public Long getPortId() {
		return portId;
	}

	public void setPortId(Long portId) {
		this.portId = portId;
	}

	public double getMaxAmps() {
		return maxAmps;
	}

	public void setMaxAmps(double maxAmps) {
		this.maxAmps = maxAmps;
	}

	public String getPortStatus() {
		return portStatus;
	}

	public void setPortStatus(String portStatus) {
		this.portStatus = portStatus;
	}

	public Long getRef_StationId() {
		return ref_StationId;
	}

	public void setRef_StationId(Long ref_StationId) {
		this.ref_StationId = ref_StationId;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public boolean isOptFlag() {
		return optFlag;
	}

	public void setOptFlag(boolean optFlag) {
		this.optFlag = optFlag;
	}

	

}
