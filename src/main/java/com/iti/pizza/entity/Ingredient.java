package com.iti.pizza.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="ingredient")
@NamedQueries(value = { @NamedQuery(name = "Ingredient.findIngredientByName", query = "select i from Ingredient i where i.name = :name") })
public class Ingredient implements Serializable {

	private static final long serialVersionUID = -6451537553728619345L;

	public static final String FIND_INGREDIENT_BY_NAME = "Ingredient.findIngredientByName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(unique = true, nullable = false, name="name")
	private String name;

	@Column(nullable = false, name="price")
	private double price;

	@Column(nullable = false, name="ingredient_type")
	@Enumerated(EnumType.STRING)
	private IngredientType ingredientType;

	@ManyToMany(mappedBy="ingredients")
    @JsonIgnore
	//@JsonSerialize(using = SimplePizzaSerializer.class)
	private List<Pizza> pizzas;

	public Ingredient() {
		super();
	}

	public Ingredient(int id, String name, double price, IngredientType type) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.ingredientType = type;
	}
	
	public Ingredient( String name, double price, IngredientType type) {
		this.name = name;
		this.price = price;
		this.ingredientType = type;
	}

	public int getIngredientId() {
		return id;
	}

	public void setIngredientId(int ingredientId) {
		this.id = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	public IngredientType getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(IngredientType ingredientType) {
		this.ingredientType = ingredientType;
	}

	public enum IngredientType {
		CHEESE, MEAT, SEAFOOD, VEGETABLES, SPICES
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", price=" + price + ", ingredientType=" + ingredientType + "]";
	}

}
