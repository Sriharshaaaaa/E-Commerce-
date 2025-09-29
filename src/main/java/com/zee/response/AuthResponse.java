package com.zee.response;

import com.zee.dto.UserRole;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String jwt;
	private String message;
	private UserRole role;

}
