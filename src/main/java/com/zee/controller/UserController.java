package com.zee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.model.User;
import com.zee.response.AuthResponse;
import com.zee.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
	
	private final UserService userService;
	
	
	// fetching details from database
	@GetMapping("/users/profile")
	public ResponseEntity<User> getUserHandler(
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		
		return ResponseEntity.ok(user);
	}

}
