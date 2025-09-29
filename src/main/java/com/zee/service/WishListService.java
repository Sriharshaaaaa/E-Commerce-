package com.zee.service;

import com.zee.model.Product;
import com.zee.model.User;
import com.zee.model.Wishlist;

public interface WishListService {
	Wishlist  createWishList(User user);
	Wishlist getWishListByUserId(User user);
	Wishlist addProductToWishList(User user,Product product);
}
