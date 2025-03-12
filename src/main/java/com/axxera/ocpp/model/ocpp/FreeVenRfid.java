package com.axxera.ocpp.model.ocpp;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="freeVenRfid")
public class FreeVenRfid extends BaseEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Column(name="FreevenRFID")
	private String freeVenRfid;
	
	//@Column(name="RfidDecimal")
		private String rfidDecimal;
	
	public String getFreeVenRfid() {
		return freeVenRfid;
	}

	public void setFreeVenRfid(String freeVenRfid) {
		this.freeVenRfid = freeVenRfid;
	}
	
	
	
	public String getRfidDecimal() {
		return rfidDecimal;
	}

	public void setRfidDecimal(String rfidDecimal) {
		this.rfidDecimal = rfidDecimal;
	}

	@Override
	public String toString() {
		return "FreeVenRfid [freeVenRfid=" + freeVenRfid + ", rfidDecimal=" + rfidDecimal + "]";
	}
	
	
}
