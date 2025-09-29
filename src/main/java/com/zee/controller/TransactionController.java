package com.zee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.exceptions.SellerException;
import com.zee.model.Seller;
import com.zee.model.Transaction;
import com.zee.service.SellerService;
import com.zee.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;
	private final SellerService sellerservice;
	
	@GetMapping("/seller")
	public ResponseEntity<List<Transaction>> getTransactionBySeller(
			@RequestHeader("Authorization")String jwt) throws SellerException {
		
		Seller seller = sellerservice.getSellerProfile(jwt);
		
		List<Transaction> transactions = transactionService.getTransactionBySellerId(seller);
		return ResponseEntity.ok(transactions);
		
	}
	
	@GetMapping
	public ResponseEntity<List<Transaction>> getAllTransactions(){
		List<Transaction> transactions = transactionService.getAllTransactions();
		return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.OK);
	}
	
	
	
}
