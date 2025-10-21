package com.coreflow.shop.admin.member;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.MemberDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

public interface AdMemberMapper {
	
	List<MemberDTO> member_list(@Param("cri") SearchCriteria cri, @Param("genderType") String genderType, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date);
	
	int getTotalCount(@Param("cri") SearchCriteria cri, @Param("genderType") String genderType, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date);

	void member_sel_delete_1(String[] member_id_arr);
	
	void member_delete(String mbsp_id);
	
	MemberDTO member_edit_form(String mbsp_id);
	
	void member_edit_save(MemberDTO dto);
	
}
