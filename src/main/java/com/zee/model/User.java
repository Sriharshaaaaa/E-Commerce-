package com.zee.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zee.dto.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@EqualsAndHashCode
public class User {
	
	// user id as primary key
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Whenever we are sending the user object as a response we don't want to send the password
	// Whenever we fetch the user object from the request body we want to accept the password
	// So we will use @JsonProperty annotation to achieve this
	// we dont want to expose the password in the frontend so we will use access = JsonProperty.Access.WRITE_ONLY
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	

	private String email;
	
	private String fullName;
	
	private String mobile;
	
	private UserRole role = UserRole.ROLE_CUSTOMER;
	
	// one user can have multiple addresses
	// on first time user is created the address giver by user will be saved in the address table
	// then in futre if user wants to add more address we can add more address to the address table
	// one to many relationship
	// one user can have multiple addresses
	@OneToMany
	private Set<Address> addresses = new HashSet<>();
	
	// this is becouse to keep track of the coupons used by the user
	// so that user cannot use the same coupon multiple times
	@ManyToMany
	// json ignore is used so that we dont need this data in frontend 
	// we just need to know that user has used the coupon or not in backend 
	// jsonignore will ignore this field when we send the user object as a response
	@JsonIgnore
	private Set<Coupon> usedCoupons = new HashSet<>();

}
