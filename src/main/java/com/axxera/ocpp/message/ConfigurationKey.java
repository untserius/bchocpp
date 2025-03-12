package com.axxera.ocpp.message;

public class ConfigurationKey {

	private Boolean readonly;

	private String value;

	private String key;

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "ConfigurationKey [readonly=" + readonly + ", value=" + value + ", key=" + key + "]";
	}

}
