package com.example.elastic.Dao;

import java.util.List;

import com.example.elastic.model.Employee;


public interface EmployeeDao extends AbstractDao<Employee> {

	List<Employee> getAllEmployees();

	Employee getEmployeeById(String id);

	List<Employee> getEmployeeByName(String field , String value);

	Employee addNewEmployeeViaEsTemplate(Employee e);

	String createNewEmployeeViaRest(Employee e);

	void deleteEmployee(String id);
}
