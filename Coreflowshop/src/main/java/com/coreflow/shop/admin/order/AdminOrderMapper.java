package com.coreflow.shop.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.OrderDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

public interface AdminOrderMapper {

	List<Map<String, Object>> order_list(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, 
										 @Param("end_date") String end_date, @Param("payment_method") String payment_method, @Param("ord_status") String ord_status);
	
	int total_count(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, 
					@Param("end_date") String end_date, @Param("payment_method") String payment_method, @Param("ord_status") String ord_status);
	
	void order_status_change(@Param("ord_code") Integer ord_code, @Param("ord_status") String ord_status);
	
	List<Map<String, Object>> order_product_info(Integer ord_code);
	
	OrderDTO order_info(Integer ord_code);
	
	void order_product_del(@Param("ord_code") Integer ord_code, @Param("pro_num") Integer pro_num);
	
	void order_price_change(@Param("ord_code") Integer ord_code, @Param("unit_price") Integer unit_price);
}
