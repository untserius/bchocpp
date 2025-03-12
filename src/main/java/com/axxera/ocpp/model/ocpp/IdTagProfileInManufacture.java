package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "idtagprofile_in_manufacture")
public class IdTagProfileInManufacture extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long idtagProfileId;

	private long manfactureId;
	
	

	public IdTagProfileInManufacture() {
		super();
	}

	public IdTagProfileInManufacture(long idtagProfileId, Long manfactureId) {
		// TODO Auto-generated constructor stub
		super();
		this.idtagProfileId = idtagProfileId;
		this.manfactureId = manfactureId;

	}

	public long getIdtagProfileId() {
		return idtagProfileId;
	}

	public void setIdtagProfileId(long idtagProfileId) {
		this.idtagProfileId = idtagProfileId;
	}

	public long getManfactureId() {
		return manfactureId;
	}

	public void setManfactureId(long manfactureId) {
		this.manfactureId = manfactureId;
	}

	@Override
	public String toString() {
		return "IdTagProfileInManufacture [idtagProfileId=" + idtagProfileId + ", manfactureId=" + manfactureId + "]";
	}

}
