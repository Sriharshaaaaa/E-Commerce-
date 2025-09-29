package com.zee.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zee.model.HomeCatagory;
import com.zee.repository.HomeCategoryRepository;
import com.zee.service.HomeCategorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategorService {
	
	private final HomeCategoryRepository homeCategoryRepository;

	@Override
	public HomeCatagory createHomeCatagory(HomeCatagory homeCategory) {
		
		return homeCategoryRepository.save(homeCategory);
	}

	@Override
	public List<HomeCatagory> createHomeCategories(List<HomeCatagory> homeCategories) {
		if(homeCategoryRepository.findAll().isEmpty()) {
			return homeCategoryRepository.saveAll(homeCategories);
		}
		return homeCategoryRepository.findAll();
	}

	@Override
	public HomeCatagory updateHomwCatagory(HomeCatagory category, Long id) throws Exception {
		HomeCatagory existingCategory = homeCategoryRepository.findById(id)
				.orElseThrow(()-> new Exception("Category not foun"));
		if(category.getImage()!=null) {
			existingCategory.setImage(category.getImage());
		}
		if(category.getCatagoryId()!=null) {
			existingCategory.setCatagoryId(category.getCatagoryId());
		}
		return homeCategoryRepository.save(existingCategory);
	}

	@Override
	public List<HomeCatagory> getAllHomeCateCatagories() {
		
		return homeCategoryRepository.findAll();
	}

}
