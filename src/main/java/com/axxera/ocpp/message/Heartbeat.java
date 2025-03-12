package com.axxera.ocpp.message;

public class Heartbeat {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Heartbeat [id=" + id + "]";
	}

}
