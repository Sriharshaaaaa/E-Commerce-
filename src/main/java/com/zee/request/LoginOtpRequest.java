package com.zee.request;

import com.zee.dto.UserRole;

import lombok.Data;

@Data
public class LoginOtpRequest {
	private String email;
	private String otp;
	private UserRole role;
}
