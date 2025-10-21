package com.coreflow.shop.category;

import java.util.List;

import com.coreflow.shop.common.dto.CategoryDTO;

public interface CategoryMapper {
	
	List<CategoryDTO> getFirstCategoryList();
	
	List<CategoryDTO> getSecondCategoryList(Integer firstCategoryCode);
}
