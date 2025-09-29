package com.zee.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.zee.exceptions.ProductException;
import com.zee.model.Category;
import com.zee.model.Product;
import com.zee.model.Seller;
import com.zee.repository.CategoryRepository;
import com.zee.repository.ProductRepository;
import com.zee.request.CreateProductRequest;
import com.zee.service.ProductService;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;


  
	
	
	@Override
	public Product createProduct(CreateProductRequest req, Seller seller) {
		
		// Setting category details
		Category category1 = categoryRepository.findByCategoryId(req.getCategory());
		
		if(category1 == null) {
			Category category = new Category();
			category.setCategoryId(req.getCategory());
			category.setLevel(1);
			category1 = categoryRepository.save(category);
		}
		
		Category category2 = categoryRepository.findByCategoryId(req.getCategory2());
		
		if(category2 == null) {
			Category category = new Category();
			category.setCategoryId(req.getCategory2());
			category.setLevel(2);
			category.setParentCategory(category1);
			category2 = categoryRepository.save(category);
		}
		
		Category category3 = categoryRepository.findByCategoryId(req.getCategory3());
		
		if(category3 == null) {
			Category category = new Category();
			category.setCategoryId(req.getCategory3());
			category.setLevel(3);
			category.setParentCategory(category2);
			category3 = categoryRepository.save(category);
		}
		

		
		Product product = new Product();
		product.setSeller(seller);
		product.setCategory(category3);
		product.setDescription(req.getDescription());
		product.setCreatedAt(LocalDateTime.now());
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setSellingPrice(req.getSellingPrice());
		product.setImages(req.getImages());
		product.setMrpPrice(req.getMrpPrice());
		product.setSizes(req.getSizes());
		product.setDiscountPercent(calculateDiscountPercentage(req.getMrpPrice(),req.getSellingPrice()));
		
		
		return productRepository.save(product);
	}
	
	private int calculateDiscountPercentage(double mrpPrice,double sellingPrice) {
		if(mrpPrice<=0) {
			throw new IllegalArgumentException("Actual price must be greater then 0");
		}
		double discount = mrpPrice-sellingPrice;
		double discountPercentge = (discount/mrpPrice)*100;
		
		return (int)discountPercentge;
	}

	
	
	@Override
	public void deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		productRepository.delete(product);
		
	}

	
	
	
	
	
	@Override
	public Product updateProduct(Long productId, Product updatedProductDetails) throws ProductException {	
//		Product productById = findProductById(productId);
//		product.setId(productId);
//
//		return productRepository.save(product);
		
		
		
		  Product existingProduct = findProductById(productId);
		    
		    // Step 2: Update the fields of the existing product with the new details.
		    // We only update the fields that are meant to be changed by the user.
		    
		    // Check and update the title if a new one is provided.
		    if (updatedProductDetails.getTitle() != null && !updatedProductDetails.getTitle().isEmpty()) {
		        existingProduct.setTitle(updatedProductDetails.getTitle());
		    }
		    
		    // Check and update the description.
		    if (updatedProductDetails.getDescription() != null) {
		        existingProduct.setDescription(updatedProductDetails.getDescription());
		    }
		    
		    // Update numerical fields. In a real application, you might use a DTO
		    // with wrapper types (Integer) to differentiate between a value of 0 and a field not being provided.
		    // For simplicity here, we assume if it's provided, it's a valid update.
		    existingProduct.setMrpPrice(updatedProductDetails.getMrpPrice());
		    existingProduct.setSellingPrice(updatedProductDetails.getSellingPrice());
		    existingProduct.setQuantity(updatedProductDetails.getQuantity());
		    
		    if (updatedProductDetails.getColor() != null) {
		        existingProduct.setColor(updatedProductDetails.getColor());
		    }
		    
		    if (updatedProductDetails.getSizes() != null) {
		        existingProduct.setSizes(updatedProductDetails.getSizes());
		    }
		    
		    if (updatedProductDetails.getImages() != null && !updatedProductDetails.getImages().isEmpty()) {
		        existingProduct.setImages(updatedProductDetails.getImages());
		    }
		    
		    // Recalculate the discount percent based on the new prices.
		    existingProduct.setDiscountPercent(
		        calculateDiscountPercentage(existingProduct.getMrpPrice(), existingProduct.getSellingPrice())
		    );

		    // Step 3: Save the updated existing product back to the database.
		    // JPA is smart enough to know this is an update, not a new creation.
		    return productRepository.save(existingProduct);
	}

	
	
	
	
	@Override
	public Product findProductById(Long productId) throws ProductException {
		
		return productRepository.findById(productId).orElseThrow(
				()-> new ProductException("product not found with given id.. "+ productId));
	}
	
	
	
	
	

	@Override
	public List<Product> searchProducts(String query) {
		
		return productRepository.searchProduct(query);
	}

	@Override
	public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
		
		
		// this is filtering feuture
		Specification<Product> spec = (root,query,criteriaBuilder)->{
			List<Predicate> predicates = new ArrayList<>();
			
			if(category != null) {
				Join<Product, Category> categoryJoin = root.join("category");
				predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), categoryJoin));
			}
			
			if (colors != null && !colors.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("color"),colors));
			}
			
			if (sizes != null && !sizes.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("size"), sizes));
			}
			
			if (minPrice != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
			}
			
			if (maxPrice != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
			}
			
			if (minDiscount != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"), minDiscount));
			}
			
			if (stock != null && !stock.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		
		
		// sorting feutre
		Pageable pageable;
		
		if(sort != null && !sort.isEmpty()) {
			
			pageable =switch (sort) {
				case "price_low" ->PageRequest.of(pageNumber!=null? pageNumber:0, 10, Sort.by("sellingPrice").ascending());
				case "price_high" -> PageRequest.of(pageNumber!=null? pageNumber:0, 10,Sort.by("sellingPrice").descending());
				default -> PageRequest.of(pageNumber!=null? pageNumber:0, 10,Sort.unsorted());
			};
		}
		else {
			pageable = PageRequest.of(pageNumber != null? pageNumber:0,10, Sort.unsorted());
		}
		
		return productRepository.findAll(spec,pageable);
	}

	@Override
	public List<Product> getProductBySellerId(Long sellerId) {
	
		return productRepository.findBySellerId(sellerId);
	}

}
