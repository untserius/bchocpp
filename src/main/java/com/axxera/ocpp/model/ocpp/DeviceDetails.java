package com.axxera.ocpp.model.ocpp;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "device_details")
public class DeviceDetails extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appInfo;
	private String deviceName;
	private String deviceType;
	private String deviceVersion;
	private String deviceToken;
	private String legacyKey;
	private Long orgId;
	private long userId;

	public DeviceDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceDetails(String appInfo, String deviceName, String deviceType, String deviceVersion, String deviceToken,
			long userId) {
		super();
		this.appInfo = appInfo;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.deviceVersion = deviceVersion;
		this.deviceToken = deviceToken;
		this.userId = userId;
	}

	public String getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLegacyKey() {
		return legacyKey;
	}

	public void setLegacyKey(String legacyKey) {
		this.legacyKey = legacyKey;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "DeviceDetails [appInfo=" + appInfo + ", deviceName=" + deviceName + ", deviceType=" + deviceType
				+ ", deviceVersion=" + deviceVersion + ", deviceToken=" + deviceToken + ", legacyKey=" + legacyKey
				+ ", orgId=" + orgId + ", userId=" + userId + "]";
	}
}
