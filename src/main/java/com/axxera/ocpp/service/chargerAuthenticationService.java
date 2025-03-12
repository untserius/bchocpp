package com.axxera.ocpp.service;

import java.util.Map;

public interface chargerAuthenticationService {

	boolean headerValidation(String clientId,String headerAuth);

}
