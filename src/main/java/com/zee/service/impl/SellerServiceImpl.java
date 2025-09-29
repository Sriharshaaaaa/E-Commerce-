package com.zee.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zee.comfig.JwtProvider;
import com.zee.dto.AccountStatus;
import com.zee.dto.UserRole;
import com.zee.exceptions.SellerException;
import com.zee.model.Address;
import com.zee.model.Seller;
import com.zee.repository.AddressRepository;
import com.zee.repository.SellerRepository;
import com.zee.service.SellerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
	
	private final SellerRepository sellerRepository;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final AddressRepository addressRepository;
	
	
	@Override
	public Seller getSellerProfile(String jwt) throws SellerException {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		return this.getSellerByEmail(email);
	}

	
	
	@Override
	public Seller createSeller(Seller seller) throws SellerException {
		Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
		
		if(sellerExist!=null) {
			throw new SellerException("Seller already exist, use differnt email");
			
		}
		Address savedAddress = addressRepository.save(seller.getPickupAdress());
		
		Seller newSeller = new Seller();
		
		newSeller.setEmail(seller.getEmail());
		newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
		newSeller.setSellerName(seller.getSellerName());
		newSeller.setPickupAdress(seller.getPickupAdress());
		newSeller.setGSTIN(seller.getGSTIN());
		newSeller.setRole(UserRole.ROLE_SELLER);
		newSeller.setMobile(seller.getMobile());
		newSeller.setBankDetails(seller.getBankDetails());
		newSeller.setBusinessDetails(seller.getBusinessDetails());
		
		
		return sellerRepository.save(newSeller);
	}
	
	
	

	@Override
	public Seller getSellerById(Long id) throws SellerException {
		
		return sellerRepository.findById(id).orElseThrow(()-> new SellerException("Seller not found with id"));
	}

	
	
	
	@Override
	public Seller getSellerByEmail(String email) throws SellerException {
		Seller seller = sellerRepository.findByEmail(email);
		if(seller == null) {
			throw new SellerException("Seller not found .. ");
		}
		return seller;
	}

	
	
	
	
	@Override
	public List<Seller> getAllSellers(AccountStatus status) {
		
		return sellerRepository.findByAccountStatus(status);
	}

	
	
	
	@Override
	public Seller updateSeller(Long id, Seller seller) throws SellerException {

		Seller existingSeller = this.getSellerById(id);
		 
		if(seller.getSellerName() != null) {
			existingSeller.setSellerName(seller.getSellerName());
		}
		if(seller.getMobile() != null) {
			existingSeller.setMobile(seller.getMobile());
		}
		if(seller.getEmail() != null) {
			existingSeller.setEmail(seller.getEmail());
		}
		if(seller.getBusinessDetails() != null && seller.getBusinessDetails().getBuisnessName() != null) {
			existingSeller.getBusinessDetails().setBuisnessName(
					seller.getBusinessDetails().getBuisnessName());
		}
		if(seller.getBankDetails() != null
				&& seller.getBankDetails().getAccountNumber() != null
				&& seller.getBankDetails().getIfscCode() != null
				&& seller.getBankDetails().getAccountNumber() != null) {
			
			existingSeller.getBankDetails().setAccuuntHolderName(
					seller.getBankDetails().getAccuuntHolderName());
			existingSeller.getBankDetails().setAccountNumber(
					seller.getBankDetails().getAccountNumber());
			existingSeller.getBankDetails().setIfscCode(
					seller.getBankDetails().getIfscCode());
		}
		if(seller.getPickupAdress() != null
				&& seller.getPickupAdress().getAddress() != null
				&& seller.getPickupAdress().getMobile() != null
				&& seller.getPickupAdress().getCity() != null
				&& seller.getPickupAdress().getState() != null) {
			
			existingSeller.getPickupAdress().setAddress(
					seller.getPickupAdress().getAddress());
			existingSeller.getPickupAdress().setCity(
					seller.getPickupAdress().getCity());
			existingSeller.getPickupAdress().setState(
					seller.getPickupAdress().getState());
			existingSeller.getPickupAdress().setMobile(
					seller.getPickupAdress().getMobile());
			existingSeller.getPickupAdress().setPincode(
					seller.getPickupAdress().getPincode());
		}
		if(seller.getGSTIN() != null) {
			existingSeller.setGSTIN(seller.getGSTIN());
		}
		
		
		return sellerRepository.save(existingSeller);
	}

	
	
	
	@Override
	public void deleteSeller(Long id) throws SellerException {
		
		Seller seller = this.getSellerById(id);
		sellerRepository.delete(seller);
		
	}
	
	
	@Override
	public Seller VerifyEmail(String email, String otp) throws SellerException {
		Seller seller = this.getSellerByEmail(email);
		seller.setEmailVerified(true);
		return sellerRepository.save(seller);
	}

	
	
	
	@Override
	public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException {
		Seller seller = this.getSellerById(sellerId);
		seller.setAccountStatus(status);
		return sellerRepository.save(seller);
	}

}
