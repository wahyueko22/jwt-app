package com.token.object.request;

public class CompanyRequestDTO {
	private String companyName;
	private String companyPhone;
	private String companyAddress;
	
	public CompanyRequestDTO() {}
	
	public CompanyRequestDTO(String companyName, String companyPhone, String companyAddress) {
		this.companyName = companyName;
		this.companyPhone = companyPhone;
		this.companyAddress = companyAddress;	
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
}
