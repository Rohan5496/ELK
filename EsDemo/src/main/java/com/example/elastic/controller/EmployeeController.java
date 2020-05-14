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

import com.example.elastic.Dao.EmployeeDao;
import com.example.elastic.model.Employee;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeDao empDao;

	@GetMapping("/all")
	public List<Employee> getAll(){
		return empDao.getAllEmployees();
	}

	@GetMapping("/search/{field}/{value}")
	public List<Employee> getByName(@PathVariable String field,@PathVariable String value) {
		return empDao.getEmployeeByName(field,value);
	}

	@PostMapping("/new")
	public Employee addNewEmployee(@RequestBody Employee e) {
		Employee addNewEmployee = empDao.addNewEmployeeViaEsTemplate(e);
		return addNewEmployee;
	}

	@PostMapping("/create")
	public String create(@RequestBody Employee e){
		return empDao.createNewEmployeeViaRest(e);
	}

	@GetMapping("/get/{id}")
	public Employee get(@PathVariable String id) throws IOException {
		return empDao.getEmployeeById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		empDao.deleteEmployee(id);
	}


}
