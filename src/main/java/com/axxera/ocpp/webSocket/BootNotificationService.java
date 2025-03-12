package com.axxera.ocpp.webSocket;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.BootNotification;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.model.ocpp.StationConfigurationForBootNotification;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPBootNotificationService;
import com.axxera.ocpp.webSocket.service.StationService;

@Service
public class BootNotificationService {
	static Logger logger = LoggerFactory.getLogger(BootNotificationService.class);

	@Autowired
	private LoggerUtil customeLogger;
	
	@Autowired
	private StationService stationService;

	@Autowired
	private Utils utils;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;
	
	@Autowired
	private OCPPBootNotificationService bootNotificationService;
	
	@Autowired
	private ResponseService responseService;

	public void bootNotification(FinalData data, String stationRefNum, Map<String, WebSocketSession> sessions, WebSocketSession session, Long stationId,Object requestMessage) {
		String msg = "";
		String uniqueID = data.getSecondValue();
		String utcDate = Utils.getUTC();
		String status="Rejected";
		Map<String, Object> station = new HashMap<>();
		try {
			if (stationId != 0) {
				station = new HashMap<>();
				status="Accepted";
				msg = "[3,\"" + uniqueID + "\",{\"status\":\""+status+"\",\"currentTime\":\"" + utcDate + "\",\"interval\":300}]";
				utils.chargerMessage(session, msg, stationRefNum);
				customeLogger.info(stationRefNum, "Boot Notification Response send to charger :-" + msg);
				Thread.sleep(1000);
				Thread th = new Thread() {
					public void run() {
						BootNotification bootNoti = data.getBootNotification();
						bootNotificationService.bootNotification(bootNoti, uniqueID, utils.getUTCDate(), stationId);
						bootNotificationService.updateBootNotificationConfigs(bootNoti.getFirmwareVersion(), stationId);
						stationService.insertchargerActivities(stationId, stationRefNum);
						stationService.updateIntialContactTime(stationId);
						
						String cc1 = "[2,\""+utils.getRandomNumber("")+":CNF\",\"ChangeConfiguration\",{\"value\":\"\",\"key\":\"StopTxnSampledData\"}]";
						utils.chargerMessage(session, cc1, stationRefNum);
						customeLogger.info(stationRefNum, "ChangeConfiguration StopTxnSampledData sent to charger : " + cc1);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String cc2 = "[2,\""+utils.getRandomNumber("")+":CNF\",\"ChangeConfiguration\",{\"value\":\"\",\"key\":\"StopTxnAlignedData\"}]";
						customeLogger.info(stationRefNum, "ChangeConfiguration StopTxnAlignedData sent to charger : " + cc2);
						utils.chargerMessage(session, cc2, stationRefNum);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String cc3 = "[2,\""+utils.getRandomNumber("")+":CNF\",\"ChangeConfiguration\",{\"value\":\"30\",\"key\":\"MeterValueSampleInterval\"}]";
						customeLogger.info(stationRefNum, "ChangeConfiguration MeterValueSampleInterval sent to charger : " + cc3);
						utils.chargerMessage(session, cc3, stationRefNum);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String cc4 = "[2,\""+utils.getRandomNumber("")+":CNF\",\"ChangeConfiguration\",{\"value\":\"SoC,Energy.Active.Import.Register,Current.Import,Voltage,Power.Active.Import\",\"key\":\"MeterValuesSampledData\"}]";
						customeLogger.info(stationRefNum, "ChangeConfiguration MeterValuesSampledData sent to charger : " + cc4);
						utils.chargerMessage(session, cc4, stationRefNum);
						
						
						StationConfigurationForBootNotification bootFlag = bootNotificationService.getBootFlag(stationId);
						if(bootFlag != null && bootFlag.isDefaultConfig()) {
							String getConfigMessage = "[2,\""+utils.getRandomNumber("")+":DCC\",\"GetConfiguration\",{}]";
							utils.chargerMessage(session, getConfigMessage, stationRefNum);
							responseService.insertStnConfig(stationRefNum, data, "GC", "", "", session);
						}
					}
				};
				th.start();
			} else {
				msg = "[3,\"" + uniqueID + "\",{\"status\":\"Rejected\",\"currentTime\":\"" + utcDate + "\",\"interval\":300}]";
				utils.chargerMessage(session, msg, stationRefNum);
				customeLogger.info(stationRefNum, "Boot Notification rejected due to station Not exist : " + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    try {
	    	esLoggerUtil.insertStationLogs(uniqueID,"Charger","BootNotification",String.valueOf(requestMessage),stationRefNum,msg,status,stationId,0);
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
}
