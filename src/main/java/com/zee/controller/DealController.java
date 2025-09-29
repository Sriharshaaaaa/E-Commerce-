package com.zee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.model.Deal;
import com.zee.response.ApiResponse;
import com.zee.service.DealService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class DealController {
	
	private final DealService dealService;
	
	@PostMapping
	public ResponseEntity<Deal> createDealHandler(
			@RequestBody Deal deal) {
		Deal createdDeal = dealService.createDeal(deal);
		return new ResponseEntity<>(deal,HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Deal> updateDealHandler(
			@PathVariable Long id,
			@RequestBody Deal deal) throws Exception {
		
		Deal updatedDeal = dealService.updateDeal(deal, id);
		return ResponseEntity.ok(updatedDeal);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteDealHandler(
			@PathVariable Long id) throws Exception{
		
		dealService.deleteDeal(id);
		
		ApiResponse resp = new ApiResponse();
		resp.setMessage("delete deal succesfull");
		
		return new  ResponseEntity<>(resp,HttpStatus.ACCEPTED);
	}

}
