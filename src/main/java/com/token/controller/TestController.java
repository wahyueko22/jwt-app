package com.token.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.token.service.MockRestTemplate;
import com.token.util.JwtUtil;

//https://www.baeldung.com/java-base64-encode-and-decode
@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
		
	@Autowired
	private MockRestTemplate mockRestTemplate;
	
	@PostMapping("/response/**")
	public ResponseEntity<Object> post(HttpServletRequest request) {
		String req = JwtUtil.getRequestBody(request);
		Enumeration headerNames = request.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String key = (String) headerNames.nextElement();
	            String value = request.getHeader(key);
	            logger.info("header key = {}, value = {}", key,value);
	    }
		logger.info("request post test = {}", req);
		String val = "{\r\n" + 
				"   \"ErrorSchema\":{\r\n" + 
				"      \"ErrorCode\":\"ESB-00-000\",\r\n" + 
				"      \"ErrorMessage\":{\r\n" + 
				"         \"English\":\"Success\",\r\n" + 
				"         \"Indonesian\":\"Berhasil\"\r\n" + 
				"      }\r\n" + 
				"   },\r\n" + 
				"   \"OutputSchema\":{\r\n" + 
				"      \"CustomerID\":\"089503405311\",\r\n" + 
				"      \"CardNumber\":\"6019001088888801\",\r\n" + 
				"      \"OTPCode\":\"379079\",\r\n" + 
				"      \"ReferenceCode\":\"00102085\",\r\n" + 
				"      \"ExpiredParam\":\"3600\",\r\n" + 
				"      \"ExpiredDate\":\"2019-12-18 14:28:29\"\r\n" + 
				"   }\r\n" + 
				" }";
		return new ResponseEntity<Object>(val, HttpStatus.OK);
	}
	
	@GetMapping("/response/**")   
	public ResponseEntity<Object> get(HttpServletRequest request) {
		logger.info("request getQueryString test = {}", request.getQueryString());
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			logger.info("header key = {}, value = {}", key, value);
		}
		String val = "{\r\n" + 
				"   \"ErrorSchema\":{\r\n" + 
				"      \"ErrorCode\":\"ESB-00-000\",\r\n" + 
				"      \"ErrorMessage\":{\r\n" + 
				"         \"English\":\"Success\",\r\n" + 
				"         \"Indonesian\":\"Berhasil\"\r\n" + 
				"      }\r\n" + 
				"   },\r\n" + 
				"   \"OutputSchema\":{\r\n" + 
				"      \"CustomerID\":\"089503405311\",\r\n" + 
				"      \"CardNumber\":\"6019001088888801\",\r\n" + 
				"      \"OTPCode\":\"379079\",\r\n" + 
				"      \"ReferenceCode\":\"00102085\",\r\n" + 
				"      \"ExpiredParam\":\"3600\",\r\n" + 
				"      \"ExpiredDate\":\"2019-12-18 14:28:29\"\r\n" + 
				"   }\r\n" + 
				" }";
		return new ResponseEntity<Object>(val, HttpStatus.OK);
	}
	
	@PutMapping("/response/**")
	public ResponseEntity<Object> putData(HttpServletRequest request) {
		String req = JwtUtil.getRequestBody(request);
		logger.info("request put test = {}", req);
	
		return new ResponseEntity<Object>("ok", HttpStatus.OK);
	}
	
	@DeleteMapping("/response/{id}")
	public ResponseEntity<Object> deleteData(HttpServletRequest request, @PathVariable("id") Long id) {
		String req = JwtUtil.getRequestBody(request);
		logger.info("request delete  id = {}", id);
		
		logger.info("request delete getContextPath = {}", request.getContextPath());
	
		return new ResponseEntity<Object>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/nasi/**")   
	public ResponseEntity<Object> example(HttpServletRequest request) {
		String restOfTheUrl = (String) request.getAttribute(
		        HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		System.out.println(restOfTheUrl + " ==== " + request.getRequestURL().toString());		
		return new ResponseEntity<Object>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/ok/{id}")
	public ResponseEntity<Object>  callRest(@PathVariable("id") int id) {
		String res= "";
		Map<String,String> mapHeader = new HashMap<String,String>();
		mapHeader.put("Content-Type", "application/json");
		 if(id == 1) {
			 String reqDTO = "testPost";
			 res= mockRestTemplate.mockPost("http://localhost:8001/test/response/post", reqDTO, mapHeader, String.class);
		 }else if(id == 2) {
			 Map<String,Object> params = new HashMap<String, Object>();
			 params.put("param_1", "value_1");
			 res= mockRestTemplate.mockGet("http://localhost:8001/test/response/get", params,mapHeader, String.class);
		 }else if(id == 3) {
			 String reqDTO = "testPut";
			 res =mockRestTemplate.mockPut("http://localhost:8001/test/response/put", reqDTO, mapHeader, String.class);
		 }else if(id == 4) {
			 String reqDTO = "test delete";
			 res = mockRestTemplate.mockDelete("http://localhost:8001/test/response/1", reqDTO, mapHeader, String.class);
		 }
		 
		return  new ResponseEntity<Object>(res, HttpStatus.OK);
	}
}
