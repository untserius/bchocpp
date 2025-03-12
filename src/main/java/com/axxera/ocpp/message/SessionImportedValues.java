package com.axxera.ocpp.message;

import java.util.Date;

import com.axxera.ocpp.model.ocpp.AccountTransactions;
import com.axxera.ocpp.model.ocpp.Port;

public class SessionImportedValues {
	
	//MeterValues
	private String currentImportContext;
	private String currentImportFormat;
	private String currentImportLocation;
	private String currentImportPhase;
	private String currentImportUnit;
	private String currentImportValue;
	private String currentImportMeasurand;

	private String currentOfferedContext;
	private String currentOfferedFormat;
	private String currentOfferedLocation;
	private String currentOfferedMeasurand;
	private String currentOfferedPhase;
	private String currentOfferedUnit;
	private String currentOfferedValue;

	private String energyActiveImportRegisterContext;
	private String energyActiveImportRegisterFormat;
	private String energyActiveImportRegisterLocation;
	private String energyActiveImportRegisterMeasurand;
	private String energyActiveImportRegisterPhase;
	private String energyActiveImportRegisterUnit="Wh";
	private String energyActiveImportRegisterValue="0";

	private String powerActiveImportContext;
	private String powerActiveImportFormat;
	private String powerActiveImportLocation;
	private String powerActiveImportMeasurand;
	private String powerActiveImportPhase;
	private String powerActiveImportUnit;
	private String powerActiveImportValue="0";
	private Double powerActiveImportSessionValue;

	private String powerOfferedContext;
	private String powerOfferedFormat;
	private String powerOfferedLocation;
	private String powerOfferedMeasurand;
	private String powerOfferedPhase;
	private String powerOfferedUnit;
	private String powerOfferedValue;
	
	private String SoCContext;
	private String SoCformat;
	private String SoClocation;
	private String SoCMeasurand;
	private String SoCPhase;
	private String SoCUnit="Percent";
	private String SoCValue="0";

	private Date meterValueTimeStatmp;
	private Date startTransTimeStamp;
	
	private String customerId;
	private String portPriceUnit;
	private String randomSessionId;
	
	private Port portObj;
	private String driverGroupName;
	private int driverGroupdId;
	private String userEmail="NA";
	private String reasonForTermination;
	private String stationMode;
	private String chargerType;
	private String authorizationStatus;
	private String connectedTimeUnits="0";
	private String stationRefNum="0";
	private Object previousSessionUniqueId;
	
	private boolean maxSessionFlag;
	private boolean energyConsumptionFlag;
	private double finalCosttostore=0.0;
	private double avgSessionLineFreqInHz=0.0;
	//private double finalCost=0.0;
	private double totalKwUsed=0.0;
	private double requestedDurationInMinutes=0.0;
	private double sessionElapsedInMinutes=0.0;
	private double oneTimeFeeCost=0.0;
	private double usedGraceTime=0.0;
	private double durationMinsOfSameEngy=0.0;
	private double connectorPrice=0.0;
	private double portPrice=0.0;
	private double watSecondsUsed=0.0;
	private double graceTime=0.0;
	private double costOfSameEnergy=0.0;
	private double finalCostInslcCurrency=0.0;
	private double totalDurationMinsofSmeEnergy=0.0;
	private double totalCostOfSmeEnergy=0.0;
	private long stationId;
	private long portId;
	private long userId;
	private double processingFee;
	private boolean masterUserTxn;
	private String sessionStatus;
	private String txnType;
	private double socStartVal;
	private double socEndVal;
	private boolean startTxnProgress;
	private String preProdSess;
	private double reservationFee=0.0;
	private AccountTransactions accountTransactions;
	private Long transactionId;
	private int sameMeterValuesCount;
	private double total_fixed_cost;
	private double total_energy_cost;
	private double total_time_cost;
	private String currencyType;
	private String idTagProfileName;
	private String profileType;
	private float currencyRate;
	private String userCurrencyType;
	private String additionalVendingPriceUnit;	
	private double additionalVendingPricePerUnit;
	private double totalAdditionalPrice;
	private boolean combinationBilling;
	private double combinationCost;
	private String settlement;
	private double startTransactionMeterValue;
    private double lastTransactionMeterValue;
    private String transactionStatus;
	private String paymentMode="NA";
	private String transactionType;
	private boolean optimized;
	private Date creationDate;
	
	private String EnergyActiveExportRegisterUnit;
	private String EnergyReactiveImportRegisterUnit;
	private String EnergyActiveExportIntervalUnit;
	private String EnergyActiveImportIntervalUnit;
	private String EnergyReactiveImportIntervalUnit;
	private String EnergyReactiveExportRegisterUnit;
	private String EnergyReactiveExportIntervalUnit;
	private String PowerActiveExportUnit;
	private String PowerReactiveExportUnit;
	private String PowerReactiveImportUnit;
	private String PowerFactorUnit;
	private String CurrentExportUnit;
	private String VoltageUnit;
	private String FrequencyUnit;
	private String TemperatureUnit;
	private String RPMUnit;
	
	private String EnergyActiveExportRegisterValue;
	private String EnergyReactiveExportRegisterValue;
	private String EnergyReactiveImportRegisterValue;
	private String EnergyActiveImportRegisterDiffValue;
	private String EnergyActiveExportIntervalValue;
	private String EnergyActiveImportIntervalValue;
	private String EnergyReactiveExportIntervalValue;
	private String EnergyReactiveImportIntervalValue;
	private String PowerActiveExportValue;
	private String PowerReactiveExportValue;
	private String PowerReactiveImportValue;
	private String PowerFactorValue;
	private String CurrentExportValue;
	private String VoltageValue;
	private String FrequencyValue;
	private String TemperatureValue;
	private String RPMValue;
	private String energyActiveImportRegisterValueES;
	private String energyActiveImportRegisterUnitES;
	private String powerActiveImportUnitES;
	private String powerActiveImportValueES="0";
	private String avg_power;
	private boolean inaccurateTxn ;
	private boolean successFlag;
	private String txnRejectedReason;
	private double userProcessingFee;
	
	private String CurrentImportDiffValue;
	private String PowerActiveImportDiffValue;
	private String SoCDiffValue;
	
	private boolean postPrice;
    private double postPriceLimit;
    private double postPortPrice;
    private double postPortCost;
    
    private String rewardType;
    private double rewardValue;
    private boolean selfCharging;
    
    private long orgId;
	
	public boolean isSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}
	public boolean isInaccurateTxn() {
		return inaccurateTxn;
	}
	public void setInaccurateTxn(boolean inaccurateTxn) {
		this.inaccurateTxn = inaccurateTxn;
	}
	public String getPowerActiveImportUnitES() {
		return powerActiveImportUnitES;
	}
	public void setPowerActiveImportUnitES(String powerActiveImportUnitES) {
		this.powerActiveImportUnitES = powerActiveImportUnitES;
	}
	public String getPowerActiveImportValueES() {
		return powerActiveImportValueES;
	}
	public void setPowerActiveImportValueES(String powerActiveImportValueES) {
		this.powerActiveImportValueES = powerActiveImportValueES;
	}
	public String getEnergyActiveImportRegisterValueES() {
		return energyActiveImportRegisterValueES;
	}
	public void setEnergyActiveImportRegisterValueES(String energyActiveImportRegisterValueES) {
		this.energyActiveImportRegisterValueES = energyActiveImportRegisterValueES;
	}
	public String getEnergyActiveImportRegisterUnitES() {
		return energyActiveImportRegisterUnitES;
	}
	public void setEnergyActiveImportRegisterUnitES(String energyActiveImportRegisterUnitES) {
		this.energyActiveImportRegisterUnitES = energyActiveImportRegisterUnitES;
	}
	public String getCurrentImportContext() {
		return currentImportContext;
	}
	public void setCurrentImportContext(String currentImportContext) {
		this.currentImportContext = currentImportContext;
	}
	public String getCurrentImportFormat() {
		return currentImportFormat;
	}
	public void setCurrentImportFormat(String currentImportFormat) {
		this.currentImportFormat = currentImportFormat;
	}
	public String getCurrentImportLocation() {
		return currentImportLocation;
	}
	public void setCurrentImportLocation(String currentImportLocation) {
		this.currentImportLocation = currentImportLocation;
	}
	public String getCurrentImportPhase() {
		return currentImportPhase;
	}
	public void setCurrentImportPhase(String currentImportPhase) {
		this.currentImportPhase = currentImportPhase;
	}
	public String getCurrentImportUnit() {
		return currentImportUnit;
	}
	public void setCurrentImportUnit(String currentImportUnit) {
		this.currentImportUnit = currentImportUnit;
	}
	public String getCurrentImportValue() {
		return currentImportValue;
	}
	public void setCurrentImportValue(String currentImportValue) {
		this.currentImportValue = currentImportValue;
	}
	public String getCurrentImportMeasurand() {
		return currentImportMeasurand;
	}
	public void setCurrentImportMeasurand(String currentImportMeasurand) {
		this.currentImportMeasurand = currentImportMeasurand;
	}
	public String getCurrentOfferedContext() {
		return currentOfferedContext;
	}
	public void setCurrentOfferedContext(String currentOfferedContext) {
		this.currentOfferedContext = currentOfferedContext;
	}
	public String getCurrentOfferedFormat() {
		return currentOfferedFormat;
	}
	public void setCurrentOfferedFormat(String currentOfferedFormat) {
		this.currentOfferedFormat = currentOfferedFormat;
	}
	public String getCurrentOfferedLocation() {
		return currentOfferedLocation;
	}
	public void setCurrentOfferedLocation(String currentOfferedLocation) {
		this.currentOfferedLocation = currentOfferedLocation;
	}
	public String getCurrentOfferedMeasurand() {
		return currentOfferedMeasurand;
	}
	public void setCurrentOfferedMeasurand(String currentOfferedMeasurand) {
		this.currentOfferedMeasurand = currentOfferedMeasurand;
	}
	public String getCurrentOfferedPhase() {
		return currentOfferedPhase;
	}
	public void setCurrentOfferedPhase(String currentOfferedPhase) {
		this.currentOfferedPhase = currentOfferedPhase;
	}
	public String getCurrentOfferedUnit() {
		return currentOfferedUnit;
	}
	public void setCurrentOfferedUnit(String currentOfferedUnit) {
		this.currentOfferedUnit = currentOfferedUnit;
	}
	public String getCurrentOfferedValue() {
		return currentOfferedValue;
	}
	public void setCurrentOfferedValue(String currentOfferedValue) {
		this.currentOfferedValue = currentOfferedValue;
	}
	public String getEnergyActiveImportRegisterContext() {
		return energyActiveImportRegisterContext;
	}
	public void setEnergyActiveImportRegisterContext(String energyActiveImportRegisterContext) {
		this.energyActiveImportRegisterContext = energyActiveImportRegisterContext;
	}
	public String getEnergyActiveImportRegisterFormat() {
		return energyActiveImportRegisterFormat;
	}
	public void setEnergyActiveImportRegisterFormat(String energyActiveImportRegisterFormat) {
		this.energyActiveImportRegisterFormat = energyActiveImportRegisterFormat;
	}
	public String getEnergyActiveImportRegisterLocation() {
		return energyActiveImportRegisterLocation;
	}
	public void setEnergyActiveImportRegisterLocation(String energyActiveImportRegisterLocation) {
		this.energyActiveImportRegisterLocation = energyActiveImportRegisterLocation;
	}
	public String getEnergyActiveImportRegisterMeasurand() {
		return energyActiveImportRegisterMeasurand;
	}
	public void setEnergyActiveImportRegisterMeasurand(String energyActiveImportRegisterMeasurand) {
		this.energyActiveImportRegisterMeasurand = energyActiveImportRegisterMeasurand;
	}
	public String getEnergyActiveImportRegisterPhase() {
		return energyActiveImportRegisterPhase;
	}
	public void setEnergyActiveImportRegisterPhase(String energyActiveImportRegisterPhase) {
		this.energyActiveImportRegisterPhase = energyActiveImportRegisterPhase;
	}
	public String getEnergyActiveImportRegisterUnit() {
		return energyActiveImportRegisterUnit;
	}
	public void setEnergyActiveImportRegisterUnit(String energyActiveImportRegisterUnit) {
		this.energyActiveImportRegisterUnit = energyActiveImportRegisterUnit;
	}
	public String getEnergyActiveImportRegisterValue() {
		return energyActiveImportRegisterValue;
	}
	public void setEnergyActiveImportRegisterValue(String energyActiveImportRegisterValue) {
		this.energyActiveImportRegisterValue = energyActiveImportRegisterValue;
	}
	public String getPowerActiveImportContext() {
		return powerActiveImportContext;
	}
	public void setPowerActiveImportContext(String powerActiveImportContext) {
		this.powerActiveImportContext = powerActiveImportContext;
	}
	public String getPowerActiveImportFormat() {
		return powerActiveImportFormat;
	}
	public void setPowerActiveImportFormat(String powerActiveImportFormat) {
		this.powerActiveImportFormat = powerActiveImportFormat;
	}
	public String getPowerActiveImportLocation() {
		return powerActiveImportLocation;
	}
	public void setPowerActiveImportLocation(String powerActiveImportLocation) {
		this.powerActiveImportLocation = powerActiveImportLocation;
	}
	public String getPowerActiveImportMeasurand() {
		return powerActiveImportMeasurand;
	}
	public void setPowerActiveImportMeasurand(String powerActiveImportMeasurand) {
		this.powerActiveImportMeasurand = powerActiveImportMeasurand;
	}
	public String getPowerActiveImportPhase() {
		return powerActiveImportPhase;
	}
	public void setPowerActiveImportPhase(String powerActiveImportPhase) {
		this.powerActiveImportPhase = powerActiveImportPhase;
	}
	public String getPowerActiveImportUnit() {
		return powerActiveImportUnit;
	}
	public void setPowerActiveImportUnit(String powerActiveImportUnit) {
		this.powerActiveImportUnit = powerActiveImportUnit;
	}
	public String getPowerActiveImportValue() {
		return powerActiveImportValue;
	}
	public void setPowerActiveImportValue(String powerActiveImportValue) {
		this.powerActiveImportValue = powerActiveImportValue;
	}
	public Double getPowerActiveImportSessionValue() {
		return powerActiveImportSessionValue;
	}
	public void setPowerActiveImportSessionValue(Double powerActiveImportSessionValue) {
		this.powerActiveImportSessionValue = powerActiveImportSessionValue;
	}
	public String getPowerOfferedContext() {
		return powerOfferedContext;
	}
	public void setPowerOfferedContext(String powerOfferedContext) {
		this.powerOfferedContext = powerOfferedContext;
	}
	public String getPowerOfferedFormat() {
		return powerOfferedFormat;
	}
	public void setPowerOfferedFormat(String powerOfferedFormat) {
		this.powerOfferedFormat = powerOfferedFormat;
	}
	public String getPowerOfferedLocation() {
		return powerOfferedLocation;
	}
	public void setPowerOfferedLocation(String powerOfferedLocation) {
		this.powerOfferedLocation = powerOfferedLocation;
	}
	public String getPowerOfferedMeasurand() {
		return powerOfferedMeasurand;
	}
	public void setPowerOfferedMeasurand(String powerOfferedMeasurand) {
		this.powerOfferedMeasurand = powerOfferedMeasurand;
	}
	public String getPowerOfferedPhase() {
		return powerOfferedPhase;
	}
	public void setPowerOfferedPhase(String powerOfferedPhase) {
		this.powerOfferedPhase = powerOfferedPhase;
	}
	public String getPowerOfferedUnit() {
		return powerOfferedUnit;
	}
	public void setPowerOfferedUnit(String powerOfferedUnit) {
		this.powerOfferedUnit = powerOfferedUnit;
	}
	public String getPowerOfferedValue() {
		return powerOfferedValue;
	}
	public void setPowerOfferedValue(String powerOfferedValue) {
		this.powerOfferedValue = powerOfferedValue;
	}
	public String getSoCContext() {
		return SoCContext;
	}
	public void setSoCContext(String soCContext) {
		SoCContext = soCContext;
	}
	public String getSoCformat() {
		return SoCformat;
	}
	public void setSoCformat(String soCformat) {
		SoCformat = soCformat;
	}
	public String getSoClocation() {
		return SoClocation;
	}
	public void setSoClocation(String soClocation) {
		SoClocation = soClocation;
	}
	public String getSoCMeasurand() {
		return SoCMeasurand;
	}
	public void setSoCMeasurand(String soCMeasurand) {
		SoCMeasurand = soCMeasurand;
	}
	public String getSoCPhase() {
		return SoCPhase;
	}
	public void setSoCPhase(String soCPhase) {
		SoCPhase = soCPhase;
	}
	public String getSoCUnit() {
		return SoCUnit;
	}
	public void setSoCUnit(String soCUnit) {
		SoCUnit = soCUnit;
	}
	public String getSoCValue() {
		return SoCValue;
	}
	public void setSoCValue(String soCValue) {
		SoCValue = soCValue;
	}
	public Date getMeterValueTimeStatmp() {
		return meterValueTimeStatmp;
	}
	public void setMeterValueTimeStatmp(Date meterValueTimeStatmp) {
		this.meterValueTimeStatmp = meterValueTimeStatmp;
	}
	public Date getStartTransTimeStamp() {
		return startTransTimeStamp;
	}
	public void setStartTransTimeStamp(Date startTransTimeStamp) {
		this.startTransTimeStamp = startTransTimeStamp;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPortPriceUnit() {
		return portPriceUnit;
	}
	public void setPortPriceUnit(String portPriceUnit) {
		this.portPriceUnit = portPriceUnit;
	}
	public String getRandomSessionId() {
		return randomSessionId;
	}
	public void setRandomSessionId(String randomSessionId) {
		this.randomSessionId = randomSessionId;
	}
	public Port getPortObj() {
		return portObj;
	}
	public void setPortObj(Port portObj) {
		this.portObj = portObj;
	}
	public String getDriverGroupName() {
		return driverGroupName;
	}
	public void setDriverGroupName(String driverGroupName) {
		this.driverGroupName = driverGroupName;
	}
	public int getDriverGroupdId() {
		return driverGroupdId;
	}
	public void setDriverGroupdId(int driverGroupdId) {
		this.driverGroupdId = driverGroupdId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getReasonForTermination() {
		return reasonForTermination;
	}
	public void setReasonForTermination(String reasonForTermination) {
		this.reasonForTermination = reasonForTermination;
	}
	public String getStationMode() {
		return stationMode;
	}
	public void setStationMode(String stationMode) {
		this.stationMode = stationMode;
	}
	public String getChargerType() {
		return chargerType;
	}
	public void setChargerType(String chargerType) {
		this.chargerType = chargerType;
	}
	public String getAuthorizationStatus() {
		return authorizationStatus;
	}
	public void setAuthorizationStatus(String authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}
	public String getConnectedTimeUnits() {
		return connectedTimeUnits;
	}
	public void setConnectedTimeUnits(String connectedTimeUnits) {
		this.connectedTimeUnits = connectedTimeUnits;
	}
	public String getStationRefNum() {
		return stationRefNum;
	}
	public void setStationRefNum(String stationRefNum) {
		this.stationRefNum = stationRefNum;
	}
	public Object getPreviousSessionUniqueId() {
		return previousSessionUniqueId;
	}
	public void setPreviousSessionUniqueId(Object previousSessionUniqueId) {
		this.previousSessionUniqueId = previousSessionUniqueId;
	}
	public boolean isMaxSessionFlag() {
		return maxSessionFlag;
	}
	public void setMaxSessionFlag(boolean maxSessionFlag) {
		this.maxSessionFlag = maxSessionFlag;
	}
	public boolean isEnergyConsumptionFlag() {
		return energyConsumptionFlag;
	}
	public void setEnergyConsumptionFlag(boolean energyConsumptionFlag) {
		this.energyConsumptionFlag = energyConsumptionFlag;
	}
	public double getFinalCosttostore() {
		return finalCosttostore;
	}
	public void setFinalCosttostore(double finalCosttostore) {
		this.finalCosttostore = finalCosttostore;
	}
	public double getAvgSessionLineFreqInHz() {
		return avgSessionLineFreqInHz;
	}
	public void setAvgSessionLineFreqInHz(double avgSessionLineFreqInHz) {
		this.avgSessionLineFreqInHz = avgSessionLineFreqInHz;
	}
	public double getTotalKwUsed() {
		return totalKwUsed;
	}
	public void setTotalKwUsed(double totalKwUsed) {
		this.totalKwUsed = totalKwUsed;
	}
	public double getRequestedDurationInMinutes() {
		return requestedDurationInMinutes;
	}
	public void setRequestedDurationInMinutes(double requestedDurationInMinutes) {
		this.requestedDurationInMinutes = requestedDurationInMinutes;
	}
	public double getSessionElapsedInMinutes() {
		return sessionElapsedInMinutes;
	}
	public void setSessionElapsedInMinutes(double sessionElapsedInMinutes) {
		this.sessionElapsedInMinutes = sessionElapsedInMinutes;
	}
	public double getOneTimeFeeCost() {
		return oneTimeFeeCost;
	}
	public void setOneTimeFeeCost(double oneTimeFeeCost) {
		this.oneTimeFeeCost = oneTimeFeeCost;
	}
	public double getUsedGraceTime() {
		return usedGraceTime;
	}
	public void setUsedGraceTime(double usedGraceTime) {
		this.usedGraceTime = usedGraceTime;
	}
	public double getDurationMinsOfSameEngy() {
		return durationMinsOfSameEngy;
	}
	public void setDurationMinsOfSameEngy(double durationMinsOfSameEngy) {
		this.durationMinsOfSameEngy = durationMinsOfSameEngy;
	}
	public double getConnectorPrice() {
		return connectorPrice;
	}
	public void setConnectorPrice(double connectorPrice) {
		this.connectorPrice = connectorPrice;
	}
	public double getPortPrice() {
		return portPrice;
	}
	public void setPortPrice(double portPrice) {
		this.portPrice = portPrice;
	}
	public double getWatSecondsUsed() {
		return watSecondsUsed;
	}
	public void setWatSecondsUsed(double watSecondsUsed) {
		this.watSecondsUsed = watSecondsUsed;
	}
	public double getGraceTime() {
		return graceTime;
	}
	public void setGraceTime(double graceTime) {
		this.graceTime = graceTime;
	}
	public double getCostOfSameEnergy() {
		return costOfSameEnergy;
	}
	public void setCostOfSameEnergy(double costOfSameEnergy) {
		this.costOfSameEnergy = costOfSameEnergy;
	}
	public double getFinalCostInslcCurrency() {
		return finalCostInslcCurrency;
	}
	public void setFinalCostInslcCurrency(double finalCostInslcCurrency) {
		this.finalCostInslcCurrency = finalCostInslcCurrency;
	}
	public double getTotalDurationMinsofSmeEnergy() {
		return totalDurationMinsofSmeEnergy;
	}
	public void setTotalDurationMinsofSmeEnergy(double totalDurationMinsofSmeEnergy) {
		this.totalDurationMinsofSmeEnergy = totalDurationMinsofSmeEnergy;
	}
	public double getTotalCostOfSmeEnergy() {
		return totalCostOfSmeEnergy;
	}
	public void setTotalCostOfSmeEnergy(double totalCostOfSmeEnergy) {
		this.totalCostOfSmeEnergy = totalCostOfSmeEnergy;
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public double getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}
	public boolean isMasterUserTxn() {
		return masterUserTxn;
	}
	public void setMasterUserTxn(boolean masterUserTxn) {
		this.masterUserTxn = masterUserTxn;
	}
	public String getSessionStatus() {
		return sessionStatus;
	}
	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public double getSocStartVal() {
		return socStartVal;
	}
	public void setSocStartVal(double socStartVal) {
		this.socStartVal = socStartVal;
	}
	public double getSocEndVal() {
		return socEndVal;
	}
	public void setSocEndVal(double socEndVal) {
		this.socEndVal = socEndVal;
	}
	public boolean isStartTxnProgress() {
		return startTxnProgress;
	}
	public void setStartTxnProgress(boolean startTxnProgress) {
		this.startTxnProgress = startTxnProgress;
	}
	public String getPreProdSess() {
		return preProdSess;
	}
	public void setPreProdSess(String preProdSess) {
		this.preProdSess = preProdSess;
	}
	public double getReservationFee() {
		return reservationFee;
	}
	public void setReservationFee(double reservationFee) {
		this.reservationFee = reservationFee;
	}
	public AccountTransactions getAccountTransactions() {
		return accountTransactions;
	}
	public void setAccountTransactions(AccountTransactions accountTransactions) {
		this.accountTransactions = accountTransactions;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public int getSameMeterValuesCount() {
		return sameMeterValuesCount;
	}
	public void setSameMeterValuesCount(int sameMeterValuesCount) {
		this.sameMeterValuesCount = sameMeterValuesCount;
	}
	public double getTotal_fixed_cost() {
		return total_fixed_cost;
	}
	public void setTotal_fixed_cost(double total_fixed_cost) {
		this.total_fixed_cost = total_fixed_cost;
	}
	public double getTotal_energy_cost() {
		return total_energy_cost;
	}
	public void setTotal_energy_cost(double total_energy_cost) {
		this.total_energy_cost = total_energy_cost;
	}
	public double getTotal_time_cost() {
		return total_time_cost;
	}
	public void setTotal_time_cost(double total_time_cost) {
		this.total_time_cost = total_time_cost;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getIdTagProfileName() {
		return idTagProfileName;
	}
	public void setIdTagProfileName(String idTagProfileName) {
		this.idTagProfileName = idTagProfileName;
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	public float getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(float currencyRate) {
		this.currencyRate = currencyRate;
	}
	public String getUserCurrencyType() {
		return userCurrencyType;
	}
	public void setUserCurrencyType(String userCurrencyType) {
		this.userCurrencyType = userCurrencyType;
	}
	public String getAdditionalVendingPriceUnit() {
		return additionalVendingPriceUnit;
	}
	public void setAdditionalVendingPriceUnit(String additionalVendingPriceUnit) {
		this.additionalVendingPriceUnit = additionalVendingPriceUnit;
	}
	public double getAdditionalVendingPricePerUnit() {
		return additionalVendingPricePerUnit;
	}
	public void setAdditionalVendingPricePerUnit(double additionalVendingPricePerUnit) {
		this.additionalVendingPricePerUnit = additionalVendingPricePerUnit;
	}
	public double getTotalAdditionalPrice() {
		return totalAdditionalPrice;
	}
	public void setTotalAdditionalPrice(double totalAdditionalPrice) {
		this.totalAdditionalPrice = totalAdditionalPrice;
	}
	public boolean isCombinationBilling() {
		return combinationBilling;
	}
	public void setCombinationBilling(boolean combinationBilling) {
		this.combinationBilling = combinationBilling;
	}
	public double getCombinationCost() {
		return combinationCost;
	}
	public void setCombinationCost(double combinationCost) {
		this.combinationCost = combinationCost;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public double getStartTransactionMeterValue() {
		return startTransactionMeterValue;
	}
	public void setStartTransactionMeterValue(double startTransactionMeterValue) {
		this.startTransactionMeterValue = startTransactionMeterValue;
	}
	public double getLastTransactionMeterValue() {
		return lastTransactionMeterValue;
	}
	public void setLastTransactionMeterValue(double lastTransactionMeterValue) {
		this.lastTransactionMeterValue = lastTransactionMeterValue;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public boolean isOptimized() {
		return optimized;
	}
	public void setOptimized(boolean optimized) {
		this.optimized = optimized;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getEnergyActiveExportRegisterUnit() {
		return EnergyActiveExportRegisterUnit;
	}
	public void setEnergyActiveExportRegisterUnit(String energyActiveExportRegisterUnit) {
		EnergyActiveExportRegisterUnit = energyActiveExportRegisterUnit;
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
	public String getEnergyReactiveImportIntervalUnit() {
		return EnergyReactiveImportIntervalUnit;
	}
	public void setEnergyReactiveImportIntervalUnit(String energyReactiveImportIntervalUnit) {
		EnergyReactiveImportIntervalUnit = energyReactiveImportIntervalUnit;
	}
	public String getEnergyReactiveExportRegisterUnit() {
		return EnergyReactiveExportRegisterUnit;
	}
	public void setEnergyReactiveExportRegisterUnit(String energyReactiveExportRegisterUnit) {
		EnergyReactiveExportRegisterUnit = energyReactiveExportRegisterUnit;
	}
	public String getEnergyReactiveExportIntervalUnit() {
		return EnergyReactiveExportIntervalUnit;
	}
	public void setEnergyReactiveExportIntervalUnit(String energyReactiveExportIntervalUnit) {
		EnergyReactiveExportIntervalUnit = energyReactiveExportIntervalUnit;
	}
	public String getPowerActiveExportUnit() {
		return PowerActiveExportUnit;
	}
	public void setPowerActiveExportUnit(String powerActiveExportUnit) {
		PowerActiveExportUnit = powerActiveExportUnit;
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
	public String getCurrentExportUnit() {
		return CurrentExportUnit;
	}
	public void setCurrentExportUnit(String currentExportUnit) {
		CurrentExportUnit = currentExportUnit;
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
	public String getRPMUnit() {
		return RPMUnit;
	}
	public void setRPMUnit(String rPMUnit) {
		RPMUnit = rPMUnit;
	}
	public String getEnergyActiveExportRegisterValue() {
		return EnergyActiveExportRegisterValue;
	}
	public void setEnergyActiveExportRegisterValue(String energyActiveExportRegisterValue) {
		EnergyActiveExportRegisterValue = energyActiveExportRegisterValue;
	}
	public String getEnergyReactiveExportRegisterValue() {
		return EnergyReactiveExportRegisterValue;
	}
	public void setEnergyReactiveExportRegisterValue(String energyReactiveExportRegisterValue) {
		EnergyReactiveExportRegisterValue = energyReactiveExportRegisterValue;
	}
	public String getEnergyReactiveImportRegisterValue() {
		return EnergyReactiveImportRegisterValue;
	}
	public void setEnergyReactiveImportRegisterValue(String energyReactiveImportRegisterValue) {
		EnergyReactiveImportRegisterValue = energyReactiveImportRegisterValue;
	}
	public String getEnergyActiveExportIntervalValue() {
		return EnergyActiveExportIntervalValue;
	}
	public void setEnergyActiveExportIntervalValue(String energyActiveExportIntervalValue) {
		EnergyActiveExportIntervalValue = energyActiveExportIntervalValue;
	}
	public String getEnergyActiveImportIntervalValue() {
		return EnergyActiveImportIntervalValue;
	}
	public void setEnergyActiveImportIntervalValue(String energyActiveImportIntervalValue) {
		EnergyActiveImportIntervalValue = energyActiveImportIntervalValue;
	}
	public String getEnergyReactiveExportIntervalValue() {
		return EnergyReactiveExportIntervalValue;
	}
	public void setEnergyReactiveExportIntervalValue(String energyReactiveExportIntervalValue) {
		EnergyReactiveExportIntervalValue = energyReactiveExportIntervalValue;
	}
	public String getEnergyReactiveImportIntervalValue() {
		return EnergyReactiveImportIntervalValue;
	}
	public void setEnergyReactiveImportIntervalValue(String energyReactiveImportIntervalValue) {
		EnergyReactiveImportIntervalValue = energyReactiveImportIntervalValue;
	}
	public String getPowerActiveExportValue() {
		return PowerActiveExportValue;
	}
	public void setPowerActiveExportValue(String powerActiveExportValue) {
		PowerActiveExportValue = powerActiveExportValue;
	}
	public String getPowerReactiveExportValue() {
		return PowerReactiveExportValue;
	}
	public void setPowerReactiveExportValue(String powerReactiveExportValue) {
		PowerReactiveExportValue = powerReactiveExportValue;
	}
	public String getPowerReactiveImportValue() {
		return PowerReactiveImportValue;
	}
	public void setPowerReactiveImportValue(String powerReactiveImportValue) {
		PowerReactiveImportValue = powerReactiveImportValue;
	}
	public String getPowerFactorValue() {
		return PowerFactorValue;
	}
	public void setPowerFactorValue(String powerFactorValue) {
		PowerFactorValue = powerFactorValue;
	}
	public String getCurrentExportValue() {
		return CurrentExportValue;
	}
	public void setCurrentExportValue(String currentExportValue) {
		CurrentExportValue = currentExportValue;
	}
	public String getVoltageValue() {
		return VoltageValue;
	}
	public void setVoltageValue(String voltageValue) {
		VoltageValue = voltageValue;
	}
	public String getFrequencyValue() {
		return FrequencyValue;
	}
	public void setFrequencyValue(String frequencyValue) {
		FrequencyValue = frequencyValue;
	}
	public String getTemperatureValue() {
		return TemperatureValue;
	}
	public void setTemperatureValue(String temperatureValue) {
		TemperatureValue = temperatureValue;
	}
	public String getRPMValue() {
		return RPMValue;
	}
	public void setRPMValue(String rPMValue) {
		RPMValue = rPMValue;
	}
	public String getAvg_power() {
		return avg_power;
	}
	public void setAvg_power(String avg_power) {
		this.avg_power = avg_power;
	}
	public String getEnergyActiveImportRegisterDiffValue() {
		return EnergyActiveImportRegisterDiffValue;
	}
	public void setEnergyActiveImportRegisterDiffValue(String energyActiveImportRegisterDiffValue) {
		EnergyActiveImportRegisterDiffValue = energyActiveImportRegisterDiffValue;
	}
	public String getCurrentImportDiffValue() {
		return CurrentImportDiffValue;
	}
	public void setCurrentImportDiffValue(String currentImportDiffValue) {
		CurrentImportDiffValue = currentImportDiffValue;
	}
	public String getPowerActiveImportDiffValue() {
		return PowerActiveImportDiffValue;
	}
	public void setPowerActiveImportDiffValue(String powerActiveImportDiffValue) {
		PowerActiveImportDiffValue = powerActiveImportDiffValue;
	}
	public String getSoCDiffValue() {
		return SoCDiffValue;
	}
	public void setSoCDiffValue(String soCDiffValue) {
		SoCDiffValue = soCDiffValue;
	}
	
	public String getTxnRejectedReason() {
		return txnRejectedReason;
	}
	public void setTxnRejectedReason(String txnRejectedReason) {
		this.txnRejectedReason = txnRejectedReason;
	}
	public double getUserProcessingFee() {
		return userProcessingFee;
	}
	public void setUserProcessingFee(double userProcessingFee) {
		this.userProcessingFee = userProcessingFee;
	}
	public boolean isPostPrice() {
		return postPrice;
	}
	public void setPostPrice(boolean postPrice) {
		this.postPrice = postPrice;
	}
	public double getPostPriceLimit() {
		return postPriceLimit;
	}
	public void setPostPriceLimit(double postPriceLimit) {
		this.postPriceLimit = postPriceLimit;
	}
	public double getPostPortPrice() {
		return postPortPrice;
	}
	public void setPostPortPrice(double postPortPrice) {
		this.postPortPrice = postPortPrice;
	}
	public double getPostPortCost() {
		return postPortCost;
	}
	public void setPostPortCost(double postPortCost) {
		this.postPortCost = postPortCost;
	}
	@Override
	public String toString() {
		return "SessionImportedValues [currentImportContext=" + currentImportContext + ", currentImportFormat="
				+ currentImportFormat + ", currentImportLocation=" + currentImportLocation + ", currentImportPhase="
				+ currentImportPhase + ", currentImportUnit=" + currentImportUnit + ", currentImportValue="
				+ currentImportValue + ", currentImportMeasurand=" + currentImportMeasurand + ", currentOfferedContext="
				+ currentOfferedContext + ", currentOfferedFormat=" + currentOfferedFormat + ", currentOfferedLocation="
				+ currentOfferedLocation + ", currentOfferedMeasurand=" + currentOfferedMeasurand
				+ ", currentOfferedPhase=" + currentOfferedPhase + ", currentOfferedUnit=" + currentOfferedUnit
				+ ", currentOfferedValue=" + currentOfferedValue + ", energyActiveImportRegisterContext="
				+ energyActiveImportRegisterContext + ", energyActiveImportRegisterFormat="
				+ energyActiveImportRegisterFormat + ", energyActiveImportRegisterLocation="
				+ energyActiveImportRegisterLocation + ", energyActiveImportRegisterMeasurand="
				+ energyActiveImportRegisterMeasurand + ", energyActiveImportRegisterPhase="
				+ energyActiveImportRegisterPhase + ", energyActiveImportRegisterUnit=" + energyActiveImportRegisterUnit
				+ ", energyActiveImportRegisterValue=" + energyActiveImportRegisterValue + ", powerActiveImportContext="
				+ powerActiveImportContext + ", powerActiveImportFormat=" + powerActiveImportFormat
				+ ", powerActiveImportLocation=" + powerActiveImportLocation + ", powerActiveImportMeasurand="
				+ powerActiveImportMeasurand + ", powerActiveImportPhase=" + powerActiveImportPhase
				+ ", powerActiveImportUnit=" + powerActiveImportUnit + ", powerActiveImportValue="
				+ powerActiveImportValue + ", powerActiveImportSessionValue=" + powerActiveImportSessionValue
				+ ", powerOfferedContext=" + powerOfferedContext + ", powerOfferedFormat=" + powerOfferedFormat
				+ ", powerOfferedLocation=" + powerOfferedLocation + ", powerOfferedMeasurand=" + powerOfferedMeasurand
				+ ", powerOfferedPhase=" + powerOfferedPhase + ", powerOfferedUnit=" + powerOfferedUnit
				+ ", powerOfferedValue=" + powerOfferedValue + ", SoCContext=" + SoCContext + ", SoCformat=" + SoCformat
				+ ", SoClocation=" + SoClocation + ", SoCMeasurand=" + SoCMeasurand + ", SoCPhase=" + SoCPhase
				+ ", SoCUnit=" + SoCUnit + ", SoCValue=" + SoCValue + ", meterValueTimeStatmp=" + meterValueTimeStatmp
				+ ", startTransTimeStamp=" + startTransTimeStamp + ", customerId=" + customerId + ", portPriceUnit="
				+ portPriceUnit + ", randomSessionId=" + randomSessionId + ", portObj=" + portObj + ", driverGroupName="
				+ driverGroupName + ", driverGroupdId=" + driverGroupdId + ", userEmail=" + userEmail
				+ ", reasonForTermination=" + reasonForTermination + ", stationMode=" + stationMode + ", chargerType="
				+ chargerType + ", authorizationStatus=" + authorizationStatus + ", connectedTimeUnits="
				+ connectedTimeUnits + ", stationRefNum=" + stationRefNum + ", previousSessionUniqueId="
				+ previousSessionUniqueId + ", maxSessionFlag=" + maxSessionFlag + ", energyConsumptionFlag="
				+ energyConsumptionFlag + ", finalCosttostore=" + finalCosttostore + ", avgSessionLineFreqInHz="
				+ avgSessionLineFreqInHz + ", totalKwUsed=" + totalKwUsed + ", requestedDurationInMinutes="
				+ requestedDurationInMinutes + ", sessionElapsedInMinutes=" + sessionElapsedInMinutes
				+ ", oneTimeFeeCost=" + oneTimeFeeCost + ", usedGraceTime=" + usedGraceTime
				+ ", durationMinsOfSameEngy=" + durationMinsOfSameEngy + ", connectorPrice=" + connectorPrice
				+ ", portPrice=" + portPrice + ", watSecondsUsed=" + watSecondsUsed + ", graceTime=" + graceTime
				+ ", costOfSameEnergy=" + costOfSameEnergy + ", finalCostInslcCurrency=" + finalCostInslcCurrency
				+ ", totalDurationMinsofSmeEnergy=" + totalDurationMinsofSmeEnergy + ", totalCostOfSmeEnergy="
				+ totalCostOfSmeEnergy + ", stationId=" + stationId + ", portId=" + portId + ", userId=" + userId
				+ ", processingFee=" + processingFee + ", masterUserTxn=" + masterUserTxn + ", sessionStatus="
				+ sessionStatus + ", txnType=" + txnType + ", socStartVal=" + socStartVal + ", socEndVal=" + socEndVal
				+ ", startTxnProgress=" + startTxnProgress + ", preProdSess=" + preProdSess + ", reservationFee="
				+ reservationFee + ", accountTransactions=" + accountTransactions + ", transactionId=" + transactionId
				+ ", sameMeterValuesCount=" + sameMeterValuesCount + ", total_fixed_cost=" + total_fixed_cost
				+ ", total_energy_cost=" + total_energy_cost + ", total_time_cost=" + total_time_cost
				+ ", currencyType=" + currencyType + ", idTagProfileName=" + idTagProfileName + ", profileType="
				+ profileType + ", currencyRate=" + currencyRate + ", userCurrencyType=" + userCurrencyType
				+ ", additionalVendingPriceUnit=" + additionalVendingPriceUnit + ", additionalVendingPricePerUnit="
				+ additionalVendingPricePerUnit + ", totalAdditionalPrice=" + totalAdditionalPrice
				+ ", combinationBilling=" + combinationBilling + ", combinationCost=" + combinationCost
				+ ", settlement=" + settlement + ", startTransactionMeterValue=" + startTransactionMeterValue
				+ ", lastTransactionMeterValue=" + lastTransactionMeterValue + ", transactionStatus="
				+ transactionStatus + ", paymentMode=" + paymentMode + ", transactionType=" + transactionType
				+ ", optimized=" + optimized + ", creationDate=" + creationDate + ", EnergyActiveExportRegisterUnit="
				+ EnergyActiveExportRegisterUnit + ", EnergyReactiveImportRegisterUnit="
				+ EnergyReactiveImportRegisterUnit + ", EnergyActiveExportIntervalUnit="
				+ EnergyActiveExportIntervalUnit + ", EnergyActiveImportIntervalUnit=" + EnergyActiveImportIntervalUnit
				+ ", EnergyReactiveImportIntervalUnit=" + EnergyReactiveImportIntervalUnit
				+ ", EnergyReactiveExportRegisterUnit=" + EnergyReactiveExportRegisterUnit
				+ ", EnergyReactiveExportIntervalUnit=" + EnergyReactiveExportIntervalUnit + ", PowerActiveExportUnit="
				+ PowerActiveExportUnit + ", PowerReactiveExportUnit=" + PowerReactiveExportUnit
				+ ", PowerReactiveImportUnit=" + PowerReactiveImportUnit + ", PowerFactorUnit=" + PowerFactorUnit
				+ ", CurrentExportUnit=" + CurrentExportUnit + ", VoltageUnit=" + VoltageUnit + ", FrequencyUnit="
				+ FrequencyUnit + ", TemperatureUnit=" + TemperatureUnit + ", RPMUnit=" + RPMUnit
				+ ", EnergyActiveExportRegisterValue=" + EnergyActiveExportRegisterValue
				+ ", EnergyReactiveExportRegisterValue=" + EnergyReactiveExportRegisterValue
				+ ", EnergyReactiveImportRegisterValue=" + EnergyReactiveImportRegisterValue
				+ ", EnergyActiveImportRegisterDiffValue=" + EnergyActiveImportRegisterDiffValue
				+ ", EnergyActiveExportIntervalValue=" + EnergyActiveExportIntervalValue
				+ ", EnergyActiveImportIntervalValue=" + EnergyActiveImportIntervalValue
				+ ", EnergyReactiveExportIntervalValue=" + EnergyReactiveExportIntervalValue
				+ ", EnergyReactiveImportIntervalValue=" + EnergyReactiveImportIntervalValue
				+ ", PowerActiveExportValue=" + PowerActiveExportValue + ", PowerReactiveExportValue="
				+ PowerReactiveExportValue + ", PowerReactiveImportValue=" + PowerReactiveImportValue
				+ ", PowerFactorValue=" + PowerFactorValue + ", CurrentExportValue=" + CurrentExportValue
				+ ", VoltageValue=" + VoltageValue + ", FrequencyValue=" + FrequencyValue + ", TemperatureValue="
				+ TemperatureValue + ", RPMValue=" + RPMValue + ", energyActiveImportRegisterValueES="
				+ energyActiveImportRegisterValueES + ", energyActiveImportRegisterUnitES="
				+ energyActiveImportRegisterUnitES + ", powerActiveImportUnitES=" + powerActiveImportUnitES
				+ ", powerActiveImportValueES=" + powerActiveImportValueES + ", avg_power=" + avg_power
				+ ", inaccurateTxn=" + inaccurateTxn + ", successFlag=" + successFlag + ", txnRejectedReason="
				+ txnRejectedReason + ", userProcessingFee=" + userProcessingFee + ", CurrentImportDiffValue="
				+ CurrentImportDiffValue + ", PowerActiveImportDiffValue=" + PowerActiveImportDiffValue
				+ ", SoCDiffValue=" + SoCDiffValue + ", postPrice=" + postPrice + ", postPriceLimit=" + postPriceLimit
				+ ", postPortPrice=" + postPortPrice + ", postPortCost=" + postPortCost + ", rewardType=" + rewardType
				+ ", rewardValue=" + rewardValue + ", selfCharging=" + selfCharging + ", orgId=" + orgId + "]";
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	public double getRewardValue() {
		return rewardValue;
	}
	public void setRewardValue(double rewardValue) {
		this.rewardValue = rewardValue;
	}
	public boolean isSelfCharging() {
		return selfCharging;
	}
	public void setSelfCharging(boolean selfCharging) {
		this.selfCharging = selfCharging;
	}
	
	
	
}