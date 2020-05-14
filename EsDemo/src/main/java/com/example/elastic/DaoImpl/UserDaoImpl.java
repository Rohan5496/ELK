package com.example.elastic.DaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.elastic.Dao.UserDao;
import com.example.elastic.model.User;

//@Repository
//public class UserDaoImpl implements UserDao {
//	
//	
//	private final String indexName = "userindex";
//	
//	private final String indexType = "user";
//	
//	@Autowired
//	private ElasticsearchTemplate esTemplate;
//
//	@Autowired
//	private RestHighLevelClient client;
//	
//	ObjectMapper objectMapper = new ObjectMapper();
//
//	/**
//	 * Return list of Users.
//	 */
//	@Override
//	public List<User> getAllUsers() {
//		SearchQuery getAllQuery = new NativeSearchQueryBuilder()
//				.withQuery(QueryBuilders.matchAllQuery()).build();
//		
//		return esTemplate.queryForList(getAllQuery, User.class);
//	}
//	
//
//	/**
//	 * Getting User via id.
//	 */
//	@Override
//	public User getUserById(String id) {
//		
//		GetRequest getRequest = new GetRequest(indexName,indexType,id);
//		GetResponse getResponse = null;
//		try {
//			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		User user = objectMapper.convertValue(getResponse.getSource(),User.class);
//		return user;
//	}
//	
//	
//	
//	/**
//	 * Searching User in Elastic Search cluster using any criteria or field except id 
//	 */
//	@Override
//	public List<User> getUserBySearchCriteria(String searchCriteria) {
//
//		return esTemplate.queryForList(search("name",searchCriteria), User.class);
//	}
//	
//	/**
//	 *  Creating User via Elastic Search Template.
//	 */
//	@Override
//	public User addNewUserViaEsTemplate(User user) {
//		IndexQuery query = new IndexQuery();
//		query.setIndexName(indexName);
//		query.setType(indexType);
//		query.setObject(user);
//		
//		esTemplate.index(query);
//		esTemplate.refresh(indexName);
//		return user;
//	}
//	
//	/**
//	 * Creating User object and via RestHighLevelClient
//	 * returning response created or updated.
//	 */
//	@Override
//	public String createNewUserViaRest(User user) {
//		
//		IndexRequest indexRequest = null;
//		IndexResponse response = null;
//		try {
//			indexRequest = new IndexRequest(indexName,indexType,user.getId())
//										.source(objectMapper.writeValueAsString(user),XContentType.JSON);
//			response = client.index(indexRequest, RequestOptions.DEFAULT);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}	catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return response.getResult().toString();
//								
//	}
//	
//	/**
//	 * Taking an id as input.
//	 * Deleting a user.
//	 * @param id
//	 */
//	@Override
//	public void  deleteUser(String id) {
//		
//		DeleteQuery query = new DeleteQuery();
//		query.setIndex(indexName);
//		query.setType(indexType);
//		query.setQuery(QueryBuilders.matchQuery("id",id));
//		
//		esTemplate.delete(query);
//		esTemplate.refresh(indexName);
//		
//	}
//	
//	public SearchQuery search(String field, String searchCriteria) {
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withQuery(QueryBuilders.matchQuery(field, searchCriteria)).build();
//		
//		return searchQuery;
//	}
//
//
//}


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