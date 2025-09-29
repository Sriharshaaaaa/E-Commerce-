package com.zee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.response.ApiResponse;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public ApiResponse HomeControllerHandler() {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("Welcome to ZeeMerse Application");
		return response;
	}
 
}
