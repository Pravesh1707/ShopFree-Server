package com.example.Service;

import com.example.Exception.CartItemException;
import com.example.Exception.UserException;
import com.example.Models.Cart;
import com.example.Models.CartItem;
import com.example.Models.Product;

public interface CartItemService {

	public CartItem isCartitemExist(Cart cart, Product product, String size, Long userId);
	public CartItem createCartitem(CartItem cartitem);
	
	public CartItem updateCartitem(Long userId, Long id, CartItem cartItem)throws CartItemException, UserException;
	public void removeCartItem(Long userId, Long cartItemld) throws CartItemException, UserException;
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
