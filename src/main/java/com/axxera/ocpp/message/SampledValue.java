package com.axxera.ocpp.message;

public class SampledValue {

	private String context;

	private String format;
	private String location;
	private String measurand;
	private String phase;
	private String unit;

	private String value;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMeasurand() {
		return measurand;
	}

	public void setMeasurand(String measurand) {
		this.measurand = measurand;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SampledValue [context=" + context + ", format=" + format + ", location=" + location + ", measurand="
				+ measurand + ", phase=" + phase + ", unit=" + unit + ", value=" + value + "]";
	}

}