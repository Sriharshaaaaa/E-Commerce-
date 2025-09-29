package com.zee.service;

import com.zee.model.Cart;
import com.zee.model.CartItem;
import com.zee.model.Product;
import com.zee.model.User;

public interface CartService {
	
	public CartItem addCartItem(
			User user,
			Product product,
			String size,
			int quantity);
	
	public Cart findUserCart(User user);
	
}
