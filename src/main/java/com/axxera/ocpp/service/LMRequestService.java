package com.axxera.ocpp.service;

import java.util.Map;

import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.message.StartTransaction;



public interface LMRequestService {
	
	void sendingRequestsToLM(startTxn sTxn, String requestType, StartTransaction startTrans);

	Map<String, Object> getFleetDataByTransactionId(long transactionId);

	void deleteIndividualScheduleTime(long portId);

	void updateLMIndividualScheduleTime(long portId);

	Map<String, Object> individualScheduleTimePreparing(long portId);
}
