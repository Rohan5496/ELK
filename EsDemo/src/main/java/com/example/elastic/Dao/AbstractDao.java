package com.example.elastic.Dao;

import java.util.List;

public interface AbstractDao<T> {
	
	List<T> getAllT();
	
	T getTById(String id);
	
	List<T> getTBySearchCriteria(String field, String value);
	
	T addNewTViaEsTemplate(T t);
	
	String createNewTUsingRest(T t , String id);
	
	void deleteT(String id);

	
}
