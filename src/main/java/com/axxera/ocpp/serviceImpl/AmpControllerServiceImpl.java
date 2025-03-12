package com.axxera.ocpp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.message.OptimizationsForm;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.service.AmpControllerService;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StationService;



@Service
public class AmpControllerServiceImpl implements AmpControllerService{

	private static final Logger logger = LoggerFactory.getLogger(AmpControllerServiceImpl.class);

	@Autowired
	private Utils utils;

	@Autowired
	private LoggerUtil customeLogger;

	@Autowired
	private StationService stationService;

	@Override
	public void sendOptimizationForEVG(OptimizationsForm optimizationData,
			Map<String, WebSocketSession> sessionswithstations) {
		Thread th = new Thread() {
			public void run() {
				List<Map<String,Object>> stnData = stationService.getStationRefNo(optimizationData.getStationId());
				logger.info("stn data : "+stnData);
				if(stnData.size() > 0) {
					logger.info("start sendOptimizationForEVG");
					String stationRefNo = String.valueOf(stnData.get(0).get("referNo")); 
					String chargerType = String.valueOf(stnData.get(0).get("chagerType")); 
					int numberphase = chargerType.equalsIgnoreCase("AC") == true?1:3;
					String uniqueId = Utils.getReferenceNumber()+":SCP";
					int chargingProfileId = 1;
					int stackLevel = 1;
					String chargingProfilePurpose = optimizationData.getChargingProfile();
					String validFrom = Utils.getUTC();
					String validTo = Utils.getUTCByOne(1);
					String chargeScheduledPeriod = "";
					String message = "";
					String utcDateTime = Utils.getUTC();
					double limit = optimizationData.getLimit();
					try {

						if (chargingProfilePurpose.equalsIgnoreCase("TxDefaultProfile")) {
							if (optimizationData.getUnit().equalsIgnoreCase("W")
									|| optimizationData.getUnit().equalsIgnoreCase("kW")) {
								if (optimizationData.getUnit().equalsIgnoreCase("kW")) {
									limit = limit * 1000;
								}
								chargingProfilePurpose = "TxDefaultProfile";
								chargeScheduledPeriod = "[{\"limit\":" + limit + ", \"startPeriod\":0 , \"numberPhases\":"+numberphase+"}]";
								message = "[2,\"" + uniqueId + "\",\"SetChargingProfile\",{\"connectorId\":"
										+ optimizationData.getConnectorId() + ",\"csChargingProfiles\":" + "{\"chargingProfileId\":"
										+ chargingProfileId + ",\"stackLevel\":" + stackLevel + ",\"chargingProfilePurpose\":\""
										+ chargingProfilePurpose + "\"," + "\"chargingProfileKind\":\"Absolute\","
										+ "\"recurrencyKind\":\"Daily\","
										+ "\"validFrom\":\"" + validFrom + "\"," + "\"validTo\":\"" + Utils.getUTCByOne(1) + "\","
										+ "\"chargingSchedule\":{\"chargingRateUnit\":\"W\",\"duration\":86400,\"startSchedule\":\""
										+ utcDateTime + "\",\"chargingSchedulePeriod\":" + chargeScheduledPeriod + "}}}]";
							} else {
								chargeScheduledPeriod = "[{\"limit\":" + limit + ", \"startPeriod\":0 , \"numberPhases\":"+numberphase+"}]";
								message = "[2,\"" + uniqueId + "\",\"SetChargingProfile\",{\"connectorId\":"
										+ optimizationData.getConnectorId() + ",\"csChargingProfiles\":" + "{\"chargingProfileId\":"
										+ chargingProfileId + ",\"stackLevel\":" + stackLevel + ",\"chargingProfilePurpose\":\""
										+ chargingProfilePurpose + "\"," + "\"chargingProfileKind\":\"Absolute\","
										+ "\"recurrencyKind\":\"Daily\","
										+ "\"validFrom\":\"" + validFrom + "\"," + "\"validTo\":\"" + Utils.getUTCByOne(1) + "\","
										+ "\"chargingSchedule\":{\"chargingRateUnit\":\"A\",\"duration\":86400,\"startSchedule\":\""
										+ utcDateTime + "\",\"chargingSchedulePeriod\":" + chargeScheduledPeriod + "}}}]";

							}
							customeLogger.info(stationRefNo, "Charging Profile message sent : " + message);
							
						} else if (chargingProfilePurpose.equalsIgnoreCase("TxProfile")) {
							List<Map<String, Object>> transactionIdList  = stationService.getFleetSessionIdByPortId(optimizationData.getPortId());
							if(transactionIdList.size()>0) {
								String transactionId = String.valueOf(transactionIdList.get(0).get("transactionId"));
								if (optimizationData.getUnit().equalsIgnoreCase("W")
										|| optimizationData.getUnit().equalsIgnoreCase("kW")) {
									if (optimizationData.getUnit().equalsIgnoreCase("kW")) {
										limit = limit * 1000;
									}
									chargingProfilePurpose = "TxProfile";
									chargeScheduledPeriod = "[{\"limit\":" + limit + ", \"startPeriod\":0 , \"numberPhases\":"+numberphase+"}]";
									message = "[2,\"" + uniqueId + "\",\"SetChargingProfile\",{\"connectorId\":"
											+ optimizationData.getConnectorId() + ",\"csChargingProfiles\":"
											+ "{\"chargingProfileId\":" + chargingProfileId + ",\"stackLevel\":" + stackLevel
											+ ",\"transactionId\":" + transactionId + ",\"chargingProfilePurpose\":\""
											+ chargingProfilePurpose + "\"," + "\"chargingProfileKind\":\"Absolute\","
											+ "\"recurrencyKind\":\"Daily\"," + "\"validFrom\":\"" + validFrom + "\","
											+ "\"validTo\":\"" + validTo + "\"," + "\"chargingSchedule\":{\"startSchedule\":\""
											+ utcDateTime + "\",\"chargingRateUnit\":\"W\",\"duration\":28800,"
											+ "\"chargingSchedulePeriod\":" + chargeScheduledPeriod + "}}}]";

								} else {
									chargeScheduledPeriod = "[{\"limit\":" + limit + ", \"startPeriod\":0 , \"numberPhases\":"+numberphase+"}]";
									message = "[2,\"" + uniqueId + "\",\"SetChargingProfile\",{\"connectorId\":"
											+ optimizationData.getConnectorId()
											+ ",\"csChargingProfiles\":{\"chargingProfileId\":" + chargingProfileId
											+ ",\"stackLevel\":" + stackLevel + ",\"transactionId\":" + transactionId
											+ ",\"chargingProfilePurpose\":\"" + chargingProfilePurpose
											+ "\",\"chargingProfileKind\":\"Absolute\"" + ",\"recurrencyKind\":\"Daily\","
											+ "\"validFrom\":\"" + validFrom + "\"," + "\"validTo\":\"" + validTo
											+ "\",\"chargingSchedule\":" + "{\"duration\": 28800,\"chargingRateUnit\":\"A\""
											+ ",\"chargingSchedulePeriod\":" + chargeScheduledPeriod + "}}}]";

								}
								customeLogger.info(stationRefNo, "Charging Profile message sent : " + message);

								customeLogger.info(stationRefNo, "optimization data: " + optimizationData);
								
								//						String networkQuery = "Select * from  network_profile where id = "
								//								+ optimizationData.getNetworkId();
								//						NetworkProfile np = generalDao.findOneBySQLQuery(networkQuery, new NetworkProfile());
								//						customeLogger.info(stationRefNo, "webhook api Profile message : " + np);
								/*String peakpower_data_query = "Select * from peakpower_data where createdTimeStamp  like '%"
										+ optimizationData.getCreatedTimeStamp() + "%' and networkId=" + np.getId() + "";
								PeakpowerData ppd = generalDao.findOneBySQLQuery(peakpower_data_query, new PeakpowerData());
								if (ppd != null) {
									ppd.setTotalOptimization(ppd.getTotalOptimization() + limit);
									ppd.setDate(com.axxera.ocpp.utils.Utils.getUTCDate());
									ppd.setPdtDate(com.axxera.ocpp.utils.Utils.getPDTDate());
									generalDao.update(ppd);
								} else {
									PeakpowerData ppdd = new PeakpowerData();
									ppdd.setCreatedTimeStamp(optimizationData.getCreatedTimeStamp());
									ppdd.setTotalOptimization(limit);
									ppdd.setNetworkId(np.getId());
									ppdd.setDate(com.axxera.ocpp.utils.Utils.getUTCDate());
									ppdd.setPdtDate(com.axxera.ocpp.utils.Utils.getPDTDate());
									generalDao.save(ppdd);
								}*/
							}
						}
						

						WebSocketSession webSocketSessionObj = sessionswithstations.get(stationRefNo);
						utils.chargerMessage(webSocketSessionObj, message, stationRefNo);
						Thread.sleep(1000);
						//}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			
			}
		};th.start();
		
		logger.info("completed : ");
	}

}
