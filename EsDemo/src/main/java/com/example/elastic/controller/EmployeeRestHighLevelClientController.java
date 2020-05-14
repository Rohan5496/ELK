package com.example.elastic.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/erest")
public class EmployeeRestHighLevelClientController {

	private final String indexName = "employeeindex";
	
	private final String indexType = "employee";
	
	@Autowired
	private ElasticsearchTemplate esTemplate;

	@Autowired
	private RestHighLevelClient client ;
	
	public String convertObjectToJsonString(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	@PostMapping("/create")
	public String create(@RequestBody Employee employee) throws IOException {
		
		IndexRequest indexRequest = new IndexRequest(indexName, indexType,employee.getId())
									.source(convertObjectToJsonString(employee),XContentType.JSON);	

		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

		return indexResponse.getResult().toString();

	} 
	
	@PostMapping("/new")
	public Employee addNewUser(@RequestBody Employee employee) {
		IndexQuery query = new IndexQuery();
		query.setIndexName("employeeindex");
		query.setType("employee");
		query.setObject(employee);
		
		esTemplate.index(query);
		esTemplate.refresh("employeeindex");
		return employee;
	}



	@GetMapping("/get/{id}")
	public Map<String,Object> get(@PathVariable String id) throws IOException {
		GetRequest getRequest = new GetRequest(indexName, indexType, id);

		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

		System.out.println(getResponse.getSource());
		return getResponse.getSource();

	}
	
	@GetMapping("/get/{field}")
	public List<Employee> findByNameUsingEsTemplate(@PathVariable String field){
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery("name",field)).build();

		List<Employee> employees = esTemplate.queryForList(searchQuery, Employee.class);

		return employees;
	}

	@GetMapping("/getall")
	public List<Employee> findByNameUsingEsTemplate(){
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchAllQuery()).build();

		List<Employee> employees = esTemplate.queryForList(searchQuery, Employee.class);

		return employees;
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable String id) throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest(indexName, indexType, id);

		DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		return deleteResponse.getResult().toString();
	}
	
//	@GetMapping("/getall")
//	public List<Employee> getAll() throws IOException {
//		SearchRequest searchRequest = new SearchRequest("employeeindex");
//		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//		List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());
//		List<Employee> results = new ArrayList<>();
//		searchHits.forEach(
//				hit -> results.add(JSON.parseObject(hit.getSourceAsString(), Employee.class)));
//
//		return results;
//	}


}
