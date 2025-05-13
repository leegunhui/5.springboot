package com.korea.product2.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Product")

public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
		private int productId;
		private String productName;
		private int productStock;
		private int productPrice;
		
		@CreationTimestamp
		private LocalDateTime registerDate;
		
		@UpdateTimestamp
		private LocalDateTime updateDate;
}
