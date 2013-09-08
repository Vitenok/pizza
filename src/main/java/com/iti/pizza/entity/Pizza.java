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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="pizza")
@NamedQueries(value = { @NamedQuery(name = "Pizza.findPizzaByName", query = "select p from Pizza p where p.name = :name"), @NamedQuery(name = "Pizza.getAllPizzas", query = "select p from Pizza p where p.isTemplate = :isTemplate") })
public class Pizza implements Serializable {

	private static final long serialVersionUID = -1537645583409515543L;

	public static final String FIND_PIZZA_BY_NAME = "Pizza.findPizzaByName";
	public static final String GET_ALL_PIZZAS = "Pizza.getAllPizzas";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(nullable = false, name="thickness")
	@Enumerated(EnumType.STRING)
	private Thickness thickness;

	@Column(nullable = false, name="size")
	@Enumerated(EnumType.STRING)
	private Size size;

	@Column(nullable = false, name="is_template")
	private boolean isTemplate;

	@Column(name="name")
	private String name;

	@ManyToMany
	private List<Ingredient> ingredients;

	@ManyToMany
	private List<PizzaTag> tags;

	@ManyToOne
	private PizzaOrder order;

	public Pizza() {
		super();
	}

	public Pizza(int id, Thickness thickness, Size size, boolean isTemplate, String name, List<Ingredient> ingredients, List<PizzaTag> tags) {
		this.id = id;
		this.thickness = thickness;
		this.size = size;
		this.isTemplate = true;
		this.name = name;
		this.ingredients = ingredients;
		this.tags = tags;
	}

	public Pizza(Thickness thickness, Size size, String name, List<Ingredient> ingredients, List<PizzaTag> tags) {
		this.thickness = thickness;
		this.size = size;
		this.isTemplate = true;
		this.name = name;
		this.ingredients = ingredients;
		this.tags = tags;
	}

	public int getPizzaId() {
		return id;
	}

	public void setPizzaId(int pizzaId) {
		this.id = pizzaId;
	}

	public Thickness getThickness() {
		return thickness;
	}

	public void setThickness(Thickness thickness) {
		this.thickness = thickness;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public boolean getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<PizzaTag> getTags() {
		return tags;
	}

	public void setTags(List<PizzaTag> tags) {
		this.tags = tags;
	}

	public PizzaOrder getOrder() {
		return order;
	}

	public void setOrder(PizzaOrder order) {
		this.order = order;
	}

	public enum Thickness {
		THIN, THICK
	};

	public enum Size {
		SMALL(1), MEDIUM(1.5), BIG(2);

		private double multiplier;

		private Size(double multiplier) {
			this.multiplier = multiplier;
		}

		public double getMultiplier() {
			return multiplier;
		}
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
		Pizza other = (Pizza) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String toString() {
		return "Pizza [id=" + id + ", thickness=" + thickness + ", size=" + size + ", isTemplate=" + isTemplate + ", name=" + name + ", ingredients=" + ingredients + ", tags=" + tags + ", order=" + order + "]";
	}

}
