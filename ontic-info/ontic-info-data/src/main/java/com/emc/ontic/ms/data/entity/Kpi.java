package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the kpi database table.
 * 
 */
@Entity
@NamedQuery(name="Kpi.findAll", query="SELECT k FROM Kpi k")
@Table(name = "kpi", schema="ontic")
public class Kpi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="description")
	private String description;

	@Column(name="service_id")
	private String serviceId;

	public Kpi() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}