package com.axxera.ocpp.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.dao.StatusNotificationDao;
import com.axxera.ocpp.message.StatusNotification;
import com.axxera.ocpp.model.ocpp.DiagnosticsFilesLocation;
import com.axxera.ocpp.model.ocpp.NotifyMe;
import com.axxera.ocpp.repository.ExecuteRepository;
import com.axxera.ocpp.utils.EsLoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webSocket.service.StationService;
import com.axxera.ocpp.webSocket.service.ocpiService;

@Service
public class StatusNotificationDaoImpl implements StatusNotificationDao{
	private final static Logger logger = LoggerFactory.getLogger(StatusNotificationDaoImpl.class);
	
	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Autowired
	private ExecuteRepository executeRepository;
	
	@Autowired
	private EsLoggerUtil esLoggerUtil;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private ocpiService ocpiService;
	
	@Autowired
	private Utils utils;
	
	@Value("${ftp.server.ip}")
	private String ftpIp;

	@Value("${ftp.user}")
	private String ftpUser;

	@Value("${ftp.password}")
	private String ftpPass;
	
	@Value("${LOADMANAGEMENT_URL}")
	private String loadManagementURL;
	
	@Override
	public String getStatus(long stationId, long connectorId)  {
		String status = "";
		try {
		    String query="select status from statusNotification where port_id='"+connectorId+"' and stationId='"+stationId+"'";
		    status=generalDao.getRecordBySql(query);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public List<NotifyMe> getNotifications(long clientId)  {
		List<NotifyMe> findAll = new ArrayList<NotifyMe>();
		try {
			findAll = generalDao.findAll("From NotifyMe where stationId=" + clientId, new NotifyMe());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return findAll;
	}
	@Override
	public void deleteNotification(long clientId)  {
		try {
			executeRepository.update("delete from notify_me where stationId=" + clientId);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
    public void updateOcppStatusNotification(String Status, Long stationId, Long connectorId,boolean ampFlag) {
        try {
        	List<Map<String,Object>> stationStatusDB = getPortStatus(stationId, connectorId);
            String updateOcppstatusNotification = "update statusNotification set status='" + Status + "' where StationID=" + stationId + " and port_id =" + connectorId;
        	executeRepository.update(updateOcppstatusNotification);
        	if(ampFlag) {
        		 updateStatusApiLoadManagement(Status, connectorId, stationId, ampFlag);
        	}
           if(stationStatusDB.size() > 0 &&  !String.valueOf(stationStatusDB.get(0).get("status")).equalsIgnoreCase(Status)) {
                String utcDateFormate = Utils.getUTCDateString();
                Map<String, Object> siteObj = stationService.getSiteDetails(stationId);
                long siteId = Long.valueOf(String.valueOf(siteObj.get("siteId")));
                stationService.updatingLastUpdatedTime(utcDateFormate, stationId, siteId);
                ocpiService.postlastupdated(connectorId,Boolean.valueOf(String.valueOf(siteObj.get("ocpiflag"))));
            }
           updatePortStatus(connectorId,Status);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
	@Override
	public void updateStatusNotification(long clientId, String uniqueId, StatusNotification statusNoti, long portUniId,
			Map<String, Object> station, String stationStatus, List<Map<String, Object>> portStatus) {
		try {
			String sql = "update statusNotification set  errorCode='" + statusNoti.getErrorCode()
					+ "',inOperativeFlag='1',requestId='" + uniqueId + "'" + " ,stationId='" + String.valueOf(clientId)
					+ "',status='" + stationStatus + "',timeStamp='" + utils.stringToDate(statusNoti.getTimestamp())
					+ "'," + "vendorErrorCode='" + statusNoti.getVendorErrorCode() + "',info='" + statusNoti.getInfo()
					+ "' where port_id='" + portUniId + "'";
			executeRepository.update(sql);

			//updateConnectorInNteworkProfiles(stationStatus, portUniId);
			updateStatusApiLoadManagement(stationStatus, portUniId, clientId,Boolean.valueOf(String.valueOf(station.get("ampFlag"))));
			updatePortStatus(portUniId, stationStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateInoperativeFlag(long StationId, long connectorid)  {
		try {
			executeRepository.update("UPDATE statusNotification SET inOperativeFlag = 1 WHERE stationId = "+StationId+" and port_id="+connectorid);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updatingStatusInStatusNotificationlist(String stationIds)  {
		try {
			String updateOcppstatusNotification = "update PortStatusNotification set status='Inoperative' , inOperativeFlag = '1' where stationId In (" + stationIds + ") and status != 'Charging'";
			generalDao.updateHqlQuiries(updateOcppstatusNotification);
			
			Thread th = new Thread() {
			public void run() {
					String updateNetworkProfiles = "update connectors_in_networkprofile set portStatus = 'Inoperative' where stationId in (" + stationIds + ")";
					generalDao.updateHqlQuiries(updateNetworkProfiles);
				}
			};
			th.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Map<String, Object>> getPortStatus(long stationId, long connectorId)  {
		List<Map<String, Object>> stationStatus = new ArrayList<Map<String, Object>>();
		try {
			String str = "select status,timeStamp,requestId From statusNotification where port_id =" + connectorId + " AND stationId=" + stationId + " order by id desc";
			
			stationStatus = executeRepository.findAll(str);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return stationStatus;
	}
	
	@Override
	public boolean getReservationId(Long id, int reservationId, long stationId,String stnRefNum) {
		Boolean flag = false;
		try {
			String stationReservationId = " select reservationId from ocpp_reservation where flag=1 and reservationId="
					+ reservationId + " and stationId=" + stationId + " and endTime >= GETUTCDATE() and userId ='"+id+"' order by id desc";
			String reserveId = generalDao.getRecordBySql(stationReservationId);
			if (reserveId != null) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public void updateStatusNotificationAvailable(long clientId, String uniqueId, StatusNotification statusNoti,long portUniId, Map<String, Object> station, Map<String, Object> portObj, String portStatus,
			List<Map<String, Object>> portStatusVal) {
		try {
			String sql = "update statusNotification set  errorCode='" + statusNoti.getErrorCode()
					+ "',inOperativeFlag='0',requestId='" + uniqueId + "'" + " ,stationId='" + String.valueOf(clientId)
					+ "',status='" + portStatus + "',timeStamp='" + utils.stringToDate(statusNoti.getTimestamp()) + "',"
					+ "vendorErrorCode='" + statusNoti.getVendorErrorCode() + "',info='" + statusNoti.getInfo()
					+ "' where port_id='" + String.valueOf(portObj.get("id")) + "'";
			executeRepository.update(sql);

			/*Thread th = new Thread() {
				public void run() {
					String sql1 = "Update connectors_in_networkprofile set portStatus ='" + portStatus
							+ "', optFlag=0 where portId = '" + String.valueOf(portObj.get("id")) + "'";
					executeRepository.update(sql1);

					String sql2 = "Update fleet_sessions set status='InActive' where stationId=" + clientId
							+ " and portId=" + portUniId;
					executeRepository.update(sql2);

					String Sql3 = "delete soc_priority where stationId=" + clientId + " and portId=" + portUniId;
					executeRepository.update(Sql3);

					String Sql4 = "delete optimization_meter where stationId=" + clientId + " and portId=" + portUniId;
					executeRepository.update(Sql4);
				}
			};
			th.start();*/
			updatePortStatus(Long.valueOf(String.valueOf(portObj.get("id"))), portStatus);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateStatusNotificationUnAvailable(long clientId, String uniqueId, StatusNotification statusNoti,long portUniId, Map<String, Object> station, Map<String, Object> portObj, String portStatus,
			List<Map<String, Object>> portStatusVal) {
		try {
			String sql = "update statusNotification set  errorCode='" + statusNoti.getErrorCode()
					+ "',inOperativeFlag='1',requestId='" + uniqueId + "'" + " ,stationId='" + String.valueOf(clientId)
					+ "',status='" + portStatus + "',timeStamp='" + utils.stringToDate(statusNoti.getTimestamp()) + "',"
					+ "vendorErrorCode='" + statusNoti.getVendorErrorCode() + "',info='" + statusNoti.getInfo()
					+ "' where port_id='" + String.valueOf(portObj.get("id")) + "'";
			executeRepository.update(sql);

			//updateConnectorInNteworkProfiles(portStatus, Long.valueOf(String.valueOf(portObj.get("id"))));
			updateStatusApiLoadManagement(portStatus, Long.valueOf(String.valueOf(portObj.get("id"))), clientId,Boolean.valueOf(String.valueOf(station.get("ampFlag"))));
			updatePortStatus(Long.valueOf(String.valueOf(portObj.get("id"))), portStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insertPortErrorStatus(StatusNotification statusNoti,long stnId,long portUniId,String clientId,String reqId,boolean scheduleMaintenance) {
		try {
			esLoggerUtil.createPortErrorStatus(statusNoti,clientId,reqId ,stnId ,portUniId,scheduleMaintenance);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean getfaultMailCheck(long stnId,String status,String Date) {
		boolean val = false;
		try {
			String query = "select id from chargerActivities where convert(varchar, ISNULL(faultedMailDate,DATEADD(DAY,-1,GETDATE())), 23) != '"+Date+"' and faultedMailFlag=1 and stationId = "+stnId;
			List<Map<String, Object>> mapData = executeRepository.findAll(query);
			if(mapData.size() > 0) {
				val = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	@Override
	public void updatePortStatus(long portUniId,String portStatus) {
		try {
			Thread th = new Thread() {
				public void run() {
					try {
						String sql="update port set status = '"+portStatus+"' where id = "+portUniId;
						executeRepository.update(sql);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			th.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertdiagnosticsFilesLocation(String manufacturerId ,long stnId, String location, String uniqueID) {
		try {
			DiagnosticsFilesLocation dgf=new DiagnosticsFilesLocation();
			dgf.setManufacturerId(manufacturerId);
			dgf.setLocation(location);
			dgf.setDate(utils.getUTCDate());
			dgf.setStationId(stnId);
			dgf.setUniqueID(uniqueID);
			dgf.setFileName("");
			generalDao.saveOrupdate(dgf);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateDiagnosticsFilesLocation(String fileName, String uniqueID) {
		try {
			String sql="update diagnosticsFilesLocation set fileName = '"+fileName+"' where uniqueID = '"+uniqueID+"'";
            executeRepository.update(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateTimeInChargerAcivites(long stnId,String date) {
		try {
			String sql="update chargerActivities set faultedMailDate='"+date+"' where stationId="+stnId;
            executeRepository.update(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void createFtpPath(String stationReferNo) {
		String remotePath = "Diagnosticsfile/"+stationReferNo;
		try {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ftpIp);
		boolean login = ftpClient.login(ftpUser, ftpPass);
		if (login) {
				boolean created = ftpClient.makeDirectory(remotePath);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void updateStatusApiLoadManagement(String portStatus, long portId, Long stationId,boolean ampFlag) {
		try {
			if(ampFlag) {
				Thread th = new Thread() {
					public void run() {
						try {
							logger.info("updateStatusApiLoadManagement ");
							String jsonInputString = "{\"portStatus\":\"" + portStatus + "\"," + "\"portId\": "+ portId +"}";
							logger.info("jsonInputString AMPCONTROL SERVICE IMPL: " + jsonInputString);
							String URL = loadManagementURL+"/"+"portStatus";
							logger.info("URL : " + URL);
							CloseableHttpClient client = HttpClients.createDefault();
							HttpPost httpPost = new HttpPost(URL);
							StringEntity entity = new StringEntity(jsonInputString);
							httpPost.setEntity(entity);
							httpPost.setHeader("Accept", "application/json");
							httpPost.setHeader("Content-type", "application/json");
							CloseableHttpResponse response = client.execute(httpPost);
							logger.info(" OCPP URL: " + response.getStatusLine());
							client.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				th.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}