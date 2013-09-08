package com.iti.pizza.services;

import java.util.List;

import com.iti.pizza.dao.AdminDAO;
import com.iti.pizza.entity.Admin;

public class AdminService {

	AdminDAO adminDAO = new AdminDAO();

	public Admin findAdminById(int id) {
		adminDAO.beginTransaction();
		Admin admin = adminDAO.find(id);
		adminDAO.closeTransaction();
		return admin;
	}

	public List<Admin> getAllAdmins() {
		adminDAO.beginTransaction();
		List<Admin> admins = adminDAO.findAll();
		adminDAO.closeTransaction();
		return admins;
	}

	public Admin addAdmin(String name, String password) {
		adminDAO.beginTransaction();
		Admin admin = new Admin();
		admin.setName(name);
		admin.setPassword(password);
		adminDAO.save(admin);
		adminDAO.commitAndCloseTransaction();
		return admin;
	}

	public boolean deleteAdminById(int id) {
		adminDAO.beginTransaction();
		Admin adminToDelete = adminDAO.find(id);
		if (adminToDelete != null) {
			adminDAO.delete(adminToDelete);
			adminDAO.commitAndCloseTransaction();
			return true;
		} else {
			adminDAO.commitAndCloseTransaction();
			return false;
		}
	}

	public Admin updateAdmin(Admin admin) {
		adminDAO.beginTransaction();
		Admin adminToUpdate = adminDAO.find(admin.getId());
		if (adminToUpdate != null) {
			adminToUpdate.setName(admin.getName());
			adminToUpdate.setPassword(admin.getPassword());
			adminDAO.update(adminToUpdate);
			adminDAO.commitAndCloseTransaction();
			return adminToUpdate;
		} else {
			adminDAO.closeTransaction();
			return null;
		}
	}

	public void closeEMF() {
		adminDAO.closeEntityManagerFactory();
	}

	public Admin isValidLogin(String login, String password) {
		adminDAO.beginTransaction();

		Admin adminToFind = adminDAO.findAdminByName(login);
		;
		if (adminToFind != null && (password.equals(adminToFind.getPassword()))) {
			adminDAO.closeTransaction();
			return adminToFind;
		} else {
			adminDAO.closeTransaction();
			return null;
		}
	}

}
