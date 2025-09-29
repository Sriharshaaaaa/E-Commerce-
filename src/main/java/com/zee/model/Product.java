 package com.zee.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor 
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String description;
	
	private int mrpPrice;
	
	private int sellingPrice;
	
	private int discountPercent;
	
	private int quantity;
	
	private String color;
	
	// whenever we use elementCollection it creates a separate table to store the collection collection of images
	// the table name will be product_images and it will have two columns product_id and images
	// product_id will be the foreign key referencing the product table
	// @ElementCollection is used to store a collection of basic types or embeddable types (embeddable means a class that is not an entity but is used to represent a part of an entity)
	// it creates well structred table in the database
	@ElementCollection
	private List<String> images = new ArrayList<>();
	
	private int numRatings;
	
	@ManyToOne
	private Category category;
	
	// many products can belong to one seller
	// when a seller is deleted, all their products will be deleted as well
	// orphanRemoval = true means if we remove a product from the seller, it will be removed from the database as well 
	// seller field in product is the owner of the relationship
	@ManyToOne
	private Seller seller;
	
	// createdAt will be set when the product is created and will not be updated
	private LocalDateTime createdAt;
	
	// sizes will be stored in a comma separated format like "S,M,L,XL"
	private String sizes;
	
	// one product can have many reviews
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews = new ArrayList<>();
	
}
