package com.axxera.ocpp.webScoket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.webSocket.service.propertiesService;
@Service
public class propertiesServiceImpl implements propertiesService{

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Override
	public String getPropety(String key) {
		String recordBySql = "";
		try {
			String query = "select value from serverProperties where property = '"+key+"'";
			recordBySql = generalDao.getRecordBySql(query);
			recordBySql = recordBySql == null ? "0" : recordBySql.equalsIgnoreCase("null") ? "0" : recordBySql;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return recordBySql;
	}
	
}
