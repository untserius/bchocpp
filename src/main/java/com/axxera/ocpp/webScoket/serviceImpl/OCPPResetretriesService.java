package com.axxera.ocpp.webScoket.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.OCPPResetretries;


@Service
public class OCPPResetretriesService {

	@Autowired
	private GeneralDao<?, ?> generalDao;

	public OCPPResetretries get(long clientId)  {
		// TODO Auto-generated method stub
		OCPPResetretries ocppResetretries = null;
		try {
			ocppResetretries = (OCPPResetretries) generalDao.findOne("From OCPPResetretries Where stationId =" + clientId, new OCPPResetretries());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ocppResetretries;
	}

	public void updateWithCountZero(long clientId)  {
		try {
			// TODO Auto-generated method stub

			OCPPResetretries ocppResetretries = get(clientId);
			if (null != ocppResetretries) {
				ocppResetretries.setCount(0);
				generalDao.update(ocppResetretries);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public boolean saveOrUpdate(long clientId, String status, String uniqueID, int retriesCount){
		boolean val = false;
		try {

			// TODO Auto-generated method stub
			OCPPResetretries ocppResetretries = get(clientId);
			if (null != ocppResetretries) {
				ocppResetretries.setCount(retriesCount);
				ocppResetretries.setMessageType(uniqueID);
				ocppResetretries.setStatus(status);
				generalDao.update(ocppResetretries);
				val = true;
			} else {
				OCPPResetretries ocppResetretriesNew = new OCPPResetretries();
				ocppResetretriesNew.setCount(retriesCount);
				ocppResetretriesNew.setMessageType(uniqueID);
				ocppResetretriesNew.setStationId(clientId);
				ocppResetretriesNew.setStatus(status);
				generalDao.save(ocppResetretriesNew);
				val = true;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return val;
	}

	public boolean update(String uniqueID, String status, int retriesCount, long clientId)  {
		boolean val = false;
		try {
			OCPPResetretries ocppResetretries = get(clientId);
			if (null != ocppResetretries) {
				ocppResetretries.setCount(retriesCount);
				ocppResetretries.setMessageType(uniqueID);
				ocppResetretries.setStatus(status);
				generalDao.update(ocppResetretries);
				val = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

}
