package com.axxera.ocpp.webSocket.service;

import java.util.Date;

import com.axxera.ocpp.forms.remoteStart;
import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;



public interface OCPPActiveTransactionsService {
	
	public  OCPPActiveTransaction getTrancation(long clientId, long connectorId) ;
	
	public  boolean updateActiveTrnsactions(long clientId, String value, long connectorId,String updateType) ;
	
	public void deleteActiveTransactionAndNotifications(long stationId, long connectorId) ;

	public void updatingInCompletedTrasactions(String stnRefNum,long stationId,long portId);

	void insertIntoActiveTransactions(remoteStart rstForm);

	public void idleChargeBilling(Long stationUniId, long portUniId,Date statsTime);

	void idleChargeBillingForOCPI(Long stationUniId, long portUniId, Date statsTime);

	void faultedIdleBilling(Long stationUniId, long portUniId, String status, Date statsTime);

}
