package com.zee.response;

import lombok.Data;

@Data
public class SighnupRequest {
	
	private String email;
	private String fullName;
	private String otp;

}
