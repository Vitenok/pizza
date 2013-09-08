package com.iti.pizza.utillity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.iti.pizza.entity.Ingredient;
import com.iti.pizza.entity.Pizza;
import com.iti.pizza.entity.PizzaOrder;
import com.iti.pizza.entity.PizzaTag;
import com.iti.pizza.entity.Pizza.Size;
import com.iti.pizza.entity.Pizza.Thickness;

public class SimplePizzaSerializer extends JsonSerializer<List<Pizza>> {

	public SimplePizzaSerializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void serialize(List<Pizza> pizzas, final JsonGenerator generator, final SerializerProvider provider) throws IOException, JsonProcessingException {
		final List<SimplePizza> simplePizzas = new ArrayList();
		for (Pizza pizza : pizzas) {
			// simplePizzas.add(new SimplePizza(pizza.getPizzaId(), pizza.getThickness(), pizza.getSize(), pizza.getIsTemplate(), pizza.getName(), pizza.getIngredients(), pizza.getTags(), pizza.getOrder()));
			simplePizzas.add(new SimplePizza(pizza.getPizzaId(), pizza.getThickness(), pizza.getSize(), pizza.getIsTemplate(), pizza.getName()));
		}
		generator.writeObject(simplePizzas);
	}

	static class SimplePizza {
		private int id;
		private Thickness thickness;
		private Size size;
		private boolean isTemplate;
		private String name;
		private List<Ingredient> ingredients;
		private List<PizzaTag> tags;
		private PizzaOrder order;

		public SimplePizza(int id, Thickness thickness, Size size, boolean isTemplate, String name, List<Ingredient> ingredients, List<PizzaTag> tags, PizzaOrder order) {
			this.id = id;
			this.thickness = thickness;
			this.size = size;
			this.isTemplate = isTemplate;
			this.name = name;
			this.ingredients = ingredients;
			this.tags = tags;
			this.order = order;
		}

		public SimplePizza(int id, Thickness thickness, Size size, boolean isTemplate, String name) {
			this.id = id;
			this.thickness = thickness;
			this.size = size;
			this.isTemplate = isTemplate;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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

		public boolean isTemplate() {
			return isTemplate;
		}

		public void setTemplate(boolean isTemplate) {
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
	}
}
