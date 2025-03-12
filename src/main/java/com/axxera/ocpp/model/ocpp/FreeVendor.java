package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "free_vendor")
public class FreeVendor extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String freevenRFID;

	private String rfidDecimal;

	public String getFreevenRFID() {
		return freevenRFID;
	}

	public void setFreevenRFID(String freevenRFID) {
		this.freevenRFID = freevenRFID;
	}

	public String getRfidDecimal() {
		return rfidDecimal;
	}

	public void setRfidDecimal(String rfidDecimal) {
		this.rfidDecimal = rfidDecimal;
	}

	@Override
	public String toString() {
		return "FreeVendor [freevenRFID=" + freevenRFID + ", rfidDecimal=" + rfidDecimal + "]";
	}

}
