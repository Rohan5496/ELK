package com.example.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.elastic.model.User;

@Repository
public interface UserRepository extends ElasticsearchRepository<User,String>{
	List<User> findByName(String text);
}
