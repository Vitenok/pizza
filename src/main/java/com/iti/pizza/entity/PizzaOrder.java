package com.iti.pizza.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries(value = { @NamedQuery(name = "PizzaOrder.findOrderByAddress", query = "select o from PizzaOrder o where o.address = :address"), @NamedQuery(name = "PizzaOrder.findOrderByDate", query = "select o from PizzaOrder o where o.orderDate = :orderDate") })
@Table(name="pizza_order")
public class PizzaOrder implements Serializable {

	private static final long serialVersionUID = -7152689850694245632L;

	public static final String GET_ALL_ORDERS_BY_ADDRESS = "PizzaOrder.findOrderByAddress";
	public static final String GET_ALL_ORDERS_BY_DATE = "PizzaOrder.findOrderByDate";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(nullable = false, name="address")
	private String address;

	@Column(nullable = false, name="order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@OneToMany
	private List<Pizza> pizzas;

	public int getOrderId() {
		return id;
	}

	public void setId(int orderId) {
		this.id = orderId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	
	public double getOrderPrice(PizzaOrder order) {
		List<Pizza> orderedPizzas = order.getPizzas();
		double orderPrice = 0;
		for (Pizza pizza : orderedPizzas) {
			double multiplier = pizza.getSize().getMultiplier();
			List<Ingredient> ingredients = pizza.getIngredients();

			for (Ingredient ingredient : ingredients) {
				orderPrice = +ingredient.getPrice() * multiplier;
			}
		}
		return orderPrice;
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
		PizzaOrder other = (PizzaOrder) obj;
		if (id != other.id)
			return false;
		return true;
	}

}