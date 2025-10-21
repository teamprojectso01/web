package com.coreflow.shop.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coreflow.shop.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/category/*")
@RequiredArgsConstructor
@Controller
public class CategoryController {

	private final CategoryService categoryService;
	
	// 2차카테고리목록
	@GetMapping("/secondCategoryList/{firstCategoryCode}")
	public ResponseEntity<List<CategoryDTO>>
	secondCategoryList(@PathVariable("firstCategoryCode") Integer firstCategoryCode) throws Exception {
		
		ResponseEntity<List<CategoryDTO>> entity = null;
		
		List<CategoryDTO> subCategoryList = categoryService.getSecondCategoryList(firstCategoryCode);
		
		entity = new ResponseEntity<List<CategoryDTO>>(subCategoryList, HttpStatus.OK);
		
		return entity;
		
	}
	
}
