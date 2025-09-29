 package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.model.Cart;
import com.zee.model.CartItem;
import com.zee.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
		CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
