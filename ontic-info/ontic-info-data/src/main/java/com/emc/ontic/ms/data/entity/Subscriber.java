package com.emc.ontic.ms.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the subscriber database table.
 * 
 */
@Entity
@NamedQuery(name="Subscriber.findAll", query="SELECT s FROM Subscriber s")
@Table(name = "subscriber", schema="ontic")
public class Subscriber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="subscriber_id_seq")
	@SequenceGenerator(name="subscriber_id_seq", sequenceName="ontic.subscriber_id_seq", allocationSize=1, schema="ontic")
	private Integer id;

	@Column(name="url")
	private String url;

	public Subscriber() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}