package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;

import com.axxera.ocpp.message.BootNotification;
import com.axxera.ocpp.model.ocpp.StationConfigurationForBootNotification;

public interface OCPPBootNotificationService {

	public boolean bootNotification(BootNotification bootNoti, String requestId, Date utcTime, long stationId);
	
	public void updateBootNotificationConfigs(String firmwareVer,long stnId) ;

	StationConfigurationForBootNotification getBootFlag(long stnId);
	
}
