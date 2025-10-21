package com.coreflow.shop.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coreflow.shop.cart.CartDTO;
import com.coreflow.shop.cart.CartService;
import com.coreflow.shop.common.dto.OrderDTO;
import com.coreflow.shop.common.utils.FileUtils;

import com.coreflow.shop.mail.EmailService;
import com.coreflow.shop.member.MemberService;

import jakarta.servlet.http.HttpSession;

import com.coreflow.shop.common.dto.EmailDTO;
import com.coreflow.shop.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order/*")
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final EmailService emailService;
	private final MemberService memberService;
	
	@Value("${com.coreflow.upload.path}")
	private String uploadPath;
	
	// 주문정보페이지
	@GetMapping("/order_info")
	public void order_info(CartDTO dto, String type, HttpSession session, Model model) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();

		dto.setMbsp_id(mbsp_id);
	
		if(type.equals("buy")) cartService.cart_add(dto);
		
		List<Map<String, Object>> orderDetails = cartService.cart_list(mbsp_id);
		model.addAttribute("orderDetails", orderDetails);
		
		String item_name = "";
		if(orderDetails.size() == 1) {
			item_name = (String) orderDetails.get(0).get("pro_name");
		}else {
			item_name = (String) orderDetails.get(0).get("pro_name") + " 외" +  (orderDetails.size() - 1) + "건";
		}
		
		model.addAttribute("item_name", item_name);
		model.addAttribute("quantity", orderDetails.size());
		
		model.addAttribute("order_total_price", cartService.cart_total_price(mbsp_id));
		
		MemberDTO memberDTO = memberService.modify(mbsp_id);
		model.addAttribute("memberDTO", memberDTO);
		
		
		
	}
	
	// 주문정보저장
	@PostMapping("/order_save")
	public String order_save(OrderDTO dto, String p_method, String account_transfer, String sender, HttpSession session, RedirectAttributes rttr) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		
		log.info("메일주소: " + dto.getOrd_mail());

		
		orderService.order_process(dto, p_method, account_transfer, sender);
		
		rttr.addAttribute("ord_code", dto.getOrd_code());
		rttr.addAttribute("ord_mail", dto.getOrd_mail());
		
		return "redirect:/order/order_result";
	}
	
	// 주문결과페이지
	int order_total_price = 0;
	@GetMapping("/order_result")
	public void order_result(Integer ord_code, String ord_mail, Model model) throws Exception {
		
		List<Map<String,Object>> order_result = orderService.order_result(ord_code);
		model.addAttribute("order_result", order_result);
		
		 order_result.forEach(o_result -> {
			 order_total_price += ((int) o_result.get("oi_price") * (int) o_result.get("oi_quantity"));
		 });

		 EmailDTO dto = new EmailDTO("CoreflowShop", "CoreflowShop", ord_mail, "주문내역", "주문내역");
		 emailService.sendMail("mail/orderConfirmation", dto, order_result, order_total_price);
		 
		 model.addAttribute("order_total_price", order_total_price);
	}
	
	// 상품이미지출력
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
}
	
