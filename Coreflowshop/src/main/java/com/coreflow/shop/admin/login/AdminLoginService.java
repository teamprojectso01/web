package com.coreflow.shop.admin.login;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminLoginService {

	private final AdminLoginMapper adminLoginMapper;
	
	public AdminLoginVO login(String ad_userid) {
		return adminLoginMapper.login(ad_userid);
	}

}
