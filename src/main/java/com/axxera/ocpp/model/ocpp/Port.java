package com.axxera.ocpp.model.ocpp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Proxy(lazy = false)
@Table(name = "port")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "station", "session" })
public class Port extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String oldRefId;
	private String displayName;
	private long capacity;
	private String chargerType;
	private String status;
	
	
	@JsonIgnore
	private Station station;

	@Temporal(TemporalType.DATE)
	private Date creationDate = new Date();

	@JsonProperty
	private Set<PortStatusNotification> statusNotification = new HashSet<PortStatusNotification>(0);

	private PowerType power_type;	
	
	private ConnectorType standard;	
		
	private ConnectorFormat format;	
		
	private int voltage;	
		
	private int amperage;	
	private String uuid;
	private long connector_id;
	private Date lastUpdatedDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "station_id")
	public Station getStation() {
		return station;
	}

	public String getOldRefId() {
		return oldRefId;
	}

	public void setOldRefId(String oldRefId) {
		this.oldRefId = oldRefId;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public String getChargerType() {
		return chargerType;
	}

	public void setChargerType(String chargerType) {
		if(chargerType==null)
			chargerType="AC";
		this.chargerType = chargerType;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "port")
	public Set<PortStatusNotification> getStatusNotification() {
		return statusNotification;
	}

	public void setStatusNotification(Set<PortStatusNotification> statusNotification) {
		this.statusNotification = statusNotification;
	}

	@JoinColumn(name = "power_type")
	@ManyToOne(fetch = FetchType.EAGER)
	public PowerType getPower_type() {
		return power_type;
	}

	public void setPower_type(PowerType power_type) {
		this.power_type = power_type;
	}

	@JoinColumn(name = "standard")
	@ManyToOne(fetch = FetchType.EAGER)
	public ConnectorType getStandard() {
	return standard;
	}



	public void setStandard(ConnectorType standard) {
	this.standard = standard;
	}



	@JoinColumn(name = "format")
	@ManyToOne(fetch = FetchType.EAGER)
	public ConnectorFormat getFormat() {
	return format;
	}



	public void setFormat(ConnectorFormat format) {
	this.format = format;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public int getAmperage() {
		return amperage;
	}

	public void setAmperage(int amperage) {
		this.amperage = amperage;
	}

	public long getConnector_id() {
		return connector_id;
	}

	public void setConnector_id(long connector_id) {
		this.connector_id = connector_id;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
