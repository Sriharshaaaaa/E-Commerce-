 package com.zee.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.zee.dto.AccountStatus;
import com.zee.dto.OrderStatus;
import com.zee.dto.PaymentStatus;
import com.zee.dto.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode; 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
 @Setter
 @Getter
 @AllArgsConstructor
 @NoArgsConstructor
 @EqualsAndHashCode
public class Order {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String orderId;
	
	@ManyToOne
	private User user;
	
	private long sellerId;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@ManyToOne
	private Address shippingAddress;
	
	@Embedded
	private PaymentDetails paymentDetails = new PaymentDetails();
	
	private double totalMrpPrice;
	
	private Integer totalSellingPrice;
	
	private Integer discount;
	
	private OrderStatus orderStatus;
	
	private int totalItem;
	
	private PaymentStatus paymentStatus = PaymentStatus.PENDING;
	
	private LocalDateTime orderDate =LocalDateTime.now();
	private LocalDateTime deliveryDate = orderDate.plusDays(7);
}
