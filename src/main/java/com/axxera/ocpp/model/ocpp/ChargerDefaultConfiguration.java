package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "charger_Default_Configuration")
public class ChargerDefaultConfiguration extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String keys;
	private String value;
	
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "ChargerDefaultConfiguration [keys=" + keys + ", value=" + value + "]";
	}
	

}
