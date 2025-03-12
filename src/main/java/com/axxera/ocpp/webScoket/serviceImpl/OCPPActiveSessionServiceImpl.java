package com.axxera.ocpp.webScoket.serviceImpl;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.config.EmailServiceImpl;
import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.OCPPActiveSession;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.OCPPActiveSessionService;
import com.axxera.ocpp.webSocket.service.StationService;

@Service
public class OCPPActiveSessionServiceImpl implements OCPPActiveSessionService {
	static Logger logger = LoggerFactory.getLogger(OCPPActiveSessionServiceImpl.class);

	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private LoggerUtil customLogger;
	
	@Value("${http.port}")
	private int httpPort;
	
	@Override
	public boolean insertActiceSession(String sessionId, String status, String stationId,String uri)  {
		try {
			Thread th = new Thread() {
				public void run() {
					try {
						InetAddress localHost = InetAddress.getLocalHost();
			            String ip = localHost.getHostAddress();
			            
			            String ipAddress = "http://"+ip + ":"+ String.valueOf(httpPort)+"/";
						String update = "update ocpp_activeSession set sessionId = '" + sessionId + "' , sessionStatus = '" + status + "',uri='"+uri+"',ocppServer='"+ipAddress+"' where stationRefNum = '" + stationId + "'";
						int updateSqlQuiries = executeRepository.update(update);
						if (updateSqlQuiries == 0) {
							long maxCount=Long.parseLong(executeRepository.getRecordBySqlStr("select value from serverProperties where property='onclose'","value"));
							String query="insert into ocpp_activeSession (sessionId,stationRefNum,sessionStatus,URI,disconnectCount,lastUpdatedTime,ocppServer,oncloseCount) values ('"+sessionId+"','"+stationId+"','"+status+"','"+uri+"','"+0+"','"+Utils.getUTCDateString()+"','"+ipAddress+"','"+maxCount+"')";
							int update2 = executeRepository.update(query);
						}
						
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			th.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	
	@Override
	public boolean deleteActiveSession(String stationRefNum,String uri)  {
		try {
			Thread th = new Thread() {
				public void run() {
					long stationUniqId = stationService.getStationUniqId(stationRefNum);
					if(stationUniqId > 0) {
						String queryForDeleteTransaction="update ocpp_activeSession set sessionStatus = 'CLOSED', oncloseCount=isNull(oncloseCount,0)+"+1+",URI='"+uri+"',lastUpdatedTime='"+Utils.getUTCDateString()+"',disconnectCount= case when DATEDIFF(MINUTE,ISNULL(lastUpdatedTime,DATEADD(mm,-31,GETUTCDATE())),GETUTCDATE()) > 30 then 1 else disconnectCount+1 END where stationRefNum='"+stationRefNum+"'";
						executeRepository.update(queryForDeleteTransaction);
					}
				}
			};
			
			th.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void updateActiveTransaction(String stationRefNum) {
		try {
			String query="update ocpp_TransactionData set idleStatus='Available' where stop=1 and portId in (select p.id from port p inner join station s on p.station_id=s.id where referNo='"+stationRefNum+"')";
			executeRepository.update(query);
			
			
			String query1="update ocpi_TransactionData set idleStatus='Available' where stop=1 and portId in (select p.id from port p inner join station s on p.station_id=s.id where referNo='"+stationRefNum+"')";
			executeRepository.update(query1);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
