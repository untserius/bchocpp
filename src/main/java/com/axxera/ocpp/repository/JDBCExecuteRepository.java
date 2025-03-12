package com.axxera.ocpp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCExecuteRepository implements ExecuteRepository {
	
	@Qualifier("primary")
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Map<String, Object> findMap(String query) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(query);
		if(queryForList.size() > 0) {
			return queryForList.get(0);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> findAll(String query) {
		return jdbcTemplate.queryForList(query);
	}

	@Override
	public String findString(String query) {
		return jdbcTemplate.queryForObject(query, String.class);
	}

	@Override
	public <T> List<T> findAllSingalObject(String query,T newEntry) {
		return (List<T>) jdbcTemplate.queryForList(query, newEntry.getClass());

	}
	
	@Override
	public String getRecordBySqlStr(String query,String removal) {
		String r = String.valueOf(jdbcTemplate.queryForList(query)).replace("[", "").replace("]", "").replace("{", "").replace(removal+"=", "").replace("}", "");
		if(r == null || r.equalsIgnoreCase("null") || r.equalsIgnoreCase(" ") || r.equalsIgnoreCase("")) {
			r = "0";
		}
		return r;
	}

	@Override
	public List<String> findAllSingalString(String query) {
		return jdbcTemplate.queryForList(query, String.class);
	}

	@Override
	public int update(String query) {
		return jdbcTemplate.update(query);
	}

	@Override
	public String getRecordBySql(String query) {
		return jdbcTemplate.queryForObject(query, String.class);
	}

}
