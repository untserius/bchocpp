package com.axxera.ocpp.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axxera.ocpp.handler.WebSocketHandler;
import com.axxera.ocpp.message.OptimizationsForm;
import com.axxera.ocpp.message.ScheduleStartForm;
import com.axxera.ocpp.rest.message.ResponseMessage;

@RestController
@Scope("request")
@RequestMapping("/amp")
public class AMPController {
	
	@Autowired
	private WebSocketHandler webSocketHandler;
	
	private static final Logger logger = LoggerFactory.getLogger(AMPController.class);

	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> newrequest(@RequestBody Map<String,Object> map) {
		logger.info("/amp/request calling : "+map);
		String msg = "Received Data";
		try {
			if(map!=null && !map.isEmpty()) {
				if(String.valueOf(map.get("message")).equalsIgnoreCase("sessionRemoval")) {
					webSocketHandler.sessionRemoval();
				}else {
				    webSocketHandler.newRquest(map);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(msg));
	}
	@RequestMapping(value = "/multirequest", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> multiRequest(@RequestBody List<Map<String,String>> map) {
		logger.info("AMPController /amp/multirequest : "+map);
		String msg = "Received Data";
		try {
			webSocketHandler.multiRequestHitting(map);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AMPController /amp/multirequest response : "+msg);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(msg));
	}
	
	@RequestMapping(value = "/scheduleStart", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> scheduleChareStartTransaction(@RequestBody ScheduleStartForm scheduleStartData) {
		logger.info("AMPController /amp/scheduleStart : "+scheduleStartData);
		String msg = "Received Data";
		try {
			webSocketHandler.scheduleChareStartTransaction(scheduleStartData);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AMPController /amp/scheduleStart response : "+msg);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(msg));
	}
	
	@RequestMapping(value = "/scheduleStop", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> scheduleChareStopTransaction(@RequestBody ScheduleStartForm scheduleStartData) {
		logger.info("AMPController /amp/scheduleStop : "+scheduleStartData);
		String msg = "Received Data";
		try {
			webSocketHandler.scheduleChareStopTransaction(scheduleStartData);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AMPController /amp/scheduleStop response : "+msg);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(msg));
	}
	
	@RequestMapping(value = "/evgWebhook", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> evgOptimization(@RequestBody OptimizationsForm optimizationData) {
		String msg = "Received Data";
		logger.info("AMPController /amp/evgWebhook : "+optimizationData);
		System.err.println("62 ** optimizationData:"+ optimizationData);
		try {
			webSocketHandler.ampSessionBaseFunctionsForEVG(optimizationData);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AMPController /amp/evgWebhook response : "+msg);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(msg));
	}
}
