package com.zee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.dto.AccountStatus;
import com.zee.model.Seller;
import com.zee.service.SellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {
	
	private final SellerService sellerService;
	
	// this method to give power to admin to manage sellers
	@PatchMapping("/seller/{id}/status/{status}")
	public ResponseEntity<Seller> updateSellerStatusHandler(
			@PathVariable Long id,
			@PathVariable AccountStatus status) throws Exception{
		
		Seller updatedSeller = sellerService.updateSellerAccountStatus(id, status);
		return ResponseEntity.ok(updatedSeller);
	}

}
