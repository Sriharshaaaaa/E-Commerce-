package com.zee.service;

import com.zee.dto.UserRole;
import com.zee.request.LonginRequest;
import com.zee.response.AuthResponse;
import com.zee.response.SighnupRequest;

public interface AuthService {
	
	void sentLoginAndSighnupOtp(String email, UserRole role) throws Exception;
	String createUser(SighnupRequest req) throws Exception;
	AuthResponse signing(LonginRequest req);

}
