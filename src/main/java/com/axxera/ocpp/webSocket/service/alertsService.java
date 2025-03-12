package com.axxera.ocpp.webSocket.service;

import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.model.ocpp.PreferredNotification;

public interface alertsService {

	PreferredNotification preferredNotify(long userId);

	void smsPAYGAuthorize(startTxn sTxn);

}
