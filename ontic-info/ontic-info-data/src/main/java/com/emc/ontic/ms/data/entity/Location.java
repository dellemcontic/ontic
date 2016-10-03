package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
@Table(name = "location", schema="ontic")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="geometry")
	private String geometry;

	@Column(name="radius")
	private Integer radius;

	@Column(name="remarks")
	private String remarks;

	@Column(name="technology")
	private String technology;

	public Location() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGeometry() {
		return this.geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	public Integer getRadius() {
		return this.radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

}