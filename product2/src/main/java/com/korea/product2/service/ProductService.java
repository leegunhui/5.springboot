package com.korea.product2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.korea.product2.model.ProductEntity;
import com.korea.product2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	//레파지토리 파일을 주입
	//메모리에 올라가있는 bean을 변수에 주입한다.
	private final ProductRepository p_repository;
	
	public List<ProductEntity> findAll(){
		return p_repository.findAll();
	}
	
	//상품 추가기능
	public List<ProductEntity> create(ProductEntity entity){
		//넘어온 엔티티가 유효한지 검사
		validate(entity);
		
		//jpa에 데이터를 전달할 때는 Entity타입이어야 한다.
		p_repository.save(entity);
		
		//데이터베이스에 데이터를 추가하고 전체 정보를 반환
		return p_repository.findAll();
	}
	
	private void validate(ProductEntity entity) {
		if(entity == null) {
			throw new RuntimeException("Entity cannot be null");
		}
	}
	
	
	
	
	
}
