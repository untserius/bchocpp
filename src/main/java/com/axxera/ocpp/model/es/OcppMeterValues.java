package com.axxera.ocpp.model.es;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(indexName = "#{@environment.getProperty('es.metervaluelogs')}")
public class OcppMeterValues {
	
	@Id
	private String id;
		
	@Field(type = FieldType.Long, name = "portId")
	private long portId;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "createdTimestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date createdTimestamp;
	
	@Field(type = FieldType.Keyword, name = "transactionId")
	private String transactionId;
	
	@Field(type =FieldType.Date, format = DateFormat.date_optional_time , name = "meterValueTimeStamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date meterValueTimeStamp;
	
	@Field(type = FieldType.Keyword, name = "sessionId")
	private String sessionId;
	
	@Field(type = FieldType.Double, name = "EnergyActiveImportRegister_Value")
	private double EnergyActiveImportRegisterValue;
	
	@Field(type = FieldType.Double, name = "EnergyActiveImportRegisterDiff_Value")
	private double EnergyActiveImportRegisterDiffValue;
	
	@Field(type = FieldType.Double, name = "PowerActiveImport_Value")
	private double PowerActiveImportValue;
	
	@Field(type = FieldType.Keyword, name = "PowerActiveImport_Unit")
	private String PowerActiveImportUnit;
	
	
	@Field(type = FieldType.Keyword, name = "EnergyActiveExportRegister_Unit")
	private String EnergyActiveExportRegisterUnit;
	
	@Field(type = FieldType.Keyword, name = "EnergyActiveImportRegister_Unit")
	private String EnergyActiveImportRegisterUnit;
	
	@Field(type = FieldType.Keyword, name = "EnergyReactiveImportRegister_Unit")
	private String EnergyReactiveImportRegisterUnit;
	
	@Field(type = FieldType.Keyword, name = "EnergyReactiveExportRegister_Unit")
	private String EnergyReactiveExportRegisterUnit;
	
	@Field(type = FieldType.Keyword, name = "EnergyActiveExportInterval_Unit")
	private String EnergyActiveExportIntervalUnit;
	
	@Field(type = FieldType.Keyword, name = "EnergyActiveImportInterval_Unit")
	private String EnergyActiveImportIntervalUnit;
	
	@Field(type = FieldType.Keyword, name = "EnergyReactiveExportInterval_Unit")
	private String EnergyReactiveExportIntervalUnit;
	
	@Field(type = FieldType.Keyword, name = "EnergyReactiveImportInterval_Unit")
	private String EnergyReactiveImportIntervalUnit;
	
	@Field(type = FieldType.Keyword, name = "PowerActiveExport_Unit")
	private String PowerActiveExportUnit;
	
	@Field(type = FieldType.Keyword, name = "PowerOffered_Unit")
	private String PowerOfferedUnit;
	
	@Field(type = FieldType.Keyword, name = "PowerReactiveExport_Unit")
	private String PowerReactiveExportUnit;
	
	@Field(type = FieldType.Keyword, name = "PowerReactiveImport_Unit")
	private String PowerReactiveImportUnit;
	
	@Field(type = FieldType.Keyword, name = "PowerFactor_Unit")
	private String PowerFactorUnit;
	
	@Field(type = FieldType.Keyword, name = "CurrentImport_Unit")
	private String CurrentImportUnit;
	
	@Field(type = FieldType.Keyword, name = "CurrentExport_Unit")
	private String CurrentExportUnit;
	
	@Field(type = FieldType.Keyword, name = "CurrentOffered_Unit")
	private String CurrentOfferedUnit;
	
	@Field(type = FieldType.Keyword, name = "Voltage_Unit")
	private String VoltageUnit;
	
	@Field(type = FieldType.Keyword, name = "Frequency_Unit")
	private String FrequencyUnit;
	
	@Field(type = FieldType.Keyword, name = "Temperature_Unit")
	private String TemperatureUnit;
	
	@Field(type = FieldType.Keyword, name = "SoC_Unit")
	private String SoCUnit;
	
	@Field(type = FieldType.Keyword, name = "RPM_Unit")
	private String RPMUnit;

	@Field(type = FieldType.Double, name = "EnergyActiveExportRegister_Value")
	private double EnergyActiveExportRegisterValue;
	
	@Field(type = FieldType.Double, name = "EnergyReactiveExportRegister_Value")
	private double EnergyReactiveExportRegisterValue;
	
	@Field(type = FieldType.Double, name = "EnergyReactiveImportRegister_Value")
	private double EnergyReactiveImportRegisterValue;
	
	@Field(type = FieldType.Double, name = "EnergyActiveExportInterval_Value")
	private double EnergyActiveExportIntervalValue;
	
	@Field(type = FieldType.Double, name = "EnergyActiveImportInterval_Value")
	private double EnergyActiveImportIntervalValue;
	
	@Field(type = FieldType.Double, name = "EnergyReactiveExportInterval_Value")
	private double EnergyReactiveExportIntervalValue;
	
	@Field(type = FieldType.Double, name = "EnergyReactiveImportInterval_Value")
	private double EnergyReactiveImportIntervalValue;
	
	@Field(type = FieldType.Double, name = "PowerActiveExport_Value")
	private double PowerActiveExportValue;
	
	@Field(type = FieldType.Double, name = "PowerOffered_Value")
	private double PowerOfferedValue;
	
	@Field(type = FieldType.Double, name = "PowerReactiveExport_Value")
	private double PowerReactiveExportValue;
	
	@Field(type = FieldType.Double, name = "PowerReactiveImport_Value")
	private double PowerReactiveImportValue;
	
	@Field(type = FieldType.Double, name = "PowerFactor_Value")
	private double PowerFactorValue;
	
	@Field(type = FieldType.Double, name = "CurrentImport_Value")
	private double CurrentImportValue;
	
	@Field(type = FieldType.Double, name = "CurrentExport_Value")
	private double CurrentExportValue;
	
	@Field(type = FieldType.Double, name = "CurrentOffered_Value")
	private double CurrentOfferedValue;
	
	@Field(type = FieldType.Double, name = "Voltage_Value")
	private double VoltageValue;
	
	@Field(type = FieldType.Double, name = "Frequency_Value")
	private double FrequencyValue;
	
	@Field(type = FieldType.Double, name = "Temperature_Value")
	private double TemperatureValue;
	
	@Field(type = FieldType.Double, name = "SoC_Value")
	private double SoCValue;
	
	@Field(type = FieldType.Double, name = "RPM_Value")
	private double RPMValue;
	
	@Field(type = FieldType.Double, name = "CurrentImportDiff_Value")
	private double CurrentImportDiffValue;
	
	@Field(type = FieldType.Double, name = "PowerActiveImportDiff_Value")
	private double PowerActiveImportDiffValue;
	
	@Field(type = FieldType.Double, name = "SoCDiff_Value")
	private double SoCDiffValue;
	
	
	public String getEnergyReactiveExportRegisterUnit() {
		return EnergyReactiveExportRegisterUnit;
	}

	public void setEnergyReactiveExportRegisterUnit(String energyReactiveExportRegisterUnit) {
		EnergyReactiveExportRegisterUnit = energyReactiveExportRegisterUnit;
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

	public double getEnergyReactiveExportRegisterValue() {
		return EnergyReactiveExportRegisterValue;
	}

	public void setEnergyReactiveExportRegisterValue(double energyReactiveExportRegisterValue) {
		EnergyReactiveExportRegisterValue = energyReactiveExportRegisterValue;
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public double getPowerActiveImportValue() {
		return PowerActiveImportValue;
	}

	public void setPowerActiveImportValue(double powerActiveImportValue) {
		PowerActiveImportValue = powerActiveImportValue;
	}

	public String getPowerActiveImportUnit() {
		return PowerActiveImportUnit;
	}

	public void setPowerActiveImportUnit(String powerActiveImportUnit) {
		PowerActiveImportUnit = powerActiveImportUnit;
	}

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public Date getMeterValueTimeStamp() {
		return meterValueTimeStamp;
	}

	public void setMeterValueTimeStamp(Date meterValueTimeStamp) {
		this.meterValueTimeStamp = meterValueTimeStamp;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getEnergyActiveImportRegisterValue() {
		return EnergyActiveImportRegisterValue;
	}

	public void setEnergyActiveImportRegisterValue(double energyActiveImportRegisterValue) {
		EnergyActiveImportRegisterValue = energyActiveImportRegisterValue;
	}

	public double getEnergyActiveImportRegisterDiffValue() {
		return EnergyActiveImportRegisterDiffValue;
	}

	public void setEnergyActiveImportRegisterDiffValue(double energyActiveImportRegisterDiffValue) {
		EnergyActiveImportRegisterDiffValue = energyActiveImportRegisterDiffValue;
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
		return "OcppMeterValues [id=" + id + ", portId=" + portId + ", createdTimestamp=" + createdTimestamp
				+ ", transactionId=" + transactionId + ", meterValueTimeStamp=" + meterValueTimeStamp + ", sessionId="
				+ sessionId + ", EnergyActiveImportRegisterValue=" + EnergyActiveImportRegisterValue
				+ ", EnergyActiveImportRegisterDiffValue=" + EnergyActiveImportRegisterDiffValue
				+ ", PowerActiveImportValue=" + PowerActiveImportValue + ", PowerActiveImportUnit="
				+ PowerActiveImportUnit + ", EnergyActiveExportRegisterUnit=" + EnergyActiveExportRegisterUnit
				+ ", EnergyActiveImportRegisterUnit=" + EnergyActiveImportRegisterUnit
				+ ", EnergyReactiveImportRegisterUnit=" + EnergyReactiveImportRegisterUnit
				+ ", EnergyReactiveExportRegisterUnit=" + EnergyReactiveExportRegisterUnit
				+ ", EnergyActiveExportIntervalUnit=" + EnergyActiveExportIntervalUnit
				+ ", EnergyActiveImportIntervalUnit=" + EnergyActiveImportIntervalUnit
				+ ", EnergyReactiveExportIntervalUnit=" + EnergyReactiveExportIntervalUnit
				+ ", EnergyReactiveImportIntervalUnit=" + EnergyReactiveImportIntervalUnit + ", PowerActiveExportUnit="
				+ PowerActiveExportUnit + ", PowerOfferedUnit=" + PowerOfferedUnit + ", PowerReactiveExportUnit="
				+ PowerReactiveExportUnit + ", PowerReactiveImportUnit=" + PowerReactiveImportUnit
				+ ", PowerFactorUnit=" + PowerFactorUnit + ", CurrentImportUnit=" + CurrentImportUnit
				+ ", CurrentExportUnit=" + CurrentExportUnit + ", CurrentOfferedUnit=" + CurrentOfferedUnit
				+ ", VoltageUnit=" + VoltageUnit + ", FrequencyUnit=" + FrequencyUnit + ", TemperatureUnit="
				+ TemperatureUnit + ", SoCUnit=" + SoCUnit + ", RPMUnit=" + RPMUnit
				+ ", EnergyActiveExportRegisterValue=" + EnergyActiveExportRegisterValue
				+ ", EnergyReactiveExportRegisterValue=" + EnergyReactiveExportRegisterValue
				+ ", EnergyReactiveImportRegisterValue=" + EnergyReactiveImportRegisterValue
				+ ", EnergyActiveExportIntervalValue=" + EnergyActiveExportIntervalValue
				+ ", EnergyActiveImportIntervalValue=" + EnergyActiveImportIntervalValue
				+ ", EnergyReactiveExportIntervalValue=" + EnergyReactiveExportIntervalValue
				+ ", EnergyReactiveImportIntervalValue=" + EnergyReactiveImportIntervalValue
				+ ", PowerActiveExportValue=" + PowerActiveExportValue + ", PowerOfferedValue=" + PowerOfferedValue
				+ ", PowerReactiveExportValue=" + PowerReactiveExportValue + ", PowerReactiveImportValue="
				+ PowerReactiveImportValue + ", PowerFactorValue=" + PowerFactorValue + ", CurrentImportValue="
				+ CurrentImportValue + ", CurrentExportValue=" + CurrentExportValue + ", CurrentOfferedValue="
				+ CurrentOfferedValue + ", VoltageValue=" + VoltageValue + ", FrequencyValue=" + FrequencyValue
				+ ", TemperatureValue=" + TemperatureValue + ", SoCValue=" + SoCValue + ", RPMValue=" + RPMValue
				+ ", CurrentImportDiffValue=" + CurrentImportDiffValue + ", PowerActiveImportDiffValue="
				+ PowerActiveImportDiffValue + ", SoCDiffValue=" + SoCDiffValue + "]";
	}
}
