package com.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exception.OrderException;
import com.example.Models.Order;
import com.example.Service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminController {

	@Autowired
	private OrderService orderService;
	
	public AdminController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler(){
			List<Order> orders=orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
	@RequestHeader("Authorization") String jwt) throws OrderException {
		
		Order order=orderService.confirmedOrder(orderId);
			
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> ShippedOrderHandler(@PathVariable Long orderId,
	@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order=orderService.shippedOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> DeliverOrderHandle(@PathVariable Long orderId,
	@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order=orderService. deliveredOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	@PutMapping("/{orderId}/cancle")
	public ResponseEntity<Order> CancelOrderHandler(@PathVariable Long orderld,
	@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order=orderService.cancledOrder(orderld);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
//	@DeleteMapping("/{orderId}/delete")
//	public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderld,
//			@RequestHeader("Authorization") String jwt) throws OrderException {
//			orderService.deleteOrder(orderld);
//			ApiResponse res=new ApiResponse();
//			res.setMessage("order deleted successfully");
//			
//			res.setStatus(true);
//			return new ResponseEntity<>(res, HttpStatus.OK);
//	}
}
