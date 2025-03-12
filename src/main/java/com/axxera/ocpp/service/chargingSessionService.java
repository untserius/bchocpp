package com.axxera.ocpp.service;

import java.util.Map;

import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.model.ocpp.OCPPActiveTransaction;
import com.axxera.ocpp.model.ocpp.OCPPTransactionData;
import com.axxera.ocpp.model.ocpp.Session;

public interface chargingSessionService {

	startTxn startTxnUserValidator(startTxn sTxn);

	startTxn startTxnStationValidator(startTxn sTxn);

	startTxn insertIntoSession(startTxn sTxn);

	OCPPTransactionData insertIntoTransactionData(startTxn sTxn);

	void insertIntoSessionPricings(startTxn sTxn);

	void deleteActiveSessionData(long portId);

	void updateChargingSessionData(Long valueOf);

	startTxn insertActiveTxnStartTxn(startTxn sTxn);

	boolean insertStartTransaction(startTxn sTxn);

	startTxn siteData(startTxn sTxn);

	void updatePAYGSessionId(startTxn sTxn);

	startTxn getRSTTxnData(startTxn sTxn);
	
	String ocpiStartCall(String requestMessage,String stnRefNum);

	long timeZone(String timeZone);

	void deleteTxnData(long portId);

	startTxn siteTimeCheck(startTxn sTxn);

	OCPPActiveTransaction getActiveTxnData(long stnId, long portId, String idTag);

}
