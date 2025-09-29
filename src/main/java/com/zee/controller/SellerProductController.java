package com.zee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.exceptions.ProductException;
import com.zee.exceptions.SellerException;
import com.zee.model.Product;
import com.zee.model.Seller;
import com.zee.request.CreateProductRequest;
import com.zee.service.ProductService;
import com.zee.service.SellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers/products")
public class SellerProductController {
	
	private final ProductService productService;
	private final SellerService sellerService;

	@GetMapping()
	public ResponseEntity<List<Product>> getProductBySellerId(
			@RequestHeader("Authorization") String jwt) throws ProductException, SellerException {
		
		Seller seller = sellerService.getSellerProfile(jwt);
		
		List<Product> product = productService.getProductBySellerId(seller.getId());
		return new ResponseEntity<>(product,HttpStatus.OK);		
	}
	
	
	@PostMapping()
	public ResponseEntity<Product> createProduct(
			@RequestBody CreateProductRequest request,
			@RequestHeader("Authorization")String jwt)
			throws Exception {
		
		System.out.println("error"+jwt);
		
		Seller seller = sellerService.getSellerProfile(jwt);
		
		Product product = productService.createProduct(request, seller);
		return new ResponseEntity<>(product,HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		try {
			productService.deleteProduct(productId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(ProductException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PutMapping("/{productId}")
	public ResponseEntity<Product> updateProduct(
			@PathVariable Long productId,
			@RequestBody Product product) {
		
		try {
			Product updatedProduct = productService.updateProduct(productId, product);
			return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
		}
		catch(ProductException p) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
		
		
	
	
}
