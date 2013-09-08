package com.iti.pizza.services;

import java.util.List;

import com.iti.pizza.dao.PizzaTagDAO;
import com.iti.pizza.entity.PizzaTag;

public class PizzaTagService {
	PizzaTagDAO pizzaTagDAO = new PizzaTagDAO();

	public List<PizzaTag> getAllTags() {
		pizzaTagDAO.beginTransaction();
		List<PizzaTag> allTags = pizzaTagDAO.findAll();
		pizzaTagDAO.closeTransaction();
		return allTags;
	}

	public PizzaTag findTagById(int id) {
		pizzaTagDAO.beginTransaction();
		PizzaTag tag = pizzaTagDAO.find(id);
		pizzaTagDAO.closeTransaction();
		return tag;
	}

	public PizzaTag addPizzaTag(PizzaTag pizzaTag) {
		pizzaTagDAO.beginTransaction();
		pizzaTagDAO.save(pizzaTag);
		pizzaTagDAO.commitAndCloseTransaction();
		return pizzaTag;
	}

	public PizzaTag updatePizzaTag(PizzaTag pizzaTag) {
		pizzaTagDAO.beginTransaction();
		PizzaTag pizzaTagToUpdate = pizzaTagDAO.find(pizzaTag.getId());
		if (pizzaTagToUpdate != null) {
			pizzaTagToUpdate.setName(pizzaTag.getName());
			pizzaTagToUpdate.getPizzas();
			pizzaTagDAO.update(pizzaTagToUpdate);
			pizzaTagDAO.commitAndCloseTransaction();
			return pizzaTagToUpdate;
		} else {
			pizzaTagDAO.closeTransaction();
			return null;
		}
	}
/**public Admin updateAdmin(Admin admin) {
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
	}**/
	public boolean deletePizzaTag(int id) {
		pizzaTagDAO.beginTransaction();
		PizzaTag pizzaTagToDelete = pizzaTagDAO.find(id);
		if (pizzaTagToDelete != null) {
			pizzaTagDAO.delete(pizzaTagToDelete);
			pizzaTagDAO.commitAndCloseTransaction();
			return true;
		} else {
			pizzaTagDAO.commitAndCloseTransaction();
			return false;
		}
	}

	public void closeEMF() {
		pizzaTagDAO.closeEntityManagerFactory();
	}
}
