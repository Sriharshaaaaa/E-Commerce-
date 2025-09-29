package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zee.model.VarificationCode;

public interface VerificationCodeRepository extends JpaRepository<VarificationCode, Long>{
	VarificationCode findByEmail(String email);

	VarificationCode findByOtp(String otp);
}
