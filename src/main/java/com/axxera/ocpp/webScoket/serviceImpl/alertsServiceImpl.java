package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.config.EmailServiceImpl;
import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.forms.startTxn;
import com.axxera.ocpp.model.ocpp.PreferredNotification;
import com.axxera.ocpp.utils.PushNotification;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.utils.smsIntegration;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.alertsService;
import com.axxera.ocpp.webSocket.service.sessionDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class alertsServiceImpl implements alertsService{
	private final static Logger logger = LoggerFactory.getLogger(alertsServiceImpl.class);
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private smsIntegration smsIntegrationImpl;
	
	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Override
	public PreferredNotification preferredNotify(long userId) {
		PreferredNotification pn = null;
		try {
			pn = generalDao.findOne("FROM PreferredNotification where userId="+userId, new PreferredNotification());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pn;
	}
	
	@Override
	public void smsPAYGAuthorize(startTxn sTxn) {
		try {
			if(sTxn.isPayAsUGoTxn() && Double.valueOf(String.valueOf(sTxn.getUserObj().get("authorizeAmount")).equalsIgnoreCase("null") ? "0" : String.valueOf(sTxn.getUserObj().get("authorizeAmount"))) > 0) {
				Map<String, Object> userObj = sTxn.getUserObj();
				smsIntegrationImpl.sendSMSUsingBootConfg(sTxn.getStnRefNum(),String.valueOf(sTxn.getChargeSessUniqId()),"PAYGAuth",Double.parseDouble(String.valueOf(userObj.get("authorizeAmount"))),sTxn.getIdTag());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
