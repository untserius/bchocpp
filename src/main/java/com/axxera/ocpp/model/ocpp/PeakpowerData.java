package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "peakpower_data")
public class PeakpowerData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long networkId;
	private String createdTimeStamp;
	private double totalOptimization;
	private Date pdtDate;
	private Date date;

	public long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}

	public double getTotalOptimization() {
		return totalOptimization;
	}

	public void setTotalOptimization(double totalOptimization) {
		this.totalOptimization = totalOptimization;
	}

	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getPdtDate() {
		return pdtDate;
	}

	public void setPdtDate(Date pdtDate) {
		this.pdtDate = pdtDate;
	}

	@Override
	public String toString() {
		return "PeakpowerData [networkId=" + networkId + ", createdTimeStamp=" + createdTimeStamp
				+ ", totalOptimization=" + totalOptimization + ", pdtDate=" + pdtDate + ", date=" + date + "]";
	}

}
