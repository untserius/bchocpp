package com.axxera.ocpp.message;

public class SignCertificate {
	
	private String certificate;

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return "SignCertificate [certificate=" + certificate + "]";
	}
	
	

}
