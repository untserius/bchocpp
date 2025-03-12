package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocpp_sessionData")
public class OCPPSessionsData extends BaseEntity {

	public OCPPSessionsData() {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "startTime", length = 10)
	private Date startTime;
	@Temporal(TemporalType.DATE)
	@Column(name = "endTime", length = 10)
	private Date endTime;
	private String sessionId;
	private long connectorId;
	private long stationId;
	private String idTag;
	private long transactionId;
	private String unit;
	private double wattSecondsUsed;
	private long wattSecondsUsedCount;
	private double kilowattHoursUsed;
	private double revenue;
	private String rfId;
	private long startRequestId;
	private long stopRequestId;
	private String errorCode;
	private String IdTagStatus;
	private String notificationStatus;
	@Temporal(TemporalType.DATE)
	@Column(name = "notificationTimeStamp", length = 10)
	private Date notificationTimeStamp;
	@Temporal(TemporalType.DATE)
	@Column(name = "timeStamp", length = 10)
	private Date timeStamp;
	private long value;
	private boolean energyCounsumptionFlag;
	private String energyActiveImportRegisterUnit="Wh";
	private boolean kWhAlert;
	private boolean revenueAlert;
	private boolean durationAlert;
	private double firstReading;
	private double lastReading;
	private boolean firstMeterValue;
	private boolean connectedNotify;
	private boolean notificationFlag;
	private boolean lowBalanceFlag;
	private double promoCodeUsedTime;

	
	
	public OCPPSessionsData(Date startTime, Date endTime, String rfId, String sessionId, long startRequestId,
			long stopRequestId, long connectorId, String errorCode, String idTag, String idTagStatus,
			String notificationStatus, Date notificationTimeStamp, Date timeStamp, long transactionId, String unit,
			long value) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.sessionId = sessionId;
		this.connectorId = connectorId;
		this.idTag = idTag;
		this.transactionId = transactionId;
		this.unit = unit;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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
	
	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getWattSecondsUsed() {
		return wattSecondsUsed;
	}

	public void setWattSecondsUsed(double wattSecondsUsed) {
		this.wattSecondsUsed = wattSecondsUsed;
	}

	public long getWattSecondsUsedCount() {
		return wattSecondsUsedCount;
	}

	public void setWattSecondsUsedCount(long wattSecondsUsedCount) {
		this.wattSecondsUsedCount = wattSecondsUsedCount;
	}

	public double getKilowattHoursUsed() {
		return kilowattHoursUsed;
	}

	public void setKilowattHoursUsed(double kilowattHoursUsed) {
		this.kilowattHoursUsed = kilowattHoursUsed;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public String getRfId() {
		return rfId;
	}

	public void setRfId(String rfId) {
		this.rfId = rfId;
	}

	public long getStartRequestId() {
		return startRequestId;
	}

	public void setStartRequestId(long startRequestId) {
		this.startRequestId = startRequestId;
	}

	public long getStopRequestId() {
		return stopRequestId;
	}

	public void setStopRequestId(long stopRequestId) {
		this.stopRequestId = stopRequestId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getIdTagStatus() {
		return IdTagStatus;
	}

	public void setIdTagStatus(String idTagStatus) {
		IdTagStatus = idTagStatus;
	}

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Date getNotificationTimeStamp() {
		return notificationTimeStamp;
	}

	public void setNotificationTimeStamp(Date notificationTimeStamp) {
		this.notificationTimeStamp = notificationTimeStamp;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public boolean isEnergyCounsumptionFlag() {
		return energyCounsumptionFlag;
	}

	public void setEnergyCounsumptionFlag(boolean energyCounsumptionFlag) {
		this.energyCounsumptionFlag = energyCounsumptionFlag;
	}

	public String getEnergyActiveImportRegisterUnit() {
		return energyActiveImportRegisterUnit;
	}

	public void setEnergyActiveImportRegisterUnit(String energyActiveImportRegisterUnit) {
		this.energyActiveImportRegisterUnit = energyActiveImportRegisterUnit;
	}

	public boolean iskWhAlert() {
		return kWhAlert;
	}

	public void setkWhAlert(boolean kWhAlert) {
		this.kWhAlert = kWhAlert;
	}

	public boolean isRevenueAlert() {
		return revenueAlert;
	}

	public void setRevenueAlert(boolean revenueAlert) {
		this.revenueAlert = revenueAlert;
	}

	public boolean isDurationAlert() {
		return durationAlert;
	}

	public void setDurationAlert(boolean durationAlert) {
		this.durationAlert = durationAlert;
	}

	public double getFirstReading() {
		return firstReading;
	}

	public void setFirstReading(double firstReading) {
		this.firstReading = firstReading;
	}

	public double getLastReading() {
		return lastReading;
	}

	public void setLastReading(double lastReading) {
		this.lastReading = lastReading;
	}

	public boolean isFirstMeterValue() {
		return firstMeterValue;
	}

	public void setFirstMeterValue(boolean firstMeterValue) {
		this.firstMeterValue = firstMeterValue;
	}

	public boolean isConnectedNotify() {
		return connectedNotify;
	}

	public void setConnectedNotify(boolean connectedNotify) {
		this.connectedNotify = connectedNotify;
	}

	public boolean isNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(boolean notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public boolean isLowBalanceFlag() {
		return lowBalanceFlag;
	}

	public void setLowBalanceFlag(boolean lowBalanceFlag) {
		this.lowBalanceFlag = lowBalanceFlag;
	}

	public double getPromoCodeUsedTime() {
		return promoCodeUsedTime;
	}

	public void setPromoCodeUsedTime(double promoCodeUsedTime) {
		this.promoCodeUsedTime = promoCodeUsedTime;
	}

	@Override
	public String toString() {
		return "OCPPSessionsData [startTime=" + startTime + ", endTime=" + endTime + ", sessionId=" + sessionId
				+ ", connectorId=" + connectorId + ", stationId=" + stationId + ", idTag=" + idTag + ", transactionId="
				+ transactionId + ", unit=" + unit + ", wattSecondsUsed=" + wattSecondsUsed + ", wattSecondsUsedCount="
				+ wattSecondsUsedCount + ", kilowattHoursUsed=" + kilowattHoursUsed + ", revenue=" + revenue + ", rfId="
				+ rfId + ", startRequestId=" + startRequestId + ", stopRequestId=" + stopRequestId + ", errorCode="
				+ errorCode + ", IdTagStatus=" + IdTagStatus + ", notificationStatus=" + notificationStatus
				+ ", notificationTimeStamp=" + notificationTimeStamp + ", timeStamp=" + timeStamp + ", value=" + value
				+ ", energyCounsumptionFlag=" + energyCounsumptionFlag + ", energyActiveImportRegisterUnit="
				+ energyActiveImportRegisterUnit + ", kWhAlert=" + kWhAlert + ", revenueAlert=" + revenueAlert
				+ ", durationAlert=" + durationAlert + ", firstReading=" + firstReading + ", lastReading=" + lastReading
				+ ", firstMeterValue=" + firstMeterValue + ", connectedNotify=" + connectedNotify
				+ ", notificationFlag=" + notificationFlag + ", lowBalanceFlag=" + lowBalanceFlag
				+ ", promoCodeUsedTime=" + promoCodeUsedTime + "]";
	}

	
}
