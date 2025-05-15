package com.korea.product2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korea.product2.dto.OrderDTO;
import com.korea.product2.dto.ProductDTO;
import com.korea.product2.model.OrderEntity;
import com.korea.product2.model.ProductEntity;
import com.korea.product2.persistence.OrderRepository;
import com.korea.product2.persistence.ProductRepository;

@Service
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	//주문내역 조회하기
    public List<OrderDTO> getAllOrderTotalPrices() {
        // JPQL 쿼리로 반환된 List<Object[]> 데이터를 받아옴
        List<Object[]> results = orderRepository.findAllOrderTotalPrices();
        
        // Object[] 데이터를 OrderDTO로 변환
        return results.stream()
                .map(result -> OrderDTO.builder()
                        .orderId(((int) result[0])) // orderId
                        .productName((String) result[1]) // productName
                        .productCount(((int) result[2]))// productCount
                        .productPrice(((int) result[3]))// productPrice
                        .totalPrice(((int) result[4])) // totalPrice
                        .orderDate(((String) result[5]))// orderDate 변환
                        .build())
                .collect(Collectors.toList());
    }
  //주문하기 기능
  	public List<ProductDTO> save(OrderDTO dto){
  		//productId와 productCount
  		
  		//상품 존재여부를 확인
  		//SELECT * FROM PRODUCT;
  		Optional<ProductEntity> option = productRepository.findById(dto.getProductId());
  		ProductEntity productEntity;
  		
  		//상품이 조회가 되면
  		if(option.isPresent()) {
  			//엔티티를 저장
  			productEntity = option.get();
  		} else {
  			//IllegalArgumentException : 잘못된 또는 부적절한 인수가 메서드에 전달됐을 때
  			//발생하는 예외
  			throw new IllegalArgumentException("상품을 찾을 수 없다");
  		}
  		
  		//재고확인
  		if(productEntity.getProductStock() < dto.getProductCount()) {
  			throw new RuntimeException("재고가 부족합니다. 현재 재고 : " + productEntity.getProductStock());
  		}
  		
  		//주문하기
  		OrderEntity order = OrderEntity.builder()
  								.product(productEntity)
  								.productCount(dto.getProductCount())
  								.build();
  		
  		//DB에 주문내역 저장하기
  		orderRepository.save(order);
  		
  		//재고 감소
  		productEntity.setProductStock(productEntity.getProductStock() - dto.getProductCount());
  		
  		//db에 수정된 재고로 업데이트
  		productRepository.save(productEntity);
  		
  		List<ProductDTO> dtos = productRepository.findAll().stream()
  									.map(entity ->new ProductDTO(entity))
  									.collect(Collectors.toList());
  		
  		return dtos;
  }
}