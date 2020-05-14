package com.example.elastic.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic.model.Employee;

@RestController
@RequestMapping("/emp")
public class EmployeeTransportController {


	@Autowired
	private Client client;

	@Autowired
	private ElasticsearchTemplate esTemplate;

	@PostMapping("/create")
	public String create(@RequestBody Employee employee) throws IOException {
		IndexResponse indexResponse = client.prepareIndex("employeeindex", "employee", employee.getId())
				.setSource(jsonBuilder()
						.startObject()
						.field("name",employee.getName())
						.field("age",employee.getAge())
						.field("designation",employee.getDesignation())
						.endObject()

						).get();

		return indexResponse.getResult().toString();

	}	

	@GetMapping("/get/{id}")
	public Map<String,Object> get(@PathVariable String id){
		GetResponse getResponse = client.prepareGet("employeeindex", "employee", id).get();

		return getResponse.getSource();
	}

	@PutMapping("/update/{id}")
	public String update(@PathVariable String id) throws IOException {
		UpdateRequest update = new UpdateRequest();
		update.index("employeeindex")
		.type("employee")
		.id(id)
		.doc(jsonBuilder()
				.startObject()
				.field("salary","30K")
				.endObject());

		try {
			UpdateResponse updateResponse = client.update(update).get();
			return updateResponse.status().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return "Exception occured";				

	}


	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable String id) {
		DeleteResponse response= client.prepareDelete("employeeindex", "employee", id).get();
		return response.getResult().toString();
	}


//	"Bad Method"
//	@GetMapping("/getall")
//	public List<Employee> getAll() {
//		SearchResponse searchResponse = client.prepareSearch("employeeindex").get();
//		List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());
//		List<Employee> results = new ArrayList<>();
//		searchHits.forEach(
//				hit -> results.add(JSON.parseObject(hit.getSourceAsString(), Employee.class)));
//
//		return results;
//	}

	@GetMapping("/getal")
	public List<Employee> getAllEmployeesViaTemplate() {
		SearchQuery getAllQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchAllQuery()).build();

		return esTemplate.queryForList(getAllQuery, Employee.class);
	}

	@GetMapping("/name/{field}")
	public List<Employee> findByName(@PathVariable String field) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery("name", field)).build();

		List<Employee> employees = esTemplate.queryForList(searchQuery, Employee.class);

		return employees;
	}

}
