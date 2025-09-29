package com.zee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.model.Home;
import com.zee.model.HomeCatagory;
import com.zee.service.HomeCategorService;
import com.zee.service.HomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HomeCategoryController {
	
	
	private final HomeCategorService homeCategoryService;
	private final HomeService homeService;
	
	// getting home page with seprated category
	@PostMapping("/home/categories")
	public ResponseEntity<Home> creatHomeCategory(
			@RequestBody List<HomeCatagory> homeCategory){
		// giving all the categories to home service
		List<HomeCatagory> categoreies = homeCategoryService.createHomeCategories(homeCategory);
		// here home service will filter and arange in seperate section and return home page
		Home home = homeService.createHomePageData(categoreies);
		return new ResponseEntity<>(home,HttpStatus.ACCEPTED);
	}
	
	// for admin only
	// geting all home categoris
	@GetMapping("/admin/home-category")
	public ResponseEntity<List<HomeCatagory>> getHomeCategory() throws Exception{
		List<HomeCatagory> categories = homeCategoryService.getAllHomeCateCatagories();
		return ResponseEntity.ok(categories);
	}
	
	// to update home category
	@PatchMapping("/admin/home-category/{id}")
	public ResponseEntity<HomeCatagory> updateCategoryHandler(
			@PathVariable Long id,
			@RequestBody HomeCatagory homeCategory) throws Exception{
		HomeCatagory updateCategory = homeCategoryService.updateHomwCatagory(homeCategory, id);
		return ResponseEntity.ok(updateCategory);
	}
	
	
	
}
