package com.axxera.ocpp.message;

import com.axxera.ocpp.model.ocpp.Station;

public class StationConfig {
	private long stationId;
	private String firmwareVersion; 
	private boolean AllowOfflineTxForUnknownId;
	private boolean AuthorizationCacheEnabled;
	private boolean AuthorizeRemoteTxRequests;
	private int BlinkRepeat;
	private int ClockAlignedDataInterval;
	private int ConnectionTimeOut;
	private int GetConfigurationMaxKeys;
	private int HeartbeatInterval;
	private int LightIntensity;
	private boolean LocalAuthorizeOffline;
	private boolean LocalPreAuthorize;
	private int MaxEnergyOnInvalidId;
	private String MeterValuesAlignedData;
	private String MeterValuesAlignedDataMaxLength;
	private String MeterValuesSampledData;
	private String MeterValuesSampledDataMaxLength;
	private int MeterValueSampleInterval;
	private int MinimumStatusDuration;
	private int NumberOfConnectors;
	private int ResetRetries;
	private String ConnectorPhaseRotation;
	private String ConnectorPhaseRotationMaxLength;
	private boolean StopTransactionOnEVSideDisconnect;
	private boolean StopTransactionOnInvalidId;
	private String StopTxnAlignedData;
	private String StopTxnAlignedDataMaxLength;
	private String StopTxnSampledData;
	private String StopTxnSampledDataMaxLength;
	private String SupportedFeatureProfiles;
	private String SupportedFeatureProfilesMaxLength;
	private int TransactionMessageAttempts;
	private int TransactionMessageRetryInterval;
	private boolean UnlockConnectorOnEVSideDisconnect;
	private int WebSocketPingInterval;
	private boolean LocalAuthListEnabled=false;
	private int LocalAuthListMaxLength=0;
	private int SendLocalListMaxLength=0;
	private boolean ReserveConnectorZeroSupported;
	private int ChargeProfileMaxStackLevel;
	private String ChargingScheduleAllowedChargingRateUnit;
	private int ChargingScheduleMaxPeriods;
	private boolean ConnectorSwitch3to1PhaseSupported;
	private int MaxChargingProfilesInstalled;
	private boolean FlagValue;
	private boolean defaultConfig=false;
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public boolean isAllowOfflineTxForUnknownId() {
		return AllowOfflineTxForUnknownId;
	}
	public void setAllowOfflineTxForUnknownId(boolean allowOfflineTxForUnknownId) {
		AllowOfflineTxForUnknownId = allowOfflineTxForUnknownId;
	}
	public boolean isAuthorizationCacheEnabled() {
		return AuthorizationCacheEnabled;
	}
	public void setAuthorizationCacheEnabled(boolean authorizationCacheEnabled) {
		AuthorizationCacheEnabled = authorizationCacheEnabled;
	}
	public boolean isAuthorizeRemoteTxRequests() {
		return AuthorizeRemoteTxRequests;
	}
	public void setAuthorizeRemoteTxRequests(boolean authorizeRemoteTxRequests) {
		AuthorizeRemoteTxRequests = authorizeRemoteTxRequests;
	}
	public int getBlinkRepeat() {
		return BlinkRepeat;
	}
	public void setBlinkRepeat(int blinkRepeat) {
		BlinkRepeat = blinkRepeat;
	}
	public int getClockAlignedDataInterval() {
		return ClockAlignedDataInterval;
	}
	public void setClockAlignedDataInterval(int clockAlignedDataInterval) {
		ClockAlignedDataInterval = clockAlignedDataInterval;
	}
	public int getConnectionTimeOut() {
		return ConnectionTimeOut;
	}
	public void setConnectionTimeOut(int connectionTimeOut) {
		ConnectionTimeOut = connectionTimeOut;
	}
	public int getGetConfigurationMaxKeys() {
		return GetConfigurationMaxKeys;
	}
	public void setGetConfigurationMaxKeys(int getConfigurationMaxKeys) {
		GetConfigurationMaxKeys = getConfigurationMaxKeys;
	}
	public int getHeartbeatInterval() {
		return HeartbeatInterval;
	}
	public void setHeartbeatInterval(int heartbeatInterval) {
		HeartbeatInterval = heartbeatInterval;
	}
	public int getLightIntensity() {
		return LightIntensity;
	}
	public void setLightIntensity(int lightIntensity) {
		LightIntensity = lightIntensity;
	}
	public boolean isLocalAuthorizeOffline() {
		return LocalAuthorizeOffline;
	}
	public void setLocalAuthorizeOffline(boolean localAuthorizeOffline) {
		LocalAuthorizeOffline = localAuthorizeOffline;
	}
	public boolean isLocalPreAuthorize() {
		return LocalPreAuthorize;
	}
	public void setLocalPreAuthorize(boolean localPreAuthorize) {
		LocalPreAuthorize = localPreAuthorize;
	}
	public int getMaxEnergyOnInvalidId() {
		return MaxEnergyOnInvalidId;
	}
	public void setMaxEnergyOnInvalidId(int maxEnergyOnInvalidId) {
		MaxEnergyOnInvalidId = maxEnergyOnInvalidId;
	}
	public String getMeterValuesAlignedData() {
		return MeterValuesAlignedData;
	}
	public void setMeterValuesAlignedData(String meterValuesAlignedData) {
		MeterValuesAlignedData = meterValuesAlignedData;
	}
	public String getMeterValuesAlignedDataMaxLength() {
		return MeterValuesAlignedDataMaxLength;
	}
	public void setMeterValuesAlignedDataMaxLength(String meterValuesAlignedDataMaxLength) {
		MeterValuesAlignedDataMaxLength = meterValuesAlignedDataMaxLength;
	}
	public String getMeterValuesSampledData() {
		return MeterValuesSampledData;
	}
	public void setMeterValuesSampledData(String meterValuesSampledData) {
		MeterValuesSampledData = meterValuesSampledData;
	}
	public String getMeterValuesSampledDataMaxLength() {
		return MeterValuesSampledDataMaxLength;
	}
	public void setMeterValuesSampledDataMaxLength(String meterValuesSampledDataMaxLength) {
		MeterValuesSampledDataMaxLength = meterValuesSampledDataMaxLength;
	}
	public int getMeterValueSampleInterval() {
		return MeterValueSampleInterval;
	}
	public void setMeterValueSampleInterval(int meterValueSampleInterval) {
		MeterValueSampleInterval = meterValueSampleInterval;
	}
	public int getMinimumStatusDuration() {
		return MinimumStatusDuration;
	}
	public void setMinimumStatusDuration(int minimumStatusDuration) {
		MinimumStatusDuration = minimumStatusDuration;
	}
	public int getNumberOfConnectors() {
		return NumberOfConnectors;
	}
	public void setNumberOfConnectors(int numberOfConnectors) {
		NumberOfConnectors = numberOfConnectors;
	}
	public int getResetRetries() {
		return ResetRetries;
	}
	public void setResetRetries(int resetRetries) {
		ResetRetries = resetRetries;
	}
	public String getConnectorPhaseRotation() {
		return ConnectorPhaseRotation;
	}
	public void setConnectorPhaseRotation(String connectorPhaseRotation) {
		ConnectorPhaseRotation = connectorPhaseRotation;
	}
	public String getConnectorPhaseRotationMaxLength() {
		return ConnectorPhaseRotationMaxLength;
	}
	public void setConnectorPhaseRotationMaxLength(String connectorPhaseRotationMaxLength) {
		ConnectorPhaseRotationMaxLength = connectorPhaseRotationMaxLength;
	}
	public boolean isStopTransactionOnEVSideDisconnect() {
		return StopTransactionOnEVSideDisconnect;
	}
	public void setStopTransactionOnEVSideDisconnect(boolean stopTransactionOnEVSideDisconnect) {
		StopTransactionOnEVSideDisconnect = stopTransactionOnEVSideDisconnect;
	}
	public boolean isStopTransactionOnInvalidId() {
		return StopTransactionOnInvalidId;
	}
	public void setStopTransactionOnInvalidId(boolean stopTransactionOnInvalidId) {
		StopTransactionOnInvalidId = stopTransactionOnInvalidId;
	}
	public String getStopTxnAlignedData() {
		return StopTxnAlignedData;
	}
	public void setStopTxnAlignedData(String stopTxnAlignedData) {
		StopTxnAlignedData = stopTxnAlignedData;
	}
	public String getStopTxnAlignedDataMaxLength() {
		return StopTxnAlignedDataMaxLength;
	}
	public void setStopTxnAlignedDataMaxLength(String stopTxnAlignedDataMaxLength) {
		StopTxnAlignedDataMaxLength = stopTxnAlignedDataMaxLength;
	}
	public String getStopTxnSampledData() {
		return StopTxnSampledData;
	}
	public void setStopTxnSampledData(String stopTxnSampledData) {
		StopTxnSampledData = stopTxnSampledData;
	}
	public String getStopTxnSampledDataMaxLength() {
		return StopTxnSampledDataMaxLength;
	}
	public void setStopTxnSampledDataMaxLength(String stopTxnSampledDataMaxLength) {
		StopTxnSampledDataMaxLength = stopTxnSampledDataMaxLength;
	}
	public String getSupportedFeatureProfiles() {
		return SupportedFeatureProfiles;
	}
	public void setSupportedFeatureProfiles(String supportedFeatureProfiles) {
		SupportedFeatureProfiles = supportedFeatureProfiles;
	}
	public String getSupportedFeatureProfilesMaxLength() {
		return SupportedFeatureProfilesMaxLength;
	}
	public void setSupportedFeatureProfilesMaxLength(String supportedFeatureProfilesMaxLength) {
		SupportedFeatureProfilesMaxLength = supportedFeatureProfilesMaxLength;
	}
	public int getTransactionMessageAttempts() {
		return TransactionMessageAttempts;
	}
	public void setTransactionMessageAttempts(int transactionMessageAttempts) {
		TransactionMessageAttempts = transactionMessageAttempts;
	}
	public int getTransactionMessageRetryInterval() {
		return TransactionMessageRetryInterval;
	}
	public void setTransactionMessageRetryInterval(int transactionMessageRetryInterval) {
		TransactionMessageRetryInterval = transactionMessageRetryInterval;
	}
	public boolean isUnlockConnectorOnEVSideDisconnect() {
		return UnlockConnectorOnEVSideDisconnect;
	}
	public void setUnlockConnectorOnEVSideDisconnect(boolean unlockConnectorOnEVSideDisconnect) {
		UnlockConnectorOnEVSideDisconnect = unlockConnectorOnEVSideDisconnect;
	}
	public int getWebSocketPingInterval() {
		return WebSocketPingInterval;
	}
	public void setWebSocketPingInterval(int webSocketPingInterval) {
		WebSocketPingInterval = webSocketPingInterval;
	}
	public boolean isLocalAuthListEnabled() {
		return LocalAuthListEnabled;
	}
	public void setLocalAuthListEnabled(boolean localAuthListEnabled) {
		LocalAuthListEnabled = localAuthListEnabled;
	}
	public int getLocalAuthListMaxLength() {
		return LocalAuthListMaxLength;
	}
	public void setLocalAuthListMaxLength(int localAuthListMaxLength) {
		LocalAuthListMaxLength = localAuthListMaxLength;
	}
	public int getSendLocalListMaxLength() {
		return SendLocalListMaxLength;
	}
	public void setSendLocalListMaxLength(int sendLocalListMaxLength) {
		SendLocalListMaxLength = sendLocalListMaxLength;
	}
	public boolean isReserveConnectorZeroSupported() {
		return ReserveConnectorZeroSupported;
	}
	public void setReserveConnectorZeroSupported(boolean reserveConnectorZeroSupported) {
		ReserveConnectorZeroSupported = reserveConnectorZeroSupported;
	}
	public int getChargeProfileMaxStackLevel() {
		return ChargeProfileMaxStackLevel;
	}
	public void setChargeProfileMaxStackLevel(int chargeProfileMaxStackLevel) {
		ChargeProfileMaxStackLevel = chargeProfileMaxStackLevel;
	}
	public String getChargingScheduleAllowedChargingRateUnit() {
		return ChargingScheduleAllowedChargingRateUnit;
	}
	public void setChargingScheduleAllowedChargingRateUnit(String chargingScheduleAllowedChargingRateUnit) {
		ChargingScheduleAllowedChargingRateUnit = chargingScheduleAllowedChargingRateUnit;
	}
	public int getChargingScheduleMaxPeriods() {
		return ChargingScheduleMaxPeriods;
	}
	public void setChargingScheduleMaxPeriods(int chargingScheduleMaxPeriods) {
		ChargingScheduleMaxPeriods = chargingScheduleMaxPeriods;
	}
	public boolean isConnectorSwitch3to1PhaseSupported() {
		return ConnectorSwitch3to1PhaseSupported;
	}
	public void setConnectorSwitch3to1PhaseSupported(boolean connectorSwitch3to1PhaseSupported) {
		ConnectorSwitch3to1PhaseSupported = connectorSwitch3to1PhaseSupported;
	}
	public int getMaxChargingProfilesInstalled() {
		return MaxChargingProfilesInstalled;
	}
	public void setMaxChargingProfilesInstalled(int maxChargingProfilesInstalled) {
		MaxChargingProfilesInstalled = maxChargingProfilesInstalled;
	}
	public boolean isFlagValue() {
		return FlagValue;
	}
	public void setFlagValue(boolean flagValue) {
		FlagValue = flagValue;
	}
	public boolean isDefaultConfig() {
		return defaultConfig;
	}
	public void setDefaultConfig(boolean defaultConfig) {
		this.defaultConfig = defaultConfig;
	}
	@Override
	public String toString() {
		return "StationConfig [stationId=" + stationId + ", firmwareVersion=" + firmwareVersion
				+ ", AllowOfflineTxForUnknownId=" + AllowOfflineTxForUnknownId + ", AuthorizationCacheEnabled="
				+ AuthorizationCacheEnabled + ", AuthorizeRemoteTxRequests=" + AuthorizeRemoteTxRequests
				+ ", BlinkRepeat=" + BlinkRepeat + ", ClockAlignedDataInterval=" + ClockAlignedDataInterval
				+ ", ConnectionTimeOut=" + ConnectionTimeOut + ", GetConfigurationMaxKeys=" + GetConfigurationMaxKeys
				+ ", HeartbeatInterval=" + HeartbeatInterval + ", LightIntensity=" + LightIntensity
				+ ", LocalAuthorizeOffline=" + LocalAuthorizeOffline + ", LocalPreAuthorize=" + LocalPreAuthorize
				+ ", MaxEnergyOnInvalidId=" + MaxEnergyOnInvalidId + ", MeterValuesAlignedData="
				+ MeterValuesAlignedData + ", MeterValuesAlignedDataMaxLength=" + MeterValuesAlignedDataMaxLength
				+ ", MeterValuesSampledData=" + MeterValuesSampledData + ", MeterValuesSampledDataMaxLength="
				+ MeterValuesSampledDataMaxLength + ", MeterValueSampleInterval=" + MeterValueSampleInterval
				+ ", MinimumStatusDuration=" + MinimumStatusDuration + ", NumberOfConnectors=" + NumberOfConnectors
				+ ", ResetRetries=" + ResetRetries + ", ConnectorPhaseRotation=" + ConnectorPhaseRotation
				+ ", ConnectorPhaseRotationMaxLength=" + ConnectorPhaseRotationMaxLength
				+ ", StopTransactionOnEVSideDisconnect=" + StopTransactionOnEVSideDisconnect
				+ ", StopTransactionOnInvalidId=" + StopTransactionOnInvalidId + ", StopTxnAlignedData="
				+ StopTxnAlignedData + ", StopTxnAlignedDataMaxLength=" + StopTxnAlignedDataMaxLength
				+ ", StopTxnSampledData=" + StopTxnSampledData + ", StopTxnSampledDataMaxLength="
				+ StopTxnSampledDataMaxLength + ", SupportedFeatureProfiles=" + SupportedFeatureProfiles
				+ ", SupportedFeatureProfilesMaxLength=" + SupportedFeatureProfilesMaxLength
				+ ", TransactionMessageAttempts=" + TransactionMessageAttempts + ", TransactionMessageRetryInterval="
				+ TransactionMessageRetryInterval + ", UnlockConnectorOnEVSideDisconnect="
				+ UnlockConnectorOnEVSideDisconnect + ", WebSocketPingInterval=" + WebSocketPingInterval
				+ ", LocalAuthListEnabled=" + LocalAuthListEnabled + ", LocalAuthListMaxLength="
				+ LocalAuthListMaxLength + ", SendLocalListMaxLength=" + SendLocalListMaxLength
				+ ", ReserveConnectorZeroSupported=" + ReserveConnectorZeroSupported + ", ChargeProfileMaxStackLevel="
				+ ChargeProfileMaxStackLevel + ", ChargingScheduleAllowedChargingRateUnit="
				+ ChargingScheduleAllowedChargingRateUnit + ", ChargingScheduleMaxPeriods=" + ChargingScheduleMaxPeriods
				+ ", ConnectorSwitch3to1PhaseSupported=" + ConnectorSwitch3to1PhaseSupported
				+ ", MaxChargingProfilesInstalled=" + MaxChargingProfilesInstalled + ", FlagValue=" + FlagValue
				+ ", defaultConfig=" + defaultConfig + "]";
	}
	
	
}
