package com.axxera.ocpp.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.axxera.ocpp.handler.WebSocketHandler;
import com.axxera.ocpp.utils.Utils;

@Component
public class scheduledTasks {
	private final static Logger LOGGER = LoggerFactory.getLogger(scheduledTasks.class);
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private WebSocketHandler handlerMessage;
	
	@Scheduled(cron = "0 0/15 * * * ?")
	public void scheduler() throws ParseException {
		Date utcDate = utils.getUTCDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		String utctime = sdf.format(utcDate);
		LOGGER.info("called at : "+utctime);
		handlerMessage.sessionRemoval();
	}

}
