package com.axxera.ocpp.model.ocpp;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "station")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "users" })
public class Station extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String evse_id;
	private String referNo;
	private String uid;

	@Column(name = "station_name", length = 200)
	private String stationName;
	@Temporal(TemporalType.DATE)
	@Column(name = "creation_date", length = 10)
	private Date creationDate = new Date();

	@Column(name = "flag")
	private String stationStatus;
	private Date stationTimeStamp;
	private String housingSerialNumber;
	private String boardSerialNumber;
	private int portQuantity;
	private String networkType;
	private String capacity;
	private String chagerType;
	private String stationMode;
	private double exceedingHours;
	private double exceedingMints;
	private String greetingMessage;
	private String greetingMail;
	private String stationAvailStatus;
	private boolean visibilityOnMap;
	private Date sendMailTime;
	private Date mailSuspendTime;
	private String modelNumber;
	private String mailEnable;
	private String evseMount;
	private String evseType;
	private String ocppSupport;
	private String imei;
	private boolean siteVisibilityOnMap;
	private boolean fleetAccess;
	private boolean privateAccess;
	private boolean publicAccess;
	private Time freeCharging;
	private double connectedTime;

	@Column(name = "connectedTimeFlag")
	@JsonProperty("connectedTimeFlag")
	private boolean connectedTimeFlag;

	private double graceTime;
	private String connectedTimeUnits;
	private double suspendedTime;
	private Time inOperativeFrom;
	private Time inOperativeTo;
	private String simCard;
	private Date mailDateAndTime;
	private String stationAddress;
	private boolean networkCommunication;
	private Date intialContactTime;
	private Date lastUpdatedDate;
	

	public Station() {

	}

	@JsonProperty
	private List<Port> ports = new ArrayList<Port>();

	@JsonProperty
	private Site site;

	@Column(name = "stationFreeDuration", columnDefinition = "double precision default 0 not null")
	private double stationFreeDuration;

	@Column(name = "stationMaxSessionTime", columnDefinition = "double precision default 0 not null")
	private double stationMaxSessiontime;

	/*
	 * @JsonProperty private States state;
	 */

	@JsonProperty
	private Manufacturer manufacturer;

	
	@JsonIgnore
	private Set<User> users = new HashSet<User>(0);

	private String stationInOperativeTo;
	private String stationInperativeFrom;

	private String inOperativeRequestSent;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/*
	 * public String getStationRef() { return stationRef; }
	 * 
	 * public void setStationRef(String stationRef) { this.stationRef = stationRef;
	 * }
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = { "stationId",
			"userId" }), joinColumns = {
					@JoinColumn(name = "stationId", nullable = false, updatable = false, referencedColumnName = "id") }, inverseJoinColumns = {
							@JoinColumn(name = "userId", nullable = false, updatable = false, referencedColumnName = "userId") })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/*
	 * @JoinColumn(name = "stateId")
	 * 
	 * @ManyToOne(fetch = FetchType.EAGER) public States getState() { return state;
	 * }
	 * 
	 * public void setState(States state) { this.state = state; }
	 */

	@JoinColumn(name = "siteId")
	@ManyToOne(fetch = FetchType.EAGER)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@JoinColumn(name = "manufacturerId")
	@ManyToOne(fetch = FetchType.LAZY)
	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getStationStatus() {
		return stationStatus;
	}

	public void setStationStatus(String stationStatus) {
		this.stationStatus = stationStatus;
	}

	public Date getStationTimeStamp() {
		return stationTimeStamp;
	}

	public void setStationTimeStamp(Date stationTimeStamp) {
		this.stationTimeStamp = stationTimeStamp;
	}

	public String getHousingSerialNumber() {
		return housingSerialNumber;
	}

	public void setHousingSerialNumber(String housingSerialNumber) {
		this.housingSerialNumber = housingSerialNumber;
	}

	public String getBoardSerialNumber() {
		return boardSerialNumber;
	}

	public void setBoardSerialNumber(String boardSerialNumber) {
		this.boardSerialNumber = boardSerialNumber;
	}

	public int getPortQuantity() {
		return portQuantity;
	}

	public void setPortQuantity(int portQuantity) {
		this.portQuantity = portQuantity;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getChagerType() {
		return chagerType;
	}

	public void setChagerType(String chagerType) {
		this.chagerType = chagerType;
	}

	public String getStationMode() {
		return stationMode;
	}

	public void setStationMode(String stationMode) {
		if (stationMode == null || stationMode == "")
			stationMode = "Normal";
		this.stationMode = stationMode;
	}

	public double getExceedingHours() {
		return exceedingHours;
	}

	public void setExceedingHours(double exceedingHours) {
		this.exceedingHours = exceedingHours;
	}

	public double getExceedingMints() {
		return exceedingMints;
	}

	public void setExceedingMints(double exceedingMints) {
		this.exceedingMints = exceedingMints;
	}

	public String getGreetingMessage() {
		return greetingMessage;
	}

	public void setGreetingMessage(String greetingMessage) {
		this.greetingMessage = greetingMessage;
	}

	public String getGreetingMail() {
		return greetingMail;
	}

	public void setGreetingMail(String greetingMail) {
		this.greetingMail = greetingMail;
	}

	public String getStationAvailStatus() {
		return stationAvailStatus;
	}

	public void setStationAvailStatus(String stationAvailStatus) {
		this.stationAvailStatus = stationAvailStatus;
	}

	public boolean getVisibilityOnMap() {
		return visibilityOnMap;
	}

	public void setVisibilityOnMap(boolean visibilityOnMap) {
		this.visibilityOnMap = visibilityOnMap;
	}

	public Date getSendMailTime() {
		return sendMailTime;
	}

	public void setSendMailTime(Date sendMailTime) {
		this.sendMailTime = sendMailTime;
	}

	public Date getMailSuspendTime() {
		return mailSuspendTime;
	}

	public void setMailSuspendTime(Date mailSuspendTime) {
		this.mailSuspendTime = mailSuspendTime;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getMailEnable() {
		return mailEnable;
	}

	public void setMailEnable(String mailEnable) {
		this.mailEnable = mailEnable;
	}

	public String getOcppSupport() {
		return ocppSupport;
	}

	public void setOcppSupport(String ocppSupport) {
		this.ocppSupport = ocppSupport;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSimCard() {
		return simCard;
	}

	public void setSimCard(String simCard) {
		this.simCard = simCard;
	}

	public boolean isSiteVisibilityOnMap() {
		return siteVisibilityOnMap;
	}

	public void setSiteVisibilityOnMap(boolean siteVisibilityOnMap) {
		this.siteVisibilityOnMap = siteVisibilityOnMap;
	}

	public boolean isFleetAccess() {
		return fleetAccess;
	}

	public void setFleetAccess(boolean fleetAccess) {
		this.fleetAccess = fleetAccess;
	}

	public boolean isPrivateAccess() {
		return privateAccess;
	}

	public void setPrivateAccess(boolean privateAccess) {
		this.privateAccess = privateAccess;
	}

	public boolean isPublicAccess() {
		return publicAccess;
	}

	public void setPublicAccess(boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "station")
	@Fetch(value = FetchMode.SELECT)
	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}

	@JoinColumn(name = "freeCharging")
	@ManyToOne(fetch = FetchType.LAZY)
	public Time getFreeCharging() {
		return freeCharging;
	}

	public void setFreeCharging(Time freeCharging) {
		this.freeCharging = freeCharging;
	}

	@Column(name = "connectedTime", columnDefinition = "double precision default 0 not null")
	public double getConnectedTime() {
		return connectedTime;
	}

	public void setConnectedTime(double connectedTime) {
		this.connectedTime = connectedTime;
	}

	@Column(name = "connectedTimeFlag", columnDefinition = "bit default 1 not null ")
	public boolean isConnectedTimeFlag() {
		return connectedTimeFlag;
	}

	public void setConnectedTimeFlag(boolean connectedTimeFlag) {
		this.connectedTimeFlag = connectedTimeFlag;
	}

	@Column(name = "graceTime", columnDefinition = "double precision default 0 not null")
	public double getGraceTime() {
		return graceTime;
	}

	public void setGraceTime(double graceTime) {
		this.graceTime = graceTime;
	}

	public String getConnectedTimeUnits() {
		return connectedTimeUnits;
	}

	public void setConnectedTimeUnits(String connectedTimeUnits) {
		this.connectedTimeUnits = connectedTimeUnits;
	}

	@Column(name = "suspendedTime", columnDefinition = "double precision default 0 not null")
	public double getSuspendedTime() {
		return suspendedTime;
	}

	public void setSuspendedTime(double suspendedTime) {
		this.suspendedTime = suspendedTime;
	}

	@JoinColumn(name = "inOperativeFrom")
	@ManyToOne(fetch = FetchType.LAZY)
	public Time getInOperativeFrom() {
		return inOperativeFrom;
	}

	public void setInOperativeFrom(Time inOperativeFrom) {
		this.inOperativeFrom = inOperativeFrom;
	}

	@JoinColumn(name = "inOperativeTo")
	@ManyToOne(fetch = FetchType.LAZY)
	public Time getInOperativeTo() {
		return inOperativeTo;
	}

	public void setInOperativeTo(Time inOperativeTo) {
		this.inOperativeTo = inOperativeTo;
	}

	@Column(name = "stationFreeDuration", columnDefinition = "double precision default 0 not null")
	public double getStationFreeDuration() {
		return stationFreeDuration;
	}

	public void setStationFreeDuration(double stationFreeDuration) {
		this.stationFreeDuration = stationFreeDuration;
	}

	@Column(name = "stationMaxSessiontime", columnDefinition = "double precision default 0 not null")
	public double getStationMaxSessiontime() {
		return stationMaxSessiontime;
	}

	public void setStationMaxSessiontime(double stationMaxSessiontime) {
		this.stationMaxSessiontime = stationMaxSessiontime;
	}

	public String getEvseMount() {
		return evseMount;
	}

	public void setEvseMount(String evseMount) {
		this.evseMount = evseMount;
	}

	public String getEvseType() {
		return evseType;
	}

	public void setEvseType(String evseType) {
		this.evseType = evseType;
	}

	public String getEvse_id() {
		return evse_id;
	}

	public void setEvse_id(String evse_id) {
		this.evse_id = evse_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getMailDateAndTime() {
		return mailDateAndTime;
	}

	public void setMailDateAndTime(Date mailDateAndTime) {
		this.mailDateAndTime = mailDateAndTime;
	}

	public String getReferNo() {
		return referNo;
	}

	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}

	public String getStationAddress() {
		return stationAddress;
	}

	public void setStationAddress(String stationAddress) {
		this.stationAddress = stationAddress;
	}

	public String getStationInOperativeTo() {
		return stationInOperativeTo;
	}

	public void setStationInOperativeTo(String stationInOperativeTo) {
		this.stationInOperativeTo = stationInOperativeTo;
	}

	public String getStationInperativeFrom() {
		return stationInperativeFrom;
	}

	public void setStationInperativeFrom(String stationInperativeFrom) {
		this.stationInperativeFrom = stationInperativeFrom;
	}

	public String getInOperativeRequestSent() {
		return inOperativeRequestSent;
	}

	public void setInOperativeRequestSent(String inOperativeRequestSent) {
		this.inOperativeRequestSent = inOperativeRequestSent;
	}

	public boolean isNetworkCommunication() {
		return networkCommunication;
	}

	public void setNetworkCommunication(boolean networkCommunication) {
		this.networkCommunication = networkCommunication;
	}

	public Date getIntialContactTime() {
		return intialContactTime;
	}

	public void setIntialContactTime(Date intialContactTime) {
		this.intialContactTime = intialContactTime;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}