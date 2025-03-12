package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_reset")
public class OCPPReset extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name = "resetDate", length = 10)
	private Date resetDate;

	private String resetType;
	
	private String requestID;

	public OCPPReset(String status, Date resetDate, String resetType) {
		super();
		this.status = status;
		this.resetDate = resetDate;
		this.resetType = resetType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public String getResetType() {
		return resetType;
	}

	public void setResetType(String resetType) {
		this.resetType = resetType;
	}

	
	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	@Override
	public String toString() {
		return "OCPPReset [status=" + status + ", resetDate=" + resetDate + ", resetType=" + resetType + "]";
	}

	public OCPPReset() {
		super();
		// TODO Auto-generated constructor stub
	}

}
