package com.coreflow.shop.common.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShippingDTO {

	private Long delivery_code;
	private Integer ord_code;
	private String shipping_recipient;
	private String shipping_phone;
	private String shipping_zipcode;
	private String shipping_address;
	private String shipping_address2;
	private LocalDateTime delivery_date;
	private String delivery_status;
}
