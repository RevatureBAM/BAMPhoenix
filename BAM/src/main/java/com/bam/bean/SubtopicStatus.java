package com.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "Subtopic_Status")
public class SubtopicStatus {

	@Id
	@Column(name = "Status_ID")
	@SequenceGenerator(name = "Status_ID_SEQ", sequenceName = "Status_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Status_ID_SEQ")
	private Integer id;

	@Column(name = "Status_Name")
	private String name;

	public SubtopicStatus() {
	}

	public SubtopicStatus(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicStatus(String name) {
		super();
		this.name = name;
	}//NOSONAR

	public Integer getId() {
		return id;
	}//NOSONAR

	public void setId(Integer id) {
		this.id = id;
	}//NOSONAR

	public String getName() {
		return name;
	}//NOSONAR

	public void setName(String name) {
		this.name = name;
	}//NOSONAR

	@Override
	public String toString() {
		return "SubtopicStatus [id=" + id + ", name=" + name + "]";//NOSONAR
	}

}
