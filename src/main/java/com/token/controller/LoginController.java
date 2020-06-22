package com.token.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.token.object.BaseResponseDTO;
import com.token.object.request.LoginRequestDTO;
import com.token.object.response.LoginResponse;
import com.token.service.LoginService;
import com.token.util.Constant;
import com.token.util.TokenProviderUtil;

@RestController
@CrossOrigin
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	private LoginService loginService;
	private TokenProviderUtil tokenProviderUtil;

	public LoginController(LoginService loginService, TokenProviderUtil tokenProviderUtil) {
		this.loginService = loginService;
		this.tokenProviderUtil = tokenProviderUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(LoginRequestDTO loginRequestDTO) throws Exception {
		logger.info("username = {}", loginRequestDTO.getUserName());
		LoginResponse loginResponse = loginService.login(loginRequestDTO);
		BaseResponseDTO baseDto = new BaseResponseDTO(Constant.RESPONSE_STRING_SUCCESS, null,
				Constant.RESPONSE_SUCCESS_CODE, Constant.RESPONSE_SUCCESS_MESSAGE, loginResponse);
		return new ResponseEntity<Object>(baseDto, HttpStatus.OK);
	}

	@PostMapping("/extracts")
	public ResponseEntity<Object> extractToken(HttpServletRequest request) throws Exception {
		String requestTokenHeader = request.getHeader("Authorization");
		String token = requestTokenHeader.substring(7);
		logger.info("token = {}", token);
		LoginResponse loginResponse = loginService.extractToken(token);
		BaseResponseDTO baseDto = new BaseResponseDTO(Constant.RESPONSE_STRING_SUCCESS, null,
				Constant.RESPONSE_SUCCESS_CODE, Constant.RESPONSE_SUCCESS_MESSAGE, loginResponse);
		return new ResponseEntity<Object>(baseDto, HttpStatus.OK);
	}

	@GetMapping("/refreshtoken")
	public ResponseEntity<Object> refreshToken(@RequestHeader("Authorization") String refreshToken) throws Exception {
		LoginResponse loginResponse = loginService.refreshToken(refreshToken);
		BaseResponseDTO baseDto = new BaseResponseDTO(Constant.RESPONSE_STRING_SUCCESS, null,
				Constant.RESPONSE_SUCCESS_CODE, Constant.RESPONSE_SUCCESS_MESSAGE, loginResponse);
		return new ResponseEntity<Object>(baseDto, HttpStatus.OK);

	}
}
