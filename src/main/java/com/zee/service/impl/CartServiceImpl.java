package com.zee.service.impl;

import org.springframework.stereotype.Service;

import com.zee.model.Cart;
import com.zee.model.CartItem;
import com.zee.model.Product;
import com.zee.model.User;
import com.zee.repository.CartItemRepository;
import com.zee.repository.CartRespository;
import com.zee.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	
	private final CartRespository cartRepository;
	private final CartItemRepository cartItemRepository;

	@Override
	public CartItem addCartItem(User user, Product product, String size, int quantity) {
	
		Cart cart = findUserCart(user);
		
		CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);
		
		if(isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setSize(size);
			cartItem.setUserId(user.getId());
			
			int totalPrice = quantity * product.getSellingPrice();
			cartItem.setSellingPrice(totalPrice);
			cartItem.setMrpPrice(quantity *product.getMrpPrice());
			cart.getCartItems().add(cartItem);
			cartItem.setCart(cart);
			
			return cartItemRepository.save(cartItem); 
			
		}
		return isPresent;
	}

	@Override
	public Cart findUserCart(User user) {
		
		Cart cart = cartRepository.findByUserId(user.getId());
		
		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;
		
		for(CartItem cartItem: cart.getCartItems()) {
			totalPrice += cartItem.getMrpPrice();
			totalDiscountedPrice += cartItem.getSellingPrice();
			totalItem +=cartItem.getQuantity();
		}
		
		cart.setTotalMrpPrice(totalPrice);
		cart.setTotalItems(totalItem);
		cart.setDiscount(calculateDiscountPercentage(totalPrice,totalDiscountedPrice));
		cart.setTotalItems(totalItem);
		
		return cart;
	}
	
	private int calculateDiscountPercentage(double mrpPrice,double sellingPrice) {
		if(mrpPrice<=0) {
			return 0;
		}
		double discount = mrpPrice-sellingPrice;
		double discountPercentge = (discount/mrpPrice)*100;
		
		return (int)discountPercentge;
	}

}
