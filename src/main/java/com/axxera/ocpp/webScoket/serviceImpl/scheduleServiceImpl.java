package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.handler.WebSocketHandler;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.schedulerService;

@Service
public class scheduleServiceImpl implements schedulerService{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(scheduleServiceImpl.class);
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private WebSocketHandler handleMessage;

	@Override
	public void trigger() {
		try {
			Thread th = new Thread() {
				public void run() {
					String query = "select st.id, st.referNo,p.connector_id from station st inner join port p on st.id = p.station_id inner join statusNotification sn on p.id = sn.port_id inner join chargerActivities ca on st.id = ca.stationId  where ca.triggerMessageFlag = 1 and (sn.status = 'Removed' or sn.status = 'Charging' or sn.status = 'Planned' or sn.status = 'Inoperative')";
					List<Map<String, Object>> mapData = executeRepository.findAll(query);
					mapData.forEach(mapObj -> {
						Map<String,Object> map=new HashMap<String,Object>();
						String stationRefNum = String.valueOf(mapObj.get("referNo"));
						long stnUnqId = Long.parseLong(String.valueOf(mapObj.get("id")));
						try {
							String msg = "[2,\"" + utils.getStationRandomNumber(stnUnqId) + ":TM" + "\",\"TriggerMessage\",{\"requestedMessage\":\"StatusNotification\",\"connectorId\":"+ Long.parseLong(String.valueOf(mapObj.get("connector_id"))) + "}]";
							map.put("clientId", stationRefNum);
							map.put("message", msg);
							handleMessage.newRquest(map);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
