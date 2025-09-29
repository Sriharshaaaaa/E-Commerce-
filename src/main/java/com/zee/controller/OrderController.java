package com.zee.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.PaymentLink;
import com.zee.dto.PaymentMethod;
import com.zee.model.Address;
import com.zee.model.Cart;
import com.zee.model.Order;
import com.zee.model.OrderItem;
import com.zee.model.PaymentOrder;
import com.zee.model.Seller;
import com.zee.model.SellerReport;
import com.zee.model.User;
import com.zee.repository.PaymentOrderRepository;
import com.zee.response.PaymentLinkResponse;
import com.zee.service.CartService;
import com.zee.service.OrderService;
import com.zee.service.PaymentService;
import com.zee.service.SellerReportService;
import com.zee.service.SellerService;
import com.zee.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
	
	private final OrderService orderService;
	private final UserService userService;
	private final CartService cartService;
	private final SellerService sellerService;
	private final SellerReportService sellerReportService;
	private final PaymentService paymentService;
	private final PaymentOrderRepository paymentOrderRepository;
	
	
	@PostMapping()
	public ResponseEntity<PaymentLinkResponse> createOrderHandler(
			@RequestBody Address shippingAddress,
			@RequestParam String paymentMethod,
			@RequestHeader("Authorization") String jwt) throws Exception {
		
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartService.findUserCart(user);
		Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);
		
		PaymentOrder paymentOrder = paymentService.createOrder(user,orders);
		
		PaymentLinkResponse res = new PaymentLinkResponse();
		
		
		if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
			PaymentLink payment = paymentService.createRazorpayPaymentLink(user,
					paymentOrder.getAmount(),
					paymentOrder.getId());
			
			String paymentUrl = payment.get("short_url");
			String paymentUrlId = payment.get("id");
			
			res.setPayment_link_url(paymentUrl);
			
			paymentOrder.setPaymentLinkId(paymentUrl);
			paymentOrderRepository.save(paymentOrder);
		}
		else if(paymentMethod.equals(PaymentMethod.COD)) {
			String paymentUrl = "url";
			String paymentUrlId = "id";
			res.setPayemnt_link_id(paymentUrlId);
			res.setPayment_link_url(paymentUrl);
			res.setPayOnDelivery("your order will be sent to your address");
		}
		else {
			String paymentUrl = paymentService.createStripePaymentLink(user,
					paymentOrder.getAmount(),
					paymentOrder.getId());
			res.setPayment_link_url(paymentUrl);
		}
	
		
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	@GetMapping("/user")
		public ResponseEntity<List<Order>> userOrderHistoryHandler(
				@RequestHeader("Authorization") String jwt) throws Exception{
			
			User user = userService.findUserByJwtToken(jwt);
			List<Order> orders = orderService.userOrderHistory(user.getId());
			
			return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
			
		}


	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		Order orders = orderService.findOrderById(orderId);
		
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
		
	
	@GetMapping("/item/{orderItemId}")
	public ResponseEntity<OrderItem> getOrderItemById(
			@PathVariable Long orderItemId,
			@RequestHeader("Authorization")String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		OrderItem orderItem = orderService.getOrderItemById(orderItemId);
		
		return new ResponseEntity<OrderItem>(orderItem,HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrderHandler(
			@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		Order order = orderService.cancelOrder(orderId, user);
		
		Seller seller = sellerService.getSellerById(order.getSellerId());
		SellerReport report = sellerReportService.getSellerReport(seller);
		
		report.setCancelledOrders(report.getCancelledOrders()+1);
		report.setTotalRefunds(report.getTotalRefunds()+order.getTotalSellingPrice());
		sellerReportService.updateSellerReport(report);
		
		return ResponseEntity.ok(order);
		
		
	}
	
			

}
