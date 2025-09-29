package com.zee.service;

import java.util.List;

import com.zee.model.Home;
import com.zee.model.HomeCatagory;

public interface HomeService {
	public Home createHomePageData( List<HomeCatagory> allCategories);
}
