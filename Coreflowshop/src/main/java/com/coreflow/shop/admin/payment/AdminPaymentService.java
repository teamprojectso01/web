package com.coreflow.shop.admin.payment;

import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.PaymentDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPaymentService {

	private final AdminPaymentMapper adminPaymentMapper;
	
	public PaymentDTO payment_info(Integer ord_code) {
		return adminPaymentMapper.payment_info(ord_code);
	}
}
