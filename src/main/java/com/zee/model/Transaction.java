package com.zee.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@EqualsAndHashCode
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	 // Many transactions can be associated with one customer 
	@ManyToOne
	private User customer;
	
	 // One transaction is associated with one order
	@OneToOne
	private Order order;
	
	 // Many transactions can be associated with one seller
	@ManyToOne
	private Seller seller;
	
	private LocalDateTime date = LocalDateTime.now();
	
}
