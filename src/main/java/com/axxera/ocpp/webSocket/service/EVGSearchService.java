package com.axxera.ocpp.webSocket.service;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.model.es.StationLogs;
import com.axxera.ocpp.repository.es.EVGRepository;

@Service
public class EVGSearchService {

	@Autowired
	private EVGRepository repository;
	
	public Optional<StationLogs> findbyid(String id) {
		return repository.findById(id);
	}

	public Page<StationLogs> findbystnRefNum(String stnRefNum, Pageable pageable) {
		return repository.findByStnRefNum(stnRefNum, pageable);
	}

	public Page<StationLogs> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<StationLogs> findbystnRefNum(String stnRefNum, int page, int size, String filter, String sortBy,
			String orderBy, String startDate, String endDate) throws ParseException {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdTimestamp").descending());

		if (!filter.equalsIgnoreCase("null")) {
			return repository.findBystnRefNumAndreqType(stnRefNum, filter, pageable);

		} else if (filter.equalsIgnoreCase("null")) {
			return repository.findBystnRefNum(stnRefNum, pageable);
		} else if (startDate != null && endDate != null && stnRefNum != null && filter != null) {

			return repository.findBycreatedTimestampAndFilterAndStnRefNum(stnRefNum, filter, startDate, endDate,
					pageable);
		}
		return null;

	}
	
	public Page<StationLogs> updateBystnRefNum(String stnRefNum, int page, int size, String filter, String sortBy,
			String orderBy, String startDate, String endDate) throws ParseException {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdTimestamp").descending());

		if (!filter.equalsIgnoreCase("null")) {
			return repository.findBystnRefNumAndreqType(stnRefNum, filter, pageable);

		} else if (filter.equalsIgnoreCase("null")) {
			return repository.findBystnRefNum(stnRefNum, pageable);
		} else if (startDate != null && endDate != null && stnRefNum != null && filter != null) {

			return repository.findBycreatedTimestampAndFilterAndStnRefNum(stnRefNum, filter, startDate, endDate,
					pageable);
		}
		return null;

	}
	
//	public StationLogs update(String id) {
//		StationLogs<StationLogs> StationLogs = repository.findById(id);
//		ocppdata.setStnRefNum(String stnRefNum);
//
//		return repository.update(ocppdata);
//	}
	
//	public void OCPPdata (String id,OCPPdata ocppdata) {
//		for(int i = 0; i < ocppdata.size();i++) {
//			OCPPdata d = OCPPdata.getId();			
//			
//		}
//	}
	

}
