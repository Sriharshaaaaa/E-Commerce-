package com.zee.service;

import com.zee.model.Seller;
import com.zee.model.SellerReport;

public interface SellerReportService {
	
	SellerReport getSellerReport(Seller seller);
	SellerReport updateSellerReport(SellerReport sellerReport);

}
