package com.zee.model;

import com.zee.dto.AccountStatus;
import com.zee.dto.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String sellerName;
	
	private String mobile;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	private String password;
	
	
	@Embedded
	private BuisnessDetails businessDetails = new BuisnessDetails();
	
	@Embedded
	private BankDetails bankDetails = new BankDetails();
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address pickupAdress = new Address();
	
	private String GSTIN;
	
	private UserRole role = UserRole.ROLE_SELLER;
	
	private boolean isEmailVerified = false;
	
	private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
	

}
