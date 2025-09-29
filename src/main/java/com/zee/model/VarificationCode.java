package com.zee.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class VarificationCode {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	// whenever we login or signup we will generate a new otp and set the expiry time to 5 minutes from now
	private String otp;
	
	
	private String email;
	
	// if user is null then the code is for  seller
	@OneToOne
	private User user;
	
	// if seller is null then the code is for user 
	@OneToOne
	private Seller seller;
}
