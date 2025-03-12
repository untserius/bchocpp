package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ocpp_remoteStartTransaction")
public class OCPPRemoteStartTransaction extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long connectorId;
	private String idTag;
	private String status;
	private String portalStation;
	private String sessionId;
	private long stationId;
	private long userId;
	private long orgId;
	private Date createdDate;
	private String paymentType;
	private boolean selfCharging;
	private String rewardType;
	private String appVersion;
	private String clientId;
	private boolean powerSharing;

	public OCPPRemoteStartTransaction() {
		super();
	}

	public OCPPRemoteStartTransaction(long connectorId, String idTag, String status, String portalStation,
			String sessionId, long stationId, long userId) {
		super();
		this.connectorId = connectorId;
		this.idTag = idTag;
		this.status = status;
		this.portalStation = portalStation;
		this.sessionId = sessionId;
		this.stationId = stationId;
		this.userId = userId;
	}

	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPortalStation() {
		return portalStation;
	}
	public void setPortalStation(String portalStation) {
		this.portalStation = portalStation;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public boolean isSelfCharging() {
		return selfCharging;
	}
	public void setSelfCharging(boolean selfCharging) {
		this.selfCharging = selfCharging;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public boolean isPowerSharing() {
		return powerSharing;
	}

	public void setPowerSharing(boolean powerSharing) {
		this.powerSharing = powerSharing;
	}

	@Override
	public String toString() {
		return "OCPPRemoteStartTransaction [connectorId=" + connectorId + ", idTag=" + idTag + ", status=" + status
				+ ", portalStation=" + portalStation + ", sessionId=" + sessionId + ", stationId=" + stationId
				+ ", userId=" + userId + ", orgId=" + orgId + ", createdDate=" + createdDate + ", paymentType="
				+ paymentType + ", selfCharging=" + selfCharging + ", rewardType=" + rewardType + ", appVersion="
				+ appVersion + ", clientId=" + clientId + ", powerSharing=" + powerSharing + "]";
	}

}
