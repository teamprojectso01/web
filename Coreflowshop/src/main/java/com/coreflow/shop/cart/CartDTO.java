package com.coreflow.shop.cart;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDTO {

	private int pro_num;
	private String mbsp_id;
	private int cart_amount;
	private Date cart_date;
}

