package com.axxera.ocpp.webSocket.service;

import java.util.List;
import java.util.Map;

import com.axxera.ocpp.message.SessionImportedValues;

public interface ocpiService {

	public void updateOcpiRequestStatus(String uniqueID,String status);

	public void postlastupdated(long id,boolean ocpiFlag);

}
