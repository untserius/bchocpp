package com.axxera.ocpp.model.es;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import com.fasterxml.jackson.annotation.JsonFormat;


@Document(indexName = "#{@environment.getProperty('es.stationlogs')}")
public class StationLogs {
	
	@Id
	private String id;
	
	@Field(type = FieldType.Keyword, name = "stnRefNum")
	private String stnRefNum;
	
	@Field(type = FieldType.Keyword, name = "stationId")
	private long stationId;

	@Field(type = FieldType.Keyword, name = "request")
	private String request;

	@Field(type = FieldType.Keyword, name = "response")
	private String response;

	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "createdTimestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date createdTimestamp;
	
	@Field(type = FieldType.Keyword, name = "reqType")
	private String requestType = "";

	@Field(type = FieldType.Keyword, name = "transactionId")
	private String transactionId;

	@Field(type = FieldType.Integer, name = "connectorId")
	private long connectorId;

	@Field(type = FieldType.Keyword, name = "portStatus")
	private String portStatus;

	@Field(type =FieldType.Date ,format = DateFormat.date_optional_time , name = "timestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date timestamp;

	@Field(type = FieldType.Keyword, name = "idTag")
	private String idTag;

	@Field(type = FieldType.Double,name = "meterReading")
	private double  meterReading;

	@Field(type = FieldType.Keyword, name = "stopTransactionReason")
	private String stopTransactionReason;

	@Field(type = FieldType.Keyword, name = "chargePointModel")
	private String chargePointModel;

	@Field(type = FieldType.Keyword, name = "chargePointVendor")
	private String chargePointVendor;

	@Field(type = FieldType.Keyword, name = "chargePointSerialNumber")
	private String chargePointSerialNumber;

	@Field(type = FieldType.Keyword, name = "chargeBoxSerialNumber")
	private String chargeBoxSerialNumber;

	@Field(type = FieldType.Keyword, name = "firmwareVersion")
	private String firmwareVersion;
	
	@Field(type = FieldType.Keyword, name = "reservationId")
	private long reservationId;
	
	@Field(type = FieldType.Keyword, name = "statusNotifyInfo")
	private String statusNotifyInfo;
	
	@Field(type = FieldType.Keyword, name = "vendorErrorCode")
	private String vendorErrorCode;
	
	@Field(type = FieldType.Keyword, name = "statusNotifyErrorCode")
	private String statusNotifyErrorCode;
	
	@Field(type = FieldType.Keyword, name = "requestId")
	private String requestId;
	
	@Field(type = FieldType.Keyword, name = "status")
	private String status;
	
	@Field(type = FieldType.Keyword, name = "clientId")
	private String clientId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStnRefNum() {
		return stnRefNum;
	}

	public void setStnRefNum(String stnRefNum) {
		this.stnRefNum = stnRefNum;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public String getPortStatus() {
		return portStatus;
	}

	public void setPortStatus(String portStatus) {
		this.portStatus = portStatus;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public double getMeterReading() {
		return meterReading;
	}

	public void setMeterReading(double meterReading) {
		this.meterReading = meterReading;
	}

	public String getChargePointModel() {
		return chargePointModel;
	}

	public void setChargePointModel(String chargePointModel) {
		this.chargePointModel = chargePointModel;
	}

	public String getChargePointVendor() {
		return chargePointVendor;
	}

	public void setChargePointVendor(String chargePointVendor) {
		this.chargePointVendor = chargePointVendor;
	}

	public String getChargePointSerialNumber() {
		return chargePointSerialNumber;
	}

	public void setChargePointSerialNumber(String chargePointSerialNumber) {
		this.chargePointSerialNumber = chargePointSerialNumber;
	}

	public String getChargeBoxSerialNumber() {
		return chargeBoxSerialNumber;
	}

	public void setChargeBoxSerialNumber(String chargeBoxSerialNumber) {
		this.chargeBoxSerialNumber = chargeBoxSerialNumber;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getStopTransactionReason() {
		return stopTransactionReason;
	}

	public void setStopTransactionReason(String stopTransactionReason) {
		this.stopTransactionReason = stopTransactionReason;
	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public String getStatusNotifyInfo() {
		return statusNotifyInfo;
	}

	public void setStatusNotifyInfo(String statusNotifyInfo) {
		this.statusNotifyInfo = statusNotifyInfo;
	}

	public String getVendorErrorCode() {
		return vendorErrorCode;
	}

	public void setVendorErrorCode(String vendorErrorCode) {
		this.vendorErrorCode = vendorErrorCode;
	}

	public String getStatusNotifyErrorCode() {
		return statusNotifyErrorCode;
	}

	public void setStatusNotifyErrorCode(String statusNotifyErrorCode) {
		this.statusNotifyErrorCode = statusNotifyErrorCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}


	@Override
	public String toString() {
		return "StationLogs [id=" + id + ", stnRefNum=" + stnRefNum + ", stationId=" + stationId + ", request="
				+ request + ", response=" + response + ", createdTimestamp=" + createdTimestamp + ", requestType="
				+ requestType + ", transactionId=" + transactionId + ", connectorId=" + connectorId + ", portStatus="
				+ portStatus + ", timestamp=" + timestamp + ", idTag=" + idTag + ", meterReading=" + meterReading
				+ ", stopTransactionReason=" + stopTransactionReason + ", chargePointModel=" + chargePointModel
				+ ", chargePointVendor=" + chargePointVendor + ", chargePointSerialNumber=" + chargePointSerialNumber
				+ ", chargeBoxSerialNumber=" + chargeBoxSerialNumber + ", firmwareVersion=" + firmwareVersion
				+ ", reservationId=" + reservationId + ", statusNotifyInfo=" + statusNotifyInfo + ", vendorErrorCode="
				+ vendorErrorCode + ", statusNotifyErrorCode=" + statusNotifyErrorCode + ", requestId=" + requestId
				+ ", status=" + status + ", clientId=" + clientId + "]";
	}
	
}
