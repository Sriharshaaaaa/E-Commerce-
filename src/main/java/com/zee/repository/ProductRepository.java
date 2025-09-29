package com.zee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zee.model.Product;
 
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
		
	List<Product> findBySellerId(Long id);
	
	@Query("SELECT p FROM Product p where (:query is null or lower(p.title)"
			+ "LIKE lower(concat('%' , :query,'%') ) )"
			+ "or (:query is null or lower(p.category.name)"
			+ "LIKE lower(concat('%', :query, '%') ) )")		
	List<Product> searchProduct(@Param("query") String query);
}
 