package com.coreflow.shop.admin.login;

import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface AdminLoginMapper {

	AdminLoginVO login(String ad_userid);
}
