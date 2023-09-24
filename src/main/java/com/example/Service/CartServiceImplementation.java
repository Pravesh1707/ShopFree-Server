package com.example.Service;

import org.springframework.stereotype.Service;

import com.example.Exception.ProductException;
import com.example.Models.Cart;
import com.example.Models.CartItem;
import com.example.Models.Product;
import com.example.Models.Users;
import com.example.Repository.CartRepository;
import com.example.Request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	public CartServiceImplementation(CartRepository cartRepository, 
			CartItemService cartItemService, ProductService productService) {
		this.cartItemService = cartItemService;
		this.cartRepository = cartRepository;
		this.productService = productService;
	}
	
	@Override
	public Cart createCart(Users user) {
		Cart cart=new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart=cartRepository.findByUserId(userId);
		Product product=productService.findProductById(req.getProducId());
		
		CartItem isPresent=cartItemService.isCartitemExist(cart, product, req.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem(); 
			cartItem.setProduct(product); 
			cartItem.setCart(cart); 
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price=req.getQuantity()*product.getDiscountedPrice(); 
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize()); 
			CartItem createdCartItem=cartItemService.createCartitem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}
		
		return "item Add to Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		for(CartItem cartitem :cart.getCartItems()) { 
			totalPrice = totalPrice + cartitem.getPrice(); 
			totalDiscountedPrice += cartitem.getDiscountedPrice();
			totalItem = totalItem + cartitem.getQuantity();
		}
		cart.setTotalDiscountedPrice(totalDiscountedPrice); cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		return cartRepository.save(cart);
	}

}
