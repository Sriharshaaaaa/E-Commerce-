package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.model.Cart;

@Repository
public interface CartRespository extends JpaRepository<Cart, Long> {
	Cart findByUserId(Long Id);
}
