package com.token.object.response;

import java.util.List;

public class LoginResponse {
	private String token;
	private String refreshToken;
	private String userId;
	private String userName;
	private Object roles;
	
	public LoginResponse(String token,String refreshToken, String userId, String userName, Object roles) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.userId = userId;
		this.userName = userName;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Object getRoles() {
		return roles;
	}

	public void setRoles(Object roles) {
		this.roles = roles;
	}		
}
