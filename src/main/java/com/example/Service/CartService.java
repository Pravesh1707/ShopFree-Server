package com.example.Service;

import com.example.Exception.ProductException;
import com.example.Models.Cart;
import com.example.Models.Users;
import com.example.Request.AddItemRequest;

public interface CartService {

	public Cart createCart(Users user);
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	public Cart findUserCart(Long userId);
}
