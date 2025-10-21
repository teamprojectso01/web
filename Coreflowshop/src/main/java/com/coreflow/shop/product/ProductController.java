package com.coreflow.shop.product;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coreflow.shop.category.CategoryService;
import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.FileUtils;
import com.coreflow.shop.common.utils.PageMaker;
import com.coreflow.shop.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {
	
	@Value("${com.coreflow.upload.path}")
	private String uploadPath;
	
	private final ProductService productService;
	private final CategoryService categoryService;
	
	// 상품페이지
	@GetMapping("/pro_list")
	public void pro_list(SearchCriteria cri, @ModelAttribute("cate_name") String cate_name, @ModelAttribute("cate_code") Integer cate_code, Model model) throws Exception {
		
		model.addAttribute("firstCategoryList", categoryService.getFirstCategoryList());
		
		cri.setPerPageNum(6);
		
		List<ProductDTO> productList = productService.getProductList(cri, cate_code);
		model.addAttribute("productList", productList);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		pageMaker.setTotalCount(productService.getProductListCount(cate_code));
		model.addAttribute("pageMaker", pageMaker);
				
	}
	
	// 주문상품이미지 보여주기
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}

}
