package com.axxera.ocpp.model.ocpp;

import java.util.ArrayList;
import java.util.List;

public class ChargingSchedule {
	private long duration;
	private String chargingRateUnit;
	private String startSchedule;
	private Object chargingScheduleObject;
	private List<ChargingSchedulePeriod> chargingSchedulePeriod = new ArrayList<ChargingSchedulePeriod>();
	

	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public String getChargingRateUnit() {
		return chargingRateUnit;
	}
	public void setChargingRateUnit(String chargingRateUnit) {
		this.chargingRateUnit = chargingRateUnit;
	}
	
	public List<ChargingSchedulePeriod> getChargingSchedulePeriod() {
		return chargingSchedulePeriod;
	}
	public void setChargingSchedulePeriod(List<ChargingSchedulePeriod> chargingSchedulePeriod) {
		this.chargingSchedulePeriod = chargingSchedulePeriod;
	}
	
	
	public String getStartSchedule() {
		return startSchedule;
	}
	public void setStartSchedule(String startSchedule) {
		this.startSchedule = startSchedule;
	}
	
	
	public Object getChargingScheduleObject() {
		return chargingScheduleObject;
	}
	public void setChargingScheduleObject(Object chargingScheduleObject) {
		this.chargingScheduleObject = chargingScheduleObject;
	}
	@Override
	public String toString() {
		return "ChargingSchedule [duration=" + duration + ", chargingRateUnit=" + chargingRateUnit + ", startSchedule="
				+ startSchedule + ", chargingScheduleObject=" + chargingScheduleObject + ", chargingSchedulePeriod="
				+ chargingSchedulePeriod + "]";
	}

}
