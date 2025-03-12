package com.axxera.ocpp.webScoket.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.OCPPUnlockConnector;


@Service
public class OCPPUnlockConnectorService {

	static Logger logger = LoggerFactory.getLogger(OCPPUnlockConnectorService.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;

	public boolean addUnlockConnector(long connectorId, String messageType, long stationId,String requestID)  {

		try {
			// TODO Auto-generated method stub
			OCPPUnlockConnector ocppUnConnector = new OCPPUnlockConnector();
			ocppUnConnector.setPortId(connectorId);
			ocppUnConnector.setMessageType(messageType);
			ocppUnConnector.setStationId(stationId);
			ocppUnConnector.setReqId(requestID);
			generalDao.save(ocppUnConnector);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;

	}

}
