package com.axxera.ocpp.repository.es;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.axxera.ocpp.model.es.portstatusindex;

public interface EVGRepositoryPort extends ElasticsearchRepository<portstatusindex, String> {
	
	@Query("{\"query_string\":{\"query\":\"(?0)\",\"fields\": [\"portId\"]} , \"sort\": [{\"ToTimeStamp\":{\"order\": \"desc\"}}]}")
    portstatusindex findTop1ByCustomField1QueryOrderByTimestampDesc(long portUniId);
	
	boolean existsById(String id);

}