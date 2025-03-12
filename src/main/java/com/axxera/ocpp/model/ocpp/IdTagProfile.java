package com.axxera.ocpp.model.ocpp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "idtag_profiles")
public class IdTagProfile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String profileType;

	private String profileName;

	private boolean neverExpired;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiry_date", length = 10)
	private Date expiredDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "creation_date", length = 10)
	private Date creationDate = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_Date", length = 10)
	private Date modifiedDate;

	@Column
	private String createdBy;
	@Column
	private String lastModifiedBy;

	@JsonManagedReference
	@JsonIgnore
	private Set<IdTags> tags = new HashSet<IdTags>();

	public IdTagProfile() {
		super();
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public boolean isNeverExpired() {
		return neverExpired;
	}

	public void setNeverExpired(boolean neverExpired) {
		this.neverExpired = neverExpired;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "idTagProfile")
	public Set<IdTags> getTags() {
		return tags;
	}

	public void setTags(Set<IdTags> tags) {
		this.tags = tags;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	@Override
	public String toString() {
		return "IdTagProfile [profileType=" + profileType + ", profileName=" + profileName + ", neverExpired="
				+ neverExpired + ", expiredDate=" + expiredDate + ", creationDate=" + creationDate + ", modifiedDate="
				+ modifiedDate + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + ", tags=" + tags
				+ "]";
	}

}
