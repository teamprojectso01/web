package com.coreflow.shop.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartMapper {
	void cart_add(CartDTO dto);
	
	 List<Map<String, Object>> cart_list(String mbsp_id);
	 
	 Integer cart_total_price(String mbsp_id);
	 
	 void cart_empty(String mbsp_id);
	 
	 void cart_sel_delete(HashMap<String, Object> map);
	 
	 void cart_quantity_change(CartDTO dto);
}
