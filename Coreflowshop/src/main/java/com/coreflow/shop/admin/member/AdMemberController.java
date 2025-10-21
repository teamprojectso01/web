package com.coreflow.shop.admin.member;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coreflow.shop.common.constants.Constants;
import com.coreflow.shop.common.dto.MemberDTO;
import com.coreflow.shop.common.utils.PageMaker;
import com.coreflow.shop.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/member/*")
public class AdMemberController {
	
	private final AdMemberService adMemberService;

	
	// 회원목록
	@GetMapping("/member_list")
	public void member_list(@ModelAttribute("cri") SearchCriteria cri,@ModelAttribute("genderType") String genderType, @ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, @ModelAttribute("end_date") String end_date, Model model) throws Exception {
		
		//1)회원목록
		List<MemberDTO> member_list = adMemberService.member_list(cri, genderType, period, start_date, end_date);
		model.addAttribute("member_list", member_list);
		
		
		// 2) 페이징 정보
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setDisplayPageNum(Constants.ADMIN_MEMBER_LIST_PAGESIZE);
		
		pageMaker.setCri(cri); // cri 기억장소에 page=1, perPageNum=10, searchType=null, keyword=null 4개의 필드
		pageMaker.setTotalCount(adMemberService.getTotalCount(cri, genderType, period, start_date, end_date));
		
		model.addAttribute("pageMaker", pageMaker);
	
	return;
	}
	
	// 선택회원삭제1(ajax용)
	@PostMapping("/member_sel_delete_1")
	public ResponseEntity<String> member_sel_delete_1(@RequestParam("member_id_arr") String[] member_id_arr) throws Exception {
		ResponseEntity<String> entity = null;
		
		// 선택회원 삭제
		adMemberService.member_sel_delete_1(member_id_arr);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);		
		
		return entity;
	}
	
	@GetMapping("/member_delete")
	public String member_delete(SearchCriteria cri, String mbsp_id, String genderType, String period, String start_date, String end_date, RedirectAttributes rttr) throws Exception {
	
		adMemberService.member_delete(mbsp_id);
		
		// 원래상태의 목록으로 주소이동 작업
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("genderType", genderType);
		rttr.addAttribute("period", period);
		rttr.addAttribute("start_date", start_date);
		rttr.addAttribute("end_date", end_date);
					
	// http://localhost:8888/admin/member/member_list?page=1&perPageNum=10&searchType=	
		return "redirect:/admin/member/member_list";
	}
	
	// 회원정보 수정폼
	@GetMapping("/member_edit")
	public void member_edit(@ModelAttribute("cri") SearchCriteria cri, String mbsp_id, Model model) throws Exception {
		
		MemberDTO memberDTO = adMemberService.member_edit_form(mbsp_id);
		model.addAttribute("memberDTO", memberDTO);	
	}
	
	// 회원정보 수정받기
	@PostMapping("/member_edit")
	public String member_edit_save(MemberDTO dto, RedirectAttributes rttr) throws Exception {
		
		adMemberService.member_edit_save(dto);
		
//		rttr.addAttribute("", "");
		
		return "redirect:/admin/member/member_list";
	}
	
	
	
	
	





}
