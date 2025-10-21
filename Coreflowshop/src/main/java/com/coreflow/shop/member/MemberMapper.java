package com.coreflow.shop.member;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.MemberDTO;

public interface MemberMapper {

	
	// 회원정보저장
	void join(MemberDTO dto);

	// 아이디 체크
	String idCheck(String mbsp_id);
	
	// 로그인 
	MemberDTO login(String mbsp_id);
	
	// 정보수정
	MemberDTO modify(String mbsp_id);
	
	// 정보수정 저장
	void modify_save(MemberDTO dto);
	
	// 아이디 찾기
	String idsearch(@Param("mbsp_name") String mbsp_name, @Param("mbsp_email") String mbsp_email);
	
	// 임시비밀번호를 발급하기 위한 아이디와 메일주소 존재여부 체크 
	String pwtemp_comfirm(@Param("mbsp_id") String mbsp_id, @Param("mbsp_email") String mbsp_email);
	
	// 비밀번호 변경(for. 임시비밀번호)
	void pwchange(@Param("mbsp_id") String mbsp_id, @Param("mbsp_password") String mbsp_password);
	
	// 회원탈퇴하기
	void withdrawal(String mbsp_id);
	
}
