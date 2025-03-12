package com.axxera.ocpp.model.ocpp;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "organization", uniqueConstraints = @UniqueConstraint(columnNames = "orgName"))
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "site", "users" })
public class Organization extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orgName;

	private String oldOrgId;

	private String description;

	private String address;

	private String salesChannel;

	private String createdBy;

	private boolean whiteLabel;
	
	@JsonIgnore
	private Set<Site> site = new HashSet<Site>();
	@JsonIgnore
	private Set<User> users = new HashSet<User>();

	public Organization(String orgName, String description, String address, String salesChannel, String createdBy) {
		super();
		this.orgName = orgName;
		this.description = description;
		this.address = address;
		this.salesChannel = salesChannel;
		this.createdBy = createdBy;
		// this.dealer = dealer;

	}

	public Organization() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOldOrgId() {
		return oldOrgId;
	}

	public void setOldOrgId(String oldOrgId) {
		this.oldOrgId = oldOrgId;
	}

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	@Transient
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public boolean isWhiteLabel() {
		return whiteLabel;
	}

	public void setWhiteLabel(boolean whiteLabel) {
		this.whiteLabel = whiteLabel;
	}

	@Override
	public String toString() {
		return "Organization [orgName=" + orgName + ", oldOrgId=" + oldOrgId + ", description=" + description
				+ ", address=" + address + ", salesChannel=" + salesChannel + ", createdBy=" + createdBy
				+ ", whiteLabel=" + whiteLabel + ", site=" + site + ", users=" + users + "]";
	}
}
