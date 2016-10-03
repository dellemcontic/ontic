package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the service database table.
 * 
 */
@Entity
@NamedQuery(name="Service.findAll", query="SELECT s FROM Service s")
@Table(name = "service", schema="ontic")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="description")
	private String description;

	public Service() {
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

}