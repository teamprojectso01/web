package com.coreflow.shop.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.OrderDTO;

public interface OrderMapper {
	void order_insert(OrderDTO dto);
	
	void order_item_insert(@Param("ord_code") Integer ord_code,@Param("mbsp_id") String mbsp_id);
	
	List<Map<String, Object>> order_result(Integer ord_code);
}
