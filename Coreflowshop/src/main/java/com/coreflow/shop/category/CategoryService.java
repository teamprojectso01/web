package com.coreflow.shop.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryMapper categoryMapper;
	
	public List<CategoryDTO> getFirstCategoryList() {
		return categoryMapper.getFirstCategoryList();
	}
	
	public List<CategoryDTO> getSecondCategoryList(Integer firstCategoryCode){
		return categoryMapper.getSecondCategoryList(firstCategoryCode);
	}
}
