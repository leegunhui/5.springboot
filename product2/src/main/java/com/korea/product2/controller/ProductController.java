package com.korea.product2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korea.product2.dto.ProductDTO;
import com.korea.product2.dto.ResponseDTO;
import com.korea.product2.model.ProductEntity;
import com.korea.product2.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService p_service;
	
	@GetMapping
	public ResponseEntity<?> productList(){
		//데이터베이스에서 전체 조회해서 반환
		List<ProductEntity> entities = p_service.findAll();
		List<ProductDTO> dtos = entities
									.stream()
									.map(ProductDTO::new)
									.collect(Collectors.toList());
		ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	//상품 추가
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO dto){
		ProductEntity entity =ProductDTO.toEntity(dto);
		List<ProductEntity> entities = p_service.create(entity);
		List<ProductDTO> dtos = entities
				.stream()
				.map(ProductDTO::new)
				.collect(Collectors.toList());
		ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	
	
	
	
	
}
