package com.token.object;


public class BaseResponseDTO{
	//response code
    protected String status;
    
    protected String errCode;
    
    //code sendiri
    protected String code;
    //message
    protected String message;
    
    //
    protected Object response;

    public BaseResponseDTO(){}
    
	public BaseResponseDTO(String status, String errCode, String code, String message, Object response) {
		super();
		this.status = status;
		this.errCode = errCode;
		this.code = code;
		this.message = message;
		this.response = response;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getErrCode() {
		return errCode;
	}







	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}







	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	       
    
}
