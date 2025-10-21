package com.coreflow.shop.admin.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.SearchCriteria;




public interface AdProductMapper {
  // dto,xml다음으로 입력  
	void pro_insert(ProductDTO dto);  
	
	List<Map<String, Object>>pro_list(
			@Param("cri") SearchCriteria cri,
			@Param("period") String period,
			@Param("start_date") String start_date,
			@Param("end_date") String end_date,
			@Param("cate_code")String cate_code
			
			
			);  
	
	
	
	int getTotalCount(@Param("cri") SearchCriteria cri,
			@Param("period") String period,
			@Param("start_date") String start_date,
			@Param("end_date") String end_date,
			@Param("cate_code") String cate_code
			); 
	
	
	
	 /*선택 상품 삭제 + 배열삭제*/  
	void pro_sel_delete_2( @Param("pro_num_arr")  int[] pro_num_arr); 
    
	void pro_sel_delete_add_info(@Param("map")  HashMap<String, Object> map); // key 로 조회 해서 value 저장해서 삭제

	
	//상품수정 
	ProductDTO pro_edit_form(@Param("pro_num") Integer pro_num);
	
	//저장
	void pro_edit_modify(ProductDTO dto);
	//삭제
	void pro_delete(@Param("pro_num") Integer pro_num);

	
	
     
}


