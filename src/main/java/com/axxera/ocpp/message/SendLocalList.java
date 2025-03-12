package com.axxera.ocpp.message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SendLocalList {
	
private String listVersion;
	
	private String updateType;
	
	private Long stationId;
	
	private Object listData;
	
	private Object responseData;
	
	List<String> listOfIdTags=new ArrayList<String> ();
	
	List<LocalAuthorizationList> localAuthorizationLists=new ArrayList<LocalAuthorizationList>();

	public List<String> getListOfIdTags() {
		return listOfIdTags;
	}

	public void setListOfIdTags(List<String> listOfIdTags) {
		this.listOfIdTags = listOfIdTags;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public Object getListData() {
		return listData;
	}

	public void setListData(Object listData) {
		this.listData = listData;
	}

	public String getListVersion() {
		return listVersion;
	}

	public void setListVersion(String listVersion) {
		this.listVersion = listVersion;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public List<LocalAuthorizationList> getLocalAuthorizationLists() {
		return localAuthorizationLists;
	}

	public void setLocalAuthorizationLists(List<LocalAuthorizationList> localAuthorizationLists) {
		this.localAuthorizationLists = localAuthorizationLists;
	}

	@Override
	public String toString() {
		return "SendLocalList [listVersion=" + listVersion + ", updateType=" + updateType + ", stationId=" + stationId
				+ ", listData=" + listData + ", responseData=" + responseData + ", listOfIdTags=" + listOfIdTags
				+ ", localAuthorizationLists=" + localAuthorizationLists + "]";
	}



}
