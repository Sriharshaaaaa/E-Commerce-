package com.zee.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zee.exceptions.ProductException;
import com.zee.model.Product;
import com.zee.model.Seller;
import com.zee.request.CreateProductRequest;

public interface ProductService {
	
	public Product createProduct(CreateProductRequest req, Seller seller);
	public void deleteProduct(Long productId) throws ProductException;
	public Product updateProduct(Long productId, Product product) throws ProductException;
	public Product findProductById(Long productId) throws ProductException;
	List<Product> searchProducts(String query);
	public Page<Product> getAllProducts(
			String category,
			String brand,
			String colors,
			String sizes,
			Integer minPrice,
			Integer maxPrice,
			Integer minDiscount,
			String sort,
			String stock,
			Integer pageNumber
			);
	List<Product> getProductBySellerId(Long sellerId);
}
