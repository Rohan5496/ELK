package com.example.elastic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "userindex", type = "user")
public class User {
	
	@Id
    private String id;
    private String name;
	
	
	public User(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public User() {
		super();
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

  }