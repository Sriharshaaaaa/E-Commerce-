package com.zee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zee.dto.UserRole;
import com.zee.model.Seller;
import com.zee.model.User;
import com.zee.repository.SellerRepository;
import com.zee.repository.UserRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final SellerRepository sellerRepository;
	
	// seller name will start with prefix seller_... and user name will be normal
	private static final String SELLER_PREFIX = "seller_";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username.startsWith(SELLER_PREFIX)) {
			String actualUsername = username.substring(SELLER_PREFIX.length());
			Seller seller = sellerRepository.findByEmail(actualUsername);
			if(seller!=null) {
				return buildUserDetails(seller.getEmail(),seller.getPassword(),seller.getRole());
			}
		}
		else {
			User user = userRepository.findByEmail(username);
			if(user!=null) {
				return buildUserDetails(user.getEmail(), user.getPassword(), user.getRole());
			}
		}
		throw new UsernameNotFoundException("user or seller not found with email - "+username);
	}

	private UserDetails buildUserDetails(String email, String password, UserRole role) {
		if(role == null) role = UserRole.ROLE_CUSTOMER;
		
		List<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority(role.toString()));
		
		return new org.springframework.security.core.userdetails.User(email, 
				password, 
				 authorityList);
	}
	
}
