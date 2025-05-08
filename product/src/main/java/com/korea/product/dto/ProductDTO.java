package com.korea.product.dto;

import com.korea.product.model.ProductEntity;
import com.korea.product.dto.ProductDTO;
import com.korea.product.model.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ProductDTO {
	private int id;
	private String name;
	private String description;
	private int price;
	
	// Entity -> DTO 변환
		public ProductDTO(ProductEntity entity) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.price = entity.getPrice();
		}
		
		// DTO -> Entity 변환
		public static ProductEntity toEntity(ProductDTO dto) {
			return ProductEntity.builder()
					.id(dto.getId())
					.name(dto.getName())
					.description(dto.getDescription())
					.price(dto.getPrice())
					.build();
		}
}
