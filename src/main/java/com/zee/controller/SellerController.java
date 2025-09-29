package com.zee.controller;

import java.util.List;

import org.aspectj.weaver.patterns.IVerificationRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zee.comfig.JwtProvider;
import com.zee.dto.AccountStatus;
import com.zee.exceptions.SellerException;
import com.zee.model.Seller;
import com.zee.model.SellerReport;
import com.zee.model.VarificationCode;
import com.zee.repository.SellerRepository;
import com.zee.repository.VerificationCodeRepository;
import com.zee.request.LonginRequest;
import com.zee.response.ApiResponse;
import com.zee.response.AuthResponse;
import com.zee.service.AuthService;
import com.zee.service.SellerReportService;
import com.zee.service.SellerService;
import com.zee.service.impl.EmailService;
import com.zee.util.OtpUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
	
	private final SellerService sellerService;
	private final VerificationCodeRepository verificationRepository;
	private final AuthService authService;
	private final EmailService emailService;
	private final JwtProvider jwtProvider;
	private final SellerReportService sellerReportService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginSeller(
			@RequestBody LonginRequest req) throws Exception{
	
		String otp = req.getOtp();
		String email = req.getEmail();
		req.setEmail("seller_"+email);
		AuthResponse authResponse = authService.signing(req);
		return ResponseEntity.ok(authResponse);		
	}
	
	
	
	// for seller otp verifcation
	@PatchMapping("/verify/{otp}")
	public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception{
		
		VarificationCode verificationCode = verificationRepository.findByOtp(otp);
		
		if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
			throw new Exception("wrong OTP.. ");
		}
		Seller seller = sellerService.VerifyEmail(verificationCode.getEmail(), otp);
		
		return new ResponseEntity<>(seller, HttpStatus.OK);
	}
	
	
	
	@PostMapping
	public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
		
		Seller savedSeller = sellerService.createSeller(seller);
		
		String otp = OtpUtil.generateOtp();
		VarificationCode verificationCode =  new VarificationCode();
		verificationCode.setOtp(otp);
		verificationCode.setEmail(seller.getEmail());
		
		verificationRepository.save(verificationCode);
		
		String subject = "Comzee Email verification Code";
		
		String text = "Welcome to Comzee, verify your account using this link";
		// for verfy by clicking link
		String frontend_url = "http://localhost:3000/verify-seller/";
		emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);
		
		return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
		
	}
	
	// to get seller details by id
	@GetMapping("/{id}")
	public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
		
		Seller seller = sellerService.getSellerById(id);
		
		return new ResponseEntity<>(seller, HttpStatus.OK);
	}
	
	// get profile details using jwt token
	@GetMapping("/profile")
	public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
		
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		Seller seller = sellerService.getSellerByEmail(email);
		
		return new ResponseEntity<>(seller, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/report")
	public ResponseEntity<SellerReport> getSellerReport(
			@RequestHeader("Authorization") String jwt) throws Exception{
		  
		Seller seller = sellerService.getSellerProfile(jwt);
		SellerReport report = sellerReportService.getSellerReport(seller);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	

	// get all sellers by status
	@GetMapping
	public ResponseEntity<List<Seller>> getAllSeller(
			@RequestParam(required = false) AccountStatus status){
		List<Seller> sellers = sellerService.getAllSellers(status);
		return ResponseEntity.ok(sellers);
	}
	
	
	// update seller details
	@PatchMapping()
	public ResponseEntity<Seller> updateSeller(
			@RequestHeader("Authorization") String jwt,
			@RequestBody Seller seller) throws Exception{
		
		Seller profile = sellerService.getSellerProfile(jwt);
		Seller updateSeller = sellerService.updateSeller(profile.getId(), seller);
		
		return ResponseEntity.ok(updateSeller);
			
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSelelr(@PathVariable Long id) throws Exception {
		
		sellerService.deleteSeller(id);
		// if you want to return empty response
		return ResponseEntity.noContent().build();
	}
	
	
	

}
