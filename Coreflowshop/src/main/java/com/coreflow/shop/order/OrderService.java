package com.coreflow.shop.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coreflow.shop.cart.CartDTO;
import com.coreflow.shop.cart.CartMapper;
import com.coreflow.shop.common.dto.OrderDTO;
import com.coreflow.shop.common.dto.PaymentDTO;
import com.coreflow.shop.common.dto.ShippingDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderMapper orderMapper;
	private final PaymentMapper paymentMapper;
	private final ShippingMapper shippingMapper;
	private final CartMapper cartMapper;
	
	@Transactional
	public void order_process(OrderDTO dto, String p_method, String account_transfer, String sender) {
		orderMapper.order_insert(dto);
		
		String p_method_info = "";
		String payment_status = "";
		if(!p_method.equals("KakaoPay")) {
			p_method_info = p_method + "/" + account_transfer + "/" + sender;
			payment_status = "입금미납";
			
		}else {
			p_method_info = p_method;
			payment_status = "입금완료";
		}
		
		dto.setOrd_status(payment_status);
		orderMapper.order_item_insert(dto.getOrd_code(), dto.getMbsp_id());
		
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setOrd_code(dto.getOrd_code());
		paymentDTO.setMbsp_id(dto.getMbsp_id());
		paymentDTO.setPayment_method(p_method_info);
		paymentDTO.setPayment_price(dto.getOrd_price());
		paymentDTO.setPayment_status(payment_status);
		
		paymentMapper.payment_insert(paymentDTO);
		
		ShippingDTO shippingDTO = new ShippingDTO();
		shippingDTO.setOrd_code(dto.getOrd_code());
		shippingDTO.setShipping_recipient(dto.getOrd_name());
		shippingDTO.setShipping_phone(dto.getOrd_tel());
		shippingDTO.setShipping_zipcode(dto.getOrd_addr_zipcode());
		shippingDTO.setShipping_address(dto.getOrd_addr_basic());
		shippingDTO.setShipping_address2(dto.getOrd_addr_detail());
		
		shippingMapper.shipping_insert(shippingDTO);
		
		cartMapper.cart_empty(dto.getMbsp_id());
	}
	
	public List<Map<String, Object>> order_result(Integer ord_code) {
		return orderMapper.order_result(ord_code);
	}
}
