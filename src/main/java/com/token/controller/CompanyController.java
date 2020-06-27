package com.token.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.token.object.BaseResponseDTO;
import com.token.object.request.CompanyRequestDTO;
import com.token.object.request.LoginRequestDTO;
import com.token.object.response.LoginResponse;
import com.token.service.CompanyService;
import com.token.util.Constant;
import com.token.util.TokenProviderUtil;

@CrossOrigin
@RestController("/company")
public class CompanyController {
	private Logger logger = LoggerFactory.getLogger(CompanyController.class);
	private CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestHeader("Authorization") String authorizationHeader, CompanyRequestDTO company) throws Exception {
		TokenProviderUtil.getUserData(authorizationHeader);
		companyService.saveCompany(company);
		BaseResponseDTO baseDto = new BaseResponseDTO(Constant.RESPONSE_STRING_SUCCESS, null,
				Constant.RESPONSE_SUCCESS_CODE, Constant.RESPONSE_SUCCESS_MESSAGE,  Constant.RESPONSE_SUCCESS_MESSAGE);
		return new ResponseEntity<Object>(baseDto, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllCompany(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		TokenProviderUtil.getUserData(authorizationHeader);
		BaseResponseDTO baseDto = new BaseResponseDTO(Constant.RESPONSE_STRING_SUCCESS, null,
				Constant.RESPONSE_SUCCESS_CODE, Constant.RESPONSE_SUCCESS_MESSAGE,  companyService.getAllCompany());
		return new ResponseEntity<Object>(baseDto, HttpStatus.OK);
	}

}
