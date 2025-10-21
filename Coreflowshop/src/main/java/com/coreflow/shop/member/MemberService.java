package com.coreflow.shop.member;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	private final MemberMapper memberMapper;
	
	public void join(MemberDTO dto) {
		memberMapper.join(dto);
	}

	public String idCheck(String mbsp_id) {
		return memberMapper.idCheck(mbsp_id);
	}
	
	public MemberDTO login(String mbsp_id) {
		return memberMapper.login(mbsp_id);
	}
	
	public MemberDTO modify(String mbsp_id) {
		return memberMapper.modify(mbsp_id);
	}
	
	public void modify_save(MemberDTO dto) {
		memberMapper.modify_save(dto);
	}
	
	public String idsearch(String mbsp_name, String mbsp_email) {
		return memberMapper.idsearch(mbsp_name, mbsp_email);	
	}
	
	public String pwtemp_comfirm(String mbsp_id, String mbsp_email) {
		return memberMapper.pwtemp_comfirm(mbsp_id, mbsp_email);
	}
	
	public void pwchange(String mbsp_id, String mbsp_password) {
		memberMapper.pwchange(mbsp_id, mbsp_password);
	}
	
	public void withdrawal(String mbsp_id) {
		memberMapper.withdrawal(mbsp_id);
	}
	
}
