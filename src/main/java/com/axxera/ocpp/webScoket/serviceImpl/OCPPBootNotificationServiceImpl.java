package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.message.BootNotification;
import com.axxera.ocpp.model.ocpp.OCPPBootNotification;
import com.axxera.ocpp.model.ocpp.StationConfigurationForBootNotification;
import com.axxera.ocpp.repository.ExecuteRepository;

@Service
public class OCPPBootNotificationServiceImpl implements OCPPBootNotificationService{
	
	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	

	public boolean bootNotification(BootNotification bootNoti, String requestId, Date utcTime, long stationId){
		try {
			OCPPBootNotification ocppBootObj = generalDao.findOne("From OCPPBootNotification where stationId="+stationId, new OCPPBootNotification());
			if(ocppBootObj==null)
			ocppBootObj = new OCPPBootNotification();
			ocppBootObj.setChargeBoxSerialNumber(bootNoti.getChargeBoxSerialNumber());
			ocppBootObj.setChargePointSerialNumber(bootNoti.getChargePointSerialNumber());
			ocppBootObj.setChargePointModel(bootNoti.getChargePointModel());
			ocppBootObj.setChargePointVendor(bootNoti.getChargePointVendor());
			ocppBootObj.setFirmwareVersion(bootNoti.getFirmwareVersion());
			ocppBootObj.setRequestId(requestId);
			ocppBootObj.setStationId(stationId);
			ocppBootObj.setBootTime(utcTime);
			generalDao.saveOrupdate(ocppBootObj);
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void updateBootNotificationConfigs(String firmwareVer,long stnId) {
		try {
			String update = "update stationConfigForBootNotf set firmwareVersion = '"+firmwareVer+"' where stationId =" + stnId + "";
			executeRepository.update(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public StationConfigurationForBootNotification getBootFlag(long stnId) {
		StationConfigurationForBootNotification ocppBootObj = null;
		try {
			ocppBootObj = generalDao.findOne("From StationConfigurationForBootNotification where stationId="+stnId, new StationConfigurationForBootNotification());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ocppBootObj;
	}

}
