package com.iti.pizza.dao;

import java.util.HashMap;
import java.util.Map;

import com.iti.pizza.entity.Admin;

public class AdminDAO extends GenericDAO<Admin> {

	private static final long serialVersionUID = -2833931742904277458L;

	public AdminDAO() {
		super(Admin.class);
	}
	public Admin findAdminByName(String name) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		return super.findOneResult(Admin.FIND_ADMIN_BY_NAME, parameters);
	}
	
}
