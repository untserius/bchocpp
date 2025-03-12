package com.axxera.ocpp.model.ocpp;

public class ChargingSchedulePeriod {

	private long limit;
	private long startPeriod;
	private long numberPhases;
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}

	public long getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(long startPeriod) {
		this.startPeriod = startPeriod;
	}
	public long getNumberPhases() {
		return numberPhases;
	}
	public void setNumberPhases(long numberPhases) {
		this.numberPhases = numberPhases;
	}
	@Override
	public String toString() {
		return "ChargingSchedulePeriod [limit=" + limit + ", startPeriod=" + startPeriod + ", numberPhases="
				+ numberPhases + "]";
	}
	
	
}
