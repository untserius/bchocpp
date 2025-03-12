package com.axxera.ocpp.message;

public class Custom {

	private String stnRefNo ;
	private long orgId;
	private String message;
	private String version;
	public String getStnRefNo() {
		return stnRefNo;
	}
	public void setStnRefNo(String stnRefNo) {
		this.stnRefNo = stnRefNo;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "Custom [stnRefNo=" + stnRefNo + ", orgId=" + orgId + ", message=" + message + ", version=" + version
				+ "]";
	}
}
