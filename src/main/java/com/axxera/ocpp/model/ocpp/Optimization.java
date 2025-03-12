package com.axxera.ocpp.model.ocpp;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "optimization_data")
public class Optimization extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long stationId;
	private long portId;
	private long networkId;
	private long connectorId;
	private String stationRefNo;
	private String networkUnId;
	private String profile;
	private String unit;
	private Date validFrom;
	private Date validTO;
	private Date startSchedular;
	private double limit;
	private long stackLevel;
	private long chargingProfileId;
	private String transactionId;

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

	public long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getStationRefNo() {
		return stationRefNo;
	}

	public void setStationRefNo(String stationRefNo) {
		this.stationRefNo = stationRefNo;
	}

	public String getNetworkUnId() {
		return networkUnId;
	}

	public void setNetworkUnId(String networkUnId) {
		this.networkUnId = networkUnId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTO() {
		return validTO;
	}

	public void setValidTO(Date validTO) {
		this.validTO = validTO;
	}

	public Date getStartSchedular() {
		return startSchedular;
	}

	public void setStartSchedular(Date startSchedular) {
		this.startSchedular = startSchedular;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public long getStackLevel() {
		return stackLevel;
	}

	public void setStackLevel(long stackLevel) {
		this.stackLevel = stackLevel;
	}

	public long getChargingProfileId() {
		return chargingProfileId;
	}

	public void setChargingProfileId(long chargingProfileId) {
		this.chargingProfileId = chargingProfileId;
	}

	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "Optimization [stationId=" + stationId + ", portId=" + portId + ", networkId=" + networkId
				+ ", connectorId=" + connectorId + ", stationRefNo=" + stationRefNo + ", networkUnId=" + networkUnId
				+ ", profile=" + profile + ", unit=" + unit + ", validFrom=" + validFrom + ", validTO=" + validTO
				+ ", startSchedular=" + startSchedular + ", limit=" + limit + ", stackLevel=" + stackLevel
				+ ", chargingProfileId=" + chargingProfileId + ", transactionId=" + transactionId + "]";
	}

}

