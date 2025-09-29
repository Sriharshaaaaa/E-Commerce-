package com.zee.service.impl;

import org.springframework.stereotype.Service;

import com.zee.model.Seller;
import com.zee.model.SellerReport;
import com.zee.repository.SellerReportRepository;
import com.zee.service.SellerReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {
	
	private final SellerReportRepository sellerReportRepository;

	@Override
	public SellerReport getSellerReport(Seller seller) {
		
		SellerReport sellerReport = sellerReportRepository.findBySellerId(seller.getId()); 
		if(sellerReport == null) {
			SellerReport newReport = new SellerReport();
			newReport.setSeller(seller);
			return sellerReportRepository.save(newReport);
		}
		return sellerReport;
	}

	@Override
	public SellerReport updateSellerReport(SellerReport sellerReport) {
		
		return sellerReportRepository.save(sellerReport);
	}

}
