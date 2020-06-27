package com.token.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.token.object.request.*;
import java.util.*;

@Service
public class CompanyService {
	private static Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	private static List<CompanyRequestDTO> lsCompany;
	static {
		lsCompany = new ArrayList<CompanyRequestDTO>();
		lsCompany.add(new CompanyRequestDTO("Goole", "0899999", "Silicon valley"));
	}
	
	public void saveCompany(CompanyRequestDTO company) {
		lsCompany.add(company);
	}
	
	public List<CompanyRequestDTO> getAllCompany() {
		return lsCompany;
	}
}
