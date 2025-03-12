package com.axxera.ocpp.model.ocpp;

import java.util.Date;

public class OCPPMeterValuesPojo {
	
	private long portId;
	
	private String reqId;

	private Date timeStamp;

	private long transactionId;

	private String unit;

	private double value;

	private String sessionId;

	private long stationId=0;

	private Date startTimeStamp;

	private double finalCost;
	
	private double currentImport_Value;

	private double engActImpRegr_Value;
	
	private double soc;
	
	private double powerActiveImport;
	
	private String EnergyActiveExportRegisterUnit;
	private String EnergyActiveImportRegisterUnit;
	private String EnergyReactiveImportRegisterUnit;
	private String EnergyReactiveExportRegisterUnit;
	private String EnergyActiveExportIntervalUnit;
	private String EnergyActiveImportIntervalUnit;
	private String EnergyReactiveExportIntervalUnit;
	private String EnergyReactiveImportIntervalUnit;
	private String PowerActiveExportUnit;
	private String PowerActiveImportUnit;
	private String PowerOfferedUnit;
	private String PowerReactiveExportUnit;
	private String PowerReactiveImportUnit;
	private String PowerFactorUnit;
	private String CurrentImportUnit;
	private String CurrentExportUnit;
	private String CurrentOfferedUnit;
	private String VoltageUnit;
	private String FrequencyUnit;
	private String TemperatureUnit;
	private String SoCUnit;
	private String RPMUnit;

	private double EnergyActiveExportRegisterValue;
	private double EnergyActiveImportRegisterValue;
	private double EnergyActiveImportRegisterDiffValue;
	private double EnergyReactiveImportRegisterValue;
	private double EnergyReactiveExportRegisterValue;
	private double EnergyActiveExportIntervalValue;
	private double EnergyActiveImportIntervalValue;
	private double EnergyReactiveExportIntervalValue;
	private double EnergyReactiveImportIntervalValue;
	private double PowerActiveExportValue;
	private double PowerActiveImportValue;
	private double PowerOfferedValue;
	private double PowerReactiveExportValue;
	private double PowerReactiveImportValue;
	private double PowerFactorValue;
	private double CurrentImportValue;
	private double CurrentExportValue;
	private double CurrentOfferedValue;
	private double VoltageValue;
	private double FrequencyValue;
	private double TemperatureValue;
	private double SoCValue;
	private double RPMValue;
	
	private double CurrentImportDiffValue;
	private double PowerActiveImportDiffValue;
	private double SoCDiffValue;
	
	
	public String getEnergyReactiveExportRegisterUnit() {
		return EnergyReactiveExportRegisterUnit;
	}
	public void setEnergyReactiveExportRegisterUnit(String energyReactiveExportRegisterUnit) {
		EnergyReactiveExportRegisterUnit = energyReactiveExportRegisterUnit;
	}
	public String getPowerActiveImportUnit() {
		return PowerActiveImportUnit;
	}
	public void setPowerActiveImportUnit(String powerActiveImportUnit) {
		PowerActiveImportUnit = powerActiveImportUnit;
	}
	public double getEnergyReactiveExportRegisterValue() {
		return EnergyReactiveExportRegisterValue;
	}
	public void setEnergyReactiveExportRegisterValue(double energyReactiveExportRegisterValue) {
		EnergyReactiveExportRegisterValue = energyReactiveExportRegisterValue;
	}
	public double getPowerActiveImportValue() {
		return PowerActiveImportValue;
	}
	public void setPowerActiveImportValue(double powerActiveImportValue) {
		PowerActiveImportValue = powerActiveImportValue;
	}
	public double getEnergyActiveImportRegisterValue() {
		return EnergyActiveImportRegisterValue;
	}
	public void setEnergyActiveImportRegisterValue(double energyActiveImportRegisterValue) {
		EnergyActiveImportRegisterValue = energyActiveImportRegisterValue;
	}
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
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
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public Date getStartTimeStamp() {
		return startTimeStamp;
	}
	public void setStartTimeStamp(Date startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	public double getFinalCost() {
		return finalCost;
	}
	public void setFinalCost(double finalCost) {
		this.finalCost = finalCost;
	}
	public double getCurrentImport_Value() {
		return currentImport_Value;
	}
	public void setCurrentImport_Value(double currentImport_Value) {
		this.currentImport_Value = currentImport_Value;
	}
	public double getEngActImpRegr_Value() {
		return engActImpRegr_Value;
	}
	public void setEngActImpRegr_Value(double engActImpRegr_Value) {
		this.engActImpRegr_Value = engActImpRegr_Value;
	}
	public double getSoc() {
		return soc;
	}
	public void setSoc(double soc) {
		this.soc = soc;
	}
	public double getPowerActiveImport() {
		return powerActiveImport;
	}
	public void setPowerActiveImport(double powerActiveImport) {
		this.powerActiveImport = powerActiveImport;
	}
	public String getEnergyActiveExportRegisterUnit() {
		return EnergyActiveExportRegisterUnit;
	}
	public void setEnergyActiveExportRegisterUnit(String energyActiveExportRegisterUnit) {
		EnergyActiveExportRegisterUnit = energyActiveExportRegisterUnit;
	}
	public String getEnergyActiveImportRegisterUnit() {
		return EnergyActiveImportRegisterUnit;
	}
	public void setEnergyActiveImportRegisterUnit(String energyActiveImportRegisterUnit) {
		EnergyActiveImportRegisterUnit = energyActiveImportRegisterUnit;
	}
	public String getEnergyReactiveImportRegisterUnit() {
		return EnergyReactiveImportRegisterUnit;
	}
	public void setEnergyReactiveImportRegisterUnit(String energyReactiveImportRegisterUnit) {
		EnergyReactiveImportRegisterUnit = energyReactiveImportRegisterUnit;
	}
	public String getEnergyActiveExportIntervalUnit() {
		return EnergyActiveExportIntervalUnit;
	}
	public void setEnergyActiveExportIntervalUnit(String energyActiveExportIntervalUnit) {
		EnergyActiveExportIntervalUnit = energyActiveExportIntervalUnit;
	}
	public String getEnergyActiveImportIntervalUnit() {
		return EnergyActiveImportIntervalUnit;
	}
	public void setEnergyActiveImportIntervalUnit(String energyActiveImportIntervalUnit) {
		EnergyActiveImportIntervalUnit = energyActiveImportIntervalUnit;
	}
	public String getEnergyReactiveExportIntervalUnit() {
		return EnergyReactiveExportIntervalUnit;
	}
	public void setEnergyReactiveExportIntervalUnit(String energyReactiveExportIntervalUnit) {
		EnergyReactiveExportIntervalUnit = energyReactiveExportIntervalUnit;
	}
	public String getEnergyReactiveImportIntervalUnit() {
		return EnergyReactiveImportIntervalUnit;
	}
	public void setEnergyReactiveImportIntervalUnit(String energyReactiveImportIntervalUnit) {
		EnergyReactiveImportIntervalUnit = energyReactiveImportIntervalUnit;
	}
	public String getPowerActiveExportUnit() {
		return PowerActiveExportUnit;
	}
	public void setPowerActiveExportUnit(String powerActiveExportUnit) {
		PowerActiveExportUnit = powerActiveExportUnit;
	}
	public String getPowerOfferedUnit() {
		return PowerOfferedUnit;
	}
	public void setPowerOfferedUnit(String powerOfferedUnit) {
		PowerOfferedUnit = powerOfferedUnit;
	}
	public String getPowerReactiveExportUnit() {
		return PowerReactiveExportUnit;
	}
	public void setPowerReactiveExportUnit(String powerReactiveExportUnit) {
		PowerReactiveExportUnit = powerReactiveExportUnit;
	}
	public String getPowerReactiveImportUnit() {
		return PowerReactiveImportUnit;
	}
	public void setPowerReactiveImportUnit(String powerReactiveImportUnit) {
		PowerReactiveImportUnit = powerReactiveImportUnit;
	}
	public String getPowerFactorUnit() {
		return PowerFactorUnit;
	}
	public void setPowerFactorUnit(String powerFactorUnit) {
		PowerFactorUnit = powerFactorUnit;
	}
	public String getCurrentImportUnit() {
		return CurrentImportUnit;
	}
	public void setCurrentImportUnit(String currentImportUnit) {
		CurrentImportUnit = currentImportUnit;
	}
	public String getCurrentExportUnit() {
		return CurrentExportUnit;
	}
	public void setCurrentExportUnit(String currentExportUnit) {
		CurrentExportUnit = currentExportUnit;
	}
	public String getCurrentOfferedUnit() {
		return CurrentOfferedUnit;
	}
	public void setCurrentOfferedUnit(String currentOfferedUnit) {
		CurrentOfferedUnit = currentOfferedUnit;
	}
	public String getVoltageUnit() {
		return VoltageUnit;
	}
	public void setVoltageUnit(String voltageUnit) {
		VoltageUnit = voltageUnit;
	}
	public String getFrequencyUnit() {
		return FrequencyUnit;
	}
	public void setFrequencyUnit(String frequencyUnit) {
		FrequencyUnit = frequencyUnit;
	}
	public String getTemperatureUnit() {
		return TemperatureUnit;
	}
	public void setTemperatureUnit(String temperatureUnit) {
		TemperatureUnit = temperatureUnit;
	}
	public String getSoCUnit() {
		return SoCUnit;
	}
	public void setSoCUnit(String soCUnit) {
		SoCUnit = soCUnit;
	}
	public String getRPMUnit() {
		return RPMUnit;
	}
	public void setRPMUnit(String rPMUnit) {
		RPMUnit = rPMUnit;
	}
	public double getEnergyActiveExportRegisterValue() {
		return EnergyActiveExportRegisterValue;
	}
	public void setEnergyActiveExportRegisterValue(double energyActiveExportRegisterValue) {
		EnergyActiveExportRegisterValue = energyActiveExportRegisterValue;
	}

	public double getEnergyReactiveImportRegisterValue() {
		return EnergyReactiveImportRegisterValue;
	}
	public void setEnergyReactiveImportRegisterValue(double energyReactiveImportRegisterValue) {
		EnergyReactiveImportRegisterValue = energyReactiveImportRegisterValue;
	}
	public double getEnergyActiveExportIntervalValue() {
		return EnergyActiveExportIntervalValue;
	}
	public void setEnergyActiveExportIntervalValue(double energyActiveExportIntervalValue) {
		EnergyActiveExportIntervalValue = energyActiveExportIntervalValue;
	}
	public double getEnergyActiveImportIntervalValue() {
		return EnergyActiveImportIntervalValue;
	}
	public void setEnergyActiveImportIntervalValue(double energyActiveImportIntervalValue) {
		EnergyActiveImportIntervalValue = energyActiveImportIntervalValue;
	}
	public double getEnergyReactiveExportIntervalValue() {
		return EnergyReactiveExportIntervalValue;
	}
	public void setEnergyReactiveExportIntervalValue(double energyReactiveExportIntervalValue) {
		EnergyReactiveExportIntervalValue = energyReactiveExportIntervalValue;
	}
	public double getEnergyReactiveImportIntervalValue() {
		return EnergyReactiveImportIntervalValue;
	}
	public void setEnergyReactiveImportIntervalValue(double energyReactiveImportIntervalValue) {
		EnergyReactiveImportIntervalValue = energyReactiveImportIntervalValue;
	}
	public double getPowerActiveExportValue() {
		return PowerActiveExportValue;
	}
	public void setPowerActiveExportValue(double powerActiveExportValue) {
		PowerActiveExportValue = powerActiveExportValue;
	}
	public double getPowerOfferedValue() {
		return PowerOfferedValue;
	}
	public void setPowerOfferedValue(double powerOfferedValue) {
		PowerOfferedValue = powerOfferedValue;
	}
	public double getPowerReactiveExportValue() {
		return PowerReactiveExportValue;
	}
	public void setPowerReactiveExportValue(double powerReactiveExportValue) {
		PowerReactiveExportValue = powerReactiveExportValue;
	}
	public double getPowerReactiveImportValue() {
		return PowerReactiveImportValue;
	}
	public void setPowerReactiveImportValue(double powerReactiveImportValue) {
		PowerReactiveImportValue = powerReactiveImportValue;
	}
	public double getPowerFactorValue() {
		return PowerFactorValue;
	}
	public void setPowerFactorValue(double powerFactorValue) {
		PowerFactorValue = powerFactorValue;
	}
	public double getCurrentImportValue() {
		return CurrentImportValue;
	}
	public void setCurrentImportValue(double currentImportValue) {
		CurrentImportValue = currentImportValue;
	}
	public double getCurrentExportValue() {
		return CurrentExportValue;
	}
	public void setCurrentExportValue(double currentExportValue) {
		CurrentExportValue = currentExportValue;
	}
	public double getCurrentOfferedValue() {
		return CurrentOfferedValue;
	}
	public void setCurrentOfferedValue(double currentOfferedValue) {
		CurrentOfferedValue = currentOfferedValue;
	}
	public double getVoltageValue() {
		return VoltageValue;
	}
	public void setVoltageValue(double voltageValue) {
		VoltageValue = voltageValue;
	}
	public double getFrequencyValue() {
		return FrequencyValue;
	}
	public void setFrequencyValue(double frequencyValue) {
		FrequencyValue = frequencyValue;
	}
	public double getTemperatureValue() {
		return TemperatureValue;
	}
	public void setTemperatureValue(double temperatureValue) {
		TemperatureValue = temperatureValue;
	}
	public double getSoCValue() {
		return SoCValue;
	}
	public void setSoCValue(double soCValue) {
		SoCValue = soCValue;
	}
	public double getRPMValue() {
		return RPMValue;
	}
	public void setRPMValue(double rPMValue) {
		RPMValue = rPMValue;
	}
	
	public double getCurrentImportDiffValue() {
		return CurrentImportDiffValue;
	}
	public void setCurrentImportDiffValue(double currentImportDiffValue) {
		CurrentImportDiffValue = currentImportDiffValue;
	}
	public double getPowerActiveImportDiffValue() {
		return PowerActiveImportDiffValue;
	}
	public void setPowerActiveImportDiffValue(double powerActiveImportDiffValue) {
		PowerActiveImportDiffValue = powerActiveImportDiffValue;
	}
	public double getSoCDiffValue() {
		return SoCDiffValue;
	}
	public void setSoCDiffValue(double soCDiffValue) {
		SoCDiffValue = soCDiffValue;
	}
	@Override
	public String toString() {
		return "OCPPMeterValuesPojo [portId=" + portId + ", reqId=" + reqId + ", timeStamp=" + timeStamp
				+ ", transactionId=" + transactionId + ", unit=" + unit + ", value=" + value + ", sessionId="
				+ sessionId + ", stationId=" + stationId + ", startTimeStamp=" + startTimeStamp + ", finalCost="
				+ finalCost + ", currentImport_Value=" + currentImport_Value + ", engActImpRegr_Value="
				+ engActImpRegr_Value + ", soc=" + soc + ", powerActiveImport=" + powerActiveImport
				+ ", EnergyActiveExportRegisterUnit=" + EnergyActiveExportRegisterUnit
				+ ", EnergyActiveImportRegisterUnit=" + EnergyActiveImportRegisterUnit
				+ ", EnergyReactiveImportRegisterUnit=" + EnergyReactiveImportRegisterUnit
				+ ", EnergyReactiveExportRegisterUnit=" + EnergyReactiveExportRegisterUnit
				+ ", EnergyActiveExportIntervalUnit=" + EnergyActiveExportIntervalUnit
				+ ", EnergyActiveImportIntervalUnit=" + EnergyActiveImportIntervalUnit
				+ ", EnergyReactiveExportIntervalUnit=" + EnergyReactiveExportIntervalUnit
				+ ", EnergyReactiveImportIntervalUnit=" + EnergyReactiveImportIntervalUnit + ", PowerActiveExportUnit="
				+ PowerActiveExportUnit + ", PowerActiveImportUnit=" + PowerActiveImportUnit + ", PowerOfferedUnit="
				+ PowerOfferedUnit + ", PowerReactiveExportUnit=" + PowerReactiveExportUnit
				+ ", PowerReactiveImportUnit=" + PowerReactiveImportUnit + ", PowerFactorUnit=" + PowerFactorUnit
				+ ", CurrentImportUnit=" + CurrentImportUnit + ", CurrentExportUnit=" + CurrentExportUnit
				+ ", CurrentOfferedUnit=" + CurrentOfferedUnit + ", VoltageUnit=" + VoltageUnit + ", FrequencyUnit="
				+ FrequencyUnit + ", TemperatureUnit=" + TemperatureUnit + ", SoCUnit=" + SoCUnit + ", RPMUnit="
				+ RPMUnit + ", EnergyActiveExportRegisterValue=" + EnergyActiveExportRegisterValue
				+ ", EnergyActiveImportRegisterValue=" + EnergyActiveImportRegisterValue
				+ ", EnergyActiveImportRegisterDiffValue=" + EnergyActiveImportRegisterDiffValue
				+ ", EnergyReactiveImportRegisterValue=" + EnergyReactiveImportRegisterValue
				+ ", EnergyReactiveExportRegisterValue=" + EnergyReactiveExportRegisterValue
				+ ", EnergyActiveExportIntervalValue=" + EnergyActiveExportIntervalValue
				+ ", EnergyActiveImportIntervalValue=" + EnergyActiveImportIntervalValue
				+ ", EnergyReactiveExportIntervalValue=" + EnergyReactiveExportIntervalValue
				+ ", EnergyReactiveImportIntervalValue=" + EnergyReactiveImportIntervalValue
				+ ", PowerActiveExportValue=" + PowerActiveExportValue + ", PowerActiveImportValue="
				+ PowerActiveImportValue + ", PowerOfferedValue=" + PowerOfferedValue + ", PowerReactiveExportValue="
				+ PowerReactiveExportValue + ", PowerReactiveImportValue=" + PowerReactiveImportValue
				+ ", PowerFactorValue=" + PowerFactorValue + ", CurrentImportValue=" + CurrentImportValue
				+ ", CurrentExportValue=" + CurrentExportValue + ", CurrentOfferedValue=" + CurrentOfferedValue
				+ ", VoltageValue=" + VoltageValue + ", FrequencyValue=" + FrequencyValue + ", TemperatureValue="
				+ TemperatureValue + ", SoCValue=" + SoCValue + ", RPMValue=" + RPMValue + ", CurrentImportDiffValue="
				+ CurrentImportDiffValue + ", PowerActiveImportDiffValue=" + PowerActiveImportDiffValue
				+ ", SoCDiffValue=" + SoCDiffValue + "]";
	}
	public double getEnergyActiveImportRegisterDiffValue() {
		return EnergyActiveImportRegisterDiffValue;
	}
	public void setEnergyActiveImportRegisterDiffValue(double energyActiveImportRegisterDiffValue) {
		EnergyActiveImportRegisterDiffValue = energyActiveImportRegisterDiffValue;
	}
	

}
