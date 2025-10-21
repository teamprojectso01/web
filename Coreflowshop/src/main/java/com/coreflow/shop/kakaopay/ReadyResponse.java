package com.coreflow.shop.kakaopay;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReadyResponse {

	private String tid;
	private String next_redirect_pc_url;
	private Date created_at;
}
