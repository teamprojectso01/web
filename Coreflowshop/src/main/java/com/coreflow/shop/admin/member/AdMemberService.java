package com.coreflow.shop.admin.member;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.MemberDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdMemberService {
	
	private final AdMemberMapper adMemberMapper; 
	
	public List<MemberDTO> member_list(SearchCriteria cri, String genderType, String period, String start_date, String end_date) {
		return adMemberMapper.member_list(cri, genderType, period, start_date, end_date);
	}
	
	public int getTotalCount(SearchCriteria cri, String genderType, String period, String start_date, String end_date) {
		return adMemberMapper.getTotalCount(cri, genderType, period, start_date, end_date);
	}
	
	public void member_sel_delete_1(String[] member_id_arr) {
		adMemberMapper.member_sel_delete_1(member_id_arr);
	}
	
	public void member_delete(String mbsp_id) {
		adMemberMapper.member_delete(mbsp_id);
	}
	
	public MemberDTO member_edit_form(String mbsp_id) {
		return adMemberMapper.member_edit_form(mbsp_id);
	}
	
	public void member_edit_save(MemberDTO dto) {
		adMemberMapper.member_edit_save(dto);
	}
	
}
