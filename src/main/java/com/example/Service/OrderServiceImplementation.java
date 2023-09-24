package com.example.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.Exception.OrderException;
import com.example.Models.*;
import com.example.Repository.*;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImplementation implements OrderService {
	
	private orderRepository orderRep;
	private CartService cartService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;
	
	public OrderServiceImplementation(orderRepository orderRep,CartService cartService,
			AddressRepository addressRepository,UserRepository userRepository,
			OrderItemService orderItemService,OrderItemRepository orderItemRepository) {
		this.orderRep=orderRep;
		this.cartService=cartService;
		this.addressRepository=addressRepository;
		this.userRepository=userRepository;
		this.orderItemService=orderItemService;
		this.orderItemRepository=orderItemRepository;
	}

	@Override
	public Order createOrder(Users user, Address shippAddress) {
		
		shippAddress.setUser(user);
		Address address= addressRepository.save(shippAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart=cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item: cart.getCartItems()) {
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalltem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreateAt(LocalDateTime.now());
		
		Order savedOrder=orderRep.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
		
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderByld(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderByld(orderId);
		order.setOrderStatus("CONFIRMED");
		
		
		return orderRep.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderByld(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRep.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderByld(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRep.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderByld(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRep.save(order);
	}


	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders=orderRep.getUsersOrders(userId);
		return orders;
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRep.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order =findOrderByld(orderId);
		
		orderRep.deleteById(orderId);
		
	}

	@Override
	public Order findOrderByld(Long orderld) throws OrderException {
		Optional<Order> opt=orderRep.findById(orderld);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id "+orderld);
	}

}
