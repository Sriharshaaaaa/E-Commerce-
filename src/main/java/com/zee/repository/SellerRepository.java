package com.zee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.dto.AccountStatus;
import com.zee.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	Seller findByEmail(String email);
	
	List<Seller> findByAccountStatus(AccountStatus status);
}
