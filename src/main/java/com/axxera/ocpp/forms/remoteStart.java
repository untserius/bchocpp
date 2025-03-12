package com.axxera.ocpp.forms;

import java.util.Map;

public class remoteStart {

	private long stationId;
	private long portId;
	private long connectorId;
	private String stage;
	private String idTag;
	private long stn_orgId;
	private boolean powerSharging;
	private String rst_rcvd_client;
	private String stn_referNo;
	private Map<String,Object> stnObj;
	private Map<String,Object> userObj;
	private Map<String,Object> siteObj;
	
	private String rst_unqReqId;
	private String rst_request;
	private String rst_msg;
	private String rst_reason;
	private String rst_paymentType;
	private String rst_rewardType;
	private String rst_emailId;
	
	private boolean payAsUGoTxn;
	private boolean registeredTxn;
	private boolean rst_Valid;
	private boolean unknownTxn;
	private boolean selfCharging;
	private boolean preProdTxn;
	private boolean driverGrpIdTagTxn;
	private boolean idTagProfileTxn;
	
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public boolean isSelfCharging() {
		return selfCharging;
	}
	public void setSelfCharging(boolean selfCharging) {
		this.selfCharging = selfCharging;
	}
	public String getRst_unqReqId() {
		return rst_unqReqId;
	}
	public void setRst_unqReqId(String rst_unqReqId) {
		this.rst_unqReqId = rst_unqReqId;
	}
	public String getRst_request() {
		return rst_request;
	}
	public void setRst_request(String rst_request) {
		this.rst_request = rst_request;
	}
	public String getRst_msg() {
		return rst_msg;
	}
	public void setRst_msg(String rst_msg) {
		this.rst_msg = rst_msg;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public long getStn_orgId() {
		return stn_orgId;
	}
	public void setStn_orgId(long stn_orgId) {
		this.stn_orgId = stn_orgId;
	}
	public String getRst_rcvd_client() {
		return rst_rcvd_client;
	}
	public void setRst_rcvd_client(String rst_rcvd_client) {
		this.rst_rcvd_client = rst_rcvd_client;
	}
	public String getStn_referNo() {
		return stn_referNo;
	}
	public void setStn_referNo(String stn_referNo) {
		this.stn_referNo = stn_referNo;
	}
	public String getRst_reason() {
		return rst_reason;
	}
	public void setRst_reason(String rst_reason) {
		this.rst_reason = rst_reason;
	}
	public String getRst_paymentType() {
		return rst_paymentType;
	}
	public void setRst_paymentType(String rst_paymentType) {
		this.rst_paymentType = rst_paymentType;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public String getRst_emailId() {
		return rst_emailId;
	}
	public void setRst_emailId(String rst_emailId) {
		this.rst_emailId = rst_emailId;
	}
	public String getRst_rewardType() {
		return rst_rewardType;
	}
	public void setRst_rewardType(String rst_rewardType) {
		this.rst_rewardType = rst_rewardType;
	}
	public boolean isRst_Valid() {
		return rst_Valid;
	}
	public void setRst_Valid(boolean rst_Valid) {
		this.rst_Valid = rst_Valid;
	}
	public Map<String, Object> getStnObj() {
		return stnObj;
	}
	public void setStnObj(Map<String, Object> stnObj) {
		this.stnObj = stnObj;
	}
	public Map<String, Object> getUserObj() {
		return userObj;
	}
	public void setUserObj(Map<String, Object> userObj) {
		this.userObj = userObj;
	}
	public boolean isPreProdTxn() {
		return preProdTxn;
	}
	public void setPreProdTxn(boolean preProdTxn) {
		this.preProdTxn = preProdTxn;
	}
	public Map<String, Object> getSiteObj() {
		return siteObj;
	}
	public void setSiteObj(Map<String, Object> siteObj) {
		this.siteObj = siteObj;
	}
	public boolean isDriverGrpIdTagTxn() {
		return driverGrpIdTagTxn;
	}
	public void setDriverGrpIdTagTxn(boolean driverGrpIdTagTxn) {
		this.driverGrpIdTagTxn = driverGrpIdTagTxn;
	}
	public boolean isIdTagProfileTxn() {
		return idTagProfileTxn;
	}
	public void setIdTagProfileTxn(boolean idTagProfileTxn) {
		this.idTagProfileTxn = idTagProfileTxn;
	}
	public boolean isUnknownTxn() {
		return unknownTxn;
	}
	public void setUnknownTxn(boolean unknownTxn) {
		this.unknownTxn = unknownTxn;
	}
	public boolean isPayAsUGoTxn() {
		return payAsUGoTxn;
	}
	public void setPayAsUGoTxn(boolean payAsUGoTxn) {
		this.payAsUGoTxn = payAsUGoTxn;
	}
	public boolean isRegisteredTxn() {
		return registeredTxn;
	}
	public void setRegisteredTxn(boolean registeredTxn) {
		this.registeredTxn = registeredTxn;
	}
	public boolean isPowerSharging() {
		return powerSharging;
	}
	public void setPowerSharging(boolean powerSharging) {
		this.powerSharging = powerSharging;
	}
	
}
