package com.iti.pizza.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iti.pizza.entity.Pizza;

public class PizzaDAO extends GenericDAO<Pizza> {

	private static final long serialVersionUID = 2796385326082413798L;

	public PizzaDAO() {
		super(Pizza.class);
	}

	public Pizza findTemplateByName(String templateName) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", templateName);
		return super.findOneResult(Pizza.FIND_PIZZA_BY_NAME, parameters);
	}

	public List<Pizza> getAllPizzas(boolean isTemplate) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("isTemplate", isTemplate);
		return super.findResultList(Pizza.GET_ALL_PIZZAS, parameters);
	}
	
}
