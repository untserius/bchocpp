package com.axxera.ocpp.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.ocpiDao;
import com.axxera.ocpp.repository.ExecuteRepository;

@Service
public class ocpiDaoImpl implements ocpiDao {

	@Value("${app.partyId}")
	private String partyId;

	@Value("${app.countryCode}")
	private String ocpiCountryCode;

	@Autowired
	private ExecuteRepository executeRepository;

	@Value("${ocpi.url}")
	private String ocpiUrl;
	
	static Logger LOGGER = LoggerFactory.getLogger(ocpiDaoImpl.class);

	@Override
	public void updateocpiRequestStatus(String uniqueID, String status) {
		try {
			Thread th = new Thread() {
				public void run() {
					String sql = "update ocpi_request set status = '"+status+"' where authorizationReference = '"+uniqueID+"'";
					int updateSqlQuiries = executeRepository.update(sql);
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
