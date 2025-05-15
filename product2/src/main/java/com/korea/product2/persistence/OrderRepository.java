package com.korea.product2.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.korea.product2.model.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{
   //모든 주문 내역을 조회하는 쿼리 만들기
   //총 결제 내역(주문개수 x 상품의 가격)도 조회를 해보자
   //@ManyToOne으로 설정된 관계 매핑 덕분에, OrerEntity에서 product필드를 통해
   //ProductEntity에 접근할 수 있다.
   //JPA는 JPQL 쿼리를 분석하고, OrderEntity에서 ProductEntity와의 관계가
   //설정된것을 감지하면 자동으로 SQL조인을 생성한다.
   //아래 쿼리문에서는 o.product를 통해 자동으로 ProductEntity와 조인이 이루어지기 때문에
   //JOIN을 명시적으로 쓰지 않아도 JPA는 적절한 SQL조인 쿼리를 생성한다.
   @Query("SELECT "
         + "o.orderId, "
         + "o.product.productName, "
         + "o.productCount, "
         + "o.product.productPrice, "
         + "(o.productCount * o.product.productPrice) AS totalPrice "
         + "from OrderEntity o")
   List<Object[]> findAllOrderTotalPrices();
   //제네릭이 오브젝트 배열인 이유
   //JPQL 쿼리에서 여러 개의 값을 선택(SELECT)하면, 한 행(row)에 대응하는 결과가
   //Object배열 형태로 반환되기 때문이다.
   //o.orderId -> Object[0]
   //o.product.productName -> Object[1]
   //..
}