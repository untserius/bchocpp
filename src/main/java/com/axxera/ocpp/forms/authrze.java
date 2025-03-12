package com.axxera.ocpp.forms;

import java.util.Map;

import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;

public class authrze {

	private String auth_uniId;
	private String status;
	private long userId;
	private String stnRefNum;
	private String idTag;
	
	private boolean payAsUGoTxn;
	private boolean ocpiTxn;
	private boolean registeredTxn;
	private boolean unknownTxn;
	private boolean driverGrpIdTagTxn;
	private boolean idtagProfTxn;
	private boolean auth_validated;
	
	private Map<String,Object>stn_obj;
	private Map<String,Object>user_obj;
	
	private OCPPActiveTransaction activeTxnData;
	
	private long site_orgId=1;
	private String rst_clientId;
	private String reason;

	public String getAuth_uniId() {
		return auth_uniId;
	}

	public void setAuth_uniId(String auth_uniId) {
		this.auth_uniId = auth_uniId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getStnRefNum() {
		return stnRefNum;
	}

	public void setStnRefNum(String stnRefNum) {
		this.stnRefNum = stnRefNum;
	}

	public Map<String, Object> getStn_obj() {
		return stn_obj;
	}

	public void setStn_obj(Map<String, Object> stn_obj) {
		this.stn_obj = stn_obj;
	}

	public Map<String, Object> getUser_obj() {
		return user_obj;
	}

	public void setUser_obj(Map<String, Object> user_obj) {
		this.user_obj = user_obj;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public boolean isPayAsUGoTxn() {
		return payAsUGoTxn;
	}

	public void setPayAsUGoTxn(boolean payAsUGoTxn) {
		this.payAsUGoTxn = payAsUGoTxn;
	}

	public boolean isOcpiTxn() {
		return ocpiTxn;
	}

	public void setOcpiTxn(boolean ocpiTxn) {
		this.ocpiTxn = ocpiTxn;
	}

	public boolean isRegisteredTxn() {
		return registeredTxn;
	}

	public void setRegisteredTxn(boolean registeredTxn) {
		this.registeredTxn = registeredTxn;
	}

	public boolean isAuth_validated() {
		return auth_validated;
	}

	public void setAuth_validated(boolean auth_validated) {
		this.auth_validated = auth_validated;
	}

	public boolean isUnknownTxn() {
		return unknownTxn;
	}

	public void setUnknownTxn(boolean unknownTxn) {
		this.unknownTxn = unknownTxn;
	}

	public long getSite_orgId() {
		return site_orgId;
	}

	public void setSite_orgId(long site_orgId) {
		this.site_orgId = site_orgId;
	}

	public String getRst_clientId() {
		return rst_clientId;
	}

	public void setRst_clientId(String rst_clientId) {
		this.rst_clientId = rst_clientId;
	}

	public boolean isDriverGrpIdTagTxn() {
		return driverGrpIdTagTxn;
	}

	public void setDriverGrpIdTagTxn(boolean driverGrpIdTagTxn) {
		this.driverGrpIdTagTxn = driverGrpIdTagTxn;
	}

	public boolean isIdtagProfTxn() {
		return idtagProfTxn;
	}

	public void setIdtagProfTxn(boolean idtagProfTxn) {
		this.idtagProfTxn = idtagProfTxn;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public OCPPActiveTransaction getActiveTxnData() {
		return activeTxnData;
	}

	public void setActiveTxnData(OCPPActiveTransaction activeTxnData) {
		this.activeTxnData = activeTxnData;
	}

	@Override
	public String toString() {
		return "authrze [auth_uniId=" + auth_uniId + ", status=" + status + ", userId=" + userId + ", stnRefNum="
				+ stnRefNum + ", idTag=" + idTag + ", payAsUGoTxn=" + payAsUGoTxn + ", ocpiTxn=" + ocpiTxn
				+ ", registeredTxn=" + registeredTxn + ", unknownTxn=" + unknownTxn + ", driverGrpIdTagTxn="
				+ driverGrpIdTagTxn + ", idtagProfTxn=" + idtagProfTxn + ", auth_validated=" + auth_validated
				+ ", stn_obj=" + stn_obj + ", user_obj=" + user_obj + ", activeTxnData=" + activeTxnData
				+ ", site_orgId=" + site_orgId + ", rst_clientId=" + rst_clientId + ", reason=" + reason + "]";
	}

}
