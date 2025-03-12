package com.axxera.ocpp.model.ocpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="stations_driverGroups")
public class StationBasedDriverGroups extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="station_unqId")
	private long stationUnqId;
	
	@Column(name="station_refno")
	private String stationRefNo;
	
	@Column(name="group_id")
	private long driverProfileGroupUnqId;

	public long getStationUnqId() {
		return stationUnqId;
	}
	public void setStationUnqId(long stationUnqId) {
		this.stationUnqId = stationUnqId;
	}

	public String getStationRefNo() {
		return stationRefNo;
	}

	public void setStationRefNo(String stationRefNo) {
		this.stationRefNo = stationRefNo;
	}

	public long getDriverProfileGroupUnqId() {
		return driverProfileGroupUnqId;
	}

	public void setDriverProfileGroupUnqId(long driverProfileGroupUnqId) {
		this.driverProfileGroupUnqId = driverProfileGroupUnqId;
	}

	@Override
	public String toString() {
		return "StationUnderDriverGroups [stationUnqId=" + stationUnqId + ", stationRefNo=" + stationRefNo
				+ ", driverProfileGroupUnqId=" + driverProfileGroupUnqId + "]";
	}
	
}
