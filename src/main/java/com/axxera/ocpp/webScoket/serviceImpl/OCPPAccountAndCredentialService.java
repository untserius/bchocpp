package com.axxera.ocpp.webScoket.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.model.ocpp.Credentials;
import com.axxera.ocpp.repository.ExecuteRepository;

@Service
public class OCPPAccountAndCredentialService {
	
	private final static Logger logger = LoggerFactory.getLogger(OCPPAccountAndCredentialService.class);

	@Autowired
	private  GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	public Credentials getCredentialByrfId(String idTag)  {
		Credentials cr = null;
		try {
			cr = generalDao.findOneBySQLQuery("select * from creadential where rfId='" + idTag + "' OR phone='" + idTag + "' OR rfIdHex='"+idTag+"'",new Credentials());
			if(cr==null) {
				char Prefix = idTag.charAt(0);
				idTag = idTag.substring(1);
				if(Prefix=='0') {
					cr = generalDao.findOneBySQLQuery("select * from creadential where rfId='" + idTag + "' OR phone='" + idTag + "' OR rfIdHex='"+idTag+"'",new Credentials());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cr;
	}
	public Map<String,Object> manualIdCheck(String idTag,long manfId,long stnId,long siteId) {
		Map<String,Object> map = new HashMap<>();
		map.put("flag", false);
		map.put("profileType", "");
		map.put("profileName", "");
		map.put("billing", false);
		try {
			int tag_profileId=0;
			String profileType="";
			String query1="select isNull(ip.billing,'0') as billing, ip.profileType,ip.profileName,ip.id as idTagProfileId from idtags_in_profile iip inner join idtag_profiles ip on iip.tag_profileId = ip.id where idTag = '"+idTag+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(query1);
			if(mapData.size() > 0) {
				map.put("billing", Boolean.parseBoolean(String.valueOf(mapData.get(0).get("billing"))));
				tag_profileId=Integer.parseInt(mapData.get(0).get("idTagProfileId").toString());
				profileType = mapData.get(0).get("profileType").toString();
				if(profileType.equalsIgnoreCase("Manufacturer")) {
					String manfactureQuery = "select m.manfName,u.email as manufacturerEmail from idtagprofile_in_manufacture iipm inner join Users u on iipm.manfactureId = u.UserId "
							+ " inner join Usersinroles uir on uir.user_id = u.UserId inner join role r on r.id = uir.role_id inner join user_in_manufacturer uim on uim.UserId = "
							+ " iipm.manfactureId inner join manufacturer m on m.id = uim.manufacturerId where r.rolename = 'Manufacturer' and iipm.idtagProfileId = '"+tag_profileId+"' and m.id = '"+manfId+"'";
					List<Map<String, Object>> mapData1 = executeRepository.findAll(manfactureQuery);
					if(mapData1.size() > 0) {
						map.put("flag", true);
						map.put("profileType", profileType);
						map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
					}
				}else if(profileType.equalsIgnoreCase("General")) {
					String str = "select idTagProfileId from idtagprofile_in_all iia where iia.profileType = 'station' and iia.profileTypeId = '"+stnId+"' and iia.idTagProfileId = '"+tag_profileId+"'";
					List<Map<String, Object>> mapData2 = executeRepository.findAll(str);
					if(mapData2.size() > 0) {
						map.put("flag", true);
						map.put("profileType", profileType);
						map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
					}else {
						str = "select idTagProfileId from idtagprofile_in_all iia where iia.profileType = 'site' and iia.profileTypeId = '"+siteId+"' and iia.idTagProfileId ='"+tag_profileId+"'";
						mapData2 = executeRepository.findAll(str);
						if(mapData2.size() > 0) {
							map.put("flag", true);
							map.put("profileType", profileType);
							map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
						}else {
							str = "select idTagProfileId from Stations_DriverGroups sdg inner join idtagprofile_in_all iia on sdg.driverProfileGroupUnqId = iia.profileTypeId "
									+ " where iia.profileType = 'drivergroup' and stationUnqId = '"+stnId+"' and iia.idTagProfileId = '"+tag_profileId+"'";
							mapData2 = executeRepository.findAll(str);
							if(mapData2.size() > 0) {
								map.put("flag", true);
								map.put("profileType", profileType);
								map.put("profileName", String.valueOf(mapData.get(0).get("profileName")));
							}
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String,Object> getDriverGroupIdTag(String idTag) {
		Map<String,Object> map = new HashMap<>();
		try {
			String query = "select dgi.rfid,dpg.groupName,CAST(1 as bit) as dgiFlag from [driver_groupRFIDS] dgi inner join driver_profile_groups dpg on dgi.driver_group_id = dpg.id where dgi.rfid = '"+idTag+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("dgiFlag", false);
				map.put("groupName", "");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String,Object> getUserIdOnBaseRfid(String rfid) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String query = "select user_id,u.email from preproduction_rfids pr inner join users u on pr.user_Id = u.userId where pr.rfid = '"+rfid+"'";
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				map = mapData.get(0);
			}else {
				map.put("user_id", 0);
				map.put("email", "");
			}
			return map;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
