package com.axxera.ocpp.rest.message;

import java.util.Date;
import java.util.List;


public class ocpiSessionForm {
	
	private String country_code;
	private String party_id;
	private Date start_date_time;
	private Date end_date_time;
	private long kwh;
	private String auth_method;
	private String authorization_reference;
	private String location_id;
	private String evse_uid;
	private String connector_id;
	private String meter_id;
	private String currency;
	private String status;
	private String idTag;
	private String rstStatus;
	private long stationId;
	
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getParty_id() {
		return party_id;
	}
	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}
	public Date getStart_date_time() {
		return start_date_time;
	}
	public void setStart_date_time(Date start_date_time) {
		this.start_date_time = start_date_time;
	}
	public Date getEnd_date_time() {
		return end_date_time;
	}
	public void setEnd_date_time(Date end_date_time) {
		this.end_date_time = end_date_time;
	}
	public long getKwh() {
		return kwh;
	}
	public void setKwh(long kwh) {
		this.kwh = kwh;
	}
	public String getAuth_method() {
		return auth_method;
	}
	public void setAuth_method(String auth_method) {
		this.auth_method = auth_method;
	}
	public String getAuthorization_reference() {
		return authorization_reference;
	}
	public void setAuthorization_reference(String authorization_reference) {
		this.authorization_reference = authorization_reference;
	}
	public String getLocation_id() {
		return location_id;
	}
	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}
	public String getEvse_uid() {
		return evse_uid;
	}
	public void setEvse_uid(String evse_uid) {
		this.evse_uid = evse_uid;
	}
	public String getConnector_id() {
		return connector_id;
	}
	public void setConnector_id(String connector_id) {
		this.connector_id = connector_id;
	}
	public String getMeter_id() {
		return meter_id;
	}
	public void setMeter_id(String meter_id) {
		this.meter_id = meter_id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public String getRstStatus() {
		return rstStatus;
	}
	public void setRstStatus(String rstStatus) {
		this.rstStatus = rstStatus;
	}
	@Override
	public String toString() {
		return "ocpiSessionForm [country_code=" + country_code + ", party_id=" + party_id + ", start_date_time="
				+ start_date_time + ", end_date_time=" + end_date_time + ", kwh=" + kwh + ", auth_method=" + auth_method
				+ ", authorization_reference=" + authorization_reference + ", location_id=" + location_id
				+ ", evse_uid=" + evse_uid + ", connector_id=" + connector_id + ", meter_id=" + meter_id + ", currency="
				+ currency + ", status=" + status + ", idTag=" + idTag + ", rstStatus=" + rstStatus + ", stationId="
				+ stationId + "]";
	}
	
}
