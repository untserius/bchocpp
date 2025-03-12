package com.axxera.ocpp.model.ocpp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "notify_me")
public class NotifyMe extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String deviceToken;
	private String deviceType;
	private long stationId;
	private long userId;
	private String appVersion;
	
	@Column(name="orgId")
	private long orgId;
	
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@Override
	public String toString() {
		return "NotifyMe [deviceToken=" + deviceToken + ", deviceType=" + deviceType + ", stationId=" + stationId
				+ ", userId=" + userId + ", appVersion=" + appVersion + ", orgId=" + orgId + "]";
	}
}
