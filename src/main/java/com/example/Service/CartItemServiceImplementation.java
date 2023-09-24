package com.example.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Exception.CartItemException;
import com.example.Exception.UserException;
import com.example.Models.Cart;
import com.example.Models.CartItem;
import com.example.Models.Product;
import com.example.Models.Users;
import com.example.Repository.CartItemRepository;
import com.example.Repository.CartRepository;

@Service
public class CartItemServiceImplementation implements CartItemService{

	private CartItemRepository cartItemRepository;
	private UserService userService;
	private CartRepository cartRepository;
	
	public CartItemServiceImplementation(CartItemRepository cartItemRepository,UserService userService,CartRepository cartRepository) {
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
		this.userService = userService;
	}
	
	@Override
	public CartItem isCartitemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem =cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public CartItem createCartitem(CartItem cartitem) {
		
		cartitem.setPrice(cartitem.getProduct().getPrice()*cartitem.getQuantity());
		cartitem.setDiscountedPrice(cartitem.getProduct().getDiscountedPrice()*cartitem.getQuantity());
		
		CartItem createdCartItem=cartItemRepository.save(cartitem);
		return createdCartItem;
	}

	@Override
	public CartItem updateCartitem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		
		CartItem item=findCartItemById(id);
		Users user=userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
			
		return cartItemRepository.save(item);
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem=findCartItemById(cartItemId);
		Users user=userService.findUserById(cartItem.getUserId());
		Users reqUser=userService.findUserById(userId);
		if(user.getId().equals(reqUser.getId())){
		cartItemRepository.deleteById(cartItemId);
		}
		else {
			throw new UserException("you can't remove another users item");
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt =cartItemRepository.findById(cartItemId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cartItem not found with id : "+cartItemId);
	}

}
