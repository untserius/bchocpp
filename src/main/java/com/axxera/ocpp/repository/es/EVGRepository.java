package com.axxera.ocpp.repository.es;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.axxera.ocpp.model.es.portstatusindex;
import com.axxera.ocpp.model.es.StationLogs;



public interface EVGRepository extends ElasticsearchRepository<StationLogs, String> {

	Page<StationLogs> findByStnRefNum(String stnRefNum, Pageable pageable);

	Page<StationLogs> findAll(Pageable pageable);

	@Query("{ \"query_string\" : { \"query\": \"(?0) AND (?1)\", \"fields\": [\"stnRefNum\", \"reqType\"] } }")
	Page<StationLogs> findBystnRefNumAndreqType(String stnRefNum, String reqType, Pageable pageable);

	@Query("{ \"query_string\" : { \"query\": \"(?0) AND (?1) AND (?2) AND (?3)\", \"fields\": [\"stnRefNum\", \"reqType\", \"createdTimestamp\", \"createdTimestamp\"] } }")
	Page<StationLogs> findBycreatedTimestampAndFilterAndStnRefNum(String stnRefNum, String filter, String startDate,
			String endDate, Pageable pageable);

	@Query("{ \"query_string\" : { \"query\": \"(?0)\", \"fields\": [\"stnRefNum\"] } }")
	Page<StationLogs> findBystnRefNum(String stnRefNum, Pageable pageable);
	
	@Query("{ \"query_string\" : { \"query\": \"(?0)\", \"fields\": [\"id\"] } }")
	Optional<StationLogs> findById(String id);
	
	@Query("GET /portstatusindex/_search{ \"query_string\" : {\"query\": {\"bool\": {\"must\": [{\"term\": {\"id\":\"(?0)\"}}]}}}}")
	Optional<portstatusindex> findByIdPort(String id);
	
	boolean existsById(String id);

	

}
