package com.axxera.ocpp.webSocket.service;

import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.forms.remoteStart;

public interface remoteStartService {

	remoteStart remotStartBalanceValidate(remoteStart rst);

	remoteStart getRemoteStartUserValidate(remoteStart rst);

	remoteStart getRemoteStartStnValidate(remoteStart rst);

}
