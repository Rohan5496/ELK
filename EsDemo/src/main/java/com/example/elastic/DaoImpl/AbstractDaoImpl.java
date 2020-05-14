package com.example.elastic.DaoImpl;

import java.io.IOException;
import java.util.List;

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
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.example.elastic.Dao.AbstractDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractDaoImpl<T> implements AbstractDao<T> {


	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ElasticsearchTemplate esTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	private Class<T> entityClass;
	private String indexName;
	private String indexType;

	public AbstractDaoImpl(Class<T> entityClass , String indexName , String indexType) {
		this.entityClass = entityClass;
		this.indexName = indexName;
		this.indexType = indexType;
	}

	@Override
	public T getTById(String id) {
		GetRequest getRequest = new GetRequest(indexName,indexType,id);
		GetResponse getResponse = null;
		try {
			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (T) objectMapper.convertValue(getResponse.getSource() , entityClass);
	}

	@Override
	public List<T> getTBySearchCriteria(String field, String value) {
		return (List<T>) esTemplate.queryForList(search(field,value), entityClass);
	}

	@Override
	public void deleteT(String id) {

		DeleteQuery query = new DeleteQuery();
		query.setIndex(indexName);
		query.setType(indexType);
		query.setQuery(QueryBuilders.matchQuery("id",id));

		esTemplate.delete(query);
		esTemplate.refresh(indexName);

	}

	@Override
	public List<T> getAllT() {

		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchAllQuery()).build();

		return (List<T>) esTemplate.queryForList(searchQuery, entityClass);
	}

	@Override
	public T addNewTViaEsTemplate(T t) {
		IndexQuery query = new IndexQuery();
		query.setIndexName(indexName);
		query.setType(indexType);
		query.setObject(t);

		esTemplate.index(query);
		esTemplate.refresh(indexName);
		return t;
	}

	@Override
	public String createNewTUsingRest(T t , String id) {
		IndexRequest indexRequest = null;
		IndexResponse response = null;
		try {
			indexRequest = new IndexRequest(indexName,indexType , id)
					.source(objectMapper.writeValueAsString(t),XContentType.JSON);
			response = client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	catch (IOException e) {
			e.printStackTrace();
		}

		return response.getResult().toString();
	}


	public SearchQuery search(String field, String searchCriteria) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery(field, searchCriteria)).build();

		return searchQuery;
	}

}
