package com.axxera.ocpp.model.ocpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "session_pricings")
public class session_pricings extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sessionId;
	private long metervaluesCount;
	private long transactionId;
    
    @Type(type = "json")
    @Column(columnDefinition="TEXT", length = 255)
    private String cost_info;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getCost_info() {
		return cost_info;
	}

	public void setCost_info(String cost_info) {
		this.cost_info = cost_info;
	}

	public long getMetervaluesCount() {
		return metervaluesCount;
	}

	public void setMetervaluesCount(long metervaluesCount) {
		this.metervaluesCount = metervaluesCount;
	}

	@Override
	public String toString() {
		return "session_pricings [sessionId=" + sessionId + ", metervaluesCount=" + metervaluesCount
				+ ", transactionId=" + transactionId + ", cost_info=" + cost_info + "]";
	}

	
	
}
