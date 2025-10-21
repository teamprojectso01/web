package com.coreflow.shop.admin.category;   
// [패키지] 이 파일의 "폴더 주소". 스프링이 파일을 찾고 묶어 관리하는 기준이 됨.

/* ========== import(가져오기) ========== */
// 자바의 목록 상자. "List<타입>" = 같은 종류의 물건들(객체)을 줄 세워 담는 상자.
import java.util.List;
// "키 → 값"으로 짝을 지어 담는 작은 사전 같은 상자.
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
// HTTP 응답의 형식 라벨(ex. JSON)을 상수로 제공.
import org.springframework.http.MediaType;
// HTTP 응답 통째(데이터 + 상태코드)를 감싸 보내는 봉투 역할.
import org.springframework.http.ResponseEntity;
// "나는 화면/요청을 담당하는 안내원"이라고 스프링에게 알림.
import org.springframework.stereotype.Controller;
// 화면(HTML)로 값을 들고 갈 장바구니.
import org.springframework.ui.Model;
// 주소 연결(@GetMapping/@PostMapping 등), 파라미터 꺼내기(@RequestParam/@PathVariable) 같은 웹 기능 묶음.
import org.springframework.web.bind.annotation.*;

// 화면/컨트롤러/서비스 사이에서 오가는 데이터 모양(카테고리 정보를 담는 통).
import com.coreflow.shop.common.dto.CategoryDTO;

// 롬복: 귀찮은 생성자/로거를 자동으로 만들어 주는 도구
import lombok.RequiredArgsConstructor; // final 필드를 자동 생성자로 주입(의존성 주입)
import lombok.extern.slf4j.Slf4j;      // log.info(...)처럼 로그를 찍을 수 있게 로거를 자동 추가

@Slf4j // [로그] 코드 중간중간에 상황을 출력해(문제 파악용). 예: log.info("...");
@RequiredArgsConstructor // [생성자 자동] final 필드를 자동으로 받는 생성자를 만들어서 빈 주입을 깔끔하게.
@RequestMapping("/admin/categories") // [공통 주소] 이 클래스의 모든 메서드 앞에 붙는 기본 주소(건물 입구 주소)
@Controller // [컨트롤러] 이 클래스가 '요청 → 처리 → 화면/데이터 응답'을 담당한다고 등록
public class AdCategoryController {

    // [서비스 직원] 실제로 데이터 창고(DB)에서 꺼내오거나 저장하는 일을 대신 해줌.
    // final: 한 번 세팅되면 바뀌지 않는 안전한 참조(실수로 다른 걸 넣는 일을 방지).
    private final AdCategoryService adCategoryService;

    @GetMapping("/categorymenu") 
    public void categorymenu(Model model) throws Exception{
    	List<CategoryDTO> firCategories = adCategoryService.FirstCategorystList();  
    	model.addAttribute("firCategories",firCategories);  
    	
    }  
    
    //2차 카테고리목록 
    @GetMapping("/secondpCategoryList/{firstCategoryNum}")  
    public ResponseEntity<List<CategoryDTO>>
      SecondList(@PathVariable("firstCategoryNum") Integer firstCategoryNum) throws Exception{
    	ResponseEntity<List<CategoryDTO>> entity  = null; 
    	List<CategoryDTO> adCategoryList = adCategoryService.SecondCategoryList(firstCategoryNum); 
    	entity = new ResponseEntity<List<CategoryDTO>>(adCategoryList,HttpStatus.OK); 
    	
    	return entity;
    	
    } 
    
 // 1차, 2차 카테고리 정렬
 	@PostMapping("/arrayCategory")
 	public ResponseEntity<String> categorySort(@RequestParam("orderArr") List<Integer> orderArr) throws Exception {
 		ResponseEntity<String> entity = null;
 		
 		log.info("1차카테고리 정렬: " + orderArr.toString());
 		
 		adCategoryService.arrayCategory(orderArr);
 		
 		entity = new ResponseEntity<String>("success", HttpStatus.OK);
 		
 		return entity;
 	}
 	
 	// 1차 카테고리등록
 	@PostMapping("/inputFirstCategory")
 	public ResponseEntity<String> addFirstCategory(String cate_name) throws Exception {
 		ResponseEntity<String> entity = null;
 		adCategoryService.inputFirstCategory(cate_name);
 		entity = new ResponseEntity<String>("success", HttpStatus.OK);
 		
 		return entity;
 	}
 	
 	// 1차 카테고리수정
 	@PostMapping("/modifyFirstCategory")
 	public ResponseEntity<String> editFirstCategory(CategoryDTO dto) throws Exception {
 		ResponseEntity<String> entity = null;
 		
 		adCategoryService.modifyFirstCategory(dto);
 		entity = new ResponseEntity<String>("success", HttpStatus.OK);
 		
 		return entity;
 	}
 	
 	// 2차 카테고리 등록
 	@PostMapping("/addSecondCategory")
 	public ResponseEntity<String> addSecondCategory(CategoryDTO dto) throws Exception {
 		ResponseEntity<String> entity = null;
 		
 		adCategoryService.addSecondCategory(dto);
 		entity = new ResponseEntity<String>("success", HttpStatus.OK);
 		
 		return entity;
 	}
 	
 	// 2차 카테고리 수정
 	@PostMapping("/secondModifyCategory")
 	public ResponseEntity<String> editSecondCategory(CategoryDTO dto) throws Exception {
 		ResponseEntity<String> entity = null;
 		
 		adCategoryService.secondModifyCategory(dto);
 		entity = new ResponseEntity<String>("success", HttpStatus.OK);
 		
 		return entity;
 	}
 	
 	// 1차, 2차카테고리 삭제.
 	@PostMapping("/deleteModifyCategory")
 	public ResponseEntity<String> deleteCategory(Integer cate_code) throws Exception {
 		ResponseEntity<String> entity = null;
 		
 		adCategoryService.deleteModifyCategory(cate_code);
 		entity = new ResponseEntity<String>("success", HttpStatus.OK);
 		
 		return entity;
 	}
    
}

