package com.token.object;

import java.util.Set;

public class UserData {
	private String userId;
	private Set<String> roles;
	
	public UserData(String userId, Set<String> roles) {
		this.userId = userId;
		this.roles = roles;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}
