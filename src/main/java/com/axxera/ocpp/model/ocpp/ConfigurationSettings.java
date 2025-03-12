package com.axxera.ocpp.model.ocpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "configurationSettings")
public class ConfigurationSettings extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private String port;
	private String host;
	private String protocol;
	private String serverKey;
	private Long orgId;
	private String orgName;
	private String logoName;
	private String address;
	private String phoneNumber;
	private String legacykey;
	private String portalLink;
	private String bgColor;
	private Long countryId;
	private String supportEmail;
	private String whatsAppNumber;
	private String whatsAppToken;
	private String driverPortalLink;
	private String operationPortalLink;
	private String fromEmail;
	private Double minkWh;

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "port")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Column(name = "host")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "protocol")
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "serverKey")
	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	@Column(name = "orgId")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "orgName")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "logoName")
	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	@Column(name = "")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phoneNumber")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "legacykey")
	public String getLegacykey() {
		return legacykey;
	}

	public void setLegacykey(String legacykey) {
		this.legacykey = legacykey;
	}

	@Column(name = "portalLink")
	public String getPortalLink() {
		return portalLink;
	}

	public void setPortalLink(String portalLink) {
		this.portalLink = portalLink;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	@Column(name = "countryId")
	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public String getWhatsAppNumber() {
		return whatsAppNumber;
	}

	public void setWhatsAppNumber(String whatsAppNumber) {
		this.whatsAppNumber = whatsAppNumber;
	}

	public String getWhatsAppToken() {
		return whatsAppToken;
	}

	public void setWhatsAppToken(String whatsAppToken) {
		this.whatsAppToken = whatsAppToken;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSupportEmail() {
		return supportEmail;
	}

	public void setSupportEmail(String supportEmail) {
		this.supportEmail = supportEmail;
	}

	public String getDriverPortalLink() {
		return driverPortalLink;
	}

	public void setDriverPortalLink(String driverPortalLink) {
		this.driverPortalLink = driverPortalLink;
	}

	public String getOperationPortalLink() {
		return operationPortalLink;
	}

	public void setOperationPortalLink(String operationPortalLink) {
		this.operationPortalLink = operationPortalLink;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public Double getMinkWh() {
		return minkWh;
	}

	public void setMinkWh(Double minkWh) {
		this.minkWh = minkWh;
	}

	@Override
	public String toString() {
		return "ConfigurationSettings [email=" + email + ", password=" + password + ", port=" + port + ", host=" + host
				+ ", protocol=" + protocol + ", serverKey=" + serverKey + ", orgId=" + orgId + ", orgName=" + orgName
				+ ", logoName=" + logoName + ", address=" + address + ", phoneNumber=" + phoneNumber + ", legacykey="
				+ legacykey + ", portalLink=" + portalLink + ", bgColor=" + bgColor + ", countryId=" + countryId
				+ ", supportEmail=" + supportEmail + ", whatsAppNumber=" + whatsAppNumber + ", whatsAppToken="
				+ whatsAppToken + ", driverPortalLink=" + driverPortalLink + ", operationPortalLink="
				+ operationPortalLink + ", fromEmail=" + fromEmail + ", minkWh=" + minkWh + "]";
	}

}