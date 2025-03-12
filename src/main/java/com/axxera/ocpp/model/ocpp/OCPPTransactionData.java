package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "ocpp_TransactionData")
public class OCPPTransactionData extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String sessionId;
    private long portId;
    private String userType;
    private String billingCases="Freeven";
    private String stationMode;
	private long transactionId;
	private boolean rstp;
	private boolean dgNoZeroBal;
	private double exceedingHours;
	private double exceedingMints;
	private long driverGroupId;
	private String paymentMode;
	private long orgId;
	private long tariffId;
	private Double minkWhEnergy;
	private Double meterStart;
	private Date idleStartTime;
	private String reasonForTer;
	private boolean stop;
	private String idleStatus;
	private String kWhStatus;
	private double maximumRevenue;
	private double maxkWh;
    private boolean offlineTransaction;
    @Type(type = "json")
    @Column(columnDefinition="TEXT")
    private String tariff_prices;
    
    @Type(type = "json")
    @Column(columnDefinition="TEXT")
    private String free_prices;
    
    @Type(type = "json")
    @Column(columnDefinition="TEXT")
    private String stn_obj;
    
    @Type(type = "json")
    @Column(columnDefinition="TEXT")
    private String site_obj;
    
    @Type(type = "json")
    @Column(columnDefinition="TEXT")
    private String user_obj;
    
    @Type(type = "json")
    @Column(columnDefinition="TEXT")
    private String reward;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getBillingCases() {
		return billingCases;
	}

	public void setBillingCases(String billingCases) {
		this.billingCases = billingCases;
	}

	public String getStationMode() {
		return stationMode;
	}

	public void setStationMode(String stationMode) {
		this.stationMode = stationMode;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isRstp() {
		return rstp;
	}

	public void setRstp(boolean rstp) {
		this.rstp = rstp;
	}

	public String getTariff_prices() {
		return tariff_prices;
	}

	public void setTariff_prices(String tariff_prices) {
		this.tariff_prices = tariff_prices;
	}

	public long getDriverGroupId() {
		return driverGroupId;
	}

	public void setDriverGroupId(long driverGroupId) {
		this.driverGroupId = driverGroupId;
	}

	public double getExceedingHours() {
		return exceedingHours;
	}

	public void setExceedingHours(double exceedingHours) {
		this.exceedingHours = exceedingHours;
	}

	public double getExceedingMints() {
		return exceedingMints;
	}

	public void setExceedingMints(double exceedingMints) {
		this.exceedingMints = exceedingMints;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getStn_obj() {
		return stn_obj;
	}

	public void setStn_obj(String stn_obj) {
		this.stn_obj = stn_obj;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getUser_obj() {
		return user_obj;
	}

	public void setUser_obj(String user_obj) {
		this.user_obj = user_obj;
	}

	public long getTariffId() {
		return tariffId;
	}

	public void setTariffId(long tariffId) {
		this.tariffId = tariffId;
	}

	public String getSite_obj() {
		return site_obj;
	}

	public void setSite_obj(String site_obj) {
		this.site_obj = site_obj;
	}

	public String getFree_prices() {
		return free_prices;
	}

	public void setFree_prices(String free_prices) {
		this.free_prices = free_prices;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public Double getMinkWhEnergy() {
		return minkWhEnergy;
	}

	public void setMinkWhEnergy(Double minkWhEnergy) {
		this.minkWhEnergy = minkWhEnergy;
	}

	public Double getMeterStart() {
		return meterStart;
	}

	public void setMeterStart(Double meterStart) {
		this.meterStart = meterStart;
	}

	public boolean isDgNoZeroBal() {
		return dgNoZeroBal;
	}

	public void setDgNoZeroBal(boolean dgNoZeroBal) {
		this.dgNoZeroBal = dgNoZeroBal;
	}
	public Date getIdleStartTime() {
		return idleStartTime;
	}

	public void setIdleStartTime(Date idleStartTime) {
		this.idleStartTime = idleStartTime;
	}

	public String getReasonForTer() {
		return reasonForTer;
	}

	public void setReasonForTer(String reasonForTer) {
		this.reasonForTer = reasonForTer;
	}
	
	@Column(name = "stop", columnDefinition = "bit default 0")
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public String getIdleStatus() {
		return idleStatus;
	}

	public void setIdleStatus(String idleStatus) {
		this.idleStatus = idleStatus;
	}

	public String getkWhStatus() {
		return kWhStatus;
	}

	public void setkWhStatus(String kWhStatus) {
		this.kWhStatus = kWhStatus;
	}

	public double getMaximumRevenue() {
		return maximumRevenue;
	}

	public void setMaximumRevenue(double maximumRevenue) {
		this.maximumRevenue = maximumRevenue;
	}

	public double getMaxkWh() {
		return maxkWh;
	}

	public void setMaxkWh(double maxkWh) {
		this.maxkWh = maxkWh;
	}

	public boolean isOfflineTransaction() {
		return offlineTransaction;
	}

	public void setOfflineTransaction(boolean offlineTransaction) {
		this.offlineTransaction = offlineTransaction;
	}

	@Override
	public String toString() {
		return "OCPPTransactionData [sessionId=" + sessionId + ", portId=" + portId + ", userType=" + userType
				+ ", billingCases=" + billingCases + ", stationMode=" + stationMode + ", transactionId=" + transactionId
				+ ", rstp=" + rstp + ", dgNoZeroBal=" + dgNoZeroBal + ", exceedingHours=" + exceedingHours
				+ ", exceedingMints=" + exceedingMints + ", driverGroupId=" + driverGroupId + ", paymentMode="
				+ paymentMode + ", orgId=" + orgId + ", tariffId=" + tariffId + ", minkWhEnergy=" + minkWhEnergy
				+ ", meterStart=" + meterStart + ", idleStartTime=" + idleStartTime + ", reasonForTer=" + reasonForTer
				+ ", stop=" + stop + ", idleStatus=" + idleStatus + ", kWhStatus=" + kWhStatus + ", maximumRevenue="
				+ maximumRevenue + ", maxkWh=" + maxkWh + ", tariff_prices=" + tariff_prices + ", free_prices="
				+ free_prices + ", stn_obj=" + stn_obj + ", site_obj=" + site_obj + ", user_obj=" + user_obj
				+ ", reward=" + reward + "]";
	}
	
}
