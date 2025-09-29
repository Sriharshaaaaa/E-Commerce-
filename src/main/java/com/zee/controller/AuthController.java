package com.zee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.dto.UserRole;
import com.zee.model.User;
import com.zee.model.VarificationCode;
import com.zee.repository.UserRepository;
import com.zee.request.LoginOtpRequest;
import com.zee.request.LonginRequest;
import com.zee.response.ApiResponse;
import com.zee.response.AuthResponse;
import com.zee.response.SighnupRequest;
import com.zee.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserRepository userRepository;
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SighnupRequest req) throws Exception{
		
		String jwt = authService.createUser(req);
		
		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setMessage("Register success");
		res.setRole(UserRole.ROLE_CUSTOMER);
	
		return ResponseEntity.ok(res);
	}
	@PostMapping("/sent/login-signup-otp")
	public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception{
		
		authService.sentLoginAndSighnupOtp(req.getEmail(),req.getRole());
		
		ApiResponse res = new ApiResponse();
		
		res.setMessage("otp sent succesfully");
		
		return ResponseEntity.ok(res);
	}

@PostMapping("/signin")
public ResponseEntity<AuthResponse> loginHandler(@RequestBody LonginRequest req) throws Exception{
	
	AuthResponse authResponse = authService.signing(req);

	return ResponseEntity.ok(authResponse);
}

}
