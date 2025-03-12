package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="preproduction_rfids")
public class preProductionRfids extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private String rfid;
	private Long user_id;
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "preProductionRfids [rfid=" + rfid + ", user_id=" + user_id + "]";
	}
}
