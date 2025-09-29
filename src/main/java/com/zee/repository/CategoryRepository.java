package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByCategoryId(String categoryId);
}
