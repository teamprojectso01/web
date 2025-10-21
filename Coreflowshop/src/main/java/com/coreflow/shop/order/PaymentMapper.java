package com.coreflow.shop.order;

import com.coreflow.shop.common.dto.PaymentDTO;

public interface PaymentMapper {

	void payment_insert(PaymentDTO dto);
}
