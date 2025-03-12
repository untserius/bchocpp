package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.DeviceDetails;
import com.axxera.ocpp.repository.ExecuteRepository;


@Service
public class OCPPDeviceDetailsService {
	private final static Logger logger = LoggerFactory.getLogger(OCPPDeviceDetailsService.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;

	public List<DeviceDetails> getDeviceByUser(Long userId)  {
		List<DeviceDetails> findAll = null;
		try {
			findAll = generalDao.findAll("From DeviceDetails Where userId=" + userId, new DeviceDetails());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return findAll;
	}

	public List<Map<String, Object>> getDeviceDetailsByStation(long stationId,long siteId){
		List<Map<String, Object>> deviceDetails=null;
		try {
			String query="select * from currentScreen where stationId ="+stationId+" or siteId ="+siteId;
			deviceDetails=executeRepository.findAll(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return deviceDetails;
	}
}
