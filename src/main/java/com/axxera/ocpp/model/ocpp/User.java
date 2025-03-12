package com.axxera.ocpp.model.ocpp;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@JsonIgnoreProperties(ignoreUnknown = true, value = { "passwords", "roles",
		"hibernateLazyInitializer", "handler", "sites", "stations", "party" })
public class User {

	protected Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String oldRefId;

	@Column(name = "FirstName", nullable = false, length = 50)
	private String firstName;

	@Column(name = "LastName", length = 50)
	private String lastName;

	@Column(name = "username", unique = true, length = 50)
	private String username;

	@Column(name = "email", unique = true, length = 50)
	private String email;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	private Set<Accounts> account = new HashSet<Accounts>(0);

	private Set<Site> sites = new HashSet<Site>();

	private Set<Organization> org = new HashSet<Organization>();

	private Set<Station> stations = new HashSet<Station>();

	@Transient
	private String token;

	@Temporal(TemporalType.DATE)
	@Column(name = "creation_date", length = 10)
	private Date creationDate = new Date();

	/*
	 * @Transient
	 * 
	 * @JsonIgnore private String confirmPassword;
	 */

	public String getOldRefId() {
		return oldRefId;
	}

	public void setOldRefId(String oldRefId) {
		this.oldRefId = oldRefId;
	}

	public User() {
	}

	public User(boolean enabled, String firstName) {
		this.enabled = enabled;
		this.firstName = firstName;
	}

	public User(String firstName, String username, boolean enabled) {

		this.firstName = firstName;
		this.username = username;
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Transient
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/*
	 * @Transient
	 * 
	 * @JsonIgnore public String getConfirmPassword() { return confirmPassword; }
	 * 
	 * @JsonIgnore public void setConfirmPassword(String confirmPassword) {
	 * this.confirmPassword = confirmPassword; }
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Accounts> getAccount() {
		return account;
	}

	public void setAccount(Set<Accounts> account) {
		this.account = account;
	}

	@ManyToMany(mappedBy = "sites", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Transient
	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

	@ManyToMany(mappedBy = "stations", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Transient
	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Organization.class)
	@JoinTable(name = "users_in_org", uniqueConstraints = @UniqueConstraint(columnNames = { "orgId",
			"userId" }), joinColumns = {
					@JoinColumn(name = "userId", nullable = false, updatable = false, referencedColumnName = "userId") }, inverseJoinColumns = {
							@JoinColumn(name = "orgId", nullable = false, updatable = false, referencedColumnName = "id") })

	public Set<Organization> getOrg() {
		return org;
	}

	public void setOrg(Set<Organization> org) {
		this.org = org;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
