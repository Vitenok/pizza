package com.iti.pizza.services;

import java.util.List;

import com.iti.pizza.dao.PizzaDAO;
import com.iti.pizza.entity.Pizza;

public class PizzaService {
	PizzaDAO pizzaDAO = new PizzaDAO();

	public Pizza findPizzaById(int id) {
		pizzaDAO.beginTransaction();
		Pizza pizza = pizzaDAO.find(id);
		pizzaDAO.closeTransaction();
		return pizza;
	}

	public Pizza findTemplateByName(String name) {
		pizzaDAO.beginTransaction();
		Pizza pizzaTemplate = pizzaDAO.findTemplateByName(name);
		pizzaDAO.closeTransaction();
		;
		return pizzaTemplate;
	}

	public List<Pizza> getAllTemplates() {
		pizzaDAO.beginTransaction();
		List<Pizza> pizzaTemplates = pizzaDAO.getAllPizzas(true);
		pizzaDAO.closeTransaction();
		return pizzaTemplates;
	}

	public List<Pizza> getAllTemplatesAndIngredients() {
		pizzaDAO.beginTransaction();
		List<Pizza> pizzaTemplates = pizzaDAO.getAllPizzas(true);
		pizzaDAO.closeTransaction();
		return pizzaTemplates;
	}

	public Pizza addPizza(Pizza pizza) {
		pizzaDAO.beginTransaction();
		pizza = pizzaDAO.update(pizza);
		pizzaDAO.commitAndCloseTransaction();
		return pizza;
	}

	public Pizza addPizzaTemplate(Pizza pizza) {
		pizza.setIsTemplate(true);
		return addPizza(pizza);
	}

	public boolean deleteTemplateById(int id) {
		pizzaDAO.beginTransaction();
		Pizza pizzaToDelete = pizzaDAO.find(id);
		if (pizzaToDelete.getIsTemplate()) {
			pizzaDAO.delete(pizzaToDelete);
			pizzaDAO.commitAndCloseTransaction();
			return true;
		} else {
			pizzaDAO.closeTransaction();
			return false;
		}
	}

	public Pizza updateTemplateById(Pizza template) {
		pizzaDAO.beginTransaction();
		Pizza templateToUpdate = pizzaDAO.find(template.getPizzaId());
		if (templateToUpdate.getIsTemplate()) {
			templateToUpdate.setThickness(template.getThickness());
			templateToUpdate.setSize(template.getSize());
			templateToUpdate.setName(template.getName());
			templateToUpdate.setIngredients(template.getIngredients());
			templateToUpdate.setTags(template.getTags());
			pizzaDAO.update(templateToUpdate);
			pizzaDAO.commitAndCloseTransaction();
			return templateToUpdate;
		} else {
			pizzaDAO.closeTransaction();
			pizzaDAO.commitAndCloseTransaction();
			return null;
		}
	}

	public void closeEMF() {
		pizzaDAO.closeEntityManagerFactory();
	}
}
