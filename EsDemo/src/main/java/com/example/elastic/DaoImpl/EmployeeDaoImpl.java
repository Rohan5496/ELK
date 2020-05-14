package com.example.elastic.DaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.elastic.Dao.EmployeeDao;
import com.example.elastic.model.Employee;

@Repository
public class EmployeeDaoImpl extends AbstractDaoImpl<Employee> implements EmployeeDao {

	private static final String indexName = "employeeindex";
	
	private static final String indexType = "employee";
	
	public EmployeeDaoImpl() {
		super(Employee.class , indexName , indexType);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		return getAllT();	
	}
	
	@Override
	public Employee getEmployeeById(String id) {
		return getTById(id);
	}
	
	@Override
	public List<Employee> getEmployeeByName(String field ,String value) {
		return getTBySearchCriteria(field,value);
	}
	
	@Override
	public Employee addNewEmployeeViaEsTemplate(Employee e) {
		return addNewTViaEsTemplate(e);
	}
	
	@Override
	public String createNewEmployeeViaRest(Employee e) {
		return createNewTUsingRest(e,e.getId());
	}
	
	@Override
	public void deleteEmployee(String id) {
		deleteT(id);
	}
	
}