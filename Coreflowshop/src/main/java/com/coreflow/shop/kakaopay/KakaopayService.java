package com.coreflow.shop.kakaopay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaopayService {

	@Value("${readUrl}")
	private String readUrl;
	
	@Value("${approveUrl}")
	private String approveUrl;
	
	@Value("${secretKey}")
	private String secretKey;
	
	@Value("${cid}")
	private String cid;
	
	@Value("${approval}")
	private String approval;
	
	@Value("${cancel}")
	private String cancel;
	
	@Value("${fail}")
	private String fail;
	
	String tid;
	String partner_order_id;
	String partner_user_id;
	
	public ReadyResponse ready(String partner_order_id, String partner_user_id, String item_name,
		Integer quantity, Integer total_amount, Integer tax_free_amount) {
		
		HttpHeaders headers = getHttpHeaders();
		
		ReadyRequest readyRequest = ReadyRequest.builder()
				.cid(cid)
				.partner_order_id(partner_order_id)
				.partner_user_id(partner_user_id)
				.item_name(item_name)
				.quantity(quantity)
				.total_amount(total_amount)
				.tax_free_amount(tax_free_amount)
				.approval_url(approval)
				.cancel_url(cancel)
				.fail_url(fail)
				.build();
		
		HttpEntity<ReadyRequest> entityMap = new HttpEntity<ReadyRequest>(readyRequest, headers);
		
		ResponseEntity<ReadyResponse> response = new RestTemplate().postForEntity(readUrl, entityMap, ReadyResponse.class);
		
		ReadyResponse readyResponse = response.getBody();
		
		tid = readyResponse.getTid();
		this.partner_order_id = partner_order_id;
		this.partner_user_id = partner_user_id;
		
		return readyResponse;
	}
	
	public boolean approve(String pg_token) {
		HttpHeaders headers = getHttpHeaders();
		
		ApproveRequest approveRequest = ApproveRequest.builder()
				.cid(cid)
				.tid(tid)
				.partner_order_id(partner_order_id)
				.partner_user_id(partner_user_id)
				.pg_token(pg_token)
				.build();
		
		HttpEntity<ApproveRequest> entityMap = new HttpEntity<ApproveRequest>(approveRequest, headers);
		
		ResponseEntity<ApproveResponse> response = new RestTemplate().postForEntity(approveUrl, entityMap, ApproveResponse.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public HttpHeaders getHttpHeaders() {
		 HttpHeaders headers = new HttpHeaders();
		 headers.set("Authorization", "SECRET_KEY " + secretKey);
		 headers.set("Content-Type", "application/json;charset=utf-8");
		 
		 return headers;
	}
}
