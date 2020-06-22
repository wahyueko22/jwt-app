package com.token.object.response;

public class DataResponse {
	private int page;
	private int totalPerPage;
	private int totalAllData;
	private Object data;
		
	public DataResponse(int page, int totalPerPage,int totalAllData, Object data) {
		super();
		this.page = page;
		this.totalPerPage = totalPerPage;
		this.totalAllData = totalAllData;
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPerPage() {
		return totalPerPage;
	}
	public void setTotalPerPage(int totalPerPage) {
		this.totalPerPage = totalPerPage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public int getTotalAllData() {
		return totalAllData;
	}

	public void setTotalAllData(int totalAllData) {
		this.totalAllData = totalAllData;
	}
	
	
	
}
