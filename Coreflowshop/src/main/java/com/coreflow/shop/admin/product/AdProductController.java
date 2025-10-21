package com.coreflow.shop.admin.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;   // ★MVC 컨트롤러 선언
import org.springframework.ui.Model;           // ★뷰로 데이터 전달 컨테이너
import org.springframework.web.bind.annotation.*; // ★라우팅/바인딩

import com.coreflow.shop.admin.category.AdCategoryService;
import com.coreflow.shop.common.constants.Constants;
import com.coreflow.shop.common.dto.CategoryDTO;
import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.FileUtils;
import com.coreflow.shop.common.utils.PageMaker;
import com.coreflow.shop.common.utils.SearchCriteria;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.Period;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.Entity;


/**
 * ★역할: 관리자 상품 CRUD 화면 라우팅
 * - 리스트/상세/등록폼/수정폼/등록/수정/삭제
 * - Service를 호출해서 DB 작업은 위임
 */ 
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products/*") // ★이 컨트롤러의 공통 URL prefix
public class AdProductController {
   
//	컨트롤러 작업후 
//	타임리프 작업 
	
    private final AdProductService adProductService; // 상품 관련 비즈니스 로직 담당 (상품 등록, 수정, 삭제, 목록).
    private final AdCategoryService adCategoryService; //카테고리 관련 비즈니스 로직 담당 (1차/2차 카테고리 목록, 카테고리 매핑)
   
    
   
   //수동생성자 롬복이 튕길떄
   //  public AdProductController(FileUtils fiUtils) {
   // 	 this.fileUtils = fileUtils;
   //  }
   
   
   // 이미지 업로드 경로 임시
   @Value("${com.coreflow.upload.path}")
   private String uploadPath;  
   
   @Value("${com.coreflow.upload.ckeditor.path}")
   private String uploadCKPath; 
   
   // 내일은 카테고리 작업함
   // 상품등록폼 1차카테고리 
   @GetMapping("/pro_insert")
  public void pro_insert(Model model) {
	   
	   //AdcategoryService
	   model.addAttribute("cate_list", adCategoryService.getFirstList());
   }
   
   // 상품등록 저장 
   @PostMapping("/pro_insert") 
   public String pro_insert(ProductDTO dto, MultipartFile product_img_upload) throws Exception {//String 임시변경
	   
	   log.info("상품정보"  + dto); 
	   
	   //상품이미지파일 업로드로직 
	   String updateFolder = FileUtils.getDateFolder();
	   String saveFileName = FileUtils.uploadFile(uploadPath, updateFolder, product_img_upload); 
	   
	   dto.setPro_up_folder(updateFolder);
	   dto.setPro_img(saveFileName); 
	   
	   //상품 db정보 저장 근데 무작정 컨트롤러 에서 입력 금지 xml에서 db입력후 주입작업후 db→xml→dto→mapper.java→service.java→controller.java 기억 
	   
	 //2)상품정보 DB저장
	 		adProductService.pro_insert(dto);
	 		
	 		return "redirect:/admin/products/pro_list";
	   
   }
   
   //  상품등록시 CKEditor에서 사용하는 상품이미지 업로드 작업 
// 상품등록시 CKEditor에서 사용하는 상품설명이미지 업로드작업
	// ckeditor 에서 사용하는 업로드. 클라이언트에서 보낸 파라미터명과 스프링에서 받는 파라미터명이 동일해야 한다.(규칙)
	@PostMapping("/imageupload")
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) throws Exception {
		
	  
		
		// 데이타를 바이트단위로 작업하는 출력스트림의 최상위클래스(추상)
		OutputStream out = null;
		PrintWriter printWriter = null; // 서버에서 클라이언트에게 응답정보를 보낼때 사용(업로드 파일정보)
		
		// 예외처리문법
		try {
			//1)CKeditor를 이용한 파일업로드 작업.
			String fileName = upload.getOriginalFilename(); // 클라이언트에서 업로드 파일명.   예>abc.gif
			byte[] bytes = upload.getBytes(); // 업로드하는 파일(abc.gif)을 나타내는 바이트배열
			
			// C:\\Dev\\upload\\ckeditor\\abc.gif
//			String ckUploadPath = uploadCKPath + "\\" + fileName;
//			ckUploadPath = ckUploadPath.replace("\\", File.separator);
			
			String ckUploadPath = uploadCKPath + File.separator + fileName;
			
			//스트림 out객체생성이 되면, 해당 경로에 파일은 생성된다. 파일크기는 0byte
			out = new FileOutputStream(ckUploadPath);
			
			out.write(bytes); // out스트림객체에 파일 byte배열을 채웠다.
			out.flush(); //out스트림객체에 존재하고 있는 byte배열을 빈파일에 쓰는 작업.
			
			//2)업로드한 파일정보를 클라이언트인 CKEditor로 보내주는 작업.
			// printWriter : 파일정보를 클라이언트쪽에 보낼때 사용하는 객체.
			printWriter = response.getWriter();
			
			// 매핑주소
			String fileUrl = "/admin/products/display/" + fileName;
			
			// ckeditor.js 4.12에서 파일정보를 아래와 같이 작업을 하도록 가이드
			// 파일정보를 JSON 데이타표현 형식 {"filename" : "abc.gif","uploaded":1,"url":"/display/abc.gif"} 
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}"); // 스트림에 채움.
				
			printWriter.flush();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			
			// 객체소멸은 객체생성의 역순으로 close()작업해준다.(이론)
			// out, printWriter 객체는 순서의 의미는 없다.
			if(out != null) {
				try {
					out.close(); // 메모리 소멸
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			if(printWriter != null) printWriter.close(); // 메모리 소멸
		}
	}
	
	//CKEditor에서 업로드된후 보여주는 기능
	// 이미지파일을 CKEditor를 통하여 화면에 출력하기
	@GetMapping("/display/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = FileUtils.getFile(uploadCKPath, fileName);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		return entity;
		
	} 
	
	//브라우저에 이미지 출력
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		log.info("날짜폴더: " + dateFolderName);
		log.info("파일명: " + fileName);
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName,fileName);
	}
   
	@GetMapping("/pro_list") 
	public String pro_list(@ModelAttribute("cri") SearchCriteria cri,
			@ModelAttribute("cate_code") String cate_code,
			@ModelAttribute("start_date") String start_date,
			@ModelAttribute("end_date") String end_date,
			@ModelAttribute("period") String period,
			Model model) throws Exception {
		
				
		
		cri.setPerPageNum(Constants.ADMIN_PRODUCT_LIST_COUNT); 
		
//		log.info("period={}, start={}, end={}, cate={}", period, start_date, end_date, cate_code);

		//1.상품 목록 
		List<Map<String, Object>> productList = adProductService.pro_list(
	            cri, period, start_date, end_date, cate_code);
		
		
		
		

	    model.addAttribute("pro_list", productList);
		//페이징 정보
		
		int totalcount =adProductService.getTotalcount(
				cri,period,start_date,end_date,cate_code
				);
		
		PageMaker pageMaker = new PageMaker(); 
		pageMaker.setCri(cri); 
		pageMaker.setTotalCount(totalcount); // 전체 상품수 
		
		model.addAttribute("pageMaker",pageMaker); 
		
		
		
		model.addAttribute("cate_list", adCategoryService.getFirstList());
		
		return "admin/products/ad_pro_list";
	}
 
  //상품수정 
	@GetMapping("/pro_edit") 
	public void pro_edit(@ModelAttribute("cri") SearchCriteria cri, Integer pro_num,Model model) throws Exception {
		
		// 1차카테고리들pro_edit_modify
		model.addAttribute("cate_list",adCategoryService.getFirstList());
		
		//상품수정
		ProductDTO productDTO = adProductService.pro_edit_form(pro_num);
		model.addAttribute("productDTO", productDTO);
		
	   
		//상품정보가 있는 2차카테고리 코드 
		int secondCategory = productDTO.getCate_code(); 
		 
		// 2차카테고리의 부모인 1차카테고리
	    CategoryDTO categoryDTO = adCategoryService.getFirstCategoryBySecondCategory(secondCategory);
	     
	     model.addAttribute("categoryDTO", categoryDTO); 
	     
	     
	     
	     int firstCategory = categoryDTO.getCate_prtcode(); // 지역 변수
	     
	     model.addAttribute("secondCategoryDTO",adCategoryService.secondList(firstCategory));
		
	    
		
	} 
	
	//상품수정 변경 
	@PostMapping("/pro_edit")
	public String pro_edit(ProductDTO dto, SearchCriteria cri, MultipartFile pro_img_upload,RedirectAttributes rttr) throws Exception {
		
		if(!pro_img_upload.isEmpty()) {
			
		//기존 이미지 삭제
			FileUtils.delete(uploadPath, "s_" + dto.getPro_up_folder(), dto.getPro_img(),"image");
			
		//새 이미지 업로드 
			String updateFolder = FileUtils.getDateFolder();
			
		//새 이미지 저장 경로
			String storageFileName = FileUtils.uploadFile(updateFolder, updateFolder,pro_img_upload);
			
		//DB에 수정 데이터를 보내기 
			dto.setPro_up_folder(updateFolder); /*새로운 날짜 폴더 저장*/ 
			dto.setPro_img(storageFileName); /*새 파일명 저장*/
			
		}
		//DB에 상품정보 변경 전달
		adProductService.pro_edit_modify(dto);
		
		//원래 상태의 목록 페이지로 돌아갈 때 필요한 페이징.검색 조건 전달
    	rttr.addAttribute("page",cri.getPage());
    	rttr.addAttribute("pagePageNum",cri.getPerPageNum());
    	rttr.addAttribute("searchType", cri.getSearchType()); 
    	rttr.addAttribute("keyword",cri.getKeyword());
    	
		return "redirect:/admin/products/pro_list";
	} 
	
	//상품삭제 
	@PostMapping("/pro_delete") 
	public String pro_delete(SearchCriteria cri,Integer pro_num,String pro_up_folder, String pro_img, RedirectAttributes rttr) throws Exception{
		
		//상품삭제 상품번호 주입 
		adProductService.pro_delete(pro_num);
		
		//이미지 파일 삭제 
		FileUtils.delete(uploadPath, pro_up_folder, pro_img, "image");// "image" : 이 파일이 이미지 파일임을 알려주는 용도 
		
		//원래자리로 주소이동 
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword()); 
		
		return "redirect:/admin/products/pro_list";
//				위 컨트롤러는 /admin/products/pro_delete?pro_num=...&pro_up_folder=...&pro_img=... 
//				형태로 GET 요청이 들어올 때 실행되며,
//				상품번호(pro_num)와 이미지경로(pro_up_folder/pro_img)를 파라미터로 받아
//				해당 상품을 삭제한 뒤 목록 페이지로 redirect 시킴 
	}
	
	//선택상품삭제 ajax용 
	@PostMapping("/pro_sel_delete_1")
	public ResponseEntity<String>pro_sel_delete_1(@RequestParam("pro_num_arr") int[] pro_num_arr) throws Exception {
		
		ResponseEntity<String>entity = null;
		
		//선택 상품 이미지 삭제 
		entity = new ResponseEntity<String>("success",HttpStatus.OK);
		return entity;
	}  
	
	/*선택상품삭제2(form태그)*/
	@PostMapping("/pro_sel_delete_2") 
	public String pro_sel_delete_form(
			int[] check,
			String[] pro_up_folder,
			String[] pro_img 
			) throws Exception{
		//배열 방식으로 선택한 상품을 DB 에서 삭제 (실제 삭제는 서비스 + 매퍼 담당)
	   adProductService.pro_sel_delete_2(check); 
	    
	   /*선택한 상품 DB삭제가 예외 없이 끝난후 for문으로 고아파일 삭제*/
	   for (int i = 0; i < check.length; i++ ) {
		   
		 FileUtils.delete(uploadPath, pro_up_folder[i], pro_img[i], "image");
		   
	   }
	  
	   //중복제출방지 실행되지 않게 리다이렉트
	   return "redirect:/admin/products/pro_list";
	} 
			
    //선택상품삭제(폼에서 이름+추가 정보를 DB로직에서 사용)
	@PostMapping("/pro_sel_delete_add_info") 
	public String pro_sel_delete_add_info(int[] check,String pro_name) throws Exception {
		
		//1. 주입 받은 SERVICE에 hashmap으로 삭제요청데이터를 전달하여 DB에서 삭제 
		adProductService.pro_sel_delete_add_info(check, pro_name);//HashMap
		
		
		
		return "redirect:/adimin/products/pro_list";
	}
}
