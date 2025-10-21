package com.coreflow.shop.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coreflow.shop.admin.payment.AdminPaymentMapper;
import com.coreflow.shop.common.dto.OrderDTO;
import com.coreflow.shop.common.dto.PaymentDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

	private final AdminOrderMapper adminOrderMapper;
	private final AdminPaymentMapper adminpaymentMapper;
	
	public List<Map<String, Object>> order_list(SearchCriteria cri, String period, String start_date, String end_date, String payment_method, String ord_status) {
		return adminOrderMapper.order_list(cri, period, start_date, end_date, start_date, end_date);
	}
	
	public int total_count(SearchCriteria cri, String period, String start_date, String end_date, String payment_method, String ord_status) {
		return adminOrderMapper.total_count(cri, period, start_date, end_date, start_date, end_date);
	}
	
	@Transactional
	public void order_status_change(Integer ord_code, String ord_status) {
		adminOrderMapper.order_status_change(ord_code, ord_status);
		
		if(ord_status == "입금완료") {
			adminpaymentMapper.payment_status_change(ord_code, ord_status);
		}
	}
	
	public List<Map<String, Object>> order_product_info(Integer ord_code) {
		return adminOrderMapper.order_product_info(ord_code);
	}
	
	public OrderDTO order_info(Integer ord_code) {
		return adminOrderMapper.order_info(ord_code);
	}
	
	@Transactional
	public void order_product_del(Integer ord_code, Integer pro_num, Integer unit_price) {
		
		adminOrderMapper.order_product_del(ord_code, pro_num);
		adminOrderMapper.order_price_change(ord_code, unit_price);
		adminpaymentMapper.payment_price_change(ord_code, unit_price);
	}
}
