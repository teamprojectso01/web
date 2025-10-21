package com.coreflow.shop.admin.category;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.CategoryDTO;

//@Mapper
public interface AdCategoryMapper {
   // 1차 관라자 카테고리 목록 
	List<CategoryDTO>FirstCategorystList(); 
	List<CategoryDTO>SecondCategoryList(Integer firstCategoryNum);
	// 1차카테고리 정렬
	void arrayCategory(@Param("cate_code") Integer cate_code, @Param("order") Integer order );
	
	// 1차카테고리 등록
	void inputFirstCategory(String cate_name);
	
	// 1차카테고리 수정
	void modifyFirstCategory(CategoryDTO dto);
	
	// 2차카테고리 등록
	void plusSecondCategory(CategoryDTO dto);
	
	// 1차, 2차카테고리 삭제
	void deleteModifyCategory(Integer cate_code);
    
	void SecondModifyCategory(CategoryDTO dto);// 2차 카테고리 수정 
    
}




