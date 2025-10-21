package com.coreflow.shop.cart;

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

import com.coreflow.shop.common.dto.MemberDTO;
import com.coreflow.shop.common.utils.FileUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart/*")
public class CartController {

	@Value("${com.coreflow.upload.path}")
	private String uploadPath;
	private final CartService cartService;
	
	// 장바구니추가
	@PostMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartDTO dto, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
		cartService.cart_add(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 장바구니목록페이지
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		
		List<Map<String, Object>> cart_list = cartService.cart_list(mbsp_id);
		model.addAttribute("cart_list", cart_list);
		
		model.addAttribute("cart_total_price", cartService.cart_total_price(mbsp_id));
	}
	
	// 장바구니비우기
	@GetMapping("/cart_empty")
	public String cart_empty(HttpSession session) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		
		cartService.cart_empty(mbsp_id);
		
		return "redirect:/cart/cart_list";
	}
	
	// 상품선택삭제
	@PostMapping("/cart_sel_delete")
	public String cart_sel_delete(int[] check, HttpSession session) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		
		cartService.cart_sel_delete(check, mbsp_id);
		
		return "redirect:/cart/cart_list";
	}
	
	// 상품수량변경
	@GetMapping("/cart_quantity_change")
	public String cart_quantity_change(CartDTO dto, HttpSession session) throws Exception {
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
	    cartService.cart_quantity_change(dto);
	    
	    return "redirect:/cart/cart_list";
	}
	
	// 상품수량변경(ajax)
	@PostMapping("/cart_quantity_change_2")
	public ResponseEntity<String> cart_quantity_change_2(CartDTO dto, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		dto.setMbsp_id(mbsp_id);
		
	    cartService.cart_quantity_change(dto);
	    
	    entity = new ResponseEntity<String>("success", HttpStatus.OK);
	    
	    return entity;
	}
	
	// 상품이미지출력
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
}
