package com.axxera.ocpp.model.ocpp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "site")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "station","users" })
public class Site implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long id;
	private String oldRefId;
	private String siteName;
	private String name;
	private String streetNo;
	private String streetName;
	private String city;
	private String state;
	private String country;
	private String address;
	private String parkingType;
	private String adaAccess;
	private boolean privateAccess;
	private boolean fleetAccess;
	private boolean publicAccess;
	private Date lastUpdatedDate;
	private String uuid;
	private boolean siteVisibilityOnMap;
	private String currencyType;
	private String timeZone;


	@JsonIgnore
	private Set<Station> station = new HashSet<Station>();

	@JsonProperty
	private Organization org;
	
	private String currencySymbol;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "siteId", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOldRefId() {
		return oldRefId;
	}

	public void setOldRefId(String oldRefId) {
		this.oldRefId = oldRefId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "site")
	public Set<Station> getStation() {
		return station;
	}

	public void setStation(Set<Station> station) {
		this.station = station;
	}

	@JoinColumn(name = "org")
	@ManyToOne(fetch = FetchType.EAGER)
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public boolean isPublicAccess() {
		return publicAccess;
	}

	public void setPublicAccess(boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

	public String getAdaAccess() {
		return adaAccess;
	}

	public void setAdaAccess(String adaAccess) {
		this.adaAccess = adaAccess;
	}

	public boolean isFleetAccess() {
		return fleetAccess;
	}

	public void setFleetAccess(boolean fleetAccess) {
		this.fleetAccess = fleetAccess;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public boolean isPrivateAccess() {
		return privateAccess;
	}

	public void setPrivateAccess(boolean privateAccess) {
		this.privateAccess = privateAccess;
	}

	public boolean isSiteVisibilityOnMap() {
		return siteVisibilityOnMap;
	}

	public void setSiteVisibilityOnMap(boolean siteVisibilityOnMap) {
		this.siteVisibilityOnMap = siteVisibilityOnMap;
	}
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getParkingType() {
		return parkingType;
	}

	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/*
	 * public String getSiteRef() { return siteRef; }
	 * 
	 * public void setSiteRef(String siteRef) { this.siteRef = siteRef; }
	 */

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
