package com.iti.pizza.main;

import com.iti.pizza.entity.Ingredient;
import com.iti.pizza.entity.Pizza;
import com.iti.pizza.services.AdminService;
import com.iti.pizza.services.IngredientService;
import com.iti.pizza.services.OrderService;
import com.iti.pizza.services.PizzaService;
import com.iti.pizza.services.PizzaTagService;

public class Main {

	public static void main(String[] args) {

		/**
		 * // EntityManagerFactory emf = Persistence.createEntityManagerFactory("pizza"); // EntityManager em = emf.createEntityManager(); // // List<Pizza> pizzas = new ArrayList<Pizza>(); // Pizza p1 = new Pizza(); // Pizza p2 = new Pizza(); // // p1.setName("one"); // p2.setName("two"); // // pizzas.add(p1); // pizzas.add(p2); // // Ingredient in1 = new Ingredient(); // in1.setName("test1"); // in1.setPrice(100); // in1.setPizzas(pizzas); // // em.getTransaction().begin(); // em.persist(in1); // em.getTransaction().commit(); // em.close(); // emf.close();
		 **/

		AdminService as = new AdminService();
		IngredientService is = new IngredientService();
		PizzaService ps = new PizzaService();
		OrderService os = new OrderService();
		PizzaTagService pts = new PizzaTagService();
		
//		for (Ingredient i : is.getAllIngredients()) {
//			System.out.print(i);
//			System.out.print(" ");
//			System.out.println(i.getPizzas());
//		}
		
		for (Pizza p : ps.getAllTemplates()) {
			System.out.print(p);
			System.out.print(" ");
			System.out.println(p.getIngredients());
		}
		
//
//		as.addIngredient("Mozarella", 10, IngredientType.CHEESE);
//		as.addIngredient("Parmejano", 10, IngredientType.CHEESE);
//		as.addIngredient("Tomatoes", 10, IngredientType.VEGETABLES);
//		as.addIngredient("Olives", 10, IngredientType.VEGETABLES);
//		as.addIngredient("Chili", 10, IngredientType.SPICES);
//		as.addIngredient("Chicken", 10, IngredientType.MEAT);
//		as.addIngredient("Beef", 10, IngredientType.MEAT);
//		as.addIngredient("Pepperoni", 10, IngredientType.MEAT);
//		as.addIngredient("Prawns", 10, IngredientType.SEAFOOD);
//		as.addIngredient("Oysters", 10, IngredientType.SEAFOOD);
//		
//		as.addPizzaTag("Spicy");
//		as.addPizzaTag("Seafood");
//		as.addPizzaTag("Meat");
//		as.addPizzaTag("Vegetarian");
	//	as.deletePizzaById(1);
		
//		List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
//		ingredients1.add(is.findIngredientById(6));
//		ingredients1.add(is.findIngredientById(2));
//		ingredients1.add(is.findIngredientById(4));
//		List<PizzaTag> tags1 = new ArrayList<PizzaTag>();
//		tags1.add(pts.findTagById(3));
//		as.addPizzaTemplate(Thickness.THICK, Size.BIG, "Super pizza", true, ingredients1,  tags1);
////		Pizza pizzaToUpdate = ps.findPizzaById(1);
////		pizzaToUpdate.setTags(tags1);
////		pizzaToUpdate.setIngredients(ingredients1);
//		
//		
//		List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
//		ingredients2.add(is.findIngredientById(1));
//		ingredients2.add(is.findIngredientById(3));
//		ingredients2.add(is.findIngredientById(7));
//		List<PizzaTag> tags2 = new ArrayList<PizzaTag>();
//		tags2.add(pts.findTagById(3));
//		as.addPizzaTemplate(Thickness.THIN, Size.MEDIUM, "Incredible!", true, null, tags2 );
////		
//		List<Ingredient>  ingredients3= new ArrayList<Ingredient>();
//		ingredients3.add(is.findIngredientById(10));
//		ingredients3.add(is.findIngredientById(9));
//		ingredients3.add(is.findIngredientById(4));
//		List<PizzaTag> tags3 = new ArrayList<PizzaTag>();
//		tags3.add(pts.findTagById(2));
//		as.addPizzaTemplate(Thickness.THIN, Size.MEDIUM, "Fabulous!", true, null ,tags3);
////		
//		List<Ingredient>  ingredients4 = new ArrayList<Ingredient>();
//		ingredients4.add(is.findIngredientById(1));
//		ingredients4.add(is.findIngredientById(3));
//		ingredients4.add(is.findIngredientById(4));
//		ingredients4.add(is.findIngredientById(5));
//		List<PizzaTag> tags4 = new ArrayList<PizzaTag>();
//		tags4.add(pts.findTagById(1));
//		tags4.add(pts.findTagById(4));
//		as.addPizzaTemplate(Thickness.THIN, Size.MEDIUM, "Vegetarian!", true, null,  tags4);
//		
//		
//		
//		List<Ingredient>  ingredients = new ArrayList<Ingredient>();
//		ingredients.add(is.findIngredientById(1));
//		ingredients.add(is.findIngredientById(3));
//		ingredients.add(is.findIngredientById(4));
//		ingredients.add(is.findIngredientById(5));
//		List<PizzaTag> tags4 = new ArrayList<PizzaTag>();
//		tags4.add(pts.findTagById(1));
//		tags4.add(pts.findTagById(4));
//		as.addPizzaTemplate(Thickness.THIN, Size.MEDIUM, "New!", true, ingredients,  tags4);
//	
		
//        System.out.println(as.getAllAdmins());
//		System.out.println(is.getAllIngredients());
//		System.out.println(ps.getAllTemplates());
//		System.out.println(os.getAllOrders());
//		System.out.println(pts.getAllTags());

		as.closeEMF();
		is.closeEMF();
		ps.closeEMF();
		os.closeEMF();
		pts.closeEMF();
	}
}
