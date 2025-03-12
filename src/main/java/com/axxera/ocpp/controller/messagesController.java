package com.axxera.ocpp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axxera.ocpp.handler.WebSocketHandler;
import com.axxera.ocpp.message.OCPPForm;
import com.axxera.ocpp.rest.message.ResponseMessage;
import com.axxera.ocpp.webSocket.service.ocppService;


@RestController
@RequestMapping(value="/ocpp")
public class messagesController {
	
	@Autowired
	private WebSocketHandler handleMessage;
	
	@Autowired
	private ocppService ocppService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(messagesController.class);
	
	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public ResponseMessage RestAPI(@RequestBody OCPPForm ocppForm) {
		ResponseMessage response = new ResponseMessage();
		response.setApiRequestId(ocppForm.getApiRequestId());
		LOGGER.info("Request object /ocpp/request : "+ocppForm);
		try {
			response = handleMessage.features(ocppForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Response object /ocpp/request : "+response);
		return response;
	}
	@RequestMapping(value = "/ocpi/request", method = RequestMethod.POST)
	public ResponseMessage ocpiRestAPI(@RequestBody OCPPForm ocppForm) {
		ResponseMessage response = new ResponseMessage();
		response.setApiRequestId(ocppForm.getApiRequestId());
		LOGGER.info("Request object /ocpi/request : "+ocppForm);
		try {
			response = handleMessage.ocpiFeatures(ocppForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Response object /ocpi/request : "+response);
		return response;
	}
	
	@RequestMapping(value = "/v3/request", method = RequestMethod.POST)
	public ResponseMessage RestAppAPI(@RequestBody OCPPForm ocppFormApp) {
		ResponseMessage response = new ResponseMessage();
		response.setApiRequestId(ocppFormApp.getApiRequestId());
		LOGGER.info("Request object /ocpp/v3/request : "+ocppFormApp);
		try {
			response = handleMessage.appFeatures(ocppFormApp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Response object /ocpp/v3/request : "+response);
		return response;
	}
	
	@RequestMapping(value = "/commands", method = RequestMethod.POST)
	public ResponseMessage MultiReqAPI(@RequestBody OCPPForm ocppForm) {
		ResponseMessage response = new ResponseMessage();
		response.setApiRequestId(ocppForm.getApiRequestId());
		LOGGER.info("Request object /ocpp/commands : "+ocppForm);
		try {
			response = handleMessage.multiFeatures(ocppForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Response object /ocpp/commands : "+response);
		return response;
	}
	
	@RequestMapping(value = "/changeAvailability", method = RequestMethod.POST)
	public ResponseMessage changeAvailability(@RequestBody OCPPForm ocppForm) {
		ResponseMessage response = new ResponseMessage();
		response.setApiRequestId(ocppForm.getApiRequestId());
		LOGGER.info("Request object /ocpp/changeAvailability : "+ocppForm);
		try {
			response = handleMessage.changeAvailability(ocppForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Response object /ocpp/request : "+response);
		return response;
	}
	
	@RequestMapping(value = "/fcm", method = RequestMethod.POST)
	public ResponseMessage fcm(@RequestBody OCPPForm ocppForm) {
		ResponseMessage response = new ResponseMessage();
		response.setApiRequestId(ocppForm.getApiRequestId());
		LOGGER.info("Request object /ocpp/fcm : "+ocppForm);
		try {
			 if(ocppForm.getRetries()==1) {
				 ocppService.fcm(ocppForm);
			 }else {
				 ocppService.fcmStop();
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Response object /ocpp/fcm : "+response);
		return response;
	}
}
