package com.korea.product2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korea.product2.model.ProductEntity;
import com.korea.product2.persistence.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService {
	
	@Autowired
	private final ProductRepository p_repository;
	
	public List<ProductEntity> findAll(){
		return p_repository.findAll();
	}
	
	public List<ProductEntity> create(final ProductEntity entity){
		validate(entity);
		
		p_repository.save(entity);
		return p_repository.findAll();
	}
	
	public void validate(final ProductEntity entity) {
		if(entity==null) {
			throw new RuntimeException("엔티티는 없을수 없음");
		}
	}
}
