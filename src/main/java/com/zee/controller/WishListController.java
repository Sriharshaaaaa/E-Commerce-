package com.zee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.model.Product;
import com.zee.model.User;
import com.zee.model.Wishlist;
import com.zee.service.ProductService;
import com.zee.service.UserService;
import com.zee.service.WishListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishListController {
	
	private final WishListService wishListService;
	private final  ProductService productService;
	private final UserService userService;
	
//	
//	@PostMapping("/create")
//	public ResponseEntity<Wishlist> createWishList(@RequestBody User user) {
//		Wishlist wishlist = wishListService.createWishList(user);
//		return ResponseEntity.ok(wishlist);
//	}
	
	
	@GetMapping()
	public ResponseEntity<Wishlist> getWishListByUserId(
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Wishlist wishList = wishListService.getWishListByUserId(user);
		return ResponseEntity.ok(wishList);
	}
	
	
	@PostMapping("/add-product/{productId}")
	public ResponseEntity<Wishlist> addProducttoWishList(
			@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		Product product = productService.findProductById(productId);
		User user = userService.findUserByJwtToken(jwt);
		Wishlist updatedWishList = wishListService.addProductToWishList(user, product);
		return ResponseEntity.ok(updatedWishList);
	}
	  

}
