package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.OCPPReset;


@Service
public class OCPPResetService {

	@Autowired
	private  GeneralDao<?, ?> generalDao;

	public  void addReset(String reqId, String resetType, Date date)  {
		try {
			// TODO Auto-generated method stub
			
			OCPPReset reset = new OCPPReset();
			reset.setRequestID(reqId);
			reset.setResetDate(date);
			reset.setStatus("");
			reset.setResetType(resetType);
			generalDao.save(reset);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public OCPPReset getReset(String uniqueID)  {
		OCPPReset findOne = null;
		try {
			// TODO Auto-generated method stub
			findOne = generalDao.findOne("From OCPPReset Where requestID='" + uniqueID + "' order by id desc ", new OCPPReset());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return findOne;
	}

	public void updateResetStatus(String uniqueID, String status)  {
		try {
			OCPPReset reset = getReset(uniqueID);
			if(reset!=null) {
				reset.setStatus(status);
				generalDao.update(reset);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
