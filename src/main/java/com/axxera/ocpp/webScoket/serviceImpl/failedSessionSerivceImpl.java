package com.axxera.ocpp.webScoket.serviceImpl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.remoteStart;
import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.model.ocpp.ocppFailedSessions;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.failedSessionService;

@Service
public class failedSessionSerivceImpl implements failedSessionService {
	
	static Logger logger = LoggerFactory.getLogger(failedSessionSerivceImpl.class);
	
	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private Utils utils;
	
	@Override
	public void insertIntoFailedSessionsRST(remoteStart rst) {
		try {
			if(!rst.isRst_Valid()) {
				ocppFailedSessions ofs = generalDao.findOneBySQLQuery("select * from ocppFailedChargingSessions where idTag='"+rst.getIdTag()+"' "
						+ " and stationId='"+rst.getStationId()+"' and portId = '"+rst.getPortId()+"' and creationTime >= DATEADD(SECOND,-60,GETUTCDATE()) "
						+ " order by id desc;", new ocppFailedSessions());
				if(ofs == null) {
					ofs = new ocppFailedSessions();	
					ofs.setCreationTime(utils.getUTCDate());
				}
				ofs.setSessionId(0);
				ofs.setStage("RemoteStartTransaction");
				ofs.setIdTag(rst.getIdTag());
				ofs.setStationId(rst.getStationId());
				ofs.setPortId(rst.getPortId());
				ofs.setReason(rst.getRst_reason());
				ofs.setStnRefNum(rst.getStn_referNo());
				ofs.setConnectorId((int) rst.getConnectorId());
				ofs.setUserId(Long.valueOf(String.valueOf(rst.getUserObj() != null ? rst.getUserObj().get("UserId") : 0)));
				ofs.setCreationTime(utils.getUTCDate());
				ofs.setEmail(rst.isRegisteredTxn() ? String.valueOf(rst.getUserObj().get("email")) : "unknown user");
				ofs.setRequestId(rst.getRst_unqReqId());
				ofs.setFlag(rst.isRst_Valid() ? false : true);
				generalDao.saveOrupdate(ofs);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertIntoFailedSessionsAuth(long sessionId,String idTag,long stnId,long portId,String reason,String stnRefNum,int connectorId,
			long userId,String email,String status) {
		try {
			if(!status.equalsIgnoreCase("Accepted")) {
				String query = "select * from ocppFailedChargingSessions where idTag='"+idTag+"' and stnRefNum='"+stnRefNum+"' and creationTime >= "
						+ " DATEADD(SECOND,-60,GETUTCDATE()) order by id desc;";
				ocppFailedSessions ofs = generalDao.findOneBySQLQuery(query, new ocppFailedSessions());
				if(ofs == null) {
					ofs = new ocppFailedSessions();	
					ofs.setCreationTime(utils.getUTCDate());
				}
				ofs.setStage("Authorize");
				ofs.setSessionId(sessionId);
				ofs.setIdTag(idTag);
				ofs.setStationId(stnId);
				ofs.setPortId(portId);
				ofs.setReason(reason);
				ofs.setStnRefNum(stnRefNum);
				ofs.setConnectorId(connectorId);
				ofs.setUserId(userId);
				ofs.setEmail(email);
				ofs.setFlag(status.equalsIgnoreCase("Accepted") ? false : true);
				generalDao.saveOrupdate(ofs);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertIntoFailedSessionsStart(startTxn sTxn) {
		try {
			if(!sTxn.isOcpiTxn()) {
				ocppFailedSessions ofs = generalDao.findOneBySQLQuery("select * from ocppFailedChargingSessions where idTag='"+sTxn.getIdTag()+"' and "
						+ " stnRefNum='"+sTxn.getStnRefNum()+"' and creationTime >= DATEADD(MINUTE,-3,GETUTCDATE()) and portId="+(sTxn.getStnObj() == null ? 0 : Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId")).equalsIgnoreCase("null") ? "0" : String.valueOf(sTxn.getStnObj().get("portId"))))+" order by id desc;", 
						new ocppFailedSessions());
				if(ofs == null) {
					ofs = new ocppFailedSessions();	
					ofs.setCreationTime(utils.getUTCDate());
				}
				ofs.setStage("StartTransaction");
				ofs.setSessionId(sTxn.getSession() == null ? 0 : sTxn.getSession().getId());
				ofs.setIdTag(sTxn.getIdTag());
				ofs.setStationId(sTxn.getStnObj() == null ? 0 : Long.valueOf(String.valueOf(sTxn.getStnObj().get("stnId")).equalsIgnoreCase("null") ? "0" : String.valueOf(sTxn.getStnObj().get("stnId"))));
				ofs.setPortId(sTxn.getStnObj() == null ? 0 : Long.valueOf(String.valueOf(sTxn.getStnObj().get("portId")).equalsIgnoreCase("null") ? "0" : String.valueOf(sTxn.getStnObj().get("portId"))));
				ofs.setReason(sTxn.getReason());
				ofs.setStnRefNum(sTxn.getStnRefNum());
				ofs.setConnectorId(sTxn.getStnObj() == null ? 0 : Integer.valueOf(String.valueOf(sTxn.getStnObj().get("connector_id")).equalsIgnoreCase("null") ? "0" : String.valueOf(sTxn.getStnObj().get("connector_id"))));
				ofs.setUserId(sTxn.getUserObj() == null ? 0 : Integer.valueOf(String.valueOf(sTxn.getUserObj().get("UserId")).equalsIgnoreCase("null") ? "0" :  String.valueOf(sTxn.getUserObj().get("UserId"))));
				ofs.setEmail(sTxn.getUserObj() == null ? "Unknown" : String.valueOf(sTxn.getUserObj().get("email")));
				ofs.setFlag(sTxn.isTxnValid() ? false : true);
				generalDao.saveOrupdate(ofs);
				
				logger.info("stxn : "+sTxn.isTxnValid()+" , power sharging : "+sTxn.isPowerSharing());
				if(sTxn.isTxnValid() && !sTxn.isPowerSharing()) {
					executeRepository.update("delete from ocppFailedChargingSessions where DATEADD(MINUTE,-3,GETUTCDATE()) < creationTime and stnrefNum = '"+sTxn.getStnRefNum()+"'");
				}else if(sTxn.isTxnValid()&& sTxn.isPowerSharing()) {
					executeRepository.update("delete from ocppFailedChargingSessions where id = '"+ofs.getId()+"'");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateFailedTxnData(String requestId) {
		try {
			executeRepository.update("update ocppFailedChargingSessions set flag = '1' where requestId='"+requestId+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteFailedSessionsMeter(long sessionId,long portId,BigDecimal duration) {
		try {
			logger.info("sessionId : "+sessionId+" ,portId : "+portId+" , duration : "+duration);
			if(Double.valueOf(String.valueOf(duration)) >= 2) {
				executeRepository.update("delete from ocppFailedChargingSessions where sessionId=" + sessionId+" and portId="+portId);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
