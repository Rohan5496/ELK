package com.example.elastic.DaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.elastic.Dao.UserDao;
import com.example.elastic.model.User;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

	private final static String indexName = "userindex";
	
	private final static String indexType = "user";
	
	public UserDaoImpl() {
		super(User.class , indexName , indexType);
	}
	
	@Override
	public List<User> getAllUsers() {
		return getAllT();	
	}
	
	@Override
	public User getUserById(String id) {
		return getTById(id);
	}
	
	@Override
	public List<User> getUserByName(String field ,String value) {
		return getTBySearchCriteria(field,value);
	}
	
	@Override
	public User addNewUserViaEsTemplate(User user) {
		return addNewTViaEsTemplate(user);
	}
	
	@Override
	public String createNewUserViaRest(User user) {
		return createNewTUsingRest(user,user.getId());
	}
	
	@Override
	public void  deleteUser(String id) {
		deleteT(id);
	}
	
}