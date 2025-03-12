package com.axxera.ocpp.model.ocpp;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Proxy(lazy = false)
@Table(name = "statusNotification")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler", "port" })
public class PortStatusNotification extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String status;

	private String errorCode;
	@Temporal(TemporalType.DATE)
	@Column(name = "timeStamp", length = 10)
	private Date timeStamp;

	private String stationId;
	private String vendorErrorCode;
	private String info;
	private boolean inOperativeFlag;
	private String requestId;
	
	//private String reason;

	private Port port;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "port_id")
	public Port getPort() {
		return port;
	}

	public void setPort(Port port) {
		this.port = port;
	}

	public String getStatus() {
		if(status==null)
		return "Unavailable";
		
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getVendorErrorCode() {
		return vendorErrorCode;
	}

	public void setVendorErrorCode(String vendorErrorCode) {
		this.vendorErrorCode = vendorErrorCode;
	}

	public boolean isInOperativeFlag() {
		return inOperativeFlag;
	}

	public void setInOperativeFlag(boolean inOperativeFlag) {
		this.inOperativeFlag = inOperativeFlag;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "PortStatusNotification [status=" + status + ", errorCode=" + errorCode + ", timeStamp=" + timeStamp
				+ ", stationId=" + stationId + ", vendorErrorCode=" + vendorErrorCode + ", info=" + info
				+ ", inOperativeFlag=" + inOperativeFlag + ", requestId=" + requestId + ", port=" + port + "]";
	}
}
