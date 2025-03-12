package com.axxera.ocpp.forms;

import java.util.Date;
import java.util.Map;

import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;
import com.axxera.ocpp.model.ocpp.OCPPTransactionData;
import com.axxera.ocpp.model.ocpp.Session;

public class startTxn {
	
	private int transactionId;
	private long reserveId;
	private String st_unqReqId;
	private String idTag;
	private String rfidOrPhone;
	private Date startTime;
	private double meterStartReading;
	private String stnRefNum;
	private long connectorId;
	
	private String chargeSessUniqId;
	private String paymentType;
	private String rewardType;
	
	private boolean txnValid;
	private boolean payAsUGoTxn;
	private boolean ocpiTxn;
	private boolean registeredTxn;
	private boolean preProdTxn;
	private boolean offlineTxn;
	private boolean unknownTxn;
	private boolean driverGrpIdTagTxn;
	private boolean idtagProfTxn;
	private long stnId;
	private long portId;
	private String userName;
	private String reason;
	private double currencyRate;
	private long paygId;
	private Map<String,Object> stnObj;
	private Map<String,Object> userObj;
	private Map<String,Object> siteObj;
	private Map<String,Object> configs;
	
	private long driverGroupId;
	private String driverGroupName;
	
	private boolean site_ocpi;
	private long site_orgId=1;
	
	private String user_crncy_HexCode;
	private String user_crncy_Code;
	private String user_crncy_Char;
	private long user_orgId=1;
	
	private String rst_clientId;
	
//	private boolean siteTimeFlag;
	private boolean selfCharging;
	private boolean powerSharing;
	private OCPPTransactionData txnData;
	private OCPPActiveTransaction activeTxnData;
	private Session session;
	private String txnInitiate;
	
	
	
	public String getTxnInitiate() {
		return txnInitiate;
	}
	public void setTxnInitiate(String txnInitiate) {
		this.txnInitiate = txnInitiate;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public long getReserveId() {
		return reserveId;
	}
	public void setReserveId(long reserveId) {
		this.reserveId = reserveId;
	}
	public String getSt_unqReqId() {
		return st_unqReqId;
	}
	public void setSt_unqReqId(String st_unqReqId) {
		this.st_unqReqId = st_unqReqId;
	}
	public String getChargeSessUniqId() {
		return chargeSessUniqId;
	}
	public void setChargeSessUniqId(String chargeSessUniqId) {
		this.chargeSessUniqId = chargeSessUniqId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	public boolean isTxnValid() {
		return txnValid;
	}
	public void setTxnValid(boolean txnValid) {
		this.txnValid = txnValid;
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
	public boolean isPreProdTxn() {
		return preProdTxn;
	}
	public void setPreProdTxn(boolean preProdTxn) {
		this.preProdTxn = preProdTxn;
	}
	public boolean isOfflineTxn() {
		return offlineTxn;
	}
	public void setOfflineTxn(boolean offlineTxn) {
		this.offlineTxn = offlineTxn;
	}
	public long getStnId() {
		return stnId;
	}
	public void setStnId(long stnId) {
		this.stnId = stnId;
	}
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Map<String, Object> getStnObj() {
		return stnObj;
	}
	public void setStnObj(Map<String, Object> stnObj) {
		this.stnObj = stnObj;
	}
	public boolean isSite_ocpi() {
		return site_ocpi;
	}
	public void setSite_ocpi(boolean site_ocpi) {
		this.site_ocpi = site_ocpi;
	}
	public long getSite_orgId() {
		return site_orgId;
	}
	public void setSite_orgId(long site_orgId) {
		this.site_orgId = site_orgId;
	}
	public String getUser_crncy_HexCode() {
		return user_crncy_HexCode;
	}
	public void setUser_crncy_HexCode(String user_crncy_HexCode) {
		this.user_crncy_HexCode = user_crncy_HexCode;
	}
	public String getUser_crncy_Code() {
		return user_crncy_Code;
	}
	public void setUser_crncy_Code(String user_crncy_Code) {
		this.user_crncy_Code = user_crncy_Code;
	}
	public String getUser_crncy_Char() {
		return user_crncy_Char;
	}
	public void setUser_crncy_Char(String user_crncy_Char) {
		this.user_crncy_Char = user_crncy_Char;
	}
	public long getUser_orgId() {
		return user_orgId;
	}
	public void setUser_orgId(long user_orgId) {
		this.user_orgId = user_orgId;
	}
	public Map<String, Object> getUserObj() {
		return userObj;
	}
	public void setUserObj(Map<String, Object> userObj) {
		this.userObj = userObj;
	}
	public Map<String, Object> getConfigs() {
		return configs;
	}
	public void setConfigs(Map<String, Object> configs) {
		this.configs = configs;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public boolean isSelfCharging() {
		return selfCharging;
	}
	public void setSelfCharging(boolean selfCharging) {
		this.selfCharging = selfCharging;
	}
	public OCPPTransactionData getTxnData() {
		return txnData;
	}
	public void setTxnData(OCPPTransactionData txnData) {
		this.txnData = txnData;
	}
	public double getMeterStartReading() {
		return meterStartReading;
	}
	public void setMeterStartReading(double meterStartReading) {
		this.meterStartReading = meterStartReading;
	}
	public Map<String, Object> getSiteObj() {
		return siteObj;
	}
	public void setSiteObj(Map<String, Object> siteObj) {
		this.siteObj = siteObj;
	}
	public long getDriverGroupId() {
		return driverGroupId;
	}
	public void setDriverGroupId(long driverGroupId) {
		this.driverGroupId = driverGroupId;
	}
	public String getDriverGroupName() {
		return driverGroupName;
	}
	public void setDriverGroupName(String driverGroupName) {
		this.driverGroupName = driverGroupName;
	}
	public long getPaygId() {
		return paygId;
	}
	public void setPaygId(long paygId) {
		this.paygId = paygId;
	}
	public OCPPActiveTransaction getActiveTxnData() {
		return activeTxnData;
	}
	public void setActiveTxnData(OCPPActiveTransaction activeTxnData) {
		this.activeTxnData = activeTxnData;
	}
	public String getRst_clientId() {
		return rst_clientId;
	}
	public void setRst_clientId(String rst_clientId) {
		this.rst_clientId = rst_clientId;
	}
	public String getStnRefNum() {
		return stnRefNum;
	}
	public void setStnRefNum(String stnRefNum) {
		this.stnRefNum = stnRefNum;
	}
	public boolean isUnknownTxn() {
		return unknownTxn;
	}
	public void setUnknownTxn(boolean unknownTxn) {
		this.unknownTxn = unknownTxn;
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
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	
	public boolean isPowerSharing() {
		return powerSharing;
	}
	public void setPowerSharing(boolean powerSharing) {
		this.powerSharing = powerSharing;
	}
	public String getRfidOrPhone() {
		return rfidOrPhone;
	}
	public void setRfidOrPhone(String rfidOrPhone) {
		this.rfidOrPhone = rfidOrPhone;
	}
@Override
public String toString() {
	return "startTxn [transactionId=" + transactionId + ", reserveId=" + reserveId + ", st_unqReqId=" + st_unqReqId
			+ ", idTag=" + idTag + ", rfidOrPhone=" + rfidOrPhone + ", startTime=" + startTime + ", meterStartReading="
			+ meterStartReading + ", stnRefNum=" + stnRefNum + ", connectorId=" + connectorId + ", chargeSessUniqId="
			+ chargeSessUniqId + ", paymentType=" + paymentType + ", rewardType=" + rewardType + ", txnValid="
			+ txnValid + ", payAsUGoTxn=" + payAsUGoTxn + ", ocpiTxn=" + ocpiTxn + ", registeredTxn=" + registeredTxn
			+ ", preProdTxn=" + preProdTxn + ", offlineTxn=" + offlineTxn + ", unknownTxn=" + unknownTxn
			+ ", driverGrpIdTagTxn=" + driverGrpIdTagTxn + ", idtagProfTxn=" + idtagProfTxn + ", stnId=" + stnId
			+ ", portId=" + portId + ", userName=" + userName + ", reason=" + reason + ", currencyRate=" + currencyRate
			+ ", paygId=" + paygId + ", stnObj=" + stnObj + ", userObj=" + userObj + ", siteObj=" + siteObj
			+ ", configs=" + configs + ", driverGroupId=" + driverGroupId + ", driverGroupName=" + driverGroupName
			+ ", site_ocpi=" + site_ocpi + ", site_orgId=" + site_orgId + ", user_crncy_HexCode=" + user_crncy_HexCode
			+ ", user_crncy_Code=" + user_crncy_Code + ", user_crncy_Char=" + user_crncy_Char + ", user_orgId="
			+ user_orgId + ", rst_clientId=" + rst_clientId + ", selfCharging=" + selfCharging + ", powerSharing="
			+ powerSharing + ", txnData=" + txnData + ", activeTxnData=" + activeTxnData + ", session=" + session
			+ ", txnInitiate=" + txnInitiate + "]";
}
		
}
