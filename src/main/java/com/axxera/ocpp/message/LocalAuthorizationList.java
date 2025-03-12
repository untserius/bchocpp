package com.axxera.ocpp.message;

public class LocalAuthorizationList {

	private String idTag;
	
	private IdTagInfo idTagInfo; 
	

	public IdTagInfo getIdTagInfo() {
		return idTagInfo;
	}



	public void setIdTagInfo(IdTagInfo idTagInfo) {
		this.idTagInfo = idTagInfo;
	}



	public String getIdTag() {
		return idTag;
	}



	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}


	@Override
	public String toString() {
		return "LocalAuthorizationList [idTag=" + idTag + ", idTagInfo=" + idTagInfo + "]";
	}

	
}
