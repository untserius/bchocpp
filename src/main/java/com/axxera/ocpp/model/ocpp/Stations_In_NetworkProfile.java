package com.axxera.ocpp.model.ocpp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "stations_in_networkprofiles")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "netowrkProfile", "hibernateLazyInitializer", "handler", })
public class Stations_In_NetworkProfile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String uniqueId;
	private double maxCapacity;
	private String name;
	private String networkId;
	private String location;
	private String latitude;
	private String longitude;
	private String manufacturer;
	private Long ref_stationId;
	private NetworkProfile netowrkProfile;
	private Long siteId;
	private double maxAmps;

	@JsonProperty
	private List<Connectors_In_NetworkProfile> connectors_In_NetworkProfile = new ArrayList<Connectors_In_NetworkProfile>();

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public Long getRef_stationId() {
		return ref_stationId;
	}

	public void setRef_stationId(Long ref_stationId) {
		this.ref_stationId = ref_stationId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "stationId")
	public List<Connectors_In_NetworkProfile> getConnectors_In_NetworkProfile() {
		return connectors_In_NetworkProfile;
	}

	public void setConnectors_In_NetworkProfile(List<Connectors_In_NetworkProfile> connectors_In_NetworkProfile) {
		this.connectors_In_NetworkProfile = connectors_In_NetworkProfile;
	}

	public double getMaxAmps() {
		return maxAmps;
	}

	public void setMaxAmps(double maxAmps) {
		this.maxAmps = maxAmps;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "profileId")
	public NetworkProfile getNetowrkProfile() {
		return netowrkProfile;
	}

	public void setNetowrkProfile(NetworkProfile netowrkProfile) {
		this.netowrkProfile = netowrkProfile;
	}

}
