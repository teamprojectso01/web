package com.coreflow.shop.common.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
	
	// 상품후기 
	private Long rev_code;
	private String mbsp_id;
	private Integer pro_num;
	private String rev_content;
	private int rev_rate;
	private LocalDateTime rev_date;
	
	// 상품
	// 사용자 상품후기목록에서는 사용안함. 
	// 관리자 상품후기목록에서는 사용함. 
	private ProductDTO product;
	
	// 상품후기 답변
	// review 테이블과 review_replies테이블 (1:N관계)
	// left outer join
	// mybatis의 collection문법사용
	// private List<ReviewReply> replies; // 추가
	
	
	
}
