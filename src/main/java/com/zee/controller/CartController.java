package com.zee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.model.Cart;
import com.zee.model.CartItem;
import com.zee.model.Product;
import com.zee.model.User;
import com.zee.request.AddItemRequest;
import com.zee.response.ApiResponse;
import com.zee.service.CartItemService;
import com.zee.service.CartService;
import com.zee.service.ProductService;
import com.zee.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

	
	private final CartService cartService;
	private final CartItemService cartItemService; 
	private final UserService userService;
	private final ProductService productService;
	
	@GetMapping
	public ResponseEntity<Cart> findUserHandler(
			@RequestHeader("Authorization") String jwt) throws Exception {
		
		User user = userService.findUserByJwtToken(jwt);	
		Cart cart = cartService.findUserCart(user);
		return new ResponseEntity<>(cart,HttpStatus.OK);	
	}
	
	
	@PutMapping("/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		
		User user = userService.findUserByJwtToken(jwt);
		Product product = productService.findProductById(req.getProductId());
		
		CartItem item = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Item added to Cart Successfully");
		
		return new ResponseEntity<>(item,HttpStatus.ACCEPTED);	
	}
	
	
	
	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItemHandler(
			@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		
		cartItemService.removeCartItem(user.getId(), cartItemId);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Item Removed from cart");
		
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/item/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItemHandler(
			@PathVariable Long cartItemId,
			@RequestBody CartItem cartItem,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		
		CartItem updatedCartItem = null;
		if(cartItem.getQuantity()>0) {
			updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		}
		
		return new ResponseEntity<CartItem>(updatedCartItem,HttpStatus.ACCEPTED);
	}
	
	
}
