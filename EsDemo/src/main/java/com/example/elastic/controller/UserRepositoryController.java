package com.example.elastic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic.model.User;
import com.example.elastic.repository.UserRepository;

@RestController
public class UserRepositoryController {


	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/abc")
	public String entry() {
		return "I am Runningggg";
	}

	@PostMapping("/createUser")
	public int saveUser(@RequestBody List<User> users) {
		userRepository.saveAll(users);
		return users.size();
	}

	@GetMapping("/getAll")
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}


	@GetMapping("/name/{text}")
	public List<User> getUsersByName(@PathVariable String text){
		return userRepository.findByName(text);
	}


}
