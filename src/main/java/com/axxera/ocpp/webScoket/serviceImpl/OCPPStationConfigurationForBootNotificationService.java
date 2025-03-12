package com.axxera.ocpp.webScoket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.message.StationConfig;
import com.axxera.ocpp.model.ocpp.Station;
import com.axxera.ocpp.model.ocpp.StationConfigurationForBootNotification;
import com.axxera.ocpp.webSocket.service.StationService;



@Service
public class OCPPStationConfigurationForBootNotificationService {

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private StationService stationService;

	public StationConfigurationForBootNotification get(long stationId)  {
		StationConfigurationForBootNotification findOne = null;
		try {
			findOne = generalDao.findOne("From StationConfigurationForBootNotification WHERE stationId=" + stationId+"  order by id desc", new StationConfigurationForBootNotification());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return findOne;
	}

	public void insertData(StationConfig stnConfigBoot, long clientid,String stationRefNum)  {
		try {

			StationConfigurationForBootNotification ocppStationConfigurationForBootNotification = get(clientid);
			Station stn = new Station();
			stn.setId(clientid);
			if(ocppStationConfigurationForBootNotification==null) {
				ocppStationConfigurationForBootNotification = new StationConfigurationForBootNotification();
				ocppStationConfigurationForBootNotification.setStation(stn);
			}
			
			if(ocppStationConfigurationForBootNotification.getStation() == null) {
				ocppStationConfigurationForBootNotification.setStation(stn);
			}
			
			ocppStationConfigurationForBootNotification.setConnectorPhaseRotation(stnConfigBoot.getConnectorPhaseRotation() == null ? "0" : stnConfigBoot.getConnectorPhaseRotation());
			ocppStationConfigurationForBootNotification.setConnectorPhaseRotationMaxLength(stnConfigBoot.getConnectorPhaseRotationMaxLength() == null ? "0" : stnConfigBoot.getConnectorPhaseRotationMaxLength());
			ocppStationConfigurationForBootNotification.setLocalAuthListEnabled(stnConfigBoot.isLocalAuthListEnabled());
			ocppStationConfigurationForBootNotification.setMeterValuesAlignedData(stnConfigBoot.getMeterValuesAlignedData() == null ? "0" : stnConfigBoot.getMeterValuesAlignedData());
			ocppStationConfigurationForBootNotification.setMeterValuesAlignedDataMaxLength(stnConfigBoot.getMeterValuesAlignedDataMaxLength() == null ? "0" : stnConfigBoot.getMeterValuesAlignedDataMaxLength());
			ocppStationConfigurationForBootNotification.setMeterValuesSampledData(stnConfigBoot.getMeterValuesSampledData() == null ? "0" : stnConfigBoot.getMeterValuesSampledData());
			ocppStationConfigurationForBootNotification.setMeterValuesSampledDataMaxLength(stnConfigBoot.getMeterValuesSampledDataMaxLength() == null ? "0" : stnConfigBoot.getMeterValuesSampledDataMaxLength());
			ocppStationConfigurationForBootNotification.setSendLocalListMaxLength(stnConfigBoot.getSendLocalListMaxLength());
			ocppStationConfigurationForBootNotification.setStopTxnAlignedData(stnConfigBoot.getStopTxnAlignedData() == null ? "0" : stnConfigBoot.getStopTxnAlignedData());
			ocppStationConfigurationForBootNotification.setStopTxnAlignedDataMaxLength(stnConfigBoot.getStopTxnAlignedDataMaxLength() == null ? "0" : stnConfigBoot.getStopTxnAlignedDataMaxLength());
			ocppStationConfigurationForBootNotification.setStopTxnSampledData(stnConfigBoot.getStopTxnSampledData() == null ? "0" : stnConfigBoot.getStopTxnSampledData());
			ocppStationConfigurationForBootNotification.setStopTxnSampledDataMaxLength(stnConfigBoot.getStopTxnSampledDataMaxLength() == null ? "0" : stnConfigBoot.getStopTxnSampledDataMaxLength());
			ocppStationConfigurationForBootNotification.setSupportedFeatureProfiles(stnConfigBoot.getSupportedFeatureProfiles() == null ? "0" : stnConfigBoot.getSupportedFeatureProfiles());
			ocppStationConfigurationForBootNotification.setSupportedFeatureProfilesMaxLength(stnConfigBoot.getSupportedFeatureProfilesMaxLength() == null ? "0" : stnConfigBoot.getSupportedFeatureProfilesMaxLength());
			ocppStationConfigurationForBootNotification.setTransactionMessageRetryInterval(stnConfigBoot.getTransactionMessageRetryInterval());
			ocppStationConfigurationForBootNotification.setHeartbeatInterval(stnConfigBoot.getHeartbeatInterval());
			ocppStationConfigurationForBootNotification.setMeterValueSampleInterval(stnConfigBoot.getMeterValueSampleInterval());
			ocppStationConfigurationForBootNotification.setGetConfigurationMaxKeys(stnConfigBoot.getGetConfigurationMaxKeys());
			ocppStationConfigurationForBootNotification.setMinimumStatusDuration(stnConfigBoot.getMinimumStatusDuration());
			ocppStationConfigurationForBootNotification.setNumberOfConnectors(stnConfigBoot.getNumberOfConnectors());
			ocppStationConfigurationForBootNotification.setConnectionTimeOut(stnConfigBoot.getConnectionTimeOut());
			ocppStationConfigurationForBootNotification.setResetRetries(stnConfigBoot.getResetRetries());
			ocppStationConfigurationForBootNotification.setStopTransactionOnEVSideDisconnect(stnConfigBoot.isStopTransactionOnEVSideDisconnect());
			ocppStationConfigurationForBootNotification.setStopTransactionOnInvalidId(stnConfigBoot.isStopTransactionOnInvalidId());
			ocppStationConfigurationForBootNotification.setTransactionMessageAttempts(stnConfigBoot.getTransactionMessageAttempts());
			ocppStationConfigurationForBootNotification.setUnlockConnectorOnEVSideDisconnect(stnConfigBoot.isUnlockConnectorOnEVSideDisconnect());
			//ocppStationConfigurationForBootNotification.setChargeBoxName(stnConfigBoot.getChargeBoxName() == null ? "0" : stnConfigBoot.getChargeBoxName());
			//ocppStationConfigurationForBootNotification.setChargeBoxTimeZone(stnConfigBoot.getChargeBoxTimeZone() == null ? "0" : stnConfigBoot.getChargeBoxTimeZone());
			//ocppStationConfigurationForBootNotification.setContactCenter(stnConfigBoot.getContactCenter() == null ? "0" : stnConfigBoot.getContactCenter());
			//ocppStationConfigurationForBootNotification.setAvailablePaymentType(stnConfigBoot.getAvailablePaymentType() == null ? "0" : stnConfigBoot.getAvailablePaymentType());
			//ocppStationConfigurationForBootNotification.setFreevendEnabled(stnConfigBoot.getFreevendEnabled() == null ? "0" : stnConfigBoot.getFreevendEnabled());
			//ocppStationConfigurationForBootNotification.setFreevendIdTag(stnConfigBoot.getFreevendIdTag() == null ? "0" : stnConfigBoot.getFreevendIdTag());
			ocppStationConfigurationForBootNotification.setLocalAuthorizeOffline(stnConfigBoot.isLocalAuthorizeOffline());
			//ocppStationConfigurationForBootNotification.setAuthorizationRequired(stnConfigBoot.isAuthorizationRequired());
			//ocppStationConfigurationForBootNotification.setNonAuthorizedTag(stnConfigBoot.getNonAuthorizedTag() == null ? "0" : stnConfigBoot.getNonAuthorizedTag());
			ocppStationConfigurationForBootNotification.setLocalPreAuthorize(stnConfigBoot.isLocalPreAuthorize());
			ocppStationConfigurationForBootNotification.setMaxEnergyOnInvalidId(stnConfigBoot.getMaxEnergyOnInvalidId());
			ocppStationConfigurationForBootNotification.setMeterValuesAlignedData(stnConfigBoot.getMeterValuesAlignedData() == null ? "0" : stnConfigBoot.getMeterValuesAlignedData());
			//ocppStationConfigurationForBootNotification.setCoreRemoteTrigger(stnConfigBoot.getCoreRemoteTrigger() == null ? "0" : stnConfigBoot.getCoreRemoteTrigger());
			ocppStationConfigurationForBootNotification.setWebSocketPingInterval(stnConfigBoot.getWebSocketPingInterval());
			ocppStationConfigurationForBootNotification.setAllowOfflineTxForUnknownId(stnConfigBoot.isAllowOfflineTxForUnknownId());
			ocppStationConfigurationForBootNotification.setAuthorizationCacheEnabled(stnConfigBoot.isAuthorizationCacheEnabled());
			ocppStationConfigurationForBootNotification.setAuthorizeRemoteTxRequests(stnConfigBoot.isAuthorizeRemoteTxRequests());
			ocppStationConfigurationForBootNotification.setBlinkRepeat(stnConfigBoot.getBlinkRepeat());
			ocppStationConfigurationForBootNotification.setReserveConnectorZeroSupported(stnConfigBoot.isReserveConnectorZeroSupported());
			ocppStationConfigurationForBootNotification.setLightIntensity(stnConfigBoot.getLightIntensity());
			ocppStationConfigurationForBootNotification.setClockAlignedDataInterval(stnConfigBoot.getClockAlignedDataInterval());
			ocppStationConfigurationForBootNotification.setChargeProfileMaxStackLevel(stnConfigBoot.getChargeProfileMaxStackLevel());
			ocppStationConfigurationForBootNotification.setChargingScheduleAllowedChargingRateUnit(stnConfigBoot.getChargingScheduleAllowedChargingRateUnit() == null ? "0" : stnConfigBoot.getChargingScheduleAllowedChargingRateUnit());
			ocppStationConfigurationForBootNotification.setChargingScheduleMaxPeriods(stnConfigBoot.getChargingScheduleMaxPeriods());
			ocppStationConfigurationForBootNotification.setConnectorSwitch3to1PhaseSupported(false);
			ocppStationConfigurationForBootNotification.setMaxChargingProfilesInstalled(stnConfigBoot.getMaxChargingProfilesInstalled());
			//ocppStationConfigurationForBootNotification.setMaxChargingTimeEnabled(stnConfigBoot.getMaxChargingTimeEnabled() == null ? "0" : stnConfigBoot.getMaxChargingTimeEnabled());
			//ocppStationConfigurationForBootNotification.setMaxChargingTime(stnConfigBoot.getMaxChargingTime() == null ? "0" : stnConfigBoot.getMaxChargingTime());
			//ocppStationConfigurationForBootNotification.setMaxSOCLimit(stnConfigBoot.getMaxSOCLimit() == null ? "0" : stnConfigBoot.getMaxSOCLimit());
			//ocppStationConfigurationForBootNotification.setCreditCardIdTag(stnConfigBoot.getCreditCardIdTag() == null ? "0" : stnConfigBoot.getCreditCardIdTag());
			//ocppStationConfigurationForBootNotification.setQRCodeContent(stnConfigBoot.getQRCodeContent() == null ? "0" : stnConfigBoot.getQRCodeContent());
			//ocppStationConfigurationForBootNotification.setRatedPMax(stnConfigBoot.getRatedPMax() == null ? "0" : stnConfigBoot.getRatedPMax());
			//ocppStationConfigurationForBootNotification.setSilentFreevendEnabled(stnConfigBoot.getSilentFreevendEnabled() == null ? "0" : stnConfigBoot.getSilentFreevendEnabled());
			ocppStationConfigurationForBootNotification.setFlagValue(stnConfigBoot.isFlagValue());
			ocppStationConfigurationForBootNotification.setDefaultConfig(ocppStationConfigurationForBootNotification.isDefaultConfig());
			generalDao.saveOrupdate(ocppStationConfigurationForBootNotification);
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
