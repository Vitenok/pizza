package com.iti.pizza.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iti.pizza.entity.Admin;
import com.iti.pizza.entity.Ingredient;
import com.iti.pizza.entity.Pizza;
import com.iti.pizza.entity.PizzaTag;
import com.iti.pizza.services.AdminService;
import com.iti.pizza.services.IngredientService;
import com.iti.pizza.services.PizzaService;
import com.iti.pizza.services.PizzaTagService;

@Path("/admin")
public class AdminController {

	private AdminService adminService = new AdminService();
	private PizzaService pizzaService = new PizzaService();
	private IngredientService ingredientService = new IngredientService();
	private PizzaTagService pizzaTagService = new PizzaTagService();

	// Admins menagement
	@GET
	@Path("/admin/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Admin> getAllAdmins() {
		return adminService.getAllAdmins();
	}

	@POST
	@Path("/admin/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Admin addAdmin(Admin admin) {
		return adminService.addAdmin(admin.getName(), admin.getPassword());
	}

	@POST
	@Path("/admin/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Admin updateAdmin(Admin admin) {
		return adminService.updateAdmin(admin);
	}

	@GET
	@Path("/admin/delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public boolean deleteAdmin(@PathParam("id") int id) {
		return adminService.deleteAdminById(id);
	}

	// Templates management
	@GET
	@Path("/templates/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pizza> getAllPizzas() {
		return pizzaService.getAllTemplates();
	}

	@POST
	@Path("/templates/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Pizza addTemplate(Pizza pizza) {
		return pizzaService.addPizza(pizza);
	}

	@POST
	@Path("/templates/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pizza updateTemplate(Pizza pizza) {
		return pizzaService.updateTemplateById(pizza);
	}

	@GET
	@Path("/templates/delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public boolean deleteTemplate(@PathParam("id") int id) {
		return pizzaService.deleteTemplateById(id);
	}

	// Ingredients menagement
	@GET
	@Path("/ingredients/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> getAllIngredients() {
		return ingredientService.getAllIngredients();
	}

	@POST
	@Path("/ingredients/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Ingredient addIngredient(Ingredient ingredient) {
		return ingredientService.addIngredient(ingredient);
	}

	@POST
	@Path("/ingredients/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ingredient updateIngredient(Ingredient ingredient) {
		return ingredientService.updateIngredient(ingredient);
	}

	@GET
	@Path("/ingredients/delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public boolean deleteIngredient(@PathParam("id") int id) {
		return ingredientService.deleteIngredient(id);
	}

	// Tags menagement
	@GET
	@Path("/tags/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PizzaTag> getAllPizzaTags() {
		return pizzaTagService.getAllTags();
	}

	@POST
	@Path("/tags/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PizzaTag addPizzaTag(PizzaTag pizzaTag) {
		return pizzaTagService.addPizzaTag(pizzaTag);
	}

	@POST
	@Path("/tags/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PizzaTag updatePizzaTag(PizzaTag pizzaTag) {
		return pizzaTagService.updatePizzaTag(pizzaTag);
	}

	@GET
	@Path("/tags/delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public boolean deletePizzaTag(@PathParam("id") int id) {
		return pizzaTagService.deletePizzaTag(id);
	}

}
