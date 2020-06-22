package com.token.service;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.token.util.JwtUtil;

@Service
public class MockRestTemplate {
	private static Logger logger = LoggerFactory.getLogger(MockRestTemplate.class);
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	
	@Value("${rest.read.timeout}")
	private String readTimeOut;
	@Value("${rest.connection.timeout}")
	private String connectionTimeout;
	
	/* Manggil ke service API 
	 * url = url yang dituju misal local:8080/service/api
	 * httpMethod = misal POST, GET dll
	 * entity = berisi header
	 * T = kelas dari object response
	 */
	public <T> ResponseEntity<T> restTemplate(String url, HttpMethod httpMethod, HttpEntity<?> entity, Class<T> T) {
		logger.info("final URL : {}", url);
		Duration readTimeOutInMiliSecond = Duration.ofMillis(Integer.valueOf(readTimeOut));
		Duration writeTimeOutInMiliSecond = Duration.ofMillis(Integer.valueOf(connectionTimeout));
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(writeTimeOutInMiliSecond).setReadTimeout(readTimeOutInMiliSecond).build();
		ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, entity, T);
		return response;
	}
	
	public <T> T mockPost(String customUrl, Object reqDTO,Map<String,String> mapHeader,  Class<T> T) {
		logger.info("req online : {}", JwtUtil.printObjectJson(reqDTO));
		logger.info("custom url : {}", customUrl);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(customUrl);
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
        }
		HttpEntity<Object> request = null;
		if(reqDTO != null)
			request = new HttpEntity<Object>(reqDTO, headers);
		else request = new HttpEntity<Object>(headers);
		ResponseEntity<T> response = restTemplate(builder.toUriString(), HttpMethod.POST, request, T);
		return response.getBody();
	}
	
	/*
	 * param T adalah kelas response contoh Response.class
	*/
	public <T> T mockGet(String customUrl,Map<String,Object> params ,Map<String,String> mapHeader, Class<T> T) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(customUrl);
		
		//set parameter ke SAP jika params tidak kosong
		if(params != null)
			params.forEach((key,value) -> builder.queryParam(key, value)); 
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
        }
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<T> response = restTemplate(builder.toUriString(), HttpMethod.GET, request, T);
		return response.getBody();
	}
	
	//untuk put
	public <T> T mockPut(String customUrl, Object reqDTO, Map<String,String> mapHeader,  Class<T> T) {
		logger.info("req online : {}", JwtUtil.printObjectJson(reqDTO));
		logger.info("custom url : {}", customUrl);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(customUrl);
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
        }
		HttpEntity<Object> request = null;
		if(reqDTO != null)
			request = new HttpEntity<Object>(reqDTO, headers);
		else request = new HttpEntity<Object>(headers);
		ResponseEntity<T> response = restTemplate(builder.toUriString(), HttpMethod.PUT, request, T);
		return response.getBody();
	}
	
	// untuk delete
	public <T> T mockDelete(String customUrl, Object reqDTO, Map<String,String> mapHeader, Class<T> T) {
		logger.info("req online : {}", JwtUtil.printObjectJson(reqDTO));
		logger.info("custom url : {}", customUrl);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(customUrl);
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
        }
		HttpEntity<Object> request = null;
		if (reqDTO != null)
			request = new HttpEntity<Object>(reqDTO, headers);
		else
			request = new HttpEntity<Object>(headers);
		ResponseEntity<T> response = restTemplate(builder.toUriString(), HttpMethod.DELETE, request, T);
		return response.getBody();
	}
	
	public <T> T mockGeneral(String customUrl, Object reqDTO,Map<String,String> mapHeader, HttpMethod httpMethod,  Class<T> T) {
		logger.info("req online : {}", JwtUtil.printObjectJson(reqDTO));
		logger.info("custom url : {}", customUrl);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(customUrl);
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
        }
		HttpEntity<Object> request = null;
		if(reqDTO != null)
			request = new HttpEntity<Object>(reqDTO, headers);
		else request = new HttpEntity<Object>(headers);
		ResponseEntity<T> response = restTemplate(builder.toUriString(), httpMethod, request, T);
		return response.getBody();
	}
	
	
}
