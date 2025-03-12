package com.axxera.ocpp.webScoket.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.remoteStart;
import com.axxera.ocpp.model.ocpp.OCPPRemoteStartTransaction;
import com.axxera.ocpp.utils.Utils;


@Service
public class OCPPRemoteStartTransactionService {
	
	private final static Logger logger = LoggerFactory.getLogger(OCPPRemoteStartTransactionService.class);

	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private Utils utils;

	public OCPPRemoteStartTransaction getSession(String uniqueID)  {
		OCPPRemoteStartTransaction findOne = null;
		try {
			findOne = generalDao.findOne("From OCPPRemoteStartTransaction Where portalStation='" + uniqueID + "'",
					new OCPPRemoteStartTransaction());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return findOne;
	}

	public boolean updateRemoteStartTransaction(String uniqueID, String status)  {
		boolean val = false;
		try {
			OCPPRemoteStartTransaction ocppRemoteStartTransaction = getSession(uniqueID);

			if (null != ocppRemoteStartTransaction) {
				ocppRemoteStartTransaction.setStatus(status);
				generalDao.update(ocppRemoteStartTransaction);
				val = true;
			}else {
				val = false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return val;
	}

	public void addRemoteStartTransaction(remoteStart rstForm)  {
		try {
			OCPPRemoteStartTransaction ocppRemoteStartTransaction = new OCPPRemoteStartTransaction();
			ocppRemoteStartTransaction.setConnectorId(rstForm.getPortId());
			ocppRemoteStartTransaction.setIdTag(rstForm.getIdTag());
			ocppRemoteStartTransaction.setPortalStation(rstForm.getRst_unqReqId());
			ocppRemoteStartTransaction.setStatus(rstForm.isRst_Valid() ? "PENDING" : "REJECTED");
			ocppRemoteStartTransaction.setStationId(rstForm.getStationId());
			ocppRemoteStartTransaction.setUserId(rstForm.isRegisteredTxn() ? Long.valueOf(String.valueOf(rstForm.getUserObj().get("UserId"))) : 0);
			ocppRemoteStartTransaction.setCreatedDate(utils.getUTCDate());
			ocppRemoteStartTransaction.setPaymentType(rstForm.getRst_paymentType());
			ocppRemoteStartTransaction.setClientId(rstForm.getRst_rcvd_client());
			ocppRemoteStartTransaction.setRewardType(rstForm.getRst_rewardType());
			ocppRemoteStartTransaction.setSelfCharging(rstForm.isSelfCharging());
			ocppRemoteStartTransaction.setOrgId(rstForm.getStn_orgId());
			ocppRemoteStartTransaction.setPowerSharing(rstForm.isPowerSharging());
			generalDao.save(ocppRemoteStartTransaction);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addRemoteStartTransaction(long connectorId, String idTag, String status, long clientId, Long userId,
			String uniqueID, String sessionId,String paymentType)  {
		try {
			OCPPRemoteStartTransaction ocppRemoteStartTransaction = new OCPPRemoteStartTransaction();
			ocppRemoteStartTransaction.setConnectorId(connectorId);
			ocppRemoteStartTransaction.setIdTag(idTag);
			ocppRemoteStartTransaction.setPortalStation(uniqueID);
			ocppRemoteStartTransaction.setSessionId(sessionId);
			ocppRemoteStartTransaction.setStatus(status);
			ocppRemoteStartTransaction.setStationId(clientId);
			ocppRemoteStartTransaction.setUserId(userId);
			ocppRemoteStartTransaction.setCreatedDate(utils.getUTCDate());
			ocppRemoteStartTransaction.setPaymentType(paymentType);
			ocppRemoteStartTransaction.setSelfCharging(false);
			generalDao.save(ocppRemoteStartTransaction);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
