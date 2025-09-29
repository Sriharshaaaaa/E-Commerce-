package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.model.Wishlist;
@Repository
public interface WishListRepository extends JpaRepository<Wishlist, Long> {

	Wishlist findByUserId(Long userId);
}
