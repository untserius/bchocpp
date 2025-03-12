package com.axxera.ocpp.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.axxera.ocpp.forms.PayloadData;
import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.message.StartTransaction;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.service.LMRequestService;
import com.axxera.ocpp.utils.Utils;



@Service
public class LMRequestServiceImpl implements LMRequestService {
	
	@Value("${LOADMANAGEMENT_URL}")
	private String loadManagementURL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(LMRequestServiceImpl.class);
	
	@Override
	public void sendingRequestsToLM(startTxn sTxn, String jsonMessage, StartTransaction startTrans) {
		if(Boolean.valueOf(String.valueOf(sTxn.getStnObj().get("ampFlag")))) {
			Thread th = new Thread() {
				public void run() {
					try {
						PayloadData lmRequest = new PayloadData();
						lmRequest.setRequestType("StartTransaction");
						lmRequest.setJsonMessage(jsonMessage);
						lmRequest.setStationId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId"))));
						lmRequest.setPortId(Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId"))));
						lmRequest.setSessionId(sTxn.getSession().getSessionId());
						lmRequest.setRefSessionId(String.valueOf(sTxn.getSession().getId()));
						lmRequest.setStartTime(startTrans.getTimestampStr());
						lmRequest.setTransactionId(sTxn.getTransactionId());
						lmRequest.setIdTag(startTrans.getIdTag());
						lmRequest.setConnectorId(startTrans.getConnectorId());
						lmRequest.setSocValue(50.0);
						lmRequest.setPowerImportValue(50000.00);
						lmRequest.setPowerImportUnit("W");
						lmRequest.setPortStatus("Charging");
						lmRequest.setPowerImportAvg(0);
						lmRequest.setMeterEndTime(startTrans.getTimestampStr());
						
						CloseableHttpClient client = HttpClients.createDefault();
						String URL = loadManagementURL+"/"+"lmRequest";
						HttpHeaders headers = new HttpHeaders();
						headers.set("Content-Type", "application/json");
						HttpEntity<Object> requestEntity = new HttpEntity<>(lmRequest, headers);
						restTemplate.postForEntity(URL, requestEntity, String.class);
						logger.info(" url hitting from ocpp -> load management : " + URL);
						client.close();
						Thread.sleep(1000);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			th.start();
		}
	}

	@Override
	public Map<String,Object> getFleetDataByTransactionId(long transactionId) {
		Map<String,Object> fs1 = new HashMap<>();
		try {
			String query = "select portId, connectorId from fleet_sessions where transactionId='"+transactionId+"' and status = 'Active'";
			List<Map<String,Object>> fs = executeRepository.findAll(query);
			if(fs.size()>0) {
				fs1 = fs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fs1;
	}

	@Override
	public void deleteIndividualScheduleTime(long portId) {
		try {
			String query = "delete from individual_ScheduleTime where flag= 1 and portId = "+portId;
			executeRepository.update(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateLMIndividualScheduleTime(long portId) {
		try {
			String utcDateTime = Utils.getUTC();
			String query = "Update individual_ScheduleTime set flag = 1 where portId = " +portId+" and startTime<'" + utcDateTime + "'";
			executeRepository.update(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public Map<String,Object> individualScheduleTimePreparing(long portId) {
		Map<String,Object> hashMap = new HashMap<>();
		try {
			String scheduleTime = "select idTag from individual_ScheduleTime where startTime <= '"+Utils.getUTCDateString().replace(" ", "T")+".000Z"+"' and endTime >= GETUTCDATE() and profileId > 0 and flag = 0 and portId = "+portId;
			List<Map<String,Object>>  lsMap = executeRepository.findAll(scheduleTime);
			if(lsMap.size()>0) {
				hashMap = lsMap.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
	}
}
