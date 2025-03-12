package com.axxera.ocpp.message;

public class chargerConfigurationsMessage {

	private long stationId;
	private int allowOfflineTxForUnknownId;
	private int authorizationCacheEnabled;
	private int authorizeRemoteTxRequests;
	private int blinkRepeat;
	private int clockAlignedDataInterval;
	private int connectionTimeOut;
	private int getConfigurationMaxKeys;
	private int heartbeatInterval;
	private int lightIntensity;
	private int localAuthorizeOffline;
	private int localPreAuthorize;
	private int maxEnergyOnInvalidId;
	private String meterValuesAlignedData;
	private String meterValuesAlignedDataMaxLength;
	private String meterValuesSampledData;
	private String meterValuesSampledDataMaxLength;
	private int meterValueSampleInterval;
	private int minimumStatusDuration;
	private int numberOfConnectors;
	private int resetRetries;
	private String connectorPhaseRotation;
	private String connectorPhaseRotationMaxLength;
	private int stopTransactionOnEVSideDisconnect;
	private int stopTransactionOnInvalidId;
	private String stopTxnAlignedData;
	private String stopTxnAlignedDataMaxLength;
	private String stopTxnSampledData;
	private String stopTxnSampledDataMaxLength;
	private String supportedFeatureProfiles;
	private String supportedFeatureProfilesMaxLength;
	private int transactionMessageAttempts;
	private int transactionMessageRetryInterval;
	private int unlockConnectorOnEVSideDisconnect;
	private int webSocketPingInterval;
	private boolean flagvalue;
	private int localAuthListEnabled;
	private int sendLocalListMaxLength;
	private String firmwareVersion;
	
	

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public long getStationId() {
		return stationId;
	}

	public void setStationId(long stationId) {
		this.stationId = stationId;
	}

	public int getAuthorizationCacheEnabled() {
		return authorizationCacheEnabled;
	}

	public void setAuthorizationCacheEnabled(int authorizationCacheEnabled) {
		this.authorizationCacheEnabled = authorizationCacheEnabled;
	}

	public int getAuthorizeRemoteTxRequests() {
		return authorizeRemoteTxRequests;
	}

	public void setAuthorizeRemoteTxRequests(int authorizeRemoteTxRequests) {
		this.authorizeRemoteTxRequests = authorizeRemoteTxRequests;
	}

	public int getLocalAuthorizeOffline() {
		return localAuthorizeOffline;
	}

	public void setLocalAuthorizeOffline(int localAuthorizeOffline) {
		this.localAuthorizeOffline = localAuthorizeOffline;
	}

	public int getLocalPreAuthorize() {
		return localPreAuthorize;
	}

	public void setLocalPreAuthorize(int localPreAuthorize) {
		this.localPreAuthorize = localPreAuthorize;
	}

	public int getStopTransactionOnEVSideDisconnect() {
		return stopTransactionOnEVSideDisconnect;
	}

	public void setStopTransactionOnEVSideDisconnect(int stopTransactionOnEVSideDisconnect) {
		this.stopTransactionOnEVSideDisconnect = stopTransactionOnEVSideDisconnect;
	}

	public int getStopTransactionOnInvalidId() {
		return stopTransactionOnInvalidId;
	}

	public void setStopTransactionOnInvalidId(int stopTransactionOnInvalidId) {
		this.stopTransactionOnInvalidId = stopTransactionOnInvalidId;
	}

	public int getUnlockConnectorOnEVSideDisconnect() {
		return unlockConnectorOnEVSideDisconnect;
	}

	public void setUnlockConnectorOnEVSideDisconnect(int unlockConnectorOnEVSideDisconnect) {
		this.unlockConnectorOnEVSideDisconnect = unlockConnectorOnEVSideDisconnect;
	}

	public int getAllowOfflineTxForUnknownId() {
		return allowOfflineTxForUnknownId;
	}

	public boolean getFlagvalue() {
		return flagvalue;
	}

	public int getLocalAuthListEnabled() {
		return localAuthListEnabled;
	}

	public void setBlinkRepeat(int blinkRepeat) {
		this.blinkRepeat = blinkRepeat;
	}

	public void setClockAlignedDataInterval(int clockAlignedDataInterval) {
		this.clockAlignedDataInterval = clockAlignedDataInterval;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public void setGetConfigurationMaxKeys(int getConfigurationMaxKeys) {
		this.getConfigurationMaxKeys = getConfigurationMaxKeys;
	}

	public void setHeartbeatInterval(int heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}

	public void setLightIntensity(int lightIntensity) {
		this.lightIntensity = lightIntensity;
	}

	public void setMaxEnergyOnInvalidId(int maxEnergyOnInvalidId) {
		this.maxEnergyOnInvalidId = maxEnergyOnInvalidId;
	}

	public void setMeterValuesAlignedData(String meterValuesAlignedData) {
		this.meterValuesAlignedData = meterValuesAlignedData;
	}

	public void setMeterValuesAlignedDataMaxLength(String meterValuesAlignedDataMaxLength) {
		this.meterValuesAlignedDataMaxLength = meterValuesAlignedDataMaxLength;
	}

	public void setMeterValuesSampledData(String meterValuesSampledData) {
		this.meterValuesSampledData = meterValuesSampledData;
	}

	public void setMeterValuesSampledDataMaxLength(String meterValuesSampledDataMaxLength) {
		this.meterValuesSampledDataMaxLength = meterValuesSampledDataMaxLength;
	}

	public void setMeterValueSampleInterval(int meterValueSampleInterval) {
		this.meterValueSampleInterval = meterValueSampleInterval;
	}

	public void setMinimumStatusDuration(int minimumStatusDuration) {
		this.minimumStatusDuration = minimumStatusDuration;
	}

	public void setNumberOfConnectors(int numberOfConnectors) {
		this.numberOfConnectors = numberOfConnectors;
	}

	public void setResetRetries(int resetRetries) {
		this.resetRetries = resetRetries;
	}

	public void setConnectorPhaseRotation(String connectorPhaseRotation) {
		this.connectorPhaseRotation = connectorPhaseRotation;
	}

	public void setConnectorPhaseRotationMaxLength(String connectorPhaseRotationMaxLength) {
		this.connectorPhaseRotationMaxLength = connectorPhaseRotationMaxLength;
	}

	public void setStopTxnAlignedData(String stopTxnAlignedData) {
		this.stopTxnAlignedData = stopTxnAlignedData;
	}

	public void setStopTxnAlignedDataMaxLength(String stopTxnAlignedDataMaxLength) {
		this.stopTxnAlignedDataMaxLength = stopTxnAlignedDataMaxLength;
	}

	public void setStopTxnSampledData(String stopTxnSampledData) {
		this.stopTxnSampledData = stopTxnSampledData;
	}

	public void setStopTxnSampledDataMaxLength(String stopTxnSampledDataMaxLength) {
		this.stopTxnSampledDataMaxLength = stopTxnSampledDataMaxLength;
	}

	public void setSupportedFeatureProfiles(String supportedFeatureProfiles) {
		this.supportedFeatureProfiles = supportedFeatureProfiles;
	}

	public void setSupportedFeatureProfilesMaxLength(String supportedFeatureProfilesMaxLength) {
		this.supportedFeatureProfilesMaxLength = supportedFeatureProfilesMaxLength;
	}

	public void setTransactionMessageAttempts(int transactionMessageAttempts) {
		this.transactionMessageAttempts = transactionMessageAttempts;
	}

	public void setTransactionMessageRetryInterval(int transactionMessageRetryInterval) {
		this.transactionMessageRetryInterval = transactionMessageRetryInterval;
	}

	public void setWebSocketPingInterval(int webSocketPingInterval) {
		this.webSocketPingInterval = webSocketPingInterval;
	}
	
	public void setAllowOfflineTxForUnknownId(int allowOfflineTxForUnknownId) {
		this.allowOfflineTxForUnknownId = allowOfflineTxForUnknownId;
	}
	
	public int getBlinkRepeat() {
		return blinkRepeat;
	}
	
	public int getClockAlignedDataInterval() {
		return clockAlignedDataInterval;
	}
	
	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public int getGetConfigurationMaxKeys() {
		return getConfigurationMaxKeys;
	}	

	public int getHeartbeatInterval() {
		return heartbeatInterval;
	}	

	public int getLightIntensity() {
		return lightIntensity;
	}	

	public int getMaxEnergyOnInvalidId() {
		return maxEnergyOnInvalidId;
	}	

	public String getMeterValuesAlignedData() {
		return meterValuesAlignedData;
	}

	public String getMeterValuesAlignedDataMaxLength() {
		return meterValuesAlignedDataMaxLength;
	}

	
	

	public String getMeterValuesSampledData() {
		return meterValuesSampledData;
	}

	
	

	public String getMeterValuesSampledDataMaxLength() {
		return meterValuesSampledDataMaxLength;
	}

	
	

	public int getMeterValueSampleInterval() {
		return meterValueSampleInterval;
	}

	
	

	public int getMinimumStatusDuration() {
		return minimumStatusDuration;
	}

	

	

	public int getNumberOfConnectors() {
		return numberOfConnectors;
	}

	

	

	public int getResetRetries() {
		return resetRetries;
	}

	
	

	public String getConnectorPhaseRotation() {
		return connectorPhaseRotation;
	}

	

	

	public String getConnectorPhaseRotationMaxLength() {
		return connectorPhaseRotationMaxLength;
	}

	

	

	

	
	

	
	

	public String getStopTxnAlignedData() {
		return stopTxnAlignedData;
	}

	

	public String getStopTxnAlignedDataMaxLength() {
		return stopTxnAlignedDataMaxLength;
	}

	
	

	public String getStopTxnSampledData() {
		return stopTxnSampledData;
	}
	
	public String getStopTxnSampledDataMaxLength() {
		return stopTxnSampledDataMaxLength;
	}

	public String getSupportedFeatureProfiles() {
		return supportedFeatureProfiles;
	}

	public String getSupportedFeatureProfilesMaxLength() {
		return supportedFeatureProfilesMaxLength;
	}

	public int getTransactionMessageAttempts() {
		return transactionMessageAttempts;
	}

	public int getTransactionMessageRetryInterval() {
		return transactionMessageRetryInterval;
	}

	public int getWebSocketPingInterval() {
		return webSocketPingInterval;
	}

	public void setFlagvalue(boolean flagvalue) {
		this.flagvalue = flagvalue;
	}

	public void setLocalAuthListEnabled(int localAuthListEnabled) {
		this.localAuthListEnabled = localAuthListEnabled;
	}

	public int getSendLocalListMaxLength() {
		return sendLocalListMaxLength;
	}

	public void setSendLocalListMaxLength(int sendLocalListMaxLength) {
		this.sendLocalListMaxLength = sendLocalListMaxLength;
	}

	@Override
	public String toString() {
		return "chargerConfigurationsMessage [stationId=" + stationId + ", allowOfflineTxForUnknownId="
				+ allowOfflineTxForUnknownId + ", authorizationCacheEnabled=" + authorizationCacheEnabled
				+ ", authorizeRemoteTxRequests=" + authorizeRemoteTxRequests + ", blinkRepeat=" + blinkRepeat
				+ ", clockAlignedDataInterval=" + clockAlignedDataInterval + ", connectionTimeOut=" + connectionTimeOut
				+ ", getConfigurationMaxKeys=" + getConfigurationMaxKeys + ", heartbeatInterval=" + heartbeatInterval
				+ ", lightIntensity=" + lightIntensity + ", localAuthorizeOffline=" + localAuthorizeOffline
				+ ", localPreAuthorize=" + localPreAuthorize + ", maxEnergyOnInvalidId=" + maxEnergyOnInvalidId
				+ ", meterValuesAlignedData=" + meterValuesAlignedData + ", meterValuesAlignedDataMaxLength="
				+ meterValuesAlignedDataMaxLength + ", meterValuesSampledData=" + meterValuesSampledData
				+ ", meterValuesSampledDataMaxLength=" + meterValuesSampledDataMaxLength + ", meterValueSampleInterval="
				+ meterValueSampleInterval + ", minimumStatusDuration=" + minimumStatusDuration
				+ ", numberOfConnectors=" + numberOfConnectors + ", resetRetries=" + resetRetries
				+ ", connectorPhaseRotation=" + connectorPhaseRotation + ", connectorPhaseRotationMaxLength="
				+ connectorPhaseRotationMaxLength + ", stopTransactionOnEVSideDisconnect="
				+ stopTransactionOnEVSideDisconnect + ", stopTransactionOnInvalidId=" + stopTransactionOnInvalidId
				+ ", stopTxnAlignedData=" + stopTxnAlignedData + ", stopTxnAlignedDataMaxLength="
				+ stopTxnAlignedDataMaxLength + ", stopTxnSampledData=" + stopTxnSampledData
				+ ", stopTxnSampledDataMaxLength=" + stopTxnSampledDataMaxLength + ", supportedFeatureProfiles="
				+ supportedFeatureProfiles + ", supportedFeatureProfilesMaxLength=" + supportedFeatureProfilesMaxLength
				+ ", transactionMessageAttempts=" + transactionMessageAttempts + ", transactionMessageRetryInterval="
				+ transactionMessageRetryInterval + ", unlockConnectorOnEVSideDisconnect="
				+ unlockConnectorOnEVSideDisconnect + ", webSocketPingInterval=" + webSocketPingInterval
				+ ", flagvalue=" + flagvalue + ", localAuthListEnabled=" + localAuthListEnabled
				+ ", sendLocalListMaxLength=" + sendLocalListMaxLength + ", firmwareVersion=" + firmwareVersion + "]";
	}
}
