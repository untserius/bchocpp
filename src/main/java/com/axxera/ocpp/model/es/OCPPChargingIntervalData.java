package com.axxera.ocpp.model.es;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;


@Document(indexName = "#{@environment.getProperty('es.ocppchargingintervallogs')}")
public class OCPPChargingIntervalData {

	@Id
	private String id;
	
	@Field(type = FieldType.Long, name = "stationId")
	private long stationId;
	
	@Field(type = FieldType.Long, name = "portId")
	private long portId;
	
	@Field(type = FieldType.Keyword, name = "sessionId")
	private String sessionId;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "createdTimestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date createdTimestamp;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "startTimestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date startTimestamp;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "endTimestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date endTimestamp;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "nextIntervalTimestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date nextIntervalTimestamp;
	
	@Field(type = FieldType.Double, name = "kWUsed")
	private double kWUsed;
	
	@Field(type = FieldType.Double, name = "spent")
	private double spent;
	
	@Field(type = FieldType.Double, name = "transactionCost")
	private double transactionCost;
	
	@Field(type = FieldType.Keyword, name = "idTag")
	private String idTag;
	
	@Field(type = FieldType.Keyword, name = "portPriceUnit")
	private String portPriceUnit;
	
	@Field(type = FieldType.Double, name = "portPrice")
	private double portPrice;
	
	@Field(type = FieldType.Keyword, name = "reasonForTermination")
	private String reasonForTermination;
	
	@Field(type = FieldType.Double, name = "intervalDuration")
	private double intervalDuration;
	
	@Field(type = FieldType.Long, name = "userId")
	private long userId;
	
	@Field(type = FieldType.Double, name = "powerActiveImport")
	private double powerActiveImport;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "startInterval")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date startInterval;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "endInterval")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date endInterval;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public Date getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Date endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public Date getNextIntervalTimestamp() {
		return nextIntervalTimestamp;
	}

	public void setNextIntervalTimestamp(Date nextIntervalTimestamp) {
		this.nextIntervalTimestamp = nextIntervalTimestamp;
	}

	public double getkWUsed() {
		return kWUsed;
	}

	public void setkWUsed(double kWUsed) {
		this.kWUsed = kWUsed;
	}

	public double getSpent() {
		return spent;
	}

	public void setSpent(double spent) {
		this.spent = spent;
	}

	public double getTransactionCost() {
		return transactionCost;
	}

	public void setTransactionCost(double transactionCost) {
		this.transactionCost = transactionCost;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public String getPortPriceUnit() {
		return portPriceUnit;
	}

	public void setPortPriceUnit(String portPriceUnit) {
		this.portPriceUnit = portPriceUnit;
	}

	public double getPortPrice() {
		return portPrice;
	}

	public void setPortPrice(double portPrice) {
		this.portPrice = portPrice;
	}

	public String getReasonForTermination() {
		return reasonForTermination;
	}

	public void setReasonForTermination(String reasonForTermination) {
		this.reasonForTermination = reasonForTermination;
	}

	public double getIntervalDuration() {
		return intervalDuration;
	}

	public void setIntervalDuration(double intervalDuration) {
		this.intervalDuration = intervalDuration;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getPowerActiveImport() {
		return powerActiveImport;
	}

	public void setPowerActiveImport(double powerActiveImport) {
		this.powerActiveImport = powerActiveImport;
	}

	public Date getStartInterval() {
		return startInterval;
	}

	public void setStartInterval(Date startInterval) {
		this.startInterval = startInterval;
	}

	public Date getEndInterval() {
		return endInterval;
	}

	public void setEndInterval(Date endInterval) {
		this.endInterval = endInterval;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Override
	public String toString() {
		return "OCPPChargingIntervalData [id=" + id + ", stationId=" + stationId + ", portId=" + portId + ", sessionId="
				+ sessionId + ", createdTimestamp=" + createdTimestamp + ", startTimestamp=" + startTimestamp
				+ ", endTimestamp=" + endTimestamp + ", nextIntervalTimestamp=" + nextIntervalTimestamp + ", kWUsed="
				+ kWUsed + ", spent=" + spent + ", transactionCost=" + transactionCost + ", idTag=" + idTag
				+ ", portPriceUnit=" + portPriceUnit + ", portPrice=" + portPrice + ", reasonForTermination="
				+ reasonForTermination + ", intervalDuration=" + intervalDuration + ", userId=" + userId
				+ ", powerActiveImport=" + powerActiveImport + ", startInterval=" + startInterval + ", endInterval="
				+ endInterval + "]";
	}
}
