package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "local_list")
public class LocalList extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idTag;
	private String versionNumber;
	private long stationId;

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	@Override
	public String toString() {
		return "LocalList [idTag=" + idTag + ", versionNumber=" + versionNumber + ", stationId=" + stationId + "]";
	}

}
