package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

	
}
