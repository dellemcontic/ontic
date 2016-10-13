package com.ontic.pgf.mitigation.db.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "location", schema="ontic")
@NamedQuery(name="Location.findAll", query="SELECT a FROM Location a")

public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Column(name="technology")
	private String technology;
	
	@Column(name="geometry")
	private String geometry;
	
	@Column(name="radius")
	private int radius;
	
	@Column(name="remarks")
	private String remarks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	
	

}

