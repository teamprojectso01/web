package com.coreflow.shop.admin.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coreflow.shop.admin.payment.AdminPaymentService;
import com.coreflow.shop.common.dto.MemberDTO;
import com.coreflow.shop.common.dto.OrderDTO;
import com.coreflow.shop.common.dto.PaymentDTO;
import com.coreflow.shop.common.utils.FileUtils;
import com.coreflow.shop.common.utils.PageMaker;
import com.coreflow.shop.common.utils.SearchCriteria;
import com.coreflow.shop.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/order/*")
public class AdminOrderController {

	private final AdminOrderService adminOrderService;
	private final AdminPaymentService adminPaymentService;
	private final MemberService memberService;
	
	@Value("${com.coreflow.upload.path}")
	private String uploadPath;
	
	// 관리자상품목록페이지
	@GetMapping("/order_list")
	public void order_list(@ModelAttribute("cri") SearchCriteria cri, @ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, 
						   @ModelAttribute("end_date")String end_date,	@ModelAttribute("payment_method") String payment_method, @ModelAttribute("ord_status") String ord_status, Model model) {
		
		cri.setPerPageNum(2);
		List<Map<String,Object>> order_list = adminOrderService.order_list(cri, period, start_date, end_date, payment_method, ord_status);
		model.addAttribute("order_list", order_list);
		
		int total_count = adminOrderService.total_count(cri, period, start_date, end_date, payment_method, ord_status);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(total_count);
		model.addAttribute("pageMaker", pageMaker);
	}
	
	// 주문상태변경버튼
	@PostMapping("/order_status_change")
	public ResponseEntity<String> order_status_change(Integer ord_code, String ord_status) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminOrderService.order_status_change(ord_code, ord_status);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 상세보기버튼
	@GetMapping("/orderdetail_info")
	public void orderdetail_info(Integer ord_code, Model model) throws Exception {
		
		// 주문내역
		List<Map<String,Object>> order_product_info = adminOrderService.order_product_info(ord_code);
		model.addAttribute("order_product_info", order_product_info);
		
		// 주문결제내역
		PaymentDTO payment_info = adminPaymentService.payment_info(ord_code);
		model.addAttribute("payment_info", payment_info);
		
		// 배송지정보
		OrderDTO order_info = adminOrderService.order_info(ord_code);
		model.addAttribute("order_info", order_info);
		
		// 주문자정보
		String mbsp_id = order_info.getMbsp_id();
		MemberDTO memberDTO = memberService.login(mbsp_id);
		model.addAttribute("member_info", memberDTO);
		log.info("회원 아이디: " + mbsp_id);
	}
	
	// 주문내역개별삭제
	@PostMapping("/order_product_del")
	public ResponseEntity<String> order_product_del(Integer ord_code, Integer pro_num, Integer unit_price) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminOrderService.order_product_del(ord_code, pro_num, unit_price);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
	
	
	// 주문상품이미지 보여주기
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
}
