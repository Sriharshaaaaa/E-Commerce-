package com.zee.service;

import java.util.List;

import com.zee.model.Order;
import com.zee.model.Seller;
import com.zee.model.Transaction;

public interface TransactionService {
	
	Transaction createTransaction(Order order);
	List<Transaction> getTransactionBySellerId(Seller seller);
	List<Transaction> getAllTransactions();
	
}
