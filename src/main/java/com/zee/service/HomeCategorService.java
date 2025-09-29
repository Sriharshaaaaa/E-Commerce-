package com.zee.service;

import java.util.List;

import com.zee.model.HomeCatagory;

public interface HomeCategorService {
	
	HomeCatagory createHomeCatagory(HomeCatagory homeCatehory);
	
	List<HomeCatagory> createHomeCategories(List<HomeCatagory> homeCategories);
	HomeCatagory updateHomwCatagory(HomeCatagory homeCategory, Long id) throws Exception;
	List<HomeCatagory> getAllHomeCateCatagories();
}
