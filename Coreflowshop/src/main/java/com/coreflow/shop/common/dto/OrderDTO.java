package com.coreflow.shop.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {

	private Integer ord_code;
	private String mbsp_id;
	private String ord_name;
	private String ord_addr_zipcode;
	private String ord_addr_basic;
	private String ord_addr_detail;
	private String ord_tel;
	private String ord_mail;
	private int ord_price;
	private String ord_status;
	private Date ord_regdate;
	private String ord_message;
}
