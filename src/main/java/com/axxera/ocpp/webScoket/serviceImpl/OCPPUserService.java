package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.LoggerUtil;

@Service
public class OCPPUserService {
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private LoggerUtil customLogger;
	
	public Map<String,Object> getOrgData(long orgId,String stationRefNum) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			String query = "select ISNULL(address,'5251 California Ave, STE 150, Irvine, CA - 92617.') as address,email,fromEmail,host,legacykey,logoName,"
					+ " orgId,ISNULL(orgName,'BC Hydro') as orgName,supportEmail,password,isnull(phoneNumber,'949-945-2000') as phoneNumber,port,portalLink, "
					+ " isnull(protocol,'smtp') as protocol,isnull(serverKey,'') as serverKey from configurationSettings where orgId = '1'";
			list = executeRepository.findAll(query);
			if(list.size() > 0) {
				map = list.get(0);
			}else {
				map.put("orgName", "BC Hydro");
				map.put("orgId", "1");
				map.put("legacykey", "");
				map.put("serverKey", "");
				map.put("email", "no-reply@evgateway.net");
				map.put("phoneNumber", "no-reply@evgateway.net");
				map.put("host", "smtp.office365.com");
				map.put("port", "587");
				map.put("password", "EvGateway@1234!");
				map.put("address", "5251 California Ave, STE 150, Irvine, CA - 92617.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public List<Map<String,Object>> configProperties() {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			String query = "select address,email,host,legacykey,logoName,orgId,orgName,password,phoneNumber,port,portalLink,"
					+ "protocol,isnull(serverKey,'') as serverKey from configurationSettings ";
			list = executeRepository.findAll(query);
			if(list.size() > 0) {
				map = list.get(0);
			}else {
				map.put("orgName", "BC Hydro");
				map.put("orgId", "1");
				map.put("legacykey", "");
				map.put("serverKey", "");
				map.put("email", "");
				map.put("host", "");
				map.put("port", "");
				map.put("password", "");
				map.put("address", "5251 California Ave, STE 150, Irvine, CA - 92617.");
				list.add(map);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public Map<String,Object> getDealerOrgName(long stnId) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			String query = "select distinct st.referNo,o.orgName from organization o inner join dealer_in_org do on do.orgId = o.id "
					+ " inner join owner_in_dealer od on od.dealerId = do.dealerId  inner join users_in_sites us on us.userId = "
					+ " od.ownerId inner join site si on us.siteId = si.siteId inner join station st on si.siteId = st.siteId "
					+ " where st.id = '"+stnId+"'";
			list = executeRepository.findAll(query);
			if(list.size() > 0) {
				map = list.get(0);
			}else {
				String selectQuery = "select distinct st.referno,o.orgName from station st inner join site si on "
						+ " si.siteId = st.siteId inner join organization o on o.id = si.org where st.id = '"+stnId+"'";
				list = executeRepository.findAll(selectQuery);
				if(list.size() > 0) {
					map.put("orgName", "BC Hydro");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String,Object> logoDeatils(long orgId,String stationRefNum) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			String query = "select url from logo_image where orgId = '"+orgId+"' and logoType='main'";
			list = executeRepository.findAll(query);
			if(list.size() > 0) {
				map = list.get(0);
			}else {
				map.put("url", "");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String,Object> getNewOrgDetails(Long oldOrg) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			String query =  "SELECT id as orgId,OrgName as orgName FROM organization WHERE oldOrgId = '"+oldOrg+"'";
			list = executeRepository.findAll(query);
			if(map == null || map.size() == 0) {
				map = list.get(0);
			}else {
				map.put("orgName", "BC Hydro");
				map.put("orgId", "1");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String,Object> getWhiteLabelOrg(Long stnId) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			String query =  "select distinct o.orgName,o.id as orgId from organization o inner join dealer_in_org do on do.orgId = o.id "
					+ " inner join owner_in_dealer od on od.dealerId = do.dealerId  inner join users_in_sites us on us.userId = "
					+ " od.ownerId inner join site si on us.siteId = si.siteId inner join station st on si.siteId = st.siteId where "
					+ " o. whitelabel = 1 and st.id = '"+stnId+"'";
			list = executeRepository.findAll(query);
			if(list.size() > 0) {
				map = list.get(0);
			}else {
				map.put("orgName", "BC Hydro");
				map.put("orgId", "1");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
