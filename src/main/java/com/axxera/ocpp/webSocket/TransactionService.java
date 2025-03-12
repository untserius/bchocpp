
package com.axxera.ocpp.webSocket;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StationService;

@Service
public class TransactionService {
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	GeneralDao<?, ?> generalDao;

	@Value("${revenue.Alert}")
	protected double revenueAlert;
	
	@Autowired
	private Utils Utils;
	
	@Autowired
	private StationService stationService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
	
	public void stopTransaction(FinalData finalData,String stationRefNum,Map<String,WebSocketSession> sessions,WebSocketSession session,Object requestMessage,long stnId)  {
		
		try {
			boolean multipleReq=stationService.getServerHitFromChargerActivities(finalData,stnId,"StopTransaction");
			if(multipleReq) {
				Utils.transactionCalling(String.valueOf(requestMessage),stationRefNum,"StopTransaction",stnId);
			}else {
				customLogger.info(stationRefNum, "No active transaction with transactionId : "+finalData.getStopTransaction().getTransactionId());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(3000);
			String response="[3,\"" + finalData.getSecondValue()+ "\",{\"idTagInfo\":{\"status\":\"Accepted\"}}]";
			Utils.chargerMessage(session, response,stationRefNum);
			customLogger.info(stationRefNum, "Response Message : "+response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
}
