package ontic.af.action.db.entity;

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
	
	@Column(name="segmentation_id")
	private String segmentation_id;
	
	@Column(name="cell_name")
	private String cell_name;
	
	@Column(name="cell_id")
	private String cell_id;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="weight")
	private int weight;

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

	public String getSegmentation_id() {
		return segmentation_id;
	}

	public void setSegmentation_id(String segmentation_id) {
		this.segmentation_id = segmentation_id;
	}

	public String getCell_name() {
		return cell_name;
	}

	public void setCell_name(String cell_name) {
		this.cell_name = cell_name;
	}

	public String getCell_id() {
		return cell_id;
	}

	public void setCell_id(String cell_id) {
		this.cell_id = cell_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	

	
	
	

}

