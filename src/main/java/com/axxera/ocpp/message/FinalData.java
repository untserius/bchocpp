package com.axxera.ocpp.message;

import java.util.ArrayList;
import java.util.List;

import com.axxera.ocpp.model.ocpp.ClearChargingProfile;
import com.axxera.ocpp.model.ocpp.GetCompositeSchedule;
import com.axxera.ocpp.model.ocpp.SetChargingProfile;

public class FinalData {

	private Long firstValue;
	private String secondValue;
	private String thirdValue;

	private Authorize authorize;
	private BootNotification bootNotification;
	private Heartbeat heartbeat;
	private MeterValues meterValues;
	private RemoteStopTransaction remoteStopTransaction;
	private RemoteStartTransaction remoteStartTransaction;
	private StartTransaction startTransaction;
	private StatusNotification statusNotification;
	private StopTransaction stopTransaction;
	private Reset reset;
	private ChangeAvailability changeAvailability;
	private UnlockConnector unlockConnector;
	private GetConfiguration getConfiguration;
	private VendingPrice vendingPrice;
	private GetDiagnostics getDiagnostics;
	private FirmwareStatusNotification firmwareStatusNotification;
	private DiagnosticsStatusNotification diagnosticsStatusNotification;
	private SignCertificate SignCertificate;
	private SecurityEventNotification SecurityEventNotification;
	private FrimWare frimWare;
	private SiteUpdate siteUpdate;
	private LogStatusNotification LogStatusNotification;
	private ChangeStatus changeStatus;
	private SignedFirmwareStatusNotification SignedFirmwareStatusNotification;
	

	private ClearCache clearCache;
	
	private ChangeConfiguration changeConfiguration;
	
	private ReserveNow reserveNow;
	
	private TriggerMessage triggerMessage;
	
	private SendLocalList sendLocalList;
	
	private Custom custom;

	private List<ConfigurationKey> configurationKey = new ArrayList<ConfigurationKey>();

	private List<UnknownKey> unknownKey = new ArrayList<UnknownKey>();

	private Status status;

	private GetLocalListVersion localListVersion;
	
	private OcppUrls ocppUrls;
	
	private CancelReservation cancelReservation;
	
	private Datatransfer datatransfer;
	
	private HeartBeatChangeConfiguration heartBeatChangeConfiguration;
	
	private String stnReferNum;
	private SetChargingProfile setChargingProfile;
	private ClearChargingProfile clearChargingProfile;
	private GetCompositeSchedule getCompositeSchedule;
	private RemoteStartWithSmartCharging RemoteStartWithSmartCharging;
	
	
	
	public RemoteStartWithSmartCharging getRemoteStartWithSmartCharging() {
		return RemoteStartWithSmartCharging;
	}

	public void setRemoteStartWithSmartCharging(RemoteStartWithSmartCharging remoteStartWithSmartCharging) {
		RemoteStartWithSmartCharging = remoteStartWithSmartCharging;
	}

	public HeartBeatChangeConfiguration getHeartBeatChangeConfiguration() {
		return heartBeatChangeConfiguration;
	}

	public void setHeartBeatChangeConfiguration(HeartBeatChangeConfiguration heartBeatChangeConfiguration) {
		this.heartBeatChangeConfiguration = heartBeatChangeConfiguration;
	}

	public Datatransfer getDatatransfer() {
		return datatransfer;
	}

	public void setDatatransfer(Datatransfer datatransfer) {
		this.datatransfer = datatransfer;
	}

	public SendLocalList getSendLocalList() {
		return sendLocalList;
	}

	public void setSendLocalList(SendLocalList sendLocalList) {
		this.sendLocalList = sendLocalList;
	}

	public CancelReservation getCancelReservation() {
		return cancelReservation;
	}

	public void setCancelReservation(CancelReservation cancelReservation) {
		this.cancelReservation = cancelReservation;
	}

	public OcppUrls getOcppUrls() {
		return ocppUrls;
	}

	public void setOcppUrls(OcppUrls ocppUrls) {
		this.ocppUrls = ocppUrls;
	}

	public GetLocalListVersion getLocalListVersion() {
		return localListVersion;
	}

	public void setLocalListVersion(GetLocalListVersion localListVersion) {
		this.localListVersion = localListVersion;
	}

	public TriggerMessage getTriggerMessage() {
		return triggerMessage;
	}

	public void setTriggerMessage(TriggerMessage triggerMessage) {
		this.triggerMessage = triggerMessage;
	}

	public ReserveNow getReserveNow() {
		return reserveNow;
	}

	public void setReserveNow(ReserveNow reserveNow) {
		this.reserveNow = reserveNow;
	}

	public Long getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(Long firstValue) {
		this.firstValue = firstValue;
	}

	public String getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}

	public Authorize getAuthorize() {
		return authorize;
	}

	public void setAuthorize(Authorize authorize) {
		this.authorize = authorize;
	}

	public BootNotification getBootNotification() {
		return bootNotification;
	}

	public void setBootNotification(BootNotification bootNotification) {
		this.bootNotification = bootNotification;
	}

	public Heartbeat getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(Heartbeat heartbeat) {
		this.heartbeat = heartbeat;
	}

	public MeterValues getMeterValues() {
		return meterValues;
	}

	public void setMeterValues(MeterValues meterValues) {
		this.meterValues = meterValues;
	}

	public RemoteStopTransaction getRemoteStopTransaction() {
		return remoteStopTransaction;
	}

	public void setRemoteStopTransaction(RemoteStopTransaction remoteStopTransaction) {
		this.remoteStopTransaction = remoteStopTransaction;
	}

	public StartTransaction getStartTransaction() {
		return startTransaction;
	}

	public void setStartTransaction(StartTransaction startTransaction) {
		this.startTransaction = startTransaction;
	}

	public StatusNotification getStatusNotification() {
		return statusNotification;
	}

	public void setStatusNotification(StatusNotification statusNotification) {
		this.statusNotification = statusNotification;
	}

	public StopTransaction getStopTransaction() {
		return stopTransaction;
	}

	public void setStopTransaction(StopTransaction stopTransaction) {
		this.stopTransaction = stopTransaction;
	}

	public Reset getReset() {
		return reset;
	}

	public void setReset(Reset reset) {
		this.reset = reset;
	}

	public List<ConfigurationKey> getConfigurationKey() {
		return configurationKey;
	}

	public void setConfigurationKey(List<ConfigurationKey> configurationKey) {
		this.configurationKey = configurationKey;
	}

	public List<UnknownKey> getUnknownKey() {
		return unknownKey;
	}

	public void setUnknownKey(List<UnknownKey> unknownKey) {
		this.unknownKey = unknownKey;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public RemoteStartTransaction getRemoteStartTransaction() {
		return remoteStartTransaction;
	}

	public void setRemoteStartTransaction(RemoteStartTransaction remoteStartTransaction) {
		this.remoteStartTransaction = remoteStartTransaction;
	}

	public ChangeAvailability getChangeAvailability() {
		return changeAvailability;
	}

	public void setChangeAvailability(ChangeAvailability changeAvailability) {
		this.changeAvailability = changeAvailability;
	}

	public UnlockConnector getUnlockConnector() {
		return unlockConnector;
	}

	public void setUnlockConnector(UnlockConnector unlockConnector) {
		this.unlockConnector = unlockConnector;
	}

	public GetConfiguration getGetConfiguration() {
		return getConfiguration;
	}

	public void setGetConfiguration(GetConfiguration getConfiguration) {
		this.getConfiguration = getConfiguration;
	}

	public VendingPrice getVendingPrice() {
		return vendingPrice;
	}

	public void setVendingPrice(VendingPrice vendingPrice) {
		this.vendingPrice = vendingPrice;
	}

	public GetDiagnostics getGetDiagnostics() {
		return getDiagnostics;
	}

	public void setGetDiagnostics(GetDiagnostics getDiagnostics) {
		this.getDiagnostics = getDiagnostics;
	}

	public FrimWare getFrimWare() {
		return frimWare;
	}

	public void setFrimWare(FrimWare frimWare) {
		this.frimWare = frimWare;
	}

	public SiteUpdate getSiteUpdate() {
		return siteUpdate;
	}

	public void setSiteUpdate(SiteUpdate siteUpdate) {
		this.siteUpdate = siteUpdate;
	}

	public ClearCache getClearCache() {
		return clearCache;
	}

	public void setClearCache(ClearCache clearCache) {
		this.clearCache = clearCache;
	}

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}

	public FirmwareStatusNotification getFirmwareStatusNotification() {
		return firmwareStatusNotification;
	}

	public void setFirmwareStatusNotification(FirmwareStatusNotification firmwareStatusNotification) {
		this.firmwareStatusNotification = firmwareStatusNotification;
	}

	public ChangeConfiguration getChangeConfiguration() {
		return changeConfiguration;
	}

	public void setChangeConfiguration(ChangeConfiguration changeConfiguration) {
		this.changeConfiguration = changeConfiguration;
	}

	public String getStnReferNum() {
		return stnReferNum;
	}

	public void setStnReferNum(String stnReferNum) {
		this.stnReferNum = stnReferNum;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
	}

	public SetChargingProfile getSetChargingProfile() {
		return setChargingProfile;
	}

	public void setSetChargingProfile(SetChargingProfile setChargingProfile) {
		this.setChargingProfile = setChargingProfile;
	}
	
	public ClearChargingProfile getClearChargingProfile() {
		return clearChargingProfile;
	}

	public void setClearChargingProfile(ClearChargingProfile clearChargingProfile) {
		this.clearChargingProfile = clearChargingProfile;
	}

	public GetCompositeSchedule getGetCompositeSchedule() {
		return getCompositeSchedule;
	}

	public void setGetCompositeSchedule(GetCompositeSchedule getCompositeSchedule) {
		this.getCompositeSchedule = getCompositeSchedule;
	}

	public DiagnosticsStatusNotification getDiagnosticsStatusNotification() {
		return diagnosticsStatusNotification;
	}

	public void setDiagnosticsStatusNotification(DiagnosticsStatusNotification diagnosticsStatusNotification) {
		this.diagnosticsStatusNotification = diagnosticsStatusNotification;
	}

	public SignCertificate getSignCertificate() {
		return SignCertificate;
	}

	public void setSignCertificate(SignCertificate signCertificate) {
		SignCertificate = signCertificate;
	}

	public SecurityEventNotification getSecurityEventNotification() {
		return SecurityEventNotification;
	}

	public void setSecurityEventNotification(SecurityEventNotification securityEventNotification) {
		SecurityEventNotification = securityEventNotification;
	}

	public LogStatusNotification getLogStatusNotification() {
		return LogStatusNotification;
	}

	public void setLogStatusNotification(LogStatusNotification logStatusNotification) {
		LogStatusNotification = logStatusNotification;
	}

	public SignedFirmwareStatusNotification getSignedFirmwareStatusNotification() {
		return SignedFirmwareStatusNotification;
	}

	public void setSignedFirmwareStatusNotification(SignedFirmwareStatusNotification signedFirmwareStatusNotification) {
		SignedFirmwareStatusNotification = signedFirmwareStatusNotification;
	}

	public String getThirdValue() {
		return thirdValue;
	}

	public void setThirdValue(String thirdValue) {
		this.thirdValue = thirdValue;
	}

	

	
}
