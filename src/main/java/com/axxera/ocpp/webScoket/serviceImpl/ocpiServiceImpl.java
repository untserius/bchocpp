package com.axxera.ocpp.webScoket.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.ocpiDao;
import com.axxera.ocpp.webSocket.service.ocpiService;

@Service
public class ocpiServiceImpl implements ocpiService {

	@Value("${app.partyId}")
	private String partyId;

	@Value("${app.countryCode}")
	private String ocpiCountryCode;

	@Autowired
	private ocpiDao ocpiDao;

	@Value("${ocpi.url}")
	private String ocpiUrl;

	@Override
	public void updateOcpiRequestStatus(String uniqueID, String status) {
		try {
			Thread th = new Thread() {
				public void run() {
					ocpiDao.updateocpiRequestStatus(uniqueID, status);
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postlastupdated(long id, boolean ocpiFlag) {
		try {
			if (ocpiFlag) {
				Thread th = new Thread() {
					public void run() {
						try {
							String urlToRead = ocpiUrl + "ocpi/ocpp/update?id=" + id;
							StringBuilder result = new StringBuilder();
							URL url = null;
							url = new URL(urlToRead);
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							conn.setRequestMethod("GET");
							BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String line;
							while ((line = rd.readLine()) != null) {
								result.append(line);
							}
							rd.close();

						} catch (Exception e) {
							// e.printStackTrace();
						}
					}
				};
				th.start();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
