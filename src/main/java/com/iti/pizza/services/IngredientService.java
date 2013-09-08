package com.iti.pizza.services;

import java.util.List;

import com.iti.pizza.dao.IngredientsDAO;
import com.iti.pizza.entity.Ingredient;

public class IngredientService {
	IngredientsDAO ingredientsDAO = new IngredientsDAO();

	public Ingredient findIngredientById(int id) {
		ingredientsDAO.beginTransaction();
		Ingredient ingredient = ingredientsDAO.find(id);
		ingredientsDAO.closeTransaction();
		return ingredient;
	}

	public Ingredient findIngredientByName(String name) {
		ingredientsDAO.beginTransaction();
		Ingredient ingredient = ingredientsDAO.findIngredientByName(name);
		ingredientsDAO.closeTransaction();
		return ingredient;
	}

	public List<Ingredient> getAllIngredients() {
		ingredientsDAO.beginTransaction();
		List<Ingredient> ingredients = ingredientsDAO.findAll();
		ingredientsDAO.closeTransaction();
		return ingredients;
	}
	
	
	public Ingredient addIngredient(Ingredient ingredient) {
		ingredientsDAO.beginTransaction();
		ingredientsDAO.save(ingredient);
		ingredientsDAO.commitAndCloseTransaction();
		return ingredient;
	}
	
	public Ingredient updateIngredient(Ingredient ingredient) {
		ingredientsDAO.beginTransaction();
		Ingredient ingredientToUpdate = ingredientsDAO.find(ingredient.getIngredientId());
		if (ingredientToUpdate != null) {
			ingredientToUpdate.setName(ingredient.getName());
			ingredientToUpdate.setPrice(ingredient.getPrice());
			ingredientToUpdate.setIngredientType(ingredient.getIngredientType());
			ingredientsDAO.update(ingredientToUpdate);
			ingredientsDAO.commitAndCloseTransaction();
			return ingredientToUpdate;
		} else {
			ingredientsDAO.closeTransaction();
			return ingredientToUpdate;
		}
	}

	public boolean deleteIngredient(int id) {
		ingredientsDAO.beginTransaction();
		Ingredient ingredientToDelete = ingredientsDAO.find(id);
		if (ingredientToDelete != null) {
			ingredientsDAO.delete(ingredientToDelete);
			ingredientsDAO.commitAndCloseTransaction();
			return true;
		} else {
			ingredientsDAO.commitAndCloseTransaction();
			return false;
		}
	}

	public void closeEMF() {
		ingredientsDAO.closeEntityManagerFactory();
	}
}
