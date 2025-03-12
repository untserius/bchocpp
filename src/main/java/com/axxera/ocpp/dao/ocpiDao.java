package com.axxera.ocpp.dao;

import java.util.List;

import com.axxera.ocpp.model.ocpp.Site;

public interface ocpiDao {

	public void updateocpiRequestStatus(String uniqueID,String status);

}
