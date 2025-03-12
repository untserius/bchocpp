package com.axxera.ocpp.message;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.axxera.ocpp.utils.Utils;

public class MeterValue {
	
	@Autowired
	private Utils utils;

	private List<SampledValue> sampledValue = new ArrayList<SampledValue>();

	private Date  timestamp;

	public List<SampledValue> getSampledValue() {
		return sampledValue;
	}

	public void setSampledValue(List<SampledValue> sampledValue) {
		this.sampledValue = sampledValue;
	}

	public Date getTimestamp() throws ParseException {
		if(timestamp==null)
		return utils.getUTCDate();
		
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "MeterValue [sampledValue=" + sampledValue + ", timestamp=" + timestamp + "]";
	}

}