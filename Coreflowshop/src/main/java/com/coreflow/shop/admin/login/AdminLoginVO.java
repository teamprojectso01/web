package com.coreflow.shop.admin.login;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminLoginVO {

	
	private String ad_userid;
	private String ad_passwd;
	private Date login_date;
	
	
}
