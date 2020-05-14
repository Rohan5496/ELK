package com.example.elastic.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic.Dao.UserDao;
import com.example.elastic.model.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	@GetMapping("/all")
	public List<User> getAll(){
		return userDao.getAllUsers();
	}

	@GetMapping("/search/{field}/{value}")
	public List<User> getByName(@PathVariable String field,@PathVariable String value) {

		return userDao.getUserByName(field,value);
	}

	@PostMapping("/new")
	public User addNewUser(@RequestBody User user) {
		User addNewUser = userDao.addNewUserViaEsTemplate(user);
		return addNewUser;
	}


	@PostMapping("/create")
	public String create(@RequestBody User user){
		return userDao.createNewUserViaRest(user);
	}

	@GetMapping("/get/{id}")
	public User get(@PathVariable String id) throws IOException {
		return userDao.getUserById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		userDao.deleteUser(id);
	}


}
