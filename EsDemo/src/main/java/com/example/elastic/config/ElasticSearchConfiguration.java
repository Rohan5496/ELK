package com.example.elastic.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {

	@SuppressWarnings("resource")
	@Bean
	public Client client() {
		TransportClient client = null;
		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
		}catch(UnknownHostException e){
			e.printStackTrace();
		}

		return client;
	}


	@Bean
	public RestHighLevelClient restClient() {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
				new HttpHost("localhost", 9200, "http")));

		return client;
	}


}
