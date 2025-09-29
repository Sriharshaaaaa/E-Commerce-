package com.zee.model;

import java.util.List;

import lombok.Data;


@Data
public class Home {
	
	private List<HomeCatagory> grid;
	
	private List<HomeCatagory> shopByCataegories;
	
	private List<HomeCatagory> electricCategories;
	
	private List<HomeCatagory> dealCategores;
	
	private List<Deal> deals;

}
