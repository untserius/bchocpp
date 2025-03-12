package com.axxera.ocpp.message;

import java.util.ArrayList;
import java.util.List;

import com.axxera.ocpp.model.ocpp.CSChargingProfiles;
import com.axxera.ocpp.utils.Utils;

public class OCPPForm {
	private long stationId;
	private List<Long> stationIdList=new ArrayList<Long>();
	private String stnRefNum;
	private long connectorId;
	private String requestType="";
	private String key;
	private String value;
	private double listVersion;
	private int retries;
	private int retriesIntervals;
	private String startDate;
	private String endDate;
	private String manufacture;
	private String fileName;
	private String retrieveDate;
	private String idTag;
	private String vendorId;
	private String messageId;
	private String validFrom;
	private String validTo;
	private String duration;
	private String startPeriod;
	private String type;
	private int stackLevel;
	private int profileId;
	private long limit;
	private String chargingSchedule;
	private String chargingProfileInfo; 
	private String transactionId;
	private long userId;
	private long reservationId;
	private String chargingProfilePurpose;
	private List<String> localAuthorizationLists=new ArrayList<String>();
	private List<CSChargingProfiles> csChargingProfiles = new ArrayList<CSChargingProfiles>();
	private DataPojo data;
	private long orgId;
	private String clientId;
	private String chargingRateUnit;
	private String uuid;
	private String paymentType="Wallet";
	private String promoCode;
	private String userType;
	private String rewardType;
	private boolean selfCharging;
	private boolean powerSharing;
	
	private String message="";
	private String requestId;
	private String apiRequestId=Utils.uuid();
	
	private long tariffId; 
	
	public DataPojo getData() {
		return data;
	}
	public void setData(DataPojo data) {
		this.data = data;
	}
	
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public List<CSChargingProfiles> getCsChargingProfiles() {
		return csChargingProfiles;
	}
	public void setCsChargingProfiles(List<CSChargingProfiles> csChargingProfiles) {
		this.csChargingProfiles = csChargingProfiles;
	}
	public String getChargingProfilePurpose() {
		return chargingProfilePurpose;
	}
	public void setChargingProfilePurpose(String chargingProfilePurpose) {
		this.chargingProfilePurpose = chargingProfilePurpose;
	}
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public double getListVersion() {
		return listVersion;
	}
	public void setListVersion(double listVersion) {
		this.listVersion = listVersion;
	}
	public int getRetries() {
		return retries;
	}
	public void setRetries(int retries) {
		this.retries = retries;
	}
	public int getRetriesIntervals() {
		return retriesIntervals;
	}
	public void setRetriesIntervals(int retriesIntervals) {
		this.retriesIntervals = retriesIntervals;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRetrieveDate() {
		return retrieveDate;
	}
	public void setRetrieveDate(String retrieveDate) {
		this.retrieveDate = retrieveDate;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStackLevel() {
		return stackLevel;
	}
	public void setStackLevel(int stackLevel) {
		this.stackLevel = stackLevel;
	}
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public String getChargingSchedule() {
		return chargingSchedule;
	}
	public void setChargingSchedule(String chargingSchedule) {
		this.chargingSchedule = chargingSchedule;
	}
	public String getChargingProfileInfo() {
		return chargingProfileInfo;
	}
	public void setChargingProfileInfo(String chargingProfileInfo) {
		this.chargingProfileInfo = chargingProfileInfo;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getReservationId() {
		return reservationId;
	}
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	
	public List<String> getLocalAuthorizationLists() {
		return localAuthorizationLists;
	}
	public void setLocalAuthorizationLists(List<String> localAuthorizationLists) {
		this.localAuthorizationLists = localAuthorizationLists;
	}
	
	public String getStnRefNum() {
		return stnRefNum;
	}
	public void setStnRefNum(String stnRefNum) {
		this.stnRefNum = stnRefNum;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getChargingRateUnit() {
		return chargingRateUnit;
	}
	public void setChargingRateUnit(String chargingRateUnit) {
		this.chargingRateUnit = chargingRateUnit;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public List<Long> getStationIdList() {
		return stationIdList;
	}
	public void setStationIdList(List<Long> stationIdList) {
		this.stationIdList = stationIdList;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	public boolean isSelfCharging() {
		return selfCharging;
	}
	public void setSelfCharging(boolean selfCharging) {
		this.selfCharging = selfCharging;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public boolean isPowerSharing() {
		return powerSharing;
	}
	public void setPowerSharing(boolean powerSharing) {
		this.powerSharing = powerSharing;
	}
	public String getApiRequestId() {
		return apiRequestId;
	}
	public void setApiRequestId(String apiRequestId) {
		this.apiRequestId = apiRequestId;
	}
	
	public long getTariffId() {
		return tariffId;
	}
	public void setTariffId(long tariffId) {
		this.tariffId = tariffId;
	}
	@Override
	public String toString() {
		return "OCPPForm [stationId=" + stationId + ", stationIdList=" + stationIdList + ", stnRefNum=" + stnRefNum
				+ ", connectorId=" + connectorId + ", requestType=" + requestType + ", key=" + key + ", value=" + value
				+ ", listVersion=" + listVersion + ", retries=" + retries + ", retriesIntervals=" + retriesIntervals
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", manufacture=" + manufacture + ", fileName="
				+ fileName + ", retrieveDate=" + retrieveDate + ", idTag=" + idTag + ", vendorId=" + vendorId
				+ ", messageId=" + messageId + ", validFrom=" + validFrom + ", validTo=" + validTo + ", duration="
				+ duration + ", startPeriod=" + startPeriod + ", type=" + type + ", stackLevel=" + stackLevel
				+ ", profileId=" + profileId + ", limit=" + limit + ", chargingSchedule=" + chargingSchedule
				+ ", chargingProfileInfo=" + chargingProfileInfo + ", transactionId=" + transactionId + ", userId="
				+ userId + ", reservationId=" + reservationId + ", chargingProfilePurpose=" + chargingProfilePurpose
				+ ", localAuthorizationLists=" + localAuthorizationLists + ", csChargingProfiles=" + csChargingProfiles
				+ ", data=" + data + ", orgId=" + orgId + ", clientId=" + clientId + ", chargingRateUnit="
				+ chargingRateUnit + ", uuid=" + uuid + ", paymentType=" + paymentType + ", promoCode=" + promoCode
				+ ", userType=" + userType + ", rewardType=" + rewardType + ", selfCharging=" + selfCharging
				+ ", powerSharing=" + powerSharing + ", message=" + message + ", requestId=" + requestId
				+ ", apiRequestId=" + apiRequestId + ", tariffId=" + tariffId + "]";
	}
}	