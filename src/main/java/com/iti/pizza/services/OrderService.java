package com.iti.pizza.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iti.pizza.dao.OrderDAO;
import com.iti.pizza.entity.Pizza;
import com.iti.pizza.entity.PizzaOrder;

public class OrderService {
	
	private PizzaService pizzaService = new PizzaService();
	private OrderDAO orderDAO = new OrderDAO();

	public PizzaOrder findOrderById(int id) {
		orderDAO.beginTransaction();
		PizzaOrder order = orderDAO.find(id);
		orderDAO.closeTransaction();
		return order;
	}

	public List<PizzaOrder> findOrdersByAddress(String address) {
		orderDAO.beginTransaction();
		List<PizzaOrder> order = orderDAO.findAllOrdersByAddress(address);
		orderDAO.closeTransaction();
		return order;
	}

	public List<PizzaOrder> findOrdersByDate(Date date) {
		orderDAO.beginTransaction();
		List<PizzaOrder> order = orderDAO.findAllOrdersByDate(date);
		orderDAO.closeTransaction();
		return order;
	}

	public List<PizzaOrder> getAllOrders() {
		orderDAO.beginTransaction();
		List<PizzaOrder> order = orderDAO.findAll();
		orderDAO.closeTransaction();
		return order;
	}

	public int addOrder(PizzaOrder order) {
		
		List<Pizza> persistedPizzas = new ArrayList<Pizza>();
		
		for (Pizza pizza : order.getPizzas()) {
			persistedPizzas.add(pizzaService.addPizza(pizza));
		}
		
		orderDAO.beginTransaction();
		order.setPizzas(persistedPizzas);
		order.setOrderDate(new Date());
		orderDAO.save(order);
		orderDAO.commitAndCloseTransaction();
		return order.getOrderId();
	}

	public void closeEMF() {
		orderDAO.closeEntityManagerFactory();
	}
}