package com.coreflow.shop.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coreflow.shop.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

/**
 * [카테고리 서비스 인터페이스]
 * - 서비스 계층의 규칙(메서드 시그니처)만 정의
 * - 실제 동작은 ServiceImpl 클래스에서 구현
 */ 

@RequiredArgsConstructor 
@Service
public class AdCategoryService {
    private final AdCategoryMapper adCategoryMapper;
   
  //1차 관리자 카테고리 
    public List<CategoryDTO>FirstCategorystList() {
    	return adCategoryMapper.FirstCategorystList();
    }

	public List<CategoryDTO> SecondCategoryList(Integer firstCategoryNum){
		return adCategoryMapper.SecondCategoryList(firstCategoryNum);
	}

	@Transactional
	public void arrayCategory(List<Integer> orderArr) {
		for(int i=0; i<orderArr.size(); i++) {
			Integer cate_code = orderArr.get(i);
			Integer order = (i + 1);
			adCategoryMapper.arrayCategory(cate_code, order);
		}
		}
		public void inputFirstCategory(String cate_name) {
			adCategoryMapper.inputFirstCategory(cate_name);
		}
	
		public void modifyFirstCategory(CategoryDTO dto) {
			adCategoryMapper.modifyFirstCategory(dto);
		}
		
		public void addSecondCategory(CategoryDTO dto) {
			adCategoryMapper.plusSecondCategory(dto);
		}
		
		public void secondModifyCategory(CategoryDTO dto) {
			adCategoryMapper.modifyFirstCategory(dto);
		}
		
		public void deleteModifyCategory(Integer cate_code) {
			adCategoryMapper.deleteModifyCategory(cate_code);
		}
}
