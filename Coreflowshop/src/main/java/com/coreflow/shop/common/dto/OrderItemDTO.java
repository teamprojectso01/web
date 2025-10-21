package com.coreflow.shop.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemDTO {
	
	private Integer ord_code;
	private Integer pro_num;
	private int oi_quantity;
	private int oi_price;
}
