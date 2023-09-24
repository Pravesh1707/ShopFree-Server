package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Models.OrderItem;
import com.example.Repository.OrderItemRepository;

@Service
public class OrderItemServiceImplementation implements OrderItemService{

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem){
		return orderItemRepository.save(orderItem);
	}
}
