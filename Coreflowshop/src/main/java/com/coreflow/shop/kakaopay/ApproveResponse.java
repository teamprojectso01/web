package com.coreflow.shop.kakaopay;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApproveResponse {

	private String aid;
	private String tid;
	private String partner_order_id;
	private String partner_user_id;
	private String payment_method_type;
	
	private Amount amount;
	private CardInfo card_info;
	
	private String item_name;
	private String item_code;
	private Integer quantity;
	private Date created_at;
	private Date approved_at;
	private String payload;
}
