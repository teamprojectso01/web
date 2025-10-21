package com.coreflow.shop.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDTO {

	private Integer cate_code;
	private int cate_prtcode;
	private String cate_name; 
	private int cate_order;
	
}
