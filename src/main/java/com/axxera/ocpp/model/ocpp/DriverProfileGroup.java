package com.axxera.ocpp.model.ocpp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "driver_profile_groups")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler" })
public class DriverProfileGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String groupName;

	private double durationHr;

	private double durationMins;
	
	private User ownerUser;
	
	private boolean salesTax;
	
	@Column(name = "noZeroBalChecking")
	private boolean noZeroBalChecking;

	private long orgId;
	
	private double freeKwh;

	private Set<DriverGroupPricing> driverGroupPricings = new HashSet<DriverGroupPricing>(0);

	@JsonProperty
	private List<User> driverUserId = new ArrayList<User>();

	@Column(name = "driverGroupFreeDuration", columnDefinition = "double precision default 0 not null")
	private double driverGroupFreeDuration;
	
	private long[] listOfdriverIds; 

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public double getDurationHr() {
		return durationHr;
	}

	public void setDurationHr(double durationHr) {
		this.durationHr = durationHr;
	}

	public double getDurationMins() {
		return durationMins;
	}

	public void setDurationMins(double durationMins) {
		this.durationMins = durationMins;
	}

	public void setNoZeroBalChecking(boolean noZeroBalChecking) {
		this.noZeroBalChecking = noZeroBalChecking;
	}

	@JoinColumn(name = "ownerUser")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public double getFreeKwh() {
		return freeKwh;
	}

	public void setFreeKwh(double freeKwh) {
		this.freeKwh = freeKwh;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "driverProfileGroup")
	public Set<DriverGroupPricing> getDriverGroupPricings() {
		return driverGroupPricings;
	}

	public void setDriverGroupPricings(Set<DriverGroupPricing> driverGroupPricings) {
		this.driverGroupPricings = driverGroupPricings;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinTable(name = "driverProfileGroup_userId", uniqueConstraints = @UniqueConstraint(columnNames = { "id",
			"user_id" }), joinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false, referencedColumnName = "id") }, inverseJoinColumns = {
							@JoinColumn(name = "user_id", nullable = false, updatable = false, referencedColumnName = "UserId") })
	@Fetch(value = FetchMode.SELECT)
	public List<User> getDriverUserId() {
		return driverUserId;
	}

	public void setDriverUserId(List<User> driverUserId) {
		this.driverUserId = driverUserId;
	}

	public double getDriverGroupFreeDuration() {
		return driverGroupFreeDuration;
	}

	public void setDriverGroupFreeDuration(double driverGroupFreeDuration) {
		this.driverGroupFreeDuration = driverGroupFreeDuration;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	
	

	public boolean isSalesTax() {
		return salesTax;
	}

	public void setSalesTax(boolean salesTax) {
		this.salesTax = salesTax;
	}

	public boolean isNoZeroBalChecking() {
		return noZeroBalChecking;
	}

	@Transient
	public long[] getListOfdriverIds() {
		return listOfdriverIds;
	}

	public void setListOfdriverIds(long[] listOfdriverIds) {
		this.listOfdriverIds = listOfdriverIds;
	}

	@Override
	public String toString() {
		return "DriverProfileGroup [groupName=" + groupName + ", durationHr=" + durationHr + ", durationMins="
				+ durationMins + ", ownerUser=" + ownerUser + ", salesTax=" + salesTax + ", noZeroBalChecking="
				+ noZeroBalChecking + ", orgId=" + orgId + ", freeKwh=" + freeKwh + ", driverGroupPricings="
				+ driverGroupPricings + ", driverUserId=" + driverUserId + ", driverGroupFreeDuration="
				+ driverGroupFreeDuration + ", listOfdriverIds=" + Arrays.toString(listOfdriverIds) + "]";
	}

}
