package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "creditCardIdTag")
public class CreditCardIdTag extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String idTag;
	
	private String manufacturerName;

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	@Override
	public String toString() {
		return "CreditCardIdTag [idTag=" + idTag + ", manufacturerName=" + manufacturerName + "]";
	}
	
}
