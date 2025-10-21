package com.coreflow.shop.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {

	private final CartMapper cartMapper;
	
	public void cart_add(CartDTO dto) {
		cartMapper.cart_add(dto);
	}
	
	public List<Map<String, Object>> cart_list(String mbsp_id) {
		return cartMapper.cart_list(mbsp_id);
	}
	
	public Integer cart_total_price(String mbsp_id) {
		return cartMapper.cart_total_price(mbsp_id);
	}
	
	public void cart_empty(String mbsp_id) {
		cartMapper.cart_empty(mbsp_id);
	}
	
	public void cart_sel_delete(int[] check, String mbsp_id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pro_num_arr", check);
		map.put("mbsp_id", mbsp_id);
		cartMapper.cart_sel_delete(map);
	}
	
	public void cart_quantity_change(CartDTO dto) {
		cartMapper.cart_quantity_change(dto);
	}
}
