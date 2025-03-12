package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.message.FinalData;
import com.axxera.ocpp.model.es.StationUpAndDownData;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.HeartbeatService;

@Service
public class HeartBeatServiceImpl implements HeartbeatService{

	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;
	
	private final static Logger logger = LoggerFactory.getLogger(EsLoggerUtil.class);
	
	@Override
	public boolean addHeartBeat(long clientId,WebSocketSession session,String stationRefNum) {
		try {
			if(clientId > 0) {
				List<Map<String, Object>> heartbeatLs = getStationIdFromHeartbeat(clientId);
				if(heartbeatLs.size()==0) {

				}else {
					executeRepository.update("update ocpp_heartBeat set reportingMailFlag='"+0+"',heartBeatTime='"+Utils.getUTC()+"',triggerMessage='"+0+"' where stationId = '"+clientId+"'");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void heartBeatInterval(FinalData finalData, String stationRefNum, Map<String, WebSocketSession> sessions, WebSocketSession session,long stationId,Object requestMessage) {
		String response ="";
		try {
			String utcDateTime = Utils.getUTC();
			String uniqueID = finalData.getSecondValue();
			response ="[3,\"" + uniqueID + "\",{\"currentTime\":\"" + utcDateTime + "\"}]";
			utils.chargerMessage(session, response,stationRefNum);
			customLogger.info(stationRefNum, "Response Message : "+response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			esLoggerUtil.insertStationLogs(finalData.getSecondValue(),"Charger","Heartbeat",String.valueOf(requestMessage),stationRefNum,response,"Accepted",stationId,0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Map<String, Object>> getStationIdFromHeartbeat(long stnId) {
		List<Map<String, Object>> mapData = new ArrayList<>();
		try {
			mapData = executeRepository.findAll("select stationId,reportingMailFlag,triggerMessage from ocpp_heartbeat where stationId = '"+stnId+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapData;
	}
	
	@Override
    public void stationActiveRecords(long stationId,String referNo) {
    	try {
    		if(stationId>0) {
    			Date utcDate=utils.getUTCDate();
    			StationUpAndDownData stationActiveData= null;
    			try {
    				stationActiveData=esLoggerUtil.getStationUpAndDownData(stationId);
    			}catch(Exception e) {
    				logger.error("",e);
    			}
    			StationUpAndDownData stationActiveData1=new StationUpAndDownData();
				stationActiveData1.setId(utils.getRandomNumber("transactionId"));
				List<IndexQuery> in = new ArrayList<>();
    			if(stationActiveData!=null && Utils.getDateFrmt(utcDate).compareTo(Utils.getDateFrmt(stationActiveData.getStartTimeStamp()))==0) {
    				String query="select ISNULL(stationTimeStamp,GETUTCDATE()) as stationTimeStamp from station where id="+stationId;
    				String stationTimeStamp=executeRepository.getRecordBySqlStr(query, "stationTimeStamp");
    				Date stationTime=utils.addMin(-15, utcDate);
    				if(stationActiveData.isActivity()) {
    					if(utils.stringToDate(stationTimeStamp).compareTo(stationTime)>0) {
    						stationActiveData.setEndTimeStamp(utcDate);
    						IndexQuery indexQuery = new IndexQueryBuilder()
									.withId(stationActiveData.getId().toString()).withObject(stationActiveData).build();
    						in.add(indexQuery);
    					}else {
    						stationActiveData1.setActivity(false);
    						stationActiveData1.setEndTimeStamp(utcDate);
    						stationActiveData1.setStartTimeStamp(stationActiveData.getEndTimeStamp());
    						stationActiveData1.setStnRefNum(referNo);
    						stationActiveData1.setStationId(stationId);
    						IndexQuery indexQuery = new IndexQueryBuilder()
									.withId(stationActiveData1.getId().toString()).withObject(stationActiveData1).build();
    						in.add(indexQuery);
    						
    						stationActiveData.setActivity(true);
    						stationActiveData.setEndTimeStamp(utcDate);
    						stationActiveData.setStartTimeStamp(utcDate);
    						stationActiveData.setStnRefNum(referNo);
    						stationActiveData.setStationId(stationId);
    						stationActiveData.setId(utils.getRandomNumber("transactionId"));
    						IndexQuery indexQuery1 = new IndexQueryBuilder()
									.withId(stationActiveData.getId().toString()).withObject(stationActiveData).build();
    						in.add(indexQuery1);
    					}
    				}else {
    					stationActiveData.setEndTimeStamp(utcDate);
						IndexQuery indexQuery = new IndexQueryBuilder()
								.withId(stationActiveData.getId().toString()).withObject(stationActiveData).build();
						in.add(indexQuery);
						
						stationActiveData1.setActivity(true);
						stationActiveData1.setEndTimeStamp(utcDate);
						stationActiveData1.setStartTimeStamp(utcDate);
						stationActiveData1.setStnRefNum(referNo);
						stationActiveData1.setStationId(stationId);
						IndexQuery indexQuery1 = new IndexQueryBuilder()
								.withId(stationActiveData1.getId().toString()).withObject(stationActiveData1).build();
						in.add(indexQuery1);
    				}
    			}else {
//    				stationActiveData1.setActivity(false);
//					stationActiveData1.setEndTimeStamp(utcDate);
//					stationActiveData1.setStartTimeStamp(Utils.getDateFrmt(utcDate));
//					stationActiveData1.setStnRefNum(referNo);
//					stationActiveData1.setStationId(stationId);
//					IndexQuery indexQuery = new IndexQueryBuilder()
//							.withId(stationActiveData1.getId().toString()).withObject(stationActiveData1).build();
//					in.add(indexQuery);
    				
    				stationActiveData =new StationUpAndDownData();
    				stationActiveData.setActivity(true);
					stationActiveData.setEndTimeStamp(utcDate);
					stationActiveData.setStartTimeStamp(utcDate);
					stationActiveData.setStnRefNum(referNo);
					stationActiveData.setStationId(stationId);
					stationActiveData.setId(utils.getRandomNumber("transactionId"));
					IndexQuery indexQuery1 = new IndexQueryBuilder()
							.withId(stationActiveData.getId().toString()).withObject(stationActiveData).build();
					in.add(indexQuery1);
    			}
    			if(in.size()>0) {
//    				System.out.println("170>>in : "+in.get(in.size()-1).getId());
//    				System.out.println("171>>in : "+in.get(in.size()-2).getId());
    				esLoggerUtil.createStationUpAndDownDataBulk(in);
    			}
    		}
    		
    	}catch (Exception e) {
    		logger.error("",e);
		}
    }
}
