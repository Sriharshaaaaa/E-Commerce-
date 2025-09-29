package com.zee.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zee.model.Deal;
import com.zee.model.HomeCatagory;
import com.zee.repository.DealRepository;
import com.zee.repository.HomeCategoryRepository;
import com.zee.service.DealService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
	
	private final DealRepository dealRepository;
	private final HomeCategoryRepository homeCategoryRepository;

	@Override
	public List<Deal> getDeals() {
		
		return dealRepository.findAll();
	}

	@Override
	public Deal createDeal(Deal deal) {
		HomeCatagory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
		Deal newDeal = new Deal();
		newDeal.setCategory(category);
		newDeal.setDiscount(deal.getDiscount());

		return dealRepository.save(newDeal);
	}

//	@Override
//	public Deal updateDeal(Deal deal, Long id) throws Exception {
//		
//		Deal existingDeal = dealRepository.findById(id).orElse(null);
//		HomeCatagory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
//		
//		if(existingDeal!=null) {
//			if(deal.getDiscount() !=null) {
//				existingDeal.setDiscount(deal.getDiscount());
//			}
//			if(category!=null) {
//				existingDeal.setCategory(category);
//			}
//			return dealRepository.save(existingDeal);
//		}
//		throw new Exception("Deal not found");
//	}

	@Override
	public Deal updateDeal(Deal dealUpdateRequest, Long id) throws Exception {
	    
	    // Step 1: Fetch the existing deal from the database.
	    // .orElseThrow() is better here because it stops execution immediately if the deal doesn't exist.
	    Deal existingDeal = dealRepository.findById(id)
	            .orElseThrow(() -> new Exception("Deal not found with id: " + id));

	    // Step 2: Check and update the discount if a new one was provided.
	    // We check for null to ensure we don't overwrite an existing value with nothing.
	    if (dealUpdateRequest.getDiscount() != null) {
	        existingDeal.setDiscount(dealUpdateRequest.getDiscount());
	    }

	    // Step 3: Check and update the category ONLY IF a new one was provided in the request.
	    // This is the main fix. We check for the nested category and its ID to avoid NullPointerExceptions.
	    if (dealUpdateRequest.getCategory() != null && dealUpdateRequest.getCategory().getId() != null) {
	        
	        // Fetch the new category from the database.
	        HomeCatagory newCategory = homeCategoryRepository.findById(dealUpdateRequest.getCategory().getId())
	                .orElseThrow(() -> new Exception("Category not found with id: " + dealUpdateRequest.getCategory().getId()));
	        
	        // If found, update the category on the existing deal.
	        existingDeal.setCategory(newCategory);
	    }

	    // Step 4: Save the modified 'existingDeal' object back to the database.
	    return dealRepository.save(existingDeal);
	}
	
	
	@Override
	public void deleteDeal(Long id) throws Exception{
		
		
		Deal deal = dealRepository.findById(id).orElseThrow(()->
		new Exception("Deal Not found"));
		
		dealRepository.delete(deal);

	}

}
