package com.axxera.ocpp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axxera.ocpp.webSocket.service.schedulerService;

@RestController
@RequestMapping(value="/schedule")
public class scheduleController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(scheduleController.class);
	
	@Autowired
	private schedulerService schedulerService;
	
	@RequestMapping(value = "/trigger", method = RequestMethod.POST)
	public void RestAPI() {
		LOGGER.info("/schedule/trigger request");
		try {
			schedulerService.trigger();
			LOGGER.info("/schedule/trigger response");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
