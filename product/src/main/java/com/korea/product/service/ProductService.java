package com.korea.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korea.product.dto.ProductDTO;
import com.korea.product.model.ProductEntity;
import com.korea.product.persistence.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 추가
    public ProductDTO addProduct(ProductDTO productDTO) {
        ProductEntity entity = ProductDTO.toEntity(productDTO);
        ProductEntity savedEntity = productRepository.save(entity);
        return new ProductDTO(savedEntity);
    }

    // 필터링된 상품 조회
    public List<ProductDTO> getFilteredProducts(Double minPrice, String name) {
        List<ProductEntity> products = productRepository.findAll();

        // 가격 필터링 (minPrice가 있을 경우)
        if (minPrice != null) {
            products = products.stream()
                    .filter(product -> product.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        }

        // 이름 필터링 (name이 있을 경우)
        if (name != null && !name.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    // 상품 수정
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Optional<ProductEntity> optionalEntity = productRepository.findById(id);
        if (optionalEntity.isPresent()) {
            ProductEntity entity = optionalEntity.get();
            entity.setName(productDTO.getName());
            entity.setDescription(productDTO.getDescription());
            entity.setPrice(productDTO.getPrice());
            productRepository.save(entity);
            return new ProductDTO(entity);
        }
        return null;
    }

    // 상품 삭제
    public boolean deleteProduct(int id) {
        Optional<ProductEntity> optionalEntity = productRepository.findById(id);
        if (optionalEntity.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}