package com.example.elastic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "employeeindex", type = "employee")
public class Employee {

	@Id
	private String id;
	private String name;
	private String age;
	private String designation;


	public Employee() {}

	public Employee(String id, String name, String age, String designation) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.designation = designation;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}


}
