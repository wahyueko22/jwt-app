package com.token.util;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.token.object.UserData;


public class JwtUtil {
	private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
			
	public static String dateTimeToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.defaultFormatTimeStamp);
		return dateFormat.format(date);
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.defaultFormatDt);
		return dateFormat.format(date);
	}
	
	/* mengubah object to string json
	 * @param obj adalah sembarang objek turunan dari Object
	 */
	public static String printObjectJson(Object jsonObject) {
		if(jsonObject == null)
			return "";
		ObjectMapper Obj = new ObjectMapper(); 
		String jsonStr = "";
        try { 
            // get Oraganisation object as a json string 
        	jsonStr = Obj.writeValueAsString(jsonObject); 
        } 
        catch (Exception e) { 
          //
        } 
        return jsonStr;
	}
	
	public static Map<String, Object> convertReqToMapOfObject(String req) {
		Map<String, Object> newMapValue = new HashMap<String, Object>();
		JSONObject requestObj = new JSONObject(req);
		for (String key : requestObj.keySet()) {
			// based on you key types
			String keyStr = key;
			Object objectValue = requestObj.get(keyStr);
			logger.info("req key = " + keyStr + " and req value = " + objectValue);
			newMapValue.put(keyStr, objectValue);
		}
		return newMapValue;
	}
		
	/**
	 * Gets the request body from the request.
	 *
	 * @param request the request
	 * @return the request body
	 */
	public static String getRequestBody(final HttpServletRequest request) {
	    final StringBuilder builder = new StringBuilder();
	    try (final BufferedReader reader = request.getReader()) {

	        if (reader == null) {
	            logger.debug("Request body could not be read because it's empty.");
	            return null;
	        }
	        String line;
	        while ((line = reader.readLine()) != null) {
	            builder.append(line);
	        }
	        return builder.toString();
	    } catch (final Exception e) {
	        logger.trace("Could not obtain the saml request body from the http request", e);
	        return null;
	    }
	}
	
	//convert string to json
	public static JSONObject convertStringToJSon(String strJson) {
		JSONObject requestObj;
		if(StringUtils.isEmpty(strJson))
			requestObj = new JSONObject();
		else requestObj = new JSONObject(strJson);
		return requestObj;
	}

	
	public static UserData getUserData(HttpServletRequest request) throws CustomException {
		if(StringUtils.isEmpty(request.getHeader("userId")))
			throw new CustomException("L0003", "Invalid user");
		if(StringUtils.isEmpty(request.getHeader("roles")) || "[]".equals(request.getHeader("roles")))
			throw new CustomException("L0002", "Roles is not set yet");
		logger.info("userId : {}", request.getHeader("userId"));
		logger.info("Roles : {}", request.getHeader("roles"));
		String[] arrRole = request.getHeader("roles").replace("[", "").replace("]", "").split(",");
		Set<String> setRole = new HashSet<>(Arrays.asList(arrRole));
		UserData userData = new UserData(request.getHeader("userId"), setRole);
		return userData;
	}
	
}
