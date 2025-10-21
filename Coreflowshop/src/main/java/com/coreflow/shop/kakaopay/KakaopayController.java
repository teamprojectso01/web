package com.coreflow.shop.kakaopay;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coreflow.shop.common.dto.MemberDTO;
import com.coreflow.shop.common.dto.OrderDTO;
import com.coreflow.shop.order.OrderService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/kakao/*")
public class KakaopayController {

	private final KakaopayService kakaopayService;
	private final OrderService orderService;
	private OrderDTO dto;
	
	// 주문정보저장
	@PostMapping("/kakaoPay")
	public ResponseEntity<ReadyResponse> kakaoPay(OrderDTO dto, String item_name, int quantity, HttpSession session) {
		
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		this.dto = dto;
		ResponseEntity<ReadyResponse> entity = null;
		
		String partner_order_id = "KDMall-" + mbsp_id + "-" + new Date().toString();
		
		ReadyResponse readyResponse = kakaopayService.ready(partner_order_id, mbsp_id, item_name, quantity, dto.getOrd_price(), 0);
		
		entity = new ResponseEntity<ReadyResponse>(readyResponse, HttpStatus.OK);
		
		return entity;
	}
	
	// 결제승인매핑주소
	@GetMapping("/approval")
	public String approval(String pg_token, RedirectAttributes rttr) {
		
		boolean isPaymentApprove = kakaopayService.approve(pg_token);
		
		String url = "";
		if(isPaymentApprove == true) {
			orderService.order_process(dto, "KakaoPay", null, null);
			
			rttr.addAttribute("ord_code", dto.getOrd_code());
			rttr.addAttribute("ord_mail", dto.getOrd_mail());
			
			url = "/order/order_result";
		}else
			url = "/order/order_fail_result";
		
		return "redirect:" + url;
	}
	
	// 결제취소매핑주소
	@GetMapping("/cancel")
	public String cancel() {
		
		return "/order/order_cancel";
	}
		
	// 결제실패매핑주소
	@GetMapping("/fail")
	public String fail() {
		
		return "/order/order_fail";
	}
}
