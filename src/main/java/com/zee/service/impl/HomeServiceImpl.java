package com.zee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zee.dto.HomeCatagorySection;
import com.zee.model.Deal;
import com.zee.model.Home;
import com.zee.model.HomeCatagory;
import com.zee.repository.DealRepository;
import com.zee.service.HomeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
	
	private final DealRepository dealRepository;
	
	@Override
	public Home createHomePageData(List<HomeCatagory> allCategories) {
		
		List<HomeCatagory> gridCategory = allCategories.stream()
				.filter(category  ->
				category.getSection() == HomeCatagorySection.GRID)
				.collect(Collectors.toList());
		
		List<HomeCatagory> shopByCategory = allCategories.stream()
				.filter(category ->
				category.getSection() == HomeCatagorySection.SHOP_BY_CATAGORY)
				.collect(Collectors.toList());
		List<HomeCatagory> electricCategory = allCategories.stream()
				.filter(category ->
				category.getSection() == HomeCatagorySection.ELECTRIC_CATAGORY)
				.collect(Collectors.toList());
		List<HomeCatagory> dealCategory = allCategories.stream()
				.filter(category ->
				category.getSection() == HomeCatagorySection.DEALS)
				.collect(Collectors.toList());
		
		List<Deal> createdDeals = new ArrayList<>();
		
		if(dealRepository.findAll().isEmpty()) {
			List<Deal> deels = allCategories.stream()
					.filter(category -> category.getSection() == HomeCatagorySection.DEALS)
					.map(category -> new Deal(null,10,category))
					.collect(Collectors.toList());
		}
		else createdDeals = dealRepository.findAll();
		
		Home home = new Home();
		home.setGrid(gridCategory);
		home.setShopByCataegories(shopByCategory);
		home.setElectricCategories(electricCategory);
		home.setDeals(createdDeals);
		home.setDealCategores(dealCategory);
		
		return home;
	}

}
