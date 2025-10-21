package com.coreflow.shop.admin.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.SearchCriteria;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ★역할: 상품 관련 비즈니스 로직 구현
 * - Controller ↔ Service ↔ Mapper(DAO) 구조에서 중간 계층
 */
@Service                         // ★ 스프링의 서비스 컴포넌트 등록
@RequiredArgsConstructor         // ★ final 필드 생성자 자동 생성
public class AdProductService {

 

    private final AdProductMapper adProductMapper;

  
//    //서비스매퍼 자바의 ProductDTO 받음
//    public void pro_insert(ProductDTO dto) {
//    	adProductMapper.pro_insert(dto);
//    }
    //상품목록조회
 
    public List<Map<String,Object>> pro_list(
            SearchCriteria cri, String period, String start_date, String end_date, String cate_code) {
        return adProductMapper.pro_list(cri, period, start_date, end_date, cate_code);
    }

    public int getTotalcount(
            SearchCriteria cri, String period, String start_date, String end_date, String cate_code) {
        return adProductMapper.getTotalCount(cri, period, start_date, end_date, cate_code);
    }
    
    //상품수정저장 productDTO의 데이터를 db에 반영
    public void pro_edit_modify(ProductDTO dto) {
		adProductMapper.pro_edit_modify(dto);
	}
	
    // 단일 상품삭제 
    public void pro_delete(Integer pro_num) {
    	adProductMapper.pro_delete(pro_num);
		
    }    
    
    /*선택 상품 삭제 + 베열*/
    public void pro_sel_delete_2(int[] pro_num_arr) {
    	adProductMapper.pro_sel_delete_2(pro_num_arr);
    }  
    
    // 상품번호 +상품명 조건으로 삭제 
    public void pro_sel_delete_add_info(int[] check, String pro_name) {
    	
    	HashMap<String,Object> map = new HashMap<>();
    	map.put("pro_num_arr", check);
    	map.put("pro_name", pro_name); 
    	
    	adProductMapper.pro_sel_delete_add_info(map);
    }

    public void pro_insert(ProductDTO dto) {
		adProductMapper.pro_insert(dto);
	}

	public ProductDTO pro_edit_form(Integer pro_num) {
		return adProductMapper.pro_edit_form(pro_num);
	}
	
	

	
	
    
}
