package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.OCPPChangeAvailability;


@Service
public class OCPPChangeAvailabilityService {
	static Logger logger = LoggerFactory.getLogger(OCPPChangeAvailabilityService.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;

	public void addChangeAvailabiliy(long connectorId, String uniqueID, String type, Date finalDate,long stationId)  {
		// TODO Auto-generated method stub
		try {
			OCPPChangeAvailability ocppChangeAvailability = new OCPPChangeAvailability();
			ocppChangeAvailability.setConnectorId(connectorId);
			ocppChangeAvailability.setModeType(type);
			ocppChangeAvailability.setStationId(stationId);
			ocppChangeAvailability.setStartTimeStamp(finalDate);
			ocppChangeAvailability.setPortalReqID(uniqueID);
			generalDao.save(ocppChangeAvailability);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
