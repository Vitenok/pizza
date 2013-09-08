package com.iti.pizza.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.iti.pizza.entity.Ingredient;
import com.iti.pizza.entity.Pizza;
import com.iti.pizza.entity.PizzaOrder;
import com.iti.pizza.entity.PizzaTag;
import com.iti.pizza.services.IngredientService;
import com.iti.pizza.services.OrderService;
import com.iti.pizza.services.PizzaService;
import com.iti.pizza.services.PizzaTagService;

@Path("/client")
public class ClientController {

	private IngredientService ingredientService = new IngredientService();
	private PizzaService pizzaService = new PizzaService();
	private OrderService orderService = new OrderService();
	private PizzaTagService pizzaTagService = new PizzaTagService();

	// all templates
	@GET
	@Path("/templates/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pizza> getAllPizzas() {
		return pizzaService.getAllTemplates();
	}

	// all ingredients
	@GET
	@Path("/ingredients/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> getAllIngredients() throws JsonGenerationException, JsonMappingException, IOException {
		List<Ingredient> allIngredients = ingredientService.getAllIngredients();
//		System.out.println(new ObjectMapper().writeValueAsString(allIngredients));
		return allIngredients;
	}

	// all pizza tags
	@GET
	@Path("/tags/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PizzaTag> getAllPizzaTags() {
		return pizzaTagService.getAllTags();
	}

	// make order
	@POST
	@Path("/placeOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int addAdmin(PizzaOrder order) {
		return orderService.addOrder(order);
	}
}
