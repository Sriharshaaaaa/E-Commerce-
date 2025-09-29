package com.zee.model;


import com.zee.dto.PaymentStatus;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDetails {

	private String paymentId;
	private String razorpayPaymentLinkId;
	private String razprpayPaymentLinkReferenceId;
	private String razorpayPaymentLinkStatus;
	private String razorpayPaymentId;
	private PaymentStatus status;

}
