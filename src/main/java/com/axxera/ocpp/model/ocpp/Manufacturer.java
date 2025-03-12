package com.axxera.ocpp.model.ocpp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "manufacturer")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "station" })
public class Manufacturer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String manfname;

	/*
	 * @JsonIgnore private Set<Station> station = new HashSet<Station>(0);
	 */

	@Temporal(TemporalType.DATE)
	private Date creationDate = new Date();

	/*
	 * @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	 * "manufacturer") public Set<Station> getStation() { return station; }
	 * 
	 * public void setStation(Set<Station> station) { this.station = station; }
	 */

	public Date getCreationDate() {
		return creationDate;
	}

	public String getManfname() {
		return manfname;
	}

	public void setManfname(String manfname) {
		this.manfname = manfname;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Manufacturer [manfname=" + manfname + ", creationDate=" + creationDate + "]";
	}

}
