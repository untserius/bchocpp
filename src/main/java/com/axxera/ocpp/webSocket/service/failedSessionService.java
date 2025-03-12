package com.axxera.ocpp.webSocket.service;

import java.math.BigDecimal;

import com.axxera.ocpp.forms.remoteStart;
import com.axxera.ocpp.forms.startTxn;

public interface failedSessionService {

	void insertIntoFailedSessionsRST(remoteStart rst);

	void insertIntoFailedSessionsAuth(long sessionId, String idTag, long stnId, long portId, String reason,
			String stnRefNum, int connectorId, long userId, String email,String status);

	void insertIntoFailedSessionsStart(startTxn sTxn);

	void deleteFailedSessionsMeter(long sessionId, long portId, BigDecimal duration);

}
