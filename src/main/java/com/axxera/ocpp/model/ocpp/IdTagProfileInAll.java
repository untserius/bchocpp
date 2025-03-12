package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "idtagprofile_in_all")
public class IdTagProfileInAll extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String profileType;

	private long idtagProfileId;

	private long profileTypeId;

	public long getIdtagProfileId() {
		return idtagProfileId;
	}

	public void setIdtagProfileId(long idtagProfileId) {
		this.idtagProfileId = idtagProfileId;
	}

	public long getProfileTypeId() {
		return profileTypeId;
	}

	public void setProfileTypeId(long profileTypeId) {
		this.profileTypeId = profileTypeId;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	@Override
	public String toString() {
		return "IdTagProfileInStation [profileType=" + profileType + ", idtagProfileId=" + idtagProfileId
				+ ", profileTypeId=" + profileTypeId + "]";
	}

}
