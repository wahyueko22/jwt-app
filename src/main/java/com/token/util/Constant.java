package com.token.util;

public class Constant {
	// utils
	public static final String defaultFormatDt = "yyyy-MM-dd";
	public static final String defaultFormatTimeStamp = "yyyy-MM-dd HH:mm";
	public static final String STRING_SAP_SUCCESS_CODE = "000";
	
	public static final String RESPONSE_STRING_ERROR = "error";
	public static final String RESPONSE_STRING_SUCCESS = "success";
	public static final String RESPONSE_SUCCESS_CODE = "SUCC-00001";
	public static final String RESPONSE_SUCCESS_MESSAGE = "Data Success";
	
	
	public static enum Error{
		ERROR_DATA_500("ERR-00000", "Oops something when wrong"),
		ERROR_DATA_ALREADY_EXIST("ERR-00001", "Data Already Exist"),
	    ERROR_DATA_NOT_FOUND("ERR-00002", "Data Not Found"),
		ERROR_HTTP_TYPE_NOT_YET_REGISTERED("ERR-00003", "HTTP type is not registered"),
		ERROR_APP_TYPE_NOT_YET_REGISTERED("ERR-00004", "App type is not registered");
		
		private String code;
	    private String errorMessage;
	        
		private Error(String code, String errorMessage) {
			this.code = code;
			this.errorMessage = errorMessage;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	    
	}
}
