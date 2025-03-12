package com.axxera.ocpp.model.ocpp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "idtags_in_profile")
public class IdTags extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String idTag;

	@JsonProperty
	private IdTagProfile idTagProfile;

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	
	@JoinColumn(name = "tag_profileId")
	@ManyToOne(fetch = FetchType.EAGER,cascade={CascadeType.MERGE})
	@JsonBackReference 
	public IdTagProfile getIdTagProfile() {
		return idTagProfile;
	}

	public void setIdTagProfile(IdTagProfile idTagProfile) {
		this.idTagProfile = idTagProfile;
	}

	@Override
	public String toString() {
		return "IdTags [idTag=" + idTag + ", idTagProfile=" + idTagProfile + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idTag == null) ? 0 : idTag.hashCode());
		result = prime * result + ((idTagProfile == null) ? 0 : idTagProfile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdTags other = (IdTags) obj;
		if (idTag == null) {
			if (other.idTag != null)
				return false;
		} else if (!idTag.equals(other.idTag))
			return false;
		if (idTagProfile == null) {
			if (other.idTagProfile != null)
				return false;
		} else if (!idTagProfile.equals(other.idTagProfile))
			return false;
		return true;
	}

	
	
	
	
}
