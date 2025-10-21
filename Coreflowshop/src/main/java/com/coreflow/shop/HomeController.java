package com.coreflow.shop;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coreflow.shop.category.CategoryService;
import com.coreflow.shop.common.dto.CategoryDTO;
import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.FileUtils;
import com.coreflow.shop.product.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final CategoryService categoryService;
	private final ProductService productService;
	
	@Value("${com.coreflow.upload.path}")
	private String uploadPath;	
	
	// 기본페이지
	@GetMapping("/")
	public String home(Model model) {
		
		List<CategoryDTO> firstCategoryList = categoryService.getFirstCategoryList();
		model.addAttribute("firstCategoryList", firstCategoryList);
		
		// 추천상품
		List<ProductDTO> recommendList = productService.getRecommendList();
		model.addAttribute("recommendList", recommendList);
		
		return "index";
	}
	
	// 주문상품이미지 보여주기
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
	@GetMapping("/sub1")
	public String sub1() {
		
		return "sub1";
	}
	
	@GetMapping("/sub2")
	public String sub2() {
		
		return "sub2";
	}
	
}
