package com.example.elastic.Dao;

import java.util.List;

import com.example.elastic.model.User;


public interface UserDao extends AbstractDao<User> {

	List<User> getAllUsers();

	User getUserById(String id);

	List<User> getUserByName(String field , String value);

	User addNewUserViaEsTemplate(User user);

	String createNewUserViaRest(User user);

	void deleteUser(String id);
}
