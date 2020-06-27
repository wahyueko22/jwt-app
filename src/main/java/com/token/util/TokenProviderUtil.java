package com.token.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.token.object.UserData;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Arrays;
//https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/security/jwt/TokenProvider.java
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class TokenProviderUtil {
	private static Logger logger = LoggerFactory.getLogger(TokenProviderUtil.class);
	
	private static final Key key = MacProvider.generateKey();

	public static String createToken(String userId, String userName, Set<String> lsRole, Date expiredTime) {
		return Jwts.builder().setId(userId).setSubject(userName).claim(Constant.AUTHORITIES_KEY, lsRole)
				.signWith(key, SignatureAlgorithm.HS512).setIssuedAt(new Date()).setExpiration(expiredTime).compact();
	}

	public static Claims getAuthentication(String token) throws Exception {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			logger.error(e.getMessage(), e);
			throw new CustomException("TOKEN-0001", "Token Expired");
		} catch (UnsupportedJwtException e) {
			logger.error(e.getMessage(), e);
			throw new CustomException("TOKEN-0002", "Unsupported Jwt");
		} catch (MalformedJwtException e) {
			logger.error(e.getMessage(), e);
			throw new CustomException("TOKEN-0003", "Malformed JWT");
		} catch (SignatureException e) {
			logger.error(e.getMessage(), e);
			throw new CustomException("TOKEN-0004", "JWT Signature does not match");
		}catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new CustomException("TOKEN-0005", "JWT token compact of handler are invalid");
		}
		return claims;
	}

	public static boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("Invalid JWT signature.");
			logger.trace("Invalid JWT signature trace: {}", e);
		} catch (ExpiredJwtException e) {
			logger.info("Expired JWT token.");
			logger.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {
			logger.info("Unsupported JWT token.");
			logger.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {
			logger.info("JWT token compact of handler are invalid.");
			logger.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return false;
	}
	
	public static UserData getUserData(String authorizationHeader) throws Exception {
		String token = authorizationHeader.substring(7);
		Claims claims = getAuthentication(token);
		logger.info("claims = {}", claims);
		if(StringUtils.isEmpty(claims.get(Constant.AUTHORITIES_KEY)) || "[]".equals(claims.get(Constant.AUTHORITIES_KEY))) {
			throw new CustomException("L0002", "Roles is not set yet");
		}
		String[] arrRole = claims.get(Constant.AUTHORITIES_KEY).toString().replace("[", "").replace("]", "").split(",");
		Set<String> setRole = new HashSet<>(Arrays.asList(arrRole));
		return new UserData(claims.getId(),setRole);
	}
}