package com.zee.service;

import java.util.Set;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.zee.model.Order;
import com.zee.model.PaymentOrder;
import com.zee.model.User;

public interface PaymentService {

	PaymentOrder createOrder(User user, Set<Order> orders);
	PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
	PaymentOrder getPaymentOrderByPaymentId(String paymentLinkId) throws Exception;
	Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String PaymrntId, String PaymentLinkId) throws RazorpayException;
	
	PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException;
	String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}
