package com.coreflow.shop.admin.payment;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.PaymentDTO;

public interface AdminPaymentMapper {
	
	void payment_status_change(@Param("ord_code") Integer ord_code, @Param("payment_status") String payment_status);
	
	PaymentDTO payment_info(Integer ord_code);
	
	void payment_price_change(@Param("ord_code") Integer ord_code, @Param("unit_price") Integer unitprice);
}
