package com.zee.service.impl;

import java.util.Set;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.zee.dto.PaymentOrderStatus;
import com.zee.dto.PaymentStatus;
import com.zee.model.Order;
import com.zee.model.PaymentOrder;
import com.zee.model.User;
import com.zee.repository.OrderRepository;
import com.zee.repository.PaymentOrderRepository;
import com.zee.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentOrderRepository paymentOrderRepository;
	private final OrderRepository orderRepository;
	
	// razorpay credantials
	private String apiKey = "apiKey";
	private String apiSecret = "apiSecret";
	
	// stripw credantials
	private String stripeSecretKey = "stripesecretKey";
	
	
	@Override
	public PaymentOrder createOrder(User user, Set<Order> orders) {
		Long amount = orders.stream().mapToLong(Order::getTotalSellingPrice).sum();
		
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setAmount(amount);
		paymentOrder.setUser(user);
		paymentOrder.setOrders(orders);
	 
		return paymentOrderRepository.save(paymentOrder);
	}

	@Override
	public PaymentOrder getPaymentOrderById(Long orderId) throws Exception {
		
		return paymentOrderRepository.findById(orderId).orElseThrow(() ->
		new Exception("payment order not foun"));
	}

	@Override
	public PaymentOrder getPaymentOrderByPaymentId(String paymentLinkId) throws Exception {
		
		PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentLinkId);
		if(paymentOrder == null) {
			throw new Exception("payment order not found with payment link id");
		}
		return paymentOrder;
	}

	@Override
	public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String PaymrntId, String PaymentLinkId) throws RazorpayException {
		
		if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
			RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);
			
			Payment payment = razorpay.payments.fetch(PaymrntId);
			
			String status = payment.get("status");
			if(status.equals("captured")) {
				Set<Order> orders = paymentOrder.getOrders();
				for(Order order:orders) {
					order.setPaymentStatus(PaymentStatus.COMPLETED);
					orderRepository.save(order);
				}
				paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
				paymentOrderRepository.save(paymentOrder);
				return true;
			}
			paymentOrder.setStatus(PaymentOrderStatus.FAILED);
			paymentOrderRepository.save(paymentOrder);
			return false;
		}
		return false;
	}
	
	// razprPay...................

	@Override
	public PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException {
		amount = amount*100; // becouse in razorpay amount is given in paisa not rupees
		
		try {
			RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount",amount);
			paymentLinkRequest.put("currency","INR");
			
			JSONObject customer = new JSONObject();
			customer.put("name",user.getFullName());
			customer.put("email",user.getEmail());
			
			paymentLinkRequest.put("customer",customer);
			
			JSONObject notify = new JSONObject();
			notify.put("email",true);
			paymentLinkRequest.put("notify",notify);
			
			// after successfull payment it will be redirected to given front end url
			paymentLinkRequest.put("callback_url","http://localhost:3000/payment-success"+orderId);	
			paymentLinkRequest.put("callback_method","get");
			
			
			PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);
			
			String paymentLinkUrl = paymentLink.get("short_url");
			String paymentLinkId = paymentLink.get("id");
			
			return paymentLink;
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new RazorpayException(e.getMessage());
		}
		
		
	}
	
	// Stripe.................... 

	@Override
	public String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
		Stripe.apiKey = stripeSecretKey;
		
		SessionCreateParams params =  SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl("http://localhost:3000/payment-success"+orderId)
				.setCancelUrl("http://localhost:3000/payment-cancel")
				.addLineItem(SessionCreateParams.LineItem.builder()
						.setQuantity(1L)
						.setPriceData(SessionCreateParams.LineItem.PriceData.builder()
								.setCurrency("usd")
								.setUnitAmount(amount*100)
								.setProductData(
										SessionCreateParams
											.LineItem.PriceData.ProductData.builder()
											.setName("ComZee payment")
											.build())
								.build())
						.build())
				.build();
		
		Session session = Session.create(params);
		return session.getUrl();
	}

}
