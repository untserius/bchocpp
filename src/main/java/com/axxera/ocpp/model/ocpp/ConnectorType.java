package com.axxera.ocpp.model.ocpp;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "connectorType")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "port" })
public class ConnectorType extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Set<Port> port = new HashSet<Port>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "standard")
	public Set<Port> getPort() {
		return port;
	}

	public void setPort(Set<Port> port) {
		this.port = port;
	}
	
	

}
