package com.example.Service;

import java.util.List;

import com.example.Exception.OrderException;
import com.example.Models.Address;
import com.example.Models.Order;
import com.example.Models.Users;

public interface OrderService {

	
	public Order createOrder(Users users, Address shippingAdress);
	public Order findOrderByld(Long orderld) throws OrderException;
	public List<Order> usersOrderHistory (Long userld);
	public Order placedOrder(Long orderld) throws OrderException;
	public Order confirmedOrder(Long orderld) throws OrderException;
	public Order shippedOrder(Long orderld) throws OrderException;
	public Order deliveredOrder(Long orderld) throws OrderException;
	public Order cancledOrder(Long orderld) throws OrderException;
	public List<Order> getAllOrders();
	public void deleteOrder(Long orderld) throws OrderException;
}
