package com.iti.pizza.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iti.pizza.entity.PizzaOrder;


public class OrderDAO extends GenericDAO<PizzaOrder>{

	private static final long serialVersionUID = -6159559030942660311L;

	public OrderDAO( ) {
		super(PizzaOrder.class);
	}

	public List<PizzaOrder> findAllOrdersByAddress(String address){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("address", address);
		return super.findResultList(PizzaOrder.GET_ALL_ORDERS_BY_ADDRESS, parameters);
	}
	
	public List<PizzaOrder> findAllOrdersByDate(Date date){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("address", date);
		return super.findResultList(PizzaOrder.GET_ALL_ORDERS_BY_DATE, parameters);
	}
}
