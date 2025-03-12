package com.axxera.ocpp.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.message.StatusNotification;
import com.axxera.ocpp.model.es.OCPPChargingIntervalData;
import com.axxera.ocpp.model.es.OcppMeterValues;
import com.axxera.ocpp.model.es.StationLogs;
import com.axxera.ocpp.model.es.StationUpAndDownData;
import com.axxera.ocpp.model.es.portstatusindex;
import com.axxera.ocpp.model.ocpp.OCPPMeterValuesPojo;
import com.axxera.ocpp.repository.es.EVGRepository;

@Service
public class EsLoggerUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(EsLoggerUtil.class);
	
	@Value("${es.stationlogs}")
	private String OCPPLOGS_INDEX;
	
	@Value("${es.metervaluelogs}")
	private String OCPPMERTERVALUELOGS_INDEX;
	
	@Value("${es.portstatuslogs}")
	private String CREATEPORTERRORSTATUS_INDEX;
	
	@Value("${es.stationupanddowndata}")
	private String stationupanddowndata;
	
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;
	
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private EVGRepository repository;
	
	@Autowired
	private Utils utils;
	
	@Value("${es.ocppchargingintervallogs}")
	public String OCPPCHARGINGINTERVALDATALOGS_INDEX;

	public void createOcppLogsIndex(StationLogs logs) {
		try {
			Thread th = new Thread() {
				public void run() {
					logs.setCreatedTimestamp(new Date());
					IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(logs.getId())).withObject(logs).build();
					elasticsearchOperations.index(indexQuery, IndexCoordinates.of(OCPPLOGS_INDEX));
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateOCPP(StationLogs StationLogs) {
		try {
			Thread th = new Thread() {
				public void run() {
					try {
						if(repository.existsById(StationLogs.getId())) {
							Optional<com.axxera.ocpp.model.es.StationLogs> findById = repository.findById(StationLogs.getId());
							if (findById != null && findById.isPresent() && findById.get()!=null) {
								StationLogs sl = findById.get();
								sl.setResponse(StationLogs.getResponse());
								sl.setStatus(StationLogs.getStatus());
								sl.setCreatedTimestamp(new Date());
								createOcppLogsIndex(sl);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void esInfo(StationLogs log) {
		try {
			Thread th = new Thread() {
				public void run() {
					createOcppLogsIndex(log);
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertLongs(String reqId, String clientId, String reqType, String request, String referNo,long stationId,String status,long connectorId) {
		try {
			Thread th = new Thread() {
				public void run() {
					if(reqId != null && reqId.contains(":")) {
						String id =reqId;
						if(clientId.equalsIgnoreCase("Portal") || clientId.equalsIgnoreCase("Android") || clientId.equalsIgnoreCase("Ios") || clientId.equalsIgnoreCase("ocpi")) {
							String[] sedVal = reqId.split(":");
							if (sedVal.length > 1) {
								id = sedVal[0];
							}
						}
				
						StationLogs log = new StationLogs();
						log.setId(id);
						log.setStationId(stationId);
						log.setRequest(request);
						log.setRequestType(reqType);
						log.setStatus(status);
						log.setRequestId(reqId);
						log.setStnRefNum(referNo);
						log.setClientId(clientId);
						log.setConnectorId(connectorId);
						esInfo(log);
					}
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateLogs(String reqId,String status,String response ) {
		try {
			Thread th = new Thread() {
				public void run() {
					StationLogs sl = new StationLogs();
					sl.setId(reqId);
					sl.setStatus(status);
					sl.setResponse(response);
					updateOCPP(sl);
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public portstatusindex getEntityByPortIdWithTimestampSort(long portId) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                		.must(QueryBuilders.matchQuery("portId", portId)))
                .withSort(SortBuilders.fieldSort("ToTimeStamp").order(SortOrder.DESC))
                .build();

        return executeNativeSearchQuery(nativeSearchQuery);
    }
	private portstatusindex executeNativeSearchQuery(NativeSearchQuery nativeSearchQuery) {
		try {
			SearchHits<portstatusindex> search = elasticsearchRestTemplate.search(nativeSearchQuery, portstatusindex.class);
			if(search.getTotalHits() > 0) {
				SearchHit<portstatusindex> searchHit = search.getSearchHit(0);
				if(searchHit != null) {
					portstatusindex content = searchHit.getContent();
					return content;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	public void updatePortStatusLogs(long stnId,long portUniId,Date toTime) {
		try {
			portstatusindex findById = getEntityByPortIdWithTimestampSort(portUniId);
			if (findById != null) {
				if(String.valueOf(findById.getStatus()).equalsIgnoreCase("SuspendedEVSE") || String.valueOf(findById.getStatus()).equalsIgnoreCase("Unavailable") || String.valueOf(findById.getStatus()).equalsIgnoreCase("Inoperative")) {
				  Map<String,Double> map=utils.getTimeDifferenceInMiliSec(findById.getTimeStamp(),toTime);
				  double time=map.get("Seconds");
				  if(time<=5 && findById.getId()!=null) {
					  deletePortErrorStatusIndex(findById);
				  }else {
					  findById.setToTimeStamp(toTime);
					  createPortErrorStatusIndex(findById);
				  }
				}else {
					findById.setToTimeStamp(toTime);
					createPortErrorStatusIndex(findById);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String deletePortStatusLogs(long stnId,long portUniId,Date toTime) {

		try {
			portstatusindex findById = getEntityByPortIdWithTimestampSort(portUniId);
			if (findById != null) {
				if(String.valueOf(findById.getStatus()).equalsIgnoreCase("SuspendedEVSE") || String.valueOf(findById.getStatus()).equalsIgnoreCase("Unavailable") || String.valueOf(findById.getStatus()).equalsIgnoreCase("Inoperative")) {
				  Map<String,Double> map=utils.getTimeDifferenceInMiliSec(findById.getTimeStamp(),toTime);
				  double time=map.get("Seconds");
				  if(time<5 && findById.getId()!=null) {
					  deletePortErrorStatusIndex(findById);
				  }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return "delete";
	}
	public void insertStationLogs(String reqId, String clientId, String reqType, String request, String referNo,String response,String status,long stationId,long connectorId) {
		try {
			Thread th = new Thread() {
				public void run() {
					String id =reqId;
					try {
						if(clientId.equalsIgnoreCase("Portal") || clientId.equalsIgnoreCase("Android") || clientId.equalsIgnoreCase("Ios") || clientId.equalsIgnoreCase("ocpi")) {
							String[] sedVal = reqId.split(":");
							if (sedVal.length > 1) {
								id = sedVal[0];
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					try {
						StationLogs log = new StationLogs();
						log.setId(utils.getRandomNumber(""));
						log.setRequest(request);
						log.setStationId(stationId);
						log.setRequestType(reqType);
						log.setStatus(status);
						log.setResponse(response);
						log.setRequestId(reqId);
						log.setStnRefNum(referNo);
						log.setClientId(clientId);
						log.setConnectorId(connectorId);
						esInfo(log);
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
	
	public void createOcppMeterLogsIndex(OCPPMeterValuesPojo meterValues,String clientId,String reqId,Date createdTimestamp) {
		Thread th = new Thread() {
			public void run() {
				String id =reqId;
				try {
					if(clientId.equalsIgnoreCase("Portal") || clientId.equalsIgnoreCase("Android") || clientId.equalsIgnoreCase("Ios") || clientId.equalsIgnoreCase("ocpi")) {
						String[] sedVal = reqId.split(":");
						if (sedVal.length > 1) {
							id = sedVal[0];
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				try {						
					OcppMeterValues log = new OcppMeterValues();
					log.setId(utils.getuuidRandomId());
					log.setSessionId(meterValues.getSessionId());
					log.setTransactionId(String.valueOf(meterValues.getTransactionId()));
					log.setPortId(meterValues.getPortId());
					log.setMeterValueTimeStamp(meterValues.getTimeStamp());
					log.setCreatedTimestamp(createdTimestamp);

					log.setEnergyActiveExportRegisterUnit(meterValues.getEnergyActiveExportRegisterUnit());
					log.setEnergyActiveImportRegisterUnit(meterValues.getEnergyActiveImportRegisterUnit());
					log.setEnergyReactiveImportRegisterUnit(meterValues.getEnergyReactiveImportRegisterUnit());
					log.setEnergyReactiveExportRegisterUnit(meterValues.getEnergyReactiveExportRegisterUnit());
					log.setEnergyActiveExportIntervalUnit(meterValues.getEnergyActiveExportIntervalUnit());
					log.setEnergyActiveImportIntervalUnit(meterValues.getEnergyActiveImportIntervalUnit());
					log.setEnergyReactiveExportIntervalUnit(meterValues.getEnergyReactiveExportIntervalUnit());
					log.setEnergyReactiveImportIntervalUnit(meterValues.getEnergyReactiveImportIntervalUnit());
					log.setPowerActiveExportUnit(meterValues.getPowerActiveExportUnit());
					log.setPowerActiveImportUnit(meterValues.getPowerActiveImportUnit());
					log.setPowerOfferedUnit(meterValues.getPowerOfferedUnit());
					log.setPowerReactiveExportUnit(meterValues.getPowerReactiveExportUnit());
					log.setPowerReactiveImportUnit(meterValues.getPowerReactiveImportUnit());
					log.setPowerFactorUnit(meterValues.getPowerFactorUnit());
					log.setCurrentImportUnit(meterValues.getCurrentImportUnit());
					log.setCurrentExportUnit(meterValues.getCurrentExportUnit());
					log.setCurrentOfferedUnit(meterValues.getCurrentOfferedUnit());
					log.setVoltageUnit(meterValues.getVoltageUnit());
					log.setFrequencyUnit(meterValues.getFrequencyUnit());
					log.setTemperatureUnit(meterValues.getTemperatureUnit());
					log.setSoCUnit(meterValues.getSoCUnit());
					log.setRPMUnit(meterValues.getRPMUnit());
					
					log.setEnergyActiveExportRegisterValue(meterValues.getEnergyActiveExportRegisterValue());
					log.setEnergyActiveImportRegisterValue(meterValues.getEnergyActiveImportRegisterValue());
					log.setEnergyActiveImportRegisterDiffValue(meterValues.getEnergyActiveImportRegisterDiffValue());
					log.setEnergyReactiveExportRegisterValue(meterValues.getEnergyReactiveExportRegisterValue());
					log.setEnergyReactiveImportRegisterValue(meterValues.getEnergyReactiveImportRegisterValue());
					log.setEnergyActiveExportIntervalValue(meterValues.getEnergyActiveExportIntervalValue());
					log.setEnergyActiveImportIntervalValue(meterValues.getEnergyActiveImportIntervalValue());
					log.setEnergyReactiveExportIntervalValue(meterValues.getEnergyReactiveExportIntervalValue());
					log.setEnergyReactiveImportIntervalValue(meterValues.getEnergyReactiveImportIntervalValue());
					log.setPowerActiveExportValue(meterValues.getPowerActiveExportValue());
					log.setPowerActiveImportValue(meterValues.getPowerActiveImportValue());
					log.setPowerOfferedValue(meterValues.getPowerOfferedValue());
					log.setPowerReactiveExportValue(meterValues.getPowerReactiveExportValue());
					log.setPowerReactiveImportValue(meterValues.getPowerReactiveImportValue());
					log.setPowerFactorValue(meterValues.getPowerFactorValue());
					log.setCurrentImportValue(meterValues.getCurrentImportValue());
					log.setCurrentExportValue(meterValues.getCurrentExportValue());
					log.setCurrentOfferedValue(meterValues.getCurrentOfferedValue());
					log.setVoltageValue(meterValues.getVoltageValue());
					log.setFrequencyValue(meterValues.getFrequencyValue());
					log.setTemperatureValue(meterValues.getTemperatureValue());
					log.setSoCValue(meterValues.getSoCValue());
					log.setRPMValue(meterValues.getRPMValue());
					log.setPowerActiveImportDiffValue(meterValues.getPowerActiveImportDiffValue());
					log.setCurrentImportDiffValue(meterValues.getCurrentImportDiffValue());
					log.setSoCDiffValue(meterValues.getSoCDiffValue());
					createOcppMeterLogsIndex(log);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.start();		
	}
	
	public void createOcppMeterLogsIndex(OcppMeterValues meterValues) {
		try {
			meterValues.setCreatedTimestamp(new Date());
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(meterValues.getId())).withObject(meterValues).build();
			elasticsearchOperations.index(indexQuery, IndexCoordinates.of(OCPPMERTERVALUELOGS_INDEX));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createPortErrorStatus(StatusNotification statusNoti,String clientId,String reqId , long stnId,long portUniId,boolean maintenance) {
//		try {
//			String delete=deletePortStatusLogs(stnId,portUniId,statusNoti.getTimestamp());
//			logger.info("delete >> : "+delete);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			updatePortStatusLogs(stnId,portUniId,statusNoti.getTimestamp());
		}catch(Exception e) {
			e.printStackTrace();
		}
		Thread th = new Thread() {
			public void run() {
				String id =reqId;
				try {
					if(clientId.equalsIgnoreCase("Portal") || clientId.equalsIgnoreCase("Android") || clientId.equalsIgnoreCase("Ios") || clientId.equalsIgnoreCase("ocpi")) {
						String[] sedVal = reqId.split(":");
						if (sedVal.length > 1) {
							id = sedVal[0];
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				try {	
					portstatusindex pes = new portstatusindex();
					pes.setId(id);
					pes.setCreateDate(utils.getUTCDate());
					pes.setErrorCode(statusNoti.getErrorCode());
					pes.setPortId(portUniId);
					pes.setStationId(stnId);
					pes.setStatus(statusNoti.getStatus());
					pes.setSource("Charger");
					pes.setTimeStamp(statusNoti.getTimestamp());
					pes.setVendorErrorCode(statusNoti.getVendorErrorCode());
					pes.setInfo(statusNoti.getInfo());
					pes.setVendorId(statusNoti.getVendorId());	
					pes.setToTimeStamp(utils.addSec(1,statusNoti.getTimestamp()));
					pes.setMaintenance(maintenance);
					createPortErrorStatusIndex(pes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.start();	
	}
	
	public void createPortErrorStatusIndex(portstatusindex pes) {
		try {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(pes.getId())).withObject(pes).build();
			String index = elasticsearchOperations.index(indexQuery, IndexCoordinates.of(CREATEPORTERRORSTATUS_INDEX));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deletePortErrorStatusIndex(portstatusindex pes) {
		try {
			String index=elasticsearchOperations.delete(pes.getId(), IndexCoordinates.of(CREATEPORTERRORSTATUS_INDEX));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void createOCPPChargingIntervalDataIndex(OCPPChargingIntervalData ocppChargingIntervalData) {
		try {
			ocppChargingIntervalData.setCreatedTimestamp(new Date());
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(ocppChargingIntervalData.getId())).withObject(ocppChargingIntervalData).build();
			elasticsearchOperations.index(indexQuery, IndexCoordinates.of(OCPPCHARGINGINTERVALDATALOGS_INDEX));
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void createStationUpAndDownData(StationUpAndDownData sud) {
		try {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(sud.getId())).withObject(sud).build();
			String index = elasticsearchOperations.index(indexQuery, IndexCoordinates.of(stationupanddowndata));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createStationUpAndDownDataBulk(List<IndexQuery> in) {
		try {
			Thread th = new Thread() {
				public void run() {
					elasticsearchOperations.bulkIndex(in, IndexCoordinates.of(stationupanddowndata));
				}
			};
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public StationUpAndDownData getStationUpAndDownData(long stationId) {
        try {
        	NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.boolQuery()
                    		.must(QueryBuilders.matchQuery("stationId", stationId)))
                    .withSort(SortBuilders.fieldSort("startTimeStamp").order(SortOrder.DESC))
                    .build();

            return executeStationUpAndDownData(nativeSearchQuery);
        }catch(Exception e) {
        	logger.error("",e);
        }
        return null;
    }
	private StationUpAndDownData executeStationUpAndDownData(NativeSearchQuery nativeSearchQuery) {
		try {
			SearchHits<StationUpAndDownData> search = elasticsearchRestTemplate.search(nativeSearchQuery, StationUpAndDownData.class);
			if(search.getTotalHits() > 0) {
				SearchHit<StationUpAndDownData> searchHit = search.getSearchHit(0);
				if(searchHit != null) {
					StationUpAndDownData content = searchHit.getContent();
					return content;
				}
			}
		}catch (Exception e) {
			logger.error("",e);
		}
        return null;
    }
	
}
