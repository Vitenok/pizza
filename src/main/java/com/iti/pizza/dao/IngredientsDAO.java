package com.iti.pizza.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iti.pizza.entity.Ingredient;

public class IngredientsDAO extends GenericDAO<Ingredient> {

	private static final long serialVersionUID = 3574927767683742947L;

	public IngredientsDAO() {
		super(Ingredient.class);
	}

	public Ingredient findIngredientByName(String name) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		return super.findOneResult(Ingredient.FIND_INGREDIENT_BY_NAME, parameters);
	}

}
