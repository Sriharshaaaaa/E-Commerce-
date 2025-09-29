package com.zee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zee.model.Order;
import com.zee.model.PaymentOrder;
import com.zee.model.Seller;
import com.zee.model.SellerReport;
import com.zee.model.User;
import com.zee.response.ApiResponse;
import com.zee.response.PaymentLinkResponse;
import com.zee.service.OrderService;
import com.zee.service.PaymentService;
import com.zee.service.SellerReportService;
import com.zee.service.SellerService;
import com.zee.service.TransactionService;
import com.zee.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	private final UserService userService;
	private final SellerService sellerService;
	private final OrderService orderService;
	private final SellerReportService sellerReportService;
	private final TransactionService transactionService;
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<ApiResponse> paymentSuccessHandler(
			@PathVariable String paymentId,
			@RequestParam String paymentLinkedId,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		
		PaymentLinkResponse paymentResponse;
		
		PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkedId);
		
		boolean paymentSuccess = paymentService.ProceedPaymentOrder(paymentOrder, paymentId, paymentLinkedId);
		
		
		if(paymentSuccess) {
			for(Order order:paymentOrder.getOrders() ) {
				transactionService.createTransaction(order);
				Seller seller = sellerService.getSellerById(order.getSellerId());
				SellerReport report = sellerReportService.getSellerReport(seller);
				report.setTotalOrders(report.getTotalOrders()+1);
				report.setTotalEarngings(report.getTotalEarngings()+order.getTotalSellingPrice());
				report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
				sellerReportService.updateSellerReport(report);
			}
		}
		
		ApiResponse res = new ApiResponse();
		res.setMessage("payment successfull");
		
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.CREATED);
	}
	

}
