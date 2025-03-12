package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.remoteStart;
import com.axxera.ocpp.model.ocpp.OCPITransactionData;
import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;
import com.axxera.ocpp.model.ocpp.OCPPTransactionData;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.rest.message.ResponseMessage;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.OCPPActiveTransactionsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OCPPActiveTransactionsServiceImpl implements OCPPActiveTransactionsService {
	private final static Logger logger = LoggerFactory.getLogger(OCPPActiveTransactionsServiceImpl.class);
	
	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private Utils utils;
	
	@Value("${mobileAuthKey}")
	private String mobileAuthKey;
	
	@Autowired
	private OCPPMeterValueServiceImpl ocppMeterValueService;
	
	 private RestTemplate restTemplate=new RestTemplate();
	
	@Value("${transaction.url}")
	private String transactionURL;
	
	@Value("${ocpi.url}")
	private String ocpiUrl;
	
	public  void deleteActiveTransactionAndNotifications(long stationId, long connectorId)  {
		try {
			
			String deleteActiveTransactionNotifications="delete from ocpp_activeTransaction where stationId="+stationId+" and connectorId="+connectorId+" ; "
					+ " delete from notify_me where stationid="+stationId+";";

			executeRepository.update(deleteActiveTransactionNotifications);
			
			String deleteActiveTransaction="delete from ocpp_activeTransaction where stationId="+stationId+" and messageType='Authorize' ; ";
			executeRepository.update(deleteActiveTransaction);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OCPPActiveTransaction getTrancation(long clientId, long connectorId)  {
		try {
			return generalDao.findOneBySQLQuery("select * from ocpp_activeTransaction Where stationId=" + clientId + " AND connectorId=" + connectorId +" order by id desc",
					new OCPPActiveTransaction());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public  boolean updateActiveTrnsactions(long clientId, String value, long connectorId,String updateType)  {
		try {
			OCPPActiveTransaction ocppActvTrans = getTrancation(clientId, connectorId);
			if (null != ocppActvTrans) {
				if(updateType.equalsIgnoreCase("status")) {
					ocppActvTrans.setStatus(value);
				}
				generalDao.update(ocppActvTrans);
				return true;
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void insertIntoActiveTransactions(remoteStart rst)  {
		OCPPActiveTransaction activeTransactionObj = null;
		try{
			activeTransactionObj = generalDao.findOne("From OCPPActiveTransaction where connectorId=" + rst.getPortId() + " and " + "stationId ="
					+ rst.getStationId() + "and messageType='RemoteStartTransaction' and userId =" + Long.valueOf(String.valueOf(rst.getUserObj() == null ? 0 : rst.getUserObj().get("UserId"))) + " and Status in ('Accepted','Preparing','Charging') order by id desc", new OCPPActiveTransaction());
			
			if(activeTransactionObj==null) {
				OCPPActiveTransaction ocppActiveTransaction = new OCPPActiveTransaction();
				ocppActiveTransaction.setConnectorId(rst.getPortId());
				ocppActiveTransaction.setMessageType("RemoteStartTransaction");
				ocppActiveTransaction.setRfId(rst.getIdTag());
				ocppActiveTransaction.setStationId(rst.getStationId());
				ocppActiveTransaction.setStatus("Preparing");
				ocppActiveTransaction.setUserId(Long.valueOf(String.valueOf(rst.getUserObj() == null ? 0 : rst.getUserObj().get("UserId"))));
				ocppActiveTransaction.setRequestedID(rst.getRst_unqReqId());
				ocppActiveTransaction.setOrgId(rst.getStn_orgId());
				ocppActiveTransaction.setTimeStamp(utils.getUTCDate());
				generalDao.save(ocppActiveTransaction);
			}else {
				activeTransactionObj.setRfId(rst.getIdTag());
				activeTransactionObj.setUserId(Long.valueOf(String.valueOf(rst.getUserObj() == null ? 0 : rst.getUserObj().get("UserId"))));
				activeTransactionObj.setTransactionId(0);
				activeTransactionObj.setTimeStamp(utils.getUTCDate());
				generalDao.update(activeTransactionObj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatingInCompletedTrasactions(String stnRefNum,long stationId,long portId) {
		try {
			String hqlQuery="select connectorId,messageType,rfid,sessionId,stationId,status,transactionId,userId,orgId,requestedID,timeStamp from ocpp_activeTransaction where connectorId='"+portId+"'";
			logger.info(stnRefNum+" statusNotiAvail query for list of activetransactions which are in session : "+hqlQuery);
			List<Map<String,Object>> listOfActiveListData = executeRepository.findAll(hqlQuery);
			logger.info(stnRefNum+" statusNotiAvail list of activetransactions which are in session : "+listOfActiveListData);
			for (Map<String,Object> activeSession : listOfActiveListData) {
				logger.info("sessionIds from ocppActiveTransactions : "+String.valueOf(activeSession.get("sessionId")));
				boolean activeAndSessionForChargingActivityData = ocppMeterValueService.getActiveAndSessionForChargingActivityData(String.valueOf(activeSession.get("sessionId")));
				if(activeAndSessionForChargingActivityData) {
					try{
						String insert = "insert into ActiveAndSessionForChargingActivityData (sessionId,RequestedID,connectorId,messageType,"
								+ " orgId,rfId,stationId,status,transactionId,userId,timeStamp) values"
								+ " ('"+String.valueOf(activeSession.get("sessionId"))+"','"+String.valueOf(activeSession.get("requestedID"))+"','"+String.valueOf(activeSession.get("connectorId"))+"','"+String.valueOf(activeSession.get("messageType"))+"',"
								+ "	  '"+String.valueOf(activeSession.get("orgId"))+"','"+String.valueOf(activeSession.get("rfid"))+"','"+String.valueOf(activeSession.get("stationId"))+"',"
								+ "	  '"+String.valueOf(activeSession.get("status"))+"','"+String.valueOf(activeSession.get("transactionId"))+"','"+String.valueOf(activeSession.get("userId"))+"','"+String.valueOf(activeSession.get("timeStamp"))+"')";
						executeRepository.update(insert);
					}catch (Exception e) {
						// TODO: handle exception
					}
				}else {
					logger.info("activeAndSessionForChargingActivityData : "+activeAndSessionForChargingActivityData);
				}
			}
			ocppMeterValueService.updateSessionsData(portId);
			listOfActiveListData.clear();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(stnRefNum+" exception at updatingInCompletedTrasactions method / exception name : "+e);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void idleChargeBilling(Long stationUniId, long portUniId,Date statsTime) {
		try {
			OCPPTransactionData txnData = generalDao.findOne("FROM OCPPTransactionData where portId='"+portUniId+"' and stop=0 and idleStatus!='Available' order by id desc", new OCPPTransactionData());
			if(txnData!=null) {
		    	String query="update ocpp_TransactionData set idleStatus='Available' where sessionId='"+txnData.getSessionId()+"' ";
				executeRepository.update(query);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			OCPPTransactionData txnData = generalDao.findOne("FROM OCPPTransactionData where portId='"+portUniId+"' and stop=1  order by id desc", new OCPPTransactionData());
			if(txnData != null){
				JsonNode tariff = objectMapper.readTree(txnData.getTariff_prices());
				if(tariff.size() > 0) {
					JsonNode prices = objectMapper.readTree(String.valueOf(tariff.get(0).get("cost_info")));
					if(prices.size() > 0) {
						JsonNode aditional = objectMapper.readTree(String.valueOf(prices.get(0).get("aditional")));
						if(aditional.size() > 0) {
							JsonNode idleCharge = objectMapper.readTree(String.valueOf(aditional.get("idleCharge")));
							logger.info("idleCharge : "+idleCharge);
							if(idleCharge.size() > 0) {
								if(txnData.getUserType().equalsIgnoreCase("OCPI")) {
									try {
										String url= ocpiUrl + "ocpi/ocpp/idlebilling";
										
										HashedMap requestBody = new HashedMap();
										HttpHeaders headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										requestBody.put("sessionId", txnData.getSessionId());
										requestBody.put("time", utils.stringToDate(statsTime));
										String json = new ObjectMapper().writeValueAsString(requestBody);
										HttpEntity<String> entity = new HttpEntity<>(json, headers);
										ResponseEntity<ResponseMessage> postForEntity = restTemplate.postForEntity(url, entity, ResponseMessage.class);
										logger.info("response Body : "+postForEntity.getBody());
									}catch(Exception e) {
										e.printStackTrace();
									}
									String query1="delete from ocpi_TransactionData where sessionId='"+txnData.getSessionId()+"' ";
									executeRepository.update(query1);
								}else {
									try {
										String url= transactionURL + "transaction/idlebilling";
										
										HashedMap requestBody = new HashedMap();
										HttpHeaders headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										requestBody.put("sessionId", txnData.getSessionId());
										requestBody.put("time", utils.stringToDate(statsTime));
										String json = new ObjectMapper().writeValueAsString(requestBody);
										HttpEntity<String> entity = new HttpEntity<>(json, headers);
										ResponseEntity<ResponseMessage> postForEntity = restTemplate.postForEntity(url, entity, ResponseMessage.class);
										logger.info("response Body : "+postForEntity.getBody());
									}catch(Exception e) {
										e.printStackTrace();
									}	
								}
							}else {
								logger.info("Idle charge billing is not selected for this session : "+txnData.getSessionId());
							}
							String query="delete from ocpp_TransactionData where sessionId='"+txnData.getSessionId()+"' ";
							executeRepository.update(query);
							
							String deleteActiveTrans = "delete from ocpp_activeTransaction where stationid='" + stationUniId + "' and connectorId = '" + portUniId + "'";
							logger.info("deleteSessionCharging_activeTransactions : " + deleteActiveTrans);
							executeRepository.update(deleteActiveTrans);
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void idleChargeBillingForOCPI(Long stationUniId, long portUniId,Date statsTime) {
		try {
			OCPITransactionData txnData = generalDao.findOne("FROM OCPITransactionData where portId='"+portUniId+"' and stop=0 and idleStatus!='Available' order by id desc", new OCPITransactionData());
			if(txnData!=null) {
		    	String query="update ocpi_TransactionData set idleStatus='Available' where sessionId='"+txnData.getSessionId()+"' ";
				executeRepository.update(query);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			OCPITransactionData txnData = generalDao.findOne("FROM OCPITransactionData where portId='"+portUniId+"' and stop=1  order by id desc", new OCPITransactionData());
			if(txnData != null){
				JsonNode tariff = objectMapper.readTree(txnData.getTariff_prices());
				if(tariff.size() > 0) {
					JsonNode prices = objectMapper.readTree(String.valueOf(tariff.get(0).get("cost_info")));
					if(prices.size() > 0) {
						JsonNode aditional = objectMapper.readTree(String.valueOf(prices.get(0).get("aditional")));
						if(aditional.size() > 0) {
							JsonNode idleCharge = objectMapper.readTree(String.valueOf(aditional.get("idleCharge")));
							logger.info("idleCharge : "+idleCharge);
							if(idleCharge.size() > 0) {
								if(txnData.getUserType().equalsIgnoreCase("OCPI")) {
									try {
										String url= ocpiUrl + "ocpi/ocpp/idlebilling";
										
										HashedMap requestBody = new HashedMap();
										HttpHeaders headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										requestBody.put("sessionId", txnData.getSessionId());
										requestBody.put("time", utils.stringToDate(statsTime));
										String json = new ObjectMapper().writeValueAsString(requestBody);
										HttpEntity<String> entity = new HttpEntity<>(json, headers);
										ResponseEntity<ResponseMessage> postForEntity = restTemplate.postForEntity(url, entity, ResponseMessage.class);
										logger.info("response Body : "+postForEntity.getBody());
									}catch(Exception e) {
										e.printStackTrace();
									}
								}
							}else {
								logger.info("Idle charge billing is not selected for this session : "+txnData.getSessionId());
							}
							String query="delete from ocpi_TransactionData where sessionId='"+txnData.getSessionId()+"' ";
							executeRepository.update(query);
							
							String deleteActiveTrans = "delete from ocpp_activeTransaction where stationid='" + stationUniId + "' and connectorId = '" + portUniId + "'";
							logger.info("deleteSessionCharging_activeTransactions : " + deleteActiveTrans);
							executeRepository.update(deleteActiveTrans);
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void faultedIdleBilling(Long stationUniId, long portUniId,String status,Date statsTime) {
		try {
			OCPPTransactionData txnData = generalDao.findOne("FROM OCPPTransactionData where portId='"+portUniId+"' and idleStatus!='"+status+"' order by id desc", new OCPPTransactionData());
			if(txnData!=null) {
				if(txnData.isStop()) {
					if(status.equalsIgnoreCase("Charging")) {
						if(txnData.getIdleStatus().equalsIgnoreCase("Finishing")) {
							String query="update ocpp_TransactionData set idleStatus='"+status+"' where sessionId='"+txnData.getSessionId()+"' ";
							executeRepository.update(query);
							idleChargeBilling(stationUniId,portUniId,statsTime);
						}
					}else {
						String query="update ocpp_TransactionData set idleStatus='"+status+"' where sessionId='"+txnData.getSessionId()+"' ";
						executeRepository.update(query);
						if(!status.equalsIgnoreCase("Finishing")) {
							idleChargeBilling(stationUniId,portUniId,statsTime);
						}
					}
				}else {
					if(!status.equalsIgnoreCase("Finishing") && !status.equalsIgnoreCase("Charging")) {
						String query="update ocpp_TransactionData set idleStatus='Available' where sessionId='"+txnData.getSessionId()+"' ";
						executeRepository.update(query);
					}
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			OCPITransactionData txnData = generalDao.findOne("FROM OCPITransactionData where portId='"+portUniId+"' and idleStatus!='"+status+"' and idleStatus!='Available' order by id desc", new OCPITransactionData());
			if(txnData!=null) {
				if(txnData.isStop()) {
					if(status.equalsIgnoreCase("Charging")) {
						if(txnData.getIdleStatus().equalsIgnoreCase("Finishing")) {
							String query="update ocpi_TransactionData set idleStatus='"+status+"' where sessionId='"+txnData.getSessionId()+"' ";
							executeRepository.update(query);
							idleChargeBillingForOCPI(stationUniId, portUniId, statsTime);
						}
					}else {
						String query="update ocpi_TransactionData set idleStatus='"+status+"' where sessionId='"+txnData.getSessionId()+"' ";
						executeRepository.update(query);
						if(!status.equalsIgnoreCase("Finishing")) {
						  idleChargeBillingForOCPI(stationUniId, portUniId, statsTime);
						}
					}
				}else {
					if(!status.equalsIgnoreCase("Finishing") && !status.equalsIgnoreCase("Charging")) {
						String query="update ocpi_TransactionData set idleStatus='Available' where sessionId='"+txnData.getSessionId()+"' ";
						executeRepository.update(query);
					}
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
