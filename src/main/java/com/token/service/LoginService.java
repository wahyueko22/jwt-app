package com.token.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.token.entity.RefreshToken;
import com.token.entity.Role;
import com.token.entity.User;
import com.token.object.UserData;
import com.token.object.request.LoginRequestDTO;
import com.token.object.response.LoginResponse;
import com.token.repository.RefreshTokenRepository;
import com.token.repository.RoleRepository;
import com.token.repository.UserRepository;
import com.token.repository.UserRoleMappingRepository;
import com.token.util.Constant;
import com.token.util.CustomException;
import com.token.util.TokenProviderUtil;

import io.jsonwebtoken.Claims;

@Service
public class LoginService {
	private static Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	private UserRepository userRepository;
	private UserRoleMappingRepository userRoleMappingRepository;
	private RoleRepository roleRepository;
	private RefreshTokenRepository loginRepository;
	
	@Value("${token.validity.milisecond}")
	private long tokenValidityInMilliseconds;
	
	@Value("${refreshToken.validity.milisecond}")
	private long refreshTokenValidityInMilliseconds;
	
	public LoginService (UserRepository userRepository, RoleRepository roleRepository, UserRoleMappingRepository userRoleMappingRepository,
			RefreshTokenRepository loginRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userRoleMappingRepository = userRoleMappingRepository;
		this.loginRepository = loginRepository;
	}
	
	public LoginResponse login(LoginRequestDTO loginDto) throws CustomException {
		User user = userRepository.findUserByUsernameAndPassword(loginDto.getUserName(),loginDto.getPassword())
				.orElseThrow(() -> new CustomException("L0001", "User not registered"));
		List<Role> lsRole = roleRepository.findRoleByUserId(user.getId());
		if(lsRole == null || lsRole.size() ==0) {
			new CustomException("L0002", "role is not set yet");
		}
		Set<String> lsUserRole  = new HashSet<String>();
		for (Role role : lsRole) {
			lsUserRole.add(role.getRole());
		}
		
		Date tokenExpiredTime = new Date(System.currentTimeMillis() + this.tokenValidityInMilliseconds);
		Date refreshTokenExpiredTime = new Date(System.currentTimeMillis() + this.refreshTokenValidityInMilliseconds);
		String  token = TokenProviderUtil.createToken(user.getUserId(), user.getUserName(), lsUserRole, tokenExpiredTime);
		String refreshToken = UUID.randomUUID().toString();
		
		//save to refresh_token table
		RefreshToken refreshTokenEntity = new RefreshToken();
		refreshTokenEntity.setRefreshToken(refreshToken);
		refreshTokenEntity.setUserId(user.getUserId());
		refreshTokenEntity.setRoles(String.join(",", lsUserRole));		
		refreshTokenEntity.setExpiredTime(refreshTokenExpiredTime);
		refreshTokenEntity.setCreatedDate(new Date());
		refreshTokenEntity.setCreatedBy(user.getUserId());	
		refreshTokenEntity.setUsername(user.getUserName());
		loginRepository.save(refreshTokenEntity);
		logger.info("refresh-token has been saved");
		
		LoginResponse logResponse = new LoginResponse(token, refreshToken ,user.getUserId(), user.getUserName() , lsUserRole);
		return logResponse;
	}
		
	public LoginResponse refreshToken(String refreshToken) throws Exception{
		
		RefreshToken refreshTokenEntity = loginRepository.findByRefreshToken(refreshToken);	
		
		if (refreshTokenEntity.getExpiredTime().before(new Date())) {
			//throw new java.lang.RuntimeException("Refresh token expired!");
			throw new CustomException("ERR-00000", "Refresh token expired!");
		}
		
		Date tokenExpiredTime = new Date(System.currentTimeMillis() + this.tokenValidityInMilliseconds);		
		Set<String> setOfRoles = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(refreshTokenEntity.getRoles(), ",");
		
		while(st.hasMoreTokens())
			setOfRoles.add(st.nextToken());
		String newToken = TokenProviderUtil.createToken(refreshTokenEntity.getUserId(), refreshTokenEntity.getUsername(), setOfRoles, tokenExpiredTime);
		logger.info("Token Refreshed");		
		LoginResponse logResponse = new LoginResponse(newToken, refreshToken ,refreshTokenEntity.getUserId(), refreshTokenEntity.getUsername() , setOfRoles);
		return logResponse;
		
	}
	
}
