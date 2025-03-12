package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "serverProperties")
public class serverProperties extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private String property;
	private String value;
	private String primaryEmail;
	private String primaryPassword;
	private String primaryHost;
	private String primaryPort;
	
	private String secondryEmail;
	private String secondryPassword;
	private String secondryHost;
	private String secondryPort;
	
	private long orgId;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getPrimaryEmail() {
		return primaryEmail;
	}
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}
	public String getPrimaryPassword() {
		return primaryPassword;
	}
	public void setPrimaryPassword(String primaryPassword) {
		this.primaryPassword = primaryPassword;
	}
	public String getPrimaryHost() {
		return primaryHost;
	}
	public void setPrimaryHost(String primaryHost) {
		this.primaryHost = primaryHost;
	}
	public String getPrimaryPort() {
		return primaryPort;
	}
	public void setPrimaryPort(String primaryPort) {
		this.primaryPort = primaryPort;
	}
	public String getSecondryEmail() {
		return secondryEmail;
	}
	public void setSecondryEmail(String secondryEmail) {
		this.secondryEmail = secondryEmail;
	}
	public String getSecondryPassword() {
		return secondryPassword;
	}
	public void setSecondryPassword(String secondryPassword) {
		this.secondryPassword = secondryPassword;
	}
	public String getSecondryHost() {
		return secondryHost;
	}
	public void setSecondryHost(String secondryHost) {
		this.secondryHost = secondryHost;
	}
	public String getSecondryPort() {
		return secondryPort;
	}
	public void setSecondryPort(String secondryPort) {
		this.secondryPort = secondryPort;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	@Override
	public String toString() {
		return "serverProperties [property=" + property + ", value=" + value + ", primaryEmail=" + primaryEmail
				+ ", primaryPassword=" + primaryPassword + ", primaryHost=" + primaryHost + ", primaryPort="
				+ primaryPort + ", secondryEmail=" + secondryEmail + ", secondryPassword=" + secondryPassword
				+ ", secondryHost=" + secondryHost + ", secondryPort=" + secondryPort + ", orgId=" + orgId + "]";
	}
	
}
