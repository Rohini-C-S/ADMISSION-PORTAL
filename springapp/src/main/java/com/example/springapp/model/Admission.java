package com.example.springapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "admission")
public class Admission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private Student student;

	@Column(name = "status")
	private String status;

	@Column(name = "course")
	private String course;

	@Lob
	@Column(name = "requiredDocuments", columnDefinition = "LONGBLOB")
	private byte[] requiredDocuments;

	public Admission() {
	}

	public Admission(int id, Student student, String status, byte[] requiredDocuments, String course) {
		this.id = id;
		this.student = student;
		this.status = status;
		this.requiredDocuments = requiredDocuments;
		this.course = course;
	}

	// Getters and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public byte[] getRequiredDocuments() {
		return requiredDocuments;
	}

	public void setRequiredDocuments(byte[] requiredDocuments) {
		this.requiredDocuments = requiredDocuments;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
}
