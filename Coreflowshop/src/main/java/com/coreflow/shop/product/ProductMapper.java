package com.coreflow.shop.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

public interface ProductMapper {

	List<ProductDTO> getProductList(@Param("cri") SearchCriteria cri, @Param("cate_code") Integer cate_code);
	
	int getProductListCount(Integer cate_code);
	
	List<ProductDTO> getRecommendList();
}
