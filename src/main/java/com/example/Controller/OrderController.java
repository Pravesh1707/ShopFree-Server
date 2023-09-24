package com.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exception.OrderException;
import com.example.Exception.UserException;
import com.example.Models.Address;
import com.example.Models.Order;
import com.example.Models.Users;
import com.example.Service.OrderService;
import com.example.Service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order>createOrderHandler(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException{
		
		Users user=userService.findUserProfileByJwt(jwt);
		Order order=orderService.createOrder(user, shippingAddress);
		System.out.println("order "+ order);
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>>usersOrderHistory(
	@RequestHeader("Authorization") String jwt) throws UserException{
		Users user=userService.findUserProfileByJwt(jwt);
		List<Order> orders =orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders, HttpStatus.CREATED);
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<Order>findOrderByld(
	@PathVariable("Id") Long orderld,
	@RequestHeader("Authorization") String jwt) throws UserException, OrderException{
	Users user=userService.findUserProfileByJwt(jwt);
	Order order =orderService.findOrderByld(orderld);
	return new ResponseEntity<>(order, HttpStatus.CREATED);
	}
}
