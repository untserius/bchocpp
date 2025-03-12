package com.axxera.ocpp.model.ocpp;



import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "driver_groupRFIDS")
public class DriverGroupRFIDs extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String rfid;

	@JsonIgnore
	private DriverProfileGroup driverProfileGroup;

	@ManyToOne(fetch = FetchType.LAZY,cascade = { CascadeType.MERGE })
	@JoinColumn(name = "driver_group_id")
	public DriverProfileGroup getDriverProfileGroup() {
		return driverProfileGroup;
	}

	public void setDriverProfileGroup(DriverProfileGroup driverProfileGroup) {
		this.driverProfileGroup = driverProfileGroup;
	}
	
	

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	@Override
	public String toString() {
		return "DriverGroupRFIDs [rfid=" + rfid + ", driverProfileGroup=" + driverProfileGroup + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(driverProfileGroup, rfid);
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
		DriverGroupRFIDs other = (DriverGroupRFIDs) obj;
		return Objects.equals(driverProfileGroup, other.driverProfileGroup) && Objects.equals(rfid, other.rfid);
	}
	
	

}
